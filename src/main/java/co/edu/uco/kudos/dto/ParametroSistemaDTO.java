package co.edu.uco.kudos.dto;

import java.util.UUID;

import co.edu.uco.kudos.transversal.utilitarios.UtilTexto;
import co.edu.uco.kudos.transversal.utilitarios.UtilUUID;

public final class ParametroSistemaDTO {

	private UUID id;
	private String clave;
	private String valor;
	private String descripcion;

	public ParametroSistemaDTO() {
		setId(UtilUUID.obtenerUUIDDefecto());
		setClave(UtilTexto.VACIO);
		setValor(UtilTexto.VACIO);
		setDescripcion(UtilTexto.VACIO);
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = UtilUUID.obtenerValorDefecto(id);
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = UtilTexto.getUtilTexto().quitarEspacioEnBlanco(clave);
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = UtilTexto.getUtilTexto().quitarEspacioEnBlanco(valor);
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = UtilTexto.getUtilTexto().quitarEspacioEnBlanco(descripcion);
	}
}
