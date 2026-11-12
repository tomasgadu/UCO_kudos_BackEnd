# 8. Políticas — UCO Kudos

> Las políticas son las reglas que el sistema garantiza. Hay tres niveles: **institucionales** (del enunciado), **invariantes** (lo que siempre debe ser cierto) y **políticas de objeto** (cómo cada objeto lo garantiza).

En el código, cada política de objeto se implementa como una clase `Regla` (por ejemplo `AsegurarCategoriaActivaRegla`), siguiendo el estilo del curso.

## 8.1 Políticas institucionales

| Política | Qué exige | Cómo se garantiza (políticas de objeto) |
|---|---|---|
| POL-001 | Toda cuenta asociada a un correo institucional válido. | USU-P-0004, USU-P-0005 |
| POL-002 | Toda cuenta asociada a un celular válido. | USU-P-0006, USU-P-0007 |
| POL-003 | Validar la identidad antes de ingresar. | USU-P-0012, VER-P-0001 a VER-P-0006 |
| POL-004 | Cada actor actúa solo en su ámbito. | USU-P-0016, SOL-P-0005, VIN-P-0004 |
| POL-005 | Ninguna autoridad ejerce funciones superiores a su rol. | USU-P-0016, VET-P-0001, RPT-P-0004, CAT-P-0006 |
| POL-006 | Las operaciones críticas deben ser rastreables. | AUD-P-0001 |
| POL-007 | Las decisiones institucionales deben poder reconstruirse. | AUD-P-0001, SOL-P-0008, RPT-P-0007, VET-P-0003 |

## 8.2 Invariantes y política que los protege

| Invariante | Protegido por |
|---|---|
| INV-001 | USU-P-0017 |
| INV-002 | USU-P-0017 |
| INV-003 | CFA-P-0002 |
| INV-004 | CPR-P-0002 |
| INV-005 | SOL-P-0004, USU-P-0013 |
| INV-006 | SOL-P-0004, USU-P-0013 |
| INV-007 | FAC-P-0005, FAC-P-0007, PRO-P-0006, PRO-P-0008 |
| INV-008 | FAC-P-0007, PRO-P-0005 |
| INV-009 | CFA-P-0004, CFA-P-0005, CPR-P-0004, CPR-P-0005, SOL-P-0010 |
| INV-010 | CFA-P-0004, CFA-P-0005, CPR-P-0004, CPR-P-0005, SOL-P-0010 |
| INV-011 | VIN-P-0001 |
| INV-012 | VIN-P-0006 |
| INV-013 | VIN-P-0005 |
| INV-014 | VIN-P-0001 |
| INV-015 | VIN-P-0005 |
| INV-016 | VIN-P-0005 |
| INV-017 | KUD-P-0003 |
| INV-018 | CAT-P-0001, CAT-P-0008 |
| INV-019 | KUD-P-0003 |
| INV-020 | PUB-P-0002 |
| INV-021 | KUD-P-0001 |
| INV-022 | PUB-P-0002 |
| INV-023 | KUD-P-0003 |
| INV-024 | PUB-P-0003 |
| INV-025 | PUB-P-0002 |
| INV-026 | RPL-P-0001 |
| INV-027 | RPL-P-0001 |
| INV-028 | RPL-P-0003 |
| INV-029 | RPL-P-0002, RPL-P-0003 |
| INV-030 | CAL-P-0001 |
| INV-031 | CAL-P-0002 |
| INV-032 | CAL-P-0002 |
| INV-033 | VET-P-0001 |
| INV-034 | VET-P-0005 |
| INV-035 | VET-P-0003 |

## 8.3 Catálogo de políticas de objeto (122)

### Categoria

