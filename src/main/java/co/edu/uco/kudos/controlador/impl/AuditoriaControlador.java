package co.edu.uco.kudos.controlador.impl;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.uco.kudos.controlador.AbstractRestController;
import co.edu.uco.kudos.controlador.respuesta.RespuestaOk;
import co.edu.uco.kudos.dto.RegistroAuditoriaDTO;
import co.edu.uco.kudos.negocio.fachada.NotificacionFachada;
import co.edu.uco.kudos.negocio.fachada.impl.NotificacionFachadaImpl;

@RestController
@RequestMapping("/api/v1/auditoria")
public class AuditoriaControlador extends AbstractRestController {

	private final NotificacionFachada fachada = new NotificacionFachadaImpl();

	@GetMapping
	public ResponseEntity<RespuestaOk<RegistroAuditoriaDTO>> consultarAuditoria(
			@RequestHeader(name = ENCABEZADO_AUTORIZACION, required = false) String autorizacion,
			@RequestParam(name = "accion", required = false) String accion,
			@RequestParam(name = "entidad", required = false) String entidad,
			@RequestParam(name = "idEntidad", required = false) UUID idEntidad) {
		var idActor = obtenerIdUsuarioSesion(autorizacion);
		return ResponseEntity.ok(RespuestaOk.crearConDatos(fachada.consultarAuditoria(idActor, accion, entidad, idEntidad)));
	}
}
