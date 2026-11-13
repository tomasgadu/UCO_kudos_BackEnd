package co.edu.uco.kudos.negocio.fachada.impl;

import java.util.List;

import co.edu.uco.kudos.dto.RolDTO;
import co.edu.uco.kudos.negocio.fachada.FachadaBase;
import co.edu.uco.kudos.negocio.fachada.RolFachada;
import co.edu.uco.kudos.negocio.fachada.ensamblador.impl.RolEnsambladorDTO;
import co.edu.uco.kudos.negocio.negocio.impl.RolNegocioImpl;

public final class RolFachadaImpl extends FachadaBase implements RolFachada {

	@Override
	public List<RolDTO> consultarRoles() {
		return ejecutarConsulta("consultar los roles", daoFactory -> RolEnsambladorDTO.getInstancia()
				.convertirADTO(new RolNegocioImpl(daoFactory).consultarRoles()));
	}
}
