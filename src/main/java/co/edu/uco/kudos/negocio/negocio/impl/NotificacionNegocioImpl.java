package co.edu.uco.kudos.negocio.negocio.impl;

import java.util.List;
import java.util.UUID;

import co.edu.uco.kudos.dao.factoria.DAOFactory;
import co.edu.uco.kudos.dominio.NotificacionDominio;
import co.edu.uco.kudos.entidad.NotificacionEntidad;
import co.edu.uco.kudos.negocio.negocio.NotificacionNegocio;
import co.edu.uco.kudos.negocio.negocio.ensamblador.impl.NotificacionEnsambladorEntidad;
import co.edu.uco.kudos.negocio.negocio.reglas.impl.usuario.ObtenerUsuarioActivoRegla;
import co.edu.uco.kudos.transversal.catalogo.CatalogoMensajes;
import co.edu.uco.kudos.transversal.excepciones.KudosNegocioExcepcion;
import co.edu.uco.kudos.transversal.utilitarios.UtilUUID;

public final class NotificacionNegocioImpl implements NotificacionNegocio {

	private final DAOFactory daoFactory;

	public NotificacionNegocioImpl(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	@Override
	public List<NotificacionDominio> consultarMisNotificaciones(UUID idActor) {
		var actor = ObtenerUsuarioActivoRegla.ejecutar(idActor, daoFactory);
		var filtro = new NotificacionEntidad();
		filtro.getDestinatario().setId(actor.getId());
		return NotificacionEnsambladorEntidad.getInstancia()
				.convertirADominio(daoFactory.obtenerNotificacionDAO().consultarPorFiltro(filtro));
	}

	@Override
	public NotificacionDominio marcarLeida(UUID idActor, UUID idNotificacion) {
		var actor = ObtenerUsuarioActivoRegla.ejecutar(idActor, daoFactory);
		var entidad = daoFactory.obtenerNotificacionDAO().consultarPorId(idNotificacion);
		var notificacion = NotificacionEnsambladorEntidad.getInstancia().convertirADominio(entidad);
		if (UtilUUID.esValorDefecto(entidad.getId()) || !notificacion.getDestinatario().getId().equals(actor.getId())) {
			throw KudosNegocioExcepcion.crear(CatalogoMensajes.Notificacion.USUARIO_ERROR_NO_EXISTE);
		}
		var leida = notificacion.copia().leida(true).build();
		daoFactory.obtenerNotificacionDAO().actualizar(leida.getId(),
				NotificacionEnsambladorEntidad.getInstancia().convertirAEntidad(leida));
		return leida;
	}
}
