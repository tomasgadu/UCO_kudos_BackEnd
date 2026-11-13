package co.edu.uco.kudos.negocio.negocio.impl;

import java.util.List;
import java.util.UUID;

import co.edu.uco.kudos.dao.factoria.DAOFactory;
import co.edu.uco.kudos.dominio.SolicitudRegistroDominio;
import co.edu.uco.kudos.dominio.UsuarioDominio;
import co.edu.uco.kudos.dominio.enums.EstadoSolicitud;
import co.edu.uco.kudos.dominio.enums.TipoNotificacion;
import co.edu.uco.kudos.entidad.CoordinacionFacultadEntidad;
import co.edu.uco.kudos.entidad.CoordinacionProgramaEntidad;
import co.edu.uco.kudos.entidad.SolicitudRegistroEntidad;
import co.edu.uco.kudos.entidad.VinculacionProgramaEntidad;
import co.edu.uco.kudos.negocio.negocio.SolicitudRegistroNegocio;
import co.edu.uco.kudos.negocio.negocio.ensamblador.impl.RolEnsambladorEntidad;
import co.edu.uco.kudos.negocio.negocio.ensamblador.impl.SolicitudRegistroEnsambladorEntidad;
import co.edu.uco.kudos.negocio.negocio.eventos.EventoDominio;
import co.edu.uco.kudos.negocio.negocio.eventos.PublicadorEventos;
import co.edu.uco.kudos.negocio.negocio.reglas.impl.ValidadorCampos;
import co.edu.uco.kudos.negocio.negocio.reglas.impl.usuario.AsegurarCargoDisponibleRegla;
import co.edu.uco.kudos.negocio.negocio.reglas.impl.usuario.ObtenerUsuarioActivoRegla;
import co.edu.uco.kudos.negocio.negocio.servicios.AutorizadorSolicitudes;
import co.edu.uco.kudos.transversal.catalogo.CatalogoMensajes;
import co.edu.uco.kudos.transversal.excepciones.KudosNegocioExcepcion;
import co.edu.uco.kudos.transversal.utilitarios.UtilFecha;
import co.edu.uco.kudos.transversal.utilitarios.UtilUUID;

/**
 * Flujo de aprobación por rol (RN-090 a RN-096): cada solicitud la decide el rol aprobador
 * dentro de su ámbito. Al aprobar se asigna la coordinación o la vinculación al programa.
 */
public final class SolicitudRegistroNegocioImpl implements SolicitudRegistroNegocio {

	private static final String ENTIDAD = "SolicitudRegistro";

	private final DAOFactory daoFactory;
	private final AutorizadorSolicitudes autorizador;
	private final PublicadorEventos eventos;

	public SolicitudRegistroNegocioImpl(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
		this.autorizador = new AutorizadorSolicitudes(daoFactory);
		this.eventos = new PublicadorEventos(daoFactory);
	}

	@Override
	public List<SolicitudRegistroDominio> consultarSolicitudesPendientes(UUID idActor) {
		var actor = ObtenerUsuarioActivoRegla.ejecutar(idActor, daoFactory);
		var filtro = new SolicitudRegistroEntidad();
		filtro.setEstado(EstadoSolicitud.PENDIENTE.name());
		var pendientes = SolicitudRegistroEnsambladorEntidad.getInstancia()
				.convertirADominio(daoFactory.obtenerSolicitudRegistroDAO().consultarPorFiltro(filtro));
		return pendientes.stream().filter(solicitud -> autorizador.puedeDecidir(actor, solicitud)).toList();
	}

	@Override
	public SolicitudRegistroDominio aprobarSolicitud(UUID idActor, UUID idSolicitud, String observacion) {
		var actor = ObtenerUsuarioActivoRegla.ejecutar(idActor, daoFactory);
		var solicitud = obtenerDecidible(actor, idSolicitud);
		var rol = RolEnsambladorEntidad.getInstancia()
				.convertirADominio(daoFactory.obtenerRolDAO().consultarPorId(solicitud.getRolSolicitado().getId()));
		AsegurarCargoDisponibleRegla.obtenerInstancia().ejecutar(rol, solicitud.getFacultad().getId(),
				solicitud.getProgramaAcademico().getId(), solicitud.getSolicitante().getId(), daoFactory);

		var aprobada = solicitud.decidir(true, actor, observacion, UtilFecha.obtenerFechaHoraActual());
		guardar(aprobada);
		asignarAmbito(aprobada);
		publicarDecision(actor, solicitud, aprobada, "aprobada");

		new UsuarioNegocioImpl(daoFactory).evaluarActivacion(solicitud.getSolicitante().getId());
		return aprobada;
	}

	/** SOL-P-0008: todo rechazo necesita una observación. */
	@Override
	public SolicitudRegistroDominio rechazarSolicitud(UUID idActor, UUID idSolicitud, String observacion) {
		var actor = ObtenerUsuarioActivoRegla.ejecutar(idActor, daoFactory);
		var solicitud = obtenerDecidible(actor, idSolicitud);
		ValidadorCampos.validarTextoObligatorio(observacion, "observación", 10, 250);

		var rechazada = solicitud.decidir(false, actor, observacion, UtilFecha.obtenerFechaHoraActual());
		guardar(rechazada);
		publicarDecision(actor, solicitud, rechazada, "rechazada");
		return rechazada;
	}

