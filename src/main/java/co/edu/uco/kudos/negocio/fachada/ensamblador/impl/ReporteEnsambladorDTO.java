package co.edu.uco.kudos.negocio.fachada.ensamblador.impl;

import java.util.ArrayList;
import java.util.List;

import co.edu.uco.kudos.dominio.ReporteDominio;
import co.edu.uco.kudos.dominio.enums.MotivoReporte;
import co.edu.uco.kudos.dominio.enums.EstadoReporte;
import co.edu.uco.kudos.dto.ReporteDTO;
import co.edu.uco.kudos.negocio.fachada.ensamblador.EnsambladorDTO;
import co.edu.uco.kudos.transversal.utilitarios.UtilObjeto;

public final class ReporteEnsambladorDTO implements EnsambladorDTO<ReporteDominio, ReporteDTO> {

	private static final EnsambladorDTO<ReporteDominio, ReporteDTO> INSTANCIA = new ReporteEnsambladorDTO();

	private ReporteEnsambladorDTO() {
	}

	public static EnsambladorDTO<ReporteDominio, ReporteDTO> getInstancia() {
		return INSTANCIA;
	}

	@Override
	public ReporteDTO convertirADTO(ReporteDominio dominio) {
		var dominioTmp = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(dominio, new ReporteDominio.Builder().build());
		var destino = new ReporteDTO();
		destino.setId(dominioTmp.getId());
		destino.setIdPublicacion(dominioTmp.getIdPublicacion());
		destino.setReportante(UsuarioEnsambladorDTO.getInstancia().convertirADTO(dominioTmp.getReportante()));
		destino.setMotivo(dominioTmp.getMotivo().name());
		destino.setDescripcion(dominioTmp.getDescripcion());
		destino.setFechaReporte(dominioTmp.getFechaReporte());
		destino.setEstado(dominioTmp.getEstado().name());
		destino.setRevisor(UsuarioEnsambladorDTO.getInstancia().convertirADTO(dominioTmp.getRevisor()));
		destino.setFechaDecision(dominioTmp.getFechaDecision());
		destino.setObservacionDecision(dominioTmp.getObservacionDecision());
		return destino;
	}

	@Override
	public ReporteDominio convertirADominio(ReporteDTO origen) {
		var origenTmp = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(origen, new ReporteDTO());
		return new ReporteDominio.Builder().id(origenTmp.getId()).idPublicacion(origenTmp.getIdPublicacion()).reportante(UsuarioEnsambladorDTO.getInstancia().convertirADominio(origenTmp.getReportante())).motivo(MotivoReporte.desdeTexto(origenTmp.getMotivo())).descripcion(origenTmp.getDescripcion()).fechaReporte(origenTmp.getFechaReporte()).estado(EstadoReporte.desdeTexto(origenTmp.getEstado())).revisor(UsuarioEnsambladorDTO.getInstancia().convertirADominio(origenTmp.getRevisor())).fechaDecision(origenTmp.getFechaDecision()).observacionDecision(origenTmp.getObservacionDecision()).build();
	}

	@Override
	public List<ReporteDTO> convertirADTO(List<ReporteDominio> lista) {
		var listaTmp = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(lista, new ArrayList<ReporteDominio>());
		return listaTmp.stream().map(this::convertirADTO).toList();
	}
}
