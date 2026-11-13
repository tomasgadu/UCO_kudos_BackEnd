package co.edu.uco.kudos.negocio.negocio.eventos;

import java.util.List;

import co.edu.uco.kudos.dao.factoria.DAOFactory;

public final class PublicadorEventos {

	private static final List<SuscriptorEvento> SUSCRIPTORES = List.of(new AuditoriaSuscriptor(),
			new NotificacionSuscriptor());

	private final DAOFactory daoFactory;

	public PublicadorEventos(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	public void publicar(EventoDominio evento) {
		SUSCRIPTORES.forEach(suscriptor -> suscriptor.atender(evento, daoFactory));
	}
}
