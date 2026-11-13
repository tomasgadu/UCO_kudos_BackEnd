package co.edu.uco.kudos.negocio.fachada.impl;

import java.util.List;
import java.util.UUID;

import co.edu.uco.kudos.dto.FacultadDTO;
import co.edu.uco.kudos.negocio.fachada.FachadaBase;
import co.edu.uco.kudos.negocio.fachada.FacultadFachada;
import co.edu.uco.kudos.negocio.fachada.ensamblador.impl.FacultadEnsambladorDTO;
import co.edu.uco.kudos.negocio.negocio.impl.FacultadNegocioImpl;

public final class FacultadFachadaImpl extends FachadaBase implements FacultadFachada {

	@Override
	public FacultadDTO registrarFacultad(UUID idActor, FacultadDTO datos) {
		return ejecutarEnTransaccion("registrar la facultad", daoFactory -> {
			var dominio = FacultadEnsambladorDTO.getInstancia().convertirADominio(datos);
			var registrada = new FacultadNegocioImpl(daoFactory).registrarFacultad(idActor, dominio);
			return FacultadEnsambladorDTO.getInstancia().convertirADTO(registrada);
		});
	}

	@Override
	public List<FacultadDTO> consultarFacultades(FacultadDTO filtro) {
		return ejecutarConsulta("consultar las facultades", daoFactory -> {
			var dominio = FacultadEnsambladorDTO.getInstancia().convertirADominio(filtro);
			return FacultadEnsambladorDTO.getInstancia()
					.convertirADTO(new FacultadNegocioImpl(daoFactory).consultarFacultades(dominio));
		});
	}
}
