package co.edu.uco.kudos.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import co.edu.uco.kudos.transversal.utilitarios.UtilFecha;
import co.edu.uco.kudos.transversal.utilitarios.UtilObjeto;
import co.edu.uco.kudos.transversal.utilitarios.UtilUUID;

public final class CoordinacionProgramaDTO {

	private UUID id;
	private UsuarioDTO coordinador;
	private ProgramaAcademicoDTO programaAcademico;
	private LocalDateTime fechaInicio;
	private LocalDateTime fechaFin;

	public CoordinacionProgramaDTO() {
		setId(UtilUUID.obtenerUUIDDefecto());
		setCoordinador(new UsuarioDTO());
		setProgramaAcademico(new ProgramaAcademicoDTO());
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

	public ProgramaAcademicoDTO getProgramaAcademico() {
		return programaAcademico;
	}

	public void setProgramaAcademico(ProgramaAcademicoDTO programaAcademico) {
		this.programaAcademico = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(programaAcademico, new ProgramaAcademicoDTO());
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
