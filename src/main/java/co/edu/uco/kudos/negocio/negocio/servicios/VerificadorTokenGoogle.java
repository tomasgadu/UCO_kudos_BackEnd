package co.edu.uco.kudos.negocio.negocio.servicios;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.regex.Pattern;

import co.edu.uco.kudos.transversal.catalogo.CatalogoMensajes;
import co.edu.uco.kudos.transversal.excepciones.KudosNegocioExcepcion;
import co.edu.uco.kudos.transversal.utilitarios.UtilTexto;

public final class VerificadorTokenGoogle {

	private static final String URL_VERIFICACION = "https://oauth2.googleapis.com/tokeninfo?id_token=";
	private static final String VARIABLE_CLIENT_ID = "KUDOS_GOOGLE_CLIENT_ID";
	private static final String VERDADERO = "true";
	private static final Duration TIEMPO_MAXIMO = Duration.ofSeconds(10);
	private static final HttpClient CLIENTE = HttpClient.newBuilder().connectTimeout(TIEMPO_MAXIMO).build();

	private VerificadorTokenGoogle() {
	}

	public static String obtenerCorreoVerificado(String credencial) {
		var clientId = UtilTexto.getUtilTexto().obtenerValorDefecto(System.getenv(VARIABLE_CLIENT_ID));
		if (UtilTexto.getUtilTexto().esVacia(clientId)) {
			throw KudosNegocioExcepcion.crear(CatalogoMensajes.Seguridad.USUARIO_ERROR_GOOGLE_NO_CONFIGURADO);
		}
		if (UtilTexto.getUtilTexto().esVacia(credencial)) {
			throw KudosNegocioExcepcion.crear(CatalogoMensajes.Seguridad.USUARIO_ERROR_GOOGLE_TOKEN_INVALIDO);
		}
		var respuesta = consultarGoogle(credencial);
		return validarRespuesta(respuesta, clientId);
	}

	static String validarRespuesta(String json, String clientId) {
		var audiencia = leerCampo(json, "aud");
		var correo = leerCampo(json, "email");
		var correoVerificado = leerCampo(json, "email_verified");

		if (!clientId.equals(audiencia) || !VERDADERO.equals(correoVerificado)
				|| UtilTexto.getUtilTexto().esVacia(correo)) {
			throw KudosNegocioExcepcion.crear(CatalogoMensajes.Seguridad.USUARIO_ERROR_GOOGLE_TOKEN_INVALIDO,
					CatalogoMensajes.Seguridad.TECNICO_ERROR_GOOGLE.formatted("audiencia o correo no válidos"));
		}
		return correo.toLowerCase();
	}

	static String leerCampo(String json, String campo) {
		var buscador = Pattern.compile("\"" + Pattern.quote(campo) + "\"\\s*:\\s*\"?([^\",}]*)\"?").matcher(
				UtilTexto.getUtilTexto().obtenerValorDefecto(json));
		return buscador.find() ? buscador.group(1).trim() : UtilTexto.VACIO;
	}

	private static String consultarGoogle(String credencial) {
		var peticion = HttpRequest
				.newBuilder(URI.create(URL_VERIFICACION + URLEncoder.encode(credencial, StandardCharsets.UTF_8)))
				.timeout(TIEMPO_MAXIMO).GET().build();
		try {
			var respuesta = CLIENTE.send(peticion, HttpResponse.BodyHandlers.ofString());
			if (respuesta.statusCode() != 200) {
				throw KudosNegocioExcepcion.crear(CatalogoMensajes.Seguridad.USUARIO_ERROR_GOOGLE_TOKEN_INVALIDO,
						CatalogoMensajes.Seguridad.TECNICO_ERROR_GOOGLE.formatted("estado " + respuesta.statusCode()));
			}
			return respuesta.body();
		} catch (IOException excepcion) {
			throw KudosNegocioExcepcion.crear(CatalogoMensajes.Seguridad.USUARIO_ERROR_GOOGLE_TOKEN_INVALIDO,
					CatalogoMensajes.Seguridad.TECNICO_ERROR_GOOGLE.formatted(excepcion.getMessage()), excepcion);
		} catch (InterruptedException excepcion) {
			Thread.currentThread().interrupt();
			throw KudosNegocioExcepcion.crear(CatalogoMensajes.Seguridad.USUARIO_ERROR_GOOGLE_TOKEN_INVALIDO,
					CatalogoMensajes.Seguridad.TECNICO_ERROR_GOOGLE.formatted(excepcion.getMessage()), excepcion);
		}
	}
}
