package co.edu.uco.kudos.negocio.fachada;

import java.util.function.Function;

import co.edu.uco.kudos.dao.factoria.DAOFactory;
import co.edu.uco.kudos.transversal.catalogo.CatalogoMensajes;
import co.edu.uco.kudos.transversal.excepciones.KudosExcepcion;
import co.edu.uco.kudos.transversal.excepciones.KudosFachadaExcepcion;

/**
 * Template Method: todas las fachadas repiten los mismos pasos (abrir conexión,
 * iniciar transacción, confirmar o cancelar, cerrar). Aquí se escriben una sola vez y
 * cada fachada solo aporta "qué hacer" dentro de la transacción (DRY, SRP).
 */
public abstract class FachadaBase {

	protected <T> T ejecutarEnTransaccion(String accion, Function<DAOFactory, T> operacion) {
		var daoFactory = DAOFactory.obtenerFactoria();
		try {
			daoFactory.iniciarTransaccion();
			var resultado = operacion.apply(daoFactory);
			daoFactory.confirmarTransaccion();
			return resultado;
		} catch (KudosExcepcion excepcion) {
			daoFactory.cancelarTransaccion();
			throw excepcion;
		} catch (Exception excepcion) {
			daoFactory.cancelarTransaccion();
			var mensajeUsuario = CatalogoMensajes.Fachada.USUARIO_ERROR_NO_CONTROLADO.formatted(accion);
			throw KudosFachadaExcepcion.crear(mensajeUsuario, excepcion.getMessage(), excepcion);
		} finally {
			daoFactory.cerrarConexion();
		}
	}

	protected <T> T ejecutarConsulta(String accion, Function<DAOFactory, T> consulta) {
		var daoFactory = DAOFactory.obtenerFactoria();
		try {
			return consulta.apply(daoFactory);
		} catch (KudosExcepcion excepcion) {
			throw excepcion;
		} catch (Exception excepcion) {
			var mensajeUsuario = CatalogoMensajes.Fachada.USUARIO_ERROR_NO_CONTROLADO.formatted(accion);
			throw KudosFachadaExcepcion.crear(mensajeUsuario, excepcion.getMessage(), excepcion);
		} finally {
			daoFactory.cerrarConexion();
		}
	}
}
