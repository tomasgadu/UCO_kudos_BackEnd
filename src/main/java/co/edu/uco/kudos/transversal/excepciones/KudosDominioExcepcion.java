package co.edu.uco.kudos.transversal.excepciones;

import co.edu.uco.kudos.transversal.excepciones.enums.Capa;

public final class KudosDominioExcepcion extends KudosExcepcion {

	private static final long serialVersionUID = 1L;

	private KudosDominioExcepcion(String mensajeUsuario, String mensajeTecnico, Exception excepcionRaiz) {
		super(Capa.DOMINIO, mensajeUsuario, mensajeTecnico, excepcionRaiz);
	}

	public static KudosDominioExcepcion crear(String mensajeUsuario) {
		return new KudosDominioExcepcion(mensajeUsuario, mensajeUsuario, new Exception(mensajeUsuario));
	}

	public static KudosDominioExcepcion crear(String mensajeUsuario, String mensajeTecnico) {
		return new KudosDominioExcepcion(mensajeUsuario, mensajeTecnico, new Exception(mensajeTecnico));
	}

	public static KudosDominioExcepcion crear(String mensajeUsuario, String mensajeTecnico, Exception excepcionRaiz) {
		return new KudosDominioExcepcion(mensajeUsuario, mensajeTecnico, excepcionRaiz);
	}
}
