package co.edu.uco.kudos.negocio.negocio.impl;

import java.util.UUID;

import co.edu.uco.kudos.dao.factoria.DAOFactory;
import co.edu.uco.kudos.dominio.CalificacionDominio;
import co.edu.uco.kudos.dominio.ResumenCalificacion;
import co.edu.uco.kudos.entidad.CalificacionEntidad;
import co.edu.uco.kudos.negocio.negocio.CalificacionNegocio;
import co.edu.uco.kudos.negocio.negocio.ensamblador.impl.CalificacionEnsambladorEntidad;
import co.edu.uco.kudos.negocio.negocio.eventos.EventoDominio;
import co.edu.uco.kudos.negocio.negocio.eventos.PublicadorEventos;
import co.edu.uco.kudos.negocio.negocio.reglas.impl.usuario.ObtenerUsuarioActivoRegla;
import co.edu.uco.kudos.negocio.negocio.servicios.GestorPublicaciones;
import co.edu.uco.kudos.transversal.catalogo.CatalogoMensajes;
import co.edu.uco.kudos.transversal.excepciones.KudosNegocioExcepcion;
import co.edu.uco.kudos.transversal.utilitarios.UtilFecha;
import co.edu.uco.kudos.transversal.utilitarios.UtilUUID;

public final class CalificacionNegocioImpl implements CalificacionNegocio {

	private static final String ENTIDAD = "Calificacion";

	private final DAOFactory daoFactory;
	private final GestorPublicaciones publicaciones;
	private final PublicadorEventos eventos;

	public CalificacionNegocioImpl(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
		this.publicaciones = new GestorPublicaciones(daoFactory);
		this.eventos = new PublicadorEventos(daoFactory);
	}

	@Override
	public CalificacionDominio calificar(UUID idActor, UUID idPublicacion, int estrellas) {
		var actor = ObtenerUsuarioActivoRegla.ejecutar(idActor, daoFactory);
		var publicacion = publicaciones.obtener(idPublicacion);
		publicacion.asegurarNoVetada();

		var dao = daoFactory.obtenerCalificacionDAO();
		var ahora = UtilFecha.obtenerFechaHoraActual();
		var existente = buscarDelUsuario(idPublicacion, actor.getId());

		if (existente == null) {
			var id = UtilUUID.generarUnico(candidato -> !UtilUUID.esValorDefecto(dao.consultarPorId(candidato).getId()));
			var nueva = CalificacionDominio.crear(id, idPublicacion, actor, estrellas, ahora);
			dao.crear(CalificacionEnsambladorEntidad.getInstancia().convertirAEntidad(nueva));
			eventos.publicar(EventoDominio.de("CALIFICACION_REGISTRADA", actor.getId(), ENTIDAD, nueva.getId(), null,
					String.valueOf(estrellas)));
			return nueva;
		}

		var modificada = existente.cambiar(estrellas, ahora);
		dao.actualizar(modificada.getId(), CalificacionEnsambladorEntidad.getInstancia().convertirAEntidad(modificada));
		eventos.publicar(EventoDominio.de("CALIFICACION_MODIFICADA", actor.getId(), ENTIDAD, modificada.getId(),
				String.valueOf(existente.getEstrellas()), String.valueOf(estrellas)));
		return modificada;
	}

	@Override
	public ResumenCalificacion consultarResumen(UUID idActor, UUID idPublicacion) {
		var actor = ObtenerUsuarioActivoRegla.ejecutar(idActor, daoFactory);
		var publicacion = publicaciones.obtener(idPublicacion);
		if (!publicacion.esVisiblePara(KudoNegocioImpl.puedeVerVetados(actor))) {
			throw KudosNegocioExcepcion.crear(CatalogoMensajes.Publicacion.USUARIO_ERROR_NO_EXISTE);
		}
		var filtro = new CalificacionEntidad();
		filtro.setIdPublicacion(idPublicacion);
		var calificaciones = CalificacionEnsambladorEntidad.getInstancia()
				.convertirADominio(daoFactory.obtenerCalificacionDAO().consultarPorFiltro(filtro));
		return ResumenCalificacion.de(calificaciones, actor.getId());
	}

	private CalificacionDominio buscarDelUsuario(UUID idPublicacion, UUID idUsuario) {
		var filtro = new CalificacionEntidad();
		filtro.setIdPublicacion(idPublicacion);
		filtro.getAutor().setId(idUsuario);
		var encontradas = daoFactory.obtenerCalificacionDAO().consultarPorFiltro(filtro);
		return encontradas.isEmpty() ? null
				: CalificacionEnsambladorEntidad.getInstancia().convertirADominio(encontradas.get(0));
	}
}
