package co.edu.uco.kudos.dao.datos.entidad.postgresql;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import co.edu.uco.kudos.dao.datos.entidad.RegistroAuditoriaDAO;
import co.edu.uco.kudos.dao.datos.entidad.SqlDAO;
import co.edu.uco.kudos.entidad.RegistroAuditoriaEntidad;
import co.edu.uco.kudos.transversal.catalogo.CatalogoMensajes;
import co.edu.uco.kudos.transversal.excepciones.KudosDatosExcepcion;
import co.edu.uco.kudos.transversal.utilitarios.UtilObjeto;
import co.edu.uco.kudos.transversal.utilitarios.UtilSQL;
import co.edu.uco.kudos.transversal.utilitarios.UtilTexto;
import co.edu.uco.kudos.transversal.utilitarios.UtilUUID;

public final class RegistroAuditoriaPostgreSqlDAO extends SqlDAO implements RegistroAuditoriaDAO {

	public RegistroAuditoriaPostgreSqlDAO(Connection conexion) {
		super(conexion);
	}

	@Override
	public void crear(RegistroAuditoriaEntidad entidad) {
		var sql = "INSERT INTO registro_auditoria (id_registro_auditoria, fecha, id_actor, accion, entidad_afectada, id_entidad_afectada, valor_anterior, valor_nuevo) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		try (var sentencia = getConexion().prepareStatement(sql)) {
			sentencia.setObject(1, entidad.getId());
			sentencia.setTimestamp(2, UtilSQL.aTimestamp(entidad.getFecha()));
			sentencia.setObject(3, UtilUUID.esValorDefecto(entidad.getActor().getId()) ? null : entidad.getActor().getId());
			sentencia.setString(4, entidad.getAccion());
			sentencia.setString(5, entidad.getEntidadAfectada());
			sentencia.setObject(6, entidad.getIdEntidadAfectada());
			sentencia.setString(7, UtilTexto.getUtilTexto().esVacia(entidad.getValorAnterior()) ? null : entidad.getValorAnterior());
			sentencia.setString(8, UtilTexto.getUtilTexto().esVacia(entidad.getValorNuevo()) ? null : entidad.getValorNuevo());
			sentencia.executeUpdate();
		} catch (SQLException excepcion) {
			throw KudosDatosExcepcion.crear(CatalogoMensajes.Datos.USUARIO_ERROR_CREANDO.formatted("RegistroAuditoria"),
					excepcion.getMessage(), excepcion);
		}
	}
	@Override
	public RegistroAuditoriaEntidad consultarPorId(UUID id) {
		if (UtilUUID.esValorDefecto(id)) {
			return new RegistroAuditoriaEntidad();
		}
		var filtro = new RegistroAuditoriaEntidad();
		filtro.setId(id);
		var resultado = consultarPorFiltro(filtro);
		return resultado.isEmpty() ? new RegistroAuditoriaEntidad() : resultado.get(0);
	}

	@Override
	public List<RegistroAuditoriaEntidad> consultarTodos() {
		return consultarPorFiltro(new RegistroAuditoriaEntidad());
	}

	@Override
	public List<RegistroAuditoriaEntidad> consultarPorFiltro(RegistroAuditoriaEntidad filtro) {
		var filtroTmp = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(filtro, new RegistroAuditoriaEntidad());
		var parametros = new ArrayList<Object>();
		var sql = new StringBuilder("SELECT t.id_registro_auditoria, t.fecha, t.id_actor, t.accion, t.entidad_afectada, t.id_entidad_afectada, t.valor_anterior, t.valor_nuevo, j2.nombres AS id_actor_nombres, j2.apellidos AS id_actor_apellidos FROM registro_auditoria t LEFT JOIN usuario j2 ON j2.id_usuario = t.id_actor ");
		if (!UtilUUID.esValorDefecto(filtroTmp.getId())) {
			agregarCondicion(sql, parametros, "t.id_registro_auditoria = ?", filtroTmp.getId());
		}
		if (!UtilUUID.esValorDefecto(filtroTmp.getActor().getId())) {
			agregarCondicion(sql, parametros, "t.id_actor = ?", filtroTmp.getActor().getId());
		}
		if (!UtilTexto.getUtilTexto().esVacia(filtroTmp.getAccion())) {
			agregarCondicion(sql, parametros, "t.accion = ?", filtroTmp.getAccion());
		}
		if (!UtilTexto.getUtilTexto().esVacia(filtroTmp.getEntidadAfectada())) {
			agregarCondicion(sql, parametros, "t.entidad_afectada = ?", filtroTmp.getEntidadAfectada());
		}
		if (!UtilUUID.esValorDefecto(filtroTmp.getIdEntidadAfectada())) {
			agregarCondicion(sql, parametros, "t.id_entidad_afectada = ?", filtroTmp.getIdEntidadAfectada());
		}
		sql.append(" ORDER BY t.fecha DESC");
		return consultar(sql.toString(), parametros);
	}

	private List<RegistroAuditoriaEntidad> consultar(String sql, List<Object> parametros) {
		var resultado = new ArrayList<RegistroAuditoriaEntidad>();
		try (var sentencia = getConexion().prepareStatement(sql)) {
			for (int posicion = 0; posicion < parametros.size(); posicion++) {
				sentencia.setObject(posicion + 1, parametros.get(posicion));
			}
			try (var filas = sentencia.executeQuery()) {
				while (filas.next()) {
					var entidad = new RegistroAuditoriaEntidad();
					entidad.setId(filas.getObject("id_registro_auditoria", UUID.class));
					entidad.setFecha(UtilSQL.aFechaHora(filas.getTimestamp("fecha")));
					entidad.getActor().setId(filas.getObject("id_actor", UUID.class));
					entidad.getActor().setNombres(filas.getString("id_actor_nombres"));
					entidad.getActor().setApellidos(filas.getString("id_actor_apellidos"));
					entidad.setAccion(filas.getString("accion"));
					entidad.setEntidadAfectada(filas.getString("entidad_afectada"));
					entidad.setIdEntidadAfectada(filas.getObject("id_entidad_afectada", UUID.class));
					entidad.setValorAnterior(filas.getString("valor_anterior"));
					entidad.setValorNuevo(filas.getString("valor_nuevo"));
					resultado.add(entidad);
				}
			}
		} catch (SQLException excepcion) {
			throw KudosDatosExcepcion.crear(CatalogoMensajes.Datos.USUARIO_ERROR_CONSULTANDO.formatted("RegistroAuditoria"),
					excepcion.getMessage(), excepcion);
		}
		return resultado;
	}
}
