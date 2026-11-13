package co.edu.uco.kudos.negocio.negocio;

import java.util.List;
import java.util.UUID;

import co.edu.uco.kudos.dominio.ReplicaDominio;

public interface ReplicaNegocio {

	ReplicaDominio crearReplica(UUID idAutor, UUID idPadre, String contenido);

	List<ReplicaDominio> consultarConversacion(UUID idActor, UUID idKudo);
}
