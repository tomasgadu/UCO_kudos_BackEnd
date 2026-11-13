package co.edu.uco.kudos.dao.datos.entidad.postgresql;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import co.edu.uco.kudos.dao.datos.entidad.VinculacionProgramaDAO;
import co.edu.uco.kudos.dao.datos.entidad.SqlDAO;
import co.edu.uco.kudos.entidad.VinculacionProgramaEntidad;
import co.edu.uco.kudos.transversal.catalogo.CatalogoMensajes;
import co.edu.uco.kudos.transversal.excepciones.KudosDatosExcepcion;
import co.edu.uco.kudos.transversal.utilitarios.UtilObjeto;
import co.edu.uco.kudos.transversal.utilitarios.UtilSQL;
import co.edu.uco.kudos.transversal.utilitarios.UtilUUID;

public final class VinculacionProgramaPostgreSqlDAO extends SqlDAO implements VinculacionProgramaDAO {

	public VinculacionProgramaPostgreSqlDAO(Connection conexion) {
		super(conexion);
	}

	@Override
	public void crear(VinculacionProgramaEntidad entidad) {
		var sql = "INSERT INTO vinculacion_programa (id_vinculacion_programa, id_usuario, id_programa_academico, fecha_inicio, fecha_fin) VALUES (?, ?, ?, ?, ?)";
		try (var sentencia = getConexion().prepareStatement(sql)) {
			sentencia.setObject(1, entidad.getId());
			sentencia.setObject(2, entidad.getUsuario().getId());
			sentencia.setObject(3, entidad.getProgramaAcademico().getId());
			sentencia.setTimestamp(4, UtilSQL.aTimestamp(entidad.getFechaInicio()));
			sentencia.setTimestamp(5, UtilSQL.aTimestamp(entidad.getFechaFin()));
			sentencia.executeUpdate();
		} catch (SQLException excepcion) {
			throw KudosDatosExcepcion.crear(CatalogoMensajes.Datos.USUARIO_ERROR_CREANDO.formatted("VinculacionPrograma"),
					excepcion.getMessage(), excepcion);
		}
	}
	@Override
	public void actualizar(UUID id, VinculacionProgramaEntidad entidad) {
		var sql = "UPDATE vinculacion_programa SET id_usuario = ?, id_programa_academico = ?, fecha_inicio = ?, fecha_fin = ? WHERE id_vinculacion_programa = ?";
		try (var sentencia = getConexion().prepareStatement(sql)) {
			sentencia.setObject(1, entidad.getUsuario().getId());
			sentencia.setObject(2, entidad.getProgramaAcademico().getId());
			sentencia.setTimestamp(3, UtilSQL.aTimestamp(entidad.getFechaInicio()));
			sentencia.setTimestamp(4, UtilSQL.aTimestamp(entidad.getFechaFin()));
			sentencia.setObject(5, id);
			sentencia.executeUpdate();
		} catch (SQLException excepcion) {
			throw KudosDatosExcepcion.crear(CatalogoMensajes.Datos.USUARIO_ERROR_ACTUALIZANDO.formatted("VinculacionPrograma"),
					excepcion.getMessage(), excepcion);
		}
	}
	@Override
	public VinculacionProgramaEntidad consultarPorId(UUID id) {
		if (UtilUUID.esValorDefecto(id)) {
			return new VinculacionProgramaEntidad();
		}
		var filtro = new VinculacionProgramaEntidad();
		filtro.setId(id);
		var resultado = consultarPorFiltro(filtro);
		return resultado.isEmpty() ? new VinculacionProgramaEntidad() : resultado.get(0);
	}

	@Override
	public List<VinculacionProgramaEntidad> consultarTodos() {
		return consultarPorFiltro(new VinculacionProgramaEntidad());
	}

	@Override
	public List<VinculacionProgramaEntidad> consultarPorFiltro(VinculacionProgramaEntidad filtro) {
		var filtroTmp = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(filtro, new VinculacionProgramaEntidad());
		var parametros = new ArrayList<Object>();
		var sql = new StringBuilder("SELECT t.id_vinculacion_programa, t.id_usuario, t.id_programa_academico, t.fecha_inicio, t.fecha_fin, j1.nombres AS id_usuario_nombres, j1.apellidos AS id_usuario_apellidos, j2.nombre AS id_programa_academico_nombre FROM vinculacion_programa t LEFT JOIN usuario j1 ON j1.id_usuario = t.id_usuario LEFT JOIN programa_academico j2 ON j2.id_programa_academico = t.id_programa_academico ");
		if (!UtilUUID.esValorDefecto(filtroTmp.getId())) {
			agregarCondicion(sql, parametros, "t.id_vinculacion_programa = ?", filtroTmp.getId());
		}
		if (!UtilUUID.esValorDefecto(filtroTmp.getUsuario().getId())) {
			agregarCondicion(sql, parametros, "t.id_usuario = ?", filtroTmp.getUsuario().getId());
		}
		if (!UtilUUID.esValorDefecto(filtroTmp.getProgramaAcademico().getId())) {
			agregarCondicion(sql, parametros, "t.id_programa_academico = ?", filtroTmp.getProgramaAcademico().getId());
		}
		sql.append(" ORDER BY t.fecha_inicio DESC");
		return consultar(sql.toString(), parametros);
	}

	private List<VinculacionProgramaEntidad> consultar(String sql, List<Object> parametros) {
		var resultado = new ArrayList<VinculacionProgramaEntidad>();
		try (var sentencia = getConexion().prepareStatement(sql)) {
			for (int posicion = 0; posicion < parametros.size(); posicion++) {
				sentencia.setObject(posicion + 1, parametros.get(posicion));
			}
			try (var filas = sentencia.executeQuery()) {
				while (filas.next()) {
					var entidad = new VinculacionProgramaEntidad();
					entidad.setId(filas.getObject("id_vinculacion_programa", UUID.class));
					entidad.getUsuario().setId(filas.getObject("id_usuario", UUID.class));
					entidad.getUsuario().setNombres(filas.getString("id_usuario_nombres"));
					entidad.getUsuario().setApellidos(filas.getString("id_usuario_apellidos"));
					entidad.getProgramaAcademico().setId(filas.getObject("id_programa_academico", UUID.class));
					entidad.getProgramaAcademico().setNombre(filas.getString("id_programa_academico_nombre"));
					entidad.setFechaInicio(UtilSQL.aFechaHora(filas.getTimestamp("fecha_inicio")));
					entidad.setFechaFin(UtilSQL.aFechaHora(filas.getTimestamp("fecha_fin")));
					resultado.add(entidad);
				}
			}
		} catch (SQLException excepcion) {
			throw KudosDatosExcepcion.crear(CatalogoMensajes.Datos.USUARIO_ERROR_CONSULTANDO.formatted("VinculacionPrograma"),
					excepcion.getMessage(), excepcion);
		}
		return resultado;
	}
}
