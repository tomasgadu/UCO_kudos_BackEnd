package co.edu.uco.kudos.negocio.negocio.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import co.edu.uco.kudos.dao.factoria.DAOFactory;
import co.edu.uco.kudos.dominio.FacultadDominio;
import co.edu.uco.kudos.dominio.ProgramaAcademicoDominio;
import co.edu.uco.kudos.dominio.RolDominio;
import co.edu.uco.kudos.dominio.SolicitudRegistroDominio;
import co.edu.uco.kudos.dominio.UsuarioDominio;
import co.edu.uco.kudos.dominio.enums.EstadoCuenta;
import co.edu.uco.kudos.dominio.enums.EstadoSolicitud;
import co.edu.uco.kudos.dominio.enums.TipoVerificacion;
import co.edu.uco.kudos.entidad.SolicitudRegistroEntidad;
import co.edu.uco.kudos.entidad.UsuarioEntidad;
import co.edu.uco.kudos.negocio.negocio.UsuarioNegocio;
import co.edu.uco.kudos.negocio.negocio.ensamblador.impl.RolEnsambladorEntidad;
import co.edu.uco.kudos.negocio.negocio.ensamblador.impl.SolicitudRegistroEnsambladorEntidad;
import co.edu.uco.kudos.negocio.negocio.ensamblador.impl.UsuarioEnsambladorEntidad;
import co.edu.uco.kudos.negocio.negocio.eventos.EventoDominio;
import co.edu.uco.kudos.negocio.negocio.eventos.PublicadorEventos;
import co.edu.uco.kudos.negocio.negocio.reglas.impl.usuario.AsegurarAmbitoCorrespondaRolRegla;
import co.edu.uco.kudos.negocio.negocio.reglas.impl.usuario.AsegurarCargoDisponibleRegla;
import co.edu.uco.kudos.negocio.negocio.reglas.impl.usuario.AsegurarContactoNoExistaRegla;
import co.edu.uco.kudos.negocio.negocio.reglas.impl.usuario.AsegurarCorreoInstitucionalRegla;
import co.edu.uco.kudos.negocio.negocio.reglas.impl.usuario.AsegurarRolPermiteRegistroRegla;
import co.edu.uco.kudos.negocio.negocio.reglas.impl.usuario.ObtenerUsuarioActivoRegla;
import co.edu.uco.kudos.negocio.negocio.reglas.impl.usuario.ValidarDatosUsuarioRegla;
import co.edu.uco.kudos.transversal.catalogo.CatalogoMensajes;
import co.edu.uco.kudos.transversal.excepciones.KudosNegocioExcepcion;
import co.edu.uco.kudos.transversal.utilitarios.UtilClave;
import co.edu.uco.kudos.transversal.utilitarios.UtilFecha;
import co.edu.uco.kudos.transversal.utilitarios.UtilTexto;
import co.edu.uco.kudos.transversal.utilitarios.UtilUUID;

public final class UsuarioNegocioImpl implements UsuarioNegocio {

	private static final String ENTIDAD = "Usuario";

	private final DAOFactory daoFactory;
	private final PublicadorEventos eventos;

	public UsuarioNegocioImpl(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
		this.eventos = new PublicadorEventos(daoFactory);
	}

	/**
	 * RF-001 a RF-005 y RF-012: crea la cuenta REGISTRADA, su solicitud de aprobación y las
	 * verificaciones de correo y celular (USU-P-0018). La cuenta se activa más adelante.
	 */
	@Override
	public UsuarioDominio registrarUsuario(UsuarioDominio datos, UUID idFacultad, UUID idProgramaAcademico) {
		ValidarDatosUsuarioRegla.obtenerInstancia().ejecutar(datos, Boolean.TRUE);
		var correo = datos.getCorreoInstitucional().toLowerCase();
		AsegurarCorreoInstitucionalRegla.obtenerInstancia().ejecutar(correo, daoFactory);
		AsegurarContactoNoExistaRegla.obtenerInstancia().ejecutar(correo, datos.getNumeroCelular(),
				UtilUUID.obtenerUUIDDefecto(), daoFactory);

		var rol = obtenerRol(datos.getRol().getId());
		AsegurarRolPermiteRegistroRegla.obtenerInstancia().ejecutar(rol);
		AsegurarAmbitoCorrespondaRolRegla.obtenerInstancia().ejecutar(rol, idFacultad, idProgramaAcademico, daoFactory);
		AsegurarCargoDisponibleRegla.obtenerInstancia().ejecutar(rol, idFacultad, idProgramaAcademico,
				UtilUUID.obtenerUUIDDefecto(), daoFactory);

		var dao = daoFactory.obtenerUsuarioDAO();
		var texto = UtilTexto.getUtilTexto();
		var usuario = new UsuarioDominio.Builder()
				.id(UtilUUID.generarUnico(id -> !UtilUUID.esValorDefecto(dao.consultarPorId(id).getId())))
				.nombres(texto.aplicarMayusculaInicial(datos.getNombres()))
				.apellidos(texto.aplicarMayusculaInicial(datos.getApellidos())).correoInstitucional(correo)
				.numeroCelular(datos.getNumeroCelular()).clave(UtilClave.cifrar(datos.getClave()))
				.estadoCuenta(EstadoCuenta.REGISTRADA).rol(rol).fechaRegistro(UtilFecha.obtenerFechaHoraActual()).build();
		dao.crear(UsuarioEnsambladorEntidad.getInstancia().convertirAEntidad(usuario));

		crearSolicitud(usuario, rol, idFacultad, idProgramaAcademico);

		var verificaciones = new VerificacionContactoNegocioImpl(daoFactory);
		verificaciones.generarVerificacion(usuario, TipoVerificacion.CORREO);
		verificaciones.generarVerificacion(usuario, TipoVerificacion.CELULAR);

		eventos.publicar(EventoDominio.de("USUARIO_REGISTRADO", usuario.getId(), ENTIDAD, usuario.getId(), null,
				rol.getCodigo().name()));
		return usuario;
	}

