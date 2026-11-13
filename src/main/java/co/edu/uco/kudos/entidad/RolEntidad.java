package co.edu.uco.kudos.entidad;

import java.util.UUID;

import co.edu.uco.kudos.transversal.utilitarios.UtilTexto;
import co.edu.uco.kudos.transversal.utilitarios.UtilUUID;

public final class RolEntidad {

	private UUID id;
	private String codigo;
	private String nombre;
	private String descripcion;
	private UUID idRolAprobador;

	public RolEntidad() {
		setId(UtilUUID.obtenerUUIDDefecto());
		setCodigo(UtilTexto.VACIO);
		setNombre(UtilTexto.VACIO);
		setDescripcion(UtilTexto.VACIO);
		setIdRolAprobador(UtilUUID.obtenerUUIDDefecto());
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = UtilUUID.obtenerValorDefecto(id);
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = UtilTexto.getUtilTexto().quitarEspacioEnBlanco(codigo);
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = UtilTexto.getUtilTexto().quitarEspacioEnBlanco(nombre);
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = UtilTexto.getUtilTexto().quitarEspacioEnBlanco(descripcion);
	}

	public UUID getIdRolAprobador() {
		return idRolAprobador;
	}

	public void setIdRolAprobador(UUID idRolAprobador) {
		this.idRolAprobador = UtilUUID.obtenerValorDefecto(idRolAprobador);
	}
}
