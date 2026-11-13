package co.edu.uco.kudos.dao.datos.entidad;

import java.util.UUID;

import co.edu.uco.kudos.dao.datos.CrearDAO;
import co.edu.uco.kudos.dao.datos.ConsultarDAO;
import co.edu.uco.kudos.dao.datos.ActualizarDAO;
import co.edu.uco.kudos.entidad.VinculacionProgramaEntidad;

public interface VinculacionProgramaDAO extends CrearDAO<VinculacionProgramaEntidad>, ConsultarDAO<VinculacionProgramaEntidad, UUID>, ActualizarDAO<VinculacionProgramaEntidad, UUID> {
}
