package co.edu.uco.kudos.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import co.edu.uco.kudos.transversal.utilitarios.UtilFecha;
import co.edu.uco.kudos.transversal.utilitarios.UtilObjeto;
import co.edu.uco.kudos.transversal.utilitarios.UtilUUID;

public final class CalificacionDTO {

	private UUID id;
	private UUID idPublicacion;
	private UsuarioDTO autor;
	private int estrellas;
	private LocalDateTime fechaRegistro;
	private LocalDateTime fechaModificacion;

	public CalificacionDTO() {
		setId(UtilUUID.obtenerUUIDDefecto());
		setIdPublicacion(UtilUUID.obtenerUUIDDefecto());
		setAutor(new UsuarioDTO());
		setEstrellas(0);
		setFechaRegistro(UtilFecha.FECHA_HORA_DEFECTO);
		setFechaModificacion(UtilFecha.FECHA_HORA_DEFECTO);
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = UtilUUID.obtenerValorDefecto(id);
	}

	public UUID getIdPublicacion() {
		return idPublicacion;
	}

	public void setIdPublicacion(UUID idPublicacion) {
		this.idPublicacion = UtilUUID.obtenerValorDefecto(idPublicacion);
	}

	public UsuarioDTO getAutor() {
		return autor;
	}

	public void setAutor(UsuarioDTO autor) {
		this.autor = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(autor, new UsuarioDTO());
	}

	public int getEstrellas() {
		return estrellas;
	}

	public void setEstrellas(int estrellas) {
		this.estrellas = estrellas;
	}

	public LocalDateTime getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(LocalDateTime fechaRegistro) {
		this.fechaRegistro = UtilFecha.obtenerValorDefecto(fechaRegistro);
	}

	public LocalDateTime getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(LocalDateTime fechaModificacion) {
		this.fechaModificacion = UtilFecha.obtenerValorDefecto(fechaModificacion);
	}
}
