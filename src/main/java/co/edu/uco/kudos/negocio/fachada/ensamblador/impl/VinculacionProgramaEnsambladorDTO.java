package co.edu.uco.kudos.negocio.fachada.ensamblador.impl;

import java.util.ArrayList;
import java.util.List;

import co.edu.uco.kudos.dominio.VinculacionProgramaDominio;
import co.edu.uco.kudos.dto.VinculacionProgramaDTO;
import co.edu.uco.kudos.negocio.fachada.ensamblador.EnsambladorDTO;
import co.edu.uco.kudos.transversal.utilitarios.UtilObjeto;

public final class VinculacionProgramaEnsambladorDTO implements EnsambladorDTO<VinculacionProgramaDominio, VinculacionProgramaDTO> {

	private static final EnsambladorDTO<VinculacionProgramaDominio, VinculacionProgramaDTO> INSTANCIA = new VinculacionProgramaEnsambladorDTO();

	private VinculacionProgramaEnsambladorDTO() {
	}

	public static EnsambladorDTO<VinculacionProgramaDominio, VinculacionProgramaDTO> getInstancia() {
		return INSTANCIA;
	}

	@Override
	public VinculacionProgramaDTO convertirADTO(VinculacionProgramaDominio dominio) {
		var dominioTmp = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(dominio, new VinculacionProgramaDominio.Builder().build());
		var destino = new VinculacionProgramaDTO();
		destino.setId(dominioTmp.getId());
		destino.setUsuario(UsuarioEnsambladorDTO.getInstancia().convertirADTO(dominioTmp.getUsuario()));
		destino.setProgramaAcademico(ProgramaAcademicoEnsambladorDTO.getInstancia().convertirADTO(dominioTmp.getProgramaAcademico()));
		destino.setFechaInicio(dominioTmp.getFechaInicio());
		destino.setFechaFin(dominioTmp.getFechaFin());
		return destino;
	}

	@Override
	public VinculacionProgramaDominio convertirADominio(VinculacionProgramaDTO origen) {
		var origenTmp = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(origen, new VinculacionProgramaDTO());
		return new VinculacionProgramaDominio.Builder().id(origenTmp.getId()).usuario(UsuarioEnsambladorDTO.getInstancia().convertirADominio(origenTmp.getUsuario())).programaAcademico(ProgramaAcademicoEnsambladorDTO.getInstancia().convertirADominio(origenTmp.getProgramaAcademico())).fechaInicio(origenTmp.getFechaInicio()).fechaFin(origenTmp.getFechaFin()).build();
	}

	@Override
	public List<VinculacionProgramaDTO> convertirADTO(List<VinculacionProgramaDominio> lista) {
		var listaTmp = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(lista, new ArrayList<VinculacionProgramaDominio>());
		return listaTmp.stream().map(this::convertirADTO).toList();
	}
}
