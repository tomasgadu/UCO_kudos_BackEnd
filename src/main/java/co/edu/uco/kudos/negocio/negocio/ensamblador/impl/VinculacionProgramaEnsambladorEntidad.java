package co.edu.uco.kudos.negocio.negocio.ensamblador.impl;

import java.util.ArrayList;
import java.util.List;

import co.edu.uco.kudos.dominio.VinculacionProgramaDominio;
import co.edu.uco.kudos.entidad.VinculacionProgramaEntidad;
import co.edu.uco.kudos.negocio.negocio.ensamblador.EnsambladorEntidad;
import co.edu.uco.kudos.transversal.utilitarios.UtilObjeto;

public final class VinculacionProgramaEnsambladorEntidad implements EnsambladorEntidad<VinculacionProgramaDominio, VinculacionProgramaEntidad> {

	private static final EnsambladorEntidad<VinculacionProgramaDominio, VinculacionProgramaEntidad> INSTANCIA = new VinculacionProgramaEnsambladorEntidad();

	private VinculacionProgramaEnsambladorEntidad() {
	}

	public static EnsambladorEntidad<VinculacionProgramaDominio, VinculacionProgramaEntidad> getInstancia() {
		return INSTANCIA;
	}

	@Override
	public VinculacionProgramaEntidad convertirAEntidad(VinculacionProgramaDominio dominio) {
		var dominioTmp = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(dominio, new VinculacionProgramaDominio.Builder().build());
		var destino = new VinculacionProgramaEntidad();
		destino.setId(dominioTmp.getId());
		destino.setUsuario(UsuarioEnsambladorEntidad.getInstancia().convertirAEntidad(dominioTmp.getUsuario()));
		destino.setProgramaAcademico(ProgramaAcademicoEnsambladorEntidad.getInstancia().convertirAEntidad(dominioTmp.getProgramaAcademico()));
		destino.setFechaInicio(dominioTmp.getFechaInicio());
		destino.setFechaFin(dominioTmp.getFechaFin());
		return destino;
	}

	@Override
	public VinculacionProgramaDominio convertirADominio(VinculacionProgramaEntidad origen) {
		var origenTmp = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(origen, new VinculacionProgramaEntidad());
		return new VinculacionProgramaDominio.Builder().id(origenTmp.getId()).usuario(UsuarioEnsambladorEntidad.getInstancia().convertirADominio(origenTmp.getUsuario())).programaAcademico(ProgramaAcademicoEnsambladorEntidad.getInstancia().convertirADominio(origenTmp.getProgramaAcademico())).fechaInicio(origenTmp.getFechaInicio()).fechaFin(origenTmp.getFechaFin()).build();
	}

	@Override
	public List<VinculacionProgramaDominio> convertirADominio(List<VinculacionProgramaEntidad> lista) {
		var listaTmp = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(lista, new ArrayList<VinculacionProgramaEntidad>());
		return listaTmp.stream().map(this::convertirADominio).toList();
	}
}
