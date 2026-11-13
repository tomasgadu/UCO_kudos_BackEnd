package co.edu.uco.kudos.negocio.fachada.ensamblador.impl;

import java.util.ArrayList;
import java.util.List;

import co.edu.uco.kudos.dominio.VerificacionContactoDominio;
import co.edu.uco.kudos.dominio.enums.TipoVerificacion;
import co.edu.uco.kudos.dominio.enums.EstadoVerificacion;
import co.edu.uco.kudos.dto.VerificacionContactoDTO;
import co.edu.uco.kudos.negocio.fachada.ensamblador.EnsambladorDTO;
import co.edu.uco.kudos.transversal.utilitarios.UtilObjeto;

public final class VerificacionContactoEnsambladorDTO implements EnsambladorDTO<VerificacionContactoDominio, VerificacionContactoDTO> {

	private static final EnsambladorDTO<VerificacionContactoDominio, VerificacionContactoDTO> INSTANCIA = new VerificacionContactoEnsambladorDTO();

	private VerificacionContactoEnsambladorDTO() {
	}

	public static EnsambladorDTO<VerificacionContactoDominio, VerificacionContactoDTO> getInstancia() {
		return INSTANCIA;
	}

	@Override
	public VerificacionContactoDTO convertirADTO(VerificacionContactoDominio dominio) {
		var dominioTmp = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(dominio, new VerificacionContactoDominio.Builder().build());
		var destino = new VerificacionContactoDTO();
		destino.setId(dominioTmp.getId());
		destino.setUsuario(UsuarioEnsambladorDTO.getInstancia().convertirADTO(dominioTmp.getUsuario()));
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
	public VerificacionContactoDominio convertirADominio(VerificacionContactoDTO origen) {
		var origenTmp = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(origen, new VerificacionContactoDTO());
		return new VerificacionContactoDominio.Builder().id(origenTmp.getId()).usuario(UsuarioEnsambladorDTO.getInstancia().convertirADominio(origenTmp.getUsuario())).tipo(TipoVerificacion.desdeTexto(origenTmp.getTipo())).valorVerificado(origenTmp.getValorVerificado()).codigo(origenTmp.getCodigo()).fechaEnvio(origenTmp.getFechaEnvio()).fechaExpiracion(origenTmp.getFechaExpiracion()).fechaVerificacion(origenTmp.getFechaVerificacion()).estado(EstadoVerificacion.desdeTexto(origenTmp.getEstado())).build();
	}

	@Override
	public List<VerificacionContactoDTO> convertirADTO(List<VerificacionContactoDominio> lista) {
		var listaTmp = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(lista, new ArrayList<VerificacionContactoDominio>());
		return listaTmp.stream().map(this::convertirADTO).toList();
	}
}
