package co.edu.uco.kudos.dao.datos.entidad.postgresql;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import co.edu.uco.kudos.dao.datos.entidad.FacultadDAO;
import co.edu.uco.kudos.dao.datos.entidad.SqlDAO;
import co.edu.uco.kudos.entidad.FacultadEntidad;
import co.edu.uco.kudos.transversal.catalogo.CatalogoMensajes;
import co.edu.uco.kudos.transversal.excepciones.KudosDatosExcepcion;
import co.edu.uco.kudos.transversal.utilitarios.UtilObjeto;
import co.edu.uco.kudos.transversal.utilitarios.UtilSQL;
import co.edu.uco.kudos.transversal.utilitarios.UtilTexto;
import co.edu.uco.kudos.transversal.utilitarios.UtilUUID;

public final class FacultadPostgreSqlDAO extends SqlDAO implements FacultadDAO {

	public FacultadPostgreSqlDAO(Connection conexion) {
		super(conexion);
	}

	@Override
	public void crear(FacultadEntidad entidad) {
		var sql = "INSERT INTO facultad (id_facultad, nombre, estado, fecha_creacion) VALUES (?, ?, ?, ?)";
		try (var sentencia = getConexion().prepareStatement(sql)) {
			sentencia.setObject(1, entidad.getId());
			sentencia.setString(2, entidad.getNombre());
			sentencia.setString(3, entidad.getEstado());
			sentencia.setTimestamp(4, UtilSQL.aTimestamp(entidad.getFechaCreacion()));
			sentencia.executeUpdate();
		} catch (SQLException excepcion) {
			throw KudosDatosExcepcion.crear(CatalogoMensajes.Datos.USUARIO_ERROR_CREANDO.formatted("Facultad"),
					excepcion.getMessage(), excepcion);
		}
	}
	@Override
	public void actualizar(UUID id, FacultadEntidad entidad) {
		var sql = "UPDATE facultad SET nombre = ?, estado = ?, fecha_creacion = ? WHERE id_facultad = ?";
		try (var sentencia = getConexion().prepareStatement(sql)) {
			sentencia.setString(1, entidad.getNombre());
			sentencia.setString(2, entidad.getEstado());
			sentencia.setTimestamp(3, UtilSQL.aTimestamp(entidad.getFechaCreacion()));
			sentencia.setObject(4, id);
			sentencia.executeUpdate();
		} catch (SQLException excepcion) {
			throw KudosDatosExcepcion.crear(CatalogoMensajes.Datos.USUARIO_ERROR_ACTUALIZANDO.formatted("Facultad"),
					excepcion.getMessage(), excepcion);
		}
	}
	@Override
	public FacultadEntidad consultarPorId(UUID id) {
		if (UtilUUID.esValorDefecto(id)) {
			return new FacultadEntidad();
		}
		var filtro = new FacultadEntidad();
		filtro.setId(id);
		var resultado = consultarPorFiltro(filtro);
		return resultado.isEmpty() ? new FacultadEntidad() : resultado.get(0);
	}

	@Override
	public List<FacultadEntidad> consultarTodos() {
		return consultarPorFiltro(new FacultadEntidad());
	}

	@Override
	public List<FacultadEntidad> consultarPorFiltro(FacultadEntidad filtro) {
		var filtroTmp = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(filtro, new FacultadEntidad());
		var parametros = new ArrayList<Object>();
		var sql = new StringBuilder("SELECT t.id_facultad, t.nombre, t.estado, t.fecha_creacion FROM facultad t ");
		if (!UtilUUID.esValorDefecto(filtroTmp.getId())) {
			agregarCondicion(sql, parametros, "t.id_facultad = ?", filtroTmp.getId());
		}
		if (!UtilTexto.getUtilTexto().esVacia(filtroTmp.getNombre())) {
			agregarCondicion(sql, parametros, "t.nombre = ?", filtroTmp.getNombre());
		}
		if (!UtilTexto.getUtilTexto().esVacia(filtroTmp.getEstado())) {
			agregarCondicion(sql, parametros, "t.estado = ?", filtroTmp.getEstado());
		}
		sql.append(" ORDER BY t.nombre");
		return consultar(sql.toString(), parametros);
	}

	private List<FacultadEntidad> consultar(String sql, List<Object> parametros) {
		var resultado = new ArrayList<FacultadEntidad>();
		try (var sentencia = getConexion().prepareStatement(sql)) {
			for (int posicion = 0; posicion < parametros.size(); posicion++) {
				sentencia.setObject(posicion + 1, parametros.get(posicion));
			}
			try (var filas = sentencia.executeQuery()) {
				while (filas.next()) {
					var entidad = new FacultadEntidad();
					entidad.setId(filas.getObject("id_facultad", UUID.class));
					entidad.setNombre(filas.getString("nombre"));
					entidad.setEstado(filas.getString("estado"));
					entidad.setFechaCreacion(UtilSQL.aFechaHora(filas.getTimestamp("fecha_creacion")));
					resultado.add(entidad);
				}
			}
		} catch (SQLException excepcion) {
			throw KudosDatosExcepcion.crear(CatalogoMensajes.Datos.USUARIO_ERROR_CONSULTANDO.formatted("Facultad"),
					excepcion.getMessage(), excepcion);
		}
		return resultado;
	}
}
