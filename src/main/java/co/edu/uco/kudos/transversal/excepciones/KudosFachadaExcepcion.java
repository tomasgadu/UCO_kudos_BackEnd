package co.edu.uco.kudos.transversal.excepciones;

import co.edu.uco.kudos.transversal.excepciones.enums.Capa;

public final class KudosFachadaExcepcion extends KudosExcepcion {

	private static final long serialVersionUID = 1L;

	private KudosFachadaExcepcion(String mensajeUsuario, String mensajeTecnico, Exception excepcionRaiz) {
		super(Capa.FACHADA, mensajeUsuario, mensajeTecnico, excepcionRaiz);
	}

	public static KudosFachadaExcepcion crear(String mensajeUsuario) {
		return new KudosFachadaExcepcion(mensajeUsuario, mensajeUsuario, new Exception(mensajeUsuario));
	}

	public static KudosFachadaExcepcion crear(String mensajeUsuario, String mensajeTecnico) {
		return new KudosFachadaExcepcion(mensajeUsuario, mensajeTecnico, new Exception(mensajeTecnico));
	}

	public static KudosFachadaExcepcion crear(String mensajeUsuario, String mensajeTecnico, Exception excepcionRaiz) {
		return new KudosFachadaExcepcion(mensajeUsuario, mensajeTecnico, excepcionRaiz);
	}
}
