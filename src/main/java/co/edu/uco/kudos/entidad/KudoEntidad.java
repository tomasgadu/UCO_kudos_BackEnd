package co.edu.uco.kudos.entidad;

import java.time.LocalDateTime;
import java.util.UUID;

import co.edu.uco.kudos.transversal.utilitarios.UtilFecha;
import co.edu.uco.kudos.transversal.utilitarios.UtilObjeto;
import co.edu.uco.kudos.transversal.utilitarios.UtilTexto;
import co.edu.uco.kudos.transversal.utilitarios.UtilUUID;

public final class KudoEntidad {

	private UUID id;
	private UsuarioEntidad autor;
	private String contenido;
	private LocalDateTime fechaCreacion;
	private String estado;
	private UsuarioEntidad destinatario;
	private CategoriaEntidad categoria;

	public KudoEntidad() {
		setId(UtilUUID.obtenerUUIDDefecto());
		setAutor(new UsuarioEntidad());
		setContenido(UtilTexto.VACIO);
		setFechaCreacion(UtilFecha.FECHA_HORA_DEFECTO);
		setEstado(UtilTexto.VACIO);
		setDestinatario(new UsuarioEntidad());
		setCategoria(new CategoriaEntidad());
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = UtilUUID.obtenerValorDefecto(id);
	}

	public UsuarioEntidad getAutor() {
		return autor;
	}

	public void setAutor(UsuarioEntidad autor) {
		this.autor = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(autor, new UsuarioEntidad());
	}

	public String getContenido() {
		return contenido;
	}

	public void setContenido(String contenido) {
		this.contenido = UtilTexto.getUtilTexto().quitarEspacioEnBlanco(contenido);
	}

	public LocalDateTime getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(LocalDateTime fechaCreacion) {
		this.fechaCreacion = UtilFecha.obtenerValorDefecto(fechaCreacion);
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = UtilTexto.getUtilTexto().quitarEspacioEnBlanco(estado);
	}

	public UsuarioEntidad getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(UsuarioEntidad destinatario) {
		this.destinatario = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(destinatario, new UsuarioEntidad());
	}

	public CategoriaEntidad getCategoria() {
		return categoria;
	}

	public void setCategoria(CategoriaEntidad categoria) {
		this.categoria = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(categoria, new CategoriaEntidad());
	}
}
