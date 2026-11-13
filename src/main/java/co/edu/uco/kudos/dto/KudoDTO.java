package co.edu.uco.kudos.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import co.edu.uco.kudos.transversal.utilitarios.UtilFecha;
import co.edu.uco.kudos.transversal.utilitarios.UtilObjeto;
import co.edu.uco.kudos.transversal.utilitarios.UtilTexto;
import co.edu.uco.kudos.transversal.utilitarios.UtilUUID;

public final class KudoDTO {

	private UUID id;
	private UsuarioDTO autor;
	private String contenido;
	private LocalDateTime fechaCreacion;
	private String estado;
	private UsuarioDTO destinatario;
	private CategoriaDTO categoria;

	public KudoDTO() {
		setId(UtilUUID.obtenerUUIDDefecto());
		setAutor(new UsuarioDTO());
		setContenido(UtilTexto.VACIO);
		setFechaCreacion(UtilFecha.FECHA_HORA_DEFECTO);
		setEstado(UtilTexto.VACIO);
		setDestinatario(new UsuarioDTO());
		setCategoria(new CategoriaDTO());
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = UtilUUID.obtenerValorDefecto(id);
	}

	public UsuarioDTO getAutor() {
		return autor;
	}

	public void setAutor(UsuarioDTO autor) {
		this.autor = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(autor, new UsuarioDTO());
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

	public UsuarioDTO getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(UsuarioDTO destinatario) {
		this.destinatario = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(destinatario, new UsuarioDTO());
	}

	public CategoriaDTO getCategoria() {
		return categoria;
	}

	public void setCategoria(CategoriaDTO categoria) {
		this.categoria = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(categoria, new CategoriaDTO());
	}
}
