package co.edu.uco.kudos.dominio;

import java.time.LocalDateTime;
import java.util.UUID;

import co.edu.uco.kudos.dominio.enums.EstadoSolicitud;
import co.edu.uco.kudos.transversal.catalogo.CatalogoMensajes;
import co.edu.uco.kudos.transversal.excepciones.KudosDominioExcepcion;
import co.edu.uco.kudos.transversal.utilitarios.UtilFecha;
import co.edu.uco.kudos.transversal.utilitarios.UtilObjeto;
import co.edu.uco.kudos.transversal.utilitarios.UtilTexto;
import co.edu.uco.kudos.transversal.utilitarios.UtilUUID;

public final class SolicitudRegistroDominio {

	private final UUID id;
	private final UsuarioDominio solicitante;
	private final RolDominio rolSolicitado;
	private final FacultadDominio facultad;
	private final ProgramaAcademicoDominio programaAcademico;
	private final EstadoSolicitud estado;
	private final LocalDateTime fechaSolicitud;
	private final UsuarioDominio aprobador;
	private final LocalDateTime fechaDecision;
	private final String observacion;

	private SolicitudRegistroDominio(Builder builder) {
		this.id = builder.id;
		this.solicitante = builder.solicitante;
		this.rolSolicitado = builder.rolSolicitado;
		this.facultad = builder.facultad;
		this.programaAcademico = builder.programaAcademico;
		this.estado = builder.estado;
		this.fechaSolicitud = builder.fechaSolicitud;
		this.aprobador = builder.aprobador;
		this.fechaDecision = builder.fechaDecision;
		this.observacion = builder.observacion;
	}

	public UUID getId() {
		return id;
	}

	public UsuarioDominio getSolicitante() {
		return solicitante;
	}

	public RolDominio getRolSolicitado() {
		return rolSolicitado;
	}

	public FacultadDominio getFacultad() {
		return facultad;
	}

	public ProgramaAcademicoDominio getProgramaAcademico() {
		return programaAcademico;
	}

	public EstadoSolicitud getEstado() {
		return estado;
	}

	public LocalDateTime getFechaSolicitud() {
		return fechaSolicitud;
	}

	public UsuarioDominio getAprobador() {
		return aprobador;
	}

	public LocalDateTime getFechaDecision() {
		return fechaDecision;
	}

	public String getObservacion() {
		return observacion;
	}

	public boolean estaPendiente() {
		return estado == EstadoSolicitud.PENDIENTE;
	}

	public SolicitudRegistroDominio decidir(boolean aprobar, UsuarioDominio decisor, String observacionDecision,
			LocalDateTime fecha) {
		if (!estaPendiente()) {
			throw KudosDominioExcepcion.crear(CatalogoMensajes.SolicitudRegistro.USUARIO_ERROR_YA_DECIDIDA);
		}
		var nuevoEstado = aprobar ? EstadoSolicitud.APROBADA : EstadoSolicitud.RECHAZADA;
		return copia().estado(nuevoEstado).aprobador(decisor).fechaDecision(fecha).observacion(observacionDecision)
				.build();
	}

	public Builder copia() {
		return new Builder().id(id).solicitante(solicitante).rolSolicitado(rolSolicitado).facultad(facultad).programaAcademico(programaAcademico).estado(estado).fechaSolicitud(fechaSolicitud).aprobador(aprobador).fechaDecision(fechaDecision).observacion(observacion);
	}

	public static class Builder {

		private UUID id;
		private UsuarioDominio solicitante;
		private RolDominio rolSolicitado;
		private FacultadDominio facultad;
		private ProgramaAcademicoDominio programaAcademico;
		private EstadoSolicitud estado;
		private LocalDateTime fechaSolicitud;
		private UsuarioDominio aprobador;
		private LocalDateTime fechaDecision;
		private String observacion;

		public Builder() {
			id = UtilUUID.obtenerUUIDDefecto();
			solicitante = new UsuarioDominio.Builder().build();
			rolSolicitado = new RolDominio.Builder().build();
			facultad = new FacultadDominio.Builder().build();
			programaAcademico = new ProgramaAcademicoDominio.Builder().build();
			estado = EstadoSolicitud.NO_DEFINIDO;
			fechaSolicitud = UtilFecha.FECHA_HORA_DEFECTO;
			aprobador = new UsuarioDominio.Builder().build();
			fechaDecision = UtilFecha.FECHA_HORA_DEFECTO;
			observacion = UtilTexto.VACIO;
		}

		public Builder id(UUID id) {
			this.id = UtilUUID.obtenerValorDefecto(id);
			return this;
		}

		public Builder solicitante(UsuarioDominio solicitante) {
			this.solicitante = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(solicitante, new UsuarioDominio.Builder().build());
			return this;
		}

		public Builder rolSolicitado(RolDominio rolSolicitado) {
			this.rolSolicitado = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(rolSolicitado, new RolDominio.Builder().build());
			return this;
		}

		public Builder facultad(FacultadDominio facultad) {
			this.facultad = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(facultad, new FacultadDominio.Builder().build());
			return this;
		}

		public Builder programaAcademico(ProgramaAcademicoDominio programaAcademico) {
			this.programaAcademico = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(programaAcademico, new ProgramaAcademicoDominio.Builder().build());
			return this;
		}

		public Builder estado(EstadoSolicitud estado) {
			this.estado = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(estado, EstadoSolicitud.NO_DEFINIDO);
			return this;
		}

		public Builder fechaSolicitud(LocalDateTime fechaSolicitud) {
			this.fechaSolicitud = UtilFecha.obtenerValorDefecto(fechaSolicitud);
			return this;
		}

		public Builder aprobador(UsuarioDominio aprobador) {
			this.aprobador = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(aprobador, new UsuarioDominio.Builder().build());
			return this;
		}

		public Builder fechaDecision(LocalDateTime fechaDecision) {
			this.fechaDecision = UtilFecha.obtenerValorDefecto(fechaDecision);
			return this;
		}

		public Builder observacion(String observacion) {
			this.observacion = UtilTexto.getUtilTexto().quitarEspacioEnBlanco(observacion);
			return this;
		}

		public SolicitudRegistroDominio build() {
			return new SolicitudRegistroDominio(this);
		}
	}
}
