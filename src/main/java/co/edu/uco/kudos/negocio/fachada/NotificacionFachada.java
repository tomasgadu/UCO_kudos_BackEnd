package co.edu.uco.kudos.negocio.fachada;

import java.util.List;
import java.util.UUID;

import co.edu.uco.kudos.dto.NotificacionDTO;
import co.edu.uco.kudos.dto.RegistroAuditoriaDTO;

public interface NotificacionFachada {

	List<NotificacionDTO> consultarMisNotificaciones(UUID idActor);

	NotificacionDTO marcarLeida(UUID idActor, UUID idNotificacion);

	List<RegistroAuditoriaDTO> consultarAuditoria(UUID idActor, String accion, String entidadAfectada,
			UUID idEntidadAfectada);
}
