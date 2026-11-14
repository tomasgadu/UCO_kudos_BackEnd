package co.edu.uco.kudos.dto;

import co.edu.uco.kudos.transversal.utilitarios.UtilTexto;

public final class CredencialGoogleDTO {

	private String credencial;

	public CredencialGoogleDTO() {
		setCredencial(UtilTexto.VACIO);
	}

	public String getCredencial() {
		return credencial;
	}

	public void setCredencial(String credencial) {
		this.credencial = UtilTexto.getUtilTexto().quitarEspacioEnBlanco(credencial);
	}
}
