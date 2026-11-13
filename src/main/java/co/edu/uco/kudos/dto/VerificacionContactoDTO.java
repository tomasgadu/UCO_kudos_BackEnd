package co.edu.uco.kudos.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import co.edu.uco.kudos.transversal.utilitarios.UtilFecha;
import co.edu.uco.kudos.transversal.utilitarios.UtilObjeto;
import co.edu.uco.kudos.transversal.utilitarios.UtilTexto;
import co.edu.uco.kudos.transversal.utilitarios.UtilUUID;

public final class VerificacionContactoDTO {

	private UUID id;
	private UsuarioDTO usuario;
	private String tipo;
	private String valorVerificado;
	private String codigo;
	private LocalDateTime fechaEnvio;
	private LocalDateTime fechaExpiracion;
	private LocalDateTime fechaVerificacion;
	private String estado;

	public VerificacionContactoDTO() {
		setId(UtilUUID.obtenerUUIDDefecto());
		setUsuario(new UsuarioDTO());
		setTipo(UtilTexto.VACIO);
		setValorVerificado(UtilTexto.VACIO);
		setCodigo(UtilTexto.VACIO);
		setFechaEnvio(UtilFecha.FECHA_HORA_DEFECTO);
		setFechaExpiracion(UtilFecha.FECHA_HORA_DEFECTO);
		setFechaVerificacion(UtilFecha.FECHA_HORA_DEFECTO);
		setEstado(UtilTexto.VACIO);
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = UtilUUID.obtenerValorDefecto(id);
	}

	public UsuarioDTO getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioDTO usuario) {
		this.usuario = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(usuario, new UsuarioDTO());
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = UtilTexto.getUtilTexto().quitarEspacioEnBlanco(tipo);
	}

	public String getValorVerificado() {
		return valorVerificado;
	}

	public void setValorVerificado(String valorVerificado) {
		this.valorVerificado = UtilTexto.getUtilTexto().quitarEspacioEnBlanco(valorVerificado);
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = UtilTexto.getUtilTexto().quitarEspacioEnBlanco(codigo);
	}

	public LocalDateTime getFechaEnvio() {
		return fechaEnvio;
	}

	public void setFechaEnvio(LocalDateTime fechaEnvio) {
		this.fechaEnvio = UtilFecha.obtenerValorDefecto(fechaEnvio);
	}

	public LocalDateTime getFechaExpiracion() {
		return fechaExpiracion;
	}

	public void setFechaExpiracion(LocalDateTime fechaExpiracion) {
		this.fechaExpiracion = UtilFecha.obtenerValorDefecto(fechaExpiracion);
	}

	public LocalDateTime getFechaVerificacion() {
		return fechaVerificacion;
	}

	public void setFechaVerificacion(LocalDateTime fechaVerificacion) {
		this.fechaVerificacion = UtilFecha.obtenerValorDefecto(fechaVerificacion);
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = UtilTexto.getUtilTexto().quitarEspacioEnBlanco(estado);
	}
}
