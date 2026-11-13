package co.edu.uco.kudos.controlador.impl;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.uco.kudos.controlador.AbstractRestController;
import co.edu.uco.kudos.controlador.respuesta.RespuestaOk;
import co.edu.uco.kudos.dto.DecisionDTO;
import co.edu.uco.kudos.dto.SolicitudRegistroDTO;
import co.edu.uco.kudos.negocio.fachada.SolicitudRegistroFachada;
import co.edu.uco.kudos.negocio.fachada.impl.SolicitudRegistroFachadaImpl;
import co.edu.uco.kudos.transversal.catalogo.CatalogoMensajes;

@RestController
@RequestMapping("/api/v1/solicitudes")
public class SolicitudRegistroControlador extends AbstractRestController {

	private final SolicitudRegistroFachada fachada = new SolicitudRegistroFachadaImpl();

	@GetMapping("/pendientes")
	public ResponseEntity<RespuestaOk<SolicitudRegistroDTO>> consultarPendientes(
			@RequestHeader(name = ENCABEZADO_AUTORIZACION, required = false) String autorizacion) {
		var idActor = obtenerIdUsuarioSesion(autorizacion);
		return ResponseEntity.ok(RespuestaOk.crearConDatos(fachada.consultarSolicitudesPendientes(idActor)));
	}

	@PostMapping("/{id}/aprobar")
	public ResponseEntity<RespuestaOk<SolicitudRegistroDTO>> aprobar(
			@RequestHeader(name = ENCABEZADO_AUTORIZACION, required = false) String autorizacion,
			@PathVariable("id") UUID id, @RequestBody(required = false) DecisionDTO decision) {
		var idActor = obtenerIdUsuarioSesion(autorizacion);
		var observacion = decision == null ? null : decision.getObservacion();
		var aprobada = fachada.aprobarSolicitud(idActor, id, observacion);
		return ResponseEntity.ok(RespuestaOk.crear(CatalogoMensajes.SolicitudRegistro.SOLICITUD_APROBADA, aprobada));
	}

	@PostMapping("/{id}/rechazar")
	public ResponseEntity<RespuestaOk<SolicitudRegistroDTO>> rechazar(
			@RequestHeader(name = ENCABEZADO_AUTORIZACION, required = false) String autorizacion,
			@PathVariable("id") UUID id, @RequestBody(required = false) DecisionDTO decision) {
		var idActor = obtenerIdUsuarioSesion(autorizacion);
		var observacion = decision == null ? null : decision.getObservacion();
		var rechazada = fachada.rechazarSolicitud(idActor, id, observacion);
		return ResponseEntity.ok(RespuestaOk.crear(CatalogoMensajes.SolicitudRegistro.SOLICITUD_RECHAZADA, rechazada));
	}
}
