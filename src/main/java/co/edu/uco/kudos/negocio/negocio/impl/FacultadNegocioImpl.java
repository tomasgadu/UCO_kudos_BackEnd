package co.edu.uco.kudos.negocio.negocio.impl;

import java.util.List;
import java.util.UUID;

import co.edu.uco.kudos.dao.factoria.DAOFactory;
import co.edu.uco.kudos.dominio.FacultadDominio;
import co.edu.uco.kudos.dominio.UsuarioDominio;
import co.edu.uco.kudos.dominio.enums.EstadoRegistro;
import co.edu.uco.kudos.dominio.enums.Permiso;
import co.edu.uco.kudos.negocio.negocio.FacultadNegocio;
import co.edu.uco.kudos.negocio.negocio.ensamblador.impl.FacultadEnsambladorEntidad;
import co.edu.uco.kudos.negocio.negocio.eventos.EventoDominio;
import co.edu.uco.kudos.negocio.negocio.eventos.PublicadorEventos;
import co.edu.uco.kudos.negocio.negocio.reglas.impl.facultad.AsegurarNombreFacultadNoExistaRegla;
import co.edu.uco.kudos.negocio.negocio.reglas.impl.facultad.ValidarDatosFacultadRegla;
import co.edu.uco.kudos.negocio.negocio.reglas.impl.usuario.AsegurarUsuarioTienePermisoRegla;
import co.edu.uco.kudos.negocio.negocio.reglas.impl.usuario.ObtenerUsuarioActivoRegla;
import co.edu.uco.kudos.transversal.utilitarios.UtilFecha;
import co.edu.uco.kudos.transversal.utilitarios.UtilUUID;

public final class FacultadNegocioImpl implements FacultadNegocio {

	private static final String ENTIDAD = "Facultad";

	private final DAOFactory daoFactory;
	private final PublicadorEventos eventos;

	public FacultadNegocioImpl(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
		this.eventos = new PublicadorEventos(daoFactory);
	}

	@Override
	public FacultadDominio registrarFacultad(UUID idActor, FacultadDominio datos) {
		var actor = autorizar(idActor);
		ValidarDatosFacultadRegla.obtenerInstancia().ejecutar(datos);
		AsegurarNombreFacultadNoExistaRegla.obtenerInstancia().ejecutar(datos.getNombre(), daoFactory);

		var dao = daoFactory.obtenerFacultadDAO();
		var nueva = new FacultadDominio.Builder()
				.id(UtilUUID.generarUnico(id -> !UtilUUID.esValorDefecto(dao.consultarPorId(id).getId())))
				.nombre(datos.getNombre()).estado(EstadoRegistro.ACTIVA).fechaCreacion(UtilFecha.obtenerFechaHoraActual())
				.build();

		dao.crear(FacultadEnsambladorEntidad.getInstancia().convertirAEntidad(nueva));
		eventos.publicar(EventoDominio.de("FACULTAD_REGISTRADA", actor.getId(), ENTIDAD, nueva.getId(), null, nueva.getNombre()));
		return nueva;
	}

	@Override
	public List<FacultadDominio> consultarFacultades(FacultadDominio filtro) {
		var filtroEntidad = FacultadEnsambladorEntidad.getInstancia().convertirAEntidad(filtro);
		var facultades = daoFactory.obtenerFacultadDAO().consultarPorFiltro(filtroEntidad);
		return FacultadEnsambladorEntidad.getInstancia().convertirADominio(facultades);
	}

	private UsuarioDominio autorizar(UUID idActor) {
		var actor = ObtenerUsuarioActivoRegla.ejecutar(idActor, daoFactory);
		AsegurarUsuarioTienePermisoRegla.obtenerInstancia().ejecutar(actor, Permiso.GESTIONAR_ESTRUCTURA);
		return actor;
	}
}
