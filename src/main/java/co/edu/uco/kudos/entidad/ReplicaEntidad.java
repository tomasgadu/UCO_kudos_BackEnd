package co.edu.uco.kudos.entidad;

import java.time.LocalDateTime;
import java.util.UUID;

import co.edu.uco.kudos.transversal.utilitarios.UtilFecha;
import co.edu.uco.kudos.transversal.utilitarios.UtilObjeto;
import co.edu.uco.kudos.transversal.utilitarios.UtilTexto;
import co.edu.uco.kudos.transversal.utilitarios.UtilUUID;

public final class ReplicaEntidad {

	private UUID id;
	private UsuarioEntidad autor;
	private String contenido;
	private LocalDateTime fechaCreacion;
	private String estado;
	private UUID idPadre;
	private UUID idKudoRaiz;

	public ReplicaEntidad() {
		setId(UtilUUID.obtenerUUIDDefecto());
		setAutor(new UsuarioEntidad());
		setContenido(UtilTexto.VACIO);
		setFechaCreacion(UtilFecha.FECHA_HORA_DEFECTO);
		setEstado(UtilTexto.VACIO);
		setIdPadre(UtilUUID.obtenerUUIDDefecto());
		setIdKudoRaiz(UtilUUID.obtenerUUIDDefecto());
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

	public UUID getIdPadre() {
		return idPadre;
	}

	public void setIdPadre(UUID idPadre) {
		this.idPadre = UtilUUID.obtenerValorDefecto(idPadre);
	}

	public UUID getIdKudoRaiz() {
		return idKudoRaiz;
	}

	public void setIdKudoRaiz(UUID idKudoRaiz) {
		this.idKudoRaiz = UtilUUID.obtenerValorDefecto(idKudoRaiz);
	}
}
