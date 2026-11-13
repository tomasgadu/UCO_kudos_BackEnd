package co.edu.uco.kudos.controlador.impl;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.uco.kudos.controlador.AbstractRestController;
import co.edu.uco.kudos.controlador.respuesta.RespuestaOk;
import co.edu.uco.kudos.dto.DecisionDTO;
import co.edu.uco.kudos.dto.ReporteDTO;
import co.edu.uco.kudos.negocio.fachada.ReporteFachada;
import co.edu.uco.kudos.negocio.fachada.impl.ReporteFachadaImpl;
import co.edu.uco.kudos.transversal.catalogo.CatalogoMensajes;

@RestController
@RequestMapping("/api/v1/reportes")
public class ReporteControlador extends AbstractRestController {

	private final ReporteFachada fachada = new ReporteFachadaImpl();

	@GetMapping
	public ResponseEntity<RespuestaOk<ReporteDTO>> consultarReportes(
			@RequestHeader(name = ENCABEZADO_AUTORIZACION, required = false) String autorizacion,
			@RequestParam(name = "estado", required = false) String estado) {
		var idActor = obtenerIdUsuarioSesion(autorizacion);
		return ResponseEntity.ok(RespuestaOk.crearConDatos(fachada.consultarReportes(idActor, estado)));
	}

	@PostMapping("/{id}/revision")
	public ResponseEntity<RespuestaOk<ReporteDTO>> iniciarRevision(
			@RequestHeader(name = ENCABEZADO_AUTORIZACION, required = false) String autorizacion,
			@PathVariable("id") UUID id) {
		var idActor = obtenerIdUsuarioSesion(autorizacion);
		var reporte = fachada.iniciarRevision(idActor, id);
		return ResponseEntity.ok(RespuestaOk.crear(CatalogoMensajes.Reporte.REPORTE_EN_REVISION, reporte));
	}

	@PostMapping("/{id}/aceptar")
	public ResponseEntity<RespuestaOk<ReporteDTO>> aceptar(
			@RequestHeader(name = ENCABEZADO_AUTORIZACION, required = false) String autorizacion,
			@PathVariable("id") UUID id, @RequestBody(required = false) DecisionDTO decision) {
		var idActor = obtenerIdUsuarioSesion(autorizacion);
		var reporte = fachada.decidir(idActor, id, true, observacion(decision));
		return ResponseEntity.ok(RespuestaOk.crear(CatalogoMensajes.Reporte.REPORTE_ACEPTADO, reporte));
	}

	@PostMapping("/{id}/rechazar")
	public ResponseEntity<RespuestaOk<ReporteDTO>> rechazar(
			@RequestHeader(name = ENCABEZADO_AUTORIZACION, required = false) String autorizacion,
			@PathVariable("id") UUID id, @RequestBody(required = false) DecisionDTO decision) {
		var idActor = obtenerIdUsuarioSesion(autorizacion);
		var reporte = fachada.decidir(idActor, id, false, observacion(decision));
		return ResponseEntity.ok(RespuestaOk.crear(CatalogoMensajes.Reporte.REPORTE_RECHAZADO, reporte));
	}

	private static String observacion(DecisionDTO decision) {
		return decision == null ? null : decision.getObservacion();
	}
}
