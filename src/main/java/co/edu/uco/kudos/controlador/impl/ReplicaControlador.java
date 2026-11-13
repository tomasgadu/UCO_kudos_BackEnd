package co.edu.uco.kudos.controlador.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.uco.kudos.controlador.AbstractRestController;
import co.edu.uco.kudos.controlador.respuesta.RespuestaOk;
import co.edu.uco.kudos.dto.NuevaReplicaDTO;
import co.edu.uco.kudos.dto.ReplicaDTO;
import co.edu.uco.kudos.negocio.fachada.KudoFachada;
import co.edu.uco.kudos.negocio.fachada.impl.KudoFachadaImpl;
import co.edu.uco.kudos.transversal.catalogo.CatalogoMensajes;

@RestController
@RequestMapping("/api/v1/replicas")
public class ReplicaControlador extends AbstractRestController {

	private final KudoFachada fachada = new KudoFachadaImpl();

	@PostMapping
	public ResponseEntity<RespuestaOk<ReplicaDTO>> crearReplica(
			@RequestHeader(name = ENCABEZADO_AUTORIZACION, required = false) String autorizacion,
			@RequestBody NuevaReplicaDTO datos) {
		var idAutor = obtenerIdUsuarioSesion(autorizacion);
		var creada = fachada.crearReplica(idAutor, datos);
		return ResponseEntity.ok(RespuestaOk.crear(CatalogoMensajes.Replica.REPLICA_CREADA, creada));
	}
}
