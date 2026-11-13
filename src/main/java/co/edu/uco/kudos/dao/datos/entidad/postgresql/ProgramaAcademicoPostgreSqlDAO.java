package co.edu.uco.kudos.dao.datos.entidad.postgresql;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import co.edu.uco.kudos.dao.datos.entidad.ProgramaAcademicoDAO;
import co.edu.uco.kudos.dao.datos.entidad.SqlDAO;
import co.edu.uco.kudos.entidad.ProgramaAcademicoEntidad;
import co.edu.uco.kudos.transversal.catalogo.CatalogoMensajes;
import co.edu.uco.kudos.transversal.excepciones.KudosDatosExcepcion;
import co.edu.uco.kudos.transversal.utilitarios.UtilObjeto;
import co.edu.uco.kudos.transversal.utilitarios.UtilSQL;
import co.edu.uco.kudos.transversal.utilitarios.UtilTexto;
import co.edu.uco.kudos.transversal.utilitarios.UtilUUID;

public final class ProgramaAcademicoPostgreSqlDAO extends SqlDAO implements ProgramaAcademicoDAO {

	public ProgramaAcademicoPostgreSqlDAO(Connection conexion) {
		super(conexion);
	}

	@Override
	public void crear(ProgramaAcademicoEntidad entidad) {
		var sql = "INSERT INTO programa_academico (id_programa_academico, nombre, id_facultad, estado, fecha_creacion) VALUES (?, ?, ?, ?, ?)";
		try (var sentencia = getConexion().prepareStatement(sql)) {
			sentencia.setObject(1, entidad.getId());
			sentencia.setString(2, entidad.getNombre());
			sentencia.setObject(3, entidad.getFacultad().getId());
			sentencia.setString(4, entidad.getEstado());
			sentencia.setTimestamp(5, UtilSQL.aTimestamp(entidad.getFechaCreacion()));
			sentencia.executeUpdate();
		} catch (SQLException excepcion) {
			throw KudosDatosExcepcion.crear(CatalogoMensajes.Datos.USUARIO_ERROR_CREANDO.formatted("ProgramaAcademico"),
					excepcion.getMessage(), excepcion);
		}
	}
	@Override
	public void actualizar(UUID id, ProgramaAcademicoEntidad entidad) {
		var sql = "UPDATE programa_academico SET nombre = ?, id_facultad = ?, estado = ?, fecha_creacion = ? WHERE id_programa_academico = ?";
		try (var sentencia = getConexion().prepareStatement(sql)) {
			sentencia.setString(1, entidad.getNombre());
			sentencia.setObject(2, entidad.getFacultad().getId());
			sentencia.setString(3, entidad.getEstado());
			sentencia.setTimestamp(4, UtilSQL.aTimestamp(entidad.getFechaCreacion()));
			sentencia.setObject(5, id);
			sentencia.executeUpdate();
		} catch (SQLException excepcion) {
			throw KudosDatosExcepcion.crear(CatalogoMensajes.Datos.USUARIO_ERROR_ACTUALIZANDO.formatted("ProgramaAcademico"),
					excepcion.getMessage(), excepcion);
		}
	}
	@Override
	public ProgramaAcademicoEntidad consultarPorId(UUID id) {
		if (UtilUUID.esValorDefecto(id)) {
			return new ProgramaAcademicoEntidad();
		}
		var filtro = new ProgramaAcademicoEntidad();
		filtro.setId(id);
		var resultado = consultarPorFiltro(filtro);
		return resultado.isEmpty() ? new ProgramaAcademicoEntidad() : resultado.get(0);
	}

	@Override
	public List<ProgramaAcademicoEntidad> consultarTodos() {
		return consultarPorFiltro(new ProgramaAcademicoEntidad());
	}

	@Override
	public List<ProgramaAcademicoEntidad> consultarPorFiltro(ProgramaAcademicoEntidad filtro) {
		var filtroTmp = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(filtro, new ProgramaAcademicoEntidad());
		var parametros = new ArrayList<Object>();
		var sql = new StringBuilder("SELECT t.id_programa_academico, t.nombre, t.id_facultad, t.estado, t.fecha_creacion, j2.nombre AS id_facultad_nombre FROM programa_academico t LEFT JOIN facultad j2 ON j2.id_facultad = t.id_facultad ");
		if (!UtilUUID.esValorDefecto(filtroTmp.getId())) {
			agregarCondicion(sql, parametros, "t.id_programa_academico = ?", filtroTmp.getId());
		}
		if (!UtilTexto.getUtilTexto().esVacia(filtroTmp.getNombre())) {
			agregarCondicion(sql, parametros, "t.nombre = ?", filtroTmp.getNombre());
		}
		if (!UtilUUID.esValorDefecto(filtroTmp.getFacultad().getId())) {
			agregarCondicion(sql, parametros, "t.id_facultad = ?", filtroTmp.getFacultad().getId());
		}
		if (!UtilTexto.getUtilTexto().esVacia(filtroTmp.getEstado())) {
			agregarCondicion(sql, parametros, "t.estado = ?", filtroTmp.getEstado());
		}
		sql.append(" ORDER BY t.nombre");
		return consultar(sql.toString(), parametros);
	}

	private List<ProgramaAcademicoEntidad> consultar(String sql, List<Object> parametros) {
		var resultado = new ArrayList<ProgramaAcademicoEntidad>();
		try (var sentencia = getConexion().prepareStatement(sql)) {
			for (int posicion = 0; posicion < parametros.size(); posicion++) {
				sentencia.setObject(posicion + 1, parametros.get(posicion));
			}
			try (var filas = sentencia.executeQuery()) {
				while (filas.next()) {
					var entidad = new ProgramaAcademicoEntidad();
					entidad.setId(filas.getObject("id_programa_academico", UUID.class));
					entidad.setNombre(filas.getString("nombre"));
					entidad.getFacultad().setId(filas.getObject("id_facultad", UUID.class));
					entidad.getFacultad().setNombre(filas.getString("id_facultad_nombre"));
					entidad.setEstado(filas.getString("estado"));
					entidad.setFechaCreacion(UtilSQL.aFechaHora(filas.getTimestamp("fecha_creacion")));
					resultado.add(entidad);
				}
			}
		} catch (SQLException excepcion) {
			throw KudosDatosExcepcion.crear(CatalogoMensajes.Datos.USUARIO_ERROR_CONSULTANDO.formatted("ProgramaAcademico"),
					excepcion.getMessage(), excepcion);
		}
		return resultado;
	}
}
