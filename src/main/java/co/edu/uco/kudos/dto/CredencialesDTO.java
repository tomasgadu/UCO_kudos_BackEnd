package co.edu.uco.kudos.dto;

import co.edu.uco.kudos.transversal.utilitarios.UtilTexto;

public final class CredencialesDTO {

	private String correoInstitucional;
	private String clave;

	public CredencialesDTO() {
		setCorreoInstitucional(UtilTexto.VACIO);
		setClave(UtilTexto.VACIO);
	}

	public String getCorreoInstitucional() {
		return correoInstitucional;
	}

	public void setCorreoInstitucional(String correoInstitucional) {
		this.correoInstitucional = UtilTexto.getUtilTexto().quitarEspacioEnBlanco(correoInstitucional);
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = UtilTexto.getUtilTexto().obtenerValorDefecto(clave);
	}
}
