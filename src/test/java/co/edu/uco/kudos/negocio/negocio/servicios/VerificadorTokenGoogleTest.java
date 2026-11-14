package co.edu.uco.kudos.negocio.negocio.servicios;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import co.edu.uco.kudos.transversal.excepciones.KudosNegocioExcepcion;

class VerificadorTokenGoogleTest {

	private static final String CLIENT_ID = "123-abc.apps.googleusercontent.com";

	private String respuestaGoogle(String audiencia, String correo, String verificado) {
		return "{ \"aud\": \"" + audiencia + "\", \"email\": \"" + correo + "\", \"email_verified\": \"" + verificado
				+ "\", \"name\": \"Tomás Gallo\" }";
	}

	@Test
	@DisplayName("Un token válido de nuestra aplicación devuelve el correo en minúscula")
	void tokenValido() {
		var correo = VerificadorTokenGoogle.validarRespuesta(respuestaGoogle(CLIENT_ID, "Tomas.Gallo@gmail.com", "true"),
				CLIENT_ID);

		assertEquals("tomas.gallo@gmail.com", correo);
	}

	@Test
	@DisplayName("Un token emitido para otra aplicación se rechaza")
	void otraAudiencia() {
		var json = respuestaGoogle("otra-app.apps.googleusercontent.com", "tomas.gallo@gmail.com", "true");

		assertThrows(KudosNegocioExcepcion.class, () -> VerificadorTokenGoogle.validarRespuesta(json, CLIENT_ID));
	}

	@Test
	@DisplayName("Un correo que Google no ha verificado se rechaza")
	void correoNoVerificado() {
		var json = respuestaGoogle(CLIENT_ID, "tomas.gallo@gmail.com", "false");

		assertThrows(KudosNegocioExcepcion.class, () -> VerificadorTokenGoogle.validarRespuesta(json, CLIENT_ID));
	}

	@Test
	@DisplayName("Lee un campo del JSON de Google")
	void leerCampo() {
		assertEquals("tomas.gallo@gmail.com",
				VerificadorTokenGoogle.leerCampo(respuestaGoogle(CLIENT_ID, "tomas.gallo@gmail.com", "true"), "email"));
	}
}