| Número | Descripción | Responsable | Origen |
|---|---|---|---|
| CAT-P-0001 | Asegurar que la categoría exista con el identificador indicado. | Categoria | INV-018 |
| CAT-P-0002 | Asegurar que los datos requeridos (nombre, descripción) sean válidos en tipo de dato, longitud, obligatoriedad y formato. | Categoria | RN-132 |
| CAT-P-0003 | Asegurar que los datos de tipo texto se sanitícen antes de guardarse. | Categoria | RNF-003 |
| CAT-P-0004 | Asegurar que el identificador generado no esté siendo utilizado por otra categoría. | Categoria | RN-132 |
| CAT-P-0005 | Asegurar que no exista otra categoría con el mismo nombre. | Categoria | RN-132 |
| CAT-P-0006 | Asegurar que solo Administrador, Rector o Coordinador Académico gestionen categorías. | Usuario | RN-134 a RN-143; Anexo 10, sección 8 |
| CAT-P-0007 | Asegurar que el cambio de estado sea válido (activar solo si está inactiva y desactivar solo si está activa). | Categoria | RN-133 |
| CAT-P-0008 | Asegurar que desactivar una categoría no modifique los Kudos ya creados con ella. | Kudo | RN-145; INV-018 |

### Facultad

| Número | Descripción | Responsable | Origen |
|---|---|---|---|
| FAC-P-0001 | Asegurar que la facultad exista con el identificador indicado. | Facultad | RN-039 |
| FAC-P-0002 | Asegurar que los datos requeridos (nombre) sean válidos en tipo de dato, longitud, obligatoriedad y formato. | Facultad | RN-040 |
| FAC-P-0003 | Asegurar que el dato de tipo texto se sanitice antes de guardarse, evitando contenido malicioso. | Facultad | RNF-003 |
| FAC-P-0004 | Asegurar que el identificador generado no esté siendo utilizado por otra facultad. | Facultad | RN-039 |
| FAC-P-0005 | Asegurar que no exista otra facultad con el mismo nombre. | Facultad | RN-039; INV-007 |
| FAC-P-0006 | Asegurar que solo el Administrador gestione facultades. | Usuario | POL-004; Supuesto |
| FAC-P-0007 | Asegurar que no se inactive una facultad con programas académicos activos. | ProgramaAcademico | INV-007; INV-008; RN-045 |

### ParametroSistema

| Número | Descripción | Responsable | Origen |
|---|---|---|---|
| PAR-P-0001 | Asegurar que el parámetro exista con la clave indicada. | ParametroSistema | RN-130 |
| PAR-P-0002 | Asegurar que el nuevo valor sea válido y esté sanitizado según el tipo de parámetro. | ParametroSistema | RN-131; RNF-003 |
| PAR-P-0003 | Asegurar que solo el Administrador gestione los parámetros globales. | Usuario | RN-130; RN-131 |

### Rol

| Número | Descripción | Responsable | Origen |
|---|---|---|---|
| ROL-P-0001 | Asegurar que el rol exista con el identificador indicado. | Rol | RN-004 |
| ROL-P-0002 | Asegurar que cada rol tenga el rol aprobador definido en la matriz de aprobaciones (el Administrador se aprueba a sí mismo). | Rol | RN-090 a RN-095; Anexo 10, sección 5 |

### ProgramaAcademico

| Número | Descripción | Responsable | Origen |
|---|---|---|---|
| PRO-P-0001 | Asegurar que el programa exista con el identificador indicado. | ProgramaAcademico | RN-047 |
| PRO-P-0002 | Asegurar que los datos requeridos (nombre, facultad) sean válidos en tipo de dato, longitud, obligatoriedad y formato. | ProgramaAcademico | RN-048 |
| PRO-P-0003 | Asegurar que los datos de tipo texto se sanitícen antes de guardarse. | ProgramaAcademico | RNF-003 |
| PRO-P-0004 | Asegurar que el identificador generado no esté siendo utilizado por otro programa. | ProgramaAcademico | RN-047 |
| PRO-P-0005 | Asegurar que la facultad indicada exista y esté activa. | Facultad | RN-046; RN-049; INV-008; RN-041 |
| PRO-P-0006 | Asegurar que no exista otro programa con el mismo nombre en la misma facultad. | ProgramaAcademico | INV-007 |
| PRO-P-0007 | Asegurar que solo el Administrador gestione programas académicos. | Usuario | POL-004; Supuesto |
| PRO-P-0008 | Asegurar que no se inactive un programa con estudiantes o docentes vinculados vigentes. | VinculacionPrograma | INV-007 |

### Usuario

