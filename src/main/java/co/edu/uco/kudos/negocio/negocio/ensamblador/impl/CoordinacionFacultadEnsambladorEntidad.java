package co.edu.uco.kudos.negocio.negocio.ensamblador.impl;

import java.util.ArrayList;
import java.util.List;

import co.edu.uco.kudos.dominio.CoordinacionFacultadDominio;
import co.edu.uco.kudos.entidad.CoordinacionFacultadEntidad;
import co.edu.uco.kudos.negocio.negocio.ensamblador.EnsambladorEntidad;
import co.edu.uco.kudos.transversal.utilitarios.UtilObjeto;

public final class CoordinacionFacultadEnsambladorEntidad implements EnsambladorEntidad<CoordinacionFacultadDominio, CoordinacionFacultadEntidad> {

	private static final EnsambladorEntidad<CoordinacionFacultadDominio, CoordinacionFacultadEntidad> INSTANCIA = new CoordinacionFacultadEnsambladorEntidad();

	private CoordinacionFacultadEnsambladorEntidad() {
	}

	public static EnsambladorEntidad<CoordinacionFacultadDominio, CoordinacionFacultadEntidad> getInstancia() {
		return INSTANCIA;
	}

	@Override
	public CoordinacionFacultadEntidad convertirAEntidad(CoordinacionFacultadDominio dominio) {
		var dominioTmp = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(dominio, new CoordinacionFacultadDominio.Builder().build());
		var destino = new CoordinacionFacultadEntidad();
		destino.setId(dominioTmp.getId());
		destino.setCoordinador(UsuarioEnsambladorEntidad.getInstancia().convertirAEntidad(dominioTmp.getCoordinador()));
		destino.setFacultad(FacultadEnsambladorEntidad.getInstancia().convertirAEntidad(dominioTmp.getFacultad()));
		destino.setFechaInicio(dominioTmp.getFechaInicio());
		destino.setFechaFin(dominioTmp.getFechaFin());
		return destino;
	}

	@Override
	public CoordinacionFacultadDominio convertirADominio(CoordinacionFacultadEntidad origen) {
		var origenTmp = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(origen, new CoordinacionFacultadEntidad());
		return new CoordinacionFacultadDominio.Builder().id(origenTmp.getId()).coordinador(UsuarioEnsambladorEntidad.getInstancia().convertirADominio(origenTmp.getCoordinador())).facultad(FacultadEnsambladorEntidad.getInstancia().convertirADominio(origenTmp.getFacultad())).fechaInicio(origenTmp.getFechaInicio()).fechaFin(origenTmp.getFechaFin()).build();
	}

	@Override
	public List<CoordinacionFacultadDominio> convertirADominio(List<CoordinacionFacultadEntidad> lista) {
		var listaTmp = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(lista, new ArrayList<CoordinacionFacultadEntidad>());
		return listaTmp.stream().map(this::convertirADominio).toList();
	}
}
