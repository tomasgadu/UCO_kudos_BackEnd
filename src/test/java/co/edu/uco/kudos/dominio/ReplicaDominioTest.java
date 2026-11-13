package co.edu.uco.kudos.dominio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import co.edu.uco.kudos.dominio.enums.EstadoPublicacion;
import co.edu.uco.kudos.transversal.excepciones.KudosDominioExcepcion;

class ReplicaDominioTest {

	@Test
	@DisplayName("Responder un Kudo crea una réplica cuyo padre y raíz son el Kudo")
	void responderKudo() {
		var kudo = FabricaDominioPrueba.kudo(EstadoPublicacion.VISIBLE);

		var replica = ReplicaDominio.responder(UUID.randomUUID(), FabricaDominioPrueba.estudiante(), kudo,
				"Totalmente de acuerdo", FabricaDominioPrueba.AHORA);

		assertEquals(kudo.getId(), replica.getIdPadre());
		assertSame(kudo, replica.obtenerKudoRaiz());
		assertEquals(EstadoPublicacion.CREADO, replica.getEstado());
	}

	@Test
	@DisplayName("Responder una réplica conserva el Kudo raíz de la conversación")
	void responderReplicaConservaRaiz() {
		var kudo = FabricaDominioPrueba.kudo(EstadoPublicacion.VISIBLE);
		var primera = ReplicaDominio.responder(UUID.randomUUID(), FabricaDominioPrueba.estudiante(), kudo,
				"Primera respuesta", FabricaDominioPrueba.AHORA).publicar();

		var segunda = ReplicaDominio.responder(UUID.randomUUID(), FabricaDominioPrueba.estudiante(), primera,
				"Respuesta a la respuesta", FabricaDominioPrueba.AHORA);

		assertEquals(primera.getId(), segunda.getIdPadre());
		assertSame(kudo, segunda.obtenerKudoRaiz());
	}

	@Test
	@DisplayName("No se puede responder una publicación vetada")
	void responderVetadaFalla() {
		var vetado = FabricaDominioPrueba.kudo(EstadoPublicacion.VETADO);
		var autor = FabricaDominioPrueba.estudiante();

		assertThrows(KudosDominioExcepcion.class, () -> ReplicaDominio.responder(UUID.randomUUID(), autor, vetado,
				"Intento responder", FabricaDominioPrueba.AHORA));
	}
}
