package co.edu.uco.kudos.negocio.fachada.ensamblador.impl;

import java.util.ArrayList;
import java.util.List;

import co.edu.uco.kudos.dominio.SolicitudRegistroDominio;
import co.edu.uco.kudos.dominio.enums.EstadoSolicitud;
import co.edu.uco.kudos.dto.SolicitudRegistroDTO;
import co.edu.uco.kudos.negocio.fachada.ensamblador.EnsambladorDTO;
import co.edu.uco.kudos.transversal.utilitarios.UtilObjeto;

public final class SolicitudRegistroEnsambladorDTO implements EnsambladorDTO<SolicitudRegistroDominio, SolicitudRegistroDTO> {

	private static final EnsambladorDTO<SolicitudRegistroDominio, SolicitudRegistroDTO> INSTANCIA = new SolicitudRegistroEnsambladorDTO();

	private SolicitudRegistroEnsambladorDTO() {
	}

	public static EnsambladorDTO<SolicitudRegistroDominio, SolicitudRegistroDTO> getInstancia() {
		return INSTANCIA;
	}

	@Override
	public SolicitudRegistroDTO convertirADTO(SolicitudRegistroDominio dominio) {
		var dominioTmp = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(dominio, new SolicitudRegistroDominio.Builder().build());
		var destino = new SolicitudRegistroDTO();
		destino.setId(dominioTmp.getId());
		destino.setSolicitante(UsuarioEnsambladorDTO.getInstancia().convertirADTO(dominioTmp.getSolicitante()));
		destino.setRolSolicitado(RolEnsambladorDTO.getInstancia().convertirADTO(dominioTmp.getRolSolicitado()));
		destino.setFacultad(FacultadEnsambladorDTO.getInstancia().convertirADTO(dominioTmp.getFacultad()));
		destino.setProgramaAcademico(ProgramaAcademicoEnsambladorDTO.getInstancia().convertirADTO(dominioTmp.getProgramaAcademico()));
		destino.setEstado(dominioTmp.getEstado().name());
		destino.setFechaSolicitud(dominioTmp.getFechaSolicitud());
		destino.setAprobador(UsuarioEnsambladorDTO.getInstancia().convertirADTO(dominioTmp.getAprobador()));
		destino.setFechaDecision(dominioTmp.getFechaDecision());
		destino.setObservacion(dominioTmp.getObservacion());
		return destino;
	}

	@Override
	public SolicitudRegistroDominio convertirADominio(SolicitudRegistroDTO origen) {
		var origenTmp = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(origen, new SolicitudRegistroDTO());
		return new SolicitudRegistroDominio.Builder().id(origenTmp.getId()).solicitante(UsuarioEnsambladorDTO.getInstancia().convertirADominio(origenTmp.getSolicitante())).rolSolicitado(RolEnsambladorDTO.getInstancia().convertirADominio(origenTmp.getRolSolicitado())).facultad(FacultadEnsambladorDTO.getInstancia().convertirADominio(origenTmp.getFacultad())).programaAcademico(ProgramaAcademicoEnsambladorDTO.getInstancia().convertirADominio(origenTmp.getProgramaAcademico())).estado(EstadoSolicitud.desdeTexto(origenTmp.getEstado())).fechaSolicitud(origenTmp.getFechaSolicitud()).aprobador(UsuarioEnsambladorDTO.getInstancia().convertirADominio(origenTmp.getAprobador())).fechaDecision(origenTmp.getFechaDecision()).observacion(origenTmp.getObservacion()).build();
	}

	@Override
	public List<SolicitudRegistroDTO> convertirADTO(List<SolicitudRegistroDominio> lista) {
		var listaTmp = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(lista, new ArrayList<SolicitudRegistroDominio>());
		return listaTmp.stream().map(this::convertirADTO).toList();
	}
}