	private void crearSolicitud(UsuarioDominio usuario, RolDominio rol, UUID idFacultad, UUID idPrograma) {
		var dao = daoFactory.obtenerSolicitudRegistroDAO();
		var solicitud = new SolicitudRegistroDominio.Builder()
				.id(UtilUUID.generarUnico(id -> !UtilUUID.esValorDefecto(dao.consultarPorId(id).getId())))
				.solicitante(usuario).rolSolicitado(rol).facultad(new FacultadDominio.Builder().id(idFacultad).build())
				.programaAcademico(new ProgramaAcademicoDominio.Builder().id(idPrograma).build())
				.estado(EstadoSolicitud.PENDIENTE).fechaSolicitud(UtilFecha.obtenerFechaHoraActual()).build();
		dao.crear(SolicitudRegistroEnsambladorEntidad.getInstancia().convertirAEntidad(solicitud));
	}

	/**
	 * USU-P-0011 y USU-P-0012: credenciales correctas y cuenta activa. Si falta algo, se
	 * le dice al usuario exactamente qué le falta (RN-089).
	 */
	@Override
	public UsuarioDominio iniciarSesion(String correoInstitucional, String clave) {
		var filtro = new UsuarioEntidad();
		filtro.setCorreoInstitucional(UtilTexto.getUtilTexto().quitarEspacioEnBlanco(correoInstitucional).toLowerCase());
		var encontrados = daoFactory.obtenerUsuarioDAO().consultarPorFiltro(filtro);

		if (encontrados.isEmpty() || !UtilClave.coincide(clave, encontrados.get(0).getClave())) {
			throw KudosNegocioExcepcion.crear(CatalogoMensajes.Usuario.USUARIO_ERROR_CREDENCIALES);
		}
		var usuario = UsuarioEnsambladorEntidad.getInstancia().convertirADominio(encontrados.get(0));

		if (!usuario.estaActivo()) {
			if (usuario.getEstadoCuenta() != EstadoCuenta.REGISTRADA) {
				throw KudosNegocioExcepcion.crear(CatalogoMensajes.Seguridad.USUARIO_ERROR_CUENTA_NO_ACTIVA);
			}
			throw KudosNegocioExcepcion
					.crear(CatalogoMensajes.Usuario.USUARIO_ERROR_CUENTA_PENDIENTE.formatted(describirPendientes(usuario)));
		}
		eventos.publicar(EventoDominio.de("SESION_INICIADA", usuario.getId(), ENTIDAD, usuario.getId(), null, null));
		return usuario;
	}

	private String describirPendientes(UsuarioDominio usuario) {
		var pendientes = new ArrayList<String>();
		if (!tieneSolicitudAprobada(usuario.getId())) {
			pendientes.add(CatalogoMensajes.Usuario.PENDIENTE_APROBACION);
		}
		if (!usuario.isCorreoVerificado()) {
			pendientes.add(CatalogoMensajes.Usuario.PENDIENTE_CORREO);
		}
		if (!usuario.isCelularVerificado()) {
			pendientes.add(CatalogoMensajes.Usuario.PENDIENTE_CELULAR);
		}
		return String.join(", ", pendientes);
	}

	@Override
	public UsuarioDominio consultarUsuario(UUID id) {
		return ObtenerUsuarioActivoRegla.obtenerExistente(id, daoFactory);
	}

