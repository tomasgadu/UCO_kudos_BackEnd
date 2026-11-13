package co.edu.uco.kudos.controlador.impl;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.uco.kudos.controlador.AbstractRestController;
import co.edu.uco.kudos.controlador.respuesta.RespuestaOk;
import co.edu.uco.kudos.dto.CalificacionDTO;
import co.edu.uco.kudos.dto.CalificarDTO;
import co.edu.uco.kudos.dto.JustificacionDTO;
import co.edu.uco.kudos.dto.NuevoReporteDTO;
import co.edu.uco.kudos.dto.ReporteDTO;
import co.edu.uco.kudos.dto.ResumenCalificacionDTO;
import co.edu.uco.kudos.dto.VetoDTO;
import co.edu.uco.kudos.negocio.fachada.PublicacionFachada;
import co.edu.uco.kudos.negocio.fachada.impl.PublicacionFachadaImpl;
import co.edu.uco.kudos.transversal.catalogo.CatalogoMensajes;

@RestController
@RequestMapping("/api/v1/publicaciones")
public class PublicacionControlador extends AbstractRestController {

	private final PublicacionFachada fachada = new PublicacionFachadaImpl();

	@PutMapping("/{id}/calificacion")
	public ResponseEntity<RespuestaOk<CalificacionDTO>> calificar(
			@RequestHeader(name = ENCABEZADO_AUTORIZACION, required = false) String autorizacion,
			@PathVariable("id") UUID id, @RequestBody CalificarDTO datos) {
		var idActor = obtenerIdUsuarioSesion(autorizacion);
		var calificacion = fachada.calificar(idActor, id, datos);
		return ResponseEntity.ok(RespuestaOk.crear(CatalogoMensajes.Calificacion.CALIFICACION_REGISTRADA, calificacion));
	}

	@GetMapping("/{id}/calificaciones")
	public ResponseEntity<RespuestaOk<ResumenCalificacionDTO>> consultarCalificaciones(
			@RequestHeader(name = ENCABEZADO_AUTORIZACION, required = false) String autorizacion,
			@PathVariable("id") UUID id) {
		var idActor = obtenerIdUsuarioSesion(autorizacion);
		return ResponseEntity.ok(RespuestaOk.crearConDato(fachada.consultarCalificaciones(idActor, id)));
	}

	@PostMapping("/{id}/reportes")
	public ResponseEntity<RespuestaOk<ReporteDTO>> reportar(
			@RequestHeader(name = ENCABEZADO_AUTORIZACION, required = false) String autorizacion,
			@PathVariable("id") UUID id, @RequestBody NuevoReporteDTO datos) {
		var idActor = obtenerIdUsuarioSesion(autorizacion);
		var reporte = fachada.reportar(idActor, id, datos);
		return ResponseEntity.ok(RespuestaOk.crear(CatalogoMensajes.Reporte.REPORTE_CREADO, reporte));
	}

	@PostMapping("/{id}/veto")
	public ResponseEntity<RespuestaOk<VetoDTO>> vetar(
			@RequestHeader(name = ENCABEZADO_AUTORIZACION, required = false) String autorizacion,
			@PathVariable("id") UUID id, @RequestBody JustificacionDTO datos) {
		var idActor = obtenerIdUsuarioSesion(autorizacion);
		var veto = fachada.vetar(idActor, id, datos);
		return ResponseEntity.ok(RespuestaOk.crear(CatalogoMensajes.Veto.PUBLICACION_VETADA, veto));
	}
}
