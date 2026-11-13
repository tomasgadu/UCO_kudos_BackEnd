package co.edu.uco.kudos.negocio.reglas;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import co.edu.uco.kudos.negocio.negocio.reglas.impl.ValidadorCampos;
import co.edu.uco.kudos.transversal.excepciones.KudosNegocioExcepcion;
import co.edu.uco.kudos.transversal.utilitarios.UtilTexto;
import co.edu.uco.kudos.transversal.utilitarios.UtilUUID;

class ValidadorCamposTest {

	@Test
	@DisplayName("Un texto obligatorio vacío se rechaza")
	void obligatorioVacio() {
		assertThrows(KudosNegocioExcepcion.class, () -> ValidadorCampos.validarTextoObligatorio("  ", "nombre", 3, 50));
	}

	@Test
	@DisplayName("Un texto fuera de la longitud permitida se rechaza")
	void longitudInvalida() {
		assertThrows(KudosNegocioExcepcion.class, () -> ValidadorCampos.validarTextoObligatorio("ab", "nombre", 3, 50));
	}

	@Test
	@DisplayName("Un texto opcional vacío se acepta")
	void opcionalVacio() {
		assertDoesNotThrow(() -> ValidadorCampos.validarTextoOpcional(null, "descripción", 10, 250));
	}

	@Test
	@DisplayName("El celular debe tener 10 dígitos y empezar por 3")
	void formatoCelular() {
		assertDoesNotThrow(() -> ValidadorCampos.validarFormato("3001234567", "celular", UtilTexto.CELULAR_COLOMBIA));
		assertThrows(KudosNegocioExcepcion.class,
				() -> ValidadorCampos.validarFormato("6041234567", "celular", UtilTexto.CELULAR_COLOMBIA));
	}

	@Test
	@DisplayName("El identificador por defecto se considera vacío")
	void identificadorDefecto() {
		var vacio = UtilUUID.obtenerUUIDDefecto();

		assertThrows(KudosNegocioExcepcion.class, () -> ValidadorCampos.validarIdentificadorObligatorio(vacio, "id"));
	}
}
