# 5. Modelo Anémico — UCO Kudos

> **Propósito (ARQ-050):** entender la **estructura** del dominio: entidades, atributos, relaciones y cardinalidades.
> Aquí **no hay comportamiento**. Las responsabilidades llegan en el modelo enriquecido (sección 6).

---

## 5.1 Decisiones clave del modelo

| # | Decisión | Por qué |
|---|----------|---------|
| D1 | **Estudiante, Docente, Rector y los Coordinadores NO son clases.** Son un **Rol** de `Usuario`. | Un `Usuario` tiene **un solo** `rol` → la exclusividad de roles (RN-001 a RN-038, INV-005, INV-006) se cumple **por estructura**, sin validar 30 combinaciones. Además un objeto no puede "cambiar de clase" si cambia de cargo. |
| D2 | `Rol` se referencia a sí mismo: **"es aprobado por"**. | La matriz de aprobaciones (anexo 10, sección 5) queda como **dato**, no como `if`s. Si cambia la cadena, se cambia un registro (Abierto/Cerrado). |
| D3 | **`Publicacion` es una clase abstracta** con dos hijas: `Kudo` y `Replica`. | Ambas tienen autor, contenido, fecha y estado, y ambas se califican, reportan y vetan. Así `Calificacion`, `Reporte` y `Veto` apuntan a **una sola** cosa (DRY, polimorfismo útil: ARQ-011). |
| D4 | El padre de una `Replica` es una `Publicacion` (Kudo **o** Réplica). Además cada réplica guarda su **Kudo raíz**. | Cumple INV-026/027 sin dos columnas "o esta o aquella". El Kudo raíz permite traer **toda la conversación** de una vez (CU-006). |
| D5 | Las vinculaciones y coordinaciones son **entidades con fechas** (`fechaInicio`, `fechaFin`). | Las reglas piden **historial** (RN-043, RN-051, RN-056, RN-057, RN-064, RN-065). Desvincular = cerrar la fecha, **no borrar**. |
| D6 | **Aprobación ≠ Activación.** La aprobación vive en `SolicitudRegistro`; la activación en `Usuario.estadoCuenta`. | RN-084: "una cuenta aprobada no implica una cuenta activa". Activa = aprobada + correo verificado + celular verificado (RN-085 a RN-089). |
| D7 | Celular con indicativo **fijo +57** y sin tipo/número de identificación. | Decisión del proyecto. El usuario se identifica por su **correo institucional**. |

---

## 5.2 Supuestos (vacíos del enunciado)

1. **Una sola universidad.** No se modela la entidad `Universidad` (la plataforma es solo de la UCO). → KISS.
2. **El Docente lo aprueba el Coordinador de Programa.** La matriz de aprobaciones no lo menciona, pero la de gestión dice que él gestiona docentes.
3. **Autor ≠ destinatario** en un Kudo ("de una persona **hacia otra**", enunciado, sección 8).
4. **La "revisión" es un estado del `Reporte`** (`EN_REVISION`), no una entidad aparte. → KISS.
5. **Los indicadores** (promedio de estrellas, totales) **se calculan**, no se guardan.
6. **Una calificación por usuario y publicación.** Si la cambia, se actualiza la misma; el cambio queda en auditoría.
7. **Un Kudo o réplica tiene como máximo un veto.**
8. Rector y Coordinador Académico **no** necesitan entidad de historial: su unicidad sale del rol + cuenta activa, y su historia queda en auditoría. Facultad y Programa **sí** la necesitan porque las reglas lo piden explícitamente.

---

## 5.3 Cómo quedan los conceptos del anexo 10

| Concepto del enunciado | Cómo se representa |
|---|---|
| Usuario | Entidad `Usuario` |
| Rol | Entidad catálogo `Rol` |
| Administrador, Rector, Coordinador Académico, Coordinador de Facultad, Coordinador de Programa, Docente, Estudiante | Valor de `Usuario.rol` (D1) |
| Facultad | Entidad `Facultad` |
| Programa Académico | Entidad `ProgramaAcademico` |
| Kudo | Entidad `Kudo` (hija de `Publicacion`) |
| Réplica | Entidad `Replica` (hija de `Publicacion`) |
| Categoría | Entidad `Categoria` |
| Calificación | Entidad `Calificacion` |
| Reporte | Entidad `Reporte` |
| Veto | Entidad `Veto` |
| Auditoría | Entidad `RegistroAuditoria` |
| Notificación | Entidad `Notificacion` |
| Solicitud de Registro | Entidad `SolicitudRegistro` |
| *(nuevo)* Verificación de correo/celular | Entidad `VerificacionContacto` |
| *(nuevo)* Vínculo estudiante/docente–programa | Entidad `VinculacionPrograma` |
| *(nuevo)* Historial de coordinadores | Entidades `CoordinacionFacultad` y `CoordinacionPrograma` |
| *(nuevo)* Parámetros globales (RN-130) | Entidad `ParametroSistema` |

