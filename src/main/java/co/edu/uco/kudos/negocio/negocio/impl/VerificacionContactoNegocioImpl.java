package co.edu.uco.kudos.negocio.negocio.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.edu.uco.kudos.dao.factoria.DAOFactory;
import co.edu.uco.kudos.dominio.UsuarioDominio;
import co.edu.uco.kudos.dominio.VerificacionContactoDominio;
import co.edu.uco.kudos.dominio.enums.EstadoVerificacion;
import co.edu.uco.kudos.dominio.enums.TipoVerificacion;
import co.edu.uco.kudos.entidad.ParametroSistemaEntidad;
import co.edu.uco.kudos.entidad.UsuarioEntidad;
import co.edu.uco.kudos.entidad.VerificacionContactoEntidad;
import co.edu.uco.kudos.negocio.negocio.VerificacionContactoNegocio;
import co.edu.uco.kudos.negocio.negocio.ensamblador.impl.UsuarioEnsambladorEntidad;
import co.edu.uco.kudos.negocio.negocio.ensamblador.impl.VerificacionContactoEnsambladorEntidad;
import co.edu.uco.kudos.negocio.negocio.eventos.EventoDominio;
import co.edu.uco.kudos.negocio.negocio.eventos.PublicadorEventos;
import co.edu.uco.kudos.negocio.negocio.reglas.impl.ValidadorCampos;
import co.edu.uco.kudos.transversal.catalogo.CatalogoMensajes;
import co.edu.uco.kudos.transversal.excepciones.KudosNegocioExcepcion;
import co.edu.uco.kudos.transversal.utilitarios.UtilClave;
import co.edu.uco.kudos.transversal.utilitarios.UtilFecha;
import co.edu.uco.kudos.transversal.utilitarios.UtilTexto;
import co.edu.uco.kudos.transversal.utilitarios.UtilUUID;

public final class VerificacionContactoNegocioImpl implements VerificacionContactoNegocio {

	private static final Logger LOGGER = LoggerFactory.getLogger(VerificacionContactoNegocioImpl.class);
	private static final String PARAMETRO_MINUTOS = "MINUTOS_EXPIRACION_CODIGO";
	private static final long MINUTOS_DEFECTO = 15;

	private final DAOFactory daoFactory;

