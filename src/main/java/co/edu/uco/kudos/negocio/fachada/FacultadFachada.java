package co.edu.uco.kudos.negocio.fachada;

import java.util.List;

import co.edu.uco.kudos.dto.FacultadDTO;

public interface FacultadFachada {

	FacultadDTO registrarFacultad(FacultadDTO datos);

	List<FacultadDTO> consultarFacultades(FacultadDTO filtro);
}
