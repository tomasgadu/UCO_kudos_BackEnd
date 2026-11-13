package co.edu.uco.kudos.dto;

import co.edu.uco.kudos.transversal.utilitarios.UtilTexto;

public final class VerificacionCodigoDTO {

	private String correoInstitucional;
	private String tipo;
	private String codigo;

	public VerificacionCodigoDTO() {
		setCorreoInstitucional(UtilTexto.VACIO);
		setTipo(UtilTexto.VACIO);
		setCodigo(UtilTexto.VACIO);
	}

	public String getCorreoInstitucional() {
		return correoInstitucional;
	}

	public void setCorreoInstitucional(String correoInstitucional) {
		this.correoInstitucional = UtilTexto.getUtilTexto().quitarEspacioEnBlanco(correoInstitucional);
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = UtilTexto.getUtilTexto().quitarEspacioEnBlanco(tipo).toUpperCase();
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = UtilTexto.getUtilTexto().quitarEspacioEnBlanco(codigo);
	}
}
