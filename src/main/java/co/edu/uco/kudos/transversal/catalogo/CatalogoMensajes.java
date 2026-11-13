package co.edu.uco.kudos.transversal.catalogo;

public final class CatalogoMensajes {

	private CatalogoMensajes() {
	}

	public static final class General {

		private General() {
		}

		public static final String USUARIO_ERROR_NO_CONTROLADO = "Se ha presentado un problema inesperado. Por favor intente de nuevo y si el problema persiste contacte al administrador.";
		public static final String USUARIO_ERROR_SIN_PERMISO = "No tiene permiso para realizar esta acción.";
		public static final String TECNICO_ERROR_CANTIDAD_DATOS_REGLA = "La regla esperaba al menos {0} datos y recibió {1}.";
	}

	public static final class UtilSQL {

		private UtilSQL() {
		}

		public static final String USUARIO_ERROR_VALIDANDO_CONEXION = "Se presentó un problema validando la conexión con la base de datos.";
		public static final String USUARIO_ERROR_VALIDANDO_TRANSACCION = "Se presentó un problema validando la transacción con la base de datos.";
		public static final String USUARIO_ERROR_CONEXION_NO_ABIERTA = "La conexión con la base de datos no está abierta.";
		public static final String USUARIO_ERROR_INICIANDO_TRANSACCION = "No fue posible iniciar la transacción con la base de datos.";
		public static final String USUARIO_ERROR_CONFIRMANDO_TRANSACCION = "No fue posible confirmar la transacción con la base de datos.";
		public static final String USUARIO_ERROR_CANCELANDO_TRANSACCION = "No fue posible cancelar la transacción con la base de datos.";
		public static final String USUARIO_ERROR_CERRANDO_CONEXION = "No fue posible cerrar la conexión con la base de datos.";
	}

	public static final class DAOFactory {

		private DAOFactory() {
		}

		public static final String USUARIO_ERROR_FUENTE_INFORMACION_NO_HABILITADA = "La fuente de información solicitada no está habilitada.";
	}

	public static final class PostgreSqlDAOFactory {

		private PostgreSqlDAOFactory() {
		}

		public static final String USUARIO_ERROR_ABRIENDO_CONEXION = "No fue posible conectarse con la base de datos. Verifique que PostgreSQL esté en ejecución y que las variables de entorno KUDOS_DB_URL, KUDOS_DB_USUARIO y KUDOS_DB_CLAVE estén configuradas.";
	}

	public static final class AbstractRestController {

		private AbstractRestController() {
		}

		public static final String USUARIO_ERROR_PROBLEMA_INESPERADO = "Se ha presentado un problema inesperado. Código de seguimiento: ";
		public static final String USUARIO_ERROR_RECURSO_NO_ENCONTRADO = "El recurso solicitado no existe. Código de seguimiento: ";
		public static final String TECNICO_ADVERTENCIA_EXCEPCION_CONTROLADA = "Excepción controlada en la capa {}: {}";
		public static final String TECNICO_ERROR_PROBLEMA_INESPERADO = "Error inesperado con código de seguimiento {}";
		public static final String TECNICO_ADVERTENCIA_RECURSO_NO_ENCONTRADO = "Recurso no encontrado {} {} con código de seguimiento {}";
		public static final String USUARIO_ERROR_FORMATO_INVALIDO = "La información enviada no tiene un formato válido. Revise el cuerpo de la petición.";
		public static final String USUARIO_ERROR_IDENTIFICADOR_INVALIDO = "El valor '%s' no tiene un formato válido para '%s'.";
		public static final String USUARIO_ERROR_METODO_NO_PERMITIDO = "La operación %s no está permitida en esta ruta.";
		public static final String TECNICO_ADVERTENCIA_PETICION_INVALIDA = "Petición inválida: {}";
	}

	public static final class EstadoControlador {

		private EstadoControlador() {
		}

		public static final String CONEXION_EXITOSA = "UCO Kudos está en funcionamiento y la conexión con la base de datos es correcta.";
	}

