package co.edu.uco.kudos.dao.datos.entidad.postgresql;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import co.edu.uco.kudos.dao.datos.entidad.CalificacionDAO;
import co.edu.uco.kudos.dao.datos.entidad.SqlDAO;
import co.edu.uco.kudos.entidad.CalificacionEntidad;
import co.edu.uco.kudos.transversal.catalogo.CatalogoMensajes;
import co.edu.uco.kudos.transversal.excepciones.KudosDatosExcepcion;
import co.edu.uco.kudos.transversal.utilitarios.UtilObjeto;
import co.edu.uco.kudos.transversal.utilitarios.UtilSQL;
import co.edu.uco.kudos.transversal.utilitarios.UtilUUID;

public final class CalificacionPostgreSqlDAO extends SqlDAO implements CalificacionDAO {

	public CalificacionPostgreSqlDAO(Connection conexion) {
		super(conexion);
	}

	@Override
	public void crear(CalificacionEntidad entidad) {
		var sql = "INSERT INTO calificacion (id_calificacion, id_publicacion, id_autor, estrellas, fecha_registro, fecha_modificacion) VALUES (?, ?, ?, ?, ?, ?)";
		try (var sentencia = getConexion().prepareStatement(sql)) {
			sentencia.setObject(1, entidad.getId());
			sentencia.setObject(2, entidad.getIdPublicacion());
			sentencia.setObject(3, entidad.getAutor().getId());
			sentencia.setInt(4, entidad.getEstrellas());
			sentencia.setTimestamp(5, UtilSQL.aTimestamp(entidad.getFechaRegistro()));
			sentencia.setTimestamp(6, UtilSQL.aTimestamp(entidad.getFechaModificacion()));
			sentencia.executeUpdate();
		} catch (SQLException excepcion) {
			throw KudosDatosExcepcion.crear(CatalogoMensajes.Datos.USUARIO_ERROR_CREANDO.formatted("Calificacion"),
					excepcion.getMessage(), excepcion);
		}
	}
	@Override
	public void actualizar(UUID id, CalificacionEntidad entidad) {
		var sql = "UPDATE calificacion SET id_publicacion = ?, id_autor = ?, estrellas = ?, fecha_registro = ?, fecha_modificacion = ? WHERE id_calificacion = ?";
		try (var sentencia = getConexion().prepareStatement(sql)) {
			sentencia.setObject(1, entidad.getIdPublicacion());
			sentencia.setObject(2, entidad.getAutor().getId());
			sentencia.setInt(3, entidad.getEstrellas());
			sentencia.setTimestamp(4, UtilSQL.aTimestamp(entidad.getFechaRegistro()));
			sentencia.setTimestamp(5, UtilSQL.aTimestamp(entidad.getFechaModificacion()));
			sentencia.setObject(6, id);
			sentencia.executeUpdate();
		} catch (SQLException excepcion) {
			throw KudosDatosExcepcion.crear(CatalogoMensajes.Datos.USUARIO_ERROR_ACTUALIZANDO.formatted("Calificacion"),
					excepcion.getMessage(), excepcion);
		}
	}
	@Override
	public CalificacionEntidad consultarPorId(UUID id) {
		if (UtilUUID.esValorDefecto(id)) {
			return new CalificacionEntidad();
		}
		var filtro = new CalificacionEntidad();
		filtro.setId(id);
		var resultado = consultarPorFiltro(filtro);
		return resultado.isEmpty() ? new CalificacionEntidad() : resultado.get(0);
	}

	@Override
	public List<CalificacionEntidad> consultarTodos() {
		return consultarPorFiltro(new CalificacionEntidad());
	}

	@Override
	public List<CalificacionEntidad> consultarPorFiltro(CalificacionEntidad filtro) {
		var filtroTmp = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(filtro, new CalificacionEntidad());
		var parametros = new ArrayList<Object>();
		var sql = new StringBuilder("SELECT t.id_calificacion, t.id_publicacion, t.id_autor, t.estrellas, t.fecha_registro, t.fecha_modificacion, j2.nombres AS id_autor_nombres, j2.apellidos AS id_autor_apellidos FROM calificacion t LEFT JOIN usuario j2 ON j2.id_usuario = t.id_autor ");
		if (!UtilUUID.esValorDefecto(filtroTmp.getId())) {
			agregarCondicion(sql, parametros, "t.id_calificacion = ?", filtroTmp.getId());
		}
		if (!UtilUUID.esValorDefecto(filtroTmp.getIdPublicacion())) {
			agregarCondicion(sql, parametros, "t.id_publicacion = ?", filtroTmp.getIdPublicacion());
		}
		if (!UtilUUID.esValorDefecto(filtroTmp.getAutor().getId())) {
			agregarCondicion(sql, parametros, "t.id_autor = ?", filtroTmp.getAutor().getId());
		}
		sql.append(" ORDER BY t.fecha_registro");
		return consultar(sql.toString(), parametros);
	}

	private List<CalificacionEntidad> consultar(String sql, List<Object> parametros) {
		var resultado = new ArrayList<CalificacionEntidad>();
		try (var sentencia = getConexion().prepareStatement(sql)) {
			for (int posicion = 0; posicion < parametros.size(); posicion++) {
				sentencia.setObject(posicion + 1, parametros.get(posicion));
			}
			try (var filas = sentencia.executeQuery()) {
				while (filas.next()) {
					var entidad = new CalificacionEntidad();
					entidad.setId(filas.getObject("id_calificacion", UUID.class));
					entidad.setIdPublicacion(filas.getObject("id_publicacion", UUID.class));
					entidad.getAutor().setId(filas.getObject("id_autor", UUID.class));
					entidad.getAutor().setNombres(filas.getString("id_autor_nombres"));
					entidad.getAutor().setApellidos(filas.getString("id_autor_apellidos"));
					entidad.setEstrellas(filas.getInt("estrellas"));
					entidad.setFechaRegistro(UtilSQL.aFechaHora(filas.getTimestamp("fecha_registro")));
					entidad.setFechaModificacion(UtilSQL.aFechaHora(filas.getTimestamp("fecha_modificacion")));
					resultado.add(entidad);
				}
			}
		} catch (SQLException excepcion) {
			throw KudosDatosExcepcion.crear(CatalogoMensajes.Datos.USUARIO_ERROR_CONSULTANDO.formatted("Calificacion"),
					excepcion.getMessage(), excepcion);
		}
		return resultado;
	}
}
