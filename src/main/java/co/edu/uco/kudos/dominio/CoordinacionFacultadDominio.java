package co.edu.uco.kudos.dominio;

import java.time.LocalDateTime;
import java.util.UUID;

import co.edu.uco.kudos.transversal.utilitarios.UtilFecha;
import co.edu.uco.kudos.transversal.utilitarios.UtilObjeto;
import co.edu.uco.kudos.transversal.utilitarios.UtilUUID;

public final class CoordinacionFacultadDominio {

	private final UUID id;
	private final UsuarioDominio coordinador;
	private final FacultadDominio facultad;
	private final LocalDateTime fechaInicio;
	private final LocalDateTime fechaFin;

	private CoordinacionFacultadDominio(Builder builder) {
		this.id = builder.id;
		this.coordinador = builder.coordinador;
		this.facultad = builder.facultad;
		this.fechaInicio = builder.fechaInicio;
		this.fechaFin = builder.fechaFin;
	}

	public UUID getId() {
		return id;
	}

	public UsuarioDominio getCoordinador() {
		return coordinador;
	}

	public FacultadDominio getFacultad() {
		return facultad;
	}

	public LocalDateTime getFechaInicio() {
		return fechaInicio;
	}

	public LocalDateTime getFechaFin() {
		return fechaFin;
	}

	public boolean estaVigente() {
		return UtilFecha.esValorDefecto(fechaFin);
	}

	public Builder copia() {
		return new Builder().id(id).coordinador(coordinador).facultad(facultad).fechaInicio(fechaInicio).fechaFin(fechaFin);
	}

	public static class Builder {

		private UUID id;
		private UsuarioDominio coordinador;
		private FacultadDominio facultad;
		private LocalDateTime fechaInicio;
		private LocalDateTime fechaFin;

		public Builder() {
			id = UtilUUID.obtenerUUIDDefecto();
			coordinador = new UsuarioDominio.Builder().build();
			facultad = new FacultadDominio.Builder().build();
			fechaInicio = UtilFecha.FECHA_HORA_DEFECTO;
			fechaFin = UtilFecha.FECHA_HORA_DEFECTO;
		}

		public Builder id(UUID id) {
			this.id = UtilUUID.obtenerValorDefecto(id);
			return this;
		}

		public Builder coordinador(UsuarioDominio coordinador) {
			this.coordinador = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(coordinador, new UsuarioDominio.Builder().build());
			return this;
		}

		public Builder facultad(FacultadDominio facultad) {
			this.facultad = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(facultad, new FacultadDominio.Builder().build());
			return this;
		}

		public Builder fechaInicio(LocalDateTime fechaInicio) {
			this.fechaInicio = UtilFecha.obtenerValorDefecto(fechaInicio);
			return this;
		}

		public Builder fechaFin(LocalDateTime fechaFin) {
			this.fechaFin = UtilFecha.obtenerValorDefecto(fechaFin);
			return this;
		}

		public CoordinacionFacultadDominio build() {
			return new CoordinacionFacultadDominio(this);
		}
	}
}
