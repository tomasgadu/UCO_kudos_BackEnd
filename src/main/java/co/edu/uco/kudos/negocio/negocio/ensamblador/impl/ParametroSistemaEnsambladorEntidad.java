package co.edu.uco.kudos.negocio.negocio.ensamblador.impl;

import java.util.ArrayList;
import java.util.List;

import co.edu.uco.kudos.dominio.ParametroSistemaDominio;
import co.edu.uco.kudos.entidad.ParametroSistemaEntidad;
import co.edu.uco.kudos.negocio.negocio.ensamblador.EnsambladorEntidad;
import co.edu.uco.kudos.transversal.utilitarios.UtilObjeto;

public final class ParametroSistemaEnsambladorEntidad implements EnsambladorEntidad<ParametroSistemaDominio, ParametroSistemaEntidad> {

	private static final EnsambladorEntidad<ParametroSistemaDominio, ParametroSistemaEntidad> INSTANCIA = new ParametroSistemaEnsambladorEntidad();

	private ParametroSistemaEnsambladorEntidad() {
	}

	public static EnsambladorEntidad<ParametroSistemaDominio, ParametroSistemaEntidad> getInstancia() {
		return INSTANCIA;
	}

	@Override
	public ParametroSistemaEntidad convertirAEntidad(ParametroSistemaDominio dominio) {
		var dominioTmp = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(dominio, new ParametroSistemaDominio.Builder().build());
		var destino = new ParametroSistemaEntidad();
		destino.setId(dominioTmp.getId());
		destino.setClave(dominioTmp.getClave());
		destino.setValor(dominioTmp.getValor());
		destino.setDescripcion(dominioTmp.getDescripcion());
		return destino;
	}

	@Override
	public ParametroSistemaDominio convertirADominio(ParametroSistemaEntidad origen) {
		var origenTmp = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(origen, new ParametroSistemaEntidad());
		return new ParametroSistemaDominio.Builder().id(origenTmp.getId()).clave(origenTmp.getClave()).valor(origenTmp.getValor()).descripcion(origenTmp.getDescripcion()).build();
	}

	@Override
	public List<ParametroSistemaDominio> convertirADominio(List<ParametroSistemaEntidad> lista) {
		var listaTmp = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(lista, new ArrayList<ParametroSistemaEntidad>());
		return listaTmp.stream().map(this::convertirADominio).toList();
	}
}
