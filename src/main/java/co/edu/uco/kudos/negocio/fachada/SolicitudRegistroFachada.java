package co.edu.uco.kudos.negocio.fachada;

import java.util.List;
import java.util.UUID;

import co.edu.uco.kudos.dto.SolicitudRegistroDTO;

public interface SolicitudRegistroFachada {

	List<SolicitudRegistroDTO> consultarSolicitudesPendientes(UUID idActor);

	SolicitudRegistroDTO aprobarSolicitud(UUID idActor, UUID idSolicitud, String observacion);

	SolicitudRegistroDTO rechazarSolicitud(UUID idActor, UUID idSolicitud, String observacion);
}
