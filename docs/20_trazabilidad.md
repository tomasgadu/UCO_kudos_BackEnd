# 20. Matriz de trazabilidad — UCO Kudos

Relaciona cada **requisito funcional** con sus **reglas de negocio**, **casos de uso**, **clases**, **componentes** (endpoint del Back y página del Front), **pruebas** y **evidencias** (RNF-034, RNF-035 y sección 4 de `07_Requisitos`).

**Cómo leerla**

| Columna | Dónde se encuentra |
|---|---|
| RN | Enunciado, archivos 03 y 04 (RN-001 a RN-200) |
| CU | [10.1 Casos de uso](10_diagramas_uml.md) |
| Clases | [9. Modelo de dominio](09_modelo_dominio.md) y paquete `negocio` |
| Componente | Endpoint de `/api/v1` y página del Front |
| Pruebas | Clases JUnit de [16. Pruebas](16_pruebas.md) |
| Evidencia | Número de caso en [19. Evidencias](19_evidencias.md) |

**Estado:** ✅ implementado y probado · 🟡 soportado por el modelo y la base de datos, sin endpoint propio · ⏳ pendiente.

---

## 20.1 Usuarios, roles y aprobaciones

| RF | Requisito | RN | CU | Clases | Componente | Pruebas | Evidencia | Estado |
|---|---|---|---|---|---|---|---|---|
| RF-001 | Registro de usuarios | RN-069 a RN-076 | CU-016 | `UsuarioDominio`, `UsuarioNegocioImpl`, `ValidarDatosUsuarioRegla` | `POST /usuarios` · `registro.html` | `ValidadorCamposTest` | Front #46 | ✅ |
| RF-002 | Pedir correo institucional | RN-069, RN-071 | CU-016 | `AsegurarCorreoInstitucionalRegla` | `POST /usuarios` | `UtilTextoTest` (formato) | — | ✅ |
| RF-003 | Pedir número celular | RN-070, RN-072 | CU-016 | `ValidarDatosUsuarioRegla` | `POST /usuarios` | `ValidadorCamposTest` (celular) | — | ✅ |
| RF-004 | Correo único | RN-073 | CU-016 | `AsegurarContactoNoExistaRegla`; restricción `UK_USUARIO_CORREO` | `POST /usuarios` | `03_pruebas_restricciones.sql` | — | ✅ |
| RF-005 | Celular único | RN-074 | CU-016 | `AsegurarContactoNoExistaRegla`; restricción `UK_USUARIO_CELULAR` | `POST /usuarios` | — | — | ✅ |
| RF-006 | Validar correo | RN-077, RN-079 | CU-017 | `VerificacionContactoDominio`, `VerificacionContactoNegocioImpl` | `POST /usuarios/verificaciones` · `registro.html` | `UtilClaveTest` (código) | — | ✅ |
| RF-007 | Validar celular | RN-078, RN-080 | CU-017 | Ídem | Ídem | Ídem | — | ✅ |
| RF-008 | Consultar información personal | RN-101, RN-103 | CU-019 | `UsuarioNegocioImpl` | `GET /usuarios/yo` · `perfil.html` | — | #1 | ✅ |
| RF-009 | Actualizar información personal | RN-102, RN-109 | CU-019 | `UsuarioNegocioImpl`, `UtilTexto.aplicarMayusculaInicial` | `PUT /usuarios/yo` · `perfil.html` | `UtilTextoTest` | Doc 15 (pruebas Bruno) | ✅ |
| RF-010 | Revalidar al cambiar correo | RN-081, RN-110 | CU-019 | `UsuarioDominio.cambiarCorreo` | `PUT /usuarios/yo` | `UsuarioDominioTest` | — | ✅ |
| RF-011 | Revalidar al cambiar celular | RN-082, RN-111 | CU-019 | `UsuarioDominio.cambiarCelular` | `PUT /usuarios/yo` | `UsuarioDominioTest` | — | ✅ |
| RF-012 | Registrar bajo diferentes roles | RN-004 | CU-016 | `RolDominio`, `AsegurarAmbitoCorrespondaRolRegla` | `GET /roles`, `POST /usuarios` | — | — | ✅ |
| RF-013 | Exclusividad de roles | RN-001 a RN-003 | CU-016 | `UsuarioDominio.rol` (un solo rol, por estructura) | — | — | — | ✅ |
| RF-014 | Impedir roles incompatibles | RN-005 a RN-038 | CU-016 | Un solo rol + `AsegurarCargoDisponibleRegla` (cargos únicos) | — | `CodigoRolTest` (cargos únicos) | — | ✅ |
| RF-035 | Aprobar estudiantes | RN-090 | CU-020 | `SolicitudRegistroDominio.decidir`, `AutorizadorSolicitudes` | `POST /solicitudes/{id}/aprobar` · `solicitudes.html` | — | Front #46 | ✅ |
| RF-036 | Aprobar Coord. de Programa | RN-091 | CU-020 | Ídem | Ídem | — | — | ✅ |
| RF-037 | Aprobar Coord. de Facultad | RN-092 | CU-020 | Ídem | Ídem | — | — | ✅ |
| RF-038 | Aprobar Coord. Académicos | RN-093, RN-126 | CU-020 | Ídem | Ídem | — | — | ✅ |
| RF-039 | Registrar Rectores | RN-094, RN-129 | — | `AsegurarRolPermiteRegistroRegla` (impide el autorregistro) | — | — | — | ⏳ Falta el endpoint para que el Administrador lo registre |
| RF-040 | Registrar Administradores | RN-095, RN-129 | — | Ídem | — | — | — | ⏳ Ídem |

