package co.edu.uco.kudos.dto;

import co.edu.uco.kudos.transversal.utilitarios.UtilTexto;

public final class NuevoReporteDTO {

	private String motivo;
	private String descripcion;

	public NuevoReporteDTO() {
		setMotivo(UtilTexto.VACIO);
		setDescripcion(UtilTexto.VACIO);
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = UtilTexto.getUtilTexto().quitarEspacioEnBlanco(motivo).toUpperCase();
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = UtilTexto.getUtilTexto().quitarEspacioEnBlanco(descripcion);
	}
}
