package co.edu.uco.kudos.negocio.negocio.eventos;

import co.edu.uco.kudos.dao.factoria.DAOFactory;
import co.edu.uco.kudos.entidad.RegistroAuditoriaEntidad;
import co.edu.uco.kudos.transversal.utilitarios.UtilFecha;
import co.edu.uco.kudos.transversal.utilitarios.UtilUUID;

/**
 * AUD-P-0001: toda operación relevante queda registrada (RN-096 a RN-100, POL-006).
 */
final class AuditoriaSuscriptor implements SuscriptorEvento {

	@Override
	public void atender(EventoDominio evento, DAOFactory daoFactory) {
		var registro = new RegistroAuditoriaEntidad();
		registro.setId(UtilUUID.generar());
		registro.setFecha(UtilFecha.obtenerFechaHoraActual());
		registro.getActor().setId(evento.idActor());
		registro.setAccion(evento.nombre());
		registro.setEntidadAfectada(evento.entidadAfectada());
		registro.setIdEntidadAfectada(evento.idEntidadAfectada());
		registro.setValorAnterior(evento.valorAnterior());
		registro.setValorNuevo(evento.valorNuevo());
		daoFactory.obtenerRegistroAuditoriaDAO().crear(registro);
	}
}
