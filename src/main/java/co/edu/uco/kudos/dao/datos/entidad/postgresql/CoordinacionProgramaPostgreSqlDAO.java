package co.edu.uco.kudos.dao.datos.entidad.postgresql;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import co.edu.uco.kudos.dao.datos.entidad.CoordinacionProgramaDAO;
import co.edu.uco.kudos.dao.datos.entidad.SqlDAO;
import co.edu.uco.kudos.entidad.CoordinacionProgramaEntidad;
import co.edu.uco.kudos.transversal.catalogo.CatalogoMensajes;
import co.edu.uco.kudos.transversal.excepciones.KudosDatosExcepcion;
import co.edu.uco.kudos.transversal.utilitarios.UtilObjeto;
import co.edu.uco.kudos.transversal.utilitarios.UtilSQL;
import co.edu.uco.kudos.transversal.utilitarios.UtilUUID;

public final class CoordinacionProgramaPostgreSqlDAO extends SqlDAO implements CoordinacionProgramaDAO {

	public CoordinacionProgramaPostgreSqlDAO(Connection conexion) {
		super(conexion);
	}

	@Override
	public void crear(CoordinacionProgramaEntidad entidad) {
		var sql = "INSERT INTO coordinacion_programa (id_coordinacion_programa, id_coordinador, id_programa_academico, fecha_inicio, fecha_fin) VALUES (?, ?, ?, ?, ?)";
		try (var sentencia = getConexion().prepareStatement(sql)) {
			sentencia.setObject(1, entidad.getId());
			sentencia.setObject(2, entidad.getCoordinador().getId());
			sentencia.setObject(3, entidad.getProgramaAcademico().getId());
			sentencia.setTimestamp(4, UtilSQL.aTimestamp(entidad.getFechaInicio()));
			sentencia.setTimestamp(5, UtilSQL.aTimestamp(entidad.getFechaFin()));
			sentencia.executeUpdate();
		} catch (SQLException excepcion) {
			throw KudosDatosExcepcion.crear(CatalogoMensajes.Datos.USUARIO_ERROR_CREANDO.formatted("CoordinacionPrograma"),
					excepcion.getMessage(), excepcion);
		}
	}
	@Override
	public void actualizar(UUID id, CoordinacionProgramaEntidad entidad) {
		var sql = "UPDATE coordinacion_programa SET id_coordinador = ?, id_programa_academico = ?, fecha_inicio = ?, fecha_fin = ? WHERE id_coordinacion_programa = ?";
		try (var sentencia = getConexion().prepareStatement(sql)) {
			sentencia.setObject(1, entidad.getCoordinador().getId());
			sentencia.setObject(2, entidad.getProgramaAcademico().getId());
			sentencia.setTimestamp(3, UtilSQL.aTimestamp(entidad.getFechaInicio()));
			sentencia.setTimestamp(4, UtilSQL.aTimestamp(entidad.getFechaFin()));
			sentencia.setObject(5, id);
			sentencia.executeUpdate();
		} catch (SQLException excepcion) {
			throw KudosDatosExcepcion.crear(CatalogoMensajes.Datos.USUARIO_ERROR_ACTUALIZANDO.formatted("CoordinacionPrograma"),
					excepcion.getMessage(), excepcion);
		}
	}
	@Override
	public CoordinacionProgramaEntidad consultarPorId(UUID id) {
		if (UtilUUID.esValorDefecto(id)) {
			return new CoordinacionProgramaEntidad();
		}
		var filtro = new CoordinacionProgramaEntidad();
		filtro.setId(id);
		var resultado = consultarPorFiltro(filtro);
		return resultado.isEmpty() ? new CoordinacionProgramaEntidad() : resultado.get(0);
	}

	@Override
	public List<CoordinacionProgramaEntidad> consultarTodos() {
		return consultarPorFiltro(new CoordinacionProgramaEntidad());
	}

	@Override
	public List<CoordinacionProgramaEntidad> consultarPorFiltro(CoordinacionProgramaEntidad filtro) {
		var filtroTmp = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(filtro, new CoordinacionProgramaEntidad());
		var parametros = new ArrayList<Object>();
		var sql = new StringBuilder("SELECT t.id_coordinacion_programa, t.id_coordinador, t.id_programa_academico, t.fecha_inicio, t.fecha_fin, j1.nombres AS id_coordinador_nombres, j1.apellidos AS id_coordinador_apellidos, j2.nombre AS id_programa_nombre FROM coordinacion_programa t LEFT JOIN usuario j1 ON j1.id_usuario = t.id_coordinador LEFT JOIN programa_academico j2 ON j2.id_programa_academico = t.id_programa_academico ");
		if (!UtilUUID.esValorDefecto(filtroTmp.getId())) {
			agregarCondicion(sql, parametros, "t.id_coordinacion_programa = ?", filtroTmp.getId());
		}
		if (!UtilUUID.esValorDefecto(filtroTmp.getCoordinador().getId())) {
			agregarCondicion(sql, parametros, "t.id_coordinador = ?", filtroTmp.getCoordinador().getId());
		}
		if (!UtilUUID.esValorDefecto(filtroTmp.getProgramaAcademico().getId())) {
			agregarCondicion(sql, parametros, "t.id_programa_academico = ?", filtroTmp.getProgramaAcademico().getId());
		}
		sql.append(" ORDER BY t.fecha_inicio DESC");
		return consultar(sql.toString(), parametros);
	}

	private List<CoordinacionProgramaEntidad> consultar(String sql, List<Object> parametros) {
		var resultado = new ArrayList<CoordinacionProgramaEntidad>();
		try (var sentencia = getConexion().prepareStatement(sql)) {
			for (int posicion = 0; posicion < parametros.size(); posicion++) {
				sentencia.setObject(posicion + 1, parametros.get(posicion));
			}
			try (var filas = sentencia.executeQuery()) {
				while (filas.next()) {
					var entidad = new CoordinacionProgramaEntidad();
					entidad.setId(filas.getObject("id_coordinacion_programa", UUID.class));
					entidad.getCoordinador().setId(filas.getObject("id_coordinador", UUID.class));
					entidad.getCoordinador().setNombres(filas.getString("id_coordinador_nombres"));
					entidad.getCoordinador().setApellidos(filas.getString("id_coordinador_apellidos"));
					entidad.getProgramaAcademico().setId(filas.getObject("id_programa_academico", UUID.class));
					entidad.getProgramaAcademico().setNombre(filas.getString("id_programa_nombre"));
					entidad.setFechaInicio(UtilSQL.aFechaHora(filas.getTimestamp("fecha_inicio")));
					entidad.setFechaFin(UtilSQL.aFechaHora(filas.getTimestamp("fecha_fin")));
					resultado.add(entidad);
				}
			}
		} catch (SQLException excepcion) {
			throw KudosDatosExcepcion.crear(CatalogoMensajes.Datos.USUARIO_ERROR_CONSULTANDO.formatted("CoordinacionPrograma"),
					excepcion.getMessage(), excepcion);
		}
		return resultado;
	}
}