| Número | Descripción | Responsable | Origen |
|---|---|---|---|
| USU-P-0001 | Asegurar que el usuario exista con el identificador indicado. | Usuario | RN-101; RN-102; RN-103 |
| USU-P-0002 | Asegurar que los datos requeridos (nombres, apellidos, correo, celular, clave, rol) sean válidos en tipo de dato, longitud, obligatoriedad y formato. | Usuario | RN-069 a RN-072 |
| USU-P-0003 | Asegurar que los datos de tipo texto se sanitícen antes de guardarse. | Usuario | RNF-003 |
| USU-P-0004 | Asegurar que el correo pertenezca al dominio institucional configurado. | ParametroSistema | RN-069; POL-001 |
| USU-P-0005 | Asegurar que no exista otro usuario con el mismo correo institucional. | Usuario | RN-073 |
| USU-P-0006 | Asegurar que no exista otro usuario con el mismo número celular. | Usuario | RN-074 |
| USU-P-0007 | Asegurar que el celular tenga 10 dígitos, inicie en 3 y use el indicativo fijo +57. | Usuario | RN-070; POL-002; Decisión del proyecto |
| USU-P-0008 | Asegurar que el rol indicado exista. | Rol | RN-004 |
| USU-P-0009 | Asegurar que el cambio de estado de la cuenta sea válido (REGISTRADA→ACTIVA, ACTIVA→SUSPENDIDA, SUSPENDIDA→ACTIVA, cualquiera→DESACTIVADA). | Usuario | RN-098; RN-099 |
| USU-P-0010 | Asegurar que la clave cumpla la política de seguridad y se guarde cifrada. | Usuario | RNF-003 |
| USU-P-0011 | Asegurar que las credenciales correspondan a un usuario registrado. | Usuario | RNF-001 |
| USU-P-0012 | Asegurar que solo ingresen cuentas ACTIVAS: aprobadas, con correo verificado y celular verificado. | Usuario | RN-084 a RN-089; POL-003; RN-075; RN-076 |
| USU-P-0013 | Asegurar que ningún usuario modifique directamente su propio rol. | Usuario | RN-107; INV-005; INV-006 |
| USU-P-0014 | Asegurar que al cambiar el correo se marque como no verificado y se genere una nueva verificación. | VerificacionContacto | RN-081; RN-110; RN-104 |
| USU-P-0015 | Asegurar que al cambiar el celular se marque como no verificado y se genere una nueva verificación. | VerificacionContacto | RN-082; RN-111; RN-105 |
| USU-P-0016 | Asegurar que quien ejecuta la acción tenga cuenta activa y el permiso de su rol, dentro de su ámbito (matrices de gestión, moderación y categorías). | Usuario | POL-004; POL-005; RN-112 a RN-131 |
| USU-P-0017 | Asegurar que solo exista un Rector activo y un Coordinador Académico activo. | Usuario | RN-005; RN-011; INV-001; INV-002 |
| USU-P-0018 | Asegurar que todo registro genere su solicitud de registro y sus verificaciones de correo y celular. | SolicitudRegistro | RN-077; RN-078; RN-085 |
| USU-P-0019 | Asegurar que toda modificación de datos y cambio de estado de la cuenta quede auditado. | RegistroAuditoria | RN-083; RN-097 a RN-099; RN-109 |

### CoordinacionFacultad

| Número | Descripción | Responsable | Origen |
|---|---|---|---|
| CFA-P-0001 | Asegurar que un reemplazo cierre la coordinación anterior con fechaFin y no la borre. | CoordinacionFacultad | RN-043; RN-044 |
| CFA-P-0002 | Asegurar que solo exista un coordinador vigente por facultad. | CoordinacionFacultad | RN-017; INV-003; RN-042 |
| CFA-P-0003 | Asegurar que el coordinador tenga el rol COORDINADOR_FACULTAD y cuenta activa. | Usuario | RN-001 |
| CFA-P-0004 | Asegurar que una facultad exista y esté activa. | Facultad | INV-009; INV-010 |
| CFA-P-0005 | Asegurar que un coordinador dirija solo una unidad vigente a la vez. | CoordinacionFacultad | INV-009; INV-010 |
| CFA-P-0006 | Asegurar que asignaciones y reemplazos queden auditados. | RegistroAuditoria | RN-044; RN-052 |
| CFA-P-0007 | Asegurar que solo el Coordinador Académico reemplace coordinadores dentro de su ámbito. | Usuario | RN-117; RN-120; RN-121 |

