package co.edu.uco.kudos.negocio.fachada;

import java.util.List;
import java.util.UUID;

import co.edu.uco.kudos.dto.FacultadDTO;

public interface FacultadFachada {

	FacultadDTO registrarFacultad(UUID idActor, FacultadDTO datos);

	List<FacultadDTO> consultarFacultades(FacultadDTO filtro);
}
