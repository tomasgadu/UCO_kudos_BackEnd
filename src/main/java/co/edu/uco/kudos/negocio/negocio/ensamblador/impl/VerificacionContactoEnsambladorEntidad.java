package co.edu.uco.kudos.negocio.negocio.ensamblador.impl;

import java.util.ArrayList;
import java.util.List;

import co.edu.uco.kudos.dominio.VerificacionContactoDominio;
import co.edu.uco.kudos.dominio.enums.TipoVerificacion;
import co.edu.uco.kudos.dominio.enums.EstadoVerificacion;
import co.edu.uco.kudos.entidad.VerificacionContactoEntidad;
import co.edu.uco.kudos.negocio.negocio.ensamblador.EnsambladorEntidad;
import co.edu.uco.kudos.transversal.utilitarios.UtilObjeto;

public final class VerificacionContactoEnsambladorEntidad implements EnsambladorEntidad<VerificacionContactoDominio, VerificacionContactoEntidad> {

	private static final EnsambladorEntidad<VerificacionContactoDominio, VerificacionContactoEntidad> INSTANCIA = new VerificacionContactoEnsambladorEntidad();

	private VerificacionContactoEnsambladorEntidad() {
	}

	public static EnsambladorEntidad<VerificacionContactoDominio, VerificacionContactoEntidad> getInstancia() {
		return INSTANCIA;
	}

	@Override
	public VerificacionContactoEntidad convertirAEntidad(VerificacionContactoDominio dominio) {
		var dominioTmp = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(dominio, new VerificacionContactoDominio.Builder().build());
		var destino = new VerificacionContactoEntidad();
		destino.setId(dominioTmp.getId());
		destino.setUsuario(UsuarioEnsambladorEntidad.getInstancia().convertirAEntidad(dominioTmp.getUsuario()));
		destino.setTipo(dominioTmp.getTipo().name());
		destino.setValorVerificado(dominioTmp.getValorVerificado());
		destino.setCodigo(dominioTmp.getCodigo());
		destino.setFechaEnvio(dominioTmp.getFechaEnvio());
		destino.setFechaExpiracion(dominioTmp.getFechaExpiracion());
		destino.setFechaVerificacion(dominioTmp.getFechaVerificacion());
		destino.setEstado(dominioTmp.getEstado().name());
		return destino;
	}

	@Override
	public VerificacionContactoDominio convertirADominio(VerificacionContactoEntidad origen) {
		var origenTmp = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(origen, new VerificacionContactoEntidad());
		return new VerificacionContactoDominio.Builder().id(origenTmp.getId()).usuario(UsuarioEnsambladorEntidad.getInstancia().convertirADominio(origenTmp.getUsuario())).tipo(TipoVerificacion.desdeTexto(origenTmp.getTipo())).valorVerificado(origenTmp.getValorVerificado()).codigo(origenTmp.getCodigo()).fechaEnvio(origenTmp.getFechaEnvio()).fechaExpiracion(origenTmp.getFechaExpiracion()).fechaVerificacion(origenTmp.getFechaVerificacion()).estado(EstadoVerificacion.desdeTexto(origenTmp.getEstado())).build();
	}

	@Override
	public List<VerificacionContactoDominio> convertirADominio(List<VerificacionContactoEntidad> lista) {
		var listaTmp = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(lista, new ArrayList<VerificacionContactoEntidad>());
		return listaTmp.stream().map(this::convertirADominio).toList();
	}
}
