package co.edu.uco.kudos.dominio;

import java.time.LocalDateTime;
import java.util.UUID;

import co.edu.uco.kudos.dominio.enums.TipoNotificacion;
import co.edu.uco.kudos.transversal.utilitarios.UtilFecha;
import co.edu.uco.kudos.transversal.utilitarios.UtilObjeto;
import co.edu.uco.kudos.transversal.utilitarios.UtilTexto;
import co.edu.uco.kudos.transversal.utilitarios.UtilUUID;

public final class NotificacionDominio {

	private final UUID id;
	private final UsuarioDominio destinatario;
	private final TipoNotificacion tipo;
	private final String mensaje;
	private final LocalDateTime fechaCreacion;
	private final boolean leida;

	private NotificacionDominio(Builder builder) {
		this.id = builder.id;
		this.destinatario = builder.destinatario;
		this.tipo = builder.tipo;
		this.mensaje = builder.mensaje;
		this.fechaCreacion = builder.fechaCreacion;
		this.leida = builder.leida;
	}

	public UUID getId() {
		return id;
	}

	public UsuarioDominio getDestinatario() {
		return destinatario;
	}

	public TipoNotificacion getTipo() {
		return tipo;
	}

	public String getMensaje() {
		return mensaje;
	}

	public LocalDateTime getFechaCreacion() {
		return fechaCreacion;
	}

	public boolean isLeida() {
		return leida;
	}

	public Builder copia() {
		return new Builder().id(id).destinatario(destinatario).tipo(tipo).mensaje(mensaje).fechaCreacion(fechaCreacion).leida(leida);
	}

	public static class Builder {

		private UUID id;
		private UsuarioDominio destinatario;
		private TipoNotificacion tipo;
		private String mensaje;
		private LocalDateTime fechaCreacion;
		private boolean leida;

		public Builder() {
			id = UtilUUID.obtenerUUIDDefecto();
			destinatario = new UsuarioDominio.Builder().build();
			tipo = TipoNotificacion.NO_DEFINIDO;
			mensaje = UtilTexto.VACIO;
			fechaCreacion = UtilFecha.FECHA_HORA_DEFECTO;
			leida = false;
		}

		public Builder id(UUID id) {
			this.id = UtilUUID.obtenerValorDefecto(id);
			return this;
		}

		public Builder destinatario(UsuarioDominio destinatario) {
			this.destinatario = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(destinatario, new UsuarioDominio.Builder().build());
			return this;
		}

		public Builder tipo(TipoNotificacion tipo) {
			this.tipo = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(tipo, TipoNotificacion.NO_DEFINIDO);
			return this;
		}

		public Builder mensaje(String mensaje) {
			this.mensaje = UtilTexto.getUtilTexto().quitarEspacioEnBlanco(mensaje);
			return this;
		}

		public Builder fechaCreacion(LocalDateTime fechaCreacion) {
			this.fechaCreacion = UtilFecha.obtenerValorDefecto(fechaCreacion);
			return this;
		}

		public Builder leida(boolean leida) {
			this.leida = leida;
			return this;
		}

		public NotificacionDominio build() {
			return new NotificacionDominio(this);
		}
	}
}
