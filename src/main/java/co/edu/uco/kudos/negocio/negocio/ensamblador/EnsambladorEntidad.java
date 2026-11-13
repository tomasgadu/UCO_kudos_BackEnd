package co.edu.uco.kudos.negocio.negocio.ensamblador;

import java.util.List;

public interface EnsambladorEntidad<D, E> {

	E convertirAEntidad(D dominio);

	D convertirADominio(E entidad);

	List<D> convertirADominio(List<E> listaEntidades);
}
