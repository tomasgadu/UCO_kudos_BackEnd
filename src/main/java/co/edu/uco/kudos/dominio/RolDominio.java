package co.edu.uco.kudos.dominio;

import java.util.UUID;

import co.edu.uco.kudos.dominio.enums.CodigoRol;
import co.edu.uco.kudos.transversal.utilitarios.UtilObjeto;
import co.edu.uco.kudos.transversal.utilitarios.UtilTexto;
import co.edu.uco.kudos.transversal.utilitarios.UtilUUID;

public final class RolDominio {

	private final UUID id;
	private final CodigoRol codigo;
	private final String nombre;
	private final String descripcion;
	private final UUID idRolAprobador;

	private RolDominio(Builder builder) {
		this.id = builder.id;
		this.codigo = builder.codigo;
		this.nombre = builder.nombre;
		this.descripcion = builder.descripcion;
		this.idRolAprobador = builder.idRolAprobador;
	}

	public UUID getId() {
		return id;
	}

	public CodigoRol getCodigo() {
		return codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public UUID getIdRolAprobador() {
		return idRolAprobador;
	}

	public boolean apruebaA(RolDominio rolSolicitado) {
		return id.equals(rolSolicitado.getIdRolAprobador());
	}

	public Builder copia() {
		return new Builder().id(id).codigo(codigo).nombre(nombre).descripcion(descripcion).idRolAprobador(idRolAprobador);
	}

	public static class Builder {

		private UUID id;
		private CodigoRol codigo;
		private String nombre;
		private String descripcion;
		private UUID idRolAprobador;

		public Builder() {
			id = UtilUUID.obtenerUUIDDefecto();
			codigo = CodigoRol.NO_DEFINIDO;
			nombre = UtilTexto.VACIO;
			descripcion = UtilTexto.VACIO;
			idRolAprobador = UtilUUID.obtenerUUIDDefecto();
		}

		public Builder id(UUID id) {
			this.id = UtilUUID.obtenerValorDefecto(id);
			return this;
		}

		public Builder codigo(CodigoRol codigo) {
			this.codigo = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(codigo, CodigoRol.NO_DEFINIDO);
			return this;
		}

		public Builder nombre(String nombre) {
			this.nombre = UtilTexto.getUtilTexto().quitarEspacioEnBlanco(nombre);
			return this;
		}

		public Builder descripcion(String descripcion) {
			this.descripcion = UtilTexto.getUtilTexto().quitarEspacioEnBlanco(descripcion);
			return this;
		}

		public Builder idRolAprobador(UUID idRolAprobador) {
			this.idRolAprobador = UtilUUID.obtenerValorDefecto(idRolAprobador);
			return this;
		}

		public RolDominio build() {
			return new RolDominio(this);
		}
	}
}
