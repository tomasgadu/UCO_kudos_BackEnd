package co.edu.uco.kudos.negocio.fachada.ensamblador.impl;

import java.util.ArrayList;
import java.util.List;

import co.edu.uco.kudos.dominio.ProgramaAcademicoDominio;
import co.edu.uco.kudos.dominio.enums.EstadoRegistro;
import co.edu.uco.kudos.dto.ProgramaAcademicoDTO;
import co.edu.uco.kudos.negocio.fachada.ensamblador.EnsambladorDTO;
import co.edu.uco.kudos.transversal.utilitarios.UtilObjeto;

public final class ProgramaAcademicoEnsambladorDTO implements EnsambladorDTO<ProgramaAcademicoDominio, ProgramaAcademicoDTO> {

	private static final EnsambladorDTO<ProgramaAcademicoDominio, ProgramaAcademicoDTO> INSTANCIA = new ProgramaAcademicoEnsambladorDTO();

	private ProgramaAcademicoEnsambladorDTO() {
	}

	public static EnsambladorDTO<ProgramaAcademicoDominio, ProgramaAcademicoDTO> getInstancia() {
		return INSTANCIA;
	}

	@Override
	public ProgramaAcademicoDTO convertirADTO(ProgramaAcademicoDominio dominio) {
		var dominioTmp = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(dominio, new ProgramaAcademicoDominio.Builder().build());
		var destino = new ProgramaAcademicoDTO();
		destino.setId(dominioTmp.getId());
		destino.setNombre(dominioTmp.getNombre());
		destino.setFacultad(FacultadEnsambladorDTO.getInstancia().convertirADTO(dominioTmp.getFacultad()));
		destino.setEstado(dominioTmp.getEstado().name());
		destino.setFechaCreacion(dominioTmp.getFechaCreacion());
		return destino;
	}

	@Override
	public ProgramaAcademicoDominio convertirADominio(ProgramaAcademicoDTO origen) {
		var origenTmp = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(origen, new ProgramaAcademicoDTO());
		return new ProgramaAcademicoDominio.Builder().id(origenTmp.getId()).nombre(origenTmp.getNombre()).facultad(FacultadEnsambladorDTO.getInstancia().convertirADominio(origenTmp.getFacultad())).estado(EstadoRegistro.desdeTexto(origenTmp.getEstado())).fechaCreacion(origenTmp.getFechaCreacion()).build();
	}

	@Override
	public List<ProgramaAcademicoDTO> convertirADTO(List<ProgramaAcademicoDominio> lista) {
		var listaTmp = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(lista, new ArrayList<ProgramaAcademicoDominio>());
		return listaTmp.stream().map(this::convertirADTO).toList();
	}
}
