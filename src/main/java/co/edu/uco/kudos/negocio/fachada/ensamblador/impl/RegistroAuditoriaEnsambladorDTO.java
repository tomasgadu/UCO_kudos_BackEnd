package co.edu.uco.kudos.negocio.fachada.ensamblador.impl;

import java.util.ArrayList;
import java.util.List;

import co.edu.uco.kudos.dominio.RegistroAuditoriaDominio;
import co.edu.uco.kudos.dto.RegistroAuditoriaDTO;
import co.edu.uco.kudos.negocio.fachada.ensamblador.EnsambladorDTO;
import co.edu.uco.kudos.transversal.utilitarios.UtilObjeto;

public final class RegistroAuditoriaEnsambladorDTO implements EnsambladorDTO<RegistroAuditoriaDominio, RegistroAuditoriaDTO> {

	private static final EnsambladorDTO<RegistroAuditoriaDominio, RegistroAuditoriaDTO> INSTANCIA = new RegistroAuditoriaEnsambladorDTO();

	private RegistroAuditoriaEnsambladorDTO() {
	}

	public static EnsambladorDTO<RegistroAuditoriaDominio, RegistroAuditoriaDTO> getInstancia() {
		return INSTANCIA;
	}

	@Override
	public RegistroAuditoriaDTO convertirADTO(RegistroAuditoriaDominio dominio) {
		var dominioTmp = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(dominio, new RegistroAuditoriaDominio.Builder().build());
		var destino = new RegistroAuditoriaDTO();
		destino.setId(dominioTmp.getId());
		destino.setFecha(dominioTmp.getFecha());
		destino.setActor(UsuarioEnsambladorDTO.getInstancia().convertirADTO(dominioTmp.getActor()));
		destino.setAccion(dominioTmp.getAccion());
		destino.setEntidadAfectada(dominioTmp.getEntidadAfectada());
		destino.setIdEntidadAfectada(dominioTmp.getIdEntidadAfectada());
		destino.setValorAnterior(dominioTmp.getValorAnterior());
		destino.setValorNuevo(dominioTmp.getValorNuevo());
		return destino;
	}

	@Override
	public RegistroAuditoriaDominio convertirADominio(RegistroAuditoriaDTO origen) {
		var origenTmp = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(origen, new RegistroAuditoriaDTO());
		return new RegistroAuditoriaDominio.Builder().id(origenTmp.getId()).fecha(origenTmp.getFecha()).actor(UsuarioEnsambladorDTO.getInstancia().convertirADominio(origenTmp.getActor())).accion(origenTmp.getAccion()).entidadAfectada(origenTmp.getEntidadAfectada()).idEntidadAfectada(origenTmp.getIdEntidadAfectada()).valorAnterior(origenTmp.getValorAnterior()).valorNuevo(origenTmp.getValorNuevo()).build();
	}

	@Override
	public List<RegistroAuditoriaDTO> convertirADTO(List<RegistroAuditoriaDominio> lista) {
		var listaTmp = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(lista, new ArrayList<RegistroAuditoriaDominio>());
		return listaTmp.stream().map(this::convertirADTO).toList();
	}
}