### CoordinacionPrograma

| Número | Descripción | Responsable | Origen |
|---|---|---|---|
| CPR-P-0001 | Asegurar que un reemplazo cierre la coordinación anterior con fechaFin y no la borre. | CoordinacionPrograma | RN-051; RN-052 |
| CPR-P-0002 | Asegurar que solo exista un coordinador vigente por programaAcademico. | CoordinacionPrograma | RN-023; INV-004; RN-050 |
| CPR-P-0003 | Asegurar que el coordinador tenga el rol COORDINADOR_PROGRAMA y cuenta activa. | Usuario | RN-001 |
| CPR-P-0004 | Asegurar que un programa académico exista y esté activa. | ProgramaAcademico | INV-009; INV-010 |
| CPR-P-0005 | Asegurar que un coordinador dirija solo una unidad vigente a la vez. | CoordinacionPrograma | INV-009; INV-010 |
| CPR-P-0006 | Asegurar que asignaciones y reemplazos queden auditados. | RegistroAuditoria | RN-044; RN-052 |
| CPR-P-0007 | Asegurar que solo el Coordinador de Facultad reemplace coordinadores dentro de su ámbito. | Usuario | RN-117; RN-120; RN-121 |

### Notificacion

| Número | Descripción | Responsable | Origen |
|---|---|---|---|
| NOT-P-0001 | Asegurar que el destinatario exista. | Usuario | Supuesto |
| NOT-P-0002 | Asegurar que un usuario solo consulte y marque sus propias notificaciones. | Notificacion | RNF-004 |

### Publicacion

| Número | Descripción | Responsable | Origen |
|---|---|---|---|
| PUB-P-0001 | Asegurar que la publicación exista con el identificador indicado. | Publicacion | RN-151 |
| PUB-P-0002 | Asegurar que el autor exista y tenga la cuenta activa. | Usuario | RN-147; RN-168; INV-020; INV-025; RN-150; RN-169; INV-022 |
| PUB-P-0003 | Asegurar que el contenido sea obligatorio, se sanitice y se guarde completo, sin límite de longitud ni truncamiento. | Publicacion | RN-152 a RN-154; RN-170; INV-024 |
| PUB-P-0004 | Asegurar que solo ocurran transiciones válidas: CREADO→VISIBLE, VISIBLE→REPORTADO, REPORTADO→EN_REVISION, EN_REVISION→VISIBLE, y cualquier estado no vetado→VETADO. | Publicacion | RN-201 a RN-209 |
| PUB-P-0005 | Asegurar que una publicación vetada no reciba réplicas, calificaciones ni reportes. | Publicacion | RN-205 |
| PUB-P-0006 | Asegurar que la publicación no se edite ni se elimine después de publicada. | Publicacion | RN-151; RN-174; Decisión del proyecto |

### RegistroAuditoria

| Número | Descripción | Responsable | Origen |
|---|---|---|---|
| AUD-P-0001 | Asegurar que toda operación relevante quede registrada y que los registros sean inmutables. | RegistroAuditoria | RN-096 a RN-100; RN-250; POL-006; POL-007 |
| AUD-P-0002 | Asegurar que solo los autorizados consulten la auditoría completa y que cada usuario vea su propio historial. | Usuario | RN-106; RF-070 |

### SolicitudRegistro

