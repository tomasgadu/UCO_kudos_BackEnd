package co.edu.uco.kudos.negocio.negocio;

import java.util.UUID;

import co.edu.uco.kudos.dominio.VetoDominio;

public interface VetoNegocio {

	VetoDominio vetar(UUID idActor, UUID idPublicacion, String justificacion);
}
