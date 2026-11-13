package co.edu.uco.kudos.negocio.negocio.servicios;

import co.edu.uco.kudos.dao.factoria.DAOFactory;
import co.edu.uco.kudos.dominio.SolicitudRegistroDominio;
import co.edu.uco.kudos.dominio.UsuarioDominio;
import co.edu.uco.kudos.entidad.CoordinacionFacultadEntidad;
import co.edu.uco.kudos.entidad.CoordinacionProgramaEntidad;
import co.edu.uco.kudos.transversal.utilitarios.UtilFecha;

public final class AutorizadorSolicitudes {

	private final DAOFactory daoFactory;

	public AutorizadorSolicitudes(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	public boolean esAutosolicitud(UsuarioDominio actor, SolicitudRegistroDominio solicitud) {
		return actor.getId().equals(solicitud.getSolicitante().getId());
	}

	public boolean puedeDecidir(UsuarioDominio actor, SolicitudRegistroDominio solicitud) {
		if (esAutosolicitud(actor, solicitud)) {
			return false;
		}
		var rolSolicitado = daoFactory.obtenerRolDAO().consultarPorId(solicitud.getRolSolicitado().getId());
		if (!actor.getRol().getId().equals(rolSolicitado.getIdRolAprobador())) {
			return false;
		}
		return switch (actor.getRol().getCodigo()) {
		case COORDINADOR_PROGRAMA -> coordinaPrograma(actor, solicitud);
		case COORDINADOR_FACULTAD -> coordinaFacultadDelPrograma(actor, solicitud);
		default -> true;
		};
	}

	private boolean coordinaPrograma(UsuarioDominio actor, SolicitudRegistroDominio solicitud) {
		var filtro = new CoordinacionProgramaEntidad();
		filtro.getCoordinador().setId(actor.getId());
		filtro.getProgramaAcademico().setId(solicitud.getProgramaAcademico().getId());
		return daoFactory.obtenerCoordinacionProgramaDAO().consultarPorFiltro(filtro).stream()
				.anyMatch(c -> UtilFecha.esValorDefecto(c.getFechaFin()));
	}

	private boolean coordinaFacultadDelPrograma(UsuarioDominio actor, SolicitudRegistroDominio solicitud) {
		var programa = daoFactory.obtenerProgramaAcademicoDAO().consultarPorId(solicitud.getProgramaAcademico().getId());
		var filtro = new CoordinacionFacultadEntidad();
		filtro.getCoordinador().setId(actor.getId());
		filtro.getFacultad().setId(programa.getFacultad().getId());
		return daoFactory.obtenerCoordinacionFacultadDAO().consultarPorFiltro(filtro).stream()
				.anyMatch(c -> UtilFecha.esValorDefecto(c.getFechaFin()));
	}
}
