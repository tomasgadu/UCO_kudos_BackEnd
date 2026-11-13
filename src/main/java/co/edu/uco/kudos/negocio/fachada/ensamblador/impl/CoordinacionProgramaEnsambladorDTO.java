package co.edu.uco.kudos.negocio.fachada.ensamblador.impl;

import java.util.ArrayList;
import java.util.List;

import co.edu.uco.kudos.dominio.CoordinacionProgramaDominio;
import co.edu.uco.kudos.dto.CoordinacionProgramaDTO;
import co.edu.uco.kudos.negocio.fachada.ensamblador.EnsambladorDTO;
import co.edu.uco.kudos.transversal.utilitarios.UtilObjeto;

public final class CoordinacionProgramaEnsambladorDTO implements EnsambladorDTO<CoordinacionProgramaDominio, CoordinacionProgramaDTO> {

	private static final EnsambladorDTO<CoordinacionProgramaDominio, CoordinacionProgramaDTO> INSTANCIA = new CoordinacionProgramaEnsambladorDTO();

	private CoordinacionProgramaEnsambladorDTO() {
	}

	public static EnsambladorDTO<CoordinacionProgramaDominio, CoordinacionProgramaDTO> getInstancia() {
		return INSTANCIA;
	}

	@Override
	public CoordinacionProgramaDTO convertirADTO(CoordinacionProgramaDominio dominio) {
		var dominioTmp = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(dominio, new CoordinacionProgramaDominio.Builder().build());
		var destino = new CoordinacionProgramaDTO();
		destino.setId(dominioTmp.getId());
		destino.setCoordinador(UsuarioEnsambladorDTO.getInstancia().convertirADTO(dominioTmp.getCoordinador()));
		destino.setProgramaAcademico(ProgramaAcademicoEnsambladorDTO.getInstancia().convertirADTO(dominioTmp.getProgramaAcademico()));
		destino.setFechaInicio(dominioTmp.getFechaInicio());
		destino.setFechaFin(dominioTmp.getFechaFin());
		return destino;
	}

	@Override
	public CoordinacionProgramaDominio convertirADominio(CoordinacionProgramaDTO origen) {
		var origenTmp = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(origen, new CoordinacionProgramaDTO());
		return new CoordinacionProgramaDominio.Builder().id(origenTmp.getId()).coordinador(UsuarioEnsambladorDTO.getInstancia().convertirADominio(origenTmp.getCoordinador())).programaAcademico(ProgramaAcademicoEnsambladorDTO.getInstancia().convertirADominio(origenTmp.getProgramaAcademico())).fechaInicio(origenTmp.getFechaInicio()).fechaFin(origenTmp.getFechaFin()).build();
	}

	@Override
	public List<CoordinacionProgramaDTO> convertirADTO(List<CoordinacionProgramaDominio> lista) {
		var listaTmp = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(lista, new ArrayList<CoordinacionProgramaDominio>());
		return listaTmp.stream().map(this::convertirADTO).toList();
	}
}
