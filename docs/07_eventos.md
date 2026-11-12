# 7. Eventos — UCO Kudos

> Cada evento es algo que **ya ocurrió** en el dominio (se nombra en pasado). Sale de las responsabilidades del modelo enriquecido.

## 7.1 Tipos de evento

| Tipo | Qué es | Ejemplo |
|---|---|---|
| Negocio | Decisión o hecho institucional que le importa a la universidad. | Usuario aprobado, Kudo vetado |
| Dominio | Cambio de estado dentro del modelo que otros objetos pueden necesitar conocer. | Kudo creado, Calificación registrada |
| Integración | Hecho que se comunica a un sistema externo. | Código de verificación enviado |

Todo evento (salvo las consultas) se registra en **RegistroAuditoria** (RN-096 a RN-100, RNF-012). Las reacciones se implementan con el patrón **Observer** (los interesados se suscriben al evento).

## 7.2 Catálogo de eventos

| Evento | Tipo | Objeto | Lo produce | Actor | Referencia | Reacciones |
|---|---|---|---|---|---|---|
| **Categoría creada** | Dominio | Categoria | Crear una categoría | Administrador / Rector / Coordinador Académico | EVT-020; RN-224 | Auditoría registra la operación. |
| **Categoría modificada** | Dominio | Categoria | Modificar una categoría | Administrador / Rector / Coordinador Académico | EVT-021; RN-225 | Auditoría registra la operación. |
| **Categoría activada** | Dominio | Categoria | Activar una categoría | Administrador / Rector / Coordinador Académico | EVT-022; RN-226 | Auditoría registra la operación. |
| **Categoría desactivada** | Dominio | Categoria | Desactivar una categoría | Administrador / Rector / Coordinador Académico | EVT-023; RN-227 | Auditoría registra la operación. |
| **Facultad creada** | Dominio | Facultad | Registrar una facultad | Administrador | EVT-008; RN-218 | Auditoría registra la operación. |
| **Facultad actualizada** | Dominio | Facultad | Actualizar una facultad | Administrador | — | Auditoría registra la operación. |
| **Facultad inactivada** | Dominio | Facultad | Inactivar una facultad | Administrador | — | Auditoría registra la operación. |
| **Parámetro actualizado** | Dominio | ParametroSistema | Actualizar un parámetro | Administrador | — | Auditoría registra la operación. |
| **Roles institucionales cargados** | Dominio | Rol | Cargar roles institucionales | Sistema | — | Auditoría registra la operación. |
| **Programa creado** | Dominio | ProgramaAcademico | Registrar un programa | Administrador | EVT-009; RN-221 | Auditoría registra la operación. |
| **Programa actualizado** | Dominio | ProgramaAcademico | Actualizar un programa | Administrador | — | Auditoría registra la operación. |
| **Programa inactivado** | Dominio | ProgramaAcademico | Inactivar un programa | Administrador | — | Auditoría registra la operación. |
| **Usuario registrado** | Dominio | Usuario | Registrarse | Persona de la comunidad | EVT-001; RN-213 | SolicitudRegistro crea la solicitud; VerificacionContacto genera códigos; Auditoría registra. |
| **Sesión iniciada** | Dominio | Usuario | Iniciar sesión | Persona de la comunidad | — | Auditoría registra la operación. |
| **Información personal actualizada** | Dominio | Usuario | Actualizar mi información | Usuario autenticado | — | Auditoría registra la operación. |
| **Correo institucional actualizado** | Dominio | Usuario | Actualizar mi correo institucional | Usuario autenticado | — | Auditoría registra la operación. |
| **Número celular actualizado** | Dominio | Usuario | Actualizar mi número celular | Usuario autenticado | — | Auditoría registra la operación. |
| **Cuenta activada** | Negocio | Usuario | Activar la cuenta | Sistema | EVT-005; RN-215 | Auditoría registra la operación. |
| **Cuenta suspendida** | Negocio | Usuario | Suspender una cuenta | Gestor según matriz de gestión | EVT-006; RN-216 | Auditoría registra la operación. |
| **Cuenta desactivada** | Negocio | Usuario | Desactivar una cuenta | Gestor según matriz de gestión | EVT-007; RN-217 | Auditoría registra la operación. |
| **Coordinador de facultad asignado** | Negocio | CoordinacionFacultad | Asignar un coordinador | Sistema / Coordinador Académico | EVT-010; RN-219 | Auditoría registra la operación. |
| **Coordinador de facultad reemplazado** | Negocio | CoordinacionFacultad | Reemplazar un coordinador | Coordinador Académico | EVT-011; RN-220 | Auditoría registra la operación. |
| **Coordinador de programa asignado** | Negocio | CoordinacionPrograma | Asignar un coordinador | Sistema / Coordinador de Facultad | EVT-012; RN-222 | Auditoría registra la operación. |
| **Coordinador de programa reemplazado** | Negocio | CoordinacionPrograma | Reemplazar un coordinador | Coordinador de Facultad | EVT-013; RN-223 | Auditoría registra la operación. |
| **Notificación generada** | Dominio | Notificacion | Generar una notificación | Sistema | — | Auditoría registra la operación. |
| **Publicación visible** | Dominio | Publicacion | Hacer visible | Sistema | — | Auditoría registra la operación. |
| **Publicación reportada** | Dominio | Publicacion | Marcar como reportada | Sistema | EVT-026; EVT-030 | Auditoría registra la operación. |
| **Revisión iniciada** | Negocio | Publicacion | Iniciar revisión | Rector / Coordinador Académico | EVT-035 | Auditoría registra la operación. |
| **Contenido liberado tras revisión** | Negocio | Publicacion | Liberar tras revisión | Rector / Coordinador Académico | EVT-037; RN-242 | Auditoría registra la operación. |
| **Contenido vetado** | Negocio | Publicacion | Vetar | Rector / Coordinador Académico | RN-241 | Auditoría registra la operación. |
| **Indicador recalculado** | Dominio | Publicacion | Calcular indicador de calificaciones | Sistema | EVT-034 | Auditoría registra la operación. |
| **Operación auditada** | Dominio | RegistroAuditoria | Registrar una operación | Sistema | — | Auditoría registra la operación. |
| **Solicitud de registro creada** | Dominio | SolicitudRegistro | Crear una solicitud de registro | Sistema | — | Notificación al rol aprobador del ámbito. |
| **Usuario aprobado** | Negocio | SolicitudRegistro | Aprobar una solicitud | Rol aprobador (según matriz de aprobaciones) | EVT-004; RN-214 | Se crea la coordinación o vinculación; Usuario intenta activar la cuenta; Notificación al solicitante; Auditoría. |
| **Solicitud de registro rechazada** | Negocio | SolicitudRegistro | Rechazar una solicitud | Rol aprobador (según matriz de aprobaciones) | — | Notificación al solicitante; Auditoría. |
| **Código de verificación enviado** | Integración | VerificacionContacto | Generar una verificación | Sistema | — | Servicio externo de correo/SMS (simulado). |
| **Correo verificado / Celular verificado** | Dominio | VerificacionContacto | Verificar un código | Persona registrada | EVT-002; EVT-003 | Usuario intenta activar la cuenta. |
| **Código de verificación enviado** | Integración | VerificacionContacto | Reenviar un código | Persona registrada | — | Servicio externo de correo/SMS (simulado). |
| **Estudiante / Docente vinculado a programa** | Dominio | VinculacionPrograma | Vincular a un programa | Sistema / Coordinador de Programa | EVT-016; EVT-018 | Auditoría registra la operación. |
| **Estudiante / Docente desvinculado de programa** | Dominio | VinculacionPrograma | Desvincular de un programa | Coordinador de Programa | EVT-017; EVT-019 | Auditoría registra la operación. |
| **Calificación registrada** | Dominio | Calificacion | Calificar una publicación | Usuario autenticado | EVT-032; RN-236 | Publicación recalcula su indicador (EVT-034); Auditoría. |
| **Calificación actualizada** | Dominio | Calificacion | Cambiar una calificación | Usuario autenticado | EVT-033; RN-237 | Publicación recalcula su indicador (EVT-034); Auditoría. |
| **Kudo creado** | Dominio | Kudo | Publicar un Kudo | Usuario autenticado | EVT-024; RN-228 | Notificación al destinatario; Auditoría. |
| **Kudo reportado / Réplica reportada** | Dominio | Reporte | Reportar una publicación | Usuario autenticado | EVT-026; EVT-030; RN-230; RN-234 | Publicación pasa a REPORTADO (si es el primer reporte); Auditoría. |
| **Revisión iniciada** | Negocio | Reporte | Iniciar la revisión de un reporte | Rector / Coordinador Académico | EVT-035; RN-239 | Auditoría registra la operación. |
| **Revisión cerrada / Contenido rechazado** | Negocio | Reporte | Aceptar un reporte | Rector / Coordinador Académico | EVT-036; EVT-038; RN-240 | Se crea el Veto; Auditoría. |
| **Revisión cerrada / Contenido aprobado** | Negocio | Reporte | Rechazar un reporte | Rector / Coordinador Académico | EVT-036; EVT-037; RN-242 | Publicación vuelve a VISIBLE si no quedan reportes abiertos; Auditoría. |
| **Réplica creada** | Dominio | Replica | Crear una réplica sobre un Kudo | Usuario autenticado | EVT-028; RN-232 | Notificación al autor del Kudo; Auditoría. |
| **Réplica respondida** | Dominio | Replica | Responder una réplica | Usuario autenticado | EVT-029; RN-233 | Notificación al autor de la réplica padre; Auditoría. |
| **Kudo vetado / Réplica vetada** | Negocio | Veto | Vetar una publicación | Rector / Coordinador Académico | EVT-027; EVT-031; RN-231; RN-235; RN-241 | Publicación pasa a VETADO; reportes abiertos se cierran; Notificación al autor; Auditoría. |

