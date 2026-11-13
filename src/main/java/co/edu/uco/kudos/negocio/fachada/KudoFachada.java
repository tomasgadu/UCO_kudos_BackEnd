package co.edu.uco.kudos.negocio.fachada;

import java.util.List;
import java.util.UUID;

import co.edu.uco.kudos.dto.KudoDTO;
import co.edu.uco.kudos.dto.NuevaReplicaDTO;
import co.edu.uco.kudos.dto.NuevoKudoDTO;
import co.edu.uco.kudos.dto.ReplicaDTO;

public interface KudoFachada {

	KudoDTO crearKudo(UUID idAutor, NuevoKudoDTO datos);

	List<KudoDTO> consultarMuro(UUID idActor, UUID idDestinatario);

	KudoDTO consultarKudo(UUID idActor, UUID idKudo);

	ReplicaDTO crearReplica(UUID idAutor, NuevaReplicaDTO datos);

	List<ReplicaDTO> consultarConversacion(UUID idActor, UUID idKudo);
}
