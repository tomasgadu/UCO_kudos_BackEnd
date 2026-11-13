package co.edu.uco.kudos.dominio.enums;

public enum TipoVerificacion {
	CORREO, CELULAR, NO_DEFINIDO;

	public static TipoVerificacion desdeTexto(String texto) {
		for (var valor : values()) {
			if (valor.name().equalsIgnoreCase(String.valueOf(texto).trim())) {
				return valor;
			}
		}
		return NO_DEFINIDO;
	}
}