| Número | Descripción | Responsable | Origen |
|---|---|---|---|
| SOL-P-0001 | Asegurar que la solicitud exista con el identificador indicado. | SolicitudRegistro | RN-085 |
| SOL-P-0002 | Asegurar que los datos requeridos (solicitante, rol solicitado) sean válidos y existan. | SolicitudRegistro | RF-012 |
| SOL-P-0003 | Asegurar que el ámbito corresponda al rol: programa para Estudiante, Docente y Coord. de Programa; facultad para Coord. de Facultad; ninguno para los demás. | SolicitudRegistro | RN-112 a RN-125 |
| SOL-P-0004 | Asegurar que el usuario no tenga otra solicitud pendiente. | SolicitudRegistro | RN-001; INV-005; INV-006 |
| SOL-P-0005 | Asegurar que solo decida el rol aprobador del rol solicitado y dentro de su ámbito (por ejemplo, el Coord. de Programa solo aprueba estudiantes de su programa). | Rol | RN-090 a RN-095; RN-112; RN-117 |
| SOL-P-0006 | Asegurar que ningún usuario apruebe su propia solicitud. | SolicitudRegistro | RN-108 |
| SOL-P-0007 | Asegurar que solo se decidan solicitudes en estado PENDIENTE. | SolicitudRegistro | RN-084 |
| SOL-P-0008 | Asegurar que todo rechazo tenga una observación. | SolicitudRegistro | POL-007 |
| SOL-P-0009 | Asegurar que no se apruebe un cargo único que ya tiene titular activo (Rector, Coord. Académico, coordinador de esa facultad o programa). | Usuario / Coordinaciones | RN-005; RN-011; RN-017; RN-023 |
| SOL-P-0010 | Asegurar que al aprobar se cree la coordinación o la vinculación correspondiente al rol. | CoordinacionFacultad / CoordinacionPrograma / VinculacionPrograma | INV-009; INV-010; RN-053; RN-061 |
| SOL-P-0011 | Asegurar que toda aprobación o rechazo quede auditado. | RegistroAuditoria | RN-096; RNF-017 |

### VerificacionContacto

| Número | Descripción | Responsable | Origen |
|---|---|---|---|
| VER-P-0001 | Asegurar que exista una verificación PENDIENTE del tipo indicado para el usuario. | VerificacionContacto | RN-077; RN-078 |
| VER-P-0002 | Asegurar que el código ingresado coincida con el generado. | VerificacionContacto | RN-079; RN-080 |
| VER-P-0003 | Asegurar que solo exista una verificación pendiente por usuario y tipo; una nueva anula las anteriores. | VerificacionContacto | RN-110; RN-111 |
| VER-P-0004 | Asegurar que el código expire según el parámetro MINUTOS_EXPIRACION_CODIGO. | ParametroSistema | RNF-003 |
| VER-P-0005 | Asegurar que no se acepte un código expirado. | VerificacionContacto | POL-003 |
| VER-P-0006 | Asegurar que al verificar se actualice el usuario y se evalúe la activación de la cuenta. | Usuario | RN-086; RN-087 |

### VinculacionPrograma

| Número | Descripción | Responsable | Origen |
|---|---|---|---|
| VIN-P-0001 | Asegurar que el usuario vinculado tenga rol ESTUDIANTE o DOCENTE. | Usuario | RN-053; RN-061; RN-054; RN-055; RN-062; RN-063; INV-011; INV-014 |
| VIN-P-0002 | Asegurar que el programa exista y esté activo. | ProgramaAcademico | RN-046 |
| VIN-P-0003 | Asegurar que no exista otro vínculo vigente del mismo usuario con el mismo programa. | VinculacionPrograma | RN-056; RN-064 |
| VIN-P-0004 | Asegurar que el Coordinador de Programa solo gestione vínculos de su programa. | Usuario | RN-112 a RN-115 |
| VIN-P-0005 | Asegurar que la desvinculación conserve el historial (se cierra, no se borra). | VinculacionPrograma | RN-057; RN-065; INV-013; INV-016; INV-015 |
| VIN-P-0006 | Asegurar que vinculaciones y desvinculaciones queden auditadas. | RegistroAuditoria | RN-116; INV-012 |

### Calificacion

| Número | Descripción | Responsable | Origen |
|---|---|---|---|
| CAL-P-0001 | Asegurar que las estrellas sean un entero entre 1 y 5. | Calificacion | RN-177; INV-030; RN-157; RN-171; RN-175; RN-176 |
| CAL-P-0002 | Asegurar que un usuario tenga una sola calificación activa por publicación. | Calificacion | RN-178 a RN-181; INV-031; INV-032; RN-184 |
| CAL-P-0003 | Asegurar que toda calificación o cambio recalcule el indicador y quede auditado. | Publicacion | RN-185; RN-238 |
| CAL-P-0004 | Asegurar que solo se cambie una calificación existente del propio usuario. | Calificacion | RN-183; RN-182 |

### Kudo

