package co.edu.uco.kudos.entidad;

import java.time.LocalDateTime;
import java.util.UUID;

import co.edu.uco.kudos.transversal.utilitarios.UtilFecha;
import co.edu.uco.kudos.transversal.utilitarios.UtilObjeto;
import co.edu.uco.kudos.transversal.utilitarios.UtilUUID;

public final class CoordinacionProgramaEntidad {

	private UUID id;
	private UsuarioEntidad coordinador;
	private ProgramaAcademicoEntidad programaAcademico;
	private LocalDateTime fechaInicio;
	private LocalDateTime fechaFin;

	public CoordinacionProgramaEntidad() {
		setId(UtilUUID.obtenerUUIDDefecto());
		setCoordinador(new UsuarioEntidad());
		setProgramaAcademico(new ProgramaAcademicoEntidad());
		setFechaInicio(UtilFecha.FECHA_HORA_DEFECTO);
		setFechaFin(UtilFecha.FECHA_HORA_DEFECTO);
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = UtilUUID.obtenerValorDefecto(id);
	}

	public UsuarioEntidad getCoordinador() {
		return coordinador;
	}

	public void setCoordinador(UsuarioEntidad coordinador) {
		this.coordinador = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(coordinador, new UsuarioEntidad());
	}

	public ProgramaAcademicoEntidad getProgramaAcademico() {
		return programaAcademico;
	}

	public void setProgramaAcademico(ProgramaAcademicoEntidad programaAcademico) {
		this.programaAcademico = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(programaAcademico, new ProgramaAcademicoEntidad());
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
