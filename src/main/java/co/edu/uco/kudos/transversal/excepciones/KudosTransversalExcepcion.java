package co.edu.uco.kudos.transversal.excepciones;

import co.edu.uco.kudos.transversal.excepciones.enums.Capa;

public final class KudosTransversalExcepcion extends KudosExcepcion {

	private static final long serialVersionUID = 1L;

	private KudosTransversalExcepcion(String mensajeUsuario, String mensajeTecnico, Exception excepcionRaiz) {
		super(Capa.TRANSVERSAL, mensajeUsuario, mensajeTecnico, excepcionRaiz);
	}

	public static KudosTransversalExcepcion crear(String mensajeUsuario) {
		return new KudosTransversalExcepcion(mensajeUsuario, mensajeUsuario, new Exception(mensajeUsuario));
	}

	public static KudosTransversalExcepcion crear(String mensajeUsuario, String mensajeTecnico) {
		return new KudosTransversalExcepcion(mensajeUsuario, mensajeTecnico, new Exception(mensajeTecnico));
	}

	public static KudosTransversalExcepcion crear(String mensajeUsuario, String mensajeTecnico, Exception excepcionRaiz) {
		return new KudosTransversalExcepcion(mensajeUsuario, mensajeTecnico, excepcionRaiz);
	}
}
