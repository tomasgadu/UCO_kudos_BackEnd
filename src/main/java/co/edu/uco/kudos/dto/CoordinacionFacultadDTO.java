package co.edu.uco.kudos.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import co.edu.uco.kudos.transversal.utilitarios.UtilFecha;
import co.edu.uco.kudos.transversal.utilitarios.UtilObjeto;
import co.edu.uco.kudos.transversal.utilitarios.UtilUUID;

public final class CoordinacionFacultadDTO {

	private UUID id;
	private UsuarioDTO coordinador;
	private FacultadDTO facultad;
	private LocalDateTime fechaInicio;
	private LocalDateTime fechaFin;

	public CoordinacionFacultadDTO() {
		setId(UtilUUID.obtenerUUIDDefecto());
		setCoordinador(new UsuarioDTO());
		setFacultad(new FacultadDTO());
		setFechaInicio(UtilFecha.FECHA_HORA_DEFECTO);
		setFechaFin(UtilFecha.FECHA_HORA_DEFECTO);
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = UtilUUID.obtenerValorDefecto(id);
	}

	public UsuarioDTO getCoordinador() {
		return coordinador;
	}

	public void setCoordinador(UsuarioDTO coordinador) {
		this.coordinador = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(coordinador, new UsuarioDTO());
	}

	public FacultadDTO getFacultad() {
		return facultad;
	}

	public void setFacultad(FacultadDTO facultad) {
		this.facultad = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(facultad, new FacultadDTO());
	}

	public LocalDateTime getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(LocalDateTime fechaInicio) {
		this.fechaInicio = UtilFecha.obtenerValorDefecto(fechaInicio);
	}

	public LocalDateTime getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(LocalDateTime fechaFin) {
		this.fechaFin = UtilFecha.obtenerValorDefecto(fechaFin);
	}
}
