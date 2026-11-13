package co.edu.uco.kudos.negocio.negocio.reglas.impl.usuario;

import java.util.UUID;

import co.edu.uco.kudos.dao.factoria.DAOFactory;
import co.edu.uco.kudos.entidad.UsuarioEntidad;
import co.edu.uco.kudos.negocio.negocio.reglas.Regla;
import co.edu.uco.kudos.transversal.catalogo.CatalogoMensajes;
import co.edu.uco.kudos.transversal.excepciones.KudosNegocioExcepcion;

public final class AsegurarContactoNoExistaRegla implements Regla<Object> {

	private static final Regla<Object> INSTANCIA = new AsegurarContactoNoExistaRegla();

	private AsegurarContactoNoExistaRegla() {
	}

	public static Regla<Object> obtenerInstancia() {
		return INSTANCIA;
	}

	@Override
	public void ejecutar(Object... datos) {
		var correo = (String) datos[0];
		var celular = (String) datos[1];
		var idExcluido = (UUID) datos[2];
		var dao = ((DAOFactory) datos[3]).obtenerUsuarioDAO();

		var porCorreo = new UsuarioEntidad();
		porCorreo.setCorreoInstitucional(correo);
		if (dao.consultarPorFiltro(porCorreo).stream().anyMatch(u -> !u.getId().equals(idExcluido))) {
			throw KudosNegocioExcepcion.crear(CatalogoMensajes.Usuario.USUARIO_ERROR_CORREO_EXISTE);
		}

		var porCelular = new UsuarioEntidad();
		porCelular.setNumeroCelular(celular);
		if (dao.consultarPorFiltro(porCelular).stream().anyMatch(u -> !u.getId().equals(idExcluido))) {
			throw KudosNegocioExcepcion.crear(CatalogoMensajes.Usuario.USUARIO_ERROR_CELULAR_EXISTE);
		}
	}
}
