package co.edu.uco.kudos.dominio;

import java.time.LocalDateTime;
import java.util.UUID;

import co.edu.uco.kudos.transversal.catalogo.CatalogoMensajes;
import co.edu.uco.kudos.transversal.excepciones.KudosDominioExcepcion;
import co.edu.uco.kudos.transversal.utilitarios.UtilFecha;
import co.edu.uco.kudos.transversal.utilitarios.UtilObjeto;
import co.edu.uco.kudos.transversal.utilitarios.UtilTexto;
import co.edu.uco.kudos.transversal.utilitarios.UtilUUID;

public final class CalificacionDominio {

	private final UUID id;
	private final UUID idPublicacion;
	private final UsuarioDominio autor;
	private final int estrellas;
	private final LocalDateTime fechaRegistro;
	private final LocalDateTime fechaModificacion;

	private CalificacionDominio(Builder builder) {
		this.id = builder.id;
		this.idPublicacion = builder.idPublicacion;
		this.autor = builder.autor;
		this.estrellas = builder.estrellas;
		this.fechaRegistro = builder.fechaRegistro;
		this.fechaModificacion = builder.fechaModificacion;
	}

	public UUID getId() {
		return id;
	}

	public UUID getIdPublicacion() {
		return idPublicacion;
	}

	public UsuarioDominio getAutor() {
		return autor;
	}

	public int getEstrellas() {
		return estrellas;
	}

	public LocalDateTime getFechaRegistro() {
		return fechaRegistro;
	}

	public LocalDateTime getFechaModificacion() {
		return fechaModificacion;
	}

	private static final int MINIMO_ESTRELLAS = 1;
	private static final int MAXIMO_ESTRELLAS = 5;

	public static CalificacionDominio crear(UUID id, UUID idPublicacion, UsuarioDominio autor, int estrellas,
			LocalDateTime ahora) {
		asegurarEstrellas(estrellas);
		return new Builder().id(id).idPublicacion(idPublicacion).autor(autor).estrellas(estrellas).fechaRegistro(ahora)
				.build();
	}

	public CalificacionDominio cambiar(int nuevasEstrellas, LocalDateTime ahora) {
		asegurarEstrellas(nuevasEstrellas);
		return copia().estrellas(nuevasEstrellas).fechaModificacion(ahora).build();
	}

	private static void asegurarEstrellas(int estrellas) {
		if (estrellas < MINIMO_ESTRELLAS || estrellas > MAXIMO_ESTRELLAS) {
			throw KudosDominioExcepcion.crear(CatalogoMensajes.Calificacion.USUARIO_ERROR_ESTRELLAS);
		}
	}

	public Builder copia() {
		return new Builder().id(id).idPublicacion(idPublicacion).autor(autor).estrellas(estrellas).fechaRegistro(fechaRegistro).fechaModificacion(fechaModificacion);
	}

	public static class Builder {

		private UUID id;
		private UUID idPublicacion;
		private UsuarioDominio autor;
		private int estrellas;
		private LocalDateTime fechaRegistro;
		private LocalDateTime fechaModificacion;

		public Builder() {
			id = UtilUUID.obtenerUUIDDefecto();
			idPublicacion = UtilUUID.obtenerUUIDDefecto();
			autor = new UsuarioDominio.Builder().build();
			estrellas = 0;
			fechaRegistro = UtilFecha.FECHA_HORA_DEFECTO;
			fechaModificacion = UtilFecha.FECHA_HORA_DEFECTO;
		}

		public Builder id(UUID id) {
			this.id = UtilUUID.obtenerValorDefecto(id);
			return this;
		}

		public Builder idPublicacion(UUID idPublicacion) {
			this.idPublicacion = UtilUUID.obtenerValorDefecto(idPublicacion);
			return this;
		}

		public Builder autor(UsuarioDominio autor) {
			this.autor = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(autor, new UsuarioDominio.Builder().build());
			return this;
		}

		public Builder estrellas(int estrellas) {
			this.estrellas = estrellas;
			return this;
		}

		public Builder fechaRegistro(LocalDateTime fechaRegistro) {
			this.fechaRegistro = UtilFecha.obtenerValorDefecto(fechaRegistro);
			return this;
		}

		public Builder fechaModificacion(LocalDateTime fechaModificacion) {
			this.fechaModificacion = UtilFecha.obtenerValorDefecto(fechaModificacion);
			return this;
		}

		public CalificacionDominio build() {
			return new CalificacionDominio(this);
		}
	}
}
