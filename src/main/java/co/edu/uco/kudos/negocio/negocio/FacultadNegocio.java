package co.edu.uco.kudos.negocio.negocio;

import java.util.List;

import co.edu.uco.kudos.dominio.FacultadDominio;

public interface FacultadNegocio {

	FacultadDominio registrarFacultad(FacultadDominio datos);

	List<FacultadDominio> consultarFacultades(FacultadDominio filtro);
}
