package co.edu.uco.kudos.negocio.reglas;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import co.edu.uco.kudos.negocio.negocio.reglas.Regla;
import co.edu.uco.kudos.negocio.negocio.reglas.impl.categoria.AsegurarNombreCategoriaNoExistaRegla;
import co.edu.uco.kudos.transversal.excepciones.KudosNegocioExcepcion;

class ReglaTest {

	@Test
	@DisplayName("Con la cantidad de datos esperada no lanza error")
	void cantidadCorrecta() {
		assertDoesNotThrow(() -> Regla.validarCantidadDatos(new Object[] { "a", "b" }, 2));
	}

	@Test
	@DisplayName("Con menos datos de los esperados lanza un error controlado")
	void cantidadInsuficiente() {
		assertThrows(KudosNegocioExcepcion.class, () -> Regla.validarCantidadDatos(new Object[] { "a" }, 3));
	}

	@Test
	@DisplayName("Con datos nulos lanza un error controlado")
	void datosNulos() {
		assertThrows(KudosNegocioExcepcion.class, () -> Regla.validarCantidadDatos(null, 1));
	}

	@Test
	@DisplayName("Una regla llamada sin datos falla con error controlado y no con ArrayIndexOutOfBounds")
	void reglaSinDatos() {
		var regla = AsegurarNombreCategoriaNoExistaRegla.obtenerInstancia();

		assertThrows(KudosNegocioExcepcion.class, regla::ejecutar);
	}
}
