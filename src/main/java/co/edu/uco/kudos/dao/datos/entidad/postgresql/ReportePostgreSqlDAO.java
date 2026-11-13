package co.edu.uco.kudos.dao.datos.entidad.postgresql;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import co.edu.uco.kudos.dao.datos.entidad.ReporteDAO;
import co.edu.uco.kudos.dao.datos.entidad.SqlDAO;
import co.edu.uco.kudos.entidad.ReporteEntidad;
import co.edu.uco.kudos.transversal.catalogo.CatalogoMensajes;
import co.edu.uco.kudos.transversal.excepciones.KudosDatosExcepcion;
import co.edu.uco.kudos.transversal.utilitarios.UtilObjeto;
import co.edu.uco.kudos.transversal.utilitarios.UtilSQL;
import co.edu.uco.kudos.transversal.utilitarios.UtilTexto;
import co.edu.uco.kudos.transversal.utilitarios.UtilUUID;

public final class ReportePostgreSqlDAO extends SqlDAO implements ReporteDAO {

	public ReportePostgreSqlDAO(Connection conexion) {
		super(conexion);
	}

	@Override
	public void crear(ReporteEntidad entidad) {
		var sql = "INSERT INTO reporte (id_reporte, id_publicacion, id_reportante, motivo, descripcion, fecha_reporte, estado, id_revisor, fecha_decision, observacion_decision) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try (var sentencia = getConexion().prepareStatement(sql)) {
			sentencia.setObject(1, entidad.getId());
			sentencia.setObject(2, entidad.getIdPublicacion());
			sentencia.setObject(3, entidad.getReportante().getId());
			sentencia.setString(4, entidad.getMotivo());
			sentencia.setString(5, UtilTexto.getUtilTexto().esVacia(entidad.getDescripcion()) ? null : entidad.getDescripcion());
			sentencia.setTimestamp(6, UtilSQL.aTimestamp(entidad.getFechaReporte()));
			sentencia.setString(7, entidad.getEstado());
			sentencia.setObject(8, UtilUUID.esValorDefecto(entidad.getRevisor().getId()) ? null : entidad.getRevisor().getId());
			sentencia.setTimestamp(9, UtilSQL.aTimestamp(entidad.getFechaDecision()));
			sentencia.setString(10, UtilTexto.getUtilTexto().esVacia(entidad.getObservacionDecision()) ? null : entidad.getObservacionDecision());
			sentencia.executeUpdate();
		} catch (SQLException excepcion) {
			throw KudosDatosExcepcion.crear(CatalogoMensajes.Datos.USUARIO_ERROR_CREANDO.formatted("Reporte"),
					excepcion.getMessage(), excepcion);
		}
	}
	@Override
	public void actualizar(UUID id, ReporteEntidad entidad) {
		var sql = "UPDATE reporte SET id_publicacion = ?, id_reportante = ?, motivo = ?, descripcion = ?, fecha_reporte = ?, estado = ?, id_revisor = ?, fecha_decision = ?, observacion_decision = ? WHERE id_reporte = ?";
		try (var sentencia = getConexion().prepareStatement(sql)) {
			sentencia.setObject(1, entidad.getIdPublicacion());
			sentencia.setObject(2, entidad.getReportante().getId());
			sentencia.setString(3, entidad.getMotivo());
			sentencia.setString(4, UtilTexto.getUtilTexto().esVacia(entidad.getDescripcion()) ? null : entidad.getDescripcion());
			sentencia.setTimestamp(5, UtilSQL.aTimestamp(entidad.getFechaReporte()));
			sentencia.setString(6, entidad.getEstado());
			sentencia.setObject(7, UtilUUID.esValorDefecto(entidad.getRevisor().getId()) ? null : entidad.getRevisor().getId());
			sentencia.setTimestamp(8, UtilSQL.aTimestamp(entidad.getFechaDecision()));
			sentencia.setString(9, UtilTexto.getUtilTexto().esVacia(entidad.getObservacionDecision()) ? null : entidad.getObservacionDecision());
			sentencia.setObject(10, id);
			sentencia.executeUpdate();
		} catch (SQLException excepcion) {
			throw KudosDatosExcepcion.crear(CatalogoMensajes.Datos.USUARIO_ERROR_ACTUALIZANDO.formatted("Reporte"),
					excepcion.getMessage(), excepcion);
		}
	}
	@Override
	public ReporteEntidad consultarPorId(UUID id) {
		if (UtilUUID.esValorDefecto(id)) {
			return new ReporteEntidad();
		}
		var filtro = new ReporteEntidad();
		filtro.setId(id);
		var resultado = consultarPorFiltro(filtro);
		return resultado.isEmpty() ? new ReporteEntidad() : resultado.get(0);
	}

	@Override
	public List<ReporteEntidad> consultarTodos() {
		return consultarPorFiltro(new ReporteEntidad());
	}

	@Override
	public List<ReporteEntidad> consultarPorFiltro(ReporteEntidad filtro) {
		var filtroTmp = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(filtro, new ReporteEntidad());
		var parametros = new ArrayList<Object>();
		var sql = new StringBuilder("SELECT t.id_reporte, t.id_publicacion, t.id_reportante, t.motivo, t.descripcion, t.fecha_reporte, t.estado, t.id_revisor, t.fecha_decision, t.observacion_decision, j2.nombres AS id_reportante_nombres, j2.apellidos AS id_reportante_apellidos, j7.nombres AS id_revisor_nombres, j7.apellidos AS id_revisor_apellidos FROM reporte t LEFT JOIN usuario j2 ON j2.id_usuario = t.id_reportante LEFT JOIN usuario j7 ON j7.id_usuario = t.id_revisor ");
		if (!UtilUUID.esValorDefecto(filtroTmp.getId())) {
			agregarCondicion(sql, parametros, "t.id_reporte = ?", filtroTmp.getId());
		}
		if (!UtilUUID.esValorDefecto(filtroTmp.getIdPublicacion())) {
			agregarCondicion(sql, parametros, "t.id_publicacion = ?", filtroTmp.getIdPublicacion());
		}
		if (!UtilUUID.esValorDefecto(filtroTmp.getReportante().getId())) {
			agregarCondicion(sql, parametros, "t.id_reportante = ?", filtroTmp.getReportante().getId());
		}
		if (!UtilTexto.getUtilTexto().esVacia(filtroTmp.getMotivo())) {
			agregarCondicion(sql, parametros, "t.motivo = ?", filtroTmp.getMotivo());
		}
		if (!UtilTexto.getUtilTexto().esVacia(filtroTmp.getEstado())) {
			agregarCondicion(sql, parametros, "t.estado = ?", filtroTmp.getEstado());
		}
		if (!UtilUUID.esValorDefecto(filtroTmp.getRevisor().getId())) {
			agregarCondicion(sql, parametros, "t.id_revisor = ?", filtroTmp.getRevisor().getId());
		}
		sql.append(" ORDER BY t.fecha_reporte");
		return consultar(sql.toString(), parametros);
	}

	private List<ReporteEntidad> consultar(String sql, List<Object> parametros) {
		var resultado = new ArrayList<ReporteEntidad>();
		try (var sentencia = getConexion().prepareStatement(sql)) {
			for (int posicion = 0; posicion < parametros.size(); posicion++) {
				sentencia.setObject(posicion + 1, parametros.get(posicion));
			}
			try (var filas = sentencia.executeQuery()) {
				while (filas.next()) {
					var entidad = new ReporteEntidad();
					entidad.setId(filas.getObject("id_reporte", UUID.class));
					entidad.setIdPublicacion(filas.getObject("id_publicacion", UUID.class));
					entidad.getReportante().setId(filas.getObject("id_reportante", UUID.class));
					entidad.getReportante().setNombres(filas.getString("id_reportante_nombres"));
					entidad.getReportante().setApellidos(filas.getString("id_reportante_apellidos"));
					entidad.setMotivo(filas.getString("motivo"));
					entidad.setDescripcion(filas.getString("descripcion"));
					entidad.setFechaReporte(UtilSQL.aFechaHora(filas.getTimestamp("fecha_reporte")));
					entidad.setEstado(filas.getString("estado"));
					entidad.getRevisor().setId(filas.getObject("id_revisor", UUID.class));
					entidad.getRevisor().setNombres(filas.getString("id_revisor_nombres"));
					entidad.getRevisor().setApellidos(filas.getString("id_revisor_apellidos"));
					entidad.setFechaDecision(UtilSQL.aFechaHora(filas.getTimestamp("fecha_decision")));
					entidad.setObservacionDecision(filas.getString("observacion_decision"));
					resultado.add(entidad);
				}
			}
		} catch (SQLException excepcion) {
			throw KudosDatosExcepcion.crear(CatalogoMensajes.Datos.USUARIO_ERROR_CONSULTANDO.formatted("Reporte"),
					excepcion.getMessage(), excepcion);
		}
		return resultado;
	}
}
