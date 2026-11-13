package co.edu.uco.kudos.dominio.enums;

public enum EstadoReporte {
	PENDIENTE, EN_REVISION, ACEPTADO, RECHAZADO, NO_DEFINIDO;

	public static EstadoReporte desdeTexto(String texto) {
		for (var valor : values()) {
			if (valor.name().equalsIgnoreCase(String.valueOf(texto).trim())) {
				return valor;
			}
		}
		return NO_DEFINIDO;
	}
}
