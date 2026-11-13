package co.edu.uco.kudos.negocio.fachada.ensamblador.impl;

import java.util.ArrayList;
import java.util.List;

import co.edu.uco.kudos.dominio.VetoDominio;
import co.edu.uco.kudos.dto.VetoDTO;
import co.edu.uco.kudos.negocio.fachada.ensamblador.EnsambladorDTO;
import co.edu.uco.kudos.transversal.utilitarios.UtilObjeto;

public final class VetoEnsambladorDTO implements EnsambladorDTO<VetoDominio, VetoDTO> {

	private static final EnsambladorDTO<VetoDominio, VetoDTO> INSTANCIA = new VetoEnsambladorDTO();

	private VetoEnsambladorDTO() {
	}

	public static EnsambladorDTO<VetoDominio, VetoDTO> getInstancia() {
		return INSTANCIA;
	}

	@Override
	public VetoDTO convertirADTO(VetoDominio dominio) {
		var dominioTmp = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(dominio, new VetoDominio.Builder().build());
		var destino = new VetoDTO();
		destino.setId(dominioTmp.getId());
		destino.setIdPublicacion(dominioTmp.getIdPublicacion());
		destino.setAutoridad(UsuarioEnsambladorDTO.getInstancia().convertirADTO(dominioTmp.getAutoridad()));
		destino.setIdReporteOrigen(dominioTmp.getIdReporteOrigen());
		destino.setJustificacion(dominioTmp.getJustificacion());
		destino.setFechaVeto(dominioTmp.getFechaVeto());
		return destino;
	}

	@Override
	public VetoDominio convertirADominio(VetoDTO origen) {
		var origenTmp = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(origen, new VetoDTO());
		return new VetoDominio.Builder().id(origenTmp.getId()).idPublicacion(origenTmp.getIdPublicacion()).autoridad(UsuarioEnsambladorDTO.getInstancia().convertirADominio(origenTmp.getAutoridad())).idReporteOrigen(origenTmp.getIdReporteOrigen()).justificacion(origenTmp.getJustificacion()).fechaVeto(origenTmp.getFechaVeto()).build();
	}

	@Override
	public List<VetoDTO> convertirADTO(List<VetoDominio> lista) {
		var listaTmp = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(lista, new ArrayList<VetoDominio>());
		return listaTmp.stream().map(this::convertirADTO).toList();
	}
}
