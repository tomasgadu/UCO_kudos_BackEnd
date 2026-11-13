package co.edu.uco.kudos.negocio.fachada;

import java.util.List;
import java.util.UUID;

import co.edu.uco.kudos.dto.ReporteDTO;

public interface ReporteFachada {

	List<ReporteDTO> consultarReportes(UUID idActor, String estado);

	ReporteDTO iniciarRevision(UUID idActor, UUID idReporte);

	ReporteDTO decidir(UUID idActor, UUID idReporte, boolean aceptar, String observacion);
}
