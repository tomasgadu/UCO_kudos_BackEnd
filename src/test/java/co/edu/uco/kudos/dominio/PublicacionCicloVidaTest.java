package co.edu.uco.kudos.dominio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import co.edu.uco.kudos.dominio.enums.EstadoPublicacion;
import co.edu.uco.kudos.transversal.excepciones.KudosDominioExcepcion;

class PublicacionCicloVidaTest {

	@Test
	@DisplayName("VISIBLE → REPORTADO al recibir un reporte")
	void marcarReportada() {
		var kudo = FabricaDominioPrueba.kudo(EstadoPublicacion.VISIBLE);

		assertEquals(EstadoPublicacion.REPORTADO, kudo.marcarReportada().getEstado());
	}

	@Test
	@DisplayName("Un segundo reporte no cambia una publicación ya REPORTADA")
	void marcarReportadaDosVeces() {
		var kudo = FabricaDominioPrueba.kudo(EstadoPublicacion.REPORTADO);

		assertEquals(EstadoPublicacion.REPORTADO, kudo.marcarReportada().getEstado());
	}

	@Test
	@DisplayName("REPORTADO → EN_REVISION")
	void iniciarRevision() {
		var kudo = FabricaDominioPrueba.kudo(EstadoPublicacion.REPORTADO);

		assertEquals(EstadoPublicacion.EN_REVISION, kudo.iniciarRevision().getEstado());
	}

	@Test
	@DisplayName("EN_REVISION → VISIBLE al rechazar el reporte")
	void restaurar() {
		var kudo = FabricaDominioPrueba.kudo(EstadoPublicacion.EN_REVISION);

		assertEquals(EstadoPublicacion.VISIBLE, kudo.restaurar().getEstado());
	}

	@Test
	@DisplayName("Solo se restaura una publicación EN_REVISION")
	void restaurarSinRevisionFalla() {
		var kudo = FabricaDominioPrueba.kudo(EstadoPublicacion.VISIBLE);

		assertThrows(KudosDominioExcepcion.class, kudo::restaurar);
	}

	@Test
	@DisplayName("Vetar deja la publicación VETADA y sin poder recibir réplicas")
	void vetar() {
		var vetado = FabricaDominioPrueba.kudo(EstadoPublicacion.EN_REVISION).vetar();

		assertTrue(vetado.estaVetada());
		assertFalse(vetado.puedeRecibirReplicas());
	}

	@Test
	@DisplayName("Una publicación no se puede vetar dos veces")
	void vetarDosVecesFalla() {
		var vetado = FabricaDominioPrueba.kudo(EstadoPublicacion.VETADO);

		assertThrows(KudosDominioExcepcion.class, vetado::vetar);
	}

	@Test
	@DisplayName("Una publicación vetada no se puede reportar")
	void reportarVetadaFalla() {
		var vetado = FabricaDominioPrueba.kudo(EstadoPublicacion.VETADO);

		assertThrows(KudosDominioExcepcion.class, vetado::marcarReportada);
	}

	@Test
	@DisplayName("Lo vetado solo lo ven quienes tienen permiso")
	void visibilidadVetada() {
		var vetado = FabricaDominioPrueba.kudo(EstadoPublicacion.VETADO);

		assertFalse(vetado.esVisiblePara(false));
		assertTrue(vetado.esVisiblePara(true));
	}
}
