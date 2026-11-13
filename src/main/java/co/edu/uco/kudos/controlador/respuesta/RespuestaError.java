package co.edu.uco.kudos.controlador.respuesta;

import java.util.List;

public class RespuestaError<D> extends Respuesta<D> {

	protected RespuestaError(List<String> mensajes, List<D> datos) {
		super(mensajes, datos, EstadoRespuesta.ERROR);
	}

	public static RespuestaError<String> crear(final String mensaje) {
		return new RespuestaError<>(List.of(mensaje), List.of());
	}

	public static <D> RespuestaError<D> crear(List<String> mensajes, List<D> datos) {
		return new RespuestaError<>(mensajes, datos);
	}
}
