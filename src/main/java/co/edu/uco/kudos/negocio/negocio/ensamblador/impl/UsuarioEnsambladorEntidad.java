package co.edu.uco.kudos.negocio.negocio.ensamblador.impl;

import java.util.ArrayList;
import java.util.List;

import co.edu.uco.kudos.dominio.UsuarioDominio;
import co.edu.uco.kudos.dominio.enums.EstadoCuenta;
import co.edu.uco.kudos.entidad.UsuarioEntidad;
import co.edu.uco.kudos.negocio.negocio.ensamblador.EnsambladorEntidad;
import co.edu.uco.kudos.transversal.utilitarios.UtilObjeto;
import co.edu.uco.kudos.transversal.utilitarios.UtilTexto;

public final class UsuarioEnsambladorEntidad implements EnsambladorEntidad<UsuarioDominio, UsuarioEntidad> {

	private static final EnsambladorEntidad<UsuarioDominio, UsuarioEntidad> INSTANCIA = new UsuarioEnsambladorEntidad();

	private UsuarioEnsambladorEntidad() {
	}

	public static EnsambladorEntidad<UsuarioDominio, UsuarioEntidad> getInstancia() {
		return INSTANCIA;
	}

	@Override
	public UsuarioEntidad convertirAEntidad(UsuarioDominio dominio) {
		var dominioTmp = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(dominio, new UsuarioDominio.Builder().build());
		var destino = new UsuarioEntidad();
		destino.setId(dominioTmp.getId());
		destino.setNombres(dominioTmp.getNombres());
		destino.setApellidos(dominioTmp.getApellidos());
		destino.setCorreoInstitucional(dominioTmp.getCorreoInstitucional());
		destino.setNumeroCelular(dominioTmp.getNumeroCelular());
		destino.setClave(dominioTmp.getClave());
		destino.setCorreoVerificado(dominioTmp.isCorreoVerificado());
		destino.setCelularVerificado(dominioTmp.isCelularVerificado());
		destino.setEstadoCuenta(UtilTexto.getUtilTexto().textoDeEnum(dominioTmp.getEstadoCuenta()));
		destino.setRol(RolEnsambladorEntidad.getInstancia().convertirAEntidad(dominioTmp.getRol()));
		destino.setFechaRegistro(dominioTmp.getFechaRegistro());
		return destino;
	}

	@Override
	public UsuarioDominio convertirADominio(UsuarioEntidad origen) {
		var origenTmp = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(origen, new UsuarioEntidad());
		return new UsuarioDominio.Builder().id(origenTmp.getId()).nombres(origenTmp.getNombres()).apellidos(origenTmp.getApellidos()).correoInstitucional(origenTmp.getCorreoInstitucional()).numeroCelular(origenTmp.getNumeroCelular()).clave(origenTmp.getClave()).correoVerificado(origenTmp.isCorreoVerificado()).celularVerificado(origenTmp.isCelularVerificado()).estadoCuenta(EstadoCuenta.desdeTexto(origenTmp.getEstadoCuenta())).rol(RolEnsambladorEntidad.getInstancia().convertirADominio(origenTmp.getRol())).fechaRegistro(origenTmp.getFechaRegistro()).build();
	}

	@Override
	public List<UsuarioDominio> convertirADominio(List<UsuarioEntidad> lista) {
		var listaTmp = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(lista, new ArrayList<UsuarioEntidad>());
		return listaTmp.stream().map(this::convertirADominio).toList();
	}
}
