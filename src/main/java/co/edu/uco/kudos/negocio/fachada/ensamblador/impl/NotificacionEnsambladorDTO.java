package co.edu.uco.kudos.negocio.fachada.ensamblador.impl;

import java.util.ArrayList;
import java.util.List;

import co.edu.uco.kudos.dominio.NotificacionDominio;
import co.edu.uco.kudos.dominio.enums.TipoNotificacion;
import co.edu.uco.kudos.dto.NotificacionDTO;
import co.edu.uco.kudos.negocio.fachada.ensamblador.EnsambladorDTO;
import co.edu.uco.kudos.transversal.utilitarios.UtilObjeto;

public final class NotificacionEnsambladorDTO implements EnsambladorDTO<NotificacionDominio, NotificacionDTO> {

	private static final EnsambladorDTO<NotificacionDominio, NotificacionDTO> INSTANCIA = new NotificacionEnsambladorDTO();

	private NotificacionEnsambladorDTO() {
	}

	public static EnsambladorDTO<NotificacionDominio, NotificacionDTO> getInstancia() {
		return INSTANCIA;
	}

	@Override
	public NotificacionDTO convertirADTO(NotificacionDominio dominio) {
		var dominioTmp = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(dominio, new NotificacionDominio.Builder().build());
		var destino = new NotificacionDTO();
		destino.setId(dominioTmp.getId());
		destino.setDestinatario(UsuarioEnsambladorDTO.getInstancia().convertirADTO(dominioTmp.getDestinatario()));
		destino.setTipo(dominioTmp.getTipo().name());
		destino.setMensaje(dominioTmp.getMensaje());
		destino.setFechaCreacion(dominioTmp.getFechaCreacion());
		destino.setLeida(dominioTmp.isLeida());
		return destino;
	}

	@Override
	public NotificacionDominio convertirADominio(NotificacionDTO origen) {
		var origenTmp = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(origen, new NotificacionDTO());
		return new NotificacionDominio.Builder().id(origenTmp.getId()).destinatario(UsuarioEnsambladorDTO.getInstancia().convertirADominio(origenTmp.getDestinatario())).tipo(TipoNotificacion.desdeTexto(origenTmp.getTipo())).mensaje(origenTmp.getMensaje()).fechaCreacion(origenTmp.getFechaCreacion()).leida(origenTmp.isLeida()).build();
	}

	@Override
	public List<NotificacionDTO> convertirADTO(List<NotificacionDominio> lista) {
		var listaTmp = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(lista, new ArrayList<NotificacionDominio>());
		return listaTmp.stream().map(this::convertirADTO).toList();
	}
}
