package co.edu.uco.kudos.negocio.negocio.ensamblador.impl;

import java.util.ArrayList;
import java.util.List;

import co.edu.uco.kudos.dominio.VetoDominio;
import co.edu.uco.kudos.entidad.VetoEntidad;
import co.edu.uco.kudos.negocio.negocio.ensamblador.EnsambladorEntidad;
import co.edu.uco.kudos.transversal.utilitarios.UtilObjeto;

public final class VetoEnsambladorEntidad implements EnsambladorEntidad<VetoDominio, VetoEntidad> {

	private static final EnsambladorEntidad<VetoDominio, VetoEntidad> INSTANCIA = new VetoEnsambladorEntidad();

	private VetoEnsambladorEntidad() {
	}

	public static EnsambladorEntidad<VetoDominio, VetoEntidad> getInstancia() {
		return INSTANCIA;
	}

	@Override
	public VetoEntidad convertirAEntidad(VetoDominio dominio) {
		var dominioTmp = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(dominio, new VetoDominio.Builder().build());
		var destino = new VetoEntidad();
		destino.setId(dominioTmp.getId());
		destino.setIdPublicacion(dominioTmp.getIdPublicacion());
		destino.setAutoridad(UsuarioEnsambladorEntidad.getInstancia().convertirAEntidad(dominioTmp.getAutoridad()));
		destino.setIdReporteOrigen(dominioTmp.getIdReporteOrigen());
		destino.setJustificacion(dominioTmp.getJustificacion());
		destino.setFechaVeto(dominioTmp.getFechaVeto());
		return destino;
	}

	@Override
	public VetoDominio convertirADominio(VetoEntidad origen) {
		var origenTmp = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(origen, new VetoEntidad());
		return new VetoDominio.Builder().id(origenTmp.getId()).idPublicacion(origenTmp.getIdPublicacion()).autoridad(UsuarioEnsambladorEntidad.getInstancia().convertirADominio(origenTmp.getAutoridad())).idReporteOrigen(origenTmp.getIdReporteOrigen()).justificacion(origenTmp.getJustificacion()).fechaVeto(origenTmp.getFechaVeto()).build();
	}

	@Override
	public List<VetoDominio> convertirADominio(List<VetoEntidad> lista) {
		var listaTmp = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(lista, new ArrayList<VetoEntidad>());
		return listaTmp.stream().map(this::convertirADominio).toList();
	}
}
