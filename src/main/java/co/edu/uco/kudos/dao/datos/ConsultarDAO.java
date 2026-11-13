package co.edu.uco.kudos.dao.datos;

import java.util.List;

public interface ConsultarDAO<E, I> {

	E consultarPorId(I id);

	List<E> consultarPorFiltro(E filtro);

	List<E> consultarTodos();
}
