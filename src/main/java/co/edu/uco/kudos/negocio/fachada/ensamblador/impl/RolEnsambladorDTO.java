package co.edu.uco.kudos.negocio.fachada.ensamblador.impl;

import java.util.ArrayList;
import java.util.List;

import co.edu.uco.kudos.dominio.RolDominio;
import co.edu.uco.kudos.dominio.enums.CodigoRol;
import co.edu.uco.kudos.dto.RolDTO;
import co.edu.uco.kudos.negocio.fachada.ensamblador.EnsambladorDTO;
import co.edu.uco.kudos.transversal.utilitarios.UtilObjeto;

public final class RolEnsambladorDTO implements EnsambladorDTO<RolDominio, RolDTO> {

	private static final EnsambladorDTO<RolDominio, RolDTO> INSTANCIA = new RolEnsambladorDTO();

	private RolEnsambladorDTO() {
	}

	public static EnsambladorDTO<RolDominio, RolDTO> getInstancia() {
		return INSTANCIA;
	}

	@Override
	public RolDTO convertirADTO(RolDominio dominio) {
		var dominioTmp = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(dominio, new RolDominio.Builder().build());
		var destino = new RolDTO();
		destino.setId(dominioTmp.getId());
		destino.setCodigo(dominioTmp.getCodigo().name());
		destino.setNombre(dominioTmp.getNombre());
		destino.setDescripcion(dominioTmp.getDescripcion());
		destino.setIdRolAprobador(dominioTmp.getIdRolAprobador());
		return destino;
	}

	@Override
	public RolDominio convertirADominio(RolDTO origen) {
		var origenTmp = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(origen, new RolDTO());
		return new RolDominio.Builder().id(origenTmp.getId()).codigo(CodigoRol.desdeTexto(origenTmp.getCodigo())).nombre(origenTmp.getNombre()).descripcion(origenTmp.getDescripcion()).idRolAprobador(origenTmp.getIdRolAprobador()).build();
	}

	@Override
	public List<RolDTO> convertirADTO(List<RolDominio> lista) {
		var listaTmp = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(lista, new ArrayList<RolDominio>());
		return listaTmp.stream().map(this::convertirADTO).toList();
	}
}
