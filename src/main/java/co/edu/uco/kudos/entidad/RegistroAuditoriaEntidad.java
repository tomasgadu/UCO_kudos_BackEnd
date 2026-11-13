package co.edu.uco.kudos.entidad;

import java.time.LocalDateTime;
import java.util.UUID;

import co.edu.uco.kudos.transversal.utilitarios.UtilFecha;
import co.edu.uco.kudos.transversal.utilitarios.UtilObjeto;
import co.edu.uco.kudos.transversal.utilitarios.UtilTexto;
import co.edu.uco.kudos.transversal.utilitarios.UtilUUID;

public final class RegistroAuditoriaEntidad {

	private UUID id;
	private LocalDateTime fecha;
	private UsuarioEntidad actor;
	private String accion;
	private String entidadAfectada;
	private UUID idEntidadAfectada;
	private String valorAnterior;
	private String valorNuevo;

	public RegistroAuditoriaEntidad() {
		setId(UtilUUID.obtenerUUIDDefecto());
		setFecha(UtilFecha.FECHA_HORA_DEFECTO);
		setActor(new UsuarioEntidad());
		setAccion(UtilTexto.VACIO);
		setEntidadAfectada(UtilTexto.VACIO);
		setIdEntidadAfectada(UtilUUID.obtenerUUIDDefecto());
		setValorAnterior(UtilTexto.VACIO);
		setValorNuevo(UtilTexto.VACIO);
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = UtilUUID.obtenerValorDefecto(id);
	}

	public LocalDateTime getFecha() {
		return fecha;
	}

	public void setFecha(LocalDateTime fecha) {
		this.fecha = UtilFecha.obtenerValorDefecto(fecha);
	}

	public UsuarioEntidad getActor() {
		return actor;
	}

	public void setActor(UsuarioEntidad actor) {
		this.actor = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(actor, new UsuarioEntidad());
	}

	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = UtilTexto.getUtilTexto().quitarEspacioEnBlanco(accion);
	}

	public String getEntidadAfectada() {
		return entidadAfectada;
	}

	public void setEntidadAfectada(String entidadAfectada) {
		this.entidadAfectada = UtilTexto.getUtilTexto().quitarEspacioEnBlanco(entidadAfectada);
	}

	public UUID getIdEntidadAfectada() {
		return idEntidadAfectada;
	}

	public void setIdEntidadAfectada(UUID idEntidadAfectada) {
		this.idEntidadAfectada = UtilUUID.obtenerValorDefecto(idEntidadAfectada);
	}

	public String getValorAnterior() {
		return valorAnterior;
	}

	public void setValorAnterior(String valorAnterior) {
		this.valorAnterior = UtilTexto.getUtilTexto().quitarEspacioEnBlanco(valorAnterior);
	}

	public String getValorNuevo() {
		return valorNuevo;
	}

	public void setValorNuevo(String valorNuevo) {
		this.valorNuevo = UtilTexto.getUtilTexto().quitarEspacioEnBlanco(valorNuevo);
	}
}
