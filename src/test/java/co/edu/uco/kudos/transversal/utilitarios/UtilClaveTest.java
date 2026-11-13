package co.edu.uco.kudos.transversal.utilitarios;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

class UtilClaveTest {

	@Test
	@DisplayName("La clave se guarda cifrada y se puede comprobar")
	void cifrarYComprobar() {
		var cifrada = UtilClave.cifrar("Kudos2026*");

		assertNotEquals("Kudos2026*", cifrada);
		assertTrue(UtilClave.coincide("Kudos2026*", cifrada));
		assertFalse(UtilClave.coincide("OtraClave1", cifrada));
	}

	@RepeatedTest(5)
	@DisplayName("El código de verificación tiene 6 dígitos")
	void codigoVerificacion() {
		assertTrue(UtilClave.generarCodigoVerificacion().matches(UtilTexto.CODIGO_VERIFICACION));
	}
}
