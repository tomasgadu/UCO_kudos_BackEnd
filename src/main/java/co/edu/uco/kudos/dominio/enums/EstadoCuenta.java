package co.edu.uco.kudos.dominio.enums;

public enum EstadoCuenta {
	REGISTRADA, ACTIVA, SUSPENDIDA, DESACTIVADA, NO_DEFINIDO;

	public static EstadoCuenta desdeTexto(String texto) {
		for (var valor : values()) {
			if (valor.name().equalsIgnoreCase(String.valueOf(texto).trim())) {
				return valor;
			}
		}
		return NO_DEFINIDO;
	}
}
