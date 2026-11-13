package co.edu.uco.kudos.transversal.excepciones;

import co.edu.uco.kudos.transversal.excepciones.enums.Capa;

public final class KudosDTOExcepcion extends KudosExcepcion {

	private static final long serialVersionUID = 1L;

	private KudosDTOExcepcion(String mensajeUsuario, String mensajeTecnico, Exception excepcionRaiz) {
		super(Capa.DTO, mensajeUsuario, mensajeTecnico, excepcionRaiz);
	}

	public static KudosDTOExcepcion crear(String mensajeUsuario) {
		return new KudosDTOExcepcion(mensajeUsuario, mensajeUsuario, new Exception(mensajeUsuario));
	}

	public static KudosDTOExcepcion crear(String mensajeUsuario, String mensajeTecnico) {
		return new KudosDTOExcepcion(mensajeUsuario, mensajeTecnico, new Exception(mensajeTecnico));
	}

	public static KudosDTOExcepcion crear(String mensajeUsuario, String mensajeTecnico, Exception excepcionRaiz) {
		return new KudosDTOExcepcion(mensajeUsuario, mensajeTecnico, excepcionRaiz);
	}
}
