package co.edu.uco.kudos.dominio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import co.edu.uco.kudos.dominio.enums.EstadoCuenta;
import co.edu.uco.kudos.transversal.excepciones.KudosDominioExcepcion;

class UsuarioDominioTest {

	private UsuarioDominio registrado(boolean correoVerificado, boolean celularVerificado) {
		return FabricaDominioPrueba.estudiante().copia().estadoCuenta(EstadoCuenta.REGISTRADA)
				.correoVerificado(correoVerificado).celularVerificado(celularVerificado).build();
	}

	@Test
	@DisplayName("Aprobado y con contacto verificado → la cuenta queda ACTIVA")
	void activarCuenta() {
		assertEquals(EstadoCuenta.ACTIVA, registrado(true, true).activar(true).getEstadoCuenta());
	}

	@Test
	@DisplayName("Aprobar no es activar: sin verificar el celular no se activa")
	void activarSinCelularFalla() {
		var usuario = registrado(true, false);

		assertThrows(KudosDominioExcepcion.class, () -> usuario.activar(true));
	}

	@Test
	@DisplayName("Sin la aprobación de la solicitud no se activa")
	void activarSinAprobacionFalla() {
		var usuario = registrado(true, true);

		assertThrows(KudosDominioExcepcion.class, () -> usuario.activar(false));
	}

	@Test
	@DisplayName("Cambiar el correo exige verificarlo de nuevo y la cuenta vuelve a REGISTRADA")
	void cambiarCorreoExigeVerificacion() {
		var cambiado = FabricaDominioPrueba.estudiante().cambiarCorreo("laura.nueva@uco.edu.co");

		assertFalse(cambiado.isCorreoVerificado());
		assertEquals(EstadoCuenta.REGISTRADA, cambiado.getEstadoCuenta());
	}

	@Test
	@DisplayName("Cambiar el celular exige verificarlo de nuevo")
	void cambiarCelularExigeVerificacion() {
		var cambiado = FabricaDominioPrueba.estudiante().cambiarCelular("3109998877");

		assertFalse(cambiado.isCelularVerificado());
		assertEquals(EstadoCuenta.REGISTRADA, cambiado.getEstadoCuenta());
	}
}
