package co.edu.uco.kudos.negocio.negocio.ensamblador.impl;

import java.util.ArrayList;
import java.util.List;

import co.edu.uco.kudos.dominio.CalificacionDominio;
import co.edu.uco.kudos.entidad.CalificacionEntidad;
import co.edu.uco.kudos.negocio.negocio.ensamblador.EnsambladorEntidad;
import co.edu.uco.kudos.transversal.utilitarios.UtilObjeto;

public final class CalificacionEnsambladorEntidad implements EnsambladorEntidad<CalificacionDominio, CalificacionEntidad> {

	private static final EnsambladorEntidad<CalificacionDominio, CalificacionEntidad> INSTANCIA = new CalificacionEnsambladorEntidad();

	private CalificacionEnsambladorEntidad() {
	}

	public static EnsambladorEntidad<CalificacionDominio, CalificacionEntidad> getInstancia() {
		return INSTANCIA;
	}

	@Override
	public CalificacionEntidad convertirAEntidad(CalificacionDominio dominio) {
		var dominioTmp = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(dominio, new CalificacionDominio.Builder().build());
		var destino = new CalificacionEntidad();
		destino.setId(dominioTmp.getId());
		destino.setIdPublicacion(dominioTmp.getIdPublicacion());
		destino.setAutor(UsuarioEnsambladorEntidad.getInstancia().convertirAEntidad(dominioTmp.getAutor()));
		destino.setEstrellas(dominioTmp.getEstrellas());
		destino.setFechaRegistro(dominioTmp.getFechaRegistro());
		destino.setFechaModificacion(dominioTmp.getFechaModificacion());
		return destino;
	}

	@Override
	public CalificacionDominio convertirADominio(CalificacionEntidad origen) {
		var origenTmp = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(origen, new CalificacionEntidad());
		return new CalificacionDominio.Builder().id(origenTmp.getId()).idPublicacion(origenTmp.getIdPublicacion()).autor(UsuarioEnsambladorEntidad.getInstancia().convertirADominio(origenTmp.getAutor())).estrellas(origenTmp.getEstrellas()).fechaRegistro(origenTmp.getFechaRegistro()).fechaModificacion(origenTmp.getFechaModificacion()).build();
	}

	@Override
	public List<CalificacionDominio> convertirADominio(List<CalificacionEntidad> lista) {
		var listaTmp = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(lista, new ArrayList<CalificacionEntidad>());
		return listaTmp.stream().map(this::convertirADominio).toList();
	}
}
