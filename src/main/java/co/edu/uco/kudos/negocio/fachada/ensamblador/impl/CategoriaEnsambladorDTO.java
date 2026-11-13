package co.edu.uco.kudos.negocio.fachada.ensamblador.impl;

import java.util.ArrayList;
import java.util.List;

import co.edu.uco.kudos.dominio.CategoriaDominio;
import co.edu.uco.kudos.dominio.enums.EstadoRegistro;
import co.edu.uco.kudos.dto.CategoriaDTO;
import co.edu.uco.kudos.negocio.fachada.ensamblador.EnsambladorDTO;
import co.edu.uco.kudos.transversal.utilitarios.UtilObjeto;

public final class CategoriaEnsambladorDTO implements EnsambladorDTO<CategoriaDominio, CategoriaDTO> {

	private static final EnsambladorDTO<CategoriaDominio, CategoriaDTO> INSTANCIA = new CategoriaEnsambladorDTO();

	private CategoriaEnsambladorDTO() {
	}

	public static EnsambladorDTO<CategoriaDominio, CategoriaDTO> getInstancia() {
		return INSTANCIA;
	}

	@Override
	public CategoriaDTO convertirADTO(CategoriaDominio dominio) {
		var dominioTmp = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(dominio, new CategoriaDominio.Builder().build());
		var destino = new CategoriaDTO();
		destino.setId(dominioTmp.getId());
		destino.setNombre(dominioTmp.getNombre());
		destino.setDescripcion(dominioTmp.getDescripcion());
		destino.setEstado(dominioTmp.getEstado().name());
		destino.setFechaCreacion(dominioTmp.getFechaCreacion());
		return destino;
	}

	@Override
	public CategoriaDominio convertirADominio(CategoriaDTO origen) {
		var origenTmp = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(origen, new CategoriaDTO());
		return new CategoriaDominio.Builder().id(origenTmp.getId()).nombre(origenTmp.getNombre()).descripcion(origenTmp.getDescripcion()).estado(EstadoRegistro.desdeTexto(origenTmp.getEstado())).fechaCreacion(origenTmp.getFechaCreacion()).build();
	}

	@Override
	public List<CategoriaDTO> convertirADTO(List<CategoriaDominio> lista) {
		var listaTmp = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(lista, new ArrayList<CategoriaDominio>());
		return listaTmp.stream().map(this::convertirADTO).toList();
	}
}
