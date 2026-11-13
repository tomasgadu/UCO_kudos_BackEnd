package co.edu.uco.kudos.negocio.negocio.ensamblador.impl;

import java.util.ArrayList;
import java.util.List;

import co.edu.uco.kudos.dominio.NotificacionDominio;
import co.edu.uco.kudos.dominio.enums.TipoNotificacion;
import co.edu.uco.kudos.entidad.NotificacionEntidad;
import co.edu.uco.kudos.negocio.negocio.ensamblador.EnsambladorEntidad;
import co.edu.uco.kudos.transversal.utilitarios.UtilObjeto;

public final class NotificacionEnsambladorEntidad implements EnsambladorEntidad<NotificacionDominio, NotificacionEntidad> {

	private static final EnsambladorEntidad<NotificacionDominio, NotificacionEntidad> INSTANCIA = new NotificacionEnsambladorEntidad();

	private NotificacionEnsambladorEntidad() {
	}

	public static EnsambladorEntidad<NotificacionDominio, NotificacionEntidad> getInstancia() {
		return INSTANCIA;
	}

	@Override
	public NotificacionEntidad convertirAEntidad(NotificacionDominio dominio) {
		var dominioTmp = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(dominio, new NotificacionDominio.Builder().build());
		var destino = new NotificacionEntidad();
		destino.setId(dominioTmp.getId());
		destino.setDestinatario(UsuarioEnsambladorEntidad.getInstancia().convertirAEntidad(dominioTmp.getDestinatario()));
		destino.setTipo(dominioTmp.getTipo().name());
		destino.setMensaje(dominioTmp.getMensaje());
		destino.setFechaCreacion(dominioTmp.getFechaCreacion());
		destino.setLeida(dominioTmp.isLeida());
		return destino;
	}

	@Override
	public NotificacionDominio convertirADominio(NotificacionEntidad origen) {
		var origenTmp = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(origen, new NotificacionEntidad());
		return new NotificacionDominio.Builder().id(origenTmp.getId()).destinatario(UsuarioEnsambladorEntidad.getInstancia().convertirADominio(origenTmp.getDestinatario())).tipo(TipoNotificacion.desdeTexto(origenTmp.getTipo())).mensaje(origenTmp.getMensaje()).fechaCreacion(origenTmp.getFechaCreacion()).leida(origenTmp.isLeida()).build();
	}

	@Override
	public List<NotificacionDominio> convertirADominio(List<NotificacionEntidad> lista) {
		var listaTmp = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(lista, new ArrayList<NotificacionEntidad>());
		return listaTmp.stream().map(this::convertirADominio).toList();
	}
}
