package co.edu.uco.kudos.entidad;

import java.time.LocalDateTime;
import java.util.UUID;

import co.edu.uco.kudos.transversal.utilitarios.UtilFecha;
import co.edu.uco.kudos.transversal.utilitarios.UtilObjeto;
import co.edu.uco.kudos.transversal.utilitarios.UtilTexto;
import co.edu.uco.kudos.transversal.utilitarios.UtilUUID;

public final class UsuarioEntidad {

	private UUID id;
	private String nombres;
	private String apellidos;
	private String correoInstitucional;
	private String numeroCelular;
	private String clave;
	private boolean correoVerificado;
	private boolean celularVerificado;
	private String estadoCuenta;
	private RolEntidad rol;
	private LocalDateTime fechaRegistro;

	public UsuarioEntidad() {
		setId(UtilUUID.obtenerUUIDDefecto());
		setNombres(UtilTexto.VACIO);
		setApellidos(UtilTexto.VACIO);
		setCorreoInstitucional(UtilTexto.VACIO);
		setNumeroCelular(UtilTexto.VACIO);
		setClave(UtilTexto.VACIO);
		setCorreoVerificado(false);
		setCelularVerificado(false);
		setEstadoCuenta(UtilTexto.VACIO);
		setRol(new RolEntidad());
		setFechaRegistro(UtilFecha.FECHA_HORA_DEFECTO);
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = UtilUUID.obtenerValorDefecto(id);
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = UtilTexto.getUtilTexto().quitarEspacioEnBlanco(nombres);
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = UtilTexto.getUtilTexto().quitarEspacioEnBlanco(apellidos);
	}

	public String getCorreoInstitucional() {
		return correoInstitucional;
	}

	public void setCorreoInstitucional(String correoInstitucional) {
		this.correoInstitucional = UtilTexto.getUtilTexto().quitarEspacioEnBlanco(correoInstitucional);
	}

	public String getNumeroCelular() {
		return numeroCelular;
	}

	public void setNumeroCelular(String numeroCelular) {
		this.numeroCelular = UtilTexto.getUtilTexto().quitarEspacioEnBlanco(numeroCelular);
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = UtilTexto.getUtilTexto().quitarEspacioEnBlanco(clave);
	}

	public boolean isCorreoVerificado() {
		return correoVerificado;
	}

	public void setCorreoVerificado(boolean correoVerificado) {
		this.correoVerificado = correoVerificado;
	}

	public boolean isCelularVerificado() {
		return celularVerificado;
	}

	public void setCelularVerificado(boolean celularVerificado) {
		this.celularVerificado = celularVerificado;
	}

	public String getEstadoCuenta() {
		return estadoCuenta;
	}

	public void setEstadoCuenta(String estadoCuenta) {
		this.estadoCuenta = UtilTexto.getUtilTexto().quitarEspacioEnBlanco(estadoCuenta);
	}

	public RolEntidad getRol() {
		return rol;
	}

	public void setRol(RolEntidad rol) {
		this.rol = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(rol, new RolEntidad());
	}

	public LocalDateTime getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(LocalDateTime fechaRegistro) {
		this.fechaRegistro = UtilFecha.obtenerValorDefecto(fechaRegistro);
	}
}
