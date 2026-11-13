package co.edu.uco.kudos.negocio.negocio.ensamblador.impl;

import java.util.ArrayList;
import java.util.List;

import co.edu.uco.kudos.dominio.ReporteDominio;
import co.edu.uco.kudos.dominio.enums.MotivoReporte;
import co.edu.uco.kudos.dominio.enums.EstadoReporte;
import co.edu.uco.kudos.entidad.ReporteEntidad;
import co.edu.uco.kudos.negocio.negocio.ensamblador.EnsambladorEntidad;
import co.edu.uco.kudos.transversal.utilitarios.UtilObjeto;

public final class ReporteEnsambladorEntidad implements EnsambladorEntidad<ReporteDominio, ReporteEntidad> {

	private static final EnsambladorEntidad<ReporteDominio, ReporteEntidad> INSTANCIA = new ReporteEnsambladorEntidad();

	private ReporteEnsambladorEntidad() {
	}

	public static EnsambladorEntidad<ReporteDominio, ReporteEntidad> getInstancia() {
		return INSTANCIA;
	}

	@Override
	public ReporteEntidad convertirAEntidad(ReporteDominio dominio) {
		var dominioTmp = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(dominio, new ReporteDominio.Builder().build());
		var destino = new ReporteEntidad();
		destino.setId(dominioTmp.getId());
		destino.setIdPublicacion(dominioTmp.getIdPublicacion());
		destino.setReportante(UsuarioEnsambladorEntidad.getInstancia().convertirAEntidad(dominioTmp.getReportante()));
		destino.setMotivo(dominioTmp.getMotivo().name());
		destino.setDescripcion(dominioTmp.getDescripcion());
		destino.setFechaReporte(dominioTmp.getFechaReporte());
		destino.setEstado(dominioTmp.getEstado().name());
		destino.setRevisor(UsuarioEnsambladorEntidad.getInstancia().convertirAEntidad(dominioTmp.getRevisor()));
		destino.setFechaDecision(dominioTmp.getFechaDecision());
		destino.setObservacionDecision(dominioTmp.getObservacionDecision());
		return destino;
	}

	@Override
	public ReporteDominio convertirADominio(ReporteEntidad origen) {
		var origenTmp = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(origen, new ReporteEntidad());
		return new ReporteDominio.Builder().id(origenTmp.getId()).idPublicacion(origenTmp.getIdPublicacion()).reportante(UsuarioEnsambladorEntidad.getInstancia().convertirADominio(origenTmp.getReportante())).motivo(MotivoReporte.desdeTexto(origenTmp.getMotivo())).descripcion(origenTmp.getDescripcion()).fechaReporte(origenTmp.getFechaReporte()).estado(EstadoReporte.desdeTexto(origenTmp.getEstado())).revisor(UsuarioEnsambladorEntidad.getInstancia().convertirADominio(origenTmp.getRevisor())).fechaDecision(origenTmp.getFechaDecision()).observacionDecision(origenTmp.getObservacionDecision()).build();
	}

	@Override
	public List<ReporteDominio> convertirADominio(List<ReporteEntidad> lista) {
		var listaTmp = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(lista, new ArrayList<ReporteEntidad>());
		return listaTmp.stream().map(this::convertirADominio).toList();
	}
}
