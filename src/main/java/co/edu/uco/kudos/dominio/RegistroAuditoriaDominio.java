package co.edu.uco.kudos.dominio;

import java.time.LocalDateTime;
import java.util.UUID;

import co.edu.uco.kudos.transversal.utilitarios.UtilFecha;
import co.edu.uco.kudos.transversal.utilitarios.UtilObjeto;
import co.edu.uco.kudos.transversal.utilitarios.UtilTexto;
import co.edu.uco.kudos.transversal.utilitarios.UtilUUID;

public final class RegistroAuditoriaDominio {

	private final UUID id;
	private final LocalDateTime fecha;
	private final UsuarioDominio actor;
	private final String accion;
	private final String entidadAfectada;
	private final UUID idEntidadAfectada;
	private final String valorAnterior;
	private final String valorNuevo;

	private RegistroAuditoriaDominio(Builder builder) {
		this.id = builder.id;
		this.fecha = builder.fecha;
		this.actor = builder.actor;
		this.accion = builder.accion;
		this.entidadAfectada = builder.entidadAfectada;
		this.idEntidadAfectada = builder.idEntidadAfectada;
		this.valorAnterior = builder.valorAnterior;
		this.valorNuevo = builder.valorNuevo;
	}

	public UUID getId() {
		return id;
	}

	public LocalDateTime getFecha() {
		return fecha;
	}

	public UsuarioDominio getActor() {
		return actor;
	}

	public String getAccion() {
		return accion;
	}

	public String getEntidadAfectada() {
		return entidadAfectada;
	}

	public UUID getIdEntidadAfectada() {
		return idEntidadAfectada;
	}

	public String getValorAnterior() {
		return valorAnterior;
	}

	public String getValorNuevo() {
		return valorNuevo;
	}

	public Builder copia() {
		return new Builder().id(id).fecha(fecha).actor(actor).accion(accion).entidadAfectada(entidadAfectada).idEntidadAfectada(idEntidadAfectada).valorAnterior(valorAnterior).valorNuevo(valorNuevo);
	}

	public static class Builder {

		private UUID id;
		private LocalDateTime fecha;
		private UsuarioDominio actor;
		private String accion;
		private String entidadAfectada;
		private UUID idEntidadAfectada;
		private String valorAnterior;
		private String valorNuevo;

		public Builder() {
			id = UtilUUID.obtenerUUIDDefecto();
			fecha = UtilFecha.FECHA_HORA_DEFECTO;
			actor = new UsuarioDominio.Builder().build();
			accion = UtilTexto.VACIO;
			entidadAfectada = UtilTexto.VACIO;
			idEntidadAfectada = UtilUUID.obtenerUUIDDefecto();
			valorAnterior = UtilTexto.VACIO;
			valorNuevo = UtilTexto.VACIO;
		}

		public Builder id(UUID id) {
			this.id = UtilUUID.obtenerValorDefecto(id);
			return this;
		}

		public Builder fecha(LocalDateTime fecha) {
			this.fecha = UtilFecha.obtenerValorDefecto(fecha);
			return this;
		}

		public Builder actor(UsuarioDominio actor) {
			this.actor = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(actor, new UsuarioDominio.Builder().build());
			return this;
		}

		public Builder accion(String accion) {
			this.accion = UtilTexto.getUtilTexto().quitarEspacioEnBlanco(accion);
			return this;
		}

		public Builder entidadAfectada(String entidadAfectada) {
			this.entidadAfectada = UtilTexto.getUtilTexto().quitarEspacioEnBlanco(entidadAfectada);
			return this;
		}

		public Builder idEntidadAfectada(UUID idEntidadAfectada) {
			this.idEntidadAfectada = UtilUUID.obtenerValorDefecto(idEntidadAfectada);
			return this;
		}

		public Builder valorAnterior(String valorAnterior) {
			this.valorAnterior = UtilTexto.getUtilTexto().quitarEspacioEnBlanco(valorAnterior);
			return this;
		}

		public Builder valorNuevo(String valorNuevo) {
			this.valorNuevo = UtilTexto.getUtilTexto().quitarEspacioEnBlanco(valorNuevo);
			return this;
		}

		public RegistroAuditoriaDominio build() {
			return new RegistroAuditoriaDominio(this);
		}
	}
}
