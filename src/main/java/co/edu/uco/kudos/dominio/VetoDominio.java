package co.edu.uco.kudos.dominio;

import java.time.LocalDateTime;
import java.util.UUID;

import co.edu.uco.kudos.transversal.catalogo.CatalogoMensajes;
import co.edu.uco.kudos.transversal.excepciones.KudosDominioExcepcion;
import co.edu.uco.kudos.transversal.utilitarios.UtilFecha;
import co.edu.uco.kudos.transversal.utilitarios.UtilObjeto;
import co.edu.uco.kudos.transversal.utilitarios.UtilTexto;
import co.edu.uco.kudos.transversal.utilitarios.UtilUUID;

public final class VetoDominio {

	private final UUID id;
	private final UUID idPublicacion;
	private final UsuarioDominio autoridad;
	private final UUID idReporteOrigen;
	private final String justificacion;
	private final LocalDateTime fechaVeto;

	private VetoDominio(Builder builder) {
		this.id = builder.id;
		this.idPublicacion = builder.idPublicacion;
		this.autoridad = builder.autoridad;
		this.idReporteOrigen = builder.idReporteOrigen;
		this.justificacion = builder.justificacion;
		this.fechaVeto = builder.fechaVeto;
	}

	public UUID getId() {
		return id;
	}

	public UUID getIdPublicacion() {
		return idPublicacion;
	}

	public UsuarioDominio getAutoridad() {
		return autoridad;
	}

	public UUID getIdReporteOrigen() {
		return idReporteOrigen;
	}

	public String getJustificacion() {
		return justificacion;
	}

	public LocalDateTime getFechaVeto() {
		return fechaVeto;
	}

	private static final int MINIMO_JUSTIFICACION = 20;

	public static VetoDominio crear(UUID id, UUID idPublicacion, UsuarioDominio autoridad, UUID idReporteOrigen,
			String justificacion, LocalDateTime ahora) {
		if (UtilTexto.getUtilTexto().quitarEspacioEnBlanco(justificacion).length() < MINIMO_JUSTIFICACION) {
			throw KudosDominioExcepcion.crear(CatalogoMensajes.Veto.USUARIO_ERROR_JUSTIFICACION);
		}
		return new Builder().id(id).idPublicacion(idPublicacion).autoridad(autoridad).idReporteOrigen(idReporteOrigen)
				.justificacion(justificacion).fechaVeto(ahora).build();
	}

	public Builder copia() {
		return new Builder().id(id).idPublicacion(idPublicacion).autoridad(autoridad).idReporteOrigen(idReporteOrigen).justificacion(justificacion).fechaVeto(fechaVeto);
	}

	public static class Builder {

		private UUID id;
		private UUID idPublicacion;
		private UsuarioDominio autoridad;
		private UUID idReporteOrigen;
		private String justificacion;
		private LocalDateTime fechaVeto;

		public Builder() {
			id = UtilUUID.obtenerUUIDDefecto();
			idPublicacion = UtilUUID.obtenerUUIDDefecto();
			autoridad = new UsuarioDominio.Builder().build();
			idReporteOrigen = UtilUUID.obtenerUUIDDefecto();
			justificacion = UtilTexto.VACIO;
			fechaVeto = UtilFecha.FECHA_HORA_DEFECTO;
		}

		public Builder id(UUID id) {
			this.id = UtilUUID.obtenerValorDefecto(id);
			return this;
		}

		public Builder idPublicacion(UUID idPublicacion) {
			this.idPublicacion = UtilUUID.obtenerValorDefecto(idPublicacion);
			return this;
		}

		public Builder autoridad(UsuarioDominio autoridad) {
			this.autoridad = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(autoridad, new UsuarioDominio.Builder().build());
			return this;
		}

		public Builder idReporteOrigen(UUID idReporteOrigen) {
			this.idReporteOrigen = UtilUUID.obtenerValorDefecto(idReporteOrigen);
			return this;
		}

		public Builder justificacion(String justificacion) {
			this.justificacion = UtilTexto.getUtilTexto().quitarEspacioEnBlanco(justificacion);
			return this;
		}

		public Builder fechaVeto(LocalDateTime fechaVeto) {
			this.fechaVeto = UtilFecha.obtenerValorDefecto(fechaVeto);
			return this;
		}

		public VetoDominio build() {
			return new VetoDominio(this);
		}
	}
}
