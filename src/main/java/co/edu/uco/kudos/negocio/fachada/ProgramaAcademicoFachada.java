package co.edu.uco.kudos.negocio.fachada;

import java.util.List;
import java.util.UUID;

import co.edu.uco.kudos.dto.ProgramaAcademicoDTO;

public interface ProgramaAcademicoFachada {

	ProgramaAcademicoDTO registrarProgramaAcademico(UUID idActor, ProgramaAcademicoDTO datos);

	List<ProgramaAcademicoDTO> consultarProgramasAcademicos(ProgramaAcademicoDTO filtro);
}
