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
import co.edu.uco.kudos.dto.KudoDTO;
import co.edu.uco.kudos.dto.NuevoKudoDTO;
import co.edu.uco.kudos.dto.ReplicaDTO;
import co.edu.uco.kudos.negocio.fachada.KudoFachada;
import co.edu.uco.kudos.negocio.fachada.impl.KudoFachadaImpl;
import co.edu.uco.kudos.transversal.catalogo.CatalogoMensajes;

@RestController
@RequestMapping("/api/v1/kudos")
public class KudoControlador extends AbstractRestController {

	private final KudoFachada fachada = new KudoFachadaImpl();

	@PostMapping
	public ResponseEntity<RespuestaOk<KudoDTO>> crearKudo(
			@RequestHeader(name = ENCABEZADO_AUTORIZACION, required = false) String autorizacion,
			@RequestBody NuevoKudoDTO datos) {
		var idAutor = obtenerIdUsuarioSesion(autorizacion);
		var creado = fachada.crearKudo(idAutor, datos);
		return ResponseEntity.ok(RespuestaOk.crear(CatalogoMensajes.Kudo.KUDO_CREADO, creado));
	}

	@GetMapping
	public ResponseEntity<RespuestaOk<KudoDTO>> consultarMuro(
			@RequestHeader(name = ENCABEZADO_AUTORIZACION, required = false) String autorizacion,
			@RequestParam(name = "idDestinatario", required = false) UUID idDestinatario) {
		var idActor = obtenerIdUsuarioSesion(autorizacion);
		return ResponseEntity.ok(RespuestaOk.crearConDatos(fachada.consultarMuro(idActor, idDestinatario)));
	}

	@GetMapping("/{id}")
	public ResponseEntity<RespuestaOk<KudoDTO>> consultarKudo(
			@RequestHeader(name = ENCABEZADO_AUTORIZACION, required = false) String autorizacion,
			@PathVariable("id") UUID id) {
		var idActor = obtenerIdUsuarioSesion(autorizacion);
		return ResponseEntity.ok(RespuestaOk.crearConDato(fachada.consultarKudo(idActor, id)));
	}

	@GetMapping("/{id}/replicas")
	public ResponseEntity<RespuestaOk<ReplicaDTO>> consultarConversacion(
			@RequestHeader(name = ENCABEZADO_AUTORIZACION, required = false) String autorizacion,
			@PathVariable("id") UUID id) {
		var idActor = obtenerIdUsuarioSesion(autorizacion);
		return ResponseEntity.ok(RespuestaOk.crearConDatos(fachada.consultarConversacion(idActor, id)));
	}
}
