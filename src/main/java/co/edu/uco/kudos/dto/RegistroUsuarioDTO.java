package co.edu.uco.kudos.dto;

import java.util.UUID;

import co.edu.uco.kudos.transversal.utilitarios.UtilTexto;
import co.edu.uco.kudos.transversal.utilitarios.UtilUUID;

public final class RegistroUsuarioDTO {

	private String nombres;
	private String apellidos;
	private String correoInstitucional;
	private String numeroCelular;
	private String clave;
	private UUID idRol;
	private UUID idFacultad;
	private UUID idProgramaAcademico;

	public RegistroUsuarioDTO() {
		setNombres(UtilTexto.VACIO);
		setApellidos(UtilTexto.VACIO);
		setCorreoInstitucional(UtilTexto.VACIO);
		setNumeroCelular(UtilTexto.VACIO);
		setClave(UtilTexto.VACIO);
		setIdRol(UtilUUID.obtenerUUIDDefecto());
		setIdFacultad(UtilUUID.obtenerUUIDDefecto());
		setIdProgramaAcademico(UtilUUID.obtenerUUIDDefecto());
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = UtilTexto.getUtilTexto().quitarEspacioEnBlanco(nombres);
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = UtilTexto.getUtilTexto().quitarEspacioEnBlanco(apellidos);
	}

	public String getCorreoInstitucional() {
		return correoInstitucional;
	}

	public void setCorreoInstitucional(String correoInstitucional) {
		this.correoInstitucional = UtilTexto.getUtilTexto().quitarEspacioEnBlanco(correoInstitucional);
	}

	public String getNumeroCelular() {
		return numeroCelular;
	}

	public void setNumeroCelular(String numeroCelular) {
		this.numeroCelular = UtilTexto.getUtilTexto().quitarEspacioEnBlanco(numeroCelular);
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = UtilTexto.getUtilTexto().obtenerValorDefecto(clave);
	}

	public UUID getIdRol() {
		return idRol;
	}

	public void setIdRol(UUID idRol) {
		this.idRol = UtilUUID.obtenerValorDefecto(idRol);
	}

	public UUID getIdFacultad() {
		return idFacultad;
	}

	public void setIdFacultad(UUID idFacultad) {
		this.idFacultad = UtilUUID.obtenerValorDefecto(idFacultad);
	}

	public UUID getIdProgramaAcademico() {
		return idProgramaAcademico;
	}

	public void setIdProgramaAcademico(UUID idProgramaAcademico) {
		this.idProgramaAcademico = UtilUUID.obtenerValorDefecto(idProgramaAcademico);
	}
}
