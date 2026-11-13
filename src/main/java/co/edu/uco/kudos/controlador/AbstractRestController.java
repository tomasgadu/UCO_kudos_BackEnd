package co.edu.uco.kudos.controlador;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import co.edu.uco.kudos.controlador.respuesta.RespuestaError;
import co.edu.uco.kudos.controlador.seguridad.GestorSesiones;
import co.edu.uco.kudos.transversal.catalogo.CatalogoMensajes;
import co.edu.uco.kudos.transversal.excepciones.KudosExcepcion;
import co.edu.uco.kudos.transversal.excepciones.KudosSeguridadExcepcion;
import co.edu.uco.kudos.transversal.utilitarios.UtilUUID;

@RestControllerAdvice
public abstract class AbstractRestController {

	private static final Logger logger = LoggerFactory.getLogger(AbstractRestController.class);

	public static final String ENCABEZADO_AUTORIZACION = "Authorization";

	protected UUID obtenerIdUsuarioSesion(String encabezadoAutorizacion) {
		return GestorSesiones.obtenerInstancia().obtenerIdUsuario(encabezadoAutorizacion);
	}

	@ExceptionHandler(KudosSeguridadExcepcion.class)
	public ResponseEntity<RespuestaError<String>> controlarSeguridad(KudosSeguridadExcepcion excepcion) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(RespuestaError.crear(excepcion.getMensajeUsuario()));
	}

	@ExceptionHandler(KudosExcepcion.class)
	public ResponseEntity<RespuestaError<String>> controlarKudosExcepcion(KudosExcepcion excepcion) {
		var respuesta = RespuestaError.crear(excepcion.getMensajeUsuario());

		logger.warn(CatalogoMensajes.AbstractRestController.TECNICO_ADVERTENCIA_EXCEPCION_CONTROLADA,
				excepcion.getCapa(), excepcion.getMensajeTecnico(), excepcion.getExcepcionRaiz());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
	}

	@ExceptionHandler(NoResourceFoundException.class)
	public ResponseEntity<RespuestaError<String>> controlarRecursoNoEncontrado(NoResourceFoundException excepcion) {
		var correlationId = UtilUUID.generarComoTexto();
		var respuesta = RespuestaError
				.crear(CatalogoMensajes.AbstractRestController.USUARIO_ERROR_RECURSO_NO_ENCONTRADO.concat(correlationId));

		logger.warn(CatalogoMensajes.AbstractRestController.TECNICO_ADVERTENCIA_RECURSO_NO_ENCONTRADO,
				excepcion.getHttpMethod(), excepcion.getResourcePath(), correlationId);

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<RespuestaError<String>> controlarExcepcion(Exception excepcion) {
		var correlationId = UtilUUID.generarComoTexto();
		var respuesta = RespuestaError
				.crear(CatalogoMensajes.AbstractRestController.USUARIO_ERROR_PROBLEMA_INESPERADO.concat(correlationId));

		logger.error(CatalogoMensajes.AbstractRestController.TECNICO_ERROR_PROBLEMA_INESPERADO, correlationId, excepcion);

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
	}
}
