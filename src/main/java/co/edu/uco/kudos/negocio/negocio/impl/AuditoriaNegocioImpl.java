package co.edu.uco.kudos.negocio.negocio.impl;

import java.util.List;
import java.util.UUID;

import co.edu.uco.kudos.dao.factoria.DAOFactory;
import co.edu.uco.kudos.dominio.RegistroAuditoriaDominio;
import co.edu.uco.kudos.dominio.enums.Permiso;
import co.edu.uco.kudos.entidad.RegistroAuditoriaEntidad;
import co.edu.uco.kudos.negocio.negocio.AuditoriaNegocio;
import co.edu.uco.kudos.negocio.negocio.ensamblador.impl.RegistroAuditoriaEnsambladorEntidad;
import co.edu.uco.kudos.negocio.negocio.reglas.impl.usuario.AsegurarUsuarioTienePermisoRegla;
import co.edu.uco.kudos.negocio.negocio.reglas.impl.usuario.ObtenerUsuarioActivoRegla;

public final class AuditoriaNegocioImpl implements AuditoriaNegocio {

	private final DAOFactory daoFactory;

	public AuditoriaNegocioImpl(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	@Override
	public List<RegistroAuditoriaDominio> consultarAuditoria(UUID idActor, String accion, String entidadAfectada,
			UUID idEntidadAfectada) {
		var actor = ObtenerUsuarioActivoRegla.ejecutar(idActor, daoFactory);
		AsegurarUsuarioTienePermisoRegla.obtenerInstancia().ejecutar(actor, Permiso.CONSULTAR_AUDITORIA);
		var filtro = new RegistroAuditoriaEntidad();
		filtro.setAccion(accion);
		filtro.setEntidadAfectada(entidadAfectada);
		filtro.setIdEntidadAfectada(idEntidadAfectada);
		return RegistroAuditoriaEnsambladorEntidad.getInstancia()
				.convertirADominio(daoFactory.obtenerRegistroAuditoriaDAO().consultarPorFiltro(filtro));
	}
}
