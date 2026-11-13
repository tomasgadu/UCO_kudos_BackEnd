package co.edu.uco.kudos.dominio;

import java.util.UUID;

import co.edu.uco.kudos.transversal.utilitarios.UtilTexto;
import co.edu.uco.kudos.transversal.utilitarios.UtilUUID;

public final class ParametroSistemaDominio {

	private final UUID id;
	private final String clave;
	private final String valor;
	private final String descripcion;

	private ParametroSistemaDominio(Builder builder) {
		this.id = builder.id;
		this.clave = builder.clave;
		this.valor = builder.valor;
		this.descripcion = builder.descripcion;
	}

	public UUID getId() {
		return id;
	}

	public String getClave() {
		return clave;
	}

	public String getValor() {
		return valor;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public Builder copia() {
		return new Builder().id(id).clave(clave).valor(valor).descripcion(descripcion);
	}

	public static class Builder {

		private UUID id;
		private String clave;
		private String valor;
		private String descripcion;

		public Builder() {
			id = UtilUUID.obtenerUUIDDefecto();
			clave = UtilTexto.VACIO;
			valor = UtilTexto.VACIO;
			descripcion = UtilTexto.VACIO;
		}

		public Builder id(UUID id) {
			this.id = UtilUUID.obtenerValorDefecto(id);
			return this;
		}

		public Builder clave(String clave) {
			this.clave = UtilTexto.getUtilTexto().quitarEspacioEnBlanco(clave);
			return this;
		}

		public Builder valor(String valor) {
			this.valor = UtilTexto.getUtilTexto().quitarEspacioEnBlanco(valor);
			return this;
		}

		public Builder descripcion(String descripcion) {
			this.descripcion = UtilTexto.getUtilTexto().quitarEspacioEnBlanco(descripcion);
			return this;
		}

		public ParametroSistemaDominio build() {
			return new ParametroSistemaDominio(this);
		}
	}
}
