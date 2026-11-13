package co.edu.uco.kudos.negocio.negocio.impl;

import java.util.List;
import java.util.UUID;

import co.edu.uco.kudos.dao.factoria.DAOFactory;
import co.edu.uco.kudos.dominio.ReporteDominio;
import co.edu.uco.kudos.dominio.UsuarioDominio;
import co.edu.uco.kudos.dominio.enums.EstadoPublicacion;
import co.edu.uco.kudos.dominio.enums.MotivoReporte;
import co.edu.uco.kudos.dominio.enums.Permiso;
import co.edu.uco.kudos.entidad.ReporteEntidad;
import co.edu.uco.kudos.negocio.negocio.ReporteNegocio;
import co.edu.uco.kudos.negocio.negocio.ensamblador.impl.ReporteEnsambladorEntidad;
import co.edu.uco.kudos.negocio.negocio.eventos.EventoDominio;
import co.edu.uco.kudos.negocio.negocio.eventos.PublicadorEventos;
import co.edu.uco.kudos.negocio.negocio.reglas.impl.usuario.AsegurarUsuarioTienePermisoRegla;
import co.edu.uco.kudos.negocio.negocio.reglas.impl.usuario.ObtenerUsuarioActivoRegla;
import co.edu.uco.kudos.negocio.negocio.servicios.GestorPublicaciones;
import co.edu.uco.kudos.transversal.catalogo.CatalogoMensajes;
import co.edu.uco.kudos.transversal.excepciones.KudosNegocioExcepcion;
import co.edu.uco.kudos.transversal.utilitarios.UtilFecha;
import co.edu.uco.kudos.transversal.utilitarios.UtilTexto;
import co.edu.uco.kudos.transversal.utilitarios.UtilUUID;

public final class ReporteNegocioImpl implements ReporteNegocio {

	private static final String ENTIDAD = "Reporte";

	private final DAOFactory daoFactory;
	private final GestorPublicaciones publicaciones;
	private final PublicadorEventos eventos;

	public ReporteNegocioImpl(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
		this.publicaciones = new GestorPublicaciones(daoFactory);
		this.eventos = new PublicadorEventos(daoFactory);
	}

	@Override
	public ReporteDominio reportar(UUID idActor, UUID idPublicacion, String motivo, String descripcion) {
		var actor = ObtenerUsuarioActivoRegla.ejecutar(idActor, daoFactory);
		var publicacion = publicaciones.obtener(idPublicacion);
		publicacion.asegurarNoVetada();
		var tieneAbierto = buscarPorPublicacion(idPublicacion).stream()
				.anyMatch(reporte -> reporte.esDe(actor.getId()) && reporte.estaAbierto());
		if (tieneAbierto) {
			throw KudosNegocioExcepcion.crear(CatalogoMensajes.Reporte.USUARIO_ERROR_YA_REPORTADA);
		}

		var dao = daoFactory.obtenerReporteDAO();
		var id = UtilUUID.generarUnico(candidato -> !UtilUUID.esValorDefecto(dao.consultarPorId(candidato).getId()));
		var reporte = ReporteDominio.crear(id, idPublicacion, actor, MotivoReporte.desdeTexto(motivo),
				UtilTexto.getUtilTexto().quitarEspacioEnBlanco(descripcion), UtilFecha.obtenerFechaHoraActual());
		dao.crear(ReporteEnsambladorEntidad.getInstancia().convertirAEntidad(reporte));

		var reportada = publicacion.marcarReportada();
		if (reportada != publicacion) {
			publicaciones.guardarEstado(reportada);
		}
		eventos.publicar(EventoDominio.de("PUBLICACION_REPORTADA", actor.getId(), ENTIDAD, reporte.getId(), null,
				reporte.getMotivo().name()));
		return reporte;
	}

