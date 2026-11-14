# 9. Modelo de dominio — UCO Kudos

> El modelo de dominio es la versión **implementada** del modelo enriquecido (sección 6): las clases del paquete `co.edu.uco.kudos.dominio`, con sus datos, su comportamiento y sus reglas. El diagrama de clases está en la [sección 10.2](10_diagramas_uml.md).

## 9.1 Cómo están construidos los objetos de dominio

| Característica | Cómo se aplica | Por qué |
|---|---|---|
| **Inmutables** | Todos los atributos son `final`; no hay *setters*. | Un objeto no puede quedar en un estado inválido a mitad de un cambio. |
| **Builder** | Cada clase tiene `new XxxDominio.Builder()...build()`. | Hay clases con 8 o 10 atributos; el Builder evita constructores enormes y deja claro qué se asigna. |
| **`copia()`** | Devuelve un Builder con los valores actuales. | Para "cambiar" algo se crea un objeto nuevo: `kudo.copia().estado(VISIBLE).build()`. |
| **Valores por defecto** | Nulos → UUID por defecto, texto vacío, `NO_DEFINIDO`. | Nunca hay `NullPointerException` dentro del dominio. |
| **Comportamiento propio** | Las reglas que dependen solo del objeto viven en él (`vetar()`, `activar()`, `decidir()`). | Modelo **enriquecido**, no anémico: el objeto protege sus invariantes. |
| **Excepciones de dominio** | Si una regla se rompe se lanza `KudosDominioExcepcion` con un mensaje del `CatalogoMensajes`. | El controlador la convierte en un 400 con un mensaje claro. |

## 9.2 Clases del dominio

### Estructura organizacional

| Clase | Responsabilidad | Comportamiento clave |
|---|---|---|
| `FacultadDominio` | Unidad académica que agrupa programas. | `estaActiva()` |
| `ProgramaAcademicoDominio` | Programa dentro de una facultad. | `estaActivo()` |
| `CoordinacionFacultadDominio` | Quién coordina una facultad y desde cuándo. | Historial con `fechaInicio` / `fechaFin` |
| `CoordinacionProgramaDominio` | Quién coordina un programa y desde cuándo. | Historial con fechas |
| `VinculacionProgramaDominio` | Estudiante o docente vinculado a un programa. | Historial con fechas |

### Usuarios y acceso

| Clase | Responsabilidad | Comportamiento clave |
|---|---|---|
| `RolDominio` | Uno de los 7 roles institucionales. | `apruebaA(rolSolicitado)`: matriz de aprobaciones como dato |
| `UsuarioDominio` | Persona registrada; se identifica por su correo institucional. | `activar()`, `cambiarCorreo()`, `cambiarCelular()`, `estaActivo()` |
| `SolicitudRegistroDominio` | Petición de registro que debe aprobar el rol superior. | `decidir(aprobar, decisor, observacion)` |
| `VerificacionContactoDominio` | Código de 6 dígitos para verificar correo o celular. | `estaExpirada()`, `codigoCoincide()` |

### Reconocimientos y conversación

| Clase | Responsabilidad | Comportamiento clave |
|---|---|---|
| `PublicacionDominio` *(abstracta)* | Lo común a Kudo y Réplica: autor, contenido, fecha, estado. | Ciclo de vida: `marcarReportada()`, `iniciarRevision()`, `restaurar()`, `vetar()` |
| `KudoDominio` | Reconocimiento de una persona hacia otra, con categoría. | `crear()` impide el Kudo a sí mismo; `publicar()` |
| `ReplicaDominio` | Respuesta a un Kudo o a otra réplica. | `responder()` guarda el padre y el **Kudo raíz** y no permite responder lo vetado |
| `CategoriaDominio` | Clasificación de los Kudos. | `activar()`, `desactivar()`, `estaActiva()` |
| `CalificacionDominio` | De 1 a 5 estrellas, una por usuario y publicación. | `crear()`, `cambiar()` validan el rango |
| `ResumenCalificacion` *(record)* | Promedio, cantidad y mi calificación. | **Objeto de valor**: se calcula, no se guarda |

### Moderación, auditoría y avisos

