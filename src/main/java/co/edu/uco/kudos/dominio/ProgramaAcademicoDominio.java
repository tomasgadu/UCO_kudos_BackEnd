package co.edu.uco.kudos.dominio;

import java.time.LocalDateTime;
import java.util.UUID;

import co.edu.uco.kudos.dominio.enums.EstadoRegistro;
import co.edu.uco.kudos.transversal.utilitarios.UtilFecha;
import co.edu.uco.kudos.transversal.utilitarios.UtilObjeto;
import co.edu.uco.kudos.transversal.utilitarios.UtilTexto;
import co.edu.uco.kudos.transversal.utilitarios.UtilUUID;

public final class ProgramaAcademicoDominio {

	private final UUID id;
	private final String nombre;
	private final FacultadDominio facultad;
	private final EstadoRegistro estado;
	private final LocalDateTime fechaCreacion;

	private ProgramaAcademicoDominio(Builder builder) {
		this.id = builder.id;
		this.nombre = builder.nombre;
		this.facultad = builder.facultad;
		this.estado = builder.estado;
		this.fechaCreacion = builder.fechaCreacion;
	}

	public UUID getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public FacultadDominio getFacultad() {
		return facultad;
	}

	public EstadoRegistro getEstado() {
		return estado;
	}

	public LocalDateTime getFechaCreacion() {
		return fechaCreacion;
	}

	public boolean estaActivo() {
		return estado == EstadoRegistro.ACTIVA;
	}

	public Builder copia() {
		return new Builder().id(id).nombre(nombre).facultad(facultad).estado(estado).fechaCreacion(fechaCreacion);
	}

	public static class Builder {

		private UUID id;
		private String nombre;
		private FacultadDominio facultad;
		private EstadoRegistro estado;
		private LocalDateTime fechaCreacion;

		public Builder() {
			id = UtilUUID.obtenerUUIDDefecto();
			nombre = UtilTexto.VACIO;
			facultad = new FacultadDominio.Builder().build();
			estado = EstadoRegistro.NO_DEFINIDO;
			fechaCreacion = UtilFecha.FECHA_HORA_DEFECTO;
		}

		public Builder id(UUID id) {
			this.id = UtilUUID.obtenerValorDefecto(id);
			return this;
		}

		public Builder nombre(String nombre) {
			this.nombre = UtilTexto.getUtilTexto().quitarEspacioEnBlanco(nombre);
			return this;
		}

		public Builder facultad(FacultadDominio facultad) {
			this.facultad = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(facultad, new FacultadDominio.Builder().build());
			return this;
		}

		public Builder estado(EstadoRegistro estado) {
			this.estado = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(estado, EstadoRegistro.NO_DEFINIDO);
			return this;
		}

		public Builder fechaCreacion(LocalDateTime fechaCreacion) {
			this.fechaCreacion = UtilFecha.obtenerValorDefecto(fechaCreacion);
			return this;
		}

		public ProgramaAcademicoDominio build() {
			return new ProgramaAcademicoDominio(this);
		}
	}
}
