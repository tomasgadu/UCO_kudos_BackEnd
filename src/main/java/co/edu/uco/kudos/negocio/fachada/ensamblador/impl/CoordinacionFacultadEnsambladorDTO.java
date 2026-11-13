package co.edu.uco.kudos.negocio.fachada.ensamblador.impl;

import java.util.ArrayList;
import java.util.List;

import co.edu.uco.kudos.dominio.CoordinacionFacultadDominio;
import co.edu.uco.kudos.dto.CoordinacionFacultadDTO;
import co.edu.uco.kudos.negocio.fachada.ensamblador.EnsambladorDTO;
import co.edu.uco.kudos.transversal.utilitarios.UtilObjeto;

public final class CoordinacionFacultadEnsambladorDTO implements EnsambladorDTO<CoordinacionFacultadDominio, CoordinacionFacultadDTO> {

	private static final EnsambladorDTO<CoordinacionFacultadDominio, CoordinacionFacultadDTO> INSTANCIA = new CoordinacionFacultadEnsambladorDTO();

	private CoordinacionFacultadEnsambladorDTO() {
	}

	public static EnsambladorDTO<CoordinacionFacultadDominio, CoordinacionFacultadDTO> getInstancia() {
		return INSTANCIA;
	}

	@Override
	public CoordinacionFacultadDTO convertirADTO(CoordinacionFacultadDominio dominio) {
		var dominioTmp = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(dominio, new CoordinacionFacultadDominio.Builder().build());
		var destino = new CoordinacionFacultadDTO();
		destino.setId(dominioTmp.getId());
		destino.setCoordinador(UsuarioEnsambladorDTO.getInstancia().convertirADTO(dominioTmp.getCoordinador()));
		destino.setFacultad(FacultadEnsambladorDTO.getInstancia().convertirADTO(dominioTmp.getFacultad()));
		destino.setFechaInicio(dominioTmp.getFechaInicio());
		destino.setFechaFin(dominioTmp.getFechaFin());
		return destino;
	}

	@Override
	public CoordinacionFacultadDominio convertirADominio(CoordinacionFacultadDTO origen) {
		var origenTmp = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(origen, new CoordinacionFacultadDTO());
		return new CoordinacionFacultadDominio.Builder().id(origenTmp.getId()).coordinador(UsuarioEnsambladorDTO.getInstancia().convertirADominio(origenTmp.getCoordinador())).facultad(FacultadEnsambladorDTO.getInstancia().convertirADominio(origenTmp.getFacultad())).fechaInicio(origenTmp.getFechaInicio()).fechaFin(origenTmp.getFechaFin()).build();
	}

	@Override
	public List<CoordinacionFacultadDTO> convertirADTO(List<CoordinacionFacultadDominio> lista) {
		var listaTmp = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(lista, new ArrayList<CoordinacionFacultadDominio>());
		return listaTmp.stream().map(this::convertirADTO).toList();
	}
}
