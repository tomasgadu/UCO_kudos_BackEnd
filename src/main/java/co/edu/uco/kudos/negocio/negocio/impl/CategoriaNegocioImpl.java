package co.edu.uco.kudos.negocio.negocio.impl;

import java.util.List;
import java.util.UUID;

import co.edu.uco.kudos.dao.factoria.DAOFactory;
import co.edu.uco.kudos.dominio.CategoriaDominio;
import co.edu.uco.kudos.dominio.UsuarioDominio;
import co.edu.uco.kudos.dominio.enums.EstadoRegistro;
import co.edu.uco.kudos.dominio.enums.Permiso;
import co.edu.uco.kudos.negocio.negocio.CategoriaNegocio;
import co.edu.uco.kudos.negocio.negocio.ensamblador.impl.CategoriaEnsambladorEntidad;
import co.edu.uco.kudos.negocio.negocio.eventos.EventoDominio;
import co.edu.uco.kudos.negocio.negocio.eventos.PublicadorEventos;
import co.edu.uco.kudos.negocio.negocio.reglas.impl.categoria.AsegurarNombreCategoriaNoExistaRegla;
import co.edu.uco.kudos.negocio.negocio.reglas.impl.categoria.ValidarDatosCategoriaRegla;
import co.edu.uco.kudos.negocio.negocio.reglas.impl.usuario.AsegurarUsuarioTienePermisoRegla;
import co.edu.uco.kudos.negocio.negocio.reglas.impl.usuario.ObtenerUsuarioActivoRegla;
import co.edu.uco.kudos.transversal.catalogo.CatalogoMensajes;
import co.edu.uco.kudos.transversal.excepciones.KudosNegocioExcepcion;
import co.edu.uco.kudos.transversal.utilitarios.UtilFecha;
import co.edu.uco.kudos.transversal.utilitarios.UtilUUID;

public final class CategoriaNegocioImpl implements CategoriaNegocio {

	private static final String ENTIDAD = "Categoria";

	private final DAOFactory daoFactory;
	private final PublicadorEventos eventos;

	public CategoriaNegocioImpl(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
		this.eventos = new PublicadorEventos(daoFactory);
	}

	@Override
	public CategoriaDominio crearCategoria(UUID idActor, CategoriaDominio datos) {
		var actor = autorizar(idActor);
		ValidarDatosCategoriaRegla.obtenerInstancia().ejecutar(datos);
		AsegurarNombreCategoriaNoExistaRegla.obtenerInstancia().ejecutar(datos.getNombre(), UtilUUID.obtenerUUIDDefecto(),
				daoFactory);

		var dao = daoFactory.obtenerCategoriaDAO();
		var nueva = new CategoriaDominio.Builder()
				.id(UtilUUID.generarUnico(id -> !UtilUUID.esValorDefecto(dao.consultarPorId(id).getId())))
				.nombre(datos.getNombre()).descripcion(datos.getDescripcion()).estado(EstadoRegistro.ACTIVA)
				.fechaCreacion(UtilFecha.obtenerFechaHoraActual()).build();

		dao.crear(CategoriaEnsambladorEntidad.getInstancia().convertirAEntidad(nueva));
		eventos.publicar(EventoDominio.de("CATEGORIA_CREADA", actor.getId(), ENTIDAD, nueva.getId(), null, nueva.getNombre()));
		return nueva;
	}

	@Override
	public CategoriaDominio modificarCategoria(UUID idActor, UUID id, CategoriaDominio datos) {
		var actor = autorizar(idActor);
		var actual = obtenerCategoria(id);
		ValidarDatosCategoriaRegla.obtenerInstancia().ejecutar(datos);
		AsegurarNombreCategoriaNoExistaRegla.obtenerInstancia().ejecutar(datos.getNombre(), id, daoFactory);

		var modificada = new CategoriaDominio.Builder().id(id).nombre(datos.getNombre())
				.descripcion(datos.getDescripcion()).estado(actual.getEstado()).fechaCreacion(actual.getFechaCreacion())
				.build();
		guardar(modificada);
		eventos.publicar(EventoDominio.de("CATEGORIA_MODIFICADA", actor.getId(), ENTIDAD, id, actual.getNombre(),
				modificada.getNombre()));
		return modificada;
	}

	@Override
	public CategoriaDominio activarCategoria(UUID idActor, UUID id) {
		var actor = autorizar(idActor);
		var actual = obtenerCategoria(id);
		var activada = actual.activar();
		guardar(activada);
		eventos.publicar(EventoDominio.de("CATEGORIA_ACTIVADA", actor.getId(), ENTIDAD, id, actual.getEstado().name(),
				activada.getEstado().name()));
		return activada;
	}

	@Override
	public CategoriaDominio desactivarCategoria(UUID idActor, UUID id) {
		var actor = autorizar(idActor);
		var actual = obtenerCategoria(id);
		var desactivada = actual.desactivar();
		guardar(desactivada);
		eventos.publicar(EventoDominio.de("CATEGORIA_INACTIVADA", actor.getId(), ENTIDAD, id, actual.getEstado().name(),
				desactivada.getEstado().name()));
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

	private UsuarioDominio autorizar(UUID idActor) {
		var actor = ObtenerUsuarioActivoRegla.ejecutar(idActor, daoFactory);
		AsegurarUsuarioTienePermisoRegla.obtenerInstancia().ejecutar(actor, Permiso.GESTIONAR_CATEGORIAS);
		return actor;
	}
}
