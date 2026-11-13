package co.edu.uco.kudos.entidad;

import java.time.LocalDateTime;
import java.util.UUID;

import co.edu.uco.kudos.transversal.utilitarios.UtilFecha;
import co.edu.uco.kudos.transversal.utilitarios.UtilObjeto;
import co.edu.uco.kudos.transversal.utilitarios.UtilTexto;
import co.edu.uco.kudos.transversal.utilitarios.UtilUUID;

public final class VetoEntidad {

	private UUID id;
	private UUID idPublicacion;
	private UsuarioEntidad autoridad;
	private UUID idReporteOrigen;
	private String justificacion;
	private LocalDateTime fechaVeto;

	public VetoEntidad() {
		setId(UtilUUID.obtenerUUIDDefecto());
		setIdPublicacion(UtilUUID.obtenerUUIDDefecto());
		setAutoridad(new UsuarioEntidad());
		setIdReporteOrigen(UtilUUID.obtenerUUIDDefecto());
		setJustificacion(UtilTexto.VACIO);
		setFechaVeto(UtilFecha.FECHA_HORA_DEFECTO);
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = UtilUUID.obtenerValorDefecto(id);
	}

	public UUID getIdPublicacion() {
		return idPublicacion;
	}

	public void setIdPublicacion(UUID idPublicacion) {
		this.idPublicacion = UtilUUID.obtenerValorDefecto(idPublicacion);
	}

	public UsuarioEntidad getAutoridad() {
		return autoridad;
	}

	public void setAutoridad(UsuarioEntidad autoridad) {
		this.autoridad = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(autoridad, new UsuarioEntidad());
	}

	public UUID getIdReporteOrigen() {
		return idReporteOrigen;
	}

	public void setIdReporteOrigen(UUID idReporteOrigen) {
		this.idReporteOrigen = UtilUUID.obtenerValorDefecto(idReporteOrigen);
	}

	public String getJustificacion() {
		return justificacion;
	}

	public void setJustificacion(String justificacion) {
		this.justificacion = UtilTexto.getUtilTexto().quitarEspacioEnBlanco(justificacion);
	}

	public LocalDateTime getFechaVeto() {
		return fechaVeto;
	}

	public void setFechaVeto(LocalDateTime fechaVeto) {
		this.fechaVeto = UtilFecha.obtenerValorDefecto(fechaVeto);
	}
}
