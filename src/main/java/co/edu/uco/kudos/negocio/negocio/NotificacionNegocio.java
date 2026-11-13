package co.edu.uco.kudos.negocio.negocio;

import java.util.List;
import java.util.UUID;

import co.edu.uco.kudos.dominio.NotificacionDominio;

public interface NotificacionNegocio {

	List<NotificacionDominio> consultarMisNotificaciones(UUID idActor);

	NotificacionDominio marcarLeida(UUID idActor, UUID idNotificacion);
}
