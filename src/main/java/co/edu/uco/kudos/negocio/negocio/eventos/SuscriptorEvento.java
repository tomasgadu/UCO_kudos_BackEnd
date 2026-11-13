package co.edu.uco.kudos.negocio.negocio.eventos;

import co.edu.uco.kudos.dao.factoria.DAOFactory;

/**
 * Patrón Observer: cada interesado en los eventos implementa esta interfaz.
 */
public interface SuscriptorEvento {

	void atender(EventoDominio evento, DAOFactory daoFactory);
}
