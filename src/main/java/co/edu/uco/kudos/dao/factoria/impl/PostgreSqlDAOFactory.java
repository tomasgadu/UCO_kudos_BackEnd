package co.edu.uco.kudos.dao.factoria.impl;

import java.sql.DriverManager;
import java.sql.SQLException;

import co.edu.uco.kudos.dao.datos.entidad.RolDAO;
import co.edu.uco.kudos.dao.datos.entidad.postgresql.RolPostgreSqlDAO;
import co.edu.uco.kudos.dao.datos.entidad.FacultadDAO;
import co.edu.uco.kudos.dao.datos.entidad.postgresql.FacultadPostgreSqlDAO;
import co.edu.uco.kudos.dao.datos.entidad.ProgramaAcademicoDAO;
import co.edu.uco.kudos.dao.datos.entidad.postgresql.ProgramaAcademicoPostgreSqlDAO;
import co.edu.uco.kudos.dao.datos.entidad.CategoriaDAO;
import co.edu.uco.kudos.dao.datos.entidad.postgresql.CategoriaPostgreSqlDAO;
import co.edu.uco.kudos.dao.datos.entidad.ParametroSistemaDAO;
import co.edu.uco.kudos.dao.datos.entidad.postgresql.ParametroSistemaPostgreSqlDAO;
import co.edu.uco.kudos.dao.datos.entidad.UsuarioDAO;
import co.edu.uco.kudos.dao.datos.entidad.postgresql.UsuarioPostgreSqlDAO;
import co.edu.uco.kudos.dao.datos.entidad.SolicitudRegistroDAO;
import co.edu.uco.kudos.dao.datos.entidad.postgresql.SolicitudRegistroPostgreSqlDAO;
import co.edu.uco.kudos.dao.datos.entidad.VerificacionContactoDAO;
import co.edu.uco.kudos.dao.datos.entidad.postgresql.VerificacionContactoPostgreSqlDAO;
import co.edu.uco.kudos.dao.datos.entidad.CoordinacionFacultadDAO;
import co.edu.uco.kudos.dao.datos.entidad.postgresql.CoordinacionFacultadPostgreSqlDAO;
import co.edu.uco.kudos.dao.datos.entidad.CoordinacionProgramaDAO;
import co.edu.uco.kudos.dao.datos.entidad.postgresql.CoordinacionProgramaPostgreSqlDAO;
import co.edu.uco.kudos.dao.datos.entidad.VinculacionProgramaDAO;
import co.edu.uco.kudos.dao.datos.entidad.postgresql.VinculacionProgramaPostgreSqlDAO;
import co.edu.uco.kudos.dao.datos.entidad.RegistroAuditoriaDAO;
import co.edu.uco.kudos.dao.datos.entidad.postgresql.RegistroAuditoriaPostgreSqlDAO;
import co.edu.uco.kudos.dao.datos.entidad.NotificacionDAO;
import co.edu.uco.kudos.dao.datos.entidad.postgresql.NotificacionPostgreSqlDAO;
import co.edu.uco.kudos.dao.datos.entidad.KudoDAO;
import co.edu.uco.kudos.dao.datos.entidad.postgresql.KudoPostgreSqlDAO;
import co.edu.uco.kudos.dao.datos.entidad.ReplicaDAO;
import co.edu.uco.kudos.dao.datos.entidad.postgresql.ReplicaPostgreSqlDAO;
import co.edu.uco.kudos.dao.datos.entidad.CalificacionDAO;
import co.edu.uco.kudos.dao.datos.entidad.postgresql.CalificacionPostgreSqlDAO;
import co.edu.uco.kudos.dao.datos.entidad.ReporteDAO;
import co.edu.uco.kudos.dao.datos.entidad.postgresql.ReportePostgreSqlDAO;
import co.edu.uco.kudos.dao.datos.entidad.VetoDAO;
import co.edu.uco.kudos.dao.datos.entidad.postgresql.VetoPostgreSqlDAO;
import co.edu.uco.kudos.dao.factoria.DAOFactory;
import co.edu.uco.kudos.transversal.catalogo.CatalogoMensajes;
import co.edu.uco.kudos.transversal.excepciones.KudosDatosExcepcion;
import co.edu.uco.kudos.transversal.utilitarios.UtilTexto;

