package co.edu.uco.kudos.negocio.negocio.ensamblador.impl;

import java.util.ArrayList;
import java.util.List;

import co.edu.uco.kudos.dominio.ProgramaAcademicoDominio;
import co.edu.uco.kudos.dominio.enums.EstadoRegistro;
import co.edu.uco.kudos.entidad.ProgramaAcademicoEntidad;
import co.edu.uco.kudos.negocio.negocio.ensamblador.EnsambladorEntidad;
import co.edu.uco.kudos.transversal.utilitarios.UtilObjeto;
import co.edu.uco.kudos.transversal.utilitarios.UtilTexto;

public final class ProgramaAcademicoEnsambladorEntidad implements EnsambladorEntidad<ProgramaAcademicoDominio, ProgramaAcademicoEntidad> {

	private static final EnsambladorEntidad<ProgramaAcademicoDominio, ProgramaAcademicoEntidad> INSTANCIA = new ProgramaAcademicoEnsambladorEntidad();

	private ProgramaAcademicoEnsambladorEntidad() {
	}

	public static EnsambladorEntidad<ProgramaAcademicoDominio, ProgramaAcademicoEntidad> getInstancia() {
		return INSTANCIA;
	}

	@Override
	public ProgramaAcademicoEntidad convertirAEntidad(ProgramaAcademicoDominio dominio) {
		var dominioTmp = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(dominio, new ProgramaAcademicoDominio.Builder().build());
		var destino = new ProgramaAcademicoEntidad();
		destino.setId(dominioTmp.getId());
		destino.setNombre(dominioTmp.getNombre());
		destino.setFacultad(FacultadEnsambladorEntidad.getInstancia().convertirAEntidad(dominioTmp.getFacultad()));
		destino.setEstado(UtilTexto.getUtilTexto().textoDeEnum(dominioTmp.getEstado()));
		destino.setFechaCreacion(dominioTmp.getFechaCreacion());
		return destino;
	}

	@Override
	public ProgramaAcademicoDominio convertirADominio(ProgramaAcademicoEntidad origen) {
		var origenTmp = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(origen, new ProgramaAcademicoEntidad());
		return new ProgramaAcademicoDominio.Builder().id(origenTmp.getId()).nombre(origenTmp.getNombre()).facultad(FacultadEnsambladorEntidad.getInstancia().convertirADominio(origenTmp.getFacultad())).estado(EstadoRegistro.desdeTexto(origenTmp.getEstado())).fechaCreacion(origenTmp.getFechaCreacion()).build();
	}

	@Override
	public List<ProgramaAcademicoDominio> convertirADominio(List<ProgramaAcademicoEntidad> lista) {
		var listaTmp = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(lista, new ArrayList<ProgramaAcademicoEntidad>());
		return listaTmp.stream().map(this::convertirADominio).toList();
	}
}
