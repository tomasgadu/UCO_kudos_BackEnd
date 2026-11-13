package co.edu.uco.kudos.controlador.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.uco.kudos.controlador.AbstractRestController;
import co.edu.uco.kudos.controlador.respuesta.RespuestaOk;
import co.edu.uco.kudos.dto.FacultadDTO;
import co.edu.uco.kudos.negocio.fachada.FacultadFachada;
import co.edu.uco.kudos.negocio.fachada.impl.FacultadFachadaImpl;
import co.edu.uco.kudos.transversal.catalogo.CatalogoMensajes;

@RestController
@RequestMapping("/api/v1/facultades")
public class FacultadControlador extends AbstractRestController {

	private final FacultadFachada fachada = new FacultadFachadaImpl();

	@GetMapping
	public ResponseEntity<RespuestaOk<FacultadDTO>> consultarFacultades(
			@RequestParam(name = "estado", required = false) String estado) {
		var filtro = new FacultadDTO();
		filtro.setEstado(estado);
		return ResponseEntity.ok(RespuestaOk.crearConDatos(fachada.consultarFacultades(filtro)));
	}

	@PostMapping
	public ResponseEntity<RespuestaOk<FacultadDTO>> registrarFacultad(
			@RequestHeader(name = ENCABEZADO_AUTORIZACION, required = false) String autorizacion,
			@RequestBody FacultadDTO datos) {
		var idActor = obtenerIdUsuarioSesion(autorizacion);
		var registrada = fachada.registrarFacultad(idActor, datos);
		return ResponseEntity.ok(RespuestaOk.crear(CatalogoMensajes.Facultad.FACULTAD_REGISTRADA, registrada));
	}
}
