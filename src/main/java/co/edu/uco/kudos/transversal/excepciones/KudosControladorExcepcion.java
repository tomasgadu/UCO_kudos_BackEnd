package co.edu.uco.kudos.transversal.excepciones;

import co.edu.uco.kudos.transversal.excepciones.enums.Capa;

public final class KudosControladorExcepcion extends KudosExcepcion {

	private static final long serialVersionUID = 1L;

	private KudosControladorExcepcion(String mensajeUsuario, String mensajeTecnico, Exception excepcionRaiz) {
		super(Capa.CONTROLADOR, mensajeUsuario, mensajeTecnico, excepcionRaiz);
	}

	public static KudosControladorExcepcion crear(String mensajeUsuario) {
		return new KudosControladorExcepcion(mensajeUsuario, mensajeUsuario, new Exception(mensajeUsuario));
	}

	public static KudosControladorExcepcion crear(String mensajeUsuario, String mensajeTecnico) {
		return new KudosControladorExcepcion(mensajeUsuario, mensajeTecnico, new Exception(mensajeTecnico));
	}

	public static KudosControladorExcepcion crear(String mensajeUsuario, String mensajeTecnico, Exception excepcionRaiz) {
		return new KudosControladorExcepcion(mensajeUsuario, mensajeTecnico, excepcionRaiz);
	}
}
