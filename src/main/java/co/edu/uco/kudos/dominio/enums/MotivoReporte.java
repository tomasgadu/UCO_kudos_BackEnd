package co.edu.uco.kudos.dominio.enums;

public enum MotivoReporte {
	LENGUAJE_OFENSIVO, ACOSO, DISCRIMINACION, SPAM, INFORMACION_FALSA, OTRO, NO_DEFINIDO;

	public static MotivoReporte desdeTexto(String texto) {
		for (var valor : values()) {
			if (valor.name().equalsIgnoreCase(String.valueOf(texto).trim())) {
				return valor;
			}
		}
		return NO_DEFINIDO;
	}
}
