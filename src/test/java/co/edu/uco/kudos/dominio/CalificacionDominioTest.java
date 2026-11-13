package co.edu.uco.kudos.dominio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import co.edu.uco.kudos.transversal.excepciones.KudosDominioExcepcion;

class CalificacionDominioTest {

	private CalificacionDominio calificar(int estrellas) {
		return CalificacionDominio.crear(UUID.randomUUID(), UUID.randomUUID(), FabricaDominioPrueba.estudiante(),
				estrellas, FabricaDominioPrueba.AHORA);
	}

	@ParameterizedTest(name = "{0} estrellas es válido")
	@ValueSource(ints = { 1, 3, 5 })
	@DisplayName("Se aceptan calificaciones de 1 a 5 estrellas")
	void estrellasValidas(int estrellas) {
		assertEquals(estrellas, calificar(estrellas).getEstrellas());
	}

	@ParameterizedTest(name = "{0} estrellas es inválido")
	@ValueSource(ints = { 0, 6, -1 })
	@DisplayName("Se rechazan calificaciones fuera de 1 a 5")
	void estrellasInvalidas(int estrellas) {
		assertThrows(KudosDominioExcepcion.class, () -> calificar(estrellas));
	}

	@ParameterizedTest(name = "cambiar a {0} estrellas")
	@ValueSource(ints = { 2, 5 })
	@DisplayName("Cambiar la calificación actualiza las estrellas y la fecha de modificación")
	void cambiarCalificacion(int nuevas) {
		var cambiada = calificar(3).cambiar(nuevas, FabricaDominioPrueba.AHORA.plusDays(1));

		assertEquals(nuevas, cambiada.getEstrellas());
		assertEquals(FabricaDominioPrueba.AHORA.plusDays(1), cambiada.getFechaModificacion());
	}
}
