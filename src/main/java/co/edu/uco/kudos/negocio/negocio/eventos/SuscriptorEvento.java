package co.edu.uco.kudos.negocio.negocio.eventos;

import co.edu.uco.kudos.dao.factoria.DAOFactory;

public interface SuscriptorEvento {

	void atender(EventoDominio evento, DAOFactory daoFactory);
}
