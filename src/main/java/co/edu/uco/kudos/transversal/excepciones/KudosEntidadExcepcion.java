package co.edu.uco.kudos.transversal.excepciones;

import co.edu.uco.kudos.transversal.excepciones.enums.Capa;

public final class KudosEntidadExcepcion extends KudosExcepcion {

	private static final long serialVersionUID = 1L;

	private KudosEntidadExcepcion(String mensajeUsuario, String mensajeTecnico, Exception excepcionRaiz) {
		super(Capa.ENTIDAD, mensajeUsuario, mensajeTecnico, excepcionRaiz);
	}

	public static KudosEntidadExcepcion crear(String mensajeUsuario) {
		return new KudosEntidadExcepcion(mensajeUsuario, mensajeUsuario, new Exception(mensajeUsuario));
	}

	public static KudosEntidadExcepcion crear(String mensajeUsuario, String mensajeTecnico) {
		return new KudosEntidadExcepcion(mensajeUsuario, mensajeTecnico, new Exception(mensajeTecnico));
	}

	public static KudosEntidadExcepcion crear(String mensajeUsuario, String mensajeTecnico, Exception excepcionRaiz) {
		return new KudosEntidadExcepcion(mensajeUsuario, mensajeTecnico, excepcionRaiz);
	}
}
