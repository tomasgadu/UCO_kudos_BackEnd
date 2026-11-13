package co.edu.uco.kudos.negocio.negocio.ensamblador.impl;

import java.util.ArrayList;
import java.util.List;

import co.edu.uco.kudos.dominio.CoordinacionProgramaDominio;
import co.edu.uco.kudos.entidad.CoordinacionProgramaEntidad;
import co.edu.uco.kudos.negocio.negocio.ensamblador.EnsambladorEntidad;
import co.edu.uco.kudos.transversal.utilitarios.UtilObjeto;

public final class CoordinacionProgramaEnsambladorEntidad implements EnsambladorEntidad<CoordinacionProgramaDominio, CoordinacionProgramaEntidad> {

	private static final EnsambladorEntidad<CoordinacionProgramaDominio, CoordinacionProgramaEntidad> INSTANCIA = new CoordinacionProgramaEnsambladorEntidad();

	private CoordinacionProgramaEnsambladorEntidad() {
	}

	public static EnsambladorEntidad<CoordinacionProgramaDominio, CoordinacionProgramaEntidad> getInstancia() {
		return INSTANCIA;
	}

	@Override
	public CoordinacionProgramaEntidad convertirAEntidad(CoordinacionProgramaDominio dominio) {
		var dominioTmp = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(dominio, new CoordinacionProgramaDominio.Builder().build());
		var destino = new CoordinacionProgramaEntidad();
		destino.setId(dominioTmp.getId());
		destino.setCoordinador(UsuarioEnsambladorEntidad.getInstancia().convertirAEntidad(dominioTmp.getCoordinador()));
		destino.setProgramaAcademico(ProgramaAcademicoEnsambladorEntidad.getInstancia().convertirAEntidad(dominioTmp.getProgramaAcademico()));
		destino.setFechaInicio(dominioTmp.getFechaInicio());
		destino.setFechaFin(dominioTmp.getFechaFin());
		return destino;
	}

	@Override
	public CoordinacionProgramaDominio convertirADominio(CoordinacionProgramaEntidad origen) {
		var origenTmp = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(origen, new CoordinacionProgramaEntidad());
		return new CoordinacionProgramaDominio.Builder().id(origenTmp.getId()).coordinador(UsuarioEnsambladorEntidad.getInstancia().convertirADominio(origenTmp.getCoordinador())).programaAcademico(ProgramaAcademicoEnsambladorEntidad.getInstancia().convertirADominio(origenTmp.getProgramaAcademico())).fechaInicio(origenTmp.getFechaInicio()).fechaFin(origenTmp.getFechaFin()).build();
	}

	@Override
	public List<CoordinacionProgramaDominio> convertirADominio(List<CoordinacionProgramaEntidad> lista) {
		var listaTmp = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(lista, new ArrayList<CoordinacionProgramaEntidad>());
		return listaTmp.stream().map(this::convertirADominio).toList();
	}
}
