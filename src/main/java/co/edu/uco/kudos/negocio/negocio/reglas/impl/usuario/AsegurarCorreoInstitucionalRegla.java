package co.edu.uco.kudos.negocio.negocio.reglas.impl.usuario;

import co.edu.uco.kudos.dao.factoria.DAOFactory;
import co.edu.uco.kudos.entidad.ParametroSistemaEntidad;
import co.edu.uco.kudos.negocio.negocio.reglas.Regla;
import co.edu.uco.kudos.transversal.catalogo.CatalogoMensajes;
import co.edu.uco.kudos.transversal.excepciones.KudosNegocioExcepcion;
import co.edu.uco.kudos.transversal.utilitarios.UtilTexto;

public final class AsegurarCorreoInstitucionalRegla implements Regla<Object> {

	private static final Regla<Object> INSTANCIA = new AsegurarCorreoInstitucionalRegla();
	private static final String PARAMETRO = "DOMINIO_CORREO_INSTITUCIONAL";
	private static final String DOMINIO_DEFECTO = "uco.edu.co";

	private AsegurarCorreoInstitucionalRegla() {
	}

	public static Regla<Object> obtenerInstancia() {
		return INSTANCIA;
	}

	@Override
	public void ejecutar(Object... datos) {
		var correo = ((String) datos[0]).toLowerCase();
		var daoFactory = (DAOFactory) datos[1];

		var filtro = new ParametroSistemaEntidad();
		filtro.setClave(PARAMETRO);
		var parametros = daoFactory.obtenerParametroSistemaDAO().consultarPorFiltro(filtro);
		var dominio = parametros.isEmpty() ? DOMINIO_DEFECTO : parametros.get(0).getValor().toLowerCase();

		if (!UtilTexto.getUtilTexto().correoPerteneceADominios(correo, dominio)) {
			throw KudosNegocioExcepcion
					.crear(CatalogoMensajes.Usuario.USUARIO_ERROR_CORREO_NO_INSTITUCIONAL.formatted(dominio));
		}
	}
}
