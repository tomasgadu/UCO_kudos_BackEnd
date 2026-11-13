package co.edu.uco.kudos.negocio.negocio.servicios;

import java.util.UUID;

import co.edu.uco.kudos.dao.factoria.DAOFactory;
import co.edu.uco.kudos.dominio.KudoDominio;
import co.edu.uco.kudos.dominio.PublicacionDominio;
import co.edu.uco.kudos.dominio.ReplicaDominio;
import co.edu.uco.kudos.negocio.negocio.ensamblador.impl.KudoEnsambladorEntidad;
import co.edu.uco.kudos.negocio.negocio.ensamblador.impl.ReplicaEnsambladorEntidad;
import co.edu.uco.kudos.transversal.catalogo.CatalogoMensajes;
import co.edu.uco.kudos.transversal.excepciones.KudosNegocioExcepcion;
import co.edu.uco.kudos.transversal.utilitarios.UtilUUID;

public final class GestorPublicaciones {

	private final DAOFactory daoFactory;

	public GestorPublicaciones(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	public PublicacionDominio obtener(UUID idPublicacion) {
		var kudo = daoFactory.obtenerKudoDAO().consultarPorId(idPublicacion);
		if (!UtilUUID.esValorDefecto(kudo.getId())) {
			return KudoEnsambladorEntidad.getInstancia().convertirADominio(kudo);
		}
		var replica = daoFactory.obtenerReplicaDAO().consultarPorId(idPublicacion);
		if (!UtilUUID.esValorDefecto(replica.getId())) {
			return ReplicaEnsambladorEntidad.getInstancia().convertirADominio(replica);
		}
		throw KudosNegocioExcepcion.crear(CatalogoMensajes.Publicacion.USUARIO_ERROR_NO_EXISTE);
	}

	public void guardarEstado(PublicacionDominio publicacion) {
		if (publicacion instanceof KudoDominio kudo) {
			daoFactory.obtenerKudoDAO().actualizar(kudo.getId(),
					KudoEnsambladorEntidad.getInstancia().convertirAEntidad(kudo));
		} else if (publicacion instanceof ReplicaDominio replica) {
			daoFactory.obtenerReplicaDAO().actualizar(replica.getId(),
					ReplicaEnsambladorEntidad.getInstancia().convertirAEntidad(replica));
		}
	}

	public static String tipoDe(PublicacionDominio publicacion) {
		return publicacion instanceof KudoDominio ? "Kudo" : "Replica";
	}
}
