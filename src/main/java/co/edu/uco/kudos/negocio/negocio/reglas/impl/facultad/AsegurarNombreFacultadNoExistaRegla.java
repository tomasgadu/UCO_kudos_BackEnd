package co.edu.uco.kudos.negocio.negocio.reglas.impl.facultad;

import co.edu.uco.kudos.dao.factoria.DAOFactory;
import co.edu.uco.kudos.negocio.negocio.reglas.Regla;
import co.edu.uco.kudos.transversal.catalogo.CatalogoMensajes;
import co.edu.uco.kudos.transversal.excepciones.KudosNegocioExcepcion;

public final class AsegurarNombreFacultadNoExistaRegla implements Regla<Object> {

	private static final Regla<Object> INSTANCIA = new AsegurarNombreFacultadNoExistaRegla();

	private AsegurarNombreFacultadNoExistaRegla() {
	}

	public static Regla<Object> obtenerInstancia() {
		return INSTANCIA;
	}

	@Override
	public void ejecutar(Object... datos) {
		var nombre = (String) datos[0];
		var daoFactory = (DAOFactory) datos[1];

		var existentes = daoFactory.obtenerFacultadDAO().consultarTodos();
		var repetido = existentes.stream().anyMatch(facultad -> facultad.getNombre().equalsIgnoreCase(nombre));

		if (repetido) {
			throw KudosNegocioExcepcion.crear(CatalogoMensajes.Facultad.USUARIO_ERROR_NOMBRE_EXISTE);
		}
	}
}
