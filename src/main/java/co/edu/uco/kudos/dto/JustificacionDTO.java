package co.edu.uco.kudos.dto;

import co.edu.uco.kudos.transversal.utilitarios.UtilTexto;

public final class JustificacionDTO {

	private String justificacion;

	public JustificacionDTO() {
		setJustificacion(UtilTexto.VACIO);
	}

	public String getJustificacion() {
		return justificacion;
	}

	public void setJustificacion(String justificacion) {
		this.justificacion = UtilTexto.getUtilTexto().quitarEspacioEnBlanco(justificacion);
	}
}
