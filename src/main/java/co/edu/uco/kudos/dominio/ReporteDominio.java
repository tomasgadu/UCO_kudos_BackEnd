package co.edu.uco.kudos.dominio;

import java.time.LocalDateTime;
import java.util.UUID;

import co.edu.uco.kudos.dominio.enums.EstadoReporte;
import co.edu.uco.kudos.dominio.enums.MotivoReporte;
import co.edu.uco.kudos.transversal.catalogo.CatalogoMensajes;
import co.edu.uco.kudos.transversal.excepciones.KudosDominioExcepcion;
import co.edu.uco.kudos.transversal.utilitarios.UtilFecha;
import co.edu.uco.kudos.transversal.utilitarios.UtilObjeto;
import co.edu.uco.kudos.transversal.utilitarios.UtilTexto;
import co.edu.uco.kudos.transversal.utilitarios.UtilUUID;

public final class ReporteDominio {

	private final UUID id;
	private final UUID idPublicacion;
	private final UsuarioDominio reportante;
	private final MotivoReporte motivo;
	private final String descripcion;
	private final LocalDateTime fechaReporte;
	private final EstadoReporte estado;
	private final UsuarioDominio revisor;
	private final LocalDateTime fechaDecision;
	private final String observacionDecision;

	private ReporteDominio(Builder builder) {
		this.id = builder.id;
		this.idPublicacion = builder.idPublicacion;
		this.reportante = builder.reportante;
		this.motivo = builder.motivo;
		this.descripcion = builder.descripcion;
		this.fechaReporte = builder.fechaReporte;
		this.estado = builder.estado;
		this.revisor = builder.revisor;
		this.fechaDecision = builder.fechaDecision;
		this.observacionDecision = builder.observacionDecision;
	}

	public UUID getId() {
		return id;
	}

	public UUID getIdPublicacion() {
		return idPublicacion;
	}

	public UsuarioDominio getReportante() {
		return reportante;
	}