	/**
	 * RF-009 a RF-011 (RN-102 a RN-111): el usuario actualiza sus datos. Si cambia el correo o
	 * el celular, debe volver a verificarlo. El rol nunca se cambia aquí (RN-107).
	 */
	@Override
	public UsuarioDominio actualizarMiInformacion(UUID idUsuario, UsuarioDominio datos) {
		var actual = ObtenerUsuarioActivoRegla.obtenerExistente(idUsuario, daoFactory);
		ValidarDatosUsuarioRegla.obtenerInstancia().ejecutar(datos, Boolean.FALSE);
		var correo = datos.getCorreoInstitucional().toLowerCase();
		AsegurarCorreoInstitucionalRegla.obtenerInstancia().ejecutar(correo, daoFactory);
		AsegurarContactoNoExistaRegla.obtenerInstancia().ejecutar(correo, datos.getNumeroCelular(), idUsuario,
				daoFactory);

		var texto = UtilTexto.getUtilTexto();
		var actualizado = actual.copia().nombres(texto.aplicarMayusculaInicial(datos.getNombres()))
				.apellidos(texto.aplicarMayusculaInicial(datos.getApellidos())).build();
		var cambioCorreo = !actual.getCorreoInstitucional().equals(correo);
		var cambioCelular = !actual.getNumeroCelular().equals(datos.getNumeroCelular());
		if (cambioCorreo) {
			actualizado = actualizado.cambiarCorreo(correo);
		}
		if (cambioCelular) {
			actualizado = actualizado.cambiarCelular(datos.getNumeroCelular());
		}
		guardar(actualizado);

		var verificaciones = new VerificacionContactoNegocioImpl(daoFactory);
		if (cambioCorreo) {
			verificaciones.generarVerificacion(actualizado, TipoVerificacion.CORREO);
		}
		if (cambioCelular) {
			verificaciones.generarVerificacion(actualizado, TipoVerificacion.CELULAR);
		}
		eventos.publicar(EventoDominio.de("INFORMACION_PERSONAL_ACTUALIZADA", idUsuario, ENTIDAD, idUsuario,
				resumen(actual), resumen(actualizado)));
		return actualizado;
	}

	@Override
	public List<UsuarioDominio> consultarUsuariosActivos(UsuarioDominio filtro) {
		var filtroEntidad = new UsuarioEntidad();
		filtroEntidad.getRol().setId(filtro.getRol().getId());
		filtroEntidad.setEstadoCuenta(EstadoCuenta.ACTIVA.name());
		return UsuarioEnsambladorEntidad.getInstancia()
				.convertirADominio(daoFactory.obtenerUsuarioDAO().consultarPorFiltro(filtroEntidad));
	}

	/**
	 * Se llama después de verificar un dato o aprobar la solicitud: si ya se cumplen las tres
	 * condiciones, la cuenta pasa a ACTIVA (RN-085 a RN-088).
	 */
	@Override
	public void evaluarActivacion(UUID idUsuario) {
		var usuario = ObtenerUsuarioActivoRegla.obtenerExistente(idUsuario, daoFactory);
		if (usuario.getEstadoCuenta() != EstadoCuenta.REGISTRADA || !usuario.tieneContactoVerificado()
				|| !tieneSolicitudAprobada(idUsuario)) {
			return;
		}
		guardar(usuario.activar(true));
		eventos.publicar(EventoDominio.de("CUENTA_ACTIVADA", idUsuario, ENTIDAD, idUsuario,
				EstadoCuenta.REGISTRADA.name(), EstadoCuenta.ACTIVA.name()));
	}

	private boolean tieneSolicitudAprobada(UUID idUsuario) {
		var filtro = new SolicitudRegistroEntidad();
		filtro.getSolicitante().setId(idUsuario);
		filtro.setEstado(EstadoSolicitud.APROBADA.name());
		return !daoFactory.obtenerSolicitudRegistroDAO().consultarPorFiltro(filtro).isEmpty();
	}

	private RolDominio obtenerRol(UUID idRol) {
		var rol = daoFactory.obtenerRolDAO().consultarPorId(idRol);
		if (UtilUUID.esValorDefecto(rol.getId())) {
			throw KudosNegocioExcepcion.crear(CatalogoMensajes.Rol.USUARIO_ERROR_NO_EXISTE);
		}
		return RolEnsambladorEntidad.getInstancia().convertirADominio(rol);
	}

	private void guardar(UsuarioDominio usuario) {
		daoFactory.obtenerUsuarioDAO().actualizar(usuario.getId(),
				UsuarioEnsambladorEntidad.getInstancia().convertirAEntidad(usuario));
	}

	private static String resumen(UsuarioDominio usuario) {
		return usuario.getNombreCompleto() + " | " + usuario.getCorreoInstitucional() + " | "
				+ usuario.getNumeroCelular();
	}
}