	public static final class Datos {

		private Datos() {
		}

		public static final String USUARIO_ERROR_CREANDO = "Se presentó un problema guardando la información de %s.";
		public static final String USUARIO_ERROR_ACTUALIZANDO = "Se presentó un problema actualizando la información de %s.";
		public static final String USUARIO_ERROR_CONSULTANDO = "Se presentó un problema consultando la información de %s.";
	}

	public static final class Validacion {

		private Validacion() {
		}

		public static final String USUARIO_ERROR_CAMPO_OBLIGATORIO = "El campo %s es obligatorio.";
		public static final String USUARIO_ERROR_CAMPO_LONGITUD = "El campo %s debe tener entre %d y %d caracteres.";
		public static final String USUARIO_ERROR_CAMPO_FORMATO = "El campo %s no tiene un formato válido.";
	}

	public static final class Fachada {

		private Fachada() {
		}

		public static final String USUARIO_ERROR_NO_CONTROLADO = "Se presentó un problema inesperado al %s. Intente de nuevo; si persiste, contacte al administrador.";
	}

	public static final class Rol {

		private Rol() {
		}

		public static final String USUARIO_ERROR_NO_EXISTE = "El rol indicado no existe.";
	}

	public static final class Facultad {

		private Facultad() {
		}

		public static final String USUARIO_ERROR_NO_EXISTE = "La facultad indicada no existe.";
		public static final String USUARIO_ERROR_INACTIVA = "La facultad indicada está inactiva.";
		public static final String USUARIO_ERROR_NOMBRE_EXISTE = "Ya existe una facultad con ese nombre.";
		public static final String FACULTAD_REGISTRADA = "La facultad se registró correctamente.";
	}

	public static final class ProgramaAcademico {

		private ProgramaAcademico() {
		}

		public static final String USUARIO_ERROR_NO_EXISTE = "El programa académico indicado no existe.";
		public static final String USUARIO_ERROR_INACTIVO = "El programa académico indicado está inactivo.";
		public static final String USUARIO_ERROR_NOMBRE_EXISTE = "Ya existe un programa con ese nombre en la facultad.";
		public static final String PROGRAMA_REGISTRADO = "El programa académico se registró correctamente.";
	}

	public static final class Categoria {

		private Categoria() {
		}

		public static final String USUARIO_ERROR_NO_EXISTE = "La categoría indicada no existe.";
		public static final String USUARIO_ERROR_INACTIVA = "La categoría indicada está inactiva y no puede usarse para nuevos Kudos.";
		public static final String USUARIO_ERROR_NOMBRE_EXISTE = "Ya existe una categoría con ese nombre.";
		public static final String USUARIO_ERROR_YA_ACTIVA = "La categoría ya está activa.";
		public static final String USUARIO_ERROR_YA_INACTIVA = "La categoría ya está inactiva.";
		public static final String CATEGORIA_CREADA = "La categoría se creó correctamente.";
		public static final String CATEGORIA_MODIFICADA = "La categoría se modificó correctamente.";
		public static final String CATEGORIA_ACTIVADA = "La categoría se activó correctamente.";
		public static final String CATEGORIA_DESACTIVADA = "La categoría se desactivó correctamente.";
	}

	public static final class Seguridad {

		private Seguridad() {
		}

		public static final String USUARIO_ERROR_SESION_REQUERIDA = "Debe iniciar sesión para realizar esta acción.";
		public static final String USUARIO_ERROR_SESION_INVALIDA = "Su sesión no es válida o expiró. Inicie sesión nuevamente.";
		public static final String USUARIO_ERROR_SIN_PERMISO = "Su rol no tiene permiso para realizar esta acción.";
		public static final String USUARIO_ERROR_CUENTA_NO_ACTIVA = "Su cuenta no está activa.";
		public static final String SESION_INICIADA = "Bienvenido(a) a UCO Kudos.";
		public static final String SESION_CERRADA = "La sesión se cerró correctamente.";
	}

	public static final class Usuario {

		private Usuario() {
		}

