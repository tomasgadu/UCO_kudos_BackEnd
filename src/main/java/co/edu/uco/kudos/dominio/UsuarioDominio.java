package co.edu.uco.kudos.dominio;

import java.time.LocalDateTime;
import java.util.UUID;

import co.edu.uco.kudos.dominio.enums.EstadoCuenta;
import co.edu.uco.kudos.transversal.catalogo.CatalogoMensajes;
import co.edu.uco.kudos.transversal.excepciones.KudosDominioExcepcion;
import co.edu.uco.kudos.transversal.utilitarios.UtilFecha;
import co.edu.uco.kudos.transversal.utilitarios.UtilObjeto;
import co.edu.uco.kudos.transversal.utilitarios.UtilTexto;
import co.edu.uco.kudos.transversal.utilitarios.UtilUUID;

public final class UsuarioDominio {

	private final UUID id;
	private final String nombres;
	private final String apellidos;
	private final String correoInstitucional;
	private final String numeroCelular;
	private final String clave;
	private final boolean correoVerificado;
	private final boolean celularVerificado;
	private final EstadoCuenta estadoCuenta;
	private final RolDominio rol;
	private final LocalDateTime fechaRegistro;

	private UsuarioDominio(Builder builder) {
		this.id = builder.id;
		this.nombres = builder.nombres;
		this.apellidos = builder.apellidos;
		this.correoInstitucional = builder.correoInstitucional;
		this.numeroCelular = builder.numeroCelular;
		this.clave = builder.clave;
		this.correoVerificado = builder.correoVerificado;
		this.celularVerificado = builder.celularVerificado;
		this.estadoCuenta = builder.estadoCuenta;
		this.rol = builder.rol;
		this.fechaRegistro = builder.fechaRegistro;
	}

	public UUID getId() {
		return id;
	}

	public String getNombres() {
		return nombres;
	}

	public String getApellidos() {
		return apellidos;
	}

	public String getCorreoInstitucional() {
		return correoInstitucional;
	}

	public String getNumeroCelular() {
		return numeroCelular;
	}

	public String getClave() {
		return clave;
	}

	public boolean isCorreoVerificado() {
		return correoVerificado;
	}

	public boolean isCelularVerificado() {
		return celularVerificado;
	}

	public EstadoCuenta getEstadoCuenta() {
		return estadoCuenta;
	}

	public RolDominio getRol() {
		return rol;
	}

	public LocalDateTime getFechaRegistro() {
		return fechaRegistro;
	}

	public boolean estaActivo() {
		return estadoCuenta == EstadoCuenta.ACTIVA;
	}

	public boolean tieneContactoVerificado() {
		return correoVerificado && celularVerificado;
	}

	public String getNombreCompleto() {
		return nombres + " " + apellidos;
	}

	public UsuarioDominio activar(boolean solicitudAprobada) {
		if (!solicitudAprobada || !tieneContactoVerificado()) {
			throw KudosDominioExcepcion.crear(CatalogoMensajes.Usuario.USUARIO_ERROR_NO_PUEDE_ACTIVARSE);
		}
		return copia().estadoCuenta(EstadoCuenta.ACTIVA).build();
	}

	public UsuarioDominio cambiarCorreo(String nuevoCorreo) {
		return copia().correoInstitucional(nuevoCorreo).correoVerificado(false).estadoCuenta(estadoSinVerificar()).build();
	}

	public UsuarioDominio cambiarCelular(String nuevoCelular) {
		return copia().numeroCelular(nuevoCelular).celularVerificado(false).estadoCuenta(estadoSinVerificar()).build();
	}

	private EstadoCuenta estadoSinVerificar() {
		return estadoCuenta == EstadoCuenta.ACTIVA ? EstadoCuenta.REGISTRADA : estadoCuenta;
	}

	public Builder copia() {
		return new Builder().id(id).nombres(nombres).apellidos(apellidos).correoInstitucional(correoInstitucional).numeroCelular(numeroCelular).clave(clave).correoVerificado(correoVerificado).celularVerificado(celularVerificado).estadoCuenta(estadoCuenta).rol(rol).fechaRegistro(fechaRegistro);
	}

	public static class Builder {

		private UUID id;
		private String nombres;
		private String apellidos;
		private String correoInstitucional;
		private String numeroCelular;
		private String clave;
		private boolean correoVerificado;
		private boolean celularVerificado;
		private EstadoCuenta estadoCuenta;
		private RolDominio rol;
		private LocalDateTime fechaRegistro;

		public Builder() {
			id = UtilUUID.obtenerUUIDDefecto();
			nombres = UtilTexto.VACIO;
			apellidos = UtilTexto.VACIO;
			correoInstitucional = UtilTexto.VACIO;
			numeroCelular = UtilTexto.VACIO;
			clave = UtilTexto.VACIO;
			correoVerificado = false;
			celularVerificado = false;
			estadoCuenta = EstadoCuenta.NO_DEFINIDO;
			rol = new RolDominio.Builder().build();
			fechaRegistro = UtilFecha.FECHA_HORA_DEFECTO;
		}

		public Builder id(UUID id) {
			this.id = UtilUUID.obtenerValorDefecto(id);
			return this;
		}

		public Builder nombres(String nombres) {
			this.nombres = UtilTexto.getUtilTexto().quitarEspacioEnBlanco(nombres);
			return this;
		}

		public Builder apellidos(String apellidos) {
			this.apellidos = UtilTexto.getUtilTexto().quitarEspacioEnBlanco(apellidos);
			return this;
		}

		public Builder correoInstitucional(String correoInstitucional) {
			this.correoInstitucional = UtilTexto.getUtilTexto().quitarEspacioEnBlanco(correoInstitucional);
			return this;
		}

		public Builder numeroCelular(String numeroCelular) {
			this.numeroCelular = UtilTexto.getUtilTexto().quitarEspacioEnBlanco(numeroCelular);
			return this;
		}

		public Builder clave(String clave) {
			this.clave = UtilTexto.getUtilTexto().quitarEspacioEnBlanco(clave);
			return this;
		}

		public Builder correoVerificado(boolean correoVerificado) {
			this.correoVerificado = correoVerificado;
			return this;
		}

		public Builder celularVerificado(boolean celularVerificado) {
			this.celularVerificado = celularVerificado;
			return this;
		}

		public Builder estadoCuenta(EstadoCuenta estadoCuenta) {
			this.estadoCuenta = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(estadoCuenta, EstadoCuenta.NO_DEFINIDO);
			return this;
		}

		public Builder rol(RolDominio rol) {
			this.rol = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(rol, new RolDominio.Builder().build());
			return this;
		}

		public Builder fechaRegistro(LocalDateTime fechaRegistro) {
			this.fechaRegistro = UtilFecha.obtenerValorDefecto(fechaRegistro);
			return this;
		}

		public UsuarioDominio build() {
			return new UsuarioDominio(this);
		}
	}
}
