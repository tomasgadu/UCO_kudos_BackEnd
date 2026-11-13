package co.edu.uco.kudos.dominio.enums;

public enum EstadoVerificacion {
	PENDIENTE, VERIFICADA, EXPIRADA, NO_DEFINIDO;

	public static EstadoVerificacion desdeTexto(String texto) {
		for (var valor : values()) {
			if (valor.name().equalsIgnoreCase(String.valueOf(texto).trim())) {
				return valor;
			}
		}
		return NO_DEFINIDO;
	}
}
