package co.edu.uco.kudos.controlador.impl;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.uco.kudos.controlador.AbstractRestController;
import co.edu.uco.kudos.controlador.respuesta.RespuestaOk;
import co.edu.uco.kudos.dto.CategoriaDTO;
import co.edu.uco.kudos.negocio.fachada.CategoriaFachada;
import co.edu.uco.kudos.negocio.fachada.impl.CategoriaFachadaImpl;
import co.edu.uco.kudos.transversal.catalogo.CatalogoMensajes;

@RestController
@RequestMapping("/api/v1/categorias")
public class CategoriaControlador extends AbstractRestController {

	private final CategoriaFachada fachada = new CategoriaFachadaImpl();

	@GetMapping
	public ResponseEntity<RespuestaOk<CategoriaDTO>> consultarCategorias(
			@RequestParam(name = "estado", required = false) String estado) {
		var filtro = new CategoriaDTO();
		filtro.setEstado(estado);
		return ResponseEntity.ok(RespuestaOk.crearConDatos(fachada.consultarCategorias(filtro)));
	}

	@PostMapping
	public ResponseEntity<RespuestaOk<CategoriaDTO>> crearCategoria(@RequestBody CategoriaDTO datos) {
		var creada = fachada.crearCategoria(datos);
		return ResponseEntity.ok(RespuestaOk.crear(CatalogoMensajes.Categoria.CATEGORIA_CREADA, creada));
	}

	@PutMapping("/{id}")
	public ResponseEntity<RespuestaOk<CategoriaDTO>> modificarCategoria(@PathVariable("id") UUID id,
			@RequestBody CategoriaDTO datos) {
		var modificada = fachada.modificarCategoria(id, datos);
		return ResponseEntity.ok(RespuestaOk.crear(CatalogoMensajes.Categoria.CATEGORIA_MODIFICADA, modificada));
	}

	@PatchMapping("/{id}/activar")
	public ResponseEntity<RespuestaOk<CategoriaDTO>> activarCategoria(@PathVariable("id") UUID id) {
		var activada = fachada.activarCategoria(id);
		return ResponseEntity.ok(RespuestaOk.crear(CatalogoMensajes.Categoria.CATEGORIA_ACTIVADA, activada));
	}

	@PatchMapping("/{id}/desactivar")
	public ResponseEntity<RespuestaOk<CategoriaDTO>> desactivarCategoria(@PathVariable("id") UUID id) {
		var desactivada = fachada.desactivarCategoria(id);
		return ResponseEntity.ok(RespuestaOk.crear(CatalogoMensajes.Categoria.CATEGORIA_DESACTIVADA, desactivada));
	}
}
