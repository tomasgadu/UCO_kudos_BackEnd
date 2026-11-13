package co.edu.uco.kudos.controlador.impl;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.uco.kudos.controlador.AbstractRestController;
import co.edu.uco.kudos.controlador.respuesta.RespuestaOk;
import co.edu.uco.kudos.dto.NotificacionDTO;
import co.edu.uco.kudos.negocio.fachada.NotificacionFachada;
import co.edu.uco.kudos.negocio.fachada.impl.NotificacionFachadaImpl;
import co.edu.uco.kudos.transversal.catalogo.CatalogoMensajes;

@RestController
@RequestMapping("/api/v1/notificaciones")
public class NotificacionControlador extends AbstractRestController {

	private final NotificacionFachada fachada = new NotificacionFachadaImpl();

	@GetMapping
	public ResponseEntity<RespuestaOk<NotificacionDTO>> consultarMisNotificaciones(
			@RequestHeader(name = ENCABEZADO_AUTORIZACION, required = false) String autorizacion) {
		var idActor = obtenerIdUsuarioSesion(autorizacion);
		return ResponseEntity.ok(RespuestaOk.crearConDatos(fachada.consultarMisNotificaciones(idActor)));
	}

	@PatchMapping("/{id}/leida")
	public ResponseEntity<RespuestaOk<NotificacionDTO>> marcarLeida(
			@RequestHeader(name = ENCABEZADO_AUTORIZACION, required = false) String autorizacion,
			@PathVariable("id") UUID id) {
		var idActor = obtenerIdUsuarioSesion(autorizacion);
		var leida = fachada.marcarLeida(idActor, id);
		return ResponseEntity.ok(RespuestaOk.crear(CatalogoMensajes.Notificacion.NOTIFICACION_LEIDA, leida));
	}
}