## 20.2 Estructura organizacional

| RF | Requisito | RN | CU | Clases | Componente | Pruebas | Evidencia | Estado |
|---|---|---|---|---|---|---|---|---|
| RF-015 | Crear facultades | RN-039, RN-040 | CU-022 | `FacultadDominio`, `FacultadNegocioImpl`, `AsegurarNombreFacultadNoExistaRegla` | `POST /facultades` · `administracion.html` | — | #37, #38 | ✅ |
| RF-016 | Consultar facultades | RN-122 | CU-022 | `FacultadNegocioImpl` | `GET /facultades` | — | — | ✅ |
| RF-017 | Modificar facultades | RN-097 | CU-022 | `FacultadDominio` | — | — | — | ⏳ Falta `PUT /facultades/{id}` |
| RF-018 | Coordinador de una facultad | RN-042 | — | `CoordinacionFacultadDominio` | — | — | — | 🟡 |
| RF-019 | Historial de coordinadores de facultad | RN-043, RN-044 | — | `CoordinacionFacultadDominio` (fechas inicio/fin) | — | — | — | 🟡 |
| RF-020 | Crear programas | RN-046 a RN-049 | CU-022 | `ProgramaAcademicoDominio`, `AsegurarProgramaNoExistaEnFacultadRegla`, `AsegurarFacultadExistaActivaRegla` | `POST /programas` · `administracion.html` | — | — | ✅ |
| RF-021 | Consultar programas | RN-118 | CU-022 | `ProgramaAcademicoNegocioImpl` | `GET /programas` | `UtilTextoTest` (`textoDeEnum`) | Hallazgo 3 | ✅ |
| RF-022 | Modificar programas | RN-097 | CU-022 | `ProgramaAcademicoDominio` | — | — | — | ⏳ Falta `PUT /programas/{id}` |
| RF-023 | Coordinador de un programa | RN-050 | — | `CoordinacionProgramaDominio` | — | — | — | 🟡 |
| RF-024 | Historial de coordinadores de programa | RN-051, RN-052 | — | `CoordinacionProgramaDominio` (fechas) | — | — | — | 🟡 |
| RF-025 | Registrar estudiantes | RN-069 | CU-016 | `UsuarioNegocioImpl` | `POST /usuarios` | — | — | ✅ |
| RF-026 | Estudiante en varios programas | RN-053 | — | `VinculacionProgramaDominio` (N vinculaciones por usuario) | Vinculación al aprobar | — | — | 🟡 Solo se vincula al programa de la solicitud |
| RF-027 | Programas de un estudiante | RN-054 | — | `VinculacionProgramaDominio` | — | — | — | 🟡 |
| RF-028 | Estudiantes de un programa | RN-055 | — | Ídem | — | — | — | 🟡 |
| RF-029 | Trazabilidad estudiante-programa | RN-056, RN-057 | — | `VinculacionProgramaDominio` (fechas, no se borra) | — | — | — | ✅ |
| RF-030 | Registrar docentes | RN-069 | CU-016 | `UsuarioNegocioImpl` | `POST /usuarios` | — | — | ✅ |
| RF-031 | Docente en varios programas | RN-061 | — | `VinculacionProgramaDominio` | Vinculación al aprobar | — | — | 🟡 |
| RF-032 | Programas de un docente | RN-062 | — | Ídem | — | — | — | 🟡 |
| RF-033 | Docentes de un programa | RN-063 | — | Ídem | — | — | — | 🟡 |
| RF-034 | Trazabilidad docente-programa | RN-064, RN-065 | — | Ídem (fechas) | — | — | — | ✅ |
| RF-072 | Programas que estudia un estudiante | RN-054 | — | Ídem | — | — | — | 🟡 |
| RF-073 | Programas en que enseña un docente | RN-062 | — | Ídem | — | — | — | 🟡 |
| RF-074 | Programa de un Coord. de Programa | RN-050 | — | `CoordinacionProgramaDominio` | — | — | — | 🟡 |
| RF-075 | Facultad de un Coord. de Facultad | RN-042 | — | `CoordinacionFacultadDominio` | — | — | — | 🟡 |
| RF-076 | Rector activo | RN-005 | — | `CodigoRol.esCargoUnico` | `GET /usuarios?idRol=` | `CodigoRolTest` | — | ✅ |
| RF-077 | Coordinador Académico activo | RN-011 | — | Ídem | `GET /usuarios?idRol=` | `CodigoRolTest` | — | ✅ |

