package co.edu.uco.kudos.controlador.respuesta;

import java.util.List;

import co.edu.uco.kudos.transversal.utilitarios.UtilTexto;

public class RespuestaOk<D> extends Respuesta<D> {

	protected RespuestaOk(List<String> mensajes, List<D> datos) {
		super(mensajes, datos, EstadoRespuesta.OK);
	}

	public static <D> RespuestaOk<D> crear(List<String> mensajes, List<D> datos) {
		return new RespuestaOk<>(mensajes, datos);
	}

	public static <D> RespuestaOk<D> crear(String mensaje) {
		var mensajeFormateado = UtilTexto.getUtilTexto().quitarEspacioEnBlanco(mensaje);
		return new RespuestaOk<>(List.of(mensajeFormateado), List.of());
	}

	public static <D> RespuestaOk<D> crear(String mensaje, D dato) {
		var mensajeFormateado = UtilTexto.getUtilTexto().quitarEspacioEnBlanco(mensaje);
		return new RespuestaOk<>(List.of(mensajeFormateado), List.of(dato));
	}

	public static <D> RespuestaOk<D> crearConDatos(List<D> datos) {
		return new RespuestaOk<>(List.of(), datos);
	}

	public static <D> RespuestaOk<D> crearConDato(D dato) {
		return new RespuestaOk<>(List.of(), List.of(dato));
	}
}
