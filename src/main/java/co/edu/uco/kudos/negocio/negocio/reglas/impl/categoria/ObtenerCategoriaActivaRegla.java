package co.edu.uco.kudos.negocio.negocio.reglas.impl.categoria;

import java.util.UUID;

import co.edu.uco.kudos.dao.factoria.DAOFactory;
import co.edu.uco.kudos.dominio.CategoriaDominio;
import co.edu.uco.kudos.negocio.negocio.ensamblador.impl.CategoriaEnsambladorEntidad;
import co.edu.uco.kudos.transversal.catalogo.CatalogoMensajes;
import co.edu.uco.kudos.transversal.excepciones.KudosNegocioExcepcion;
import co.edu.uco.kudos.transversal.utilitarios.UtilUUID;

public final class ObtenerCategoriaActivaRegla {

	private ObtenerCategoriaActivaRegla() {
	}

	public static CategoriaDominio ejecutar(UUID idCategoria, DAOFactory daoFactory) {
		var entidad = daoFactory.obtenerCategoriaDAO().consultarPorId(idCategoria);
		if (UtilUUID.esValorDefecto(entidad.getId())) {
			throw KudosNegocioExcepcion.crear(CatalogoMensajes.Categoria.USUARIO_ERROR_NO_EXISTE);
		}
		var categoria = CategoriaEnsambladorEntidad.getInstancia().convertirADominio(entidad);
		if (!categoria.estaActiva()) {
			throw KudosNegocioExcepcion.crear(CatalogoMensajes.Categoria.USUARIO_ERROR_INACTIVA);
		}
		return categoria;
	}
}
