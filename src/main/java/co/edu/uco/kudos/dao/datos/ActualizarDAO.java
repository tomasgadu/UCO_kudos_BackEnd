package co.edu.uco.kudos.dao.datos;

public interface ActualizarDAO<E, I> {

	void actualizar(I id, E entidad);
}
