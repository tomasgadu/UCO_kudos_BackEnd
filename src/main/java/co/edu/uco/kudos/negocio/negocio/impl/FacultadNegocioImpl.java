package co.edu.uco.kudos.negocio.negocio.impl;

import java.util.List;

import co.edu.uco.kudos.dao.factoria.DAOFactory;
import co.edu.uco.kudos.dominio.FacultadDominio;
import co.edu.uco.kudos.dominio.enums.EstadoRegistro;
import co.edu.uco.kudos.negocio.negocio.FacultadNegocio;
import co.edu.uco.kudos.negocio.negocio.ensamblador.impl.FacultadEnsambladorEntidad;
import co.edu.uco.kudos.negocio.negocio.reglas.impl.facultad.AsegurarNombreFacultadNoExistaRegla;
import co.edu.uco.kudos.negocio.negocio.reglas.impl.facultad.ValidarDatosFacultadRegla;
import co.edu.uco.kudos.transversal.utilitarios.UtilFecha;
import co.edu.uco.kudos.transversal.utilitarios.UtilUUID;

public final class FacultadNegocioImpl implements FacultadNegocio {

	private final DAOFactory daoFactory;

	public FacultadNegocioImpl(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	@Override
	public FacultadDominio registrarFacultad(FacultadDominio datos) {
		ValidarDatosFacultadRegla.obtenerInstancia().ejecutar(datos);
		AsegurarNombreFacultadNoExistaRegla.obtenerInstancia().ejecutar(datos.getNombre(), daoFactory);

		var dao = daoFactory.obtenerFacultadDAO();
		var nueva = new FacultadDominio.Builder()
				.id(UtilUUID.generarUnico(id -> !UtilUUID.esValorDefecto(dao.consultarPorId(id).getId())))
				.nombre(datos.getNombre()).estado(EstadoRegistro.ACTIVA).fechaCreacion(UtilFecha.obtenerFechaHoraActual())
				.build();

		dao.crear(FacultadEnsambladorEntidad.getInstancia().convertirAEntidad(nueva));
		return nueva;
	}

	@Override
	public List<FacultadDominio> consultarFacultades(FacultadDominio filtro) {
		var filtroEntidad = FacultadEnsambladorEntidad.getInstancia().convertirAEntidad(filtro);
		var facultades = daoFactory.obtenerFacultadDAO().consultarPorFiltro(filtroEntidad);
		return FacultadEnsambladorEntidad.getInstancia().convertirADominio(facultades);
	}
}
