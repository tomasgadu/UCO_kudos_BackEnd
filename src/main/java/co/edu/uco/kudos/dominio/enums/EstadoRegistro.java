package co.edu.uco.kudos.dominio.enums;

public enum EstadoRegistro {
	ACTIVA, INACTIVA, NO_DEFINIDO;

	public static EstadoRegistro desdeTexto(String texto) {
		for (var valor : values()) {
			if (valor.name().equalsIgnoreCase(String.valueOf(texto).trim())) {
				return valor;
			}
		}
		return NO_DEFINIDO;
	}
}
