package co.edu.uco.kudos.negocio.negocio.impl;

import java.util.List;
import java.util.UUID;

import co.edu.uco.kudos.dao.factoria.DAOFactory;
import co.edu.uco.kudos.dominio.CategoriaDominio;
import co.edu.uco.kudos.dominio.enums.EstadoRegistro;
import co.edu.uco.kudos.negocio.negocio.CategoriaNegocio;
import co.edu.uco.kudos.negocio.negocio.ensamblador.impl.CategoriaEnsambladorEntidad;
import co.edu.uco.kudos.negocio.negocio.reglas.impl.categoria.AsegurarNombreCategoriaNoExistaRegla;
import co.edu.uco.kudos.negocio.negocio.reglas.impl.categoria.ValidarDatosCategoriaRegla;
import co.edu.uco.kudos.transversal.catalogo.CatalogoMensajes;
import co.edu.uco.kudos.transversal.excepciones.KudosNegocioExcepcion;
import co.edu.uco.kudos.transversal.utilitarios.UtilFecha;
import co.edu.uco.kudos.transversal.utilitarios.UtilUUID;

public final class CategoriaNegocioImpl implements CategoriaNegocio {

	private final DAOFactory daoFactory;

	public CategoriaNegocioImpl(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	@Override
	public CategoriaDominio crearCategoria(CategoriaDominio datos) {
		ValidarDatosCategoriaRegla.obtenerInstancia().ejecutar(datos);
		AsegurarNombreCategoriaNoExistaRegla.obtenerInstancia().ejecutar(datos.getNombre(), UtilUUID.obtenerUUIDDefecto(),
				daoFactory);

		var dao = daoFactory.obtenerCategoriaDAO();
		var nueva = new CategoriaDominio.Builder()
				.id(UtilUUID.generarUnico(id -> !UtilUUID.esValorDefecto(dao.consultarPorId(id).getId())))
				.nombre(datos.getNombre()).descripcion(datos.getDescripcion()).estado(EstadoRegistro.ACTIVA)
				.fechaCreacion(UtilFecha.obtenerFechaHoraActual()).build();

		dao.crear(CategoriaEnsambladorEntidad.getInstancia().convertirAEntidad(nueva));
		return nueva;
	}

	@Override
	public CategoriaDominio modificarCategoria(UUID id, CategoriaDominio datos) {
		var actual = obtenerCategoria(id);
		ValidarDatosCategoriaRegla.obtenerInstancia().ejecutar(datos);
		AsegurarNombreCategoriaNoExistaRegla.obtenerInstancia().ejecutar(datos.getNombre(), id, daoFactory);

		var modificada = new CategoriaDominio.Builder().id(id).nombre(datos.getNombre())
				.descripcion(datos.getDescripcion()).estado(actual.getEstado()).fechaCreacion(actual.getFechaCreacion())
				.build();
		guardar(modificada);
		return modificada;
	}

	@Override
	public CategoriaDominio activarCategoria(UUID id) {
		var activada = obtenerCategoria(id).activar();
		guardar(activada);
		return activada;
	}

	@Override
	public CategoriaDominio desactivarCategoria(UUID id) {
		var desactivada = obtenerCategoria(id).desactivar();
		guardar(desactivada);
		return desactivada;
	}

	@Override
	public List<CategoriaDominio> consultarCategorias(CategoriaDominio filtro) {
		var filtroEntidad = CategoriaEnsambladorEntidad.getInstancia().convertirAEntidad(filtro);
		var categorias = daoFactory.obtenerCategoriaDAO().consultarPorFiltro(filtroEntidad);
		return CategoriaEnsambladorEntidad.getInstancia().convertirADominio(categorias);
	}

	private CategoriaDominio obtenerCategoria(UUID id) {
		var entidad = daoFactory.obtenerCategoriaDAO().consultarPorId(id);
		if (UtilUUID.esValorDefecto(entidad.getId())) {
			throw KudosNegocioExcepcion.crear(CatalogoMensajes.Categoria.USUARIO_ERROR_NO_EXISTE);
		}
		return CategoriaEnsambladorEntidad.getInstancia().convertirADominio(entidad);
	}

	private void guardar(CategoriaDominio categoria) {
		daoFactory.obtenerCategoriaDAO().actualizar(categoria.getId(),
				CategoriaEnsambladorEntidad.getInstancia().convertirAEntidad(categoria));
	}
}
