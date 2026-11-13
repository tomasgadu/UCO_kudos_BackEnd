package co.edu.uco.kudos.dto;

import co.edu.uco.kudos.transversal.utilitarios.UtilTexto;

public final class DecisionDTO {

	private String observacion;

	public DecisionDTO() {
		setObservacion(UtilTexto.VACIO);
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = UtilTexto.getUtilTexto().quitarEspacioEnBlanco(observacion);
	}
}