| Clase | Responsabilidad | Comportamiento clave |
|---|---|---|
| `ReporteDominio` | Solicitud de revisión de una publicación. | `crear()`, `iniciarRevision()`, `decidir()`, `cerrarPorVeto()` |
| `VetoDominio` | Decisión institucional que oculta una publicación. | Máximo uno por publicación |
| `NotificacionDominio` | Aviso para un usuario. | `isLeida()` |
| `RegistroAuditoriaDominio` | Qué pasó, quién, cuándo, valor anterior y nuevo. | Solo se crea; nunca se modifica |
| `ParametroSistemaDominio` | Valores configurables (por ejemplo, minutos de vigencia del código). | — |

## 9.3 Enumeraciones con comportamiento

| Enum | Valores | Qué aporta |
|---|---|---|
| `CodigoRol` | ADMINISTRADOR, RECTOR, COORDINADOR_ACADEMICO, COORDINADOR_FACULTAD, COORDINADOR_PROGRAMA, DOCENTE, ESTUDIANTE | Cada rol sabe su **ámbito** (`getAmbito()`), sus **permisos** (`tienePermiso()`) y si es cargo único (`esCargoUnico()`). |
| `Permiso` | GESTIONAR_ESTRUCTURA, GESTIONAR_CATEGORIAS, GESTIONAR_PARAMETROS, CONSULTAR_AUDITORIA, VETAR, REVISAR_REPORTES | Lo que se pide en cada operación protegida. |
| `EstadoPublicacion` | CREADO, VISIBLE, REPORTADO, EN_REVISION, VETADO | Ciclo de vida de Kudos y réplicas. |
| `EstadoReporte` | PENDIENTE, EN_REVISION, ACEPTADO, RECHAZADO | Ciclo de vida del reporte. |
| `EstadoCuenta` | REGISTRADA, ACTIVA, SUSPENDIDA, DESACTIVADA | Estado de la cuenta del usuario. |
| `EstadoSolicitud` | PENDIENTE, APROBADA, RECHAZADA | Estado de la solicitud de registro. |
| `MotivoReporte` | LENGUAJE_OFENSIVO, ACOSO, DISCRIMINACION, SPAM, INFORMACION_FALSA, OTRO | OTRO exige descripción. |

Todos tienen `NO_DEFINIDO` y `desdeTexto()`, que convierte un texto desconocido en `NO_DEFINIDO` en vez de lanzar error.

## 9.4 Servicios de dominio

Algunas reglas involucran a **varios objetos** o necesitan datos de la base de datos; por eso no caben en una sola clase de dominio.

| Servicio | Qué hace |
|---|---|
| `GestorPublicaciones` | Obtiene una publicación sin saber si es Kudo o Réplica (polimorfismo) y guarda su nuevo estado en la tabla que corresponde. |
| `AutorizadorSolicitudes` | Decide si un usuario puede aprobar una solicitud: su rol debe ser el aprobador y, si aplica, debe coordinar esa facultad o ese programa. |
| Reglas (`negocio.negocio.reglas`) | Validaciones reutilizables que consultan la base de datos, por ejemplo "el nombre de la categoría no existe" o "el correo no está registrado". |

## 9.5 Invariantes principales

| Invariante | Dónde se protege |
|---|---|
| Un usuario tiene un solo rol | Estructura: `UsuarioDominio.rol` es uno solo |
| Nadie se da un Kudo a sí mismo | `KudoDominio.crear()` |
| No se responde ni se reporta lo vetado | `PublicacionDominio.asegurarNoVetada()` |
| Una réplica siempre conoce su Kudo raíz | `ReplicaDominio.responder()` |
| Calificación de 1 a 5 | `CalificacionDominio.asegurarEstrellas()` |
| Una publicación se veta una sola vez | `PublicacionDominio.vetar()` |
| Solo se decide un reporte EN_REVISION, con observación | `ReporteDominio.decidir()` |
| Cuenta activa = solicitud aprobada + correo y celular verificados | `UsuarioDominio.activar()` |
| Cambiar correo o celular exige verificarlo de nuevo | `UsuarioDominio.cambiarCorreo()` / `cambiarCelular()` |

Cada invariante tiene al menos una prueba unitaria (sección [16. Pruebas](16_pruebas.md)).
