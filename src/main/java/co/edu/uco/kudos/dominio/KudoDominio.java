package co.edu.uco.kudos.dominio;

import java.time.LocalDateTime;
import java.util.UUID;

import co.edu.uco.kudos.dominio.enums.EstadoPublicacion;
import co.edu.uco.kudos.transversal.catalogo.CatalogoMensajes;
import co.edu.uco.kudos.transversal.excepciones.KudosDominioExcepcion;
import co.edu.uco.kudos.transversal.utilitarios.UtilObjeto;

public final class KudoDominio extends PublicacionDominio {

	private final UsuarioDominio destinatario;
	private final CategoriaDominio categoria;

	private KudoDominio(Builder builder) {
		super(builder.id, builder.autor, builder.contenido, builder.fechaCreacion, builder.estado);
		this.destinatario = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(builder.destinatario,
				new UsuarioDominio.Builder().build());
		this.categoria = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(builder.categoria,
				new CategoriaDominio.Builder().build());
	}

	public static KudoDominio crear(UUID id, UsuarioDominio autor, UsuarioDominio destinatario,
			CategoriaDominio categoria, String contenido, LocalDateTime ahora) {
		if (autor.getId().equals(destinatario.getId())) {
			throw KudosDominioExcepcion.crear(CatalogoMensajes.Kudo.USUARIO_ERROR_MISMO_DESTINATARIO);
		}
		return new Builder().id(id).autor(autor).destinatario(destinatario).categoria(categoria).contenido(contenido)
				.fechaCreacion(ahora).estado(EstadoPublicacion.CREADO).build();
	}

	public UsuarioDominio getDestinatario() {
		return destinatario;
	}

	public CategoriaDominio getCategoria() {
		return categoria;
	}

	@Override
	public KudoDominio obtenerKudoRaiz() {
		return this;
	}

	@Override
	public KudoDominio publicar() {
		asegurarTransicion(EstadoPublicacion.CREADO, EstadoPublicacion.VISIBLE);
		return copia().estado(EstadoPublicacion.VISIBLE).build();
	}

	public Builder copia() {
		return new Builder().id(getId()).autor(getAutor()).contenido(getContenido()).fechaCreacion(getFechaCreacion())
				.estado(getEstado()).destinatario(destinatario).categoria(categoria);
	}

	public static class Builder {

		private UUID id;
		private UsuarioDominio autor;
		private String contenido;
		private LocalDateTime fechaCreacion;
		private EstadoPublicacion estado;
		private UsuarioDominio destinatario;
		private CategoriaDominio categoria;

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

		public Builder destinatario(UsuarioDominio destinatario) {
			this.destinatario = destinatario;
			return this;
		}

		public Builder categoria(CategoriaDominio categoria) {
			this.categoria = categoria;
			return this;
		}

		public KudoDominio build() {
			return new KudoDominio(this);
		}
	}
}
