package co.edu.uco.kudos.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import co.edu.uco.kudos.transversal.utilitarios.UtilFecha;
import co.edu.uco.kudos.transversal.utilitarios.UtilObjeto;
import co.edu.uco.kudos.transversal.utilitarios.UtilTexto;
import co.edu.uco.kudos.transversal.utilitarios.UtilUUID;

public final class ReporteDTO {

	private UUID id;
	private UUID idPublicacion;
	private UsuarioDTO reportante;
	private String motivo;
	private String descripcion;
	private LocalDateTime fechaReporte;
	private String estado;
	private UsuarioDTO revisor;
	private LocalDateTime fechaDecision;
	private String observacionDecision;

	public ReporteDTO() {
		setId(UtilUUID.obtenerUUIDDefecto());
		setIdPublicacion(UtilUUID.obtenerUUIDDefecto());
		setReportante(new UsuarioDTO());
		setMotivo(UtilTexto.VACIO);
		setDescripcion(UtilTexto.VACIO);
		setFechaReporte(UtilFecha.FECHA_HORA_DEFECTO);
		setEstado(UtilTexto.VACIO);
		setRevisor(new UsuarioDTO());
		setFechaDecision(UtilFecha.FECHA_HORA_DEFECTO);
		setObservacionDecision(UtilTexto.VACIO);
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

	public UsuarioDTO getReportante() {
		return reportante;
	}

	public void setReportante(UsuarioDTO reportante) {
		this.reportante = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(reportante, new UsuarioDTO());
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = UtilTexto.getUtilTexto().quitarEspacioEnBlanco(motivo);
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = UtilTexto.getUtilTexto().quitarEspacioEnBlanco(descripcion);
	}

	public LocalDateTime getFechaReporte() {
		return fechaReporte;
	}

	public void setFechaReporte(LocalDateTime fechaReporte) {
		this.fechaReporte = UtilFecha.obtenerValorDefecto(fechaReporte);
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = UtilTexto.getUtilTexto().quitarEspacioEnBlanco(estado);
	}

	public UsuarioDTO getRevisor() {
		return revisor;
	}

	public void setRevisor(UsuarioDTO revisor) {
		this.revisor = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(revisor, new UsuarioDTO());
	}

	public LocalDateTime getFechaDecision() {
		return fechaDecision;
	}

	public void setFechaDecision(LocalDateTime fechaDecision) {
		this.fechaDecision = UtilFecha.obtenerValorDefecto(fechaDecision);
	}

	public String getObservacionDecision() {
		return observacionDecision;
	}

	public void setObservacionDecision(String observacionDecision) {
		this.observacionDecision = UtilTexto.getUtilTexto().quitarEspacioEnBlanco(observacionDecision);
	}
}
