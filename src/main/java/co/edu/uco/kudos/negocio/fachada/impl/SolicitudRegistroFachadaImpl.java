package co.edu.uco.kudos.negocio.fachada.impl;

import java.util.List;
import java.util.UUID;

import co.edu.uco.kudos.dto.SolicitudRegistroDTO;
import co.edu.uco.kudos.negocio.fachada.FachadaBase;
import co.edu.uco.kudos.negocio.fachada.SolicitudRegistroFachada;
import co.edu.uco.kudos.negocio.fachada.ensamblador.impl.SolicitudRegistroEnsambladorDTO;
import co.edu.uco.kudos.negocio.negocio.impl.SolicitudRegistroNegocioImpl;

public final class SolicitudRegistroFachadaImpl extends FachadaBase implements SolicitudRegistroFachada {

	@Override
	public List<SolicitudRegistroDTO> consultarSolicitudesPendientes(UUID idActor) {
		return ejecutarConsulta("consultar las solicitudes", daoFactory -> SolicitudRegistroEnsambladorDTO.getInstancia()
				.convertirADTO(new SolicitudRegistroNegocioImpl(daoFactory).consultarSolicitudesPendientes(idActor)));
	}

	@Override
	public SolicitudRegistroDTO aprobarSolicitud(UUID idActor, UUID idSolicitud, String observacion) {
		return ejecutarEnTransaccion("aprobar la solicitud", daoFactory -> SolicitudRegistroEnsambladorDTO.getInstancia()
				.convertirADTO(new SolicitudRegistroNegocioImpl(daoFactory).aprobarSolicitud(idActor, idSolicitud,
						observacion)));
	}

	@Override
	public SolicitudRegistroDTO rechazarSolicitud(UUID idActor, UUID idSolicitud, String observacion) {
		return ejecutarEnTransaccion("rechazar la solicitud", daoFactory -> SolicitudRegistroEnsambladorDTO.getInstancia()
				.convertirADTO(new SolicitudRegistroNegocioImpl(daoFactory).rechazarSolicitud(idActor, idSolicitud,
						observacion)));
	}
}
