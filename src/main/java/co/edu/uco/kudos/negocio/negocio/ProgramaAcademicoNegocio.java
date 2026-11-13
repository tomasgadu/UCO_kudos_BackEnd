package co.edu.uco.kudos.negocio.negocio;

import java.util.List;
import java.util.UUID;

import co.edu.uco.kudos.dominio.ProgramaAcademicoDominio;

public interface ProgramaAcademicoNegocio {

	ProgramaAcademicoDominio registrarProgramaAcademico(UUID idActor, ProgramaAcademicoDominio datos);

	List<ProgramaAcademicoDominio> consultarProgramasAcademicos(ProgramaAcademicoDominio filtro);
}
