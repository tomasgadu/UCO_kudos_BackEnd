package co.edu.uco.kudos.controlador.impl;

import java.util.UUID;

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
import co.edu.uco.kudos.dto.ProgramaAcademicoDTO;
import co.edu.uco.kudos.negocio.fachada.ProgramaAcademicoFachada;
import co.edu.uco.kudos.negocio.fachada.impl.ProgramaAcademicoFachadaImpl;
import co.edu.uco.kudos.transversal.catalogo.CatalogoMensajes;

@RestController
@RequestMapping("/api/v1/programas")
public class ProgramaAcademicoControlador extends AbstractRestController {

	private final ProgramaAcademicoFachada fachada = new ProgramaAcademicoFachadaImpl();

	@GetMapping
	public ResponseEntity<RespuestaOk<ProgramaAcademicoDTO>> consultarProgramas(
			@RequestParam(name = "idFacultad", required = false) UUID idFacultad,
			@RequestParam(name = "estado", required = false) String estado) {
		var filtro = new ProgramaAcademicoDTO();
		filtro.getFacultad().setId(idFacultad);
		filtro.setEstado(estado);
		return ResponseEntity.ok(RespuestaOk.crearConDatos(fachada.consultarProgramasAcademicos(filtro)));
	}

	@PostMapping
	public ResponseEntity<RespuestaOk<ProgramaAcademicoDTO>> registrarPrograma(
			@RequestHeader(name = ENCABEZADO_AUTORIZACION, required = false) String autorizacion,
			@RequestBody ProgramaAcademicoDTO datos) {
		var idActor = obtenerIdUsuarioSesion(autorizacion);
		var registrado = fachada.registrarProgramaAcademico(idActor, datos);
		return ResponseEntity.ok(RespuestaOk.crear(CatalogoMensajes.ProgramaAcademico.PROGRAMA_REGISTRADO, registrado));
	}
}
