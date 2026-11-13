package co.edu.uco.kudos.negocio.negocio.impl;

import java.util.List;
import java.util.UUID;

import co.edu.uco.kudos.dao.factoria.DAOFactory;
import co.edu.uco.kudos.dominio.KudoDominio;
import co.edu.uco.kudos.dominio.UsuarioDominio;
import co.edu.uco.kudos.dominio.enums.Permiso;
import co.edu.uco.kudos.dominio.enums.TipoNotificacion;
import co.edu.uco.kudos.entidad.KudoEntidad;
import co.edu.uco.kudos.negocio.negocio.KudoNegocio;
import co.edu.uco.kudos.negocio.negocio.ensamblador.impl.KudoEnsambladorEntidad;
import co.edu.uco.kudos.negocio.negocio.eventos.EventoDominio;
import co.edu.uco.kudos.negocio.negocio.eventos.PublicadorEventos;
import co.edu.uco.kudos.negocio.negocio.reglas.impl.categoria.ObtenerCategoriaActivaRegla;
import co.edu.uco.kudos.negocio.negocio.reglas.impl.publicacion.SanearContenidoPublicacionRegla;
import co.edu.uco.kudos.negocio.negocio.reglas.impl.usuario.ObtenerUsuarioActivoRegla;
import co.edu.uco.kudos.transversal.catalogo.CatalogoMensajes;
import co.edu.uco.kudos.transversal.excepciones.KudosNegocioExcepcion;
import co.edu.uco.kudos.transversal.utilitarios.UtilFecha;
import co.edu.uco.kudos.transversal.utilitarios.UtilUUID;

public final class KudoNegocioImpl implements KudoNegocio {

	private static final String ENTIDAD = "Kudo";

	private final DAOFactory daoFactory;
	private final PublicadorEventos eventos;

	public KudoNegocioImpl(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
		this.eventos = new PublicadorEventos(daoFactory);
	}

	@Override
	public KudoDominio crearKudo(UUID idAutor, UUID idDestinatario, UUID idCategoria, String contenido) {
		var autor = ObtenerUsuarioActivoRegla.ejecutar(idAutor, daoFactory);
		var destinatario = obtenerDestinatario(idDestinatario);
		var categoria = ObtenerCategoriaActivaRegla.ejecutar(idCategoria, daoFactory);
		var texto = SanearContenidoPublicacionRegla.ejecutar(contenido);

		var dao = daoFactory.obtenerKudoDAO();
		var id = UtilUUID.generarUnico(candidato -> !UtilUUID.esValorDefecto(dao.consultarPorId(candidato).getId()));
		var kudo = KudoDominio.crear(id, autor, destinatario, categoria, texto, UtilFecha.obtenerFechaHoraActual())
				.publicar();
		dao.crear(KudoEnsambladorEntidad.getInstancia().convertirAEntidad(kudo));

		var mensaje = CatalogoMensajes.Kudo.NOTIFICACION_KUDO_RECIBIDO.formatted(autor.getNombreCompleto(),
				categoria.getNombre());
		eventos.publicar(EventoDominio.de("KUDO_CREADO", autor.getId(), ENTIDAD, kudo.getId(), null,
				kudo.getEstado().name()).notificando(destinatario.getId(), TipoNotificacion.KUDO_RECIBIDO, mensaje));
		return kudo;
	}

	@Override
	public List<KudoDominio> consultarMuro(UUID idActor, UUID idDestinatario) {
		var actor = ObtenerUsuarioActivoRegla.ejecutar(idActor, daoFactory);
		var filtro = new KudoEntidad();
		filtro.getDestinatario().setId(idDestinatario);
		var kudos = KudoEnsambladorEntidad.getInstancia()
				.convertirADominio(daoFactory.obtenerKudoDAO().consultarPorFiltro(filtro));
		var puedeVerVetados = puedeVerVetados(actor);
		return kudos.stream().filter(kudo -> kudo.esVisiblePara(puedeVerVetados)).toList();
	}

	@Override
	public KudoDominio consultarKudo(UUID idActor, UUID idKudo) {
		var actor = ObtenerUsuarioActivoRegla.ejecutar(idActor, daoFactory);
		var kudo = obtenerVisible(idKudo, actor);
		eventos.publicar(EventoDominio.de("KUDO_CONSULTADO", actor.getId(), ENTIDAD, kudo.getId(), null, null));
		return kudo;
	}

	KudoDominio obtenerVisible(UUID idKudo, UsuarioDominio actor) {
		var entidad = daoFactory.obtenerKudoDAO().consultarPorId(idKudo);
		var kudo = KudoEnsambladorEntidad.getInstancia().convertirADominio(entidad);
		if (UtilUUID.esValorDefecto(entidad.getId()) || !kudo.esVisiblePara(puedeVerVetados(actor))) {
			throw KudosNegocioExcepcion.crear(CatalogoMensajes.Kudo.USUARIO_ERROR_NO_EXISTE);
		}
		return kudo;
	}

	static boolean puedeVerVetados(UsuarioDominio actor) {
		return actor.getRol().getCodigo().tienePermiso(Permiso.VETAR);
	}

	private UsuarioDominio obtenerDestinatario(UUID idDestinatario) {
		var destinatario = ObtenerUsuarioActivoRegla.obtenerExistente(idDestinatario, daoFactory);
		if (!destinatario.estaActivo()) {
			throw KudosNegocioExcepcion.crear(CatalogoMensajes.Kudo.USUARIO_ERROR_DESTINATARIO_NO_ACTIVO);
		}
		return destinatario;
	}
}
