package co.edu.uco.kudos.negocio.negocio;

import java.util.UUID;

import co.edu.uco.kudos.dominio.CalificacionDominio;
import co.edu.uco.kudos.dominio.ResumenCalificacion;

public interface CalificacionNegocio {

	CalificacionDominio calificar(UUID idActor, UUID idPublicacion, int estrellas);

	ResumenCalificacion consultarResumen(UUID idActor, UUID idPublicacion);
}