## 20.3 Categorías

| RF | Requisito | RN | CU | Clases | Componente | Pruebas | Evidencia | Estado |
|---|---|---|---|---|---|---|---|---|
| RF-041 | Crear categorías | RN-134 a RN-140 | CU-021 | `CategoriaDominio`, `ValidarDatosCategoriaRegla`, `AsegurarNombreCategoriaNoExistaRegla` | `POST /categorias` · `administracion.html` | `ReglaTest`, `AsegurarUsuarioTienePermisoReglaTest` | #35, #36 | ✅ |
| RF-042 | Modificar categorías | RN-141 | CU-021 | `CategoriaNegocioImpl` | `PUT /categorias/{id}` | — | Front #49 | ✅ |
| RF-043 | Activar categorías | RN-142 | CU-021 | `CategoriaDominio.activar` | `PATCH /categorias/{id}/activar` | — | Front #49 | ✅ |
| RF-044 | Desactivar categorías | RN-143, RN-144 | CU-021 | `CategoriaDominio.desactivar` | `PATCH /categorias/{id}/desactivar` | — | #8 | ✅ |
| RF-045 | Consultar categorías | RN-132, RN-133 | CU-021 | `CategoriaNegocioImpl` | `GET /categorias` | — | — | ✅ |

## 20.4 Kudos y réplicas

| RF | Requisito | RN | CU | Clases | Componente | Pruebas | Evidencia | Estado |
|---|---|---|---|---|---|---|---|---|
| RF-046 | Crear Kudos | RN-146 a RN-150 | CU-001 | `KudoDominio.crear`, `KudoNegocioImpl`, `SanearContenidoPublicacionRegla` | `POST /kudos` · `kudos.html` | `KudoDominioTest`, `SanearContenidoPublicacionReglaTest` | #5, #7, #9 | ✅ |
| RF-047 | Consultar Kudos | RN-154, RN-155 | CU-002 | `KudoNegocioImpl.consultarKudo` | `GET /kudos/{id}` · `conversacion.html` | — | Front #48 | ✅ |
| RF-048 | Historial de Kudos | RN-151 | CU-003 | `KudoNegocioImpl.consultarMuro` | `GET /kudos` · `kudos.html` | — | Front #47 | ✅ |
| RF-049 | Kudos recibidos por una persona | RN-059, RN-067 | CU-003 | Ídem | `GET /kudos?idDestinatario=` | — | — | ✅ |
| RF-050 | Kudos emitidos por una persona | RN-058, RN-066 | CU-003 | `KudoEntidad.autor` | — | — | — | ⏳ Falta el filtro `?idAutor=` |
| RF-051 | Asociar Kudos a categorías | RN-149, RN-145 | CU-001 | `ObtenerCategoriaActivaRegla` | `POST /kudos` | — | #8 | ✅ |
| RF-052 | Contenido de longitud libre | RN-152 a RN-154 | CU-001 | Columna `TEXT` en `publicacion` | `POST /kudos` | — | — | ✅ |
| RF-053 | Réplicas sobre Kudos | RN-161 | CU-004 | `ReplicaDominio.responder` | `POST /replicas` · `conversacion.html` | `ReplicaDominioTest` | #11 | ✅ |
| RF-054 | Réplicas sobre réplicas | RN-162, RN-166, RN-167 | CU-005 | Ídem (padre = `PublicacionDominio`) | Ídem | `ReplicaDominioTest` | #12 | ✅ |
| RF-055 | Conversación completa | RN-163 | CU-006 | Kudo raíz en cada réplica | `GET /kudos/{id}/replicas` | `ReplicaDominioTest` | #6, Front #48 | ✅ |
| RF-056 | Profundidad ilimitada | RN-164, RN-165 | CU-006 | La réplica referencia a su publicación padre; árbol recursivo en el Front | Ídem | — | Front #48 | ✅ |

## 20.5 Calificaciones, reportes y moderación

