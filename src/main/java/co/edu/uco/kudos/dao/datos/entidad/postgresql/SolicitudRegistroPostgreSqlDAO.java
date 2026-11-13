package co.edu.uco.kudos.dao.datos.entidad.postgresql;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import co.edu.uco.kudos.dao.datos.entidad.SolicitudRegistroDAO;
import co.edu.uco.kudos.dao.datos.entidad.SqlDAO;
import co.edu.uco.kudos.entidad.SolicitudRegistroEntidad;
import co.edu.uco.kudos.transversal.catalogo.CatalogoMensajes;
import co.edu.uco.kudos.transversal.excepciones.KudosDatosExcepcion;
import co.edu.uco.kudos.transversal.utilitarios.UtilObjeto;
import co.edu.uco.kudos.transversal.utilitarios.UtilSQL;
import co.edu.uco.kudos.transversal.utilitarios.UtilTexto;
import co.edu.uco.kudos.transversal.utilitarios.UtilUUID;

public final class SolicitudRegistroPostgreSqlDAO extends SqlDAO implements SolicitudRegistroDAO {

	public SolicitudRegistroPostgreSqlDAO(Connection conexion) {
		super(conexion);
	}

	@Override
	public void crear(SolicitudRegistroEntidad entidad) {
		var sql = "INSERT INTO solicitud_registro (id_solicitud_registro, id_solicitante, id_rol_solicitado, id_facultad, id_programa_academico, estado, fecha_solicitud, id_aprobador, fecha_decision, observacion) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try (var sentencia = getConexion().prepareStatement(sql)) {
			sentencia.setObject(1, entidad.getId());
			sentencia.setObject(2, entidad.getSolicitante().getId());
			sentencia.setObject(3, entidad.getRolSolicitado().getId());
			sentencia.setObject(4, UtilUUID.esValorDefecto(entidad.getFacultad().getId()) ? null : entidad.getFacultad().getId());
			sentencia.setObject(5, UtilUUID.esValorDefecto(entidad.getProgramaAcademico().getId()) ? null : entidad.getProgramaAcademico().getId());
			sentencia.setString(6, entidad.getEstado());
			sentencia.setTimestamp(7, UtilSQL.aTimestamp(entidad.getFechaSolicitud()));
			sentencia.setObject(8, UtilUUID.esValorDefecto(entidad.getAprobador().getId()) ? null : entidad.getAprobador().getId());
			sentencia.setTimestamp(9, UtilSQL.aTimestamp(entidad.getFechaDecision()));
			sentencia.setString(10, UtilTexto.getUtilTexto().esVacia(entidad.getObservacion()) ? null : entidad.getObservacion());
			sentencia.executeUpdate();
		} catch (SQLException excepcion) {
			throw KudosDatosExcepcion.crear(CatalogoMensajes.Datos.USUARIO_ERROR_CREANDO.formatted("SolicitudRegistro"),
					excepcion.getMessage(), excepcion);
		}
	}
	@Override
	public void actualizar(UUID id, SolicitudRegistroEntidad entidad) {
		var sql = "UPDATE solicitud_registro SET id_solicitante = ?, id_rol_solicitado = ?, id_facultad = ?, id_programa_academico = ?, estado = ?, fecha_solicitud = ?, id_aprobador = ?, fecha_decision = ?, observacion = ? WHERE id_solicitud_registro = ?";
		try (var sentencia = getConexion().prepareStatement(sql)) {
			sentencia.setObject(1, entidad.getSolicitante().getId());
			sentencia.setObject(2, entidad.getRolSolicitado().getId());
			sentencia.setObject(3, UtilUUID.esValorDefecto(entidad.getFacultad().getId()) ? null : entidad.getFacultad().getId());
			sentencia.setObject(4, UtilUUID.esValorDefecto(entidad.getProgramaAcademico().getId()) ? null : entidad.getProgramaAcademico().getId());
			sentencia.setString(5, entidad.getEstado());
			sentencia.setTimestamp(6, UtilSQL.aTimestamp(entidad.getFechaSolicitud()));
			sentencia.setObject(7, UtilUUID.esValorDefecto(entidad.getAprobador().getId()) ? null : entidad.getAprobador().getId());
			sentencia.setTimestamp(8, UtilSQL.aTimestamp(entidad.getFechaDecision()));
			sentencia.setString(9, UtilTexto.getUtilTexto().esVacia(entidad.getObservacion()) ? null : entidad.getObservacion());
			sentencia.setObject(10, id);
			sentencia.executeUpdate();
		} catch (SQLException excepcion) {
			throw KudosDatosExcepcion.crear(CatalogoMensajes.Datos.USUARIO_ERROR_ACTUALIZANDO.formatted("SolicitudRegistro"),
					excepcion.getMessage(), excepcion);
		}
	}
	@Override
	public SolicitudRegistroEntidad consultarPorId(UUID id) {
		if (UtilUUID.esValorDefecto(id)) {
			return new SolicitudRegistroEntidad();
		}
		var filtro = new SolicitudRegistroEntidad();
		filtro.setId(id);
		var resultado = consultarPorFiltro(filtro);
		return resultado.isEmpty() ? new SolicitudRegistroEntidad() : resultado.get(0);
	}

	@Override
	public List<SolicitudRegistroEntidad> consultarTodos() {
		return consultarPorFiltro(new SolicitudRegistroEntidad());
	}

	@Override
	public List<SolicitudRegistroEntidad> consultarPorFiltro(SolicitudRegistroEntidad filtro) {
		var filtroTmp = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(filtro, new SolicitudRegistroEntidad());
		var parametros = new ArrayList<Object>();
		var sql = new StringBuilder("SELECT t.id_solicitud_registro, t.id_solicitante, t.id_rol_solicitado, t.id_facultad, t.id_programa_academico, t.estado, t.fecha_solicitud, t.id_aprobador, t.fecha_decision, t.observacion, j1.nombres AS id_solicitante_nombres, j1.apellidos AS id_solicitante_apellidos, j1.correo_institucional AS id_solicitante_correo_institucional, j2.codigo AS id_rol_solicitado_codigo, j2.nombre AS id_rol_solicitado_nombre, j3.nombre AS id_facultad_nombre, j4.nombre AS id_programa_academico_nombre, j7.nombres AS id_aprobador_nombres, j7.apellidos AS id_aprobador_apellidos FROM solicitud_registro t LEFT JOIN usuario j1 ON j1.id_usuario = t.id_solicitante LEFT JOIN rol j2 ON j2.id_rol = t.id_rol_solicitado LEFT JOIN facultad j3 ON j3.id_facultad = t.id_facultad LEFT JOIN programa_academico j4 ON j4.id_programa_academico = t.id_programa_academico LEFT JOIN usuario j7 ON j7.id_usuario = t.id_aprobador ");
		if (!UtilUUID.esValorDefecto(filtroTmp.getId())) {
			agregarCondicion(sql, parametros, "t.id_solicitud_registro = ?", filtroTmp.getId());
		}
		if (!UtilUUID.esValorDefecto(filtroTmp.getSolicitante().getId())) {
			agregarCondicion(sql, parametros, "t.id_solicitante = ?", filtroTmp.getSolicitante().getId());
		}
		if (!UtilUUID.esValorDefecto(filtroTmp.getRolSolicitado().getId())) {
			agregarCondicion(sql, parametros, "t.id_rol_solicitado = ?", filtroTmp.getRolSolicitado().getId());
		}
		if (!UtilUUID.esValorDefecto(filtroTmp.getFacultad().getId())) {
			agregarCondicion(sql, parametros, "t.id_facultad = ?", filtroTmp.getFacultad().getId());
		}
		if (!UtilUUID.esValorDefecto(filtroTmp.getProgramaAcademico().getId())) {
			agregarCondicion(sql, parametros, "t.id_programa_academico = ?", filtroTmp.getProgramaAcademico().getId());
		}
		if (!UtilTexto.getUtilTexto().esVacia(filtroTmp.getEstado())) {
			agregarCondicion(sql, parametros, "t.estado = ?", filtroTmp.getEstado());
		}
		if (!UtilUUID.esValorDefecto(filtroTmp.getAprobador().getId())) {
			agregarCondicion(sql, parametros, "t.id_aprobador = ?", filtroTmp.getAprobador().getId());
		}
		sql.append(" ORDER BY t.fecha_solicitud DESC");
		return consultar(sql.toString(), parametros);
	}

	private List<SolicitudRegistroEntidad> consultar(String sql, List<Object> parametros) {
		var resultado = new ArrayList<SolicitudRegistroEntidad>();
		try (var sentencia = getConexion().prepareStatement(sql)) {
			for (int posicion = 0; posicion < parametros.size(); posicion++) {
				sentencia.setObject(posicion + 1, parametros.get(posicion));
			}
			try (var filas = sentencia.executeQuery()) {
				while (filas.next()) {
					var entidad = new SolicitudRegistroEntidad();
					entidad.setId(filas.getObject("id_solicitud_registro", UUID.class));
					entidad.getSolicitante().setId(filas.getObject("id_solicitante", UUID.class));
					entidad.getSolicitante().setNombres(filas.getString("id_solicitante_nombres"));
					entidad.getSolicitante().setApellidos(filas.getString("id_solicitante_apellidos"));
					entidad.getSolicitante().setCorreoInstitucional(filas.getString("id_solicitante_correo_institucional"));
					entidad.getRolSolicitado().setId(filas.getObject("id_rol_solicitado", UUID.class));
					entidad.getRolSolicitado().setCodigo(filas.getString("id_rol_solicitado_codigo"));
					entidad.getRolSolicitado().setNombre(filas.getString("id_rol_solicitado_nombre"));
					entidad.getFacultad().setId(filas.getObject("id_facultad", UUID.class));
					entidad.getFacultad().setNombre(filas.getString("id_facultad_nombre"));
					entidad.getProgramaAcademico().setId(filas.getObject("id_programa_academico", UUID.class));
					entidad.getProgramaAcademico().setNombre(filas.getString("id_programa_academico_nombre"));
					entidad.setEstado(filas.getString("estado"));
					entidad.setFechaSolicitud(UtilSQL.aFechaHora(filas.getTimestamp("fecha_solicitud")));
					entidad.getAprobador().setId(filas.getObject("id_aprobador", UUID.class));
					entidad.getAprobador().setNombres(filas.getString("id_aprobador_nombres"));
					entidad.getAprobador().setApellidos(filas.getString("id_aprobador_apellidos"));
					entidad.setFechaDecision(UtilSQL.aFechaHora(filas.getTimestamp("fecha_decision")));
					entidad.setObservacion(filas.getString("observacion"));
					resultado.add(entidad);
				}
			}
		} catch (SQLException excepcion) {
			throw KudosDatosExcepcion.crear(CatalogoMensajes.Datos.USUARIO_ERROR_CONSULTANDO.formatted("SolicitudRegistro"),
					excepcion.getMessage(), excepcion);
		}
		return resultado;
	}
}
