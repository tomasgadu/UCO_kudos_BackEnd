package co.edu.uco.kudos.controlador.impl;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.uco.kudos.controlador.AbstractRestController;
import co.edu.uco.kudos.controlador.respuesta.RespuestaOk;
import co.edu.uco.kudos.dto.RegistroUsuarioDTO;
import co.edu.uco.kudos.dto.UsuarioDTO;
import co.edu.uco.kudos.dto.VerificacionCodigoDTO;
import co.edu.uco.kudos.negocio.fachada.UsuarioFachada;
import co.edu.uco.kudos.negocio.fachada.impl.UsuarioFachadaImpl;
import co.edu.uco.kudos.transversal.catalogo.CatalogoMensajes;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioControlador extends AbstractRestController {

	private final UsuarioFachada fachada = new UsuarioFachadaImpl();

	@PostMapping
	public ResponseEntity<RespuestaOk<UsuarioDTO>> registrarUsuario(@RequestBody RegistroUsuarioDTO datos) {
		var registrado = fachada.registrarUsuario(datos);
		return ResponseEntity.ok(RespuestaOk.crear(CatalogoMensajes.Usuario.USUARIO_REGISTRADO, registrado));
	}

	@PostMapping("/verificaciones")
	public ResponseEntity<RespuestaOk<String>> verificarCodigo(@RequestBody VerificacionCodigoDTO datos) {
		fachada.verificarCodigo(datos);
		return ResponseEntity.ok(RespuestaOk.crear(CatalogoMensajes.Verificacion.DATO_VERIFICADO));
	}

	@PostMapping("/verificaciones/reenvio")
	public ResponseEntity<RespuestaOk<String>> reenviarCodigo(@RequestBody VerificacionCodigoDTO datos) {
		fachada.reenviarCodigo(datos);
		return ResponseEntity.ok(RespuestaOk.crear(CatalogoMensajes.Verificacion.CODIGO_REENVIADO));
	}

	@GetMapping("/yo")
	public ResponseEntity<RespuestaOk<UsuarioDTO>> consultarMiInformacion(
			@RequestHeader(name = ENCABEZADO_AUTORIZACION, required = false) String autorizacion) {
		var idUsuario = obtenerIdUsuarioSesion(autorizacion);
		return ResponseEntity.ok(RespuestaOk.crearConDato(fachada.consultarMiInformacion(idUsuario)));
	}

	@PutMapping("/yo")
	public ResponseEntity<RespuestaOk<UsuarioDTO>> actualizarMiInformacion(
			@RequestHeader(name = ENCABEZADO_AUTORIZACION, required = false) String autorizacion,
			@RequestBody UsuarioDTO datos) {
		var idUsuario = obtenerIdUsuarioSesion(autorizacion);
		var actualizado = fachada.actualizarMiInformacion(idUsuario, datos);
		return ResponseEntity.ok(RespuestaOk.crear(CatalogoMensajes.Usuario.INFORMACION_ACTUALIZADA, actualizado));
	}

	@GetMapping
	public ResponseEntity<RespuestaOk<UsuarioDTO>> consultarUsuarios(
			@RequestHeader(name = ENCABEZADO_AUTORIZACION, required = false) String autorizacion,
			@RequestParam(name = "idRol", required = false) UUID idRol) {
		var idActor = obtenerIdUsuarioSesion(autorizacion);
		return ResponseEntity.ok(RespuestaOk.crearConDatos(fachada.consultarUsuariosActivos(idActor, idRol)));
	}
}