---

## 5.4 Entidades y atributos

> Todos los identificadores son **UUID** (como en el molde del curso).
> `texto ilimitado` = sin límite de longitud (RN-152, RN-153, RN-170).

### Organización y usuarios

**Rol**
| Atributo | Tipo | Nota |
|---|---|---|
| id | UUID | |
| codigo | CodigoRol | único |
| nombre | texto | |
| descripcion | texto | |
| rolAprobador | Rol (0..1) | D2. Administrador se aprueba a sí mismo |

**Usuario**
| Atributo | Tipo | Nota |
|---|---|---|
| id | UUID | |
| nombres | texto | obligatorio |
| apellidos | texto | obligatorio |
| correoInstitucional | texto | obligatorio, único (RN-069, RN-071, RN-073) |
| numeroCelular | texto (10 dígitos) | obligatorio, único; se guarda sin el +57 (RN-070, RN-072, RN-074) |
| correoVerificado | booleano | RN-077 |
| celularVerificado | booleano | RN-078 |
| estadoCuenta | EstadoCuenta | |
| rol | Rol (1) | un solo rol (RN-001) |
| fechaRegistro | fecha-hora | |

> Credenciales de acceso: **pendiente** hasta decidir el mecanismo de login.

**SolicitudRegistro**
| Atributo | Tipo | Nota |
|---|---|---|
| id | UUID | |
| solicitante | Usuario (1) | |
| rolSolicitado | Rol (1) | |
| facultad | Facultad (0..1) | ámbito, si el rol es Coordinador de Facultad |
| programaAcademico | ProgramaAcademico (0..1) | ámbito, si el rol es Estudiante, Docente o Coord. de Programa |
| estado | EstadoSolicitud | inicia PENDIENTE |
| fechaSolicitud | fecha-hora | |
| aprobador | Usuario (0..1) | quien decidió; nunca el mismo solicitante (RN-108) |
| fechaDecision | fecha-hora (0..1) | |
| observacion | texto (0..1) | |

**VerificacionContacto**
| Atributo | Tipo | Nota |
|---|---|---|
| id | UUID | |
| usuario | Usuario (1) | |
| tipo | TipoVerificacion | CORREO o CELULAR |
| valorVerificado | texto | el correo o celular que se está validando |
| codigo | texto | código enviado |
| fechaEnvio | fecha-hora | |
| fechaExpiracion | fecha-hora | |
| fechaVerificacion | fecha-hora (0..1) | |
| estado | EstadoVerificacion | si cambia el dato → nueva verificación PENDIENTE (RN-110, RN-111) |

**Facultad**
| Atributo | Tipo | Nota |
|---|---|---|
| id | UUID | RN-039 |
| nombre | texto | obligatorio, único (RN-040) |
| estado | EstadoRegistro | ACTIVA / INACTIVA |
| fechaCreacion | fecha-hora | |

**ProgramaAcademico**
| Atributo | Tipo | Nota |
|---|---|---|
| id | UUID | RN-047 |
| nombre | texto | obligatorio (RN-048) |
| facultad | Facultad (1) | exactamente una (RN-046, RN-049) |
| estado | EstadoRegistro | |
| fechaCreacion | fecha-hora | |

**VinculacionPrograma** (estudiante o docente ↔ programa)
| Atributo | Tipo | Nota |
|---|---|---|
| id | UUID | |
| usuario | Usuario (1) | con rol ESTUDIANTE o DOCENTE |
| programaAcademico | ProgramaAcademico (1) | |
| fechaInicio | fecha-hora | |
| fechaFin | fecha-hora (0..1) | vacía = vínculo vigente |

