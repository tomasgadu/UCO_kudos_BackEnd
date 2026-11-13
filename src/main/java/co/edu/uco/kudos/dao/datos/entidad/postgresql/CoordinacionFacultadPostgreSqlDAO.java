package co.edu.uco.kudos.dao.datos.entidad.postgresql;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import co.edu.uco.kudos.dao.datos.entidad.CoordinacionFacultadDAO;
import co.edu.uco.kudos.dao.datos.entidad.SqlDAO;
import co.edu.uco.kudos.entidad.CoordinacionFacultadEntidad;
import co.edu.uco.kudos.transversal.catalogo.CatalogoMensajes;
import co.edu.uco.kudos.transversal.excepciones.KudosDatosExcepcion;
import co.edu.uco.kudos.transversal.utilitarios.UtilObjeto;
import co.edu.uco.kudos.transversal.utilitarios.UtilSQL;
import co.edu.uco.kudos.transversal.utilitarios.UtilUUID;

public final class CoordinacionFacultadPostgreSqlDAO extends SqlDAO implements CoordinacionFacultadDAO {

	public CoordinacionFacultadPostgreSqlDAO(Connection conexion) {
		super(conexion);
	}

	@Override
	public void crear(CoordinacionFacultadEntidad entidad) {
		var sql = "INSERT INTO coordinacion_facultad (id_coordinacion_facultad, id_coordinador, id_facultad, fecha_inicio, fecha_fin) VALUES (?, ?, ?, ?, ?)";
		try (var sentencia = getConexion().prepareStatement(sql)) {
			sentencia.setObject(1, entidad.getId());
			sentencia.setObject(2, entidad.getCoordinador().getId());
			sentencia.setObject(3, entidad.getFacultad().getId());
			sentencia.setTimestamp(4, UtilSQL.aTimestamp(entidad.getFechaInicio()));
			sentencia.setTimestamp(5, UtilSQL.aTimestamp(entidad.getFechaFin()));
			sentencia.executeUpdate();
		} catch (SQLException excepcion) {
			throw KudosDatosExcepcion.crear(CatalogoMensajes.Datos.USUARIO_ERROR_CREANDO.formatted("CoordinacionFacultad"),
					excepcion.getMessage(), excepcion);
		}
	}
	@Override
	public void actualizar(UUID id, CoordinacionFacultadEntidad entidad) {
		var sql = "UPDATE coordinacion_facultad SET id_coordinador = ?, id_facultad = ?, fecha_inicio = ?, fecha_fin = ? WHERE id_coordinacion_facultad = ?";
		try (var sentencia = getConexion().prepareStatement(sql)) {
			sentencia.setObject(1, entidad.getCoordinador().getId());
			sentencia.setObject(2, entidad.getFacultad().getId());
			sentencia.setTimestamp(3, UtilSQL.aTimestamp(entidad.getFechaInicio()));
			sentencia.setTimestamp(4, UtilSQL.aTimestamp(entidad.getFechaFin()));
			sentencia.setObject(5, id);
			sentencia.executeUpdate();
		} catch (SQLException excepcion) {
			throw KudosDatosExcepcion.crear(CatalogoMensajes.Datos.USUARIO_ERROR_ACTUALIZANDO.formatted("CoordinacionFacultad"),
					excepcion.getMessage(), excepcion);
		}
	}
	@Override
	public CoordinacionFacultadEntidad consultarPorId(UUID id) {
		if (UtilUUID.esValorDefecto(id)) {
			return new CoordinacionFacultadEntidad();
		}
		var filtro = new CoordinacionFacultadEntidad();
		filtro.setId(id);
		var resultado = consultarPorFiltro(filtro);
		return resultado.isEmpty() ? new CoordinacionFacultadEntidad() : resultado.get(0);
	}

	@Override
	public List<CoordinacionFacultadEntidad> consultarTodos() {
		return consultarPorFiltro(new CoordinacionFacultadEntidad());
	}

	@Override
	public List<CoordinacionFacultadEntidad> consultarPorFiltro(CoordinacionFacultadEntidad filtro) {
		var filtroTmp = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(filtro, new CoordinacionFacultadEntidad());
		var parametros = new ArrayList<Object>();
		var sql = new StringBuilder("SELECT t.id_coordinacion_facultad, t.id_coordinador, t.id_facultad, t.fecha_inicio, t.fecha_fin, j1.nombres AS id_coordinador_nombres, j1.apellidos AS id_coordinador_apellidos, j2.nombre AS id_facultad_nombre FROM coordinacion_facultad t LEFT JOIN usuario j1 ON j1.id_usuario = t.id_coordinador LEFT JOIN facultad j2 ON j2.id_facultad = t.id_facultad ");
		if (!UtilUUID.esValorDefecto(filtroTmp.getId())) {
			agregarCondicion(sql, parametros, "t.id_coordinacion_facultad = ?", filtroTmp.getId());
		}
		if (!UtilUUID.esValorDefecto(filtroTmp.getCoordinador().getId())) {
			agregarCondicion(sql, parametros, "t.id_coordinador = ?", filtroTmp.getCoordinador().getId());
		}
		if (!UtilUUID.esValorDefecto(filtroTmp.getFacultad().getId())) {
			agregarCondicion(sql, parametros, "t.id_facultad = ?", filtroTmp.getFacultad().getId());
		}
		sql.append(" ORDER BY t.fecha_inicio DESC");
		return consultar(sql.toString(), parametros);
	}

	private List<CoordinacionFacultadEntidad> consultar(String sql, List<Object> parametros) {
		var resultado = new ArrayList<CoordinacionFacultadEntidad>();
		try (var sentencia = getConexion().prepareStatement(sql)) {
			for (int posicion = 0; posicion < parametros.size(); posicion++) {
				sentencia.setObject(posicion + 1, parametros.get(posicion));
			}
			try (var filas = sentencia.executeQuery()) {
				while (filas.next()) {
					var entidad = new CoordinacionFacultadEntidad();
					entidad.setId(filas.getObject("id_coordinacion_facultad", UUID.class));
					entidad.getCoordinador().setId(filas.getObject("id_coordinador", UUID.class));
					entidad.getCoordinador().setNombres(filas.getString("id_coordinador_nombres"));
					entidad.getCoordinador().setApellidos(filas.getString("id_coordinador_apellidos"));
					entidad.getFacultad().setId(filas.getObject("id_facultad", UUID.class));
					entidad.getFacultad().setNombre(filas.getString("id_facultad_nombre"));
					entidad.setFechaInicio(UtilSQL.aFechaHora(filas.getTimestamp("fecha_inicio")));
					entidad.setFechaFin(UtilSQL.aFechaHora(filas.getTimestamp("fecha_fin")));
					resultado.add(entidad);
				}
			}
		} catch (SQLException excepcion) {
			throw KudosDatosExcepcion.crear(CatalogoMensajes.Datos.USUARIO_ERROR_CONSULTANDO.formatted("CoordinacionFacultad"),
					excepcion.getMessage(), excepcion);
		}
		return resultado;
	}
}
