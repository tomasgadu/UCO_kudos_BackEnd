package co.edu.uco.kudos.negocio.negocio.reglas.impl.categoria;

import java.util.UUID;

import co.edu.uco.kudos.dao.factoria.DAOFactory;
import co.edu.uco.kudos.negocio.negocio.reglas.Regla;
import co.edu.uco.kudos.transversal.catalogo.CatalogoMensajes;
import co.edu.uco.kudos.transversal.excepciones.KudosNegocioExcepcion;

public final class AsegurarNombreCategoriaNoExistaRegla implements Regla<Object> {

	private static final Regla<Object> INSTANCIA = new AsegurarNombreCategoriaNoExistaRegla();

	private AsegurarNombreCategoriaNoExistaRegla() {
	}

	public static Regla<Object> obtenerInstancia() {
		return INSTANCIA;
	}

	@Override
	public void ejecutar(Object... datos) {
		Regla.validarCantidadDatos(datos, 3);
		var nombre = (String) datos[0];
		var idExcluido = (UUID) datos[1];
		var daoFactory = (DAOFactory) datos[2];

		var repetido = daoFactory.obtenerCategoriaDAO().consultarTodos().stream()
				.anyMatch(categoria -> categoria.getNombre().equalsIgnoreCase(nombre)
						&& !categoria.getId().equals(idExcluido));

		if (repetido) {
			throw KudosNegocioExcepcion.crear(CatalogoMensajes.Categoria.USUARIO_ERROR_NOMBRE_EXISTE);
		}
	}
}
