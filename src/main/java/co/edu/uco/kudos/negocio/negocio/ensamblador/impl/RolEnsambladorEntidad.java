package co.edu.uco.kudos.negocio.negocio.ensamblador.impl;

import java.util.ArrayList;
import java.util.List;

import co.edu.uco.kudos.dominio.RolDominio;
import co.edu.uco.kudos.dominio.enums.CodigoRol;
import co.edu.uco.kudos.entidad.RolEntidad;
import co.edu.uco.kudos.negocio.negocio.ensamblador.EnsambladorEntidad;
import co.edu.uco.kudos.transversal.utilitarios.UtilObjeto;

public final class RolEnsambladorEntidad implements EnsambladorEntidad<RolDominio, RolEntidad> {

	private static final EnsambladorEntidad<RolDominio, RolEntidad> INSTANCIA = new RolEnsambladorEntidad();

	private RolEnsambladorEntidad() {
	}

	public static EnsambladorEntidad<RolDominio, RolEntidad> getInstancia() {
		return INSTANCIA;
	}

	@Override
	public RolEntidad convertirAEntidad(RolDominio dominio) {
		var dominioTmp = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(dominio, new RolDominio.Builder().build());
		var destino = new RolEntidad();
		destino.setId(dominioTmp.getId());
		destino.setCodigo(dominioTmp.getCodigo().name());
		destino.setNombre(dominioTmp.getNombre());
		destino.setDescripcion(dominioTmp.getDescripcion());
		destino.setIdRolAprobador(dominioTmp.getIdRolAprobador());
		return destino;
	}

	@Override
	public RolDominio convertirADominio(RolEntidad origen) {
		var origenTmp = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(origen, new RolEntidad());
		return new RolDominio.Builder().id(origenTmp.getId()).codigo(CodigoRol.desdeTexto(origenTmp.getCodigo())).nombre(origenTmp.getNombre()).descripcion(origenTmp.getDescripcion()).idRolAprobador(origenTmp.getIdRolAprobador()).build();
	}

	@Override
	public List<RolDominio> convertirADominio(List<RolEntidad> lista) {
		var listaTmp = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(lista, new ArrayList<RolEntidad>());
		return listaTmp.stream().map(this::convertirADominio).toList();
	}
}
