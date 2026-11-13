package co.edu.uco.kudos.entidad;

import java.time.LocalDateTime;
import java.util.UUID;

import co.edu.uco.kudos.transversal.utilitarios.UtilFecha;
import co.edu.uco.kudos.transversal.utilitarios.UtilObjeto;
import co.edu.uco.kudos.transversal.utilitarios.UtilTexto;
import co.edu.uco.kudos.transversal.utilitarios.UtilUUID;

public final class NotificacionEntidad {

	private UUID id;
	private UsuarioEntidad destinatario;
	private String tipo;
	private String mensaje;
	private LocalDateTime fechaCreacion;
	private boolean leida;

	public NotificacionEntidad() {
		setId(UtilUUID.obtenerUUIDDefecto());
		setDestinatario(new UsuarioEntidad());
		setTipo(UtilTexto.VACIO);
		setMensaje(UtilTexto.VACIO);
		setFechaCreacion(UtilFecha.FECHA_HORA_DEFECTO);
		setLeida(false);
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = UtilUUID.obtenerValorDefecto(id);
	}

	public UsuarioEntidad getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(UsuarioEntidad destinatario) {
		this.destinatario = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(destinatario, new UsuarioEntidad());
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = UtilTexto.getUtilTexto().quitarEspacioEnBlanco(tipo);
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = UtilTexto.getUtilTexto().quitarEspacioEnBlanco(mensaje);
	}

	public LocalDateTime getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(LocalDateTime fechaCreacion) {
		this.fechaCreacion = UtilFecha.obtenerValorDefecto(fechaCreacion);
	}

	public boolean isLeida() {
		return leida;
	}

	public void setLeida(boolean leida) {
		this.leida = leida;
	}
}