	private SolicitudRegistroDominio obtenerDecidible(UsuarioDominio actor, UUID idSolicitud) {
		var entidad = daoFactory.obtenerSolicitudRegistroDAO().consultarPorId(idSolicitud);
		if (UtilUUID.esValorDefecto(entidad.getId())) {
			throw KudosNegocioExcepcion.crear(CatalogoMensajes.SolicitudRegistro.USUARIO_ERROR_NO_EXISTE);
		}
		var solicitud = SolicitudRegistroEnsambladorEntidad.getInstancia().convertirADominio(entidad);
		if (autorizador.esAutosolicitud(actor, solicitud)) {
			throw KudosNegocioExcepcion.crear(CatalogoMensajes.SolicitudRegistro.USUARIO_ERROR_AUTOAPROBACION);
		}
		if (!autorizador.puedeDecidir(actor, solicitud)) {
			throw KudosNegocioExcepcion.crear(CatalogoMensajes.SolicitudRegistro.USUARIO_ERROR_NO_PUEDE_DECIDIR);
		}
		return solicitud;
	}

	/** SOL-P-0010: al aprobar se crea la coordinación o la vinculación que corresponde al rol. */
	private void asignarAmbito(SolicitudRegistroDominio solicitud) {
		var ahora = UtilFecha.obtenerFechaHoraActual();
		var idUsuario = solicitud.getSolicitante().getId();
		switch (solicitud.getRolSolicitado().getCodigo()) {
		case COORDINADOR_FACULTAD -> {
			var coordinacion = new CoordinacionFacultadEntidad();
			coordinacion.setId(UtilUUID.generar());
			coordinacion.getCoordinador().setId(idUsuario);
			coordinacion.getFacultad().setId(solicitud.getFacultad().getId());
			coordinacion.setFechaInicio(ahora);
			daoFactory.obtenerCoordinacionFacultadDAO().crear(coordinacion);
			eventos.publicar(EventoDominio.de("COORDINADOR_FACULTAD_ASIGNADO", idUsuario, "CoordinacionFacultad",
					coordinacion.getId(), null, solicitud.getFacultad().getId().toString()));
		}
		case COORDINADOR_PROGRAMA -> {
			var coordinacion = new CoordinacionProgramaEntidad();
			coordinacion.setId(UtilUUID.generar());
			coordinacion.getCoordinador().setId(idUsuario);
			coordinacion.getProgramaAcademico().setId(solicitud.getProgramaAcademico().getId());
			coordinacion.setFechaInicio(ahora);
			daoFactory.obtenerCoordinacionProgramaDAO().crear(coordinacion);
			eventos.publicar(EventoDominio.de("COORDINADOR_PROGRAMA_ASIGNADO", idUsuario, "CoordinacionPrograma",
					coordinacion.getId(), null, solicitud.getProgramaAcademico().getId().toString()));
		}
		case DOCENTE, ESTUDIANTE -> {
			var vinculacion = new VinculacionProgramaEntidad();
			vinculacion.setId(UtilUUID.generar());
			vinculacion.getUsuario().setId(idUsuario);
			vinculacion.getProgramaAcademico().setId(solicitud.getProgramaAcademico().getId());
			vinculacion.setFechaInicio(ahora);
			daoFactory.obtenerVinculacionProgramaDAO().crear(vinculacion);
			eventos.publicar(EventoDominio.de(solicitud.getRolSolicitado().getCodigo().name() + "_VINCULADO_A_PROGRAMA",
					idUsuario, "VinculacionPrograma", vinculacion.getId(), null,
					solicitud.getProgramaAcademico().getId().toString()));
		}
		default -> {
			// Rector, Coordinador Académico y Administrador no tienen ámbito.
		}
		}
	}

	private void publicarDecision(UsuarioDominio actor, SolicitudRegistroDominio antes,
			SolicitudRegistroDominio despues, String resultado) {
		var nombreEvento = despues.getEstado() == EstadoSolicitud.APROBADA ? "USUARIO_APROBADO" : "SOLICITUD_RECHAZADA";
		var mensaje = CatalogoMensajes.SolicitudRegistro.NOTIFICACION_RESUELTA
				.formatted(antes.getRolSolicitado().getNombre(), resultado);
		eventos.publicar(EventoDominio.de(nombreEvento, actor.getId(), ENTIDAD, despues.getId(), antes.getEstado().name(),
				despues.getEstado().name()).notificando(antes.getSolicitante().getId(),
						TipoNotificacion.SOLICITUD_RESUELTA, mensaje));
	}

	private void guardar(SolicitudRegistroDominio solicitud) {
		daoFactory.obtenerSolicitudRegistroDAO().actualizar(solicitud.getId(),
				SolicitudRegistroEnsambladorEntidad.getInstancia().convertirAEntidad(solicitud));
	}
}