**CoordinacionFacultad**
| Atributo | Tipo | Nota |
|---|---|---|
| id | UUID | |
| coordinador | Usuario (1) | rol COORDINADOR_FACULTAD |
| facultad | Facultad (1) | |
| fechaInicio | fecha-hora | |
| fechaFin | fecha-hora (0..1) | solo una vigente por facultad (RN-017) |

**CoordinacionPrograma**
| Atributo | Tipo | Nota |
|---|---|---|
| id | UUID | |
| coordinador | Usuario (1) | rol COORDINADOR_PROGRAMA |
| programaAcademico | ProgramaAcademico (1) | |
| fechaInicio | fecha-hora | |
| fechaFin | fecha-hora (0..1) | solo una vigente por programa (RN-023) |

### Reconocimientos y conversación

**Categoria**
| Atributo | Tipo | Nota |
|---|---|---|
| id | UUID | |
| nombre | texto | obligatorio, único |
| descripcion | texto | |
| estado | EstadoRegistro | ACTIVA / INACTIVA (RN-133) |
| fechaCreacion | fecha-hora | |

**Publicacion** *(abstracta)*
| Atributo | Tipo | Nota |
|---|---|---|
| id | UUID | |
| autor | Usuario (1) | RN-147, RN-168 |
| contenido | texto ilimitado | |
| fechaCreacion | fecha-hora | RN-150, RN-169 |
| estado | EstadoPublicacion | inicia CREADO (RN-201, RN-206) |

**Kudo** *(hereda de Publicacion)*
| Atributo | Tipo | Nota |
|---|---|---|
| destinatario | Usuario (1) | distinto del autor (supuesto 3) |
| categoria | Categoria (1) | la que tenía al crearse (RN-145) |

**Replica** *(hereda de Publicacion)*
| Atributo | Tipo | Nota |
|---|---|---|
| padre | Publicacion (1) | Kudo u otra Réplica (INV-026, INV-027) |
| kudoRaiz | Kudo (1) | D4 |

### Interacción y moderación

**Calificacion**
| Atributo | Tipo | Nota |
|---|---|---|
| id | UUID | |
| publicacion | Publicacion (1) | RN-184 |
| autor | Usuario (1) | RN-183 |
| estrellas | entero 1..5 | RN-177 |
| fechaRegistro | fecha-hora | RN-182 |
| fechaModificacion | fecha-hora (0..1) | |
| | | **único** (publicacion, autor) → RN-178 a RN-181 |

**Reporte**
| Atributo | Tipo | Nota |
|---|---|---|
| id | UUID | |
| publicacion | Publicacion (1) | |
| reportante | Usuario (1) | RN-188 |
| motivo | MotivoReporte | RN-190 |
| descripcion | texto (0..1) | obligatoria si el motivo es OTRO |
| fechaReporte | fecha-hora | RN-189 |
| estado | EstadoReporte | inicia PENDIENTE (RN-210) |
| revisor | Usuario (0..1) | Rector o Coord. Académico |
| fechaDecision | fecha-hora (0..1) | |
| observacionDecision | texto (0..1) | |

**Veto**
| Atributo | Tipo | Nota |
|---|---|---|
| id | UUID | |
| publicacion | Publicacion (1) | máximo un veto por publicación |
| autoridad | Usuario (1) | solo Rector o Coord. Académico (INV-033) |
| reporteOrigen | Reporte (0..1) | se puede vetar sin reporte previo |
| justificacion | texto | |
| fechaVeto | fecha-hora | |

### Transversales

**Notificacion**
| Atributo | Tipo | Nota |
|---|---|---|
| id | UUID | |
| destinatario | Usuario (1) | |
| tipo | TipoNotificacion | |
| mensaje | texto | |
| fechaCreacion | fecha-hora | |
| leida | booleano | |

**RegistroAuditoria**
| Atributo | Tipo | Nota |
|---|---|---|
| id | UUID | |
| fecha | fecha-hora | |
| actor | Usuario (0..1) | vacío = acción del sistema |
| accion | texto | nombre del evento (ej. `KUDO_VETADO`) |
| entidadAfectada | texto | ej. `Kudo` |
| idEntidadAfectada | UUID | |
| valorAnterior | texto (0..1) | para reconstruir decisiones (RN-250) |
| valorNuevo | texto (0..1) | |

**ParametroSistema**
| Atributo | Tipo | Nota |
|---|---|---|
| clave | texto | único (ej. `DOMINIO_CORREO_INSTITUCIONAL`) |
| valor | texto | |
| descripcion | texto | |

