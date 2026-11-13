package co.edu.uco.kudos.controlador.seguridad;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import co.edu.uco.kudos.transversal.catalogo.CatalogoMensajes;
import co.edu.uco.kudos.transversal.excepciones.KudosSeguridadExcepcion;
import co.edu.uco.kudos.transversal.utilitarios.UtilTexto;
import co.edu.uco.kudos.transversal.utilitarios.UtilUUID;

public final class GestorSesiones {

	private static final GestorSesiones INSTANCIA = new GestorSesiones();
	private static final long HORAS_VIGENCIA = 8;
	private static final String PREFIJO = "Bearer ";

	private final Map<String, Sesion> sesiones = new ConcurrentHashMap<>();

	private record Sesion(UUID idUsuario, LocalDateTime vence) {
	}

	private GestorSesiones() {
	}

	public static GestorSesiones obtenerInstancia() {
		return INSTANCIA;
	}

	public String crearSesion(UUID idUsuario) {
		var token = UtilUUID.generarComoTexto();
		sesiones.put(token, new Sesion(idUsuario, LocalDateTime.now().plusHours(HORAS_VIGENCIA)));
		return token;
	}

	public void cerrarSesion(String encabezadoAutorizacion) {
		sesiones.remove(extraerToken(encabezadoAutorizacion));
	}

	public UUID obtenerIdUsuario(String encabezadoAutorizacion) {
		var token = extraerToken(encabezadoAutorizacion);
		if (UtilTexto.getUtilTexto().esVacia(token)) {
			throw KudosSeguridadExcepcion.crear(CatalogoMensajes.Seguridad.USUARIO_ERROR_SESION_REQUERIDA);
		}
		var sesion = sesiones.get(token);
		if (sesion == null || LocalDateTime.now().isAfter(sesion.vence())) {
			sesiones.remove(token);
			throw KudosSeguridadExcepcion.crear(CatalogoMensajes.Seguridad.USUARIO_ERROR_SESION_INVALIDA);
		}
		return sesion.idUsuario();
	}

	private String extraerToken(String encabezado) {
		var valor = UtilTexto.getUtilTexto().quitarEspacioEnBlanco(encabezado);
		return valor.startsWith(PREFIJO) ? valor.substring(PREFIJO.length()).trim() : valor;
	}
}
