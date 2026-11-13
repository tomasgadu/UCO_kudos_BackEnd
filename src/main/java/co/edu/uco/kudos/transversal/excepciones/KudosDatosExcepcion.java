package co.edu.uco.kudos.transversal.excepciones;

import co.edu.uco.kudos.transversal.excepciones.enums.Capa;

public final class KudosDatosExcepcion extends KudosExcepcion {

	private static final long serialVersionUID = 1L;

	private KudosDatosExcepcion(String mensajeUsuario, String mensajeTecnico, Exception excepcionRaiz) {
		super(Capa.DATOS, mensajeUsuario, mensajeTecnico, excepcionRaiz);
	}

	public static KudosDatosExcepcion crear(String mensajeUsuario) {
		return new KudosDatosExcepcion(mensajeUsuario, mensajeUsuario, new Exception(mensajeUsuario));
	}

	public static KudosDatosExcepcion crear(String mensajeUsuario, String mensajeTecnico) {
		return new KudosDatosExcepcion(mensajeUsuario, mensajeTecnico, new Exception(mensajeTecnico));
	}

	public static KudosDatosExcepcion crear(String mensajeUsuario, String mensajeTecnico, Exception excepcionRaiz) {
		return new KudosDatosExcepcion(mensajeUsuario, mensajeTecnico, excepcionRaiz);
	}
}
