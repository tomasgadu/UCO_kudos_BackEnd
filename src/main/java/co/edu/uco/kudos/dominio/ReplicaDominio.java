package co.edu.uco.kudos.dominio;

import java.time.LocalDateTime;
import java.util.UUID;

import co.edu.uco.kudos.dominio.enums.EstadoPublicacion;
import co.edu.uco.kudos.transversal.utilitarios.UtilObjeto;
import co.edu.uco.kudos.transversal.utilitarios.UtilUUID;

public final class ReplicaDominio extends PublicacionDominio {

	private final UUID idPadre;
	private final KudoDominio kudoRaiz;

	private ReplicaDominio(Builder builder) {
		super(builder.id, builder.autor, builder.contenido, builder.fechaCreacion, builder.estado);
		this.idPadre = UtilUUID.obtenerValorDefecto(builder.idPadre);
		this.kudoRaiz = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(builder.kudoRaiz,
				new KudoDominio.Builder().build());
	}

	public static ReplicaDominio responder(UUID id, UsuarioDominio autor, PublicacionDominio padre, String contenido,
			LocalDateTime ahora) {
		padre.asegurarNoVetada();
		return new Builder().id(id).autor(autor).contenido(contenido).fechaCreacion(ahora)
				.estado(EstadoPublicacion.CREADO).idPadre(padre.getId()).kudoRaiz(padre.obtenerKudoRaiz()).build();
	}

	public UUID getIdPadre() {
		return idPadre;
	}

	public KudoDominio getKudoRaiz() {
		return kudoRaiz;
	}

	@Override
	public KudoDominio obtenerKudoRaiz() {
		return kudoRaiz;
	}

	@Override
	public ReplicaDominio publicar() {
		asegurarTransicion(EstadoPublicacion.CREADO, EstadoPublicacion.VISIBLE);
		return copia().estado(EstadoPublicacion.VISIBLE).build();
	}

	public Builder copia() {
		return new Builder().id(getId()).autor(getAutor()).contenido(getContenido()).fechaCreacion(getFechaCreacion())
				.estado(getEstado()).idPadre(idPadre).kudoRaiz(kudoRaiz);
	}

	public static class Builder {

		private UUID id;
		private UsuarioDominio autor;
		private String contenido;
		private LocalDateTime fechaCreacion;
		private EstadoPublicacion estado;
		private UUID idPadre;
		private KudoDominio kudoRaiz;

		public Builder id(UUID id) {
			this.id = id;
			return this;
		}

		public Builder autor(UsuarioDominio autor) {
			this.autor = autor;
			return this;
		}

		public Builder contenido(String contenido) {
			this.contenido = contenido;
			return this;
		}

		public Builder fechaCreacion(LocalDateTime fechaCreacion) {
			this.fechaCreacion = fechaCreacion;
			return this;
		}

		public Builder estado(EstadoPublicacion estado) {
			this.estado = estado;
			return this;
		}

		public Builder idPadre(UUID idPadre) {
			this.idPadre = idPadre;
			return this;
		}

		public Builder kudoRaiz(KudoDominio kudoRaiz) {
			this.kudoRaiz = kudoRaiz;
			return this;
		}

		public ReplicaDominio build() {
			return new ReplicaDominio(this);
		}
	}
}
