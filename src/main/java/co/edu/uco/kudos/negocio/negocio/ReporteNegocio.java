package co.edu.uco.kudos.negocio.negocio;

import java.util.List;
import java.util.UUID;

import co.edu.uco.kudos.dominio.ReporteDominio;

public interface ReporteNegocio {

	ReporteDominio reportar(UUID idActor, UUID idPublicacion, String motivo, String descripcion);

	List<ReporteDominio> consultarReportes(UUID idActor, String estado);

	ReporteDominio iniciarRevision(UUID idActor, UUID idReporte);

	ReporteDominio decidir(UUID idActor, UUID idReporte, boolean aceptar, String observacion);
}
