package co.edu.uco.kudos.dao.factoria;

import java.sql.Connection;

import co.edu.uco.kudos.dao.datos.entidad.RolDAO;
import co.edu.uco.kudos.dao.datos.entidad.FacultadDAO;
import co.edu.uco.kudos.dao.datos.entidad.ProgramaAcademicoDAO;
import co.edu.uco.kudos.dao.datos.entidad.CategoriaDAO;
import co.edu.uco.kudos.dao.datos.entidad.ParametroSistemaDAO;
import co.edu.uco.kudos.dao.datos.entidad.UsuarioDAO;
import co.edu.uco.kudos.dao.datos.entidad.SolicitudRegistroDAO;
import co.edu.uco.kudos.dao.datos.entidad.VerificacionContactoDAO;
import co.edu.uco.kudos.dao.datos.entidad.CoordinacionFacultadDAO;
import co.edu.uco.kudos.dao.datos.entidad.CoordinacionProgramaDAO;
import co.edu.uco.kudos.dao.datos.entidad.VinculacionProgramaDAO;
import co.edu.uco.kudos.dao.datos.entidad.RegistroAuditoriaDAO;
import co.edu.uco.kudos.dao.datos.entidad.NotificacionDAO;
import co.edu.uco.kudos.dao.factoria.enums.FactoriaEnum;
import co.edu.uco.kudos.dao.factoria.impl.PostgreSqlDAOFactory;
import co.edu.uco.kudos.transversal.catalogo.CatalogoMensajes;
import co.edu.uco.kudos.transversal.excepciones.KudosDatosExcepcion;
import co.edu.uco.kudos.transversal.utilitarios.UtilSQL;

public abstract class DAOFactory {

	private Connection conexion;

	private static final FactoriaEnum FACTORIA = FactoriaEnum.POSTGRESQL;

	public static DAOFactory obtenerFactoria() {
		if (FACTORIA == FactoriaEnum.POSTGRESQL) {
			return new PostgreSqlDAOFactory();
		}
		throw KudosDatosExcepcion.crear(CatalogoMensajes.DAOFactory.USUARIO_ERROR_FUENTE_INFORMACION_NO_HABILITADA);
	}

	protected DAOFactory() {
		abrirConexion();
	}

	protected Connection getConexion() {
		return conexion;
	}

	protected void setConexion(Connection conexion) {
		UtilSQL.asegurarConexionAbierta(conexion);
		this.conexion = conexion;
	}

	protected abstract void abrirConexion();

	public void iniciarTransaccion() {
		UtilSQL.iniciarTransaccion(conexion);
	}

	public void confirmarTransaccion() {
		UtilSQL.confirmarTransaccion(conexion);
	}

	public void cancelarTransaccion() {
		UtilSQL.cancelarTransaccion(conexion);
	}

	public void cerrarConexion() {
		UtilSQL.cerrarConexion(conexion);
	}

	public abstract RolDAO obtenerRolDAO();

	public abstract FacultadDAO obtenerFacultadDAO();

	public abstract ProgramaAcademicoDAO obtenerProgramaAcademicoDAO();

	public abstract CategoriaDAO obtenerCategoriaDAO();

	public abstract ParametroSistemaDAO obtenerParametroSistemaDAO();

	public abstract UsuarioDAO obtenerUsuarioDAO();

	public abstract SolicitudRegistroDAO obtenerSolicitudRegistroDAO();

	public abstract VerificacionContactoDAO obtenerVerificacionContactoDAO();

	public abstract CoordinacionFacultadDAO obtenerCoordinacionFacultadDAO();

	public abstract CoordinacionProgramaDAO obtenerCoordinacionProgramaDAO();

	public abstract VinculacionProgramaDAO obtenerVinculacionProgramaDAO();

	public abstract RegistroAuditoriaDAO obtenerRegistroAuditoriaDAO();

	public abstract NotificacionDAO obtenerNotificacionDAO();
}
