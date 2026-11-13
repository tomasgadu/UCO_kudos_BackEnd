package co.edu.uco.kudos.negocio.fachada.ensamblador.impl;

import java.util.ArrayList;
import java.util.List;

import co.edu.uco.kudos.dominio.ParametroSistemaDominio;
import co.edu.uco.kudos.dto.ParametroSistemaDTO;
import co.edu.uco.kudos.negocio.fachada.ensamblador.EnsambladorDTO;
import co.edu.uco.kudos.transversal.utilitarios.UtilObjeto;

public final class ParametroSistemaEnsambladorDTO implements EnsambladorDTO<ParametroSistemaDominio, ParametroSistemaDTO> {

	private static final EnsambladorDTO<ParametroSistemaDominio, ParametroSistemaDTO> INSTANCIA = new ParametroSistemaEnsambladorDTO();

	private ParametroSistemaEnsambladorDTO() {
	}

	public static EnsambladorDTO<ParametroSistemaDominio, ParametroSistemaDTO> getInstancia() {
		return INSTANCIA;
	}

	@Override
	public ParametroSistemaDTO convertirADTO(ParametroSistemaDominio dominio) {
		var dominioTmp = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(dominio, new ParametroSistemaDominio.Builder().build());
		var destino = new ParametroSistemaDTO();
		destino.setId(dominioTmp.getId());
		destino.setClave(dominioTmp.getClave());
		destino.setValor(dominioTmp.getValor());
		destino.setDescripcion(dominioTmp.getDescripcion());
		return destino;
	}

	@Override
	public ParametroSistemaDominio convertirADominio(ParametroSistemaDTO origen) {
		var origenTmp = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(origen, new ParametroSistemaDTO());
		return new ParametroSistemaDominio.Builder().id(origenTmp.getId()).clave(origenTmp.getClave()).valor(origenTmp.getValor()).descripcion(origenTmp.getDescripcion()).build();
	}

	@Override
	public List<ParametroSistemaDTO> convertirADTO(List<ParametroSistemaDominio> lista) {
		var listaTmp = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(lista, new ArrayList<ParametroSistemaDominio>());
		return listaTmp.stream().map(this::convertirADTO).toList();
	}
}
