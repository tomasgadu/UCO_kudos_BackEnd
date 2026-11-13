package co.edu.uco.kudos.dao.datos.entidad.postgresql;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import co.edu.uco.kudos.dao.datos.entidad.NotificacionDAO;
import co.edu.uco.kudos.dao.datos.entidad.SqlDAO;
import co.edu.uco.kudos.entidad.NotificacionEntidad;
import co.edu.uco.kudos.transversal.catalogo.CatalogoMensajes;
import co.edu.uco.kudos.transversal.excepciones.KudosDatosExcepcion;
import co.edu.uco.kudos.transversal.utilitarios.UtilObjeto;
import co.edu.uco.kudos.transversal.utilitarios.UtilSQL;
import co.edu.uco.kudos.transversal.utilitarios.UtilTexto;
import co.edu.uco.kudos.transversal.utilitarios.UtilUUID;

public final class NotificacionPostgreSqlDAO extends SqlDAO implements NotificacionDAO {

	public NotificacionPostgreSqlDAO(Connection conexion) {
		super(conexion);
	}

	@Override
	public void crear(NotificacionEntidad entidad) {
		var sql = "INSERT INTO notificacion (id_notificacion, id_destinatario, tipo, mensaje, fecha_creacion, leida) VALUES (?, ?, ?, ?, ?, ?)";
		try (var sentencia = getConexion().prepareStatement(sql)) {
			sentencia.setObject(1, entidad.getId());
			sentencia.setObject(2, entidad.getDestinatario().getId());
			sentencia.setString(3, entidad.getTipo());
			sentencia.setString(4, entidad.getMensaje());
			sentencia.setTimestamp(5, UtilSQL.aTimestamp(entidad.getFechaCreacion()));
			sentencia.setBoolean(6, entidad.isLeida());
			sentencia.executeUpdate();
		} catch (SQLException excepcion) {
			throw KudosDatosExcepcion.crear(CatalogoMensajes.Datos.USUARIO_ERROR_CREANDO.formatted("Notificacion"),
					excepcion.getMessage(), excepcion);
		}
	}
	@Override
	public void actualizar(UUID id, NotificacionEntidad entidad) {
		var sql = "UPDATE notificacion SET id_destinatario = ?, tipo = ?, mensaje = ?, fecha_creacion = ?, leida = ? WHERE id_notificacion = ?";
		try (var sentencia = getConexion().prepareStatement(sql)) {
			sentencia.setObject(1, entidad.getDestinatario().getId());
			sentencia.setString(2, entidad.getTipo());
			sentencia.setString(3, entidad.getMensaje());
			sentencia.setTimestamp(4, UtilSQL.aTimestamp(entidad.getFechaCreacion()));
			sentencia.setBoolean(5, entidad.isLeida());
			sentencia.setObject(6, id);
			sentencia.executeUpdate();
		} catch (SQLException excepcion) {
			throw KudosDatosExcepcion.crear(CatalogoMensajes.Datos.USUARIO_ERROR_ACTUALIZANDO.formatted("Notificacion"),
					excepcion.getMessage(), excepcion);
		}
	}
	@Override
	public NotificacionEntidad consultarPorId(UUID id) {
		if (UtilUUID.esValorDefecto(id)) {
			return new NotificacionEntidad();
		}
		var filtro = new NotificacionEntidad();
		filtro.setId(id);
		var resultado = consultarPorFiltro(filtro);
		return resultado.isEmpty() ? new NotificacionEntidad() : resultado.get(0);
	}

	@Override
	public List<NotificacionEntidad> consultarTodos() {
		return consultarPorFiltro(new NotificacionEntidad());
	}

	@Override
	public List<NotificacionEntidad> consultarPorFiltro(NotificacionEntidad filtro) {
		var filtroTmp = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(filtro, new NotificacionEntidad());
		var parametros = new ArrayList<Object>();
		var sql = new StringBuilder("SELECT t.id_notificacion, t.id_destinatario, t.tipo, t.mensaje, t.fecha_creacion, t.leida FROM notificacion t ");
		if (!UtilUUID.esValorDefecto(filtroTmp.getId())) {
			agregarCondicion(sql, parametros, "t.id_notificacion = ?", filtroTmp.getId());
		}
		if (!UtilUUID.esValorDefecto(filtroTmp.getDestinatario().getId())) {
			agregarCondicion(sql, parametros, "t.id_destinatario = ?", filtroTmp.getDestinatario().getId());
		}
		if (!UtilTexto.getUtilTexto().esVacia(filtroTmp.getTipo())) {
			agregarCondicion(sql, parametros, "t.tipo = ?", filtroTmp.getTipo());
		}
		sql.append(" ORDER BY t.leida, t.fecha_creacion DESC");
		return consultar(sql.toString(), parametros);
	}

	private List<NotificacionEntidad> consultar(String sql, List<Object> parametros) {
		var resultado = new ArrayList<NotificacionEntidad>();
		try (var sentencia = getConexion().prepareStatement(sql)) {
			for (int posicion = 0; posicion < parametros.size(); posicion++) {
				sentencia.setObject(posicion + 1, parametros.get(posicion));
			}
			try (var filas = sentencia.executeQuery()) {
				while (filas.next()) {
					var entidad = new NotificacionEntidad();
					entidad.setId(filas.getObject("id_notificacion", UUID.class));
					entidad.getDestinatario().setId(filas.getObject("id_destinatario", UUID.class));
					entidad.setTipo(filas.getString("tipo"));
					entidad.setMensaje(filas.getString("mensaje"));
					entidad.setFechaCreacion(UtilSQL.aFechaHora(filas.getTimestamp("fecha_creacion")));
					entidad.setLeida(filas.getBoolean("leida"));
					resultado.add(entidad);
				}
			}
		} catch (SQLException excepcion) {
			throw KudosDatosExcepcion.crear(CatalogoMensajes.Datos.USUARIO_ERROR_CONSULTANDO.formatted("Notificacion"),
					excepcion.getMessage(), excepcion);
		}
		return resultado;
	}
}
