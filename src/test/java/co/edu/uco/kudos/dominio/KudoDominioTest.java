package co.edu.uco.kudos.dominio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import co.edu.uco.kudos.dominio.enums.EstadoPublicacion;
import co.edu.uco.kudos.transversal.excepciones.KudosDominioExcepcion;

class KudoDominioTest {

	@Test
	@DisplayName("Un Kudo nuevo nace en estado CREADO")
	void crearKudoQuedaCreado() {
		var autor = FabricaDominioPrueba.estudiante();
		var destinatario = FabricaDominioPrueba.estudiante();

		var kudo = KudoDominio.crear(UUID.randomUUID(), autor, destinatario, new CategoriaDominio.Builder().build(),
				"Excelente trabajo en el semillero", FabricaDominioPrueba.AHORA);

		assertEquals(EstadoPublicacion.CREADO, kudo.getEstado());
		assertEquals(destinatario, kudo.getDestinatario());
	}

	@Test
	@DisplayName("Nadie puede darse un Kudo a sí mismo")
	void crearKudoASiMismoFalla() {
		var autor = FabricaDominioPrueba.estudiante();

		assertThrows(KudosDominioExcepcion.class, () -> KudoDominio.crear(UUID.randomUUID(), autor, autor,
				new CategoriaDominio.Builder().build(), "Me reconozco a mí mismo", FabricaDominioPrueba.AHORA));
	}

	@Test
	@DisplayName("Publicar pasa el Kudo de CREADO a VISIBLE")
	void publicarKudoCreado() {
		var kudo = FabricaDominioPrueba.kudo(EstadoPublicacion.CREADO);

		assertEquals(EstadoPublicacion.VISIBLE, kudo.publicar().getEstado());
	}

	@Test
	@DisplayName("No se puede publicar un Kudo que ya está visible")
	void publicarKudoVisibleFalla() {
		var kudo = FabricaDominioPrueba.kudo(EstadoPublicacion.VISIBLE);

		assertThrows(KudosDominioExcepcion.class, kudo::publicar);
	}

	@Test
	@DisplayName("El Kudo raíz de un Kudo es él mismo")
	void kudoRaizEsElMismo() {
		var kudo = FabricaDominioPrueba.kudo(EstadoPublicacion.VISIBLE);

		assertSame(kudo, kudo.obtenerKudoRaiz());
	}

	@Test
	@DisplayName("Cambiar el estado no modifica el objeto original (inmutabilidad)")
	void transicionNoModificaOriginal() {
		var kudo = FabricaDominioPrueba.kudo(EstadoPublicacion.CREADO);

		kudo.publicar();

		assertEquals(EstadoPublicacion.CREADO, kudo.getEstado());
	}
}
