package co.edu.uco.kudos.controlador.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.uco.kudos.controlador.AbstractRestController;
import co.edu.uco.kudos.controlador.respuesta.RespuestaOk;
import co.edu.uco.kudos.dao.factoria.DAOFactory;
import co.edu.uco.kudos.transversal.catalogo.CatalogoMensajes;

/**
 * Permite comprobar que la aplicación y la conexión a la base de datos funcionan.
 */
@RestController
@RequestMapping("/api/v1/estado")
public class EstadoControlador extends AbstractRestController {

	@GetMapping
	public ResponseEntity<RespuestaOk<String>> consultarEstado() {
		var daoFactory = DAOFactory.obtenerFactoria();
		daoFactory.cerrarConexion();
		return ResponseEntity.ok(RespuestaOk.crear(CatalogoMensajes.EstadoControlador.CONEXION_EXITOSA));
	}
}
