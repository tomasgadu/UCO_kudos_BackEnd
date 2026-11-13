package co.edu.uco.kudos.negocio.fachada.ensamblador.impl;

import java.util.ArrayList;
import java.util.List;

import co.edu.uco.kudos.dominio.FacultadDominio;
import co.edu.uco.kudos.dominio.enums.EstadoRegistro;
import co.edu.uco.kudos.dto.FacultadDTO;
import co.edu.uco.kudos.negocio.fachada.ensamblador.EnsambladorDTO;
import co.edu.uco.kudos.transversal.utilitarios.UtilObjeto;

public final class FacultadEnsambladorDTO implements EnsambladorDTO<FacultadDominio, FacultadDTO> {

	private static final EnsambladorDTO<FacultadDominio, FacultadDTO> INSTANCIA = new FacultadEnsambladorDTO();

	private FacultadEnsambladorDTO() {
	}

	public static EnsambladorDTO<FacultadDominio, FacultadDTO> getInstancia() {
		return INSTANCIA;
	}

	@Override
	public FacultadDTO convertirADTO(FacultadDominio dominio) {
		var dominioTmp = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(dominio, new FacultadDominio.Builder().build());
		var destino = new FacultadDTO();
		destino.setId(dominioTmp.getId());
		destino.setNombre(dominioTmp.getNombre());
		destino.setEstado(dominioTmp.getEstado().name());
		destino.setFechaCreacion(dominioTmp.getFechaCreacion());
		return destino;
	}

	@Override
	public FacultadDominio convertirADominio(FacultadDTO origen) {
		var origenTmp = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(origen, new FacultadDTO());
		return new FacultadDominio.Builder().id(origenTmp.getId()).nombre(origenTmp.getNombre()).estado(EstadoRegistro.desdeTexto(origenTmp.getEstado())).fechaCreacion(origenTmp.getFechaCreacion()).build();
	}

	@Override
	public List<FacultadDTO> convertirADTO(List<FacultadDominio> lista) {
		var listaTmp = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(lista, new ArrayList<FacultadDominio>());
		return listaTmp.stream().map(this::convertirADTO).toList();
	}
}
