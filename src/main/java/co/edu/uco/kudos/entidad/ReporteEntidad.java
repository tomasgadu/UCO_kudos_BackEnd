package co.edu.uco.kudos.entidad;

import java.time.LocalDateTime;
import java.util.UUID;

import co.edu.uco.kudos.transversal.utilitarios.UtilFecha;
import co.edu.uco.kudos.transversal.utilitarios.UtilObjeto;
import co.edu.uco.kudos.transversal.utilitarios.UtilTexto;
import co.edu.uco.kudos.transversal.utilitarios.UtilUUID;

public final class ReporteEntidad {

	private UUID id;
	private UUID idPublicacion;
	private UsuarioEntidad reportante;
	private String motivo;
	private String descripcion;
	private LocalDateTime fechaReporte;
	private String estado;
	private UsuarioEntidad revisor;
	private LocalDateTime fechaDecision;
	private String observacionDecision;

	public ReporteEntidad() {
		setId(UtilUUID.obtenerUUIDDefecto());
		setIdPublicacion(UtilUUID.obtenerUUIDDefecto());
		setReportante(new UsuarioEntidad());
		setMotivo(UtilTexto.VACIO);
		setDescripcion(UtilTexto.VACIO);
		setFechaReporte(UtilFecha.FECHA_HORA_DEFECTO);
		setEstado(UtilTexto.VACIO);
		setRevisor(new UsuarioEntidad());
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

	public UsuarioEntidad getReportante() {
		return reportante;
	}

	public void setReportante(UsuarioEntidad reportante) {
		this.reportante = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(reportante, new UsuarioEntidad());
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

	public UsuarioEntidad getRevisor() {
		return revisor;
	}

	public void setRevisor(UsuarioEntidad revisor) {
		this.revisor = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(revisor, new UsuarioEntidad());
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
