package co.edu.uco.kudos.negocio.fachada.impl;

import java.util.List;
import java.util.UUID;

import co.edu.uco.kudos.dto.ProgramaAcademicoDTO;
import co.edu.uco.kudos.negocio.fachada.FachadaBase;
import co.edu.uco.kudos.negocio.fachada.ProgramaAcademicoFachada;
import co.edu.uco.kudos.negocio.fachada.ensamblador.impl.ProgramaAcademicoEnsambladorDTO;
import co.edu.uco.kudos.negocio.negocio.impl.ProgramaAcademicoNegocioImpl;

public final class ProgramaAcademicoFachadaImpl extends FachadaBase implements ProgramaAcademicoFachada {

	@Override
	public ProgramaAcademicoDTO registrarProgramaAcademico(UUID idActor, ProgramaAcademicoDTO datos) {
		return ejecutarEnTransaccion("registrar el programa académico", daoFactory -> {
			var dominio = ProgramaAcademicoEnsambladorDTO.getInstancia().convertirADominio(datos);
			var registrado = new ProgramaAcademicoNegocioImpl(daoFactory).registrarProgramaAcademico(idActor, dominio);
			return ProgramaAcademicoEnsambladorDTO.getInstancia().convertirADTO(registrado);
		});
	}

	@Override
	public List<ProgramaAcademicoDTO> consultarProgramasAcademicos(ProgramaAcademicoDTO filtro) {
		return ejecutarConsulta("consultar los programas académicos", daoFactory -> {
			var dominio = ProgramaAcademicoEnsambladorDTO.getInstancia().convertirADominio(filtro);
			return ProgramaAcademicoEnsambladorDTO.getInstancia()
					.convertirADTO(new ProgramaAcademicoNegocioImpl(daoFactory).consultarProgramasAcademicos(dominio));
		});
	}
}
