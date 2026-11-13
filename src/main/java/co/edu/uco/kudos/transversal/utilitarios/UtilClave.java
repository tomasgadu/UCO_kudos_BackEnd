package co.edu.uco.kudos.transversal.utilitarios;

import java.security.SecureRandom;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public final class UtilClave {

	private static final BCryptPasswordEncoder CIFRADOR = new BCryptPasswordEncoder();
	private static final SecureRandom ALEATORIO = new SecureRandom();

	private UtilClave() {
	}

	public static String cifrar(String clave) {
		return CIFRADOR.encode(UtilTexto.getUtilTexto().obtenerValorDefecto(clave));
	}

	public static boolean coincide(String claveIngresada, String claveCifrada) {
		if (UtilTexto.getUtilTexto().esVacia(claveIngresada) || UtilTexto.getUtilTexto().esVacia(claveCifrada)) {
			return false;
		}
		return CIFRADOR.matches(claveIngresada, claveCifrada);
	}

	public static String generarCodigoVerificacion() {
		return String.format("%06d", ALEATORIO.nextInt(1_000_000));
	}
}
