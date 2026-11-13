package co.edu.uco.kudos.dominio.enums;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

class CodigoRolTest {

	@ParameterizedTest(name = "{0} puede vetar")
	@EnumSource(value = CodigoRol.class, names = { "RECTOR", "COORDINADOR_ACADEMICO" })
	@DisplayName("Solo el Rector y el Coordinador Académico pueden vetar")
	void autoridadesPuedenVetar(CodigoRol rol) {
		assertTrue(rol.tienePermiso(Permiso.VETAR));
	}

	@ParameterizedTest(name = "{0} no puede vetar")
	@EnumSource(value = CodigoRol.class, names = { "ADMINISTRADOR", "COORDINADOR_FACULTAD", "COORDINADOR_PROGRAMA",
			"DOCENTE", "ESTUDIANTE" })
	@DisplayName("Los demás roles, incluido el Administrador, no pueden vetar")
	void demasNoPuedenVetar(CodigoRol rol) {
		assertFalse(rol.tienePermiso(Permiso.VETAR));
	}

	@Test
	@DisplayName("Solo el Administrador gestiona la estructura organizacional")
	void soloAdministradorGestionaEstructura() {
		assertTrue(CodigoRol.ADMINISTRADOR.tienePermiso(Permiso.GESTIONAR_ESTRUCTURA));
		assertFalse(CodigoRol.RECTOR.tienePermiso(Permiso.GESTIONAR_ESTRUCTURA));
	}

	@Test
	@DisplayName("Rector y Coordinador Académico son cargos únicos")
	void cargosUnicos() {
		assertTrue(CodigoRol.RECTOR.esCargoUnico());
		assertTrue(CodigoRol.COORDINADOR_ACADEMICO.esCargoUnico());
		assertFalse(CodigoRol.DOCENTE.esCargoUnico());
	}

	@Test
	@DisplayName("Un texto desconocido se convierte en NO_DEFINIDO")
	void desdeTextoDesconocido() {
		assertEquals(CodigoRol.ESTUDIANTE, CodigoRol.desdeTexto(" estudiante "));
		assertEquals(CodigoRol.NO_DEFINIDO, CodigoRol.desdeTexto("PRESIDENTE"));
		assertEquals(CodigoRol.NO_DEFINIDO, CodigoRol.desdeTexto(null));
	}
}
