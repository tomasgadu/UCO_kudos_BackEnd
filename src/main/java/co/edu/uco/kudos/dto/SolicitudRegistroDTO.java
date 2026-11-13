package co.edu.uco.kudos.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import co.edu.uco.kudos.transversal.utilitarios.UtilFecha;
import co.edu.uco.kudos.transversal.utilitarios.UtilObjeto;
import co.edu.uco.kudos.transversal.utilitarios.UtilTexto;
import co.edu.uco.kudos.transversal.utilitarios.UtilUUID;

public final class SolicitudRegistroDTO {

	private UUID id;
	private UsuarioDTO solicitante;
	private RolDTO rolSolicitado;
	private FacultadDTO facultad;
	private ProgramaAcademicoDTO programaAcademico;
	private String estado;
	private LocalDateTime fechaSolicitud;
	private UsuarioDTO aprobador;
	private LocalDateTime fechaDecision;
	private String observacion;

	public SolicitudRegistroDTO() {
		setId(UtilUUID.obtenerUUIDDefecto());
		setSolicitante(new UsuarioDTO());
		setRolSolicitado(new RolDTO());
		setFacultad(new FacultadDTO());
		setProgramaAcademico(new ProgramaAcademicoDTO());
		setEstado(UtilTexto.VACIO);
		setFechaSolicitud(UtilFecha.FECHA_HORA_DEFECTO);
		setAprobador(new UsuarioDTO());
		setFechaDecision(UtilFecha.FECHA_HORA_DEFECTO);
		setObservacion(UtilTexto.VACIO);
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = UtilUUID.obtenerValorDefecto(id);
	}

	public UsuarioDTO getSolicitante() {
		return solicitante;
	}

	public void setSolicitante(UsuarioDTO solicitante) {
		this.solicitante = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(solicitante, new UsuarioDTO());
	}

	public RolDTO getRolSolicitado() {
		return rolSolicitado;
	}

	public void setRolSolicitado(RolDTO rolSolicitado) {
		this.rolSolicitado = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(rolSolicitado, new RolDTO());
	}

	public FacultadDTO getFacultad() {
		return facultad;
	}

	public void setFacultad(FacultadDTO facultad) {
		this.facultad = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(facultad, new FacultadDTO());
	}

	public ProgramaAcademicoDTO getProgramaAcademico() {
		return programaAcademico;
	}

	public void setProgramaAcademico(ProgramaAcademicoDTO programaAcademico) {
		this.programaAcademico = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(programaAcademico, new ProgramaAcademicoDTO());
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

	public UsuarioDTO getAprobador() {
		return aprobador;
	}

	public void setAprobador(UsuarioDTO aprobador) {
		this.aprobador = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(aprobador, new UsuarioDTO());
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
