package co.edu.uco.kudos.negocio.negocio;

import java.util.List;
import java.util.UUID;

import co.edu.uco.kudos.dominio.SolicitudRegistroDominio;

public interface SolicitudRegistroNegocio {

	List<SolicitudRegistroDominio> consultarSolicitudesPendientes(UUID idActor);

	SolicitudRegistroDominio aprobarSolicitud(UUID idActor, UUID idSolicitud, String observacion);

	SolicitudRegistroDominio rechazarSolicitud(UUID idActor, UUID idSolicitud, String observacion);
}