| RF | Requisito | RN | CU | Clases | Componente | Pruebas | Evidencia | Estado |
|---|---|---|---|---|---|---|---|---|
| RF-057 | Calificar Kudos | RN-175, RN-182 a RN-184 | CU-007 | `CalificacionDominio`, `CalificacionNegocioImpl` | `PUT /publicaciones/{id}/calificacion` | `CalificacionDominioTest` | #14 | ✅ |
| RF-058 | Calificar réplicas | RN-176 | CU-008 | Ídem (sobre `PublicacionDominio`) | Ídem | Ídem | — | ✅ |
| RF-059 | Escala 1 a 5 | RN-177 | CU-007 | `CalificacionDominio.asegurarEstrellas` | Ídem | `CalificacionDominioTest` | #17 | ✅ |
| RF-060 | Una calificación por usuario | RN-178 a RN-181 | CU-007 | `CalificacionDominio.cambiar`; restricción `UK_CALIFICACION_PUBLICACION_AUTOR` | Ídem (PUT) | `CalificacionDominioTest`, `03_pruebas_restricciones.sql` | #15, #16 | ✅ |
| RF-061 | Reportar Kudos | RN-186, RN-188 a RN-190 | CU-010 | `ReporteDominio.crear`, `ReporteNegocioImpl` | `POST /publicaciones/{id}/reportes` | `ReporteDominioTest` | #19, #20 | ✅ |
| RF-062 | Reportar réplicas | RN-187 | CU-011 | Ídem | Ídem | Ídem | — | ✅ |
| RF-063 | Guardar el motivo | RN-190 | CU-010 | `MotivoReporte` | Ídem | `ReporteDominioTest` | #21, Hallazgo 4 | ✅ |
| RF-064 | Consultar reportes | — | CU-012 | `ReporteNegocioImpl.consultarReportes` | `GET /reportes` · `moderacion.html` | `AsegurarUsuarioTienePermisoReglaTest` | #22, #23 | ✅ |
| RF-065 | Vetar Kudos | RN-191, RN-193, RN-194 | CU-013 | `PublicacionDominio.vetar`, `VetoNegocioImpl` | `POST /publicaciones/{id}/veto`, `POST /reportes/{id}/aceptar` | `PublicacionCicloVidaTest` | #27, #28 | ✅ |
| RF-066 | Vetar réplicas | RN-192, RN-195, RN-196 | CU-014 | Ídem | Ídem | Ídem | — | ✅ |
| RF-067 | Solo autorizados vetan | RN-197 a RN-200 | CU-013 | `CodigoRol` (permiso `VETAR`) | Ídem | `CodigoRolTest`, `AsegurarUsuarioTienePermisoReglaTest` | #31 | ✅ |
| RF-068 | Historial de moderación | RNF-016 | CU-015, CU-025 | `ReporteDominio`, `VetoDominio`, `AuditoriaSuscriptor` | `GET /auditoria?accion=PUBLICACION_VETADA` | — | #42 | ✅ |

## 20.6 Auditoría

| RF | Requisito | RN | CU | Clases | Componente | Pruebas | Evidencia | Estado |
|---|---|---|---|---|---|---|---|---|
| RF-069 | Registrar eventos | RN-096 a RN-100 | CU-024 | `PublicadorEventos`, `AuditoriaSuscriptor` (Observer) | Automático en cada operación | — | #41 | ✅ |
| RF-070 | Consultar auditoría | RN-106 | CU-024 | `AuditoriaNegocioImpl` | `GET /auditoria` · `auditoria.html` | — | #41, #42, Front #50 | ✅ |
| RF-071 | Trazabilidad histórica | RN-100 | CU-024 | `RegistroAuditoriaDominio` (solo se crea) | — | — | #42 | ✅ |

---

## Funcionalidad adicional

No es un RF del enunciado; se agregó por indicación del profesor.

| Funcionalidad | CU | Clases | Componente | Pruebas | Evidencia | Estado |
|---|---|---|---|---|---|---|
| Iniciar sesión con Google | CU-018 | `VerificadorTokenGoogle`, `UsuarioNegocioImpl.iniciarSesionConGoogle`, `UtilTexto.correoPerteneceADominios` | `POST /sesiones/google` · `index.html` | `VerificadorTokenGoogleTest`, `UtilTextoTest` | Front #51, #52 | ✅ |

---

## 20.7 Resumen

| Estado | Cantidad | Requisitos |
|---|---|---|
| ✅ Implementado | 58 | Todos los demás |
| 🟡 Soportado por el modelo, sin endpoint | 14 | RF-018, 019, 023, 024, 026, 027, 028, 031, 032, 033, 072, 073, 074, 075 |
| ⏳ Pendiente | 5 | RF-017, RF-022, RF-039, RF-040, RF-050 |
| **Total** | **77** | |

**Por qué los 🟡 se pueden completar sin cambiar el diseño:** las tablas `coordinacion_facultad`, `coordinacion_programa` y `vinculacion_programa` ya existen con sus fechas de inicio y fin, y tienen sus DAOs. Solo falta exponer consultas en el negocio, la fachada y el controlador, siguiendo el mismo patrón de los demás endpoints. Están priorizados como trabajo inmediato en [17. Conclusiones](17_conclusiones.md).
