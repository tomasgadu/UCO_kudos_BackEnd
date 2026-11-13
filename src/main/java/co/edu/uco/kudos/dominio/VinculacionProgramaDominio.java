package co.edu.uco.kudos.dominio;

import java.time.LocalDateTime;
import java.util.UUID;

import co.edu.uco.kudos.transversal.utilitarios.UtilFecha;
import co.edu.uco.kudos.transversal.utilitarios.UtilObjeto;
import co.edu.uco.kudos.transversal.utilitarios.UtilUUID;

public final class VinculacionProgramaDominio {

	private final UUID id;
	private final UsuarioDominio usuario;
	private final ProgramaAcademicoDominio programaAcademico;
	private final LocalDateTime fechaInicio;
	private final LocalDateTime fechaFin;

	private VinculacionProgramaDominio(Builder builder) {
		this.id = builder.id;
		this.usuario = builder.usuario;
		this.programaAcademico = builder.programaAcademico;
		this.fechaInicio = builder.fechaInicio;
		this.fechaFin = builder.fechaFin;
	}

	public UUID getId() {
		return id;
	}

	public UsuarioDominio getUsuario() {
		return usuario;
	}

	public ProgramaAcademicoDominio getProgramaAcademico() {
		return programaAcademico;
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
		return new Builder().id(id).usuario(usuario).programaAcademico(programaAcademico).fechaInicio(fechaInicio).fechaFin(fechaFin);
	}

	public static class Builder {

		private UUID id;
		private UsuarioDominio usuario;
		private ProgramaAcademicoDominio programaAcademico;
		private LocalDateTime fechaInicio;
		private LocalDateTime fechaFin;

		public Builder() {
			id = UtilUUID.obtenerUUIDDefecto();
			usuario = new UsuarioDominio.Builder().build();
			programaAcademico = new ProgramaAcademicoDominio.Builder().build();
			fechaInicio = UtilFecha.FECHA_HORA_DEFECTO;
			fechaFin = UtilFecha.FECHA_HORA_DEFECTO;
		}

		public Builder id(UUID id) {
			this.id = UtilUUID.obtenerValorDefecto(id);
			return this;
		}

		public Builder usuario(UsuarioDominio usuario) {
			this.usuario = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(usuario, new UsuarioDominio.Builder().build());
			return this;
		}

		public Builder programaAcademico(ProgramaAcademicoDominio programaAcademico) {
			this.programaAcademico = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(programaAcademico, new ProgramaAcademicoDominio.Builder().build());
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

		public VinculacionProgramaDominio build() {
			return new VinculacionProgramaDominio(this);
		}
	}
}