public final class PostgreSqlDAOFactory extends DAOFactory {

	private static final String URL_DEFECTO = "jdbc:postgresql://localhost:5432/kudos?currentSchema=kudos";
	private static final String USUARIO_DEFECTO = "postgres";

	@Override
	protected void abrirConexion() {
		var texto = UtilTexto.getUtilTexto();
		var url = texto.obtenerValorDefecto(System.getenv("KUDOS_DB_URL"), URL_DEFECTO);
		var usuario = texto.obtenerValorDefecto(System.getenv("KUDOS_DB_USUARIO"), USUARIO_DEFECTO);
		var clave = texto.obtenerValorDefecto(System.getenv("KUDOS_DB_CLAVE"));
		try {
			setConexion(DriverManager.getConnection(url, usuario, clave));
		} catch (SQLException excepcion) {
			var mensajeUsuario = CatalogoMensajes.PostgreSqlDAOFactory.USUARIO_ERROR_ABRIENDO_CONEXION;
			throw KudosDatosExcepcion.crear(mensajeUsuario, excepcion.getMessage(), excepcion);
		}
	}

	@Override
	public RolDAO obtenerRolDAO() {
		return new RolPostgreSqlDAO(getConexion());
	}

	@Override
	public FacultadDAO obtenerFacultadDAO() {
		return new FacultadPostgreSqlDAO(getConexion());
	}

	@Override
	public ProgramaAcademicoDAO obtenerProgramaAcademicoDAO() {
		return new ProgramaAcademicoPostgreSqlDAO(getConexion());
	}

	@Override
	public CategoriaDAO obtenerCategoriaDAO() {
		return new CategoriaPostgreSqlDAO(getConexion());
	}

	@Override
	public ParametroSistemaDAO obtenerParametroSistemaDAO() {
		return new ParametroSistemaPostgreSqlDAO(getConexion());
	}

	@Override
	public UsuarioDAO obtenerUsuarioDAO() {
		return new UsuarioPostgreSqlDAO(getConexion());
	}

	@Override
	public SolicitudRegistroDAO obtenerSolicitudRegistroDAO() {
		return new SolicitudRegistroPostgreSqlDAO(getConexion());
	}

	@Override
	public VerificacionContactoDAO obtenerVerificacionContactoDAO() {
		return new VerificacionContactoPostgreSqlDAO(getConexion());
	}

	@Override
	public CoordinacionFacultadDAO obtenerCoordinacionFacultadDAO() {
		return new CoordinacionFacultadPostgreSqlDAO(getConexion());
	}

	@Override
	public CoordinacionProgramaDAO obtenerCoordinacionProgramaDAO() {
		return new CoordinacionProgramaPostgreSqlDAO(getConexion());
	}

	@Override
	public VinculacionProgramaDAO obtenerVinculacionProgramaDAO() {
		return new VinculacionProgramaPostgreSqlDAO(getConexion());
	}

	@Override
	public RegistroAuditoriaDAO obtenerRegistroAuditoriaDAO() {
		return new RegistroAuditoriaPostgreSqlDAO(getConexion());
	}

	@Override
	public NotificacionDAO obtenerNotificacionDAO() {
		return new NotificacionPostgreSqlDAO(getConexion());
	}

	@Override
	public KudoDAO obtenerKudoDAO() {
		return new KudoPostgreSqlDAO(getConexion());
	}

	@Override
	public ReplicaDAO obtenerReplicaDAO() {
		return new ReplicaPostgreSqlDAO(getConexion());
	}

	@Override
	public CalificacionDAO obtenerCalificacionDAO() {
		return new CalificacionPostgreSqlDAO(getConexion());
	}

	@Override
	public ReporteDAO obtenerReporteDAO() {
		return new ReportePostgreSqlDAO(getConexion());
	}

	@Override
	public VetoDAO obtenerVetoDAO() {
		return new VetoPostgreSqlDAO(getConexion());
	}
}
