package co.edu.uco.kudos.negocio.fachada;

import java.util.List;

import co.edu.uco.kudos.dto.ProgramaAcademicoDTO;

public interface ProgramaAcademicoFachada {

	ProgramaAcademicoDTO registrarProgramaAcademico(ProgramaAcademicoDTO datos);

	List<ProgramaAcademicoDTO> consultarProgramasAcademicos(ProgramaAcademicoDTO filtro);
}
