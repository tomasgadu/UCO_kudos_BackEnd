package co.edu.uco.kudos.negocio.negocio.impl;

import java.util.List;
import java.util.UUID;

import co.edu.uco.kudos.dao.factoria.DAOFactory;
import co.edu.uco.kudos.dominio.PublicacionDominio;
import co.edu.uco.kudos.dominio.ReplicaDominio;
import co.edu.uco.kudos.dominio.enums.TipoNotificacion;
import co.edu.uco.kudos.entidad.ReplicaEntidad;
import co.edu.uco.kudos.negocio.negocio.ReplicaNegocio;
import co.edu.uco.kudos.negocio.negocio.ensamblador.impl.KudoEnsambladorEntidad;
import co.edu.uco.kudos.negocio.negocio.ensamblador.impl.ReplicaEnsambladorEntidad;
import co.edu.uco.kudos.negocio.negocio.eventos.EventoDominio;
import co.edu.uco.kudos.negocio.negocio.eventos.PublicadorEventos;
import co.edu.uco.kudos.negocio.negocio.reglas.impl.publicacion.SanearContenidoPublicacionRegla;
import co.edu.uco.kudos.negocio.negocio.reglas.impl.usuario.ObtenerUsuarioActivoRegla;
import co.edu.uco.kudos.transversal.catalogo.CatalogoMensajes;
import co.edu.uco.kudos.transversal.excepciones.KudosNegocioExcepcion;
import co.edu.uco.kudos.transversal.utilitarios.UtilFecha;
import co.edu.uco.kudos.transversal.utilitarios.UtilUUID;

public final class ReplicaNegocioImpl implements ReplicaNegocio {

	private static final String ENTIDAD = "Replica";

	private final DAOFactory daoFactory;
	private final PublicadorEventos eventos;

	public ReplicaNegocioImpl(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
		this.eventos = new PublicadorEventos(daoFactory);
	}

	@Override
	public ReplicaDominio crearReplica(UUID idAutor, UUID idPadre, String contenido) {
		var autor = ObtenerUsuarioActivoRegla.ejecutar(idAutor, daoFactory);
		var padre = obtenerPadre(idPadre);
		var texto = SanearContenidoPublicacionRegla.ejecutar(contenido);

		var dao = daoFactory.obtenerReplicaDAO();
		var id = UtilUUID.generarUnico(candidato -> !UtilUUID.esValorDefecto(dao.consultarPorId(candidato).getId()));
		var replica = ReplicaDominio.responder(id, autor, padre, texto, UtilFecha.obtenerFechaHoraActual()).publicar();
		dao.crear(ReplicaEnsambladorEntidad.getInstancia().convertirAEntidad(replica));

		var evento = EventoDominio.de("REPLICA_CREADA", autor.getId(), ENTIDAD, replica.getId(), null,
				replica.getEstado().name());
		if (!padre.esDe(autor.getId())) {
			var mensaje = CatalogoMensajes.Replica.NOTIFICACION_REPLICA_RECIBIDA.formatted(autor.getNombreCompleto());
			evento = evento.notificando(padre.getAutor().getId(), TipoNotificacion.REPLICA_RECIBIDA, mensaje);
		}
		eventos.publicar(evento);
		return replica;
	}

	@Override
	public List<ReplicaDominio> consultarConversacion(UUID idActor, UUID idKudo) {
		var actor = ObtenerUsuarioActivoRegla.ejecutar(idActor, daoFactory);
		new KudoNegocioImpl(daoFactory).obtenerVisible(idKudo, actor);
		var filtro = new ReplicaEntidad();
		filtro.setIdKudoRaiz(idKudo);
		var replicas = ReplicaEnsambladorEntidad.getInstancia()
				.convertirADominio(daoFactory.obtenerReplicaDAO().consultarPorFiltro(filtro));
		var puedeVerVetadas = KudoNegocioImpl.puedeVerVetados(actor);
		return replicas.stream().filter(replica -> replica.esVisiblePara(puedeVerVetadas)).toList();
	}

	private PublicacionDominio obtenerPadre(UUID idPadre) {
		var kudo = daoFactory.obtenerKudoDAO().consultarPorId(idPadre);
		if (!UtilUUID.esValorDefecto(kudo.getId())) {
			return KudoEnsambladorEntidad.getInstancia().convertirADominio(kudo);
		}
		var replica = daoFactory.obtenerReplicaDAO().consultarPorId(idPadre);
		if (!UtilUUID.esValorDefecto(replica.getId())) {
			return ReplicaEnsambladorEntidad.getInstancia().convertirADominio(replica);
		}
		throw KudosNegocioExcepcion.crear(CatalogoMensajes.Replica.USUARIO_ERROR_PADRE_NO_EXISTE);
	}
}
