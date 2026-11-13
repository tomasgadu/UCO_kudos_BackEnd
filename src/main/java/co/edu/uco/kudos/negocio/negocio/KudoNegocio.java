package co.edu.uco.kudos.negocio.negocio;

import java.util.List;
import java.util.UUID;

import co.edu.uco.kudos.dominio.KudoDominio;

public interface KudoNegocio {

	KudoDominio crearKudo(UUID idAutor, UUID idDestinatario, UUID idCategoria, String contenido);

	List<KudoDominio> consultarMuro(UUID idActor, UUID idDestinatario);

	KudoDominio consultarKudo(UUID idActor, UUID idKudo);
}
