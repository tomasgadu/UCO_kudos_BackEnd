package co.edu.uco.kudos.dao.datos.entidad;

import java.util.UUID;

import co.edu.uco.kudos.dao.datos.CrearDAO;
import co.edu.uco.kudos.dao.datos.ConsultarDAO;
import co.edu.uco.kudos.entidad.RegistroAuditoriaEntidad;

public interface RegistroAuditoriaDAO extends CrearDAO<RegistroAuditoriaEntidad>, ConsultarDAO<RegistroAuditoriaEntidad, UUID> {
}
