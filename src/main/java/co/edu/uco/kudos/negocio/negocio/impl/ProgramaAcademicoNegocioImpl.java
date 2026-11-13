package co.edu.uco.kudos.negocio.negocio.impl;

import java.util.List;

import co.edu.uco.kudos.dao.factoria.DAOFactory;
import co.edu.uco.kudos.dominio.ProgramaAcademicoDominio;
import co.edu.uco.kudos.dominio.enums.EstadoRegistro;
import co.edu.uco.kudos.negocio.negocio.ProgramaAcademicoNegocio;
import co.edu.uco.kudos.negocio.negocio.ensamblador.impl.ProgramaAcademicoEnsambladorEntidad;
import co.edu.uco.kudos.negocio.negocio.reglas.impl.facultad.AsegurarFacultadExistaActivaRegla;
import co.edu.uco.kudos.negocio.negocio.reglas.impl.programa.AsegurarProgramaNoExistaEnFacultadRegla;
import co.edu.uco.kudos.negocio.negocio.reglas.impl.programa.ValidarDatosProgramaAcademicoRegla;
import co.edu.uco.kudos.transversal.utilitarios.UtilFecha;
import co.edu.uco.kudos.transversal.utilitarios.UtilUUID;

public final class ProgramaAcademicoNegocioImpl implements ProgramaAcademicoNegocio {

	private final DAOFactory daoFactory;

	public ProgramaAcademicoNegocioImpl(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	@Override
	public ProgramaAcademicoDominio registrarProgramaAcademico(ProgramaAcademicoDominio datos) {
		ValidarDatosProgramaAcademicoRegla.obtenerInstancia().ejecutar(datos);
		var idFacultad = datos.getFacultad().getId();
		AsegurarFacultadExistaActivaRegla.obtenerInstancia().ejecutar(idFacultad, daoFactory);
		AsegurarProgramaNoExistaEnFacultadRegla.obtenerInstancia().ejecutar(datos.getNombre(), idFacultad, daoFactory);

		var dao = daoFactory.obtenerProgramaAcademicoDAO();
		var nuevo = new ProgramaAcademicoDominio.Builder()
				.id(UtilUUID.generarUnico(id -> !UtilUUID.esValorDefecto(dao.consultarPorId(id).getId())))
				.nombre(datos.getNombre()).facultad(datos.getFacultad()).estado(EstadoRegistro.ACTIVA)
				.fechaCreacion(UtilFecha.obtenerFechaHoraActual()).build();

		dao.crear(ProgramaAcademicoEnsambladorEntidad.getInstancia().convertirAEntidad(nuevo));
		return nuevo;
	}

	@Override
	public List<ProgramaAcademicoDominio> consultarProgramasAcademicos(ProgramaAcademicoDominio filtro) {
		var filtroEntidad = ProgramaAcademicoEnsambladorEntidad.getInstancia().convertirAEntidad(filtro);
		var programas = daoFactory.obtenerProgramaAcademicoDAO().consultarPorFiltro(filtroEntidad);
		return ProgramaAcademicoEnsambladorEntidad.getInstancia().convertirADominio(programas);
	}
}
