package co.edu.uco.kudos.transversal.utilitarios;

import java.util.UUID;
import java.util.function.Predicate;

public final class UtilUUID {

	public static final String UUID_DEFECTO_TEXTO = "00000000-0000-0000-0000-000000000000";

	private UtilUUID() {
	}

	public static UUID generar() {
		return UUID.randomUUID();
	}

	public static String generarComoTexto() {
		return generar().toString().toLowerCase();
	}

	public static UUID convertirAUUID(final String uuidTexto) {
		return UUID.fromString(obtenerValorDefectoComoTexto(uuidTexto));
	}

	public static UUID obtenerValorDefecto(final UUID valor, final UUID valorDefecto) {
		return UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(valor, valorDefecto);
	}

	public static UUID obtenerValorDefecto(final UUID valor) {
		return obtenerValorDefecto(valor, obtenerUUIDDefecto());
	}

	public static UUID obtenerUUIDDefecto() {
		return convertirAUUID(UUID_DEFECTO_TEXTO);
	}

	public static boolean esValorDefecto(final UUID valor) {
		return obtenerUUIDDefecto().equals(obtenerValorDefecto(valor));
	}

	public static UUID generarUnico(Predicate<UUID> existe) {
		var id = generar();
		while (existe.test(id)) {
			id = generar();
		}
		return id;
	}

	public static String obtenerValorDefectoComoTexto(final String valor) {
		return UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(valor, UUID_DEFECTO_TEXTO);
	}
}
