package co.edu.uco.kudos.dominio.enums;

public enum TipoNotificacion {
	KUDO_RECIBIDO, REPLICA_RECIBIDA, SOLICITUD_PENDIENTE, SOLICITUD_RESUELTA, CONTENIDO_VETADO, NO_DEFINIDO;

	public static TipoNotificacion desdeTexto(String texto) {
		for (var valor : values()) {
			if (valor.name().equalsIgnoreCase(String.valueOf(texto).trim())) {
				return valor;
			}
		}
		return NO_DEFINIDO;
	}
}
