package co.edu.uco.kudos.negocio.fachada.impl;

import java.util.List;
import java.util.UUID;

import co.edu.uco.kudos.dto.ReporteDTO;
import co.edu.uco.kudos.negocio.fachada.FachadaBase;
import co.edu.uco.kudos.negocio.fachada.ReporteFachada;
import co.edu.uco.kudos.negocio.fachada.ensamblador.impl.ReporteEnsambladorDTO;
import co.edu.uco.kudos.negocio.negocio.impl.ReporteNegocioImpl;

public final class ReporteFachadaImpl extends FachadaBase implements ReporteFachada {

	@Override
	public List<ReporteDTO> consultarReportes(UUID idActor, String estado) {
		return ejecutarConsulta("consultar los reportes", daoFactory -> ReporteEnsambladorDTO.getInstancia()
				.convertirADTO(new ReporteNegocioImpl(daoFactory).consultarReportes(idActor, estado)));
	}

	@Override
	public ReporteDTO iniciarRevision(UUID idActor, UUID idReporte) {
		return ejecutarEnTransaccion("iniciar la revisión", daoFactory -> ReporteEnsambladorDTO.getInstancia()
				.convertirADTO(new ReporteNegocioImpl(daoFactory).iniciarRevision(idActor, idReporte)));
	}

	@Override
	public ReporteDTO decidir(UUID idActor, UUID idReporte, boolean aceptar, String observacion) {
		return ejecutarEnTransaccion("decidir el reporte", daoFactory -> ReporteEnsambladorDTO.getInstancia()
				.convertirADTO(new ReporteNegocioImpl(daoFactory).decidir(idActor, idReporte, aceptar, observacion)));
	}
}
