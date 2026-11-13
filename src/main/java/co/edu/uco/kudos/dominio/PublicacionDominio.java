package co.edu.uco.kudos.dominio;

import java.time.LocalDateTime;
import java.util.UUID;

import co.edu.uco.kudos.dominio.enums.EstadoPublicacion;
import co.edu.uco.kudos.transversal.catalogo.CatalogoMensajes;
import co.edu.uco.kudos.transversal.excepciones.KudosDominioExcepcion;
import co.edu.uco.kudos.transversal.utilitarios.UtilFecha;
import co.edu.uco.kudos.transversal.utilitarios.UtilObjeto;
import co.edu.uco.kudos.transversal.utilitarios.UtilTexto;
import co.edu.uco.kudos.transversal.utilitarios.UtilUUID;

public abstract class PublicacionDominio {

	private final UUID id;
	private final UsuarioDominio autor;
	private final String contenido;
	private final LocalDateTime fechaCreacion;
	private final EstadoPublicacion estado;

	protected PublicacionDominio(UUID id, UsuarioDominio autor, String contenido, LocalDateTime fechaCreacion,
			EstadoPublicacion estado) {
		this.id = UtilUUID.obtenerValorDefecto(id);
		this.autor = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(autor, new UsuarioDominio.Builder().build());
		this.contenido = UtilTexto.getUtilTexto().quitarEspacioEnBlanco(contenido);
		this.fechaCreacion = UtilFecha.obtenerValorDefecto(fechaCreacion);
		this.estado = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(estado, EstadoPublicacion.NO_DEFINIDO);
	}

	public UUID getId() {
		return id;
	}

	public UsuarioDominio getAutor() {
		return autor;
	}

	public String getContenido() {
		return contenido;
	}

	public LocalDateTime getFechaCreacion() {
		return fechaCreacion;
	}

	public EstadoPublicacion getEstado() {
		return estado;
	}

	public boolean estaVetada() {
		return estado == EstadoPublicacion.VETADO;
	}

	public boolean puedeRecibirReplicas() {
		return !estaVetada();
	}

	public boolean esVisiblePara(boolean puedeVerVetadas) {
		return !estaVetada() || puedeVerVetadas;
	}

	public boolean esDe(UUID idUsuario) {
		return autor.getId().equals(idUsuario);
	}

	public abstract KudoDominio obtenerKudoRaiz();

	public abstract PublicacionDominio publicar();

	protected void asegurarTransicion(EstadoPublicacion origen, EstadoPublicacion destino) {
		if (estado != origen) {
			throw KudosDominioExcepcion.crear(
					CatalogoMensajes.Publicacion.USUARIO_ERROR_TRANSICION_INVALIDA.formatted(estado, destino));
		}
	}

	protected void asegurarNoVetada() {
		if (estaVetada()) {
			throw KudosDominioExcepcion.crear(CatalogoMensajes.Publicacion.USUARIO_ERROR_VETADA);
		}
	}
}
