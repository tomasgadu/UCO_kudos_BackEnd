package co.edu.uco.kudos.negocio.negocio.ensamblador.impl;

import java.util.ArrayList;
import java.util.List;

import co.edu.uco.kudos.dominio.RegistroAuditoriaDominio;
import co.edu.uco.kudos.entidad.RegistroAuditoriaEntidad;
import co.edu.uco.kudos.negocio.negocio.ensamblador.EnsambladorEntidad;
import co.edu.uco.kudos.transversal.utilitarios.UtilObjeto;

public final class RegistroAuditoriaEnsambladorEntidad implements EnsambladorEntidad<RegistroAuditoriaDominio, RegistroAuditoriaEntidad> {

	private static final EnsambladorEntidad<RegistroAuditoriaDominio, RegistroAuditoriaEntidad> INSTANCIA = new RegistroAuditoriaEnsambladorEntidad();

	private RegistroAuditoriaEnsambladorEntidad() {
	}

	public static EnsambladorEntidad<RegistroAuditoriaDominio, RegistroAuditoriaEntidad> getInstancia() {
		return INSTANCIA;
	}

	@Override
	public RegistroAuditoriaEntidad convertirAEntidad(RegistroAuditoriaDominio dominio) {
		var dominioTmp = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(dominio, new RegistroAuditoriaDominio.Builder().build());
		var destino = new RegistroAuditoriaEntidad();
		destino.setId(dominioTmp.getId());
		destino.setFecha(dominioTmp.getFecha());
		destino.setActor(UsuarioEnsambladorEntidad.getInstancia().convertirAEntidad(dominioTmp.getActor()));
		destino.setAccion(dominioTmp.getAccion());
		destino.setEntidadAfectada(dominioTmp.getEntidadAfectada());
		destino.setIdEntidadAfectada(dominioTmp.getIdEntidadAfectada());
		destino.setValorAnterior(dominioTmp.getValorAnterior());
		destino.setValorNuevo(dominioTmp.getValorNuevo());
		return destino;
	}

	@Override
	public RegistroAuditoriaDominio convertirADominio(RegistroAuditoriaEntidad origen) {
		var origenTmp = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(origen, new RegistroAuditoriaEntidad());
		return new RegistroAuditoriaDominio.Builder().id(origenTmp.getId()).fecha(origenTmp.getFecha()).actor(UsuarioEnsambladorEntidad.getInstancia().convertirADominio(origenTmp.getActor())).accion(origenTmp.getAccion()).entidadAfectada(origenTmp.getEntidadAfectada()).idEntidadAfectada(origenTmp.getIdEntidadAfectada()).valorAnterior(origenTmp.getValorAnterior()).valorNuevo(origenTmp.getValorNuevo()).build();
	}

	@Override
	public List<RegistroAuditoriaDominio> convertirADominio(List<RegistroAuditoriaEntidad> lista) {
		var listaTmp = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(lista, new ArrayList<RegistroAuditoriaEntidad>());
		return listaTmp.stream().map(this::convertirADominio).toList();
	}
}