---

## 5.5 Enumeraciones (valores fijos)

| Enumeración | Valores |
|---|---|
| CodigoRol | ADMINISTRADOR, RECTOR, COORDINADOR_ACADEMICO, COORDINADOR_FACULTAD, COORDINADOR_PROGRAMA, DOCENTE, ESTUDIANTE |
| EstadoCuenta | REGISTRADA, ACTIVA, SUSPENDIDA, DESACTIVADA |
| EstadoSolicitud | PENDIENTE, APROBADA, RECHAZADA |
| TipoVerificacion | CORREO, CELULAR |
| EstadoVerificacion | PENDIENTE, VERIFICADA, EXPIRADA |
| EstadoRegistro | ACTIVA, INACTIVA |
| EstadoPublicacion | CREADO, VISIBLE, REPORTADO, EN_REVISION, VETADO |
| MotivoReporte | LENGUAJE_OFENSIVO, ACOSO, DISCRIMINACION, SPAM, INFORMACION_FALSA, OTRO |
| EstadoReporte | PENDIENTE, EN_REVISION, ACEPTADO, RECHAZADO |
| TipoNotificacion | KUDO_RECIBIDO, REPLICA_RECIBIDA, SOLICITUD_PENDIENTE, SOLICITUD_RESUELTA, CONTENIDO_VETADO |

---

## 5.6 Relaciones y cardinalidades

| Relación | Cardinalidad | Regla |
|---|---|---|
| Usuario — Rol | N : 1 (cada usuario tiene **1** rol) | RN-001 |
| Rol — Rol (aprobador) | N : 0..1 | Anexo 10, sección 5 |
| Usuario — SolicitudRegistro (solicitante) | 1 : 0..N | |
| Usuario — SolicitudRegistro (aprobador) | 1 : 0..N | RN-090 a RN-095 |
| Usuario — VerificacionContacto | 1 : 0..N | RN-077 a RN-082 |
| Facultad — ProgramaAcademico | 1 : 0..N | RN-041, RN-045, RN-046 |
| Usuario (estudiante/docente) — ProgramaAcademico | N : M vía `VinculacionPrograma` | RN-053, RN-061 |
| Facultad — CoordinacionFacultad | 1 : 0..N (solo **1 vigente**) | RN-017, RN-043 |
| ProgramaAcademico — CoordinacionPrograma | 1 : 0..N (solo **1 vigente**) | RN-023, RN-051 |
| Usuario — Kudo (autor) | 1 : 0..N | RN-147 |
| Usuario — Kudo (destinatario) | 1 : 0..N | RN-148 |
| Categoria — Kudo | 1 : 0..N | RN-149 |
| Publicacion — Replica (padre) | 1 : 0..N | RN-161, RN-162, RN-164 |
| Kudo — Replica (raíz) | 1 : 0..N | D4 |
| Publicacion — Calificacion | 1 : 0..N | RN-175, RN-176 |
| Usuario — Calificacion | 1 : 0..N (máx. 1 por publicación) | RN-178 a RN-181 |
| Publicacion — Reporte | 1 : 0..N | RN-158, RN-172 |
| Publicacion — Veto | 1 : 0..1 | supuesto 7 |
| Reporte — Veto | 1 : 0..1 | |
| Usuario — Notificacion | 1 : 0..N | |
| Usuario — RegistroAuditoria (actor) | 0..1 : 0..N | RN-096 a RN-100 |

### Cadena de aprobación (datos de `Rol.rolAprobador`)

| Rol | Lo aprueba |
|---|---|
| ESTUDIANTE | COORDINADOR_PROGRAMA |
| DOCENTE | COORDINADOR_PROGRAMA *(supuesto 2)* |
| COORDINADOR_PROGRAMA | COORDINADOR_FACULTAD |
| COORDINADOR_FACULTAD | COORDINADOR_ACADEMICO |
| COORDINADOR_ACADEMICO | RECTOR |
| RECTOR | ADMINISTRADOR |
| ADMINISTRADOR | ADMINISTRADOR |

---

## 5.7 Diagrama del modelo anémico

![Modelo anémico](diagramas/modelo_anemico.png)