| Número | Descripción | Responsable | Origen |
|---|---|---|---|
| KUD-P-0001 | Asegurar que el destinatario exista y tenga la cuenta activa. | Usuario | RN-148; INV-021; RN-058; RN-059; RN-066; RN-067 |
| KUD-P-0002 | Asegurar que el autor y el destinatario sean personas distintas. | Kudo | Enunciado, sección 8 ('hacia otra persona'); Supuesto |
| KUD-P-0003 | Asegurar que la categoría exista y esté activa al crear el Kudo. | Categoria | RN-144; RN-149; INV-017; INV-019; INV-023 |
| KUD-P-0004 | Asegurar que la creación y consulta del Kudo generen eventos auditables y notifiquen al destinatario. | RegistroAuditoria | RN-160; RN-146 |
| KUD-P-0005 | Asegurar que los Kudos vetados no se muestren a la comunidad (solo a Rector y Coord. Académico). | Publicacion | RN-155; RN-205 |

### Reporte

| Número | Descripción | Responsable | Origen |
|---|---|---|---|
| RPT-P-0001 | Asegurar que el motivo sea uno de los permitidos y que la descripción exista si el motivo es OTRO. | Reporte | RN-190; CU-010; RN-060; RN-068; RN-186 a RN-189 |
| RPT-P-0002 | Asegurar que un usuario no tenga dos reportes abiertos sobre la misma publicación. | Reporte | Supuesto |
| RPT-P-0003 | Asegurar que el primer reporte marque la publicación como REPORTADA. | Publicacion | RN-202; RN-207; RN-158; RN-172 |
| RPT-P-0004 | Asegurar que solo el Rector y el Coordinador Académico revisen y decidan reportes. | Usuario | RN-243 a RN-248 |
| RPT-P-0005 | Asegurar que solo se inicie la revisión de reportes PENDIENTES. | Reporte | RN-211; RN-210 |
| RPT-P-0006 | Asegurar que solo se decidan reportes EN_REVISION y que la decisión sea aceptación o rechazo. | Reporte | RN-212 |
| RPT-P-0007 | Asegurar que toda decisión tenga una observación que permita reconstruirla. | Reporte | RN-250; POL-007 |
| RPT-P-0008 | Asegurar que todo reporte y decisión de moderación quede auditado. | RegistroAuditoria | RN-249; RNF-016 |
| RPT-P-0009 | Asegurar que un reporte aceptado produzca el veto de la publicación. | Veto | RN-204; RN-209 |
| RPT-P-0010 | Asegurar que una publicación sin reportes abiertos vuelva a VISIBLE. | Publicacion | RN-242 |

### Replica

| Número | Descripción | Responsable | Origen |
|---|---|---|---|
| RPL-P-0001 | Asegurar que el padre exista y sea un Kudo o una Réplica. | Publicacion | INV-026; INV-027; RN-156; RN-161; RN-162 |
| RPL-P-0002 | Asegurar que el kudoRaiz se derive del padre, para que toda réplica pertenezca a la conversación de un único Kudo. | Replica | INV-029 |
| RPL-P-0003 | Asegurar que no haya límite de profundidad ni de cantidad de réplicas, y que el árbol no tenga ciclos (una réplica solo puede responder a publicaciones que ya existen, nunca a sí misma). | Replica | RN-163 a RN-167; INV-028; INV-029 |
| RPL-P-0004 | Asegurar que la creación de réplicas notifique al autor del padre y quede auditada. | RegistroAuditoria | RN-174; RN-232; RN-233 |

### Veto

| Número | Descripción | Responsable | Origen |
|---|---|---|---|
| VET-P-0001 | Asegurar que solo el Rector o el Coordinador Académico, con cuenta activa, puedan vetar. | Usuario | RN-193 a RN-200; RN-243; INV-033; RN-159; RN-173; RN-191; RN-192 |
| VET-P-0002 | Asegurar que una publicación tenga como máximo un veto. | Veto | Supuesto |
| VET-P-0003 | Asegurar que todo veto tenga una justificación que permita reconstruir la decisión. | Veto | RN-250; INV-035 |
| VET-P-0004 | Asegurar que al vetar se cierren los reportes abiertos de esa publicación. | Reporte | RN-212 |
| VET-P-0005 | Asegurar que todo veto quede auditado y se notifique al autor. | RegistroAuditoria | RN-249; INV-034; RNF-016 |
