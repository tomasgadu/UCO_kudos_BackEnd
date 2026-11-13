package co.edu.uco.kudos.transversal.excepciones;

import co.edu.uco.kudos.transversal.excepciones.enums.Capa;

public final class KudosSeguridadExcepcion extends KudosExcepcion {

	private static final long serialVersionUID = 1L;

	private KudosSeguridadExcepcion(String mensajeUsuario) {
		super(Capa.CONTROLADOR, mensajeUsuario, mensajeUsuario, new Exception(mensajeUsuario));
	}

	public static KudosSeguridadExcepcion crear(String mensajeUsuario) {
		return new KudosSeguridadExcepcion(mensajeUsuario);
	}
}
