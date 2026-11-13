package co.edu.uco.kudos.negocio.reglas;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import co.edu.uco.kudos.dominio.FabricaDominioPrueba;
import co.edu.uco.kudos.dominio.enums.CodigoRol;
import co.edu.uco.kudos.dominio.enums.Permiso;
import co.edu.uco.kudos.negocio.negocio.reglas.impl.usuario.AsegurarUsuarioTienePermisoRegla;
import co.edu.uco.kudos.transversal.excepciones.KudosNegocioExcepcion;

class AsegurarUsuarioTienePermisoReglaTest {

	private final AsegurarUsuarioTienePermisoRegla regla = (AsegurarUsuarioTienePermisoRegla) AsegurarUsuarioTienePermisoRegla
			.obtenerInstancia();

	@Test
	@DisplayName("El Rector tiene permiso para vetar")
	void rectorPuedeVetar() {
		var rector = FabricaDominioPrueba.usuario(CodigoRol.RECTOR);

		assertDoesNotThrow(() -> regla.ejecutar(rector, Permiso.VETAR));
	}

	@Test
	@DisplayName("Un estudiante no tiene permiso para gestionar categorías")
	void estudianteNoGestionaCategorias() {
		var estudiante = FabricaDominioPrueba.estudiante();

		assertThrows(KudosNegocioExcepcion.class, () -> regla.ejecutar(estudiante, Permiso.GESTIONAR_CATEGORIAS));
	}

	@Test
	@DisplayName("El Administrador no modera contenido")
	void administradorNoVeta() {
		var administrador = FabricaDominioPrueba.usuario(CodigoRol.ADMINISTRADOR);

		assertThrows(KudosNegocioExcepcion.class, () -> regla.ejecutar(administrador, Permiso.VETAR));
	}
}
