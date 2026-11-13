package co.edu.uco.kudos.dao.datos.entidad.postgresql;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import co.edu.uco.kudos.dao.datos.entidad.VetoDAO;
import co.edu.uco.kudos.dao.datos.entidad.SqlDAO;
import co.edu.uco.kudos.entidad.VetoEntidad;
import co.edu.uco.kudos.transversal.catalogo.CatalogoMensajes;
import co.edu.uco.kudos.transversal.excepciones.KudosDatosExcepcion;
import co.edu.uco.kudos.transversal.utilitarios.UtilObjeto;
import co.edu.uco.kudos.transversal.utilitarios.UtilSQL;
import co.edu.uco.kudos.transversal.utilitarios.UtilUUID;

public final class VetoPostgreSqlDAO extends SqlDAO implements VetoDAO {

	public VetoPostgreSqlDAO(Connection conexion) {
		super(conexion);
	}

	@Override
	public void crear(VetoEntidad entidad) {
		var sql = "INSERT INTO veto (id_veto, id_publicacion, id_autoridad, id_reporte_origen, justificacion, fecha_veto) VALUES (?, ?, ?, ?, ?, ?)";
		try (var sentencia = getConexion().prepareStatement(sql)) {
			sentencia.setObject(1, entidad.getId());
			sentencia.setObject(2, entidad.getIdPublicacion());
			sentencia.setObject(3, entidad.getAutoridad().getId());
			sentencia.setObject(4, UtilUUID.esValorDefecto(entidad.getIdReporteOrigen()) ? null : entidad.getIdReporteOrigen());
			sentencia.setString(5, entidad.getJustificacion());
			sentencia.setTimestamp(6, UtilSQL.aTimestamp(entidad.getFechaVeto()));
			sentencia.executeUpdate();
		} catch (SQLException excepcion) {
			throw KudosDatosExcepcion.crear(CatalogoMensajes.Datos.USUARIO_ERROR_CREANDO.formatted("Veto"),
					excepcion.getMessage(), excepcion);
		}
	}
	@Override
	public VetoEntidad consultarPorId(UUID id) {
		if (UtilUUID.esValorDefecto(id)) {
			return new VetoEntidad();
		}
		var filtro = new VetoEntidad();
		filtro.setId(id);
		var resultado = consultarPorFiltro(filtro);
		return resultado.isEmpty() ? new VetoEntidad() : resultado.get(0);
	}

	@Override
	public List<VetoEntidad> consultarTodos() {
		return consultarPorFiltro(new VetoEntidad());
	}

	@Override
	public List<VetoEntidad> consultarPorFiltro(VetoEntidad filtro) {
		var filtroTmp = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(filtro, new VetoEntidad());
		var parametros = new ArrayList<Object>();
		var sql = new StringBuilder("SELECT t.id_veto, t.id_publicacion, t.id_autoridad, t.id_reporte_origen, t.justificacion, t.fecha_veto, j2.nombres AS id_autoridad_nombres, j2.apellidos AS id_autoridad_apellidos FROM veto t LEFT JOIN usuario j2 ON j2.id_usuario = t.id_autoridad ");
		if (!UtilUUID.esValorDefecto(filtroTmp.getId())) {
			agregarCondicion(sql, parametros, "t.id_veto = ?", filtroTmp.getId());
		}
		if (!UtilUUID.esValorDefecto(filtroTmp.getIdPublicacion())) {
			agregarCondicion(sql, parametros, "t.id_publicacion = ?", filtroTmp.getIdPublicacion());
		}
		if (!UtilUUID.esValorDefecto(filtroTmp.getAutoridad().getId())) {
			agregarCondicion(sql, parametros, "t.id_autoridad = ?", filtroTmp.getAutoridad().getId());
		}
		if (!UtilUUID.esValorDefecto(filtroTmp.getIdReporteOrigen())) {
			agregarCondicion(sql, parametros, "t.id_reporte_origen = ?", filtroTmp.getIdReporteOrigen());
		}
		sql.append(" ORDER BY t.fecha_veto DESC");
		return consultar(sql.toString(), parametros);
	}

	private List<VetoEntidad> consultar(String sql, List<Object> parametros) {
		var resultado = new ArrayList<VetoEntidad>();
		try (var sentencia = getConexion().prepareStatement(sql)) {
			for (int posicion = 0; posicion < parametros.size(); posicion++) {
				sentencia.setObject(posicion + 1, parametros.get(posicion));
			}
			try (var filas = sentencia.executeQuery()) {
				while (filas.next()) {
					var entidad = new VetoEntidad();
					entidad.setId(filas.getObject("id_veto", UUID.class));
					entidad.setIdPublicacion(filas.getObject("id_publicacion", UUID.class));
					entidad.getAutoridad().setId(filas.getObject("id_autoridad", UUID.class));
					entidad.getAutoridad().setNombres(filas.getString("id_autoridad_nombres"));
					entidad.getAutoridad().setApellidos(filas.getString("id_autoridad_apellidos"));
					entidad.setIdReporteOrigen(filas.getObject("id_reporte_origen", UUID.class));
					entidad.setJustificacion(filas.getString("justificacion"));
					entidad.setFechaVeto(UtilSQL.aFechaHora(filas.getTimestamp("fecha_veto")));
					resultado.add(entidad);
				}
			}
		} catch (SQLException excepcion) {
			throw KudosDatosExcepcion.crear(CatalogoMensajes.Datos.USUARIO_ERROR_CONSULTANDO.formatted("Veto"),
					excepcion.getMessage(), excepcion);
		}
		return resultado;
	}
}
