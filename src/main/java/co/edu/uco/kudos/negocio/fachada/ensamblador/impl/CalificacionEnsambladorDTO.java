package co.edu.uco.kudos.negocio.fachada.ensamblador.impl;

import java.util.ArrayList;
import java.util.List;

import co.edu.uco.kudos.dominio.CalificacionDominio;
import co.edu.uco.kudos.dto.CalificacionDTO;
import co.edu.uco.kudos.negocio.fachada.ensamblador.EnsambladorDTO;
import co.edu.uco.kudos.transversal.utilitarios.UtilObjeto;

public final class CalificacionEnsambladorDTO implements EnsambladorDTO<CalificacionDominio, CalificacionDTO> {

	private static final EnsambladorDTO<CalificacionDominio, CalificacionDTO> INSTANCIA = new CalificacionEnsambladorDTO();

	private CalificacionEnsambladorDTO() {
	}

	public static EnsambladorDTO<CalificacionDominio, CalificacionDTO> getInstancia() {
		return INSTANCIA;
	}

	@Override
	public CalificacionDTO convertirADTO(CalificacionDominio dominio) {
		var dominioTmp = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(dominio, new CalificacionDominio.Builder().build());
		var destino = new CalificacionDTO();
		destino.setId(dominioTmp.getId());
		destino.setIdPublicacion(dominioTmp.getIdPublicacion());
		destino.setAutor(UsuarioEnsambladorDTO.getInstancia().convertirADTO(dominioTmp.getAutor()));
		destino.setEstrellas(dominioTmp.getEstrellas());
		destino.setFechaRegistro(dominioTmp.getFechaRegistro());
		destino.setFechaModificacion(dominioTmp.getFechaModificacion());
		return destino;
	}

	@Override
	public CalificacionDominio convertirADominio(CalificacionDTO origen) {
		var origenTmp = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(origen, new CalificacionDTO());
		return new CalificacionDominio.Builder().id(origenTmp.getId()).idPublicacion(origenTmp.getIdPublicacion()).autor(UsuarioEnsambladorDTO.getInstancia().convertirADominio(origenTmp.getAutor())).estrellas(origenTmp.getEstrellas()).fechaRegistro(origenTmp.getFechaRegistro()).fechaModificacion(origenTmp.getFechaModificacion()).build();
	}

	@Override
	public List<CalificacionDTO> convertirADTO(List<CalificacionDominio> lista) {
		var listaTmp = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(lista, new ArrayList<CalificacionDominio>());
		return listaTmp.stream().map(this::convertirADTO).toList();
	}
}
