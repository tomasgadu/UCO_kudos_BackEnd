package co.edu.uco.kudos.negocio.negocio.ensamblador.impl;

import java.util.ArrayList;
import java.util.List;

import co.edu.uco.kudos.dominio.SolicitudRegistroDominio;
import co.edu.uco.kudos.dominio.enums.EstadoSolicitud;
import co.edu.uco.kudos.entidad.SolicitudRegistroEntidad;
import co.edu.uco.kudos.negocio.negocio.ensamblador.EnsambladorEntidad;
import co.edu.uco.kudos.transversal.utilitarios.UtilObjeto;

public final class SolicitudRegistroEnsambladorEntidad implements EnsambladorEntidad<SolicitudRegistroDominio, SolicitudRegistroEntidad> {

	private static final EnsambladorEntidad<SolicitudRegistroDominio, SolicitudRegistroEntidad> INSTANCIA = new SolicitudRegistroEnsambladorEntidad();

	private SolicitudRegistroEnsambladorEntidad() {
	}

	public static EnsambladorEntidad<SolicitudRegistroDominio, SolicitudRegistroEntidad> getInstancia() {
		return INSTANCIA;
	}

	@Override
	public SolicitudRegistroEntidad convertirAEntidad(SolicitudRegistroDominio dominio) {
		var dominioTmp = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(dominio, new SolicitudRegistroDominio.Builder().build());
		var destino = new SolicitudRegistroEntidad();
		destino.setId(dominioTmp.getId());
		destino.setSolicitante(UsuarioEnsambladorEntidad.getInstancia().convertirAEntidad(dominioTmp.getSolicitante()));
		destino.setRolSolicitado(RolEnsambladorEntidad.getInstancia().convertirAEntidad(dominioTmp.getRolSolicitado()));
		destino.setFacultad(FacultadEnsambladorEntidad.getInstancia().convertirAEntidad(dominioTmp.getFacultad()));
		destino.setProgramaAcademico(ProgramaAcademicoEnsambladorEntidad.getInstancia().convertirAEntidad(dominioTmp.getProgramaAcademico()));
		destino.setEstado(dominioTmp.getEstado().name());
		destino.setFechaSolicitud(dominioTmp.getFechaSolicitud());
		destino.setAprobador(UsuarioEnsambladorEntidad.getInstancia().convertirAEntidad(dominioTmp.getAprobador()));
		destino.setFechaDecision(dominioTmp.getFechaDecision());
		destino.setObservacion(dominioTmp.getObservacion());
		return destino;
	}

	@Override
	public SolicitudRegistroDominio convertirADominio(SolicitudRegistroEntidad origen) {
		var origenTmp = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(origen, new SolicitudRegistroEntidad());
		return new SolicitudRegistroDominio.Builder().id(origenTmp.getId()).solicitante(UsuarioEnsambladorEntidad.getInstancia().convertirADominio(origenTmp.getSolicitante())).rolSolicitado(RolEnsambladorEntidad.getInstancia().convertirADominio(origenTmp.getRolSolicitado())).facultad(FacultadEnsambladorEntidad.getInstancia().convertirADominio(origenTmp.getFacultad())).programaAcademico(ProgramaAcademicoEnsambladorEntidad.getInstancia().convertirADominio(origenTmp.getProgramaAcademico())).estado(EstadoSolicitud.desdeTexto(origenTmp.getEstado())).fechaSolicitud(origenTmp.getFechaSolicitud()).aprobador(UsuarioEnsambladorEntidad.getInstancia().convertirADominio(origenTmp.getAprobador())).fechaDecision(origenTmp.getFechaDecision()).observacion(origenTmp.getObservacion()).build();
	}

	@Override
	public List<SolicitudRegistroDominio> convertirADominio(List<SolicitudRegistroEntidad> lista) {
		var listaTmp = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(lista, new ArrayList<SolicitudRegistroEntidad>());
		return listaTmp.stream().map(this::convertirADominio).toList();
	}
}
