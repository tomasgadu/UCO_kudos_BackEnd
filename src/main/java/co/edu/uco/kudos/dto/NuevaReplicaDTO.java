package co.edu.uco.kudos.dto;

import java.util.UUID;

import co.edu.uco.kudos.transversal.utilitarios.UtilTexto;
import co.edu.uco.kudos.transversal.utilitarios.UtilUUID;

public final class NuevaReplicaDTO {

	private UUID idPadre;
	private String contenido;

	public NuevaReplicaDTO() {
		setIdPadre(UtilUUID.obtenerUUIDDefecto());
		setContenido(UtilTexto.VACIO);
	}

	public UUID getIdPadre() {
		return idPadre;
	}

	public void setIdPadre(UUID idPadre) {
		this.idPadre = UtilUUID.obtenerValorDefecto(idPadre);
	}

	public String getContenido() {
		return contenido;
	}

	public void setContenido(String contenido) {
		this.contenido = UtilTexto.getUtilTexto().obtenerValorDefecto(contenido);
	}
}