	@Override
	public List<ReporteDominio> consultarReportes(UUID idActor, String estado) {
		obtenerRevisor(idActor);
		var filtro = new ReporteEntidad();
		filtro.setEstado(estado);
		return ReporteEnsambladorEntidad.getInstancia()
				.convertirADominio(daoFactory.obtenerReporteDAO().consultarPorFiltro(filtro));
	}

	@Override
	public ReporteDominio iniciarRevision(UUID idActor, UUID idReporte) {
		var revisor = obtenerRevisor(idActor);
		var reporte = obtenerReporte(idReporte);
		var enRevision = reporte.iniciarRevision(revisor);
		guardar(enRevision);

		var publicacion = publicaciones.obtener(reporte.getIdPublicacion());
		var publicacionEnRevision = publicacion.iniciarRevision();
		if (publicacionEnRevision != publicacion) {
			publicaciones.guardarEstado(publicacionEnRevision);
		}
		eventos.publicar(EventoDominio.de("REPORTE_EN_REVISION", revisor.getId(), ENTIDAD, reporte.getId(),
				reporte.getEstado().name(), enRevision.getEstado().name()));
		return enRevision;
	}

	@Override
	public ReporteDominio decidir(UUID idActor, UUID idReporte, boolean aceptar, String observacion) {
		var revisor = obtenerRevisor(idActor);
		var reporte = obtenerReporte(idReporte);
		var decidido = reporte.decidir(aceptar, revisor, observacion, UtilFecha.obtenerFechaHoraActual());
		guardar(decidido);
		eventos.publicar(EventoDominio.de(aceptar ? "REPORTE_ACEPTADO" : "REPORTE_RECHAZADO", revisor.getId(), ENTIDAD,
				reporte.getId(), reporte.getEstado().name(), decidido.getEstado().name()));

		var publicacion = publicaciones.obtener(reporte.getIdPublicacion());
		if (aceptar) {
			AsegurarUsuarioTienePermisoRegla.obtenerInstancia().ejecutar(revisor, Permiso.VETAR);
			new VetoNegocioImpl(daoFactory).aplicarVeto(revisor, publicacion, reporte.getId(),
					CatalogoMensajes.Reporte.PREFIJO_JUSTIFICACION + decidido.getObservacionDecision());
		} else if (!quedanAbiertos(reporte.getIdPublicacion())
				&& publicacion.getEstado() == EstadoPublicacion.EN_REVISION) {
			publicaciones.guardarEstado(publicacion.restaurar());
		}
		return decidido;
	}

	private UsuarioDominio obtenerRevisor(UUID idActor) {
		var actor = ObtenerUsuarioActivoRegla.ejecutar(idActor, daoFactory);
		AsegurarUsuarioTienePermisoRegla.obtenerInstancia().ejecutar(actor, Permiso.REVISAR_REPORTES);
		return actor;
	}

	private ReporteDominio obtenerReporte(UUID idReporte) {
		var entidad = daoFactory.obtenerReporteDAO().consultarPorId(idReporte);
		if (UtilUUID.esValorDefecto(entidad.getId())) {
			throw KudosNegocioExcepcion.crear(CatalogoMensajes.Reporte.USUARIO_ERROR_NO_EXISTE);
		}
		return ReporteEnsambladorEntidad.getInstancia().convertirADominio(entidad);
	}

	private List<ReporteDominio> buscarPorPublicacion(UUID idPublicacion) {
		var filtro = new ReporteEntidad();
		filtro.setIdPublicacion(idPublicacion);
		return ReporteEnsambladorEntidad.getInstancia()
				.convertirADominio(daoFactory.obtenerReporteDAO().consultarPorFiltro(filtro));
	}

	private boolean quedanAbiertos(UUID idPublicacion) {
		return buscarPorPublicacion(idPublicacion).stream().anyMatch(ReporteDominio::estaAbierto);
	}

	private void guardar(ReporteDominio reporte) {
		daoFactory.obtenerReporteDAO().actualizar(reporte.getId(),
				ReporteEnsambladorEntidad.getInstancia().convertirAEntidad(reporte));
	}
}
