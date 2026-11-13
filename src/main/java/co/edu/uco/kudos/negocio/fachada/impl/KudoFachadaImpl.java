package co.edu.uco.kudos.negocio.fachada.impl;

import java.util.List;
import java.util.UUID;

import co.edu.uco.kudos.dto.KudoDTO;
import co.edu.uco.kudos.dto.NuevaReplicaDTO;
import co.edu.uco.kudos.dto.NuevoKudoDTO;
import co.edu.uco.kudos.dto.ReplicaDTO;
import co.edu.uco.kudos.negocio.fachada.FachadaBase;
import co.edu.uco.kudos.negocio.fachada.KudoFachada;
import co.edu.uco.kudos.negocio.fachada.ensamblador.impl.KudoEnsambladorDTO;
import co.edu.uco.kudos.negocio.fachada.ensamblador.impl.ReplicaEnsambladorDTO;
import co.edu.uco.kudos.negocio.negocio.impl.KudoNegocioImpl;
import co.edu.uco.kudos.negocio.negocio.impl.ReplicaNegocioImpl;

public final class KudoFachadaImpl extends FachadaBase implements KudoFachada {

	@Override
	public KudoDTO crearKudo(UUID idAutor, NuevoKudoDTO datos) {
		return ejecutarEnTransaccion("publicar el Kudo", daoFactory -> KudoEnsambladorDTO.getInstancia()
				.convertirADTO(new KudoNegocioImpl(daoFactory).crearKudo(idAutor, datos.getIdDestinatario(),
						datos.getIdCategoria(), datos.getContenido())));
	}

	@Override
	public List<KudoDTO> consultarMuro(UUID idActor, UUID idDestinatario) {
		return ejecutarConsulta("consultar los Kudos", daoFactory -> KudoEnsambladorDTO.getInstancia()
				.convertirADTO(new KudoNegocioImpl(daoFactory).consultarMuro(idActor, idDestinatario)));
	}

	@Override
	public KudoDTO consultarKudo(UUID idActor, UUID idKudo) {
		return ejecutarEnTransaccion("consultar el Kudo", daoFactory -> KudoEnsambladorDTO.getInstancia()
				.convertirADTO(new KudoNegocioImpl(daoFactory).consultarKudo(idActor, idKudo)));
	}

	@Override
	public ReplicaDTO crearReplica(UUID idAutor, NuevaReplicaDTO datos) {
		return ejecutarEnTransaccion("publicar la réplica", daoFactory -> ReplicaEnsambladorDTO.getInstancia()
				.convertirADTO(new ReplicaNegocioImpl(daoFactory).crearReplica(idAutor, datos.getIdPadre(),
						datos.getContenido())));
	}

	@Override
	public List<ReplicaDTO> consultarConversacion(UUID idActor, UUID idKudo) {
		return ejecutarConsulta("consultar la conversación", daoFactory -> ReplicaEnsambladorDTO.getInstancia()
				.convertirADTO(new ReplicaNegocioImpl(daoFactory).consultarConversacion(idActor, idKudo)));
	}
}
