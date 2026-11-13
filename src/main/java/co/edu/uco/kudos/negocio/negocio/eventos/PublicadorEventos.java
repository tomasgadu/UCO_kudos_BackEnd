package co.edu.uco.kudos.negocio.negocio.eventos;

import java.util.List;

import co.edu.uco.kudos.dao.factoria.DAOFactory;

/**
 * Patrón Observer: el negocio solo "publica" lo que pasó; la auditoría y las
 * notificaciones reaccionan. Agregar otro suscriptor no obliga a tocar el negocio
 * (Abierto/Cerrado, bajo acoplamiento). Los suscriptores usan la misma transacción.
 */
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
