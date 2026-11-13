package co.edu.uco.kudos.negocio.fachada.impl;

import java.util.List;
import java.util.UUID;

import co.edu.uco.kudos.dto.NotificacionDTO;
import co.edu.uco.kudos.dto.RegistroAuditoriaDTO;
import co.edu.uco.kudos.negocio.fachada.FachadaBase;
import co.edu.uco.kudos.negocio.fachada.NotificacionFachada;
import co.edu.uco.kudos.negocio.fachada.ensamblador.impl.NotificacionEnsambladorDTO;
import co.edu.uco.kudos.negocio.fachada.ensamblador.impl.RegistroAuditoriaEnsambladorDTO;
import co.edu.uco.kudos.negocio.negocio.impl.AuditoriaNegocioImpl;
import co.edu.uco.kudos.negocio.negocio.impl.NotificacionNegocioImpl;

public final class NotificacionFachadaImpl extends FachadaBase implements NotificacionFachada {

	@Override
	public List<NotificacionDTO> consultarMisNotificaciones(UUID idActor) {
		return ejecutarConsulta("consultar sus notificaciones", daoFactory -> NotificacionEnsambladorDTO.getInstancia()
				.convertirADTO(new NotificacionNegocioImpl(daoFactory).consultarMisNotificaciones(idActor)));
	}

	@Override
	public NotificacionDTO marcarLeida(UUID idActor, UUID idNotificacion) {
		return ejecutarEnTransaccion("marcar la notificación", daoFactory -> NotificacionEnsambladorDTO.getInstancia()
				.convertirADTO(new NotificacionNegocioImpl(daoFactory).marcarLeida(idActor, idNotificacion)));
	}

	@Override
	public List<RegistroAuditoriaDTO> consultarAuditoria(UUID idActor, String accion, String entidadAfectada,
			UUID idEntidadAfectada) {
		return ejecutarConsulta("consultar la auditoría", daoFactory -> RegistroAuditoriaEnsambladorDTO.getInstancia()
				.convertirADTO(new AuditoriaNegocioImpl(daoFactory).consultarAuditoria(idActor, accion, entidadAfectada,
						idEntidadAfectada)));
	}
}
