package co.edu.uco.kudos.controlador.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.uco.kudos.controlador.AbstractRestController;
import co.edu.uco.kudos.controlador.respuesta.RespuestaOk;
import co.edu.uco.kudos.controlador.seguridad.GestorSesiones;
import co.edu.uco.kudos.dto.CredencialGoogleDTO;
import co.edu.uco.kudos.dto.CredencialesDTO;
import co.edu.uco.kudos.dto.SesionDTO;
import co.edu.uco.kudos.negocio.fachada.UsuarioFachada;
import co.edu.uco.kudos.negocio.fachada.impl.UsuarioFachadaImpl;
import co.edu.uco.kudos.transversal.catalogo.CatalogoMensajes;

@RestController
@RequestMapping("/api/v1/sesiones")
public class SesionControlador extends AbstractRestController {

	private final UsuarioFachada fachada = new UsuarioFachadaImpl();

	@PostMapping
	public ResponseEntity<RespuestaOk<SesionDTO>> iniciarSesion(@RequestBody CredencialesDTO credenciales) {
		var usuario = fachada.iniciarSesion(credenciales);
		var token = GestorSesiones.obtenerInstancia().crearSesion(usuario.getId());
		return ResponseEntity.ok(RespuestaOk.crear(CatalogoMensajes.Seguridad.SESION_INICIADA, new SesionDTO(token, usuario)));
	}

	@PostMapping("/google")
	public ResponseEntity<RespuestaOk<SesionDTO>> iniciarSesionConGoogle(@RequestBody CredencialGoogleDTO credencial) {
		var usuario = fachada.iniciarSesionConGoogle(credencial);
		var token = GestorSesiones.obtenerInstancia().crearSesion(usuario.getId());
		return ResponseEntity.ok(RespuestaOk.crear(CatalogoMensajes.Seguridad.SESION_INICIADA, new SesionDTO(token, usuario)));
	}

	@DeleteMapping
	public ResponseEntity<RespuestaOk<String>> cerrarSesion(
			@RequestHeader(name = ENCABEZADO_AUTORIZACION, required = false) String autorizacion) {
		GestorSesiones.obtenerInstancia().cerrarSesion(autorizacion);
		return ResponseEntity.ok(RespuestaOk.crear(CatalogoMensajes.Seguridad.SESION_CERRADA));
	}
}
