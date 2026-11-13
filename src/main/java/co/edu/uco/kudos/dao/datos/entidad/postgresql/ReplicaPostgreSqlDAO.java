package co.edu.uco.kudos.dao.datos.entidad.postgresql;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import co.edu.uco.kudos.dao.datos.entidad.ReplicaDAO;
import co.edu.uco.kudos.dao.datos.entidad.SqlDAO;
import co.edu.uco.kudos.entidad.ReplicaEntidad;
import co.edu.uco.kudos.transversal.catalogo.CatalogoMensajes;
import co.edu.uco.kudos.transversal.excepciones.KudosDatosExcepcion;
import co.edu.uco.kudos.transversal.utilitarios.UtilObjeto;
import co.edu.uco.kudos.transversal.utilitarios.UtilSQL;
import co.edu.uco.kudos.transversal.utilitarios.UtilTexto;
import co.edu.uco.kudos.transversal.utilitarios.UtilUUID;

public final class ReplicaPostgreSqlDAO extends SqlDAO implements ReplicaDAO {

	private static final String SELECT = "SELECT p.id_publicacion, p.id_autor, p.contenido, p.fecha_creacion, p.estado, "
			+ "r.id_padre, r.id_kudo_raiz, a.nombres AS autor_nombres, a.apellidos AS autor_apellidos "
			+ "FROM replica r JOIN publicacion p ON p.id_publicacion = r.id_replica "
			+ "JOIN usuario a ON a.id_usuario = p.id_autor ";

	public ReplicaPostgreSqlDAO(Connection conexion) {
		super(conexion);
	}

	@Override
	public void crear(ReplicaEntidad entidad) {
		var sqlPublicacion = "INSERT INTO publicacion (id_publicacion, tipo, id_autor, contenido, fecha_creacion, estado) VALUES (?, 'REPLICA', ?, ?, ?, ?)";
		var sqlReplica = "INSERT INTO replica (id_replica, id_padre, id_kudo_raiz) VALUES (?, ?, ?)";
		try (var publicacion = getConexion().prepareStatement(sqlPublicacion);
				var replica = getConexion().prepareStatement(sqlReplica)) {
			publicacion.setObject(1, entidad.getId());
			publicacion.setObject(2, entidad.getAutor().getId());
			publicacion.setString(3, entidad.getContenido());
			publicacion.setTimestamp(4, UtilSQL.aTimestamp(entidad.getFechaCreacion()));
			publicacion.setString(5, entidad.getEstado());
			publicacion.executeUpdate();
			replica.setObject(1, entidad.getId());
			replica.setObject(2, entidad.getIdPadre());
			replica.setObject(3, entidad.getIdKudoRaiz());
			replica.executeUpdate();
		} catch (SQLException excepcion) {
			throw KudosDatosExcepcion.crear(CatalogoMensajes.Datos.USUARIO_ERROR_CREANDO.formatted("Replica"),
					excepcion.getMessage(), excepcion);
		}
	}

	@Override
	public void actualizar(UUID id, ReplicaEntidad entidad) {
		var sql = "UPDATE publicacion SET estado = ? WHERE id_publicacion = ?";
		try (var sentencia = getConexion().prepareStatement(sql)) {
			sentencia.setString(1, entidad.getEstado());
			sentencia.setObject(2, id);
			sentencia.executeUpdate();
		} catch (SQLException excepcion) {
			throw KudosDatosExcepcion.crear(CatalogoMensajes.Datos.USUARIO_ERROR_ACTUALIZANDO.formatted("Replica"),
					excepcion.getMessage(), excepcion);
		}
	}

	@Override
	public ReplicaEntidad consultarPorId(UUID id) {
		if (UtilUUID.esValorDefecto(id)) {
			return new ReplicaEntidad();
		}
		var filtro = new ReplicaEntidad();
		filtro.setId(id);
		var resultado = consultarPorFiltro(filtro);
		return resultado.isEmpty() ? new ReplicaEntidad() : resultado.get(0);
	}

	@Override
	public List<ReplicaEntidad> consultarTodos() {
		return consultarPorFiltro(new ReplicaEntidad());
	}

	@Override
	public List<ReplicaEntidad> consultarPorFiltro(ReplicaEntidad filtro) {
		var filtroTmp = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(filtro, new ReplicaEntidad());
		var parametros = new ArrayList<Object>();
		var sql = new StringBuilder(SELECT);
		if (!UtilUUID.esValorDefecto(filtroTmp.getId())) {
			agregarCondicion(sql, parametros, "p.id_publicacion = ?", filtroTmp.getId());
		}
		if (!UtilUUID.esValorDefecto(filtroTmp.getIdPadre())) {
			agregarCondicion(sql, parametros, "r.id_padre = ?", filtroTmp.getIdPadre());
		}
		if (!UtilUUID.esValorDefecto(filtroTmp.getIdKudoRaiz())) {
			agregarCondicion(sql, parametros, "r.id_kudo_raiz = ?", filtroTmp.getIdKudoRaiz());
		}
		if (!UtilTexto.getUtilTexto().esVacia(filtroTmp.getEstado())) {
			agregarCondicion(sql, parametros, "p.estado = ?", filtroTmp.getEstado());
		}
		sql.append(" ORDER BY p.fecha_creacion");
		return consultar(sql.toString(), parametros);
	}

	private List<ReplicaEntidad> consultar(String sql, List<Object> parametros) {
		var resultado = new ArrayList<ReplicaEntidad>();
		try (var sentencia = getConexion().prepareStatement(sql)) {
			for (int posicion = 0; posicion < parametros.size(); posicion++) {
				sentencia.setObject(posicion + 1, parametros.get(posicion));
			}
			try (var filas = sentencia.executeQuery()) {
				while (filas.next()) {
					var entidad = new ReplicaEntidad();
					entidad.setId(filas.getObject("id_publicacion", UUID.class));
					entidad.getAutor().setId(filas.getObject("id_autor", UUID.class));
					entidad.getAutor().setNombres(filas.getString("autor_nombres"));
					entidad.getAutor().setApellidos(filas.getString("autor_apellidos"));
					entidad.setContenido(filas.getString("contenido"));
					entidad.setFechaCreacion(UtilSQL.aFechaHora(filas.getTimestamp("fecha_creacion")));
					entidad.setEstado(filas.getString("estado"));
					entidad.setIdPadre(filas.getObject("id_padre", UUID.class));
					entidad.setIdKudoRaiz(filas.getObject("id_kudo_raiz", UUID.class));
					resultado.add(entidad);
				}
			}
		} catch (SQLException excepcion) {
			throw KudosDatosExcepcion.crear(CatalogoMensajes.Datos.USUARIO_ERROR_CONSULTANDO.formatted("Replica"),
					excepcion.getMessage(), excepcion);
		}
		return resultado;
	}
}
