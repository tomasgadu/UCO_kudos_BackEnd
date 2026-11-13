package co.edu.uco.kudos.dominio.enums;

public enum EstadoPublicacion {
	CREADO, VISIBLE, REPORTADO, EN_REVISION, VETADO, NO_DEFINIDO;

	public static EstadoPublicacion desdeTexto(String texto) {
		for (var valor : values()) {
			if (valor.name().equalsIgnoreCase(String.valueOf(texto).trim())) {
				return valor;
			}
		}
		return NO_DEFINIDO;
	}
}
