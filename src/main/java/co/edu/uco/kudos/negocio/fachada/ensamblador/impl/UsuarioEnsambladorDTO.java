package co.edu.uco.kudos.negocio.fachada.ensamblador.impl;

import java.util.ArrayList;
import java.util.List;

import co.edu.uco.kudos.dominio.UsuarioDominio;
import co.edu.uco.kudos.dominio.enums.EstadoCuenta;
import co.edu.uco.kudos.dto.UsuarioDTO;
import co.edu.uco.kudos.negocio.fachada.ensamblador.EnsambladorDTO;
import co.edu.uco.kudos.transversal.utilitarios.UtilObjeto;
import co.edu.uco.kudos.transversal.utilitarios.UtilTexto;

public final class UsuarioEnsambladorDTO implements EnsambladorDTO<UsuarioDominio, UsuarioDTO> {

	private static final EnsambladorDTO<UsuarioDominio, UsuarioDTO> INSTANCIA = new UsuarioEnsambladorDTO();

	private UsuarioEnsambladorDTO() {
	}

	public static EnsambladorDTO<UsuarioDominio, UsuarioDTO> getInstancia() {
		return INSTANCIA;
	}

	@Override
	public UsuarioDTO convertirADTO(UsuarioDominio dominio) {
		var dominioTmp = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(dominio, new UsuarioDominio.Builder().build());
		var destino = new UsuarioDTO();
		destino.setId(dominioTmp.getId());
		destino.setNombres(dominioTmp.getNombres());
		destino.setApellidos(dominioTmp.getApellidos());
		destino.setCorreoInstitucional(dominioTmp.getCorreoInstitucional());
		destino.setNumeroCelular(dominioTmp.getNumeroCelular());
		// La clave (cifrada) nunca sale del Back-End (RNF-003).
		destino.setClave(UtilTexto.VACIO);
		destino.setCorreoVerificado(dominioTmp.isCorreoVerificado());
		destino.setCelularVerificado(dominioTmp.isCelularVerificado());
		destino.setEstadoCuenta(dominioTmp.getEstadoCuenta().name());
		destino.setRol(RolEnsambladorDTO.getInstancia().convertirADTO(dominioTmp.getRol()));
		destino.setFechaRegistro(dominioTmp.getFechaRegistro());
		return destino;
	}

	@Override
	public UsuarioDominio convertirADominio(UsuarioDTO origen) {
		var origenTmp = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(origen, new UsuarioDTO());
		return new UsuarioDominio.Builder().id(origenTmp.getId()).nombres(origenTmp.getNombres()).apellidos(origenTmp.getApellidos()).correoInstitucional(origenTmp.getCorreoInstitucional()).numeroCelular(origenTmp.getNumeroCelular()).clave(origenTmp.getClave()).correoVerificado(origenTmp.isCorreoVerificado()).celularVerificado(origenTmp.isCelularVerificado()).estadoCuenta(EstadoCuenta.desdeTexto(origenTmp.getEstadoCuenta())).rol(RolEnsambladorDTO.getInstancia().convertirADominio(origenTmp.getRol())).fechaRegistro(origenTmp.getFechaRegistro()).build();
	}

	@Override
	public List<UsuarioDTO> convertirADTO(List<UsuarioDominio> lista) {
		var listaTmp = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(lista, new ArrayList<UsuarioDominio>());
		return listaTmp.stream().map(this::convertirADTO).toList();
	}
}