		public static final String USUARIO_ERROR_NO_EXISTE = "El usuario indicado no existe.";
		public static final String USUARIO_ERROR_CORREO_NO_INSTITUCIONAL = "El correo debe pertenecer al dominio institucional @%s.";
		public static final String USUARIO_ERROR_CORREO_EXISTE = "Ya existe una cuenta con ese correo institucional.";
		public static final String USUARIO_ERROR_CELULAR_EXISTE = "Ya existe una cuenta con ese número celular.";
		public static final String USUARIO_ERROR_ROL_NO_AUTORREGISTRO = "El rol seleccionado no permite registrarse directamente; lo registra un Administrador.";
		public static final String USUARIO_ERROR_CARGO_OCUPADO = "El cargo solicitado ya tiene un titular activo.";
		public static final String USUARIO_ERROR_AMBITO_PROGRAMA = "Para este rol debe indicar el programa académico.";
		public static final String USUARIO_ERROR_AMBITO_FACULTAD = "Para este rol debe indicar la facultad.";
		public static final String USUARIO_ERROR_AMBITO_NO_APLICA = "Este rol no se asocia a una facultad ni a un programa.";
		public static final String USUARIO_ERROR_CREDENCIALES = "El correo o la contraseña no son correctos.";
		public static final String USUARIO_ERROR_CUENTA_PENDIENTE = "Su cuenta aún no está activa. Pendiente: %s.";
		public static final String USUARIO_ERROR_NO_PUEDE_ACTIVARSE = "La cuenta solo puede activarse con la solicitud aprobada y el correo y el celular verificados.";
		public static final String PENDIENTE_APROBACION = "aprobación de la solicitud";
		public static final String PENDIENTE_CORREO = "verificación del correo";
		public static final String PENDIENTE_CELULAR = "verificación del celular";
		public static final String USUARIO_REGISTRADO = "Registro exitoso. Revise los códigos de verificación enviados a su correo y celular, y espere la aprobación de su solicitud.";
		public static final String INFORMACION_ACTUALIZADA = "Su información se actualizó correctamente.";
		public static final String TECNICO_CODIGO_SIMULADO = "[ENVIO SIMULADO] Código de verificación {} para {}: {}";
	}

	public static final class Verificacion {

		private Verificacion() {
		}

		public static final String USUARIO_ERROR_SIN_PENDIENTE = "No hay una verificación pendiente de ese tipo para el usuario.";
		public static final String USUARIO_ERROR_CODIGO_INCORRECTO = "El código de verificación no es correcto.";
		public static final String USUARIO_ERROR_CODIGO_EXPIRADO = "El código de verificación expiró. Solicite uno nuevo.";
		public static final String DATO_VERIFICADO = "El dato se verificó correctamente.";
		public static final String CODIGO_REENVIADO = "Se envió un nuevo código de verificación.";
	}

	public static final class SolicitudRegistro {

		private SolicitudRegistro() {
		}

		public static final String USUARIO_ERROR_NO_EXISTE = "La solicitud indicada no existe.";
		public static final String USUARIO_ERROR_YA_DECIDIDA = "La solicitud ya fue decidida.";
		public static final String USUARIO_ERROR_NO_PUEDE_DECIDIR = "Usted no es el responsable de decidir esta solicitud.";
		public static final String USUARIO_ERROR_AUTOAPROBACION = "Nadie puede decidir su propia solicitud.";
		public static final String SOLICITUD_APROBADA = "La solicitud se aprobó correctamente.";
		public static final String SOLICITUD_RECHAZADA = "La solicitud se rechazó correctamente.";
		public static final String NOTIFICACION_RESUELTA = "Su solicitud de registro como %s fue %s.";
	}

	public static final class Publicacion {

		private Publicacion() {
		}

		public static final String USUARIO_ERROR_CONTENIDO_OBLIGATORIO = "El contenido de la publicación es obligatorio.";
		public static final String USUARIO_ERROR_TRANSICION_INVALIDA = "La publicación no puede pasar de %s a %s.";
		public static final String USUARIO_ERROR_VETADA = "La publicación está vetada y no admite réplicas, calificaciones ni reportes.";
		public static final String USUARIO_ERROR_NO_EXISTE = "La publicación indicada no existe.";
	}

