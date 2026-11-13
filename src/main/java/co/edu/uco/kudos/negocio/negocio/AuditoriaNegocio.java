package co.edu.uco.kudos.negocio.negocio;

import java.util.List;
import java.util.UUID;

import co.edu.uco.kudos.dominio.RegistroAuditoriaDominio;

public interface AuditoriaNegocio {

	List<RegistroAuditoriaDominio> consultarAuditoria(UUID idActor, String accion, String entidadAfectada,
			UUID idEntidadAfectada);
}