	public MotivoReporte getMotivo() {
		return motivo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public LocalDateTime getFechaReporte() {
		return fechaReporte;
	}

	public EstadoReporte getEstado() {
		return estado;
	}

	public UsuarioDominio getRevisor() {
		return revisor;
	}

	public LocalDateTime getFechaDecision() {
		return fechaDecision;
	}

	public String getObservacionDecision() {
		return observacionDecision;
	}

	private static final int MINIMO_TEXTO = 10;

	public static ReporteDominio crear(UUID id, UUID idPublicacion, UsuarioDominio reportante, MotivoReporte motivo,
			String descripcion, LocalDateTime ahora) {
		if (motivo == null || motivo == MotivoReporte.NO_DEFINIDO) {
			throw KudosDominioExcepcion.crear(CatalogoMensajes.Reporte.USUARIO_ERROR_MOTIVO);
		}
		if (motivo == MotivoReporte.OTRO && longitud(descripcion) < MINIMO_TEXTO) {
			throw KudosDominioExcepcion.crear(CatalogoMensajes.Reporte.USUARIO_ERROR_DESCRIPCION_OTRO);
		}
		return new Builder().id(id).idPublicacion(idPublicacion).reportante(reportante).motivo(motivo)
				.descripcion(descripcion).fechaReporte(ahora).estado(EstadoReporte.PENDIENTE).build();
	}

	public boolean estaAbierto() {
		return estado == EstadoReporte.PENDIENTE || estado == EstadoReporte.EN_REVISION;
	}

	public boolean esDe(UUID idUsuario) {
		return reportante.getId().equals(idUsuario);
	}

	public ReporteDominio iniciarRevision(UsuarioDominio revisorAsignado) {
		if (estado != EstadoReporte.PENDIENTE) {
			throw KudosDominioExcepcion.crear(CatalogoMensajes.Reporte.USUARIO_ERROR_NO_PENDIENTE);
		}
		return copia().estado(EstadoReporte.EN_REVISION).revisor(revisorAsignado).build();
	}

	public ReporteDominio decidir(boolean aceptar, UsuarioDominio decisor, String observacion, LocalDateTime ahora) {
		if (estado != EstadoReporte.EN_REVISION) {
			throw KudosDominioExcepcion.crear(CatalogoMensajes.Reporte.USUARIO_ERROR_NO_EN_REVISION);
		}
		if (longitud(observacion) < MINIMO_TEXTO) {
			throw KudosDominioExcepcion.crear(CatalogoMensajes.Reporte.USUARIO_ERROR_OBSERVACION);
		}
		var nuevoEstado = aceptar ? EstadoReporte.ACEPTADO : EstadoReporte.RECHAZADO;
		return copia().estado(nuevoEstado).revisor(decisor).fechaDecision(ahora).observacionDecision(observacion).build();
	}

	public ReporteDominio cerrarPorVeto(UsuarioDominio autoridad, LocalDateTime ahora) {
		return copia().estado(EstadoReporte.ACEPTADO).revisor(autoridad).fechaDecision(ahora)
				.observacionDecision(CatalogoMensajes.Reporte.OBSERVACION_CIERRE_POR_VETO).build();
	}

	private static int longitud(String texto) {
		return UtilTexto.getUtilTexto().quitarEspacioEnBlanco(texto).length();
	}

	public Builder copia() {
		return new Builder().id(id).idPublicacion(idPublicacion).reportante(reportante).motivo(motivo).descripcion(descripcion).fechaReporte(fechaReporte).estado(estado).revisor(revisor).fechaDecision(fechaDecision).observacionDecision(observacionDecision);
	}

	public static class Builder {

		private UUID id;
		private UUID idPublicacion;
		private UsuarioDominio reportante;
		private MotivoReporte motivo;
		private String descripcion;
		private LocalDateTime fechaReporte;
		private EstadoReporte estado;
		private UsuarioDominio revisor;
		private LocalDateTime fechaDecision;
		private String observacionDecision;

		public Builder() {
			id = UtilUUID.obtenerUUIDDefecto();
			idPublicacion = UtilUUID.obtenerUUIDDefecto();
			reportante = new UsuarioDominio.Builder().build();
			motivo = MotivoReporte.NO_DEFINIDO;
			descripcion = UtilTexto.VACIO;
			fechaReporte = UtilFecha.FECHA_HORA_DEFECTO;
			estado = EstadoReporte.NO_DEFINIDO;
			revisor = new UsuarioDominio.Builder().build();
			fechaDecision = UtilFecha.FECHA_HORA_DEFECTO;
			observacionDecision = UtilTexto.VACIO;
		}

		public Builder id(UUID id) {
			this.id = UtilUUID.obtenerValorDefecto(id);
			return this;
		}

		public Builder idPublicacion(UUID idPublicacion) {
			this.idPublicacion = UtilUUID.obtenerValorDefecto(idPublicacion);
			return this;
		}

		public Builder reportante(UsuarioDominio reportante) {
			this.reportante = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(reportante, new UsuarioDominio.Builder().build());
			return this;
		}

		public Builder motivo(MotivoReporte motivo) {
			this.motivo = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(motivo, MotivoReporte.NO_DEFINIDO);
			return this;
		}

		public Builder descripcion(String descripcion) {
			this.descripcion = UtilTexto.getUtilTexto().quitarEspacioEnBlanco(descripcion);
			return this;
		}

		public Builder fechaReporte(LocalDateTime fechaReporte) {
			this.fechaReporte = UtilFecha.obtenerValorDefecto(fechaReporte);
			return this;
		}

		public Builder estado(EstadoReporte estado) {
			this.estado = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(estado, EstadoReporte.NO_DEFINIDO);
			return this;
		}

		public Builder revisor(UsuarioDominio revisor) {
			this.revisor = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(revisor, new UsuarioDominio.Builder().build());
			return this;
		}

		public Builder fechaDecision(LocalDateTime fechaDecision) {
			this.fechaDecision = UtilFecha.obtenerValorDefecto(fechaDecision);
			return this;
		}

		public Builder observacionDecision(String observacionDecision) {
			this.observacionDecision = UtilTexto.getUtilTexto().quitarEspacioEnBlanco(observacionDecision);
			return this;
		}

		public ReporteDominio build() {
			return new ReporteDominio(this);
		}
	}
}
