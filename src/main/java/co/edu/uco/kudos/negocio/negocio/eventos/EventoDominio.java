package co.edu.uco.kudos.negocio.negocio.eventos;

import java.util.UUID;

import co.edu.uco.kudos.dominio.enums.TipoNotificacion;
import co.edu.uco.kudos.transversal.utilitarios.UtilUUID;

public record EventoDominio(String nombre, UUID idActor, String entidadAfectada, UUID idEntidadAfectada,
		String valorAnterior, String valorNuevo, UUID idDestinatario, TipoNotificacion tipoNotificacion,
		String mensajeNotificacion) {

	public static EventoDominio de(String nombre, UUID idActor, String entidad, UUID idEntidad, String anterior,
			String nuevo) {
		return new EventoDominio(nombre, idActor, entidad, idEntidad, anterior, nuevo, UtilUUID.obtenerUUIDDefecto(),
				TipoNotificacion.NO_DEFINIDO, "");
	}

	public EventoDominio notificando(UUID destinatario, TipoNotificacion tipo, String mensaje) {
		return new EventoDominio(nombre, idActor, entidadAfectada, idEntidadAfectada, valorAnterior, valorNuevo,
				destinatario, tipo, mensaje);
	}

	public boolean tieneDestinatario() {
		return !UtilUUID.esValorDefecto(idDestinatario);
	}
}