- Diagrama editable: [`diagramas/modelo_anemico.drawio`](diagramas/modelo_anemico.drawio).
- Muestra entidades, relaciones con verbos, cardinalidades y línea de vida. Los atributos están en la sección 5.4.
- El **diagrama de clases UML** (con atributos y métodos) se construye desde el modelo enriquecido, en la sección 10.
- El número en círculo es la **línea de vida**: una entidad solo depende de entidades de número menor. Sirve como **orden de creación** de tablas y datos de muestreo.

| Línea de vida | Entidades |
|---|---|
| 1 | Rol, Facultad, Categoria, ParametroSistema |
| 2 | Usuario, ProgramaAcademico |
| 3 | SolicitudRegistro, VerificacionContacto, CoordinacionFacultad, CoordinacionPrograma, VinculacionPrograma, Publicacion, Notificacion, RegistroAuditoria |
| 4 | Kudo, Calificacion, Reporte |
| 5 | Replica, Veto |

---

## 5.8 Lo que queda para el modelo enriquecido

1. **Quién puede hacer qué** (vetar, gestionar categorías, aprobar): reglas de permisos por rol.
2. **Transiciones de estado** de Kudo, Réplica, Reporte y Cuenta (patrón **State** candidato).
3. **Validaciones**: formato de correo y celular, escala de estrellas, ausencia de ciclos en réplicas.
4. **Eventos** que dispara cada operación y su registro en auditoría.
5. **Objetos de valor** candidatos: `CorreoInstitucional`, `NumeroCelular`, `Estrellas`, `Contenido`.

---

## 5.9 Justificación del autor (retroalimentación)

> Respuestas propias a las preguntas de análisis, revisadas y complementadas.

**1. ¿Qué es el modelo anémico y para qué sirve?**
Es la representación del problema en términos de negocio: solo **estructura** (entidades, atributos, relaciones y cardinalidades), **sin comportamiento**. Sirve como base para el modelo enriquecido, donde se reparten las responsabilidades, y para tener una estructura clara antes de programar (ARQ-050).

**2. ¿Por qué Estudiante, Docente, Rector y Coordinadores no son clases?**
Porque se modelan como el **rol** del Usuario y no como herencia. RN-001 dice que una persona solo puede tener **un rol activo**, y RN-002 que los roles son **mutuamente excluyentes**. Con un único atributo `rol` es imposible tener dos roles a la vez, y si una persona cambia de cargo solo cambia ese dato. Lo que diferencia a cada rol es **lo que puede hacer** (aprobar, vetar, gestionar), y eso se resuelve en el modelo enriquecido.

**3. ¿Qué significa el número en círculo (línea de vida)?**
Indica el **orden en que las entidades pueden existir**: una entidad depende de las de número menor. Su número es el mayor de sus dependencias más 1. Ejemplos: Rol (1) → Usuario (2), porque todo usuario necesita un rol; Facultad (1) → Programa Académico (2), porque no hay programa sin facultad; Usuario (2) → Publicación (3) → Kudo (4) → Réplica (5). Este orden se usa luego para crear las tablas e insertar los datos de muestreo.

**4. ¿Por qué Kudo y Réplica heredan de Publicación?**
Porque **son** publicaciones dentro del sistema, que funciona como un foro donde interactúan los distintos roles. Se aplica la diferencia entre **"es un"** (herencia: un Kudo *es una* publicación) y **"tiene un"** (asociación: un Kudo *tiene una* categoría). Lo que se gana: autor, contenido, fecha y estado se definen una sola vez; Calificación, Reporte y Veto apuntan a una sola entidad; y una Réplica puede responder a un Kudo o a otra Réplica (INV-027). Sin herencia el modelo crecería con relaciones duplicadas (por ejemplo, CalificacionKudo y CalificacionReplica), como pasó en InduMatch con los tipos de cliente.

**5. ¿Por qué Vinculación y Coordinaciones son entidades con fechas?**
Porque reflejan cómo se organiza realmente una universidad y porque las reglas exigen **historial**: RN-043 y RN-051 (historial de coordinadores), RN-057 y RN-065 (desvincular conserva la historia). Una línea simple entre Usuario y Programa no puede guardar fechas. Desvincular significa registrar `fechaFin`, no borrar, y "un solo coordinador vigente" (RN-017, RN-023) significa uno solo con `fechaFin` vacía. Además, un estudiante o docente puede estar en varios programas (relación N:M), lo que requiere una entidad intermedia.