## 7.3 Eventos de consulta

Se registran cuando el enunciado lo pide (por ejemplo **Kudo consultado**, RN-229); los demás solo informan.

| Evento | Objeto | Lo produce | Referencia |
|---|---|---|---|
| Categorías consultadas | Categoria | Consultar las categorías | — |
| Categoría consultada | Categoria | Consultar una categoría | — |
| Facultades consultadas | Facultad | Consultar las facultades | — |
| Facultad consultada | Facultad | Consultar una facultad | — |
| Parámetros consultados | ParametroSistema | Consultar los parámetros | — |
| Parámetro consultado | ParametroSistema | Consultar un parámetro | — |
| Roles consultados | Rol | Consultar los roles | — |
| Rol consultado | Rol | Consultar un rol | — |
| Programas consultados | ProgramaAcademico | Consultar los programas | — |
| Programa consultado | ProgramaAcademico | Consultar un programa | — |
| Información personal consultada | Usuario | Consultar mi información | — |
| Usuario consultado | Usuario | Consultar un usuario | — |
| Usuarios consultados | Usuario | Consultar los usuarios | — |
| Coordinador consultado | CoordinacionFacultad | Consultar el coordinador vigente | — |
| Historial de coordinadores consultado | CoordinacionFacultad | Consultar el historial de coordinadores | — |
| Coordinador consultado | CoordinacionPrograma | Consultar el coordinador vigente | — |
| Historial de coordinadores consultado | CoordinacionPrograma | Consultar el historial de coordinadores | — |
| Notificaciones consultadas | Notificacion | Consultar mis notificaciones | — |
| Notificación leída | Notificacion | Marcar como leída | — |
| Auditoría consultada | RegistroAuditoria | Consultar la auditoría | — |
| Historial de actividad consultado | RegistroAuditoria | Consultar mi historial de actividad | — |
| Solicitudes consultadas | SolicitudRegistro | Consultar las solicitudes pendientes | — |
| Solicitud consultada | SolicitudRegistro | Consultar una solicitud | — |
| Verificaciones consultadas | VerificacionContacto | Consultar mis verificaciones | — |
| Vinculaciones consultadas | VinculacionPrograma | Consultar vinculaciones | — |
| Valoraciones consultadas | Calificacion | Consultar valoraciones | — |
| Kudo consultado | Kudo | Consultar un Kudo | EVT-025; RN-229 |
| Kudos consultados | Kudo | Consultar los Kudos | — |
| Reportes consultados | Reporte | Consultar los reportes | — |
| Conversación consultada | Replica | Consultar la conversación | — |
| Histórico de vetos consultado | Veto | Consultar el histórico de vetos | — |
