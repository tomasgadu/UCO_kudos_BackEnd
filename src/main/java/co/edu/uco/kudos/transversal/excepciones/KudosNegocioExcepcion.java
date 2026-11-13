package co.edu.uco.kudos.transversal.excepciones;

import co.edu.uco.kudos.transversal.excepciones.enums.Capa;

public final class KudosNegocioExcepcion extends KudosExcepcion {

	private static final long serialVersionUID = 1L;

	private KudosNegocioExcepcion(String mensajeUsuario, String mensajeTecnico, Exception excepcionRaiz) {
		super(Capa.NEGOCIO, mensajeUsuario, mensajeTecnico, excepcionRaiz);
	}

	public static KudosNegocioExcepcion crear(String mensajeUsuario) {
		return new KudosNegocioExcepcion(mensajeUsuario, mensajeUsuario, new Exception(mensajeUsuario));
	}

	public static KudosNegocioExcepcion crear(String mensajeUsuario, String mensajeTecnico) {
		return new KudosNegocioExcepcion(mensajeUsuario, mensajeTecnico, new Exception(mensajeTecnico));
	}

	public static KudosNegocioExcepcion crear(String mensajeUsuario, String mensajeTecnico, Exception excepcionRaiz) {
		return new KudosNegocioExcepcion(mensajeUsuario, mensajeTecnico, excepcionRaiz);
	}
}
