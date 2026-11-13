package co.edu.uco.kudos.negocio.negocio.reglas.impl.usuario;

import java.util.UUID;

import co.edu.uco.kudos.dao.factoria.DAOFactory;
import co.edu.uco.kudos.dominio.UsuarioDominio;
import co.edu.uco.kudos.negocio.negocio.ensamblador.impl.UsuarioEnsambladorEntidad;
import co.edu.uco.kudos.transversal.catalogo.CatalogoMensajes;
import co.edu.uco.kudos.transversal.excepciones.KudosNegocioExcepcion;
import co.edu.uco.kudos.transversal.utilitarios.UtilUUID;

public final class ObtenerUsuarioActivoRegla {

	private ObtenerUsuarioActivoRegla() {
	}

	public static UsuarioDominio ejecutar(UUID idUsuario, DAOFactory daoFactory) {
		var usuario = obtenerExistente(idUsuario, daoFactory);
		if (!usuario.estaActivo()) {
			throw KudosNegocioExcepcion.crear(CatalogoMensajes.Seguridad.USUARIO_ERROR_CUENTA_NO_ACTIVA);
		}
		return usuario;
	}

	public static UsuarioDominio obtenerExistente(UUID idUsuario, DAOFactory daoFactory) {
		var entidad = daoFactory.obtenerUsuarioDAO().consultarPorId(idUsuario);
		if (UtilUUID.esValorDefecto(entidad.getId())) {
			throw KudosNegocioExcepcion.crear(CatalogoMensajes.Usuario.USUARIO_ERROR_NO_EXISTE);
		}
		return UsuarioEnsambladorEntidad.getInstancia().convertirADominio(entidad);
	}
}
