package co.edu.uco.kudos.negocio.negocio.eventos;

import co.edu.uco.kudos.dao.factoria.DAOFactory;
import co.edu.uco.kudos.entidad.NotificacionEntidad;
import co.edu.uco.kudos.transversal.utilitarios.UtilFecha;
import co.edu.uco.kudos.transversal.utilitarios.UtilUUID;

final class NotificacionSuscriptor implements SuscriptorEvento {

	@Override
	public void atender(EventoDominio evento, DAOFactory daoFactory) {
		if (!evento.tieneDestinatario()) {
			return;
		}
		var notificacion = new NotificacionEntidad();
		notificacion.setId(UtilUUID.generar());
		notificacion.getDestinatario().setId(evento.idDestinatario());
		notificacion.setTipo(evento.tipoNotificacion().name());
		notificacion.setMensaje(evento.mensajeNotificacion());
		notificacion.setFechaCreacion(UtilFecha.obtenerFechaHoraActual());
		notificacion.setLeida(false);
		daoFactory.obtenerNotificacionDAO().crear(notificacion);
	}
}
