package co.edu.uco.kudos.dto;

import java.util.UUID;

import co.edu.uco.kudos.transversal.utilitarios.UtilTexto;
import co.edu.uco.kudos.transversal.utilitarios.UtilUUID;

public final class NuevoKudoDTO {

	private UUID idDestinatario;
	private UUID idCategoria;
	private String contenido;

	public NuevoKudoDTO() {
		setIdDestinatario(UtilUUID.obtenerUUIDDefecto());
		setIdCategoria(UtilUUID.obtenerUUIDDefecto());
		setContenido(UtilTexto.VACIO);
	}

	public UUID getIdDestinatario() {
		return idDestinatario;
	}

	public void setIdDestinatario(UUID idDestinatario) {
		this.idDestinatario = UtilUUID.obtenerValorDefecto(idDestinatario);
	}

	public UUID getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(UUID idCategoria) {
		this.idCategoria = UtilUUID.obtenerValorDefecto(idCategoria);
	}

	public String getContenido() {
		return contenido;
	}

	public void setContenido(String contenido) {
		this.contenido = UtilTexto.getUtilTexto().obtenerValorDefecto(contenido);
	}
}