	public VerificacionContactoNegocioImpl(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	@Override
	public void generarVerificacion(UsuarioDominio usuario, TipoVerificacion tipo) {
		var dao = daoFactory.obtenerVerificacionContactoDAO();
		for (var pendiente : buscarPendientes(usuario, tipo)) {
			pendiente.setEstado(EstadoVerificacion.EXPIRADA.name());
			dao.actualizar(pendiente.getId(), pendiente);
		}

		var ahora = UtilFecha.obtenerFechaHoraActual();
		var valor = tipo == TipoVerificacion.CORREO ? usuario.getCorreoInstitucional() : usuario.getNumeroCelular();
		var verificacion = new VerificacionContactoDominio.Builder().id(UtilUUID.generar()).usuario(usuario).tipo(tipo)
				.valorVerificado(valor).codigo(UtilClave.generarCodigoVerificacion()).fechaEnvio(ahora)
				.fechaExpiracion(ahora.plusMinutes(minutosVigencia())).estado(EstadoVerificacion.PENDIENTE).build();
		dao.crear(VerificacionContactoEnsambladorEntidad.getInstancia().convertirAEntidad(verificacion));

		LOGGER.info(CatalogoMensajes.Usuario.TECNICO_CODIGO_SIMULADO, tipo, UtilTexto.getUtilTexto().limpiarParaRegistro(valor),
				verificacion.getCodigo());
	}

	@Override
	public void verificarCodigo(String correoInstitucional, TipoVerificacion tipo, String codigo) {
		ValidadorCampos.validarFormato(codigo, "código de verificación", UtilTexto.CODIGO_VERIFICACION);
		var usuario = buscarPorCorreo(correoInstitucional);
		var pendientes = buscarPendientes(usuario, tipo);
		if (pendientes.isEmpty()) {
			throw KudosNegocioExcepcion.crear(CatalogoMensajes.Verificacion.USUARIO_ERROR_SIN_PENDIENTE);
		}
		var verificacion = VerificacionContactoEnsambladorEntidad.getInstancia().convertirADominio(pendientes.get(0));
		var ahora = UtilFecha.obtenerFechaHoraActual();
		if (verificacion.estaExpirada(ahora)) {
			throw KudosNegocioExcepcion.crear(CatalogoMensajes.Verificacion.USUARIO_ERROR_CODIGO_EXPIRADO);
		}
		if (!verificacion.codigoCoincide(codigo)) {
			throw KudosNegocioExcepcion.crear(CatalogoMensajes.Verificacion.USUARIO_ERROR_CODIGO_INCORRECTO);
		}

		var verificada = verificacion.copia().estado(EstadoVerificacion.VERIFICADA).fechaVerificacion(ahora).build();
		daoFactory.obtenerVerificacionContactoDAO().actualizar(verificada.getId(),
				VerificacionContactoEnsambladorEntidad.getInstancia().convertirAEntidad(verificada));

		var actualizado = tipo == TipoVerificacion.CORREO ? usuario.copia().correoVerificado(true).build()
				: usuario.copia().celularVerificado(true).build();
		daoFactory.obtenerUsuarioDAO().actualizar(usuario.getId(),
				UsuarioEnsambladorEntidad.getInstancia().convertirAEntidad(actualizado));

		new PublicadorEventos(daoFactory).publicar(EventoDominio.de(tipo.name() + "_VERIFICADO", usuario.getId(),
				"VerificacionContacto", verificada.getId(), EstadoVerificacion.PENDIENTE.name(),
				EstadoVerificacion.VERIFICADA.name()));
		new UsuarioNegocioImpl(daoFactory).evaluarActivacion(usuario.getId());
	}

	@Override
	public void reenviarCodigo(String correoInstitucional, TipoVerificacion tipo) {
		generarVerificacion(buscarPorCorreo(correoInstitucional), tipo);
	}

	private java.util.List<VerificacionContactoEntidad> buscarPendientes(UsuarioDominio usuario,
			TipoVerificacion tipo) {
		var filtro = new VerificacionContactoEntidad();
		filtro.getUsuario().setId(usuario.getId());
		filtro.setTipo(tipo.name());
		filtro.setEstado(EstadoVerificacion.PENDIENTE.name());
		return daoFactory.obtenerVerificacionContactoDAO().consultarPorFiltro(filtro);
	}

	private UsuarioDominio buscarPorCorreo(String correo) {
		var filtro = new UsuarioEntidad();
		filtro.setCorreoInstitucional(UtilTexto.getUtilTexto().quitarEspacioEnBlanco(correo).toLowerCase());
		var encontrados = daoFactory.obtenerUsuarioDAO().consultarPorFiltro(filtro);
		if (encontrados.isEmpty()) {
			throw KudosNegocioExcepcion.crear(CatalogoMensajes.Usuario.USUARIO_ERROR_NO_EXISTE);
		}
		return UsuarioEnsambladorEntidad.getInstancia().convertirADominio(encontrados.get(0));
	}

	private long minutosVigencia() {
		var filtro = new ParametroSistemaEntidad();
		filtro.setClave(PARAMETRO_MINUTOS);
		var parametros = daoFactory.obtenerParametroSistemaDAO().consultarPorFiltro(filtro);
		try {
			return parametros.isEmpty() ? MINUTOS_DEFECTO : Long.parseLong(parametros.get(0).getValor());
		} catch (NumberFormatException excepcion) {
			return MINUTOS_DEFECTO;
		}
	}
}
