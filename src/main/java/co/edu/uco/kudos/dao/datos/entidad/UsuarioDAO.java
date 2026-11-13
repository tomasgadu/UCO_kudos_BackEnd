package co.edu.uco.kudos.dao.datos.entidad;

import java.util.UUID;

import co.edu.uco.kudos.dao.datos.CrearDAO;
import co.edu.uco.kudos.dao.datos.ConsultarDAO;
import co.edu.uco.kudos.dao.datos.ActualizarDAO;
import co.edu.uco.kudos.entidad.UsuarioEntidad;

public interface UsuarioDAO extends CrearDAO<UsuarioEntidad>, ConsultarDAO<UsuarioEntidad, UUID>, ActualizarDAO<UsuarioEntidad, UUID> {
}
