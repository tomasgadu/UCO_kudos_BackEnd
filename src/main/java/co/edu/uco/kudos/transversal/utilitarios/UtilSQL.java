package co.edu.uco.kudos.transversal.utilitarios;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import co.edu.uco.kudos.transversal.catalogo.CatalogoMensajes;
import co.edu.uco.kudos.transversal.excepciones.KudosTransversalExcepcion;

public final class UtilSQL {

	private UtilSQL() {
	}

	public static boolean conexionEstaVacia(Connection conexion) {
		return UtilObjeto.esNulo(conexion);
	}

	public static boolean conexionEstaAbierta(Connection conexion) {
		try {
			return !conexionEstaVacia(conexion) && !conexion.isClosed();
		} catch (SQLException excepcion) {
			var mensajeUsuario = CatalogoMensajes.UtilSQL.USUARIO_ERROR_VALIDANDO_CONEXION;
			throw KudosTransversalExcepcion.crear(mensajeUsuario, excepcion.getMessage(), excepcion);
		}
	}

	public static boolean transaccionEstaIniciada(Connection conexion) {
		if (!conexionEstaAbierta(conexion)) {
			return false;
		}
		try {
			return !conexion.getAutoCommit();
		} catch (SQLException excepcion) {
			var mensajeUsuario = CatalogoMensajes.UtilSQL.USUARIO_ERROR_VALIDANDO_TRANSACCION;
			throw KudosTransversalExcepcion.crear(mensajeUsuario, excepcion.getMessage(), excepcion);
		}
	}

	public static void asegurarConexionAbierta(Connection conexion) {
		if (!conexionEstaAbierta(conexion)) {
			throw KudosTransversalExcepcion.crear(CatalogoMensajes.UtilSQL.USUARIO_ERROR_CONEXION_NO_ABIERTA);
		}
	}

	public static void iniciarTransaccion(Connection conexion) {
		if (!conexionEstaAbierta(conexion) || transaccionEstaIniciada(conexion)) {
			throw KudosTransversalExcepcion.crear(CatalogoMensajes.UtilSQL.USUARIO_ERROR_INICIANDO_TRANSACCION);
		}
		try {
			conexion.setAutoCommit(false);
		} catch (SQLException excepcion) {
			var mensajeUsuario = CatalogoMensajes.UtilSQL.USUARIO_ERROR_INICIANDO_TRANSACCION;
			throw KudosTransversalExcepcion.crear(mensajeUsuario, excepcion.getMessage(), excepcion);
		}
	}

	public static void confirmarTransaccion(Connection conexion) {
		if (!transaccionEstaIniciada(conexion)) {
			throw KudosTransversalExcepcion.crear(CatalogoMensajes.UtilSQL.USUARIO_ERROR_CONFIRMANDO_TRANSACCION);
		}
		try {
			conexion.commit();
		} catch (SQLException excepcion) {
			var mensajeUsuario = CatalogoMensajes.UtilSQL.USUARIO_ERROR_CONFIRMANDO_TRANSACCION;
			throw KudosTransversalExcepcion.crear(mensajeUsuario, excepcion.getMessage(), excepcion);
		}
	}

	public static void cancelarTransaccion(Connection conexion) {
		if (!transaccionEstaIniciada(conexion)) {
			return;
		}
		try {
			conexion.rollback();
		} catch (SQLException excepcion) {
			var mensajeUsuario = CatalogoMensajes.UtilSQL.USUARIO_ERROR_CANCELANDO_TRANSACCION;
			throw KudosTransversalExcepcion.crear(mensajeUsuario, excepcion.getMessage(), excepcion);
		}
	}

	public static void cerrarConexion(Connection conexion) {
		if (!conexionEstaAbierta(conexion)) {
			return;
		}
		try {
			conexion.close();
		} catch (SQLException excepcion) {
			var mensajeUsuario = CatalogoMensajes.UtilSQL.USUARIO_ERROR_CERRANDO_CONEXION;
			throw KudosTransversalExcepcion.crear(mensajeUsuario, excepcion.getMessage(), excepcion);
		}
	}

	public static Timestamp aTimestamp(LocalDateTime fechaHora) {
		return UtilFecha.esValorDefecto(fechaHora) ? null : Timestamp.valueOf(fechaHora);
	}

	public static LocalDateTime aFechaHora(Timestamp timestamp) {
		return UtilObjeto.esNulo(timestamp) ? UtilFecha.FECHA_HORA_DEFECTO : timestamp.toLocalDateTime();
	}
}
