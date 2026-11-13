package co.edu.uco.kudos.negocio.negocio.impl;

import java.util.UUID;

import co.edu.uco.kudos.dao.factoria.DAOFactory;
import co.edu.uco.kudos.dominio.PublicacionDominio;
import co.edu.uco.kudos.dominio.UsuarioDominio;
import co.edu.uco.kudos.dominio.VetoDominio;
import co.edu.uco.kudos.dominio.enums.Permiso;
import co.edu.uco.kudos.dominio.enums.TipoNotificacion;
import co.edu.uco.kudos.entidad.ReporteEntidad;
import co.edu.uco.kudos.negocio.negocio.VetoNegocio;
import co.edu.uco.kudos.negocio.negocio.ensamblador.impl.ReporteEnsambladorEntidad;
import co.edu.uco.kudos.negocio.negocio.ensamblador.impl.VetoEnsambladorEntidad;
import co.edu.uco.kudos.negocio.negocio.eventos.EventoDominio;
import co.edu.uco.kudos.negocio.negocio.eventos.PublicadorEventos;
import co.edu.uco.kudos.negocio.negocio.reglas.impl.usuario.AsegurarUsuarioTienePermisoRegla;
import co.edu.uco.kudos.negocio.negocio.reglas.impl.usuario.ObtenerUsuarioActivoRegla;
import co.edu.uco.kudos.negocio.negocio.servicios.GestorPublicaciones;
import co.edu.uco.kudos.transversal.catalogo.CatalogoMensajes;
import co.edu.uco.kudos.transversal.utilitarios.UtilFecha;
import co.edu.uco.kudos.transversal.utilitarios.UtilUUID;

public final class VetoNegocioImpl implements VetoNegocio {

	private static final int LONGITUD_MAXIMA_NOTIFICACION = 250;

	private final DAOFactory daoFactory;
	private final GestorPublicaciones publicaciones;
	private final PublicadorEventos eventos;

	public VetoNegocioImpl(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
		this.publicaciones = new GestorPublicaciones(daoFactory);
		this.eventos = new PublicadorEventos(daoFactory);
	}

	@Override
	public VetoDominio vetar(UUID idActor, UUID idPublicacion, String justificacion) {
		var actor = ObtenerUsuarioActivoRegla.ejecutar(idActor, daoFactory);
		AsegurarUsuarioTienePermisoRegla.obtenerInstancia().ejecutar(actor, Permiso.VETAR);
		return aplicarVeto(actor, publicaciones.obtener(idPublicacion), null, justificacion);
	}

	VetoDominio aplicarVeto(UsuarioDominio autoridad, PublicacionDominio publicacion, UUID idReporteOrigen,
			String justificacion) {
		var ahora = UtilFecha.obtenerFechaHoraActual();
		var dao = daoFactory.obtenerVetoDAO();
		var id = UtilUUID.generarUnico(candidato -> !UtilUUID.esValorDefecto(dao.consultarPorId(candidato).getId()));
		var veto = VetoDominio.crear(id, publicacion.getId(), autoridad, idReporteOrigen, justificacion, ahora);
		var vetada = publicacion.vetar();

		dao.crear(VetoEnsambladorEntidad.getInstancia().convertirAEntidad(veto));
		publicaciones.guardarEstado(vetada);
		cerrarReportesAbiertos(publicacion.getId(), idReporteOrigen, autoridad);

		var mensaje = CatalogoMensajes.Veto.NOTIFICACION_CONTENIDO_VETADO.formatted(veto.getJustificacion());
		if (mensaje.length() > LONGITUD_MAXIMA_NOTIFICACION) {
			mensaje = mensaje.substring(0, LONGITUD_MAXIMA_NOTIFICACION - 3) + "...";
		}
		eventos.publicar(EventoDominio.de("PUBLICACION_VETADA", autoridad.getId(), GestorPublicaciones.tipoDe(publicacion),
				publicacion.getId(), publicacion.getEstado().name(), vetada.getEstado().name())
				.notificando(publicacion.getAutor().getId(), TipoNotificacion.CONTENIDO_VETADO, mensaje));
		return veto;
	}

	private void cerrarReportesAbiertos(UUID idPublicacion, UUID idReporteOrigen, UsuarioDominio autoridad) {
		var filtro = new ReporteEntidad();
		filtro.setIdPublicacion(idPublicacion);
		var dao = daoFactory.obtenerReporteDAO();
		var reportes = ReporteEnsambladorEntidad.getInstancia().convertirADominio(dao.consultarPorFiltro(filtro));
		var ahora = UtilFecha.obtenerFechaHoraActual();
		reportes.stream().filter(reporte -> reporte.estaAbierto() && !reporte.getId().equals(idReporteOrigen))
				.map(reporte -> reporte.cerrarPorVeto(autoridad, ahora)).forEach(cerrado -> dao
						.actualizar(cerrado.getId(), ReporteEnsambladorEntidad.getInstancia().convertirAEntidad(cerrado)));
	}
}
