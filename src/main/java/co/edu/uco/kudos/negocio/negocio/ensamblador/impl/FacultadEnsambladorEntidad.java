package co.edu.uco.kudos.negocio.negocio.ensamblador.impl;

import java.util.ArrayList;
import java.util.List;

import co.edu.uco.kudos.dominio.FacultadDominio;
import co.edu.uco.kudos.dominio.enums.EstadoRegistro;
import co.edu.uco.kudos.entidad.FacultadEntidad;
import co.edu.uco.kudos.negocio.negocio.ensamblador.EnsambladorEntidad;
import co.edu.uco.kudos.transversal.utilitarios.UtilObjeto;
import co.edu.uco.kudos.transversal.utilitarios.UtilTexto;

public final class FacultadEnsambladorEntidad implements EnsambladorEntidad<FacultadDominio, FacultadEntidad> {

	private static final EnsambladorEntidad<FacultadDominio, FacultadEntidad> INSTANCIA = new FacultadEnsambladorEntidad();

	private FacultadEnsambladorEntidad() {
	}

	public static EnsambladorEntidad<FacultadDominio, FacultadEntidad> getInstancia() {
		return INSTANCIA;
	}

	@Override
	public FacultadEntidad convertirAEntidad(FacultadDominio dominio) {
		var dominioTmp = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(dominio, new FacultadDominio.Builder().build());
		var destino = new FacultadEntidad();
		destino.setId(dominioTmp.getId());
		destino.setNombre(dominioTmp.getNombre());
		destino.setEstado(UtilTexto.getUtilTexto().textoDeEnum(dominioTmp.getEstado()));
		destino.setFechaCreacion(dominioTmp.getFechaCreacion());
		return destino;
	}

	@Override
	public FacultadDominio convertirADominio(FacultadEntidad origen) {
		var origenTmp = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(origen, new FacultadEntidad());
		return new FacultadDominio.Builder().id(origenTmp.getId()).nombre(origenTmp.getNombre()).estado(EstadoRegistro.desdeTexto(origenTmp.getEstado())).fechaCreacion(origenTmp.getFechaCreacion()).build();
	}

	@Override
	public List<FacultadDominio> convertirADominio(List<FacultadEntidad> lista) {
		var listaTmp = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(lista, new ArrayList<FacultadEntidad>());
		return listaTmp.stream().map(this::convertirADominio).toList();
	}
}
