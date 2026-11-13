package co.edu.uco.kudos.dto;

import co.edu.uco.kudos.transversal.utilitarios.UtilObjeto;
import co.edu.uco.kudos.transversal.utilitarios.UtilTexto;

public final class SesionDTO {

	private String token;
	private UsuarioDTO usuario;

	public SesionDTO() {
		setToken(UtilTexto.VACIO);
		setUsuario(new UsuarioDTO());
	}

	public SesionDTO(String token, UsuarioDTO usuario) {
		setToken(token);
		setUsuario(usuario);
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = UtilTexto.getUtilTexto().quitarEspacioEnBlanco(token);
	}

	public UsuarioDTO getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioDTO usuario) {
		this.usuario = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(usuario, new UsuarioDTO());
	}
}
