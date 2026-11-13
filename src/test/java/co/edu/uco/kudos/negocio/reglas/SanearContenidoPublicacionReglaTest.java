package co.edu.uco.kudos.negocio.reglas;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import co.edu.uco.kudos.negocio.negocio.reglas.impl.publicacion.SanearContenidoPublicacionRegla;
import co.edu.uco.kudos.transversal.excepciones.KudosNegocioExcepcion;

class SanearContenidoPublicacionReglaTest {

	@Test
	@DisplayName("Quita las etiquetas HTML y los espacios de los extremos")
	void quitaEtiquetas() {
		assertEquals("Gracias por todo",
				SanearContenidoPublicacionRegla.ejecutar("  <b>Gracias</b> por <script>todo</script> "));
	}

	@ParameterizedTest(name = "contenido \"{0}\" es inválido")
	@NullSource
	@ValueSource(strings = { "", "   ", "<p></p>" })
	@DisplayName("El contenido vacío, o que solo tiene etiquetas, se rechaza")
	void contenidoVacio(String contenido) {
		assertThrows(KudosNegocioExcepcion.class, () -> SanearContenidoPublicacionRegla.ejecutar(contenido));
	}
}
