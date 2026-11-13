package co.edu.uco.kudos.dominio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import co.edu.uco.kudos.dominio.enums.CodigoRol;
import co.edu.uco.kudos.dominio.enums.EstadoReporte;
import co.edu.uco.kudos.dominio.enums.MotivoReporte;
import co.edu.uco.kudos.transversal.excepciones.KudosDominioExcepcion;

class ReporteDominioTest {

	private static final String OBSERVACION = "Se revisó el contenido con el comité";

	private ReporteDominio reporte(MotivoReporte motivo, String descripcion) {
		return ReporteDominio.crear(UUID.randomUUID(), UUID.randomUUID(), FabricaDominioPrueba.estudiante(), motivo,
				descripcion, FabricaDominioPrueba.AHORA);
	}

	@Test
	@DisplayName("Un reporte nuevo queda PENDIENTE y abierto")
	void crearReporte() {
		var reporte = reporte(MotivoReporte.SPAM, null);

		assertEquals(EstadoReporte.PENDIENTE, reporte.getEstado());
		assertTrue(reporte.estaAbierto());
	}

	@Test
	@DisplayName("El motivo es obligatorio")
	void motivoObligatorio() {
		assertThrows(KudosDominioExcepcion.class, () -> reporte(MotivoReporte.NO_DEFINIDO, null));
	}

	@Test
	@DisplayName("El motivo OTRO exige una descripción de al menos 10 caracteres")
	void motivoOtroExigeDescripcion() {
		assertThrows(KudosDominioExcepcion.class, () -> reporte(MotivoReporte.OTRO, "corta"));
	}

	@Test
	@DisplayName("PENDIENTE → EN_REVISION → ACEPTADO")
	void aceptarReporte() {
		var autoridad = FabricaDominioPrueba.usuario(CodigoRol.RECTOR);

		var decidido = reporte(MotivoReporte.ACOSO, null).iniciarRevision(autoridad).decidir(true, autoridad,
				OBSERVACION, FabricaDominioPrueba.AHORA);

		assertEquals(EstadoReporte.ACEPTADO, decidido.getEstado());
		assertFalse(decidido.estaAbierto());
	}

	@Test
	@DisplayName("PENDIENTE → EN_REVISION → RECHAZADO")
	void rechazarReporte() {
		var autoridad = FabricaDominioPrueba.usuario(CodigoRol.COORDINADOR_ACADEMICO);

		var decidido = reporte(MotivoReporte.SPAM, null).iniciarRevision(autoridad).decidir(false, autoridad,
				OBSERVACION, FabricaDominioPrueba.AHORA);

		assertEquals(EstadoReporte.RECHAZADO, decidido.getEstado());
	}

	@Test
	@DisplayName("No se puede decidir un reporte que no está EN_REVISION")
	void decidirSinRevisionFalla() {
		var reporte = reporte(MotivoReporte.SPAM, null);
		var autoridad = FabricaDominioPrueba.usuario(CodigoRol.RECTOR);

		assertThrows(KudosDominioExcepcion.class,
				() -> reporte.decidir(true, autoridad, OBSERVACION, FabricaDominioPrueba.AHORA));
	}

	@Test
	@DisplayName("La decisión exige una observación de al menos 10 caracteres")
	void decidirSinObservacionFalla() {
		var autoridad = FabricaDominioPrueba.usuario(CodigoRol.RECTOR);
		var enRevision = reporte(MotivoReporte.SPAM, null).iniciarRevision(autoridad);

		assertThrows(KudosDominioExcepcion.class,
				() -> enRevision.decidir(true, autoridad, "corta", FabricaDominioPrueba.AHORA));
	}
}
