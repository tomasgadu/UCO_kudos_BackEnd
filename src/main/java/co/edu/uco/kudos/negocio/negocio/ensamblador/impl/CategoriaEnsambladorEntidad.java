package co.edu.uco.kudos.negocio.negocio.ensamblador.impl;

import java.util.ArrayList;
import java.util.List;

import co.edu.uco.kudos.dominio.CategoriaDominio;
import co.edu.uco.kudos.dominio.enums.EstadoRegistro;
import co.edu.uco.kudos.entidad.CategoriaEntidad;
import co.edu.uco.kudos.negocio.negocio.ensamblador.EnsambladorEntidad;
import co.edu.uco.kudos.transversal.utilitarios.UtilObjeto;
import co.edu.uco.kudos.transversal.utilitarios.UtilTexto;

public final class CategoriaEnsambladorEntidad implements EnsambladorEntidad<CategoriaDominio, CategoriaEntidad> {

	private static final EnsambladorEntidad<CategoriaDominio, CategoriaEntidad> INSTANCIA = new CategoriaEnsambladorEntidad();

	private CategoriaEnsambladorEntidad() {
	}

	public static EnsambladorEntidad<CategoriaDominio, CategoriaEntidad> getInstancia() {
		return INSTANCIA;
	}

	@Override
	public CategoriaEntidad convertirAEntidad(CategoriaDominio dominio) {
		var dominioTmp = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(dominio, new CategoriaDominio.Builder().build());
		var destino = new CategoriaEntidad();
		destino.setId(dominioTmp.getId());
		destino.setNombre(dominioTmp.getNombre());
		destino.setDescripcion(dominioTmp.getDescripcion());
		destino.setEstado(UtilTexto.getUtilTexto().textoDeEnum(dominioTmp.getEstado()));
		destino.setFechaCreacion(dominioTmp.getFechaCreacion());
		return destino;
	}

	@Override
	public CategoriaDominio convertirADominio(CategoriaEntidad origen) {
		var origenTmp = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(origen, new CategoriaEntidad());
		return new CategoriaDominio.Builder().id(origenTmp.getId()).nombre(origenTmp.getNombre()).descripcion(origenTmp.getDescripcion()).estado(EstadoRegistro.desdeTexto(origenTmp.getEstado())).fechaCreacion(origenTmp.getFechaCreacion()).build();
	}

	@Override
	public List<CategoriaDominio> convertirADominio(List<CategoriaEntidad> lista) {
		var listaTmp = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(lista, new ArrayList<CategoriaEntidad>());
		return listaTmp.stream().map(this::convertirADominio).toList();
	}
}
