package co.edu.uco.kudos.negocio.negocio.reglas.impl.usuario;

import co.edu.uco.kudos.dominio.UsuarioDominio;
import co.edu.uco.kudos.dominio.enums.Permiso;
import co.edu.uco.kudos.negocio.negocio.reglas.Regla;
import co.edu.uco.kudos.transversal.catalogo.CatalogoMensajes;
import co.edu.uco.kudos.transversal.excepciones.KudosNegocioExcepcion;

public final class AsegurarUsuarioTienePermisoRegla implements Regla<Object> {

	private static final Regla<Object> INSTANCIA = new AsegurarUsuarioTienePermisoRegla();

	private AsegurarUsuarioTienePermisoRegla() {
	}

	public static Regla<Object> obtenerInstancia() {
		return INSTANCIA;
	}

	@Override
	public void ejecutar(Object... datos) {
		var usuario = (UsuarioDominio) datos[0];
		var permiso = (Permiso) datos[1];

		if (!usuario.getRol().getCodigo().tienePermiso(permiso)) {
			throw KudosNegocioExcepcion.crear(CatalogoMensajes.Seguridad.USUARIO_ERROR_SIN_PERMISO);
		}
	}
}
