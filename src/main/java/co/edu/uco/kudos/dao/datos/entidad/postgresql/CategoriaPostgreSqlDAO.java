package co.edu.uco.kudos.dao.datos.entidad.postgresql;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import co.edu.uco.kudos.dao.datos.entidad.CategoriaDAO;
import co.edu.uco.kudos.dao.datos.entidad.SqlDAO;
import co.edu.uco.kudos.entidad.CategoriaEntidad;
import co.edu.uco.kudos.transversal.catalogo.CatalogoMensajes;
import co.edu.uco.kudos.transversal.excepciones.KudosDatosExcepcion;
import co.edu.uco.kudos.transversal.utilitarios.UtilObjeto;
import co.edu.uco.kudos.transversal.utilitarios.UtilSQL;
import co.edu.uco.kudos.transversal.utilitarios.UtilTexto;
import co.edu.uco.kudos.transversal.utilitarios.UtilUUID;

public final class CategoriaPostgreSqlDAO extends SqlDAO implements CategoriaDAO {

	public CategoriaPostgreSqlDAO(Connection conexion) {
		super(conexion);
	}

	@Override
	public void crear(CategoriaEntidad entidad) {
		var sql = "INSERT INTO categoria (id_categoria, nombre, descripcion, estado, fecha_creacion) VALUES (?, ?, ?, ?, ?)";
		try (var sentencia = getConexion().prepareStatement(sql)) {
			sentencia.setObject(1, entidad.getId());
			sentencia.setString(2, entidad.getNombre());
			sentencia.setString(3, entidad.getDescripcion());
			sentencia.setString(4, entidad.getEstado());
			sentencia.setTimestamp(5, UtilSQL.aTimestamp(entidad.getFechaCreacion()));
			sentencia.executeUpdate();
		} catch (SQLException excepcion) {
			throw KudosDatosExcepcion.crear(CatalogoMensajes.Datos.USUARIO_ERROR_CREANDO.formatted("Categoria"),
					excepcion.getMessage(), excepcion);
		}
	}
	@Override
	public void actualizar(UUID id, CategoriaEntidad entidad) {
		var sql = "UPDATE categoria SET nombre = ?, descripcion = ?, estado = ?, fecha_creacion = ? WHERE id_categoria = ?";
		try (var sentencia = getConexion().prepareStatement(sql)) {
			sentencia.setString(1, entidad.getNombre());
			sentencia.setString(2, entidad.getDescripcion());
			sentencia.setString(3, entidad.getEstado());
			sentencia.setTimestamp(4, UtilSQL.aTimestamp(entidad.getFechaCreacion()));
			sentencia.setObject(5, id);
			sentencia.executeUpdate();
		} catch (SQLException excepcion) {
			throw KudosDatosExcepcion.crear(CatalogoMensajes.Datos.USUARIO_ERROR_ACTUALIZANDO.formatted("Categoria"),
					excepcion.getMessage(), excepcion);
		}
	}
	@Override
	public CategoriaEntidad consultarPorId(UUID id) {
		if (UtilUUID.esValorDefecto(id)) {
			return new CategoriaEntidad();
		}
		var filtro = new CategoriaEntidad();
		filtro.setId(id);
		var resultado = consultarPorFiltro(filtro);
		return resultado.isEmpty() ? new CategoriaEntidad() : resultado.get(0);
	}

	@Override
	public List<CategoriaEntidad> consultarTodos() {
		return consultarPorFiltro(new CategoriaEntidad());
	}

	@Override
	public List<CategoriaEntidad> consultarPorFiltro(CategoriaEntidad filtro) {
		var filtroTmp = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(filtro, new CategoriaEntidad());
		var parametros = new ArrayList<Object>();
		var sql = new StringBuilder("SELECT t.id_categoria, t.nombre, t.descripcion, t.estado, t.fecha_creacion FROM categoria t ");
		if (!UtilUUID.esValorDefecto(filtroTmp.getId())) {
			agregarCondicion(sql, parametros, "t.id_categoria = ?", filtroTmp.getId());
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

	private List<CategoriaEntidad> consultar(String sql, List<Object> parametros) {
		var resultado = new ArrayList<CategoriaEntidad>();
		try (var sentencia = getConexion().prepareStatement(sql)) {
			for (int posicion = 0; posicion < parametros.size(); posicion++) {
				sentencia.setObject(posicion + 1, parametros.get(posicion));
			}
			try (var filas = sentencia.executeQuery()) {
				while (filas.next()) {
					var entidad = new CategoriaEntidad();
					entidad.setId(filas.getObject("id_categoria", UUID.class));
					entidad.setNombre(filas.getString("nombre"));
					entidad.setDescripcion(filas.getString("descripcion"));
					entidad.setEstado(filas.getString("estado"));
					entidad.setFechaCreacion(UtilSQL.aFechaHora(filas.getTimestamp("fecha_creacion")));
					resultado.add(entidad);
				}
			}
		} catch (SQLException excepcion) {
			throw KudosDatosExcepcion.crear(CatalogoMensajes.Datos.USUARIO_ERROR_CONSULTANDO.formatted("Categoria"),
					excepcion.getMessage(), excepcion);
		}
		return resultado;
	}
}
