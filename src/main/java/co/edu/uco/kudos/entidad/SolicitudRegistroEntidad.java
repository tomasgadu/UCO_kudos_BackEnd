package co.edu.uco.kudos.entidad;

import java.time.LocalDateTime;
import java.util.UUID;

import co.edu.uco.kudos.transversal.utilitarios.UtilFecha;
import co.edu.uco.kudos.transversal.utilitarios.UtilObjeto;
import co.edu.uco.kudos.transversal.utilitarios.UtilTexto;
import co.edu.uco.kudos.transversal.utilitarios.UtilUUID;

public final class SolicitudRegistroEntidad {

	private UUID id;
	private UsuarioEntidad solicitante;
	private RolEntidad rolSolicitado;
	private FacultadEntidad facultad;
	private ProgramaAcademicoEntidad programaAcademico;
	private String estado;
	private LocalDateTime fechaSolicitud;
	private UsuarioEntidad aprobador;
	private LocalDateTime fechaDecision;
	private String observacion;

	public SolicitudRegistroEntidad() {
		setId(UtilUUID.obtenerUUIDDefecto());
		setSolicitante(new UsuarioEntidad());
		setRolSolicitado(new RolEntidad());
		setFacultad(new FacultadEntidad());
		setProgramaAcademico(new ProgramaAcademicoEntidad());
		setEstado(UtilTexto.VACIO);
		setFechaSolicitud(UtilFecha.FECHA_HORA_DEFECTO);
		setAprobador(new UsuarioEntidad());
		setFechaDecision(UtilFecha.FECHA_HORA_DEFECTO);
		setObservacion(UtilTexto.VACIO);
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = UtilUUID.obtenerValorDefecto(id);
	}

	public UsuarioEntidad getSolicitante() {
		return solicitante;
	}

	public void setSolicitante(UsuarioEntidad solicitante) {
		this.solicitante = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(solicitante, new UsuarioEntidad());
	}

	public RolEntidad getRolSolicitado() {
		return rolSolicitado;
	}

	public void setRolSolicitado(RolEntidad rolSolicitado) {
		this.rolSolicitado = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(rolSolicitado, new RolEntidad());
	}

	public FacultadEntidad getFacultad() {
		return facultad;
	}

	public void setFacultad(FacultadEntidad facultad) {
		this.facultad = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(facultad, new FacultadEntidad());
	}

	public ProgramaAcademicoEntidad getProgramaAcademico() {
		return programaAcademico;
	}

	public void setProgramaAcademico(ProgramaAcademicoEntidad programaAcademico) {
		this.programaAcademico = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(programaAcademico, new ProgramaAcademicoEntidad());
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = UtilTexto.getUtilTexto().quitarEspacioEnBlanco(estado);
	}

	public LocalDateTime getFechaSolicitud() {
		return fechaSolicitud;
	}

	public void setFechaSolicitud(LocalDateTime fechaSolicitud) {
		this.fechaSolicitud = UtilFecha.obtenerValorDefecto(fechaSolicitud);
	}

	public UsuarioEntidad getAprobador() {
		return aprobador;
	}

	public void setAprobador(UsuarioEntidad aprobador) {
		this.aprobador = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(aprobador, new UsuarioEntidad());
	}

	public LocalDateTime getFechaDecision() {
		return fechaDecision;
	}

	public void setFechaDecision(LocalDateTime fechaDecision) {
		this.fechaDecision = UtilFecha.obtenerValorDefecto(fechaDecision);
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = UtilTexto.getUtilTexto().quitarEspacioEnBlanco(observacion);
	}
}
