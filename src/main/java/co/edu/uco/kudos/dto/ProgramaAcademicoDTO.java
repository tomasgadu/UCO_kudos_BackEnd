package co.edu.uco.kudos.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import co.edu.uco.kudos.transversal.utilitarios.UtilFecha;
import co.edu.uco.kudos.transversal.utilitarios.UtilObjeto;
import co.edu.uco.kudos.transversal.utilitarios.UtilTexto;
import co.edu.uco.kudos.transversal.utilitarios.UtilUUID;

public final class ProgramaAcademicoDTO {

	private UUID id;
	private String nombre;
	private FacultadDTO facultad;
	private String estado;
	private LocalDateTime fechaCreacion;

	public ProgramaAcademicoDTO() {
		setId(UtilUUID.obtenerUUIDDefecto());
		setNombre(UtilTexto.VACIO);
		setFacultad(new FacultadDTO());
		setEstado(UtilTexto.VACIO);
		setFechaCreacion(UtilFecha.FECHA_HORA_DEFECTO);
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = UtilUUID.obtenerValorDefecto(id);
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = UtilTexto.getUtilTexto().quitarEspacioEnBlanco(nombre);
	}

	public FacultadDTO getFacultad() {
		return facultad;
	}

	public void setFacultad(FacultadDTO facultad) {
		this.facultad = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(facultad, new FacultadDTO());
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = UtilTexto.getUtilTexto().quitarEspacioEnBlanco(estado);
	}

	public LocalDateTime getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(LocalDateTime fechaCreacion) {
		this.fechaCreacion = UtilFecha.obtenerValorDefecto(fechaCreacion);
	}
}