	public static final class Kudo {

		private Kudo() {
		}

		public static final String USUARIO_ERROR_NO_EXISTE = "El Kudo indicado no existe.";
		public static final String USUARIO_ERROR_MISMO_DESTINATARIO = "No puede darse un Kudo a sí mismo; el reconocimiento es para otra persona.";
		public static final String USUARIO_ERROR_DESTINATARIO_NO_ACTIVO = "La persona a la que quiere reconocer no tiene la cuenta activa.";
		public static final String KUDO_CREADO = "El Kudo se publicó correctamente.";
		public static final String NOTIFICACION_KUDO_RECIBIDO = "%s te dio un Kudo en %s.";
	}

	public static final class Replica {

		private Replica() {
		}

		public static final String USUARIO_ERROR_PADRE_NO_EXISTE = "La publicación que intenta responder no existe.";
		public static final String REPLICA_CREADA = "La réplica se publicó correctamente.";
		public static final String NOTIFICACION_REPLICA_RECIBIDA = "%s respondió tu publicación.";
	}

	public static final class Calificacion {

		private Calificacion() {
		}

		public static final String USUARIO_ERROR_ESTRELLAS = "La calificación debe ser un número entero entre 1 y 5 estrellas.";
		public static final String CALIFICACION_REGISTRADA = "Su calificación se registró correctamente.";
	}

	public static final class Reporte {

		private Reporte() {
		}

		public static final String USUARIO_ERROR_NO_EXISTE = "El reporte indicado no existe.";
		public static final String USUARIO_ERROR_MOTIVO = "El motivo del reporte no es válido. Use LENGUAJE_OFENSIVO, ACOSO, DISCRIMINACION, SPAM, INFORMACION_FALSA u OTRO.";
		public static final String USUARIO_ERROR_DESCRIPCION_OTRO = "Si el motivo es OTRO, describa el problema en al menos 10 caracteres.";
		public static final String USUARIO_ERROR_YA_REPORTADA = "Ya tiene un reporte abierto sobre esta publicación.";
		public static final String USUARIO_ERROR_NO_PENDIENTE = "Solo se puede iniciar la revisión de un reporte PENDIENTE.";
		public static final String USUARIO_ERROR_NO_EN_REVISION = "Solo se puede decidir un reporte que esté EN_REVISION.";
		public static final String USUARIO_ERROR_OBSERVACION = "La decisión necesita una observación de al menos 10 caracteres.";
		public static final String REPORTE_CREADO = "Su reporte se registró y será revisado.";
		public static final String REPORTE_EN_REVISION = "El reporte quedó en revisión.";
		public static final String REPORTE_ACEPTADO = "El reporte se aceptó y la publicación fue vetada.";
		public static final String REPORTE_RECHAZADO = "El reporte se rechazó.";
		public static final String OBSERVACION_CIERRE_POR_VETO = "Cerrado automáticamente porque la publicación fue vetada.";
		public static final String PREFIJO_JUSTIFICACION = "Reporte aceptado: ";
	}

	public static final class Veto {

		private Veto() {
		}

		public static final String USUARIO_ERROR_JUSTIFICACION = "La justificación del veto debe tener al menos 20 caracteres.";
		public static final String USUARIO_ERROR_YA_VETADA = "La publicación ya fue vetada.";
		public static final String PUBLICACION_VETADA = "La publicación fue vetada.";
		public static final String NOTIFICACION_CONTENIDO_VETADO = "Una de tus publicaciones fue vetada. Motivo: %s";
	}

	public static final class Notificacion {

		private Notificacion() {
		}

		public static final String USUARIO_ERROR_NO_EXISTE = "La notificación indicada no existe.";
		public static final String NOTIFICACION_LEIDA = "La notificación se marcó como leída.";
	}
}
