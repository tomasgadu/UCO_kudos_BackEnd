package co.edu.uco.kudos.dominio;

import java.time.LocalDateTime;
import java.util.UUID;

import co.edu.uco.kudos.dominio.enums.EstadoVerificacion;
import co.edu.uco.kudos.dominio.enums.TipoVerificacion;
import co.edu.uco.kudos.transversal.utilitarios.UtilFecha;
import co.edu.uco.kudos.transversal.utilitarios.UtilObjeto;
import co.edu.uco.kudos.transversal.utilitarios.UtilTexto;
import co.edu.uco.kudos.transversal.utilitarios.UtilUUID;

public final class VerificacionContactoDominio {

	private final UUID id;
	private final UsuarioDominio usuario;
	private final TipoVerificacion tipo;
	private final String valorVerificado;
	private final String codigo;
	private final LocalDateTime fechaEnvio;
	private final LocalDateTime fechaExpiracion;
	private final LocalDateTime fechaVerificacion;
	private final EstadoVerificacion estado;

	private VerificacionContactoDominio(Builder builder) {
		this.id = builder.id;
		this.usuario = builder.usuario;
		this.tipo = builder.tipo;
		this.valorVerificado = builder.valorVerificado;
		this.codigo = builder.codigo;
		this.fechaEnvio = builder.fechaEnvio;
		this.fechaExpiracion = builder.fechaExpiracion;
		this.fechaVerificacion = builder.fechaVerificacion;
		this.estado = builder.estado;
	}

	public UUID getId() {
		return id;
	}

	public UsuarioDominio getUsuario() {
		return usuario;
	}

	public TipoVerificacion getTipo() {
		return tipo;
	}

	public String getValorVerificado() {
		return valorVerificado;
	}

	public String getCodigo() {
		return codigo;
	}

	public LocalDateTime getFechaEnvio() {
		return fechaEnvio;
	}

	public LocalDateTime getFechaExpiracion() {
		return fechaExpiracion;
	}

	public LocalDateTime getFechaVerificacion() {
		return fechaVerificacion;
	}

	public EstadoVerificacion getEstado() {
		return estado;
	}

	public boolean estaPendiente() {
		return estado == EstadoVerificacion.PENDIENTE;
	}

	public boolean estaExpirada(LocalDateTime ahora) {
		return ahora.isAfter(fechaExpiracion);
	}

	public boolean codigoCoincide(String codigoIngresado) {
		return codigo.equals(codigoIngresado);
	}

	public Builder copia() {
		return new Builder().id(id).usuario(usuario).tipo(tipo).valorVerificado(valorVerificado).codigo(codigo).fechaEnvio(fechaEnvio).fechaExpiracion(fechaExpiracion).fechaVerificacion(fechaVerificacion).estado(estado);
	}

	public static class Builder {

		private UUID id;
		private UsuarioDominio usuario;
		private TipoVerificacion tipo;
		private String valorVerificado;
		private String codigo;
		private LocalDateTime fechaEnvio;
		private LocalDateTime fechaExpiracion;
		private LocalDateTime fechaVerificacion;
		private EstadoVerificacion estado;

		public Builder() {
			id = UtilUUID.obtenerUUIDDefecto();
			usuario = new UsuarioDominio.Builder().build();
			tipo = TipoVerificacion.NO_DEFINIDO;
			valorVerificado = UtilTexto.VACIO;
			codigo = UtilTexto.VACIO;
			fechaEnvio = UtilFecha.FECHA_HORA_DEFECTO;
			fechaExpiracion = UtilFecha.FECHA_HORA_DEFECTO;
			fechaVerificacion = UtilFecha.FECHA_HORA_DEFECTO;
			estado = EstadoVerificacion.NO_DEFINIDO;
		}

		public Builder id(UUID id) {
			this.id = UtilUUID.obtenerValorDefecto(id);
			return this;
		}

		public Builder usuario(UsuarioDominio usuario) {
			this.usuario = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(usuario, new UsuarioDominio.Builder().build());
			return this;
		}

		public Builder tipo(TipoVerificacion tipo) {
			this.tipo = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(tipo, TipoVerificacion.NO_DEFINIDO);
			return this;
		}

		public Builder valorVerificado(String valorVerificado) {
			this.valorVerificado = UtilTexto.getUtilTexto().quitarEspacioEnBlanco(valorVerificado);
			return this;
		}

		public Builder codigo(String codigo) {
			this.codigo = UtilTexto.getUtilTexto().quitarEspacioEnBlanco(codigo);
			return this;
		}

		public Builder fechaEnvio(LocalDateTime fechaEnvio) {
			this.fechaEnvio = UtilFecha.obtenerValorDefecto(fechaEnvio);
			return this;
		}

		public Builder fechaExpiracion(LocalDateTime fechaExpiracion) {
			this.fechaExpiracion = UtilFecha.obtenerValorDefecto(fechaExpiracion);
			return this;
		}

		public Builder fechaVerificacion(LocalDateTime fechaVerificacion) {
			this.fechaVerificacion = UtilFecha.obtenerValorDefecto(fechaVerificacion);
			return this;
		}

		public Builder estado(EstadoVerificacion estado) {
			this.estado = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(estado, EstadoVerificacion.NO_DEFINIDO);
			return this;
		}

		public VerificacionContactoDominio build() {
			return new VerificacionContactoDominio(this);
		}
	}
}
