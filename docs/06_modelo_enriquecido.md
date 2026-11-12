# 6. Modelo Enriquecido — UCO Kudos

> Evolución del modelo anémico (sección 5): a cada objeto se le asignan **responsabilidades**, la **información** que necesita, sus **colaboraciones** (otros objetos y políticas) y las **reglas** que protege.
> Formato basado en el modelo de dominio enriquecido del curso. Versión completa con las 38 columnas de caracterización por atributo: [`anexos/ModeloDominioEnriquecido_Kudos.xlsx`](anexos/ModeloDominioEnriquecido_Kudos.xlsx).

## 6.1 Cómo leer cada objeto

1. **Descripción y línea de vida:** qué representa y en qué orden puede existir.
2. **Responsabilidades (event storming):** quién (actor) hace qué (acción), qué políticas se validan y qué evento se produce.
3. **Atributos:** tipo y validaciones de cada dato.
4. **Políticas:** reglas que el objeto garantiza, con el objeto responsable y la regla del enunciado de donde salen.
5. **Muestreo:** datos de ejemplo coherentes entre todos los objetos.

## 6.2 Decisiones tomadas en esta fase

| # | Decisión | Por qué |
|---|---|---|
| E1 | Kudos y Réplicas **no se editan ni se eliminan**; solo se vetan. | Trazabilidad e historial completo (RN-151, RN-154, RN-174). |
| E2 | Login con **correo + contraseña cifrada** (`claveHash`). La implementación técnica se hace en el Back-End. | Lo más simple que cumple RNF-001 y RNF-003. |
| E3 | La **verificación** de correo y celular es **simulada** (el código se registra en el log). | Permite cumplir RN-077/078 sin depender de un servicio pago; luego se integra uno real. |
| E4 | Un Kudo pasa a **VISIBLE** apenas se crea. | CU-001, paso 7. |
| E5 | Los **reportes** los revisan Rector y Coord. Académico: aceptado → veto; rechazado → la publicación vuelve a VISIBLE. | RN-211, RN-212, RN-242, RN-243. |
| E6 | Facultad, Programa y Categoría **no se eliminan, se inactivan**. | Conservan historia (INV-018, RN-145). |
| E7 | Facultades y programas los gestiona el **Administrador**. | El enunciado no lo define; es administración técnica de la estructura (supuesto). |
| E8 | Cada política indica su **origen** en el enunciado (RN, INV, POL) o si es un supuesto. | Trazabilidad entre requisitos y diseño (RNF-035). |

## 6.3 Objetos de dominio

| Objeto | Descripción | Línea de vida |
|---|---|---|
| [Categoria](#categoria) | Objeto de dominio que representa una clasificación institucional de reconocimiento, utilizada para agrupar los Kudos según el tipo de comportamiento o logro que reconocen. | 1 |
| [Facultad](#facultad) | Objeto de dominio que representa una unidad académica de la universidad que agrupa programas académicos y es dirigida por un coordinador de facultad. | 1 |
| [ParametroSistema](#parametrosistema) | Objeto de dominio que representa un parámetro global de configuración de la plataforma que el Administrador puede modificar sin cambiar el código. | 1 |
| [Rol](#rol) | Objeto de dominio que representa el rol institucional que cumple una persona dentro de UCO Kudos, el cual determina qué acciones puede realizar y qué rol es responsable de aprobar su registro. | 1 |
| [ProgramaAcademico](#programaacademico) | Objeto de dominio que representa un programa académico ofrecido por la universidad, que pertenece a exactamente una facultad y es dirigido por un coordinador de programa. | 2 |
| [Usuario](#usuario) | Objeto de dominio que representa a una persona de la comunidad universitaria registrada en UCO Kudos, identificada por su correo institucional, que cumple un único rol institucional. | 2 |
| [CoordinacionFacultad](#coordinacionfacultad) | Objeto de dominio que representa el periodo durante el cual un usuario con rol COORDINADOR_FACULTAD dirige una facultad, permitiendo conocer el coordinador vigente y su historial. | 3 |
| [CoordinacionPrograma](#coordinacionprograma) | Objeto de dominio que representa el periodo durante el cual un usuario con rol COORDINADOR_PROGRAMA dirige un programa académico, permitiendo conocer el coordinador vigente y su historial. | 3 |
| [Notificacion](#notificacion) | Objeto de dominio que representa un aviso que la plataforma envía a un usuario cuando ocurre algo que le interesa, como recibir un Kudo o tener una solicitud por aprobar. | 3 |
| [Publicacion](#publicacion) *(abstracto)* | Objeto de dominio abstracto que representa cualquier contenido publicado por un usuario en la conversación institucional (un Kudo o una Réplica). Concentra lo común: autor, contenido, fecha, estado y su ciclo de vida. | 3 |
| [RegistroAuditoria](#registroauditoria) | Objeto de dominio que representa el registro histórico e inmutable de una operación relevante realizada en la plataforma, que permite reconstruir quién hizo qué, cuándo y sobre qué. | 3 |
| [SolicitudRegistro](#solicitudregistro) | Objeto de dominio que representa la petición de una persona para ejercer un rol institucional, la cual debe ser aprobada o rechazada por el rol aprobador correspondiente dentro de su ámbito. | 3 |
| [VerificacionContacto](#verificacioncontacto) | Objeto de dominio que representa el proceso de validación de un correo institucional o de un número celular de un usuario mediante un código temporal. | 3 |
| [VinculacionPrograma](#vinculacionprograma) | Objeto de dominio que representa el periodo durante el cual un estudiante o docente pertenece a un programa académico, conservando el historial cuando se desvincula. | 3 |
| [Calificacion](#calificacion) | Objeto de dominio que representa la valoración de 1 a 5 estrellas que un usuario asigna a una publicación (Kudo o Réplica). Un usuario tiene una sola calificación activa por publicación. | 4 |
| [Kudo](#kudo) | Objeto de dominio que representa un reconocimiento formal que una persona de la comunidad otorga a otra, clasificado en una categoría. Es una Publicación y es la raíz de una conversación. | 4 |
| [Reporte](#reporte) | Objeto de dominio que representa la solicitud de revisión que un usuario hace sobre una publicación que considera inapropiada, la cual es analizada y decidida por el Rector o el Coordinador Académico. | 4 |
| [Replica](#replica) | Objeto de dominio que representa una intervención en la conversación de un Kudo, que responde a un Kudo o a otra Réplica. Es una Publicación y forma un árbol de conversación sin límite de profundidad. | 5 |
| [Veto](#veto) | Objeto de dominio que representa la decisión institucional del Rector o del Coordinador Académico de inhabilitar una publicación por incumplir las políticas, conservando la evidencia de la decisión. | 5 |

## 6.4 Supuestos y preguntas abiertas (hotspots)

> Preguntas que el proyecto se fue haciendo a medida que avanzaba, y cómo se resolvió cada una. En el event storming aparecen en la columna *Hotspot / Aspectos por solucionar* con el mismo código.

| # | Fase | Objeto | Pregunta / vacío detectado | Decisión | Estado |
|---|---|---|---|---|---|
| H-01 | Análisis | Enunciado | ¿Dónde está el archivo 02 del enunciado? | Su contenido está dentro del archivo 01 (respuesta del profesor). | Resuelto |
| H-02 | Modelo anémico | Usuario / Rol | ¿Estudiante, Docente, Rector y Coordinadores son clases (herencia) o un dato del Usuario? | Son el atributo `rol` del Usuario: un solo rol activo (RN-001, RN-002). | Decidido |
| H-03 | Modelo anémico | Publicacion | ¿Cómo modelar que una réplica responda a un Kudo o a otra réplica? | Herencia: Kudo y Réplica son Publicación; el padre de una réplica es una Publicación (INV-027). | Decidido |
| H-04 | Modelo anémico | Rol | La matriz de aprobaciones no dice quién aprueba a los Docentes. | Los aprueba el Coordinador de Programa, porque la matriz de gestión dice que él gestiona docentes. | Decidido (confirmar con el profesor) |
| H-05 | Modelo enriquecido | Rol | ¿Los roles se crean o eliminan desde la plataforma? | No: es un catálogo fijo de 7 roles cargado al instalar, porque los define la institución (RN-001 a RN-038). | Decidido |
| H-06 | Modelo enriquecido | Facultad / ProgramaAcademico | El enunciado no dice quién crea facultades y programas. | Los gestiona el Administrador (administración técnica de la estructura). | Decidido (confirmar con el profesor) |
| H-07 | Modelo enriquecido | Categoria | RN-134 dice 'solo los Administradores crean categorías', pero RN-135 y RN-136 permiten al Rector y al Coord. Académico. | Se toma la matriz del Anexo 10, sección 8: Administrador, Rector y Coordinador Académico. | Decidido |
| H-08 | Modelo enriquecido | Usuario | ¿El Rector y el Administrador se auto-registran? | No: los registra un Administrador (RN-094, RN-095). | Decidido |
| H-09 | Modelo enriquecido | Usuario | ¿Cuál es el mecanismo de inicio de sesión? | Correo + contraseña cifrada (`claveHash`); la implementación técnica (Spring Security / JWT) se hace en el Back-End. | Pendiente de implementación |
| H-10 | Modelo enriquecido | VerificacionContacto | ¿Se envían códigos reales por correo y SMS? | Por ahora el envío es simulado (el código queda en el log); luego se puede integrar un servicio real. | Decidido (mejora futura) |
| H-11 | Modelo enriquecido | RegistroAuditoria | El enunciado no dice quién consulta la auditoría completa. | Administrador, Rector y Coordinador Académico; cada usuario ve su propio historial (RN-106). | Decidido (confirmar con el profesor) |
| H-12 | Modelo enriquecido | Kudo | ¿Una persona puede darse un Kudo a sí misma? | No: el Kudo es 'de una persona hacia otra' (archivo 01, sección 8). | Decidido |
| H-13 | Modelo enriquecido | Kudo / Replica | ¿Un Kudo o una Réplica se pueden editar o eliminar? | No: solo se vetan, para conservar la trazabilidad (RN-151, RN-154, RN-174). | Decidido |
| H-14 | Modelo enriquecido | Reporte | ¿Un usuario puede reportar varias veces la misma publicación? | No mientras tenga un reporte abierto sobre ella. | Decidido |
| H-15 | Modelo enriquecido | Veto | ¿Una publicación puede tener varios vetos o revertirse? | Máximo un veto y no se revierte; la liberación solo ocurre cuando se rechaza un reporte antes del veto. | Decidido |
| H-16 | Modelo enriquecido | Usuario | ¿Qué formato tiene el celular? | 10 dígitos que inician en 3, con indicativo fijo +57 (sin tabla de indicativos). | Decidido |

---

## Categoria

**Línea de vida:** 1

Objeto de dominio que representa una clasificación institucional de reconocimiento, utilizada para agrupar los Kudos según el tipo de comportamiento o logro que reconocen.

### Responsabilidades

| Responsabilidad | Actor | Descripción | Entradas | Salida | Políticas | Evento |
|---|---|---|---|---|---|---|
| **Consultar las categorías** | Usuario autenticado | Permite listar las categorías; al crear un Kudo solo se muestran las activas. | filtro | Lista<Categoria> | — | Categorías consultadas |
| **Consultar una categoría** | Usuario autenticado | Permite obtener una categoría a partir de su identificador. | id | Categoria | CAT-P-0001 | Categoría consultada |
| **Crear una categoría** | Administrador / Rector / Coordinador Académico | Permite registrar una nueva categoría de reconocimiento. | datos {nombre, descripcion} | Categoria | CAT-P-0006, CAT-P-0002, CAT-P-0003, CAT-P-0004, CAT-P-0005 | Categoría creada (EVT-020; RN-224) |
| **Modificar una categoría** | Administrador / Rector / Coordinador Académico | Permite cambiar nombre o descripción de una categoría. | id; datos {nombre, descripcion} | Categoria | CAT-P-0006, CAT-P-0001, CAT-P-0002, CAT-P-0003, CAT-P-0005 | Categoría modificada (EVT-021; RN-225) |
| **Activar una categoría** | Administrador / Rector / Coordinador Académico | Permite volver a usar una categoría inactiva para nuevos Kudos. | id | Categoria | CAT-P-0006, CAT-P-0001, CAT-P-0007 | Categoría activada (EVT-022; RN-226) |
| **Desactivar una categoría** | Administrador / Rector / Coordinador Académico | Permite impedir que una categoría se use en nuevos Kudos, sin afectar los Kudos históricos. | id | Categoria | CAT-P-0006, CAT-P-0001, CAT-P-0007, CAT-P-0008 | Categoría desactivada (EVT-023; RN-227) |

**Hotspots (ver 6.4):**

- *Crear una categoría:* H-07: RN-134 dice 'solo los Administradores crean categorías', pero RN-135 y RN-136 permiten al Rector y al Coord. Académico. → Se toma la matriz del Anexo 10, sección 8: Administrador, Rector y Coordinador Académico.

### Atributos

| Atributo | Descripción | Tipo | Obligatorio | Longitud | Rango | Formato / reglas | Sensible |
|---|---|---|---|---|---|---|---|
| `id` | Representa el identificador único que le pertenece a cada categoría registrado en el sistema. | Identificador Único Universal | Sí | — | — | — | No |
| `nombre` | Representa el nombre de la categoría de reconocimiento. | Texto | Sí | 3 a 60 | — | Se debe asegurar la sanitización del dato para garantizar que su contenido no incluya intentos de ataque o instrucciones maliciosas. | No |
| `descripcion` | Representa la explicación de qué tipo de logros o comportamientos agrupa la categoría. | Texto | Sí | 10 a 250 | — | Se debe asegurar la sanitización del dato para garantizar que su contenido no incluya intentos de ataque o instrucciones maliciosas. | No |
| `estado` | Representa si la categoría puede usarse para nuevos Kudos. | Texto (valor fijo) | Sí | — | — | Valores permitidos: ACTIVA, INACTIVA Inicia en ACTIVA. Solo cambia mediante las responsabilidades del objeto. | No |
| `fechaCreacion` | Representa la fecha y hora de registro de la categoría. | Fecha y hora | Sí | — | — | AAAA-MM-DD HH:MM:SS | No |

**Combinaciones únicas:**

- Combinación única 1: Indica que no pueden existir dos categorías con el mismo nombre. (`nombre`)

### Políticas

| Número | Descripción | Objeto responsable | Regla responsable | Origen (enunciado) |
|---|---|---|---|---|
| CAT-P-0001 | Asegurar que la categoría exista con el identificador indicado. | Categoria | — | INV-018 |
| CAT-P-0002 | Asegurar que los datos requeridos (nombre, descripción) sean válidos en tipo de dato, longitud, obligatoriedad y formato. | Categoria | — | RN-132 |
| CAT-P-0003 | Asegurar que los datos de tipo texto se sanitícen antes de guardarse. | Categoria | — | RNF-003 |
| CAT-P-0004 | Asegurar que el identificador generado no esté siendo utilizado por otra categoría. | Categoria | — | RN-132 |
| CAT-P-0005 | Asegurar que no exista otra categoría con el mismo nombre. | Categoria | — | RN-132 |
| CAT-P-0006 | Asegurar que solo Administrador, Rector o Coordinador Académico gestionen categorías. | Usuario | USU-P-0016 | RN-134 a RN-143; Anexo 10, sección 8 |
| CAT-P-0007 | Asegurar que el cambio de estado sea válido (activar solo si está inactiva y desactivar solo si está activa). | Categoria | — | RN-133 |
| CAT-P-0008 | Asegurar que desactivar una categoría no modifique los Kudos ya creados con ella. | Kudo | — | RN-145; INV-018 |

### Muestreo de datos

| id | nombre | descripcion | estado | fechaCreacion | Combinación única 1 |
|---|---|---|---|---|---|
| 1 | Excelencia académica | Reconoce logros académicos sobresalientes. | ACTIVA | 2026-01-12 09:00:00 | Excelencia académica |
| 2 | Trabajo en equipo | Reconoce la colaboración y el apoyo a otros. | ACTIVA | 2026-01-12 09:01:00 | Trabajo en equipo |
| 3 | Liderazgo | Reconoce a quien guía e inspira a otros. | ACTIVA | 2026-01-12 09:02:00 | Liderazgo |
| 4 | Servicio a la comunidad | Reconoce aportes a la comunidad universitaria. | ACTIVA | 2026-01-12 09:03:00 | Servicio a la comunidad |
| 5 | Innovación | Reconoce ideas y proyectos innovadores. | INACTIVA | 2026-01-12 09:04:00 | Innovación |

---

## Facultad

**Línea de vida:** 1

Objeto de dominio que representa una unidad académica de la universidad que agrupa programas académicos y es dirigida por un coordinador de facultad.

### Responsabilidades

| Responsabilidad | Actor | Descripción | Entradas | Salida | Políticas | Evento |
|---|---|---|---|---|---|---|
| **Consultar las facultades** | Usuario autenticado | Permite obtener el listado de facultades, opcionalmente filtrado por estado. | filtro | Lista<Facultad> | — | Facultades consultadas |
| **Consultar una facultad** | Usuario autenticado | Permite obtener una facultad puntual a partir de su identificador. | id | Facultad | FAC-P-0001 | Facultad consultada |
| **Registrar una facultad** | Administrador | Permite crear una nueva facultad en la estructura organizacional. | datos {nombre} | Facultad | FAC-P-0006, FAC-P-0002, FAC-P-0003, FAC-P-0004, FAC-P-0005 | Facultad creada (EVT-008; RN-218) |
| **Actualizar una facultad** | Administrador | Permite corregir el nombre de una facultad. | id; datos {nombre} | Facultad | FAC-P-0006, FAC-P-0001, FAC-P-0002, FAC-P-0003, FAC-P-0005 | Facultad actualizada |
| **Inactivar una facultad** | Administrador | Permite inactivar una facultad. No se elimina para conservar su historia. | id | Facultad | FAC-P-0006, FAC-P-0001, FAC-P-0007 | Facultad inactivada |

**Hotspots (ver 6.4):**

- *Registrar una facultad:* H-06: El enunciado no dice quién crea facultades y programas. → Los gestiona el Administrador (administración técnica de la estructura).

### Atributos

| Atributo | Descripción | Tipo | Obligatorio | Longitud | Rango | Formato / reglas | Sensible |
|---|---|---|---|---|---|---|---|
| `id` | Representa el identificador único que le pertenece a cada facultad registrado en el sistema. | Identificador Único Universal | Sí | — | — | — | No |
| `nombre` | Representa el nombre oficial de la facultad. | Texto | Sí | 3 a 100 | — | Se debe asegurar la sanitización del dato para garantizar que su contenido no incluya intentos de ataque o instrucciones maliciosas. | No |
| `estado` | Representa si la facultad está activa o inactiva. | Texto (valor fijo) | Sí | — | — | Valores permitidos: ACTIVA, INACTIVA Inicia en ACTIVA. Solo cambia mediante las responsabilidades del objeto. | No |
| `fechaCreacion` | Representa la fecha y hora en que la facultad fue registrada. | Fecha y hora | Sí | — | — | AAAA-MM-DD HH:MM:SS | No |

**Combinaciones únicas:**

- Combinación única 1: Indica que no pueden existir dos facultades con el mismo nombre. (`nombre`)

### Políticas

| Número | Descripción | Objeto responsable | Regla responsable | Origen (enunciado) |
|---|---|---|---|---|
| FAC-P-0001 | Asegurar que la facultad exista con el identificador indicado. | Facultad | — | RN-039 |
| FAC-P-0002 | Asegurar que los datos requeridos (nombre) sean válidos en tipo de dato, longitud, obligatoriedad y formato. | Facultad | — | RN-040 |
| FAC-P-0003 | Asegurar que el dato de tipo texto se sanitice antes de guardarse, evitando contenido malicioso. | Facultad | — | RNF-003 |
| FAC-P-0004 | Asegurar que el identificador generado no esté siendo utilizado por otra facultad. | Facultad | — | RN-039 |
| FAC-P-0005 | Asegurar que no exista otra facultad con el mismo nombre. | Facultad | — | RN-039; INV-007 |
| FAC-P-0006 | Asegurar que solo el Administrador gestione facultades. | Usuario | USU-P-0016 | POL-004; Supuesto |
| FAC-P-0007 | Asegurar que no se inactive una facultad con programas académicos activos. | ProgramaAcademico | PRO-P-0001 | INV-007; INV-008; RN-045 |

### Muestreo de datos

| id | nombre | estado | fechaCreacion | Combinación única 1 |
|---|---|---|---|---|
| 1 | Ingeniería | ACTIVA | 2026-01-10 08:00:00 | Ingeniería |
| 2 | Ciencias de la Salud | ACTIVA | 2026-01-10 08:05:00 | Ciencias de la Salud |
| 3 | Ciencias Sociales | ACTIVA | 2026-01-10 08:10:00 | Ciencias Sociales |
| 4 | Educación | INACTIVA | 2026-01-10 08:15:00 | Educación |

---

## ParametroSistema

**Línea de vida:** 1

Objeto de dominio que representa un parámetro global de configuración de la plataforma que el Administrador puede modificar sin cambiar el código.

### Responsabilidades

| Responsabilidad | Actor | Descripción | Entradas | Salida | Políticas | Evento |
|---|---|---|---|---|---|---|
| **Consultar los parámetros** | Administrador | Permite listar los parámetros globales. | filtro | Lista<ParametroSistema> | PAR-P-0003 | Parámetros consultados |
| **Consultar un parámetro** | Sistema / Administrador | Permite leer el valor de un parámetro (lo usan otros objetos, por ejemplo para validar el dominio del correo). | clave | ParametroSistema | PAR-P-0001 | Parámetro consultado |
| **Actualizar un parámetro** | Administrador | Permite cambiar el valor de un parámetro global. | clave; valor | ParametroSistema | PAR-P-0003, PAR-P-0001, PAR-P-0002 | Parámetro actualizado |

### Atributos

| Atributo | Descripción | Tipo | Obligatorio | Longitud | Rango | Formato / reglas | Sensible |
|---|---|---|---|---|---|---|---|
| `id` | Representa el identificador único que le pertenece a cada parámetro registrado en el sistema. | Identificador Único Universal | Sí | — | — | — | No |
| `clave` | Representa el nombre único del parámetro. | Texto | Sí | 3 a 60 | — | MAYUSCULAS_CON_GUION_BAJO Se debe asegurar la sanitización del dato para garantizar que su contenido no incluya intentos de ataque o instrucciones maliciosas. | No |
| `valor` | Representa el valor vigente del parámetro. | Texto | Sí | 1 a 250 | — | Se debe asegurar la sanitización del dato para garantizar que su contenido no incluya intentos de ataque o instrucciones maliciosas. | No |
| `descripcion` | Representa para qué sirve el parámetro. | Texto | Sí | 10 a 250 | — | Se debe asegurar la sanitización del dato para garantizar que su contenido no incluya intentos de ataque o instrucciones maliciosas. | No |

**Combinaciones únicas:**

- Combinación única 1: Indica que no pueden existir dos parámetros con la misma clave. (`clave`)

### Políticas

| Número | Descripción | Objeto responsable | Regla responsable | Origen (enunciado) |
|---|---|---|---|---|
| PAR-P-0001 | Asegurar que el parámetro exista con la clave indicada. | ParametroSistema | — | RN-130 |
| PAR-P-0002 | Asegurar que el nuevo valor sea válido y esté sanitizado según el tipo de parámetro. | ParametroSistema | — | RN-131; RNF-003 |
| PAR-P-0003 | Asegurar que solo el Administrador gestione los parámetros globales. | Usuario | USU-P-0016 | RN-130; RN-131 |

### Muestreo de datos

| id | clave | valor | descripcion | Combinación única 1 |
|---|---|---|---|---|
| 1 | DOMINIO_CORREO_INSTITUCIONAL | uco.edu.co | Dominio permitido para los correos institucionales. | DOMINIO_CORREO_INSTITUCIONAL |
| 2 | MINUTOS_EXPIRACION_CODIGO | 15 | Minutos de vigencia de un código de verificación. | MINUTOS_EXPIRACION_CODIGO |
| 3 | INDICATIVO_CELULAR | +57 | Indicativo fijo de los números celulares. | INDICATIVO_CELULAR |

---

## Rol

**Línea de vida:** 1

Objeto de dominio que representa el rol institucional que cumple una persona dentro de UCO Kudos, el cual determina qué acciones puede realizar y qué rol es responsable de aprobar su registro.

### Responsabilidades

| Responsabilidad | Actor | Descripción | Entradas | Salida | Políticas | Evento |
|---|---|---|---|---|---|---|
| **Consultar los roles** | Usuario autenticado / Visitante | Permite obtener el listado de los roles institucionales, usado al registrarse y al gestionar usuarios. | filtro | Lista<Rol> | — | Roles consultados |
| **Consultar un rol** | Usuario autenticado | Permite obtener el detalle de un rol y su rol aprobador. | id | Rol | ROL-P-0001 | Rol consultado |
| **Cargar roles institucionales** | Sistema | Carga al instalar la plataforma los 7 roles del enunciado con su rol aprobador. | — | Lista<Rol> | ROL-P-0002 | Roles institucionales cargados |

**Hotspots (ver 6.4):**

- *Cargar roles institucionales:* H-05: ¿Los roles se crean o eliminan desde la plataforma? → No: es un catálogo fijo de 7 roles cargado al instalar, porque los define la institución (RN-001 a RN-038).

### Atributos

| Atributo | Descripción | Tipo | Obligatorio | Longitud | Rango | Formato / reglas | Sensible |
|---|---|---|---|---|---|---|---|
| `id` | Representa el identificador único que le pertenece a cada rol registrado en el sistema. | Identificador Único Universal | Sí | — | — | — | No |
| `codigo` | Representa el código único del rol institucional. | Texto (valor fijo) | Sí | — | — | ADMINISTRADOR, RECTOR, COORDINADOR_ACADEMICO, COORDINADOR_FACULTAD, COORDINADOR_PROGRAMA, DOCENTE, ESTUDIANTE Catálogo fijo definido por el enunciado; no se crea ni elimina desde la plataforma. | No |
| `nombre` | Representa el nombre legible del rol para mostrar en la interfaz. | Texto | Sí | 3 a 50 | — | Se debe asegurar la sanitización del dato para garantizar que su contenido no incluya intentos de ataque o instrucciones maliciosas. | No |
| `descripcion` | Representa la explicación de las responsabilidades del rol dentro de la universidad. | Texto | Sí | 10 a 250 | — | Se debe asegurar la sanitización del dato para garantizar que su contenido no incluya intentos de ataque o instrucciones maliciosas. | No |
| `rolAprobador` | Representa el rol responsable de aprobar las solicitudes de registro de este rol, según la matriz de aprobaciones. | Rol | Sí | — | — | — | No |

**Combinaciones únicas:**

- Combinación única 1: Indica que no pueden existir dos roles con el mismo código. (`codigo`)

### Políticas

| Número | Descripción | Objeto responsable | Regla responsable | Origen (enunciado) |
|---|---|---|---|---|
| ROL-P-0001 | Asegurar que el rol exista con el identificador indicado. | Rol | — | RN-004 |
| ROL-P-0002 | Asegurar que cada rol tenga el rol aprobador definido en la matriz de aprobaciones (el Administrador se aprueba a sí mismo). | Rol | — | RN-090 a RN-095; Anexo 10, sección 5 |

### Muestreo de datos

| id | codigo | nombre | rolAprobador |
|---|---|---|---|
| 1 | ADMINISTRADOR | Administrador | ADMINISTRADOR |
| 2 | RECTOR | Rector | ADMINISTRADOR |
| 3 | COORDINADOR_ACADEMICO | Coordinador Académico | RECTOR |
| 4 | COORDINADOR_FACULTAD | Coordinador de Facultad | COORDINADOR_ACADEMICO |
| 5 | COORDINADOR_PROGRAMA | Coordinador de Programa | COORDINADOR_FACULTAD |
| 6 | DOCENTE | Docente | COORDINADOR_PROGRAMA |
| 7 | ESTUDIANTE | Estudiante | COORDINADOR_PROGRAMA |

---

## ProgramaAcademico

**Línea de vida:** 2

Objeto de dominio que representa un programa académico ofrecido por la universidad, que pertenece a exactamente una facultad y es dirigido por un coordinador de programa.

### Responsabilidades

| Responsabilidad | Actor | Descripción | Entradas | Salida | Políticas | Evento |
|---|---|---|---|---|---|---|
| **Consultar los programas** | Usuario autenticado | Permite listar programas, opcionalmente por facultad. El Coordinador de Facultad ve los de su facultad. | filtro | Lista<ProgramaAcademico> | — | Programas consultados |
| **Consultar un programa** | Usuario autenticado | Permite obtener un programa por su identificador. | id | ProgramaAcademico | PRO-P-0001 | Programa consultado |
| **Registrar un programa** | Administrador | Permite crear un programa dentro de una facultad activa. | datos {nombre, facultad} | ProgramaAcademico | PRO-P-0007, PRO-P-0002, PRO-P-0003, PRO-P-0004, PRO-P-0005, PRO-P-0006 | Programa creado (EVT-009; RN-221) |
| **Actualizar un programa** | Administrador | Permite corregir el nombre o la facultad de un programa. | id; datos {nombre, facultad} | ProgramaAcademico | PRO-P-0007, PRO-P-0001, PRO-P-0002, PRO-P-0005, PRO-P-0006 | Programa actualizado |
| **Inactivar un programa** | Administrador | Permite inactivar un programa sin vínculos vigentes. No se elimina para conservar su historia. | id | ProgramaAcademico | PRO-P-0007, PRO-P-0001, PRO-P-0008 | Programa inactivado |

**Hotspots (ver 6.4):**

- *Registrar un programa:* H-06: El enunciado no dice quién crea facultades y programas. → Los gestiona el Administrador (administración técnica de la estructura).

### Atributos

| Atributo | Descripción | Tipo | Obligatorio | Longitud | Rango | Formato / reglas | Sensible |
|---|---|---|---|---|---|---|---|
| `id` | Representa el identificador único que le pertenece a cada programa académico registrado en el sistema. | Identificador Único Universal | Sí | — | — | — | No |
| `nombre` | Representa el nombre oficial del programa. | Texto | Sí | 3 a 100 | — | Se debe asegurar la sanitización del dato para garantizar que su contenido no incluya intentos de ataque o instrucciones maliciosas. | No |
| `facultad` | Representa la facultad a la que pertenece el programa. | Facultad | Sí | — | — | — | No |
| `estado` | Representa si el programa está activo o inactivo. | Texto (valor fijo) | Sí | — | — | Valores permitidos: ACTIVA, INACTIVA Inicia en ACTIVA. Solo cambia mediante las responsabilidades del objeto. | No |
| `fechaCreacion` | Representa la fecha y hora de registro del programa. | Fecha y hora | Sí | — | — | AAAA-MM-DD HH:MM:SS | No |

**Combinaciones únicas:**

- Combinación única 1: Indica que no pueden existir dos programas con el mismo nombre dentro de la misma facultad. (`nombre`, `facultad`)

### Políticas

| Número | Descripción | Objeto responsable | Regla responsable | Origen (enunciado) |
|---|---|---|---|---|
| PRO-P-0001 | Asegurar que el programa exista con el identificador indicado. | ProgramaAcademico | — | RN-047 |
| PRO-P-0002 | Asegurar que los datos requeridos (nombre, facultad) sean válidos en tipo de dato, longitud, obligatoriedad y formato. | ProgramaAcademico | — | RN-048 |
| PRO-P-0003 | Asegurar que los datos de tipo texto se sanitícen antes de guardarse. | ProgramaAcademico | — | RNF-003 |
| PRO-P-0004 | Asegurar que el identificador generado no esté siendo utilizado por otro programa. | ProgramaAcademico | — | RN-047 |
| PRO-P-0005 | Asegurar que la facultad indicada exista y esté activa. | Facultad | FAC-P-0001 | RN-046; RN-049; INV-008; RN-041 |
| PRO-P-0006 | Asegurar que no exista otro programa con el mismo nombre en la misma facultad. | ProgramaAcademico | — | INV-007 |
| PRO-P-0007 | Asegurar que solo el Administrador gestione programas académicos. | Usuario | USU-P-0016 | POL-004; Supuesto |
| PRO-P-0008 | Asegurar que no se inactive un programa con estudiantes o docentes vinculados vigentes. | VinculacionPrograma | VIN-P-0003 | INV-007 |

### Muestreo de datos

| id | nombre | facultad | estado | fechaCreacion | Combinación única 1 |
|---|---|---|---|---|---|
| 1 | Ingeniería de Sistemas | Ingeniería | ACTIVA | 2026-01-10 09:00:00 | Ingeniería de Sistemas-Ingeniería |
| 2 | Ingeniería Industrial | Ingeniería | ACTIVA | 2026-01-10 09:05:00 | Ingeniería Industrial-Ingeniería |
| 3 | Enfermería | Ciencias de la Salud | ACTIVA | 2026-01-10 09:10:00 | Enfermería-Ciencias de la Salud |
| 4 | Psicología | Ciencias Sociales | ACTIVA | 2026-01-10 09:15:00 | Psicología-Ciencias Sociales |

---

## Usuario

**Línea de vida:** 2

Objeto de dominio que representa a una persona de la comunidad universitaria registrada en UCO Kudos, identificada por su correo institucional, que cumple un único rol institucional.

### Responsabilidades

| Responsabilidad | Actor | Descripción | Entradas | Salida | Políticas | Evento |
|---|---|---|---|---|---|---|
| **Registrarse** | Persona de la comunidad | Permite a una persona de la comunidad crear su cuenta indicando el rol que solicita. La cuenta queda REGISTRADA hasta ser aprobada y verificada. | datos {nombres, apellidos, correoInstitucional, numeroCelular, clave, rol, ámbito} | Usuario | USU-P-0002, USU-P-0003, USU-P-0004, USU-P-0005, USU-P-0007, USU-P-0006, USU-P-0010, USU-P-0008, USU-P-0017, USU-P-0018 | Usuario registrado (EVT-001; RN-213) |
| **Iniciar sesión** | Persona de la comunidad | Permite ingresar a la plataforma con correo y clave. | credenciales {correoInstitucional, clave} | Sesión | USU-P-0011, USU-P-0012 | Sesión iniciada |
| **Consultar mi información** | Usuario autenticado | Permite al usuario ver sus datos personales y el estado de sus validaciones. | idSesion | Usuario | USU-P-0001 | Información personal consultada |
| **Actualizar mi información** | Usuario autenticado | Permite al usuario cambiar sus nombres y apellidos. | datos {nombres, apellidos} | Usuario | USU-P-0001, USU-P-0002, USU-P-0003, USU-P-0013, USU-P-0019 | Información personal actualizada |
| **Actualizar mi correo institucional** | Usuario autenticado | Permite cambiar el correo; queda pendiente de una nueva verificación. | correoInstitucional | Usuario | USU-P-0004, USU-P-0005, USU-P-0014, USU-P-0019 | Correo institucional actualizado |
| **Actualizar mi número celular** | Usuario autenticado | Permite cambiar el celular; queda pendiente de una nueva verificación. | numeroCelular | Usuario | USU-P-0007, USU-P-0006, USU-P-0015, USU-P-0019 | Número celular actualizado |
| **Activar la cuenta** | Sistema | El sistema activa la cuenta cuando se cumplen las tres condiciones: solicitud aprobada, correo verificado y celular verificado. | id | Usuario | USU-P-0012, USU-P-0019 | Cuenta activada (EVT-005; RN-215) |
| **Suspender una cuenta** | Gestor según matriz de gestión | Permite al gestor suspender temporalmente la cuenta de un usuario de su ámbito. | id; motivo | Usuario | USU-P-0016, USU-P-0001, USU-P-0009, USU-P-0019 | Cuenta suspendida (EVT-006; RN-216) |
| **Desactivar una cuenta** | Gestor según matriz de gestión | Permite al gestor desactivar la cuenta de un usuario de su ámbito. No se elimina para conservar su historial. | id; motivo | Usuario | USU-P-0016, USU-P-0001, USU-P-0009, USU-P-0019 | Cuenta desactivada (EVT-007; RN-217) |
| **Consultar un usuario** | Gestor según matriz de gestión | Permite consultar los datos de un usuario dentro del ámbito del gestor. | id | Usuario | USU-P-0016, USU-P-0001 | Usuario consultado |
| **Consultar los usuarios** | Usuario autenticado | Permite listar usuarios filtrando por rol, programa o facultad, solo dentro del ámbito del gestor. También sirve para buscar destinatarios de Kudos. (RF-076 y RF-077: el filtro por rol permite saber quién es el Rector y el Coordinador Académico activos). | filtro | Lista<Usuario> | USU-P-0016 | Usuarios consultados |

**Hotspots (ver 6.4):**

- *Registrarse:* H-08: ¿El Rector y el Administrador se auto-registran? → No: los registra un Administrador (RN-094, RN-095).; H-16: ¿Qué formato tiene el celular? → 10 dígitos que inician en 3, con indicativo fijo +57 (sin tabla de indicativos).
- *Iniciar sesión:* H-09: ¿Cuál es el mecanismo de inicio de sesión? → Correo + contraseña cifrada (`claveHash`); la implementación técnica (Spring Security / JWT) se hace en el Back-End.

### Atributos

| Atributo | Descripción | Tipo | Obligatorio | Longitud | Rango | Formato / reglas | Sensible |
|---|---|---|---|---|---|---|---|
| `id` | Representa el identificador único que le pertenece a cada usuario registrado en el sistema. | Identificador Único Universal | Sí | — | — | — | No |
| `nombres` | Representa los nombres de la persona. | Texto | Sí | 2 a 60 | — | Solo letras y espacios Se debe asegurar la sanitización del dato para garantizar que su contenido no incluya intentos de ataque o instrucciones maliciosas. | No |
| `apellidos` | Representa los apellidos de la persona. | Texto | Sí | 2 a 60 | — | Solo letras y espacios Se debe asegurar la sanitización del dato para garantizar que su contenido no incluya intentos de ataque o instrucciones maliciosas. | No |
| `correoInstitucional` | Representa el correo con el que la persona se identifica e ingresa a la plataforma. | Texto | Sí | 6 a 100 | — | usuario@<DOMINIO_CORREO_INSTITUCIONAL> Se debe asegurar la sanitización del dato para garantizar que su contenido no incluya intentos de ataque o instrucciones maliciosas. Al cambiar, debe volver a verificarse. | No |
| `numeroCelular` | Representa el número celular de la persona, con indicativo fijo +57. | Texto numérico | Sí | 10 a 10 | — | 10 dígitos que inician en 3 Se guarda sin el indicativo. Al cambiar, debe volver a verificarse. | No |
| `claveHash` | Representa la contraseña de la persona, guardada cifrada (hash). | Texto | Sí | 8 a 64 | — | Mínimo una mayúscula, una minúscula y un número Nunca se guarda ni se muestra en texto plano. | Sí |
| `correoVerificado` | Indica si el correo institucional fue validado. | Booleano | Sí | — | — | Inicia en falso. | No |
| `celularVerificado` | Indica si el número celular fue validado. | Booleano | Sí | — | — | Inicia en falso. | No |
| `estadoCuenta` | Representa el estado de la cuenta. | Texto (valor fijo) | Sí | — | — | Valores permitidos: REGISTRADA, ACTIVA, SUSPENDIDA, DESACTIVADA Inicia en REGISTRADA. Solo cambia mediante las responsabilidades del objeto. | No |
| `rol` | Representa el único rol institucional de la persona. | Rol | Sí | — | — | No lo puede modificar el propio usuario (RN-107). | No |
| `fechaRegistro` | Representa la fecha y hora de registro. | Fecha y hora | Sí | — | — | AAAA-MM-DD HH:MM:SS | No |

**Combinaciones únicas:**

- Combinación única 1: Indica que no pueden existir dos usuarios con el mismo correo institucional. (`correoInstitucional`)
- Combinación única 2: Indica que no pueden existir dos usuarios con el mismo número celular. (`numeroCelular`)

### Políticas

| Número | Descripción | Objeto responsable | Regla responsable | Origen (enunciado) |
|---|---|---|---|---|
| USU-P-0001 | Asegurar que el usuario exista con el identificador indicado. | Usuario | — | RN-101; RN-102; RN-103 |
| USU-P-0002 | Asegurar que los datos requeridos (nombres, apellidos, correo, celular, clave, rol) sean válidos en tipo de dato, longitud, obligatoriedad y formato. | Usuario | — | RN-069 a RN-072 |
| USU-P-0003 | Asegurar que los datos de tipo texto se sanitícen antes de guardarse. | Usuario | — | RNF-003 |
| USU-P-0004 | Asegurar que el correo pertenezca al dominio institucional configurado. | ParametroSistema | PAR-P-0001 | RN-069; POL-001 |
| USU-P-0005 | Asegurar que no exista otro usuario con el mismo correo institucional. | Usuario | — | RN-073 |
| USU-P-0006 | Asegurar que no exista otro usuario con el mismo número celular. | Usuario | — | RN-074 |
| USU-P-0007 | Asegurar que el celular tenga 10 dígitos, inicie en 3 y use el indicativo fijo +57. | Usuario | — | RN-070; POL-002; Decisión del proyecto |
| USU-P-0008 | Asegurar que el rol indicado exista. | Rol | ROL-P-0001 | RN-004 |
| USU-P-0009 | Asegurar que el cambio de estado de la cuenta sea válido (REGISTRADA→ACTIVA, ACTIVA→SUSPENDIDA, SUSPENDIDA→ACTIVA, cualquiera→DESACTIVADA). | Usuario | — | RN-098; RN-099 |
| USU-P-0010 | Asegurar que la clave cumpla la política de seguridad y se guarde cifrada. | Usuario | — | RNF-003 |
| USU-P-0011 | Asegurar que las credenciales correspondan a un usuario registrado. | Usuario | — | RNF-001 |
| USU-P-0012 | Asegurar que solo ingresen cuentas ACTIVAS: aprobadas, con correo verificado y celular verificado. | Usuario | — | RN-084 a RN-089; POL-003; RN-075; RN-076 |
| USU-P-0013 | Asegurar que ningún usuario modifique directamente su propio rol. | Usuario | — | RN-107; INV-005; INV-006 |
| USU-P-0014 | Asegurar que al cambiar el correo se marque como no verificado y se genere una nueva verificación. | VerificacionContacto | VER-P-0003 | RN-081; RN-110; RN-104 |
| USU-P-0015 | Asegurar que al cambiar el celular se marque como no verificado y se genere una nueva verificación. | VerificacionContacto | VER-P-0003 | RN-082; RN-111; RN-105 |
| USU-P-0016 | Asegurar que quien ejecuta la acción tenga cuenta activa y el permiso de su rol, dentro de su ámbito (matrices de gestión, moderación y categorías). | Usuario | — | POL-004; POL-005; RN-112 a RN-131 |
| USU-P-0017 | Asegurar que solo exista un Rector activo y un Coordinador Académico activo. | Usuario | — | RN-005; RN-011; INV-001; INV-002 |
| USU-P-0018 | Asegurar que todo registro genere su solicitud de registro y sus verificaciones de correo y celular. | SolicitudRegistro | SOL-P-0001 | RN-077; RN-078; RN-085 |
| USU-P-0019 | Asegurar que toda modificación de datos y cambio de estado de la cuenta quede auditado. | RegistroAuditoria | AUD-P-0001 | RN-083; RN-097 a RN-099; RN-109 |

### Muestreo de datos

| id | nombres | apellidos | correoInstitucional | numeroCelular | claveHash | correoVerificado | celularVerificado | estadoCuenta | rol | fechaRegistro | Combinación única 1 | Combinación única 2 |
|---|---|---|---|---|---|---|---|---|---|---|---|---|
| 1 | Laura | Gómez | laura.gomez@uco.edu.co | 3001112233 | ******** | Sí | Sí | ACTIVA | ADMINISTRADOR | 2026-01-05 08:00:00 | laura.gomez@uco.edu.co | 3001112233 |
| 2 | Carlos | Restrepo | carlos.restrepo@uco.edu.co | 3002223344 | ******** | Sí | Sí | ACTIVA | RECTOR | 2026-01-06 09:00:00 | carlos.restrepo@uco.edu.co | 3002223344 |
| 3 | Ana | Martínez | ana.martinez@uco.edu.co | 3003334455 | ******** | Sí | Sí | ACTIVA | COORDINADOR_ACADEMICO | 2026-01-07 10:00:00 | ana.martinez@uco.edu.co | 3003334455 |
| 4 | Jorge | Ramírez | jorge.ramirez@uco.edu.co | 3004445566 | ******** | Sí | Sí | ACTIVA | COORDINADOR_FACULTAD | 2026-01-08 10:00:00 | jorge.ramirez@uco.edu.co | 3004445566 |
| 5 | Paula | Ospina | paula.ospina@uco.edu.co | 3005556677 | ******** | Sí | Sí | ACTIVA | COORDINADOR_PROGRAMA | 2026-01-09 11:00:00 | paula.ospina@uco.edu.co | 3005556677 |
| 6 | Andrés | Zapata | andres.zapata@uco.edu.co | 3006667788 | ******** | Sí | Sí | ACTIVA | DOCENTE | 2026-01-20 14:00:00 | andres.zapata@uco.edu.co | 3006667788 |
| 7 | Valentina | Ríos | valentina.rios@uco.edu.co | 3007778899 | ******** | Sí | Sí | ACTIVA | ESTUDIANTE | 2026-02-02 15:00:00 | valentina.rios@uco.edu.co | 3007778899 |
| 8 | Mateo | Castaño | mateo.castano@uco.edu.co | 3008889900 | ******** | Sí | No | REGISTRADA | ESTUDIANTE | 2026-11-10 16:00:00 | mateo.castano@uco.edu.co | 3008889900 |

---

## CoordinacionFacultad

**Línea de vida:** 3

Objeto de dominio que representa el periodo durante el cual un usuario con rol COORDINADOR_FACULTAD dirige una facultad, permitiendo conocer el coordinador vigente y su historial.

### Responsabilidades

| Responsabilidad | Actor | Descripción | Entradas | Salida | Políticas | Evento |
|---|---|---|---|---|---|---|
| **Asignar un coordinador** | Sistema / Coordinador Académico | Asigna el coordinador a una facultad cuando se aprueba su solicitud. | coordinador; facultad | CoordinacionFacultad | CFA-P-0003, CFA-P-0004, CFA-P-0002, CFA-P-0005, CFA-P-0006 | Coordinador de facultad asignado (EVT-010; RN-219) |
| **Reemplazar un coordinador** | Coordinador Académico | Cierra la coordinación vigente (fechaFin) y abre una nueva con el nuevo coordinador. | facultad; nuevoCoordinador | CoordinacionFacultad | CFA-P-0007, CFA-P-0003, CFA-P-0005, CFA-P-0001, CFA-P-0006 | Coordinador de facultad reemplazado (EVT-011; RN-220) |
| **Consultar el coordinador vigente** | Usuario autenticado | Permite saber quién dirige actualmente una facultad (o qué unidad dirige un coordinador). | facultad | CoordinacionFacultad | — | Coordinador consultado |
| **Consultar el historial de coordinadores** | Coordinador Académico | Permite ver todos los coordinadores que ha tenido una facultad. | facultad | Lista<CoordinacionFacultad> | CFA-P-0007 | Historial de coordinadores consultado |

### Atributos

| Atributo | Descripción | Tipo | Obligatorio | Longitud | Rango | Formato / reglas | Sensible |
|---|---|---|---|---|---|---|---|
| `id` | Representa el identificador único que le pertenece a cada coordinación registrado en el sistema. | Identificador Único Universal | Sí | — | — | — | No |
| `coordinador` | Representa el usuario con rol COORDINADOR_FACULTAD que dirige una facultad. | Usuario | Sí | — | — | — | No |
| `facultad` | Representa una facultad dirigida. | Facultad | Sí | — | — | — | No |
| `fechaInicio` | Representa desde cuándo el coordinador está vigente. | Fecha y hora | Sí | — | — | AAAA-MM-DD HH:MM:SS | No |
| `fechaFin` | Representa hasta cuándo estuvo vigente; vacía significa vigente. | Fecha y hora | No | — | — | AAAA-MM-DD HH:MM:SS | No |

**Combinaciones únicas:**

- Combinación única 1: Indica que solo puede existir una coordinación vigente (fechaFin vacía) por facultad. (`facultad`, `fechaFin vacía`)

### Políticas

| Número | Descripción | Objeto responsable | Regla responsable | Origen (enunciado) |
|---|---|---|---|---|
| CFA-P-0001 | Asegurar que un reemplazo cierre la coordinación anterior con fechaFin y no la borre. | CoordinacionFacultad | — | RN-043; RN-044 |
| CFA-P-0002 | Asegurar que solo exista un coordinador vigente por facultad. | CoordinacionFacultad | — | RN-017; INV-003; RN-042 |
| CFA-P-0003 | Asegurar que el coordinador tenga el rol COORDINADOR_FACULTAD y cuenta activa. | Usuario | USU-P-0001 | RN-001 |
| CFA-P-0004 | Asegurar que una facultad exista y esté activa. | Facultad | FAC-P-0001 | INV-009; INV-010 |
| CFA-P-0005 | Asegurar que un coordinador dirija solo una unidad vigente a la vez. | CoordinacionFacultad | — | INV-009; INV-010 |
| CFA-P-0006 | Asegurar que asignaciones y reemplazos queden auditados. | RegistroAuditoria | AUD-P-0001 | RN-044; RN-052 |
| CFA-P-0007 | Asegurar que solo el Coordinador Académico reemplace coordinadores dentro de su ámbito. | Usuario | USU-P-0016 | RN-117; RN-120; RN-121 |

### Muestreo de datos

| id | coordinador | facultad | fechaInicio | fechaFin | Combinación única 1 |
|---|---|---|---|---|---|
| 1 | Jorge Ramírez | Ingeniería | 2026-01-08 12:00:00 |  | Ingeniería (vigente) |

---

## CoordinacionPrograma

**Línea de vida:** 3

Objeto de dominio que representa el periodo durante el cual un usuario con rol COORDINADOR_PROGRAMA dirige un programa académico, permitiendo conocer el coordinador vigente y su historial.

### Responsabilidades

| Responsabilidad | Actor | Descripción | Entradas | Salida | Políticas | Evento |
|---|---|---|---|---|---|---|
| **Asignar un coordinador** | Sistema / Coordinador de Facultad | Asigna el coordinador a un programa académico cuando se aprueba su solicitud. | coordinador; programaAcademico | CoordinacionPrograma | CPR-P-0003, CPR-P-0004, CPR-P-0002, CPR-P-0005, CPR-P-0006 | Coordinador de programa asignado (EVT-012; RN-222) |
| **Reemplazar un coordinador** | Coordinador de Facultad | Cierra la coordinación vigente (fechaFin) y abre una nueva con el nuevo coordinador. | programaAcademico; nuevoCoordinador | CoordinacionPrograma | CPR-P-0007, CPR-P-0003, CPR-P-0005, CPR-P-0001, CPR-P-0006 | Coordinador de programa reemplazado (EVT-013; RN-223) |
| **Consultar el coordinador vigente** | Usuario autenticado | Permite saber quién dirige actualmente un programa académico (o qué unidad dirige un coordinador). | programaAcademico | CoordinacionPrograma | — | Coordinador consultado |
| **Consultar el historial de coordinadores** | Coordinador de Facultad | Permite ver todos los coordinadores que ha tenido un programa académico. | programaAcademico | Lista<CoordinacionPrograma> | CPR-P-0007 | Historial de coordinadores consultado |

### Atributos

| Atributo | Descripción | Tipo | Obligatorio | Longitud | Rango | Formato / reglas | Sensible |
|---|---|---|---|---|---|---|---|
| `id` | Representa el identificador único que le pertenece a cada coordinación registrado en el sistema. | Identificador Único Universal | Sí | — | — | — | No |
| `coordinador` | Representa el usuario con rol COORDINADOR_PROGRAMA que dirige un programa académico. | Usuario | Sí | — | — | — | No |
| `programaAcademico` | Representa un programa académico dirigida. | ProgramaAcademico | Sí | — | — | — | No |
| `fechaInicio` | Representa desde cuándo el coordinador está vigente. | Fecha y hora | Sí | — | — | AAAA-MM-DD HH:MM:SS | No |
| `fechaFin` | Representa hasta cuándo estuvo vigente; vacía significa vigente. | Fecha y hora | No | — | — | AAAA-MM-DD HH:MM:SS | No |

**Combinaciones únicas:**

- Combinación única 1: Indica que solo puede existir una coordinación vigente (fechaFin vacía) por programaAcademico. (`programaAcademico`, `fechaFin vacía`)

### Políticas

| Número | Descripción | Objeto responsable | Regla responsable | Origen (enunciado) |
|---|---|---|---|---|
| CPR-P-0001 | Asegurar que un reemplazo cierre la coordinación anterior con fechaFin y no la borre. | CoordinacionPrograma | — | RN-051; RN-052 |
| CPR-P-0002 | Asegurar que solo exista un coordinador vigente por programaAcademico. | CoordinacionPrograma | — | RN-023; INV-004; RN-050 |
| CPR-P-0003 | Asegurar que el coordinador tenga el rol COORDINADOR_PROGRAMA y cuenta activa. | Usuario | USU-P-0001 | RN-001 |
| CPR-P-0004 | Asegurar que un programa académico exista y esté activa. | ProgramaAcademico | PRO-P-0001 | INV-009; INV-010 |
| CPR-P-0005 | Asegurar que un coordinador dirija solo una unidad vigente a la vez. | CoordinacionPrograma | — | INV-009; INV-010 |
| CPR-P-0006 | Asegurar que asignaciones y reemplazos queden auditados. | RegistroAuditoria | AUD-P-0001 | RN-044; RN-052 |
| CPR-P-0007 | Asegurar que solo el Coordinador de Facultad reemplace coordinadores dentro de su ámbito. | Usuario | USU-P-0016 | RN-117; RN-120; RN-121 |

### Muestreo de datos

| id | coordinador | programaAcademico | fechaInicio | fechaFin | Combinación única 1 |
|---|---|---|---|---|---|
| 1 | Paula Ospina | Ingeniería de Sistemas | 2026-01-09 15:00:00 |  | Ingeniería de Sistemas (vigente) |

---

## Notificacion

**Línea de vida:** 3

Objeto de dominio que representa un aviso que la plataforma envía a un usuario cuando ocurre algo que le interesa, como recibir un Kudo o tener una solicitud por aprobar.

### Responsabilidades

| Responsabilidad | Actor | Descripción | Entradas | Salida | Políticas | Evento |
|---|---|---|---|---|---|---|
| **Generar una notificación** | Sistema | El sistema crea el aviso como reacción a un evento del dominio (Kudo creado, réplica creada, solicitud creada o resuelta, contenido vetado). | destinatario; tipo; mensaje | Notificacion | NOT-P-0001 | Notificación generada |
| **Consultar mis notificaciones** | Usuario autenticado | Permite al usuario ver sus avisos, primero los no leídos. | — | Lista<Notificacion> | NOT-P-0002 | Notificaciones consultadas |
| **Marcar como leída** | Usuario autenticado | Permite marcar un aviso como leído. | id | Notificacion | NOT-P-0002 | Notificación leída |

### Atributos

| Atributo | Descripción | Tipo | Obligatorio | Longitud | Rango | Formato / reglas | Sensible |
|---|---|---|---|---|---|---|---|
| `id` | Representa el identificador único que le pertenece a cada notificación registrado en el sistema. | Identificador Único Universal | Sí | — | — | — | No |
| `destinatario` | Representa el usuario que recibe el aviso. | Usuario | Sí | — | — | — | No |
| `tipo` | Representa la clase de aviso. | Texto (valor fijo) | Sí | — | — | KUDO_RECIBIDO, REPLICA_RECIBIDA, SOLICITUD_PENDIENTE, SOLICITUD_RESUELTA, CONTENIDO_VETADO | No |
| `mensaje` | Representa el texto del aviso. | Texto | Sí | — a 250 | — | — | No |
| `fechaCreacion` | Representa cuándo se generó el aviso. | Fecha y hora | Sí | — | — | AAAA-MM-DD HH:MM:SS | No |
| `leida` | Indica si el destinatario ya vio el aviso. | Booleano | Sí | — | — | Inicia en falso. | No |

### Políticas

| Número | Descripción | Objeto responsable | Regla responsable | Origen (enunciado) |
|---|---|---|---|---|
| NOT-P-0001 | Asegurar que el destinatario exista. | Usuario | USU-P-0001 | Supuesto |
| NOT-P-0002 | Asegurar que un usuario solo consulte y marque sus propias notificaciones. | Notificacion | — | RNF-004 |

### Muestreo de datos

| id | destinatario | tipo | mensaje | fechaCreacion | leida |
|---|---|---|---|---|---|
| 1 | Valentina Ríos | KUDO_RECIBIDO | Andrés Zapata te dio un Kudo en Excelencia académica. | 2026-10-15 10:00:00 | Sí |
| 2 | Andrés Zapata | REPLICA_RECIBIDA | Paula Ospina respondió tu Kudo. | 2026-10-15 11:00:00 | No |
| 3 | Paula Ospina | SOLICITUD_PENDIENTE | Mateo Castaño solicita registro como ESTUDIANTE. | 2026-11-10 16:00:00 | No |
| 4 | Valentina Ríos | CONTENIDO_VETADO | Tu Kudo fue vetado por la Rectoría. | 2026-11-02 09:00:00 | No |

---

## Publicacion

**Línea de vida:** 3 · **Objeto abstracto** (lo heredan Kudo y Réplica)

Objeto de dominio abstracto que representa cualquier contenido publicado por un usuario en la conversación institucional (un Kudo o una Réplica). Concentra lo común: autor, contenido, fecha, estado y su ciclo de vida.

### Responsabilidades

| Responsabilidad | Actor | Descripción | Entradas | Salida | Políticas | Evento |
|---|---|---|---|---|---|---|
| **Hacer visible** | Sistema | Pasa la publicación de CREADO a VISIBLE inmediatamente después de registrarse. | — | Publicacion | PUB-P-0004 | Publicación visible |
| **Marcar como reportada** | Sistema | Pasa a REPORTADO cuando recibe su primer reporte. | — | Publicacion | PUB-P-0004 | Publicación reportada (EVT-026; EVT-030) |
| **Iniciar revisión** | Rector / Coordinador Académico | Pasa a EN_REVISION cuando una autoridad empieza a revisar sus reportes. | — | Publicacion | PUB-P-0004 | Revisión iniciada (EVT-035) |
| **Liberar tras revisión** | Rector / Coordinador Académico | Vuelve a VISIBLE cuando se rechazan todos sus reportes. | — | Publicacion | PUB-P-0004 | Contenido liberado tras revisión (EVT-037; RN-242) |
| **Vetar** | Rector / Coordinador Académico | Pasa a VETADO; deja de estar disponible para interacción normal. | — | Publicacion | PUB-P-0004, PUB-P-0005 | Contenido vetado (RN-241) |
| **Calcular indicador de calificaciones** | Sistema | Calcula el promedio y la cantidad de calificaciones de la publicación. | — | Indicador {promedio, cantidad} | — | Indicador recalculado (EVT-034) |

### Atributos

| Atributo | Descripción | Tipo | Obligatorio | Longitud | Rango | Formato / reglas | Sensible |
|---|---|---|---|---|---|---|---|
| `id` | Representa el identificador único que le pertenece a cada publicación registrado en el sistema. | Identificador Único Universal | Sí | — | — | — | No |
| `autor` | Representa el usuario que publica el contenido. | Usuario | Sí | — | — | — | No |
| `contenido` | Representa el texto completo de la publicación, sin límite de longitud. | Texto ilimitado | Sí | 1 a Sin límite | — | Se debe asegurar la sanitización del dato para garantizar que su contenido no incluya intentos de ataque o instrucciones maliciosas. Nunca se trunca. | No |
| `fechaCreacion` | Representa cuándo se publicó. | Fecha y hora | Sí | — | — | AAAA-MM-DD HH:MM:SS | No |
| `estado` | Representa el estado en el ciclo de vida del contenido. | Texto (valor fijo) | Sí | — | — | Valores permitidos: CREADO, VISIBLE, REPORTADO, EN_REVISION, VETADO Inicia en CREADO. Solo cambia mediante las responsabilidades del objeto. | No |
| `promedioEstrellas` | Representa el promedio de las calificaciones recibidas. | Decimal | No | — | 1 a 5 | Indicador calculado a partir de las calificaciones (RN-185). | No |

### Políticas

| Número | Descripción | Objeto responsable | Regla responsable | Origen (enunciado) |
|---|---|---|---|---|
| PUB-P-0001 | Asegurar que la publicación exista con el identificador indicado. | Publicacion | — | RN-151 |
| PUB-P-0002 | Asegurar que el autor exista y tenga la cuenta activa. | Usuario | USU-P-0012 | RN-147; RN-168; INV-020; INV-025; RN-150; RN-169; INV-022 |
| PUB-P-0003 | Asegurar que el contenido sea obligatorio, se sanitice y se guarde completo, sin límite de longitud ni truncamiento. | Publicacion | — | RN-152 a RN-154; RN-170; INV-024 |
| PUB-P-0004 | Asegurar que solo ocurran transiciones válidas: CREADO→VISIBLE, VISIBLE→REPORTADO, REPORTADO→EN_REVISION, EN_REVISION→VISIBLE, y cualquier estado no vetado→VETADO. | Publicacion | — | RN-201 a RN-209 |
| PUB-P-0005 | Asegurar que una publicación vetada no reciba réplicas, calificaciones ni reportes. | Publicacion | — | RN-205 |
| PUB-P-0006 | Asegurar que la publicación no se edite ni se elimine después de publicada. | Publicacion | — | RN-151; RN-174; Decisión del proyecto |

### Muestreo de datos

| id | autor | contenido | fechaCreacion | estado | promedioEstrellas |
|---|---|---|---|---|---|
| 1 | Andrés Zapata | Valentina obtuvo el mejor resultado del curso de Estructuras de Datos... | 2026-10-15 10:00:00 | VISIBLE | 4.5 |
| 2 | Valentina Ríos | Gracias al profe Andrés por acompañar el semillero los sábados... | 2026-10-16 08:30:00 | VISIBLE | 4.0 |
| 3 | Paula Ospina | Andrés lideró la acreditación del programa este semestre... | 2026-10-20 14:00:00 | REPORTADO |  |
| 4 | Valentina Ríos | Contenido que incumple la política institucional... | 2026-10-28 19:00:00 | VETADO |  |
| 5 | Paula Ospina | ¡Felicitaciones Valentina, muy merecido! | 2026-10-15 11:00:00 | VISIBLE | 5.0 |
| 6 | Valentina Ríos | ¡Gracias, profe Paula! | 2026-10-15 12:00:00 | VISIBLE |  |
| 7 | Andrés Zapata | Con gusto, el semillero es de todos. | 2026-10-16 09:00:00 | VISIBLE |  |

---

## RegistroAuditoria

**Línea de vida:** 3

Objeto de dominio que representa el registro histórico e inmutable de una operación relevante realizada en la plataforma, que permite reconstruir quién hizo qué, cuándo y sobre qué.

### Responsabilidades

| Responsabilidad | Actor | Descripción | Entradas | Salida | Políticas | Evento |
|---|---|---|---|---|---|---|
| **Registrar una operación** | Sistema | El sistema guarda un registro por cada evento relevante del dominio. | evento | RegistroAuditoria | AUD-P-0001 | Operación auditada |
| **Consultar la auditoría** | Administrador / Rector / Coordinador Académico | Permite consultar el historial filtrando por fecha, actor, acción o entidad. | filtro | Lista<RegistroAuditoria> | AUD-P-0002 | Auditoría consultada |
| **Consultar mi historial de actividad** | Usuario autenticado | Permite a cada usuario ver sus propias acciones. | — | Lista<RegistroAuditoria> | AUD-P-0002 | Historial de actividad consultado |

**Hotspots (ver 6.4):**

- *Consultar la auditoría:* H-11: El enunciado no dice quién consulta la auditoría completa. → Administrador, Rector y Coordinador Académico; cada usuario ve su propio historial (RN-106).

### Atributos

| Atributo | Descripción | Tipo | Obligatorio | Longitud | Rango | Formato / reglas | Sensible |
|---|---|---|---|---|---|---|---|
| `id` | Representa el identificador único que le pertenece a cada registro de auditoría registrado en el sistema. | Identificador Único Universal | Sí | — | — | — | No |
| `fecha` | Representa cuándo ocurrió la operación. | Fecha y hora | Sí | — | — | AAAA-MM-DD HH:MM:SS | No |
| `actor` | Representa el usuario que realizó la operación; vacío si fue el sistema. | Usuario | No | — | — | — | No |
| `accion` | Representa el nombre del evento ocurrido (por ejemplo KUDO_VETADO). | Texto | Sí | — a 60 | — | — | No |
| `entidadAfectada` | Representa el tipo de objeto afectado. | Texto | Sí | — a 60 | — | — | No |
| `idEntidadAfectada` | Representa el identificador del objeto afectado. | Identificador Único Universal | Sí | — | — | — | No |
| `valorAnterior` | Representa el estado anterior del objeto, para reconstruir decisiones. | Texto ilimitado | No | — | — | — | No |
| `valorNuevo` | Representa el estado nuevo del objeto. | Texto ilimitado | No | — | — | — | No |

### Políticas

| Número | Descripción | Objeto responsable | Regla responsable | Origen (enunciado) |
|---|---|---|---|---|
| AUD-P-0001 | Asegurar que toda operación relevante quede registrada y que los registros sean inmutables. | RegistroAuditoria | — | RN-096 a RN-100; RN-250; POL-006; POL-007 |
| AUD-P-0002 | Asegurar que solo los autorizados consulten la auditoría completa y que cada usuario vea su propio historial. | Usuario | USU-P-0016 | RN-106; RF-070 |

### Muestreo de datos

| id | fecha | actor | accion | entidadAfectada | idEntidadAfectada | valorAnterior | valorNuevo |
|---|---|---|---|---|---|---|---|
| 1 | 2026-02-03 09:00:00 | Paula Ospina | USUARIO_APROBADO | SolicitudRegistro | 6 | PENDIENTE | APROBADA |
| 2 | 2026-10-15 10:00:00 | Andrés Zapata | KUDO_CREADO | Kudo | 1 |  | VISIBLE |
| 3 | 2026-11-02 09:00:00 | Carlos Restrepo | KUDO_VETADO | Kudo | 4 | EN_REVISION | VETADO |

---

## SolicitudRegistro

**Línea de vida:** 3

Objeto de dominio que representa la petición de una persona para ejercer un rol institucional, la cual debe ser aprobada o rechazada por el rol aprobador correspondiente dentro de su ámbito.

### Responsabilidades

| Responsabilidad | Actor | Descripción | Entradas | Salida | Políticas | Evento |
|---|---|---|---|---|---|---|
| **Crear una solicitud de registro** | Sistema | El sistema crea la solicitud al registrarse un usuario, con el ámbito correspondiente al rol. | usuario; ámbito {facultad \| programa} | SolicitudRegistro | SOL-P-0002, SOL-P-0003, SOL-P-0004, SOL-P-0001 | Solicitud de registro creada |
| **Consultar las solicitudes pendientes** | Rol aprobador (según matriz de aprobaciones) | Permite al aprobador ver solo las solicitudes que le corresponde decidir en su ámbito. | filtro | Lista<SolicitudRegistro> | SOL-P-0005 | Solicitudes consultadas |
| **Consultar una solicitud** | Solicitante / Rol aprobador (según matriz de aprobaciones) | Permite consultar una solicitud; el solicitante consulta la suya (estado de validaciones). | id | SolicitudRegistro | SOL-P-0001 | Solicitud consultada |
| **Aprobar una solicitud** | Rol aprobador (según matriz de aprobaciones) | Permite al rol aprobador aceptar la solicitud. Según el rol, se asigna la coordinación o se crea la vinculación al programa, y luego se intenta activar la cuenta. | id; observacion | SolicitudRegistro | SOL-P-0001, SOL-P-0007, SOL-P-0005, SOL-P-0006, SOL-P-0009, SOL-P-0010, SOL-P-0011 | Usuario aprobado (EVT-004; RN-214) |
| **Rechazar una solicitud** | Rol aprobador (según matriz de aprobaciones) | Permite al rol aprobador rechazar la solicitud con una justificación. | id; observacion | SolicitudRegistro | SOL-P-0001, SOL-P-0007, SOL-P-0005, SOL-P-0008, SOL-P-0011 | Solicitud de registro rechazada |

### Atributos

| Atributo | Descripción | Tipo | Obligatorio | Longitud | Rango | Formato / reglas | Sensible |
|---|---|---|---|---|---|---|---|
| `id` | Representa el identificador único que le pertenece a cada solicitud de registro registrado en el sistema. | Identificador Único Universal | Sí | — | — | — | No |
| `solicitante` | Representa la persona que solicita el registro. | Usuario | Sí | — | — | — | No |
| `rolSolicitado` | Representa el rol institucional que se solicita. | Rol | Sí | — | — | — | No |
| `facultad` | Representa la facultad del ámbito, obligatoria si el rol es Coordinador de Facultad. | Facultad | No | — | — | — | No |
| `programaAcademico` | Representa el programa del ámbito, obligatorio si el rol es Estudiante, Docente o Coordinador de Programa. | ProgramaAcademico | No | — | — | — | No |
| `estado` | Representa el estado de la solicitud. | Texto (valor fijo) | Sí | — | — | Valores permitidos: PENDIENTE, APROBADA, RECHAZADA Inicia en PENDIENTE. Solo cambia mediante las responsabilidades del objeto. | No |
| `fechaSolicitud` | Representa la fecha y hora en que se creó la solicitud. | Fecha y hora | Sí | — | — | AAAA-MM-DD HH:MM:SS | No |
| `aprobador` | Representa el usuario que decidió la solicitud. | Usuario | No | — | — | — | No |
| `fechaDecision` | Representa la fecha y hora de la decisión. | Fecha y hora | No | — | — | AAAA-MM-DD HH:MM:SS | No |
| `observacion` | Representa la justificación de la decisión; obligatoria al rechazar. | Texto | No | 10 a 250 | — | Se debe asegurar la sanitización del dato para garantizar que su contenido no incluya intentos de ataque o instrucciones maliciosas. | No |

**Combinaciones únicas:**

- Combinación única 1: Indica que un usuario no puede tener dos solicitudes PENDIENTES al mismo tiempo. (`solicitante`, `estado = PENDIENTE`)

### Políticas

| Número | Descripción | Objeto responsable | Regla responsable | Origen (enunciado) |
|---|---|---|---|---|
| SOL-P-0001 | Asegurar que la solicitud exista con el identificador indicado. | SolicitudRegistro | — | RN-085 |
| SOL-P-0002 | Asegurar que los datos requeridos (solicitante, rol solicitado) sean válidos y existan. | SolicitudRegistro | — | RF-012 |
| SOL-P-0003 | Asegurar que el ámbito corresponda al rol: programa para Estudiante, Docente y Coord. de Programa; facultad para Coord. de Facultad; ninguno para los demás. | SolicitudRegistro | — | RN-112 a RN-125 |
| SOL-P-0004 | Asegurar que el usuario no tenga otra solicitud pendiente. | SolicitudRegistro | — | RN-001; INV-005; INV-006 |
| SOL-P-0005 | Asegurar que solo decida el rol aprobador del rol solicitado y dentro de su ámbito (por ejemplo, el Coord. de Programa solo aprueba estudiantes de su programa). | Rol | ROL-P-0002 | RN-090 a RN-095; RN-112; RN-117 |
| SOL-P-0006 | Asegurar que ningún usuario apruebe su propia solicitud. | SolicitudRegistro | — | RN-108 |
| SOL-P-0007 | Asegurar que solo se decidan solicitudes en estado PENDIENTE. | SolicitudRegistro | — | RN-084 |
| SOL-P-0008 | Asegurar que todo rechazo tenga una observación. | SolicitudRegistro | — | POL-007 |
| SOL-P-0009 | Asegurar que no se apruebe un cargo único que ya tiene titular activo (Rector, Coord. Académico, coordinador de esa facultad o programa). | Usuario / Coordinaciones | USU-P-0017; CFA-P-0002; CPR-P-0002 | RN-005; RN-011; RN-017; RN-023 |
| SOL-P-0010 | Asegurar que al aprobar se cree la coordinación o la vinculación correspondiente al rol. | CoordinacionFacultad / CoordinacionPrograma / VinculacionPrograma | CFA-P-0001; CPR-P-0001; VIN-P-0001 | INV-009; INV-010; RN-053; RN-061 |
| SOL-P-0011 | Asegurar que toda aprobación o rechazo quede auditado. | RegistroAuditoria | AUD-P-0001 | RN-096; RNF-017 |

### Muestreo de datos

| id | solicitante | rolSolicitado | facultad | programaAcademico | estado | fechaSolicitud | aprobador | fechaDecision | observacion |
|---|---|---|---|---|---|---|---|---|---|
| 1 | Carlos Restrepo | RECTOR |  |  | APROBADA | 2026-01-06 09:00:00 | Laura Gómez | 2026-01-06 09:30:00 | Registrado por el Administrador. |
| 2 | Ana Martínez | COORDINADOR_ACADEMICO |  |  | APROBADA | 2026-01-07 10:00:00 | Carlos Restrepo | 2026-01-07 11:00:00 |  |
| 3 | Jorge Ramírez | COORDINADOR_FACULTAD | Ingeniería |  | APROBADA | 2026-01-08 10:00:00 | Ana Martínez | 2026-01-08 12:00:00 |  |
| 4 | Paula Ospina | COORDINADOR_PROGRAMA |  | Ingeniería de Sistemas | APROBADA | 2026-01-09 11:00:00 | Jorge Ramírez | 2026-01-09 15:00:00 |  |
| 5 | Andrés Zapata | DOCENTE |  | Ingeniería de Sistemas | APROBADA | 2026-01-20 14:00:00 | Paula Ospina | 2026-01-21 08:00:00 |  |
| 6 | Valentina Ríos | ESTUDIANTE |  | Ingeniería de Sistemas | APROBADA | 2026-02-02 15:00:00 | Paula Ospina | 2026-02-03 09:00:00 |  |
| 7 | Mateo Castaño | ESTUDIANTE |  | Ingeniería de Sistemas | PENDIENTE | 2026-11-10 16:00:00 |  |  |  |

---

## VerificacionContacto

**Línea de vida:** 3

Objeto de dominio que representa el proceso de validación de un correo institucional o de un número celular de un usuario mediante un código temporal.

### Responsabilidades

| Responsabilidad | Actor | Descripción | Entradas | Salida | Políticas | Evento |
|---|---|---|---|---|---|---|
| **Generar una verificación** | Sistema | El sistema genera un código y lo envía al correo o al celular. Anula las verificaciones pendientes anteriores del mismo tipo. | usuario; tipo | VerificacionContacto | VER-P-0003, VER-P-0004 | Código de verificación enviado |
| **Verificar un código** | Persona registrada | Permite al usuario confirmar su correo o celular con el código recibido; si se cumplen las tres condiciones se activa la cuenta. | tipo; codigo | VerificacionContacto | VER-P-0001, VER-P-0002, VER-P-0005, VER-P-0006 | Correo verificado / Celular verificado (EVT-002; EVT-003) |
| **Reenviar un código** | Persona registrada | Permite solicitar un nuevo código si el anterior expiró. | tipo | VerificacionContacto | VER-P-0003 | Código de verificación enviado |
| **Consultar mis verificaciones** | Persona registrada | Permite al usuario ver el estado de sus validaciones. | — | Lista<VerificacionContacto> | — | Verificaciones consultadas |

**Hotspots (ver 6.4):**

- *Generar una verificación:* H-10: ¿Se envían códigos reales por correo y SMS? → Por ahora el envío es simulado (el código queda en el log); luego se puede integrar un servicio real.

### Atributos

| Atributo | Descripción | Tipo | Obligatorio | Longitud | Rango | Formato / reglas | Sensible |
|---|---|---|---|---|---|---|---|
| `id` | Representa el identificador único que le pertenece a cada verificación registrado en el sistema. | Identificador Único Universal | Sí | — | — | — | No |
| `usuario` | Representa el usuario dueño del dato a verificar. | Usuario | Sí | — | — | — | No |
| `tipo` | Representa qué dato se verifica. | Texto (valor fijo) | Sí | — | — | CORREO, CELULAR | No |
| `valorVerificado` | Representa el correo o celular que se está validando. | Texto | Sí | — | — | — | No |
| `codigo` | Representa el código temporal enviado al usuario. | Texto numérico | Sí | 6 a 6 | — | 6 dígitos aleatorios | Sí |
| `fechaEnvio` | Representa cuándo se generó y envió el código. | Fecha y hora | Sí | — | — | AAAA-MM-DD HH:MM:SS | No |
| `fechaExpiracion` | Representa hasta cuándo es válido el código (parámetro MINUTOS_EXPIRACION_CODIGO). | Fecha y hora | Sí | — | — | AAAA-MM-DD HH:MM:SS | No |
| `fechaVerificacion` | Representa cuándo se verificó el dato. | Fecha y hora | No | — | — | AAAA-MM-DD HH:MM:SS | No |
| `estado` | Representa el estado de la verificación. | Texto (valor fijo) | Sí | — | — | Valores permitidos: PENDIENTE, VERIFICADA, EXPIRADA Inicia en PENDIENTE. Solo cambia mediante las responsabilidades del objeto. | No |

### Políticas

| Número | Descripción | Objeto responsable | Regla responsable | Origen (enunciado) |
|---|---|---|---|---|
| VER-P-0001 | Asegurar que exista una verificación PENDIENTE del tipo indicado para el usuario. | VerificacionContacto | — | RN-077; RN-078 |
| VER-P-0002 | Asegurar que el código ingresado coincida con el generado. | VerificacionContacto | — | RN-079; RN-080 |
| VER-P-0003 | Asegurar que solo exista una verificación pendiente por usuario y tipo; una nueva anula las anteriores. | VerificacionContacto | — | RN-110; RN-111 |
| VER-P-0004 | Asegurar que el código expire según el parámetro MINUTOS_EXPIRACION_CODIGO. | ParametroSistema | PAR-P-0001 | RNF-003 |
| VER-P-0005 | Asegurar que no se acepte un código expirado. | VerificacionContacto | — | POL-003 |
| VER-P-0006 | Asegurar que al verificar se actualice el usuario y se evalúe la activación de la cuenta. | Usuario | USU-P-0012 | RN-086; RN-087 |

### Muestreo de datos

| id | usuario | tipo | valorVerificado | codigo | fechaEnvio | fechaExpiracion | fechaVerificacion | estado |
|---|---|---|---|---|---|---|---|---|
| 1 | Valentina Ríos | CORREO | valentina.rios@uco.edu.co | ****** | 2026-02-02 15:00:00 | 2026-02-02 15:15:00 | 2026-02-02 15:05:00 | VERIFICADA |
| 2 | Valentina Ríos | CELULAR | 3007778899 | ****** | 2026-02-02 15:00:00 | 2026-02-02 15:15:00 | 2026-02-02 15:07:00 | VERIFICADA |
| 3 | Mateo Castaño | CORREO | mateo.castano@uco.edu.co | ****** | 2026-11-10 16:00:00 | 2026-11-10 16:15:00 | 2026-11-10 16:04:00 | VERIFICADA |
| 4 | Mateo Castaño | CELULAR | 3008889900 | ****** | 2026-11-10 16:00:00 | 2026-11-10 16:15:00 |  | EXPIRADA |

---

## VinculacionPrograma

**Línea de vida:** 3

Objeto de dominio que representa el periodo durante el cual un estudiante o docente pertenece a un programa académico, conservando el historial cuando se desvincula.

### Responsabilidades

| Responsabilidad | Actor | Descripción | Entradas | Salida | Políticas | Evento |
|---|---|---|---|---|---|---|
| **Vincular a un programa** | Sistema / Coordinador de Programa | Vincula un estudiante o docente a un programa (al aprobar su solicitud o cuando el coordinador agrega otro programa). | usuario; programaAcademico | VinculacionPrograma | VIN-P-0001, VIN-P-0002, VIN-P-0003, VIN-P-0004, VIN-P-0006 | Estudiante / Docente vinculado a programa (EVT-016; EVT-018) |
| **Desvincular de un programa** | Coordinador de Programa | Cierra el vínculo con fechaFin sin borrarlo. | id | VinculacionPrograma | VIN-P-0004, VIN-P-0005, VIN-P-0006 | Estudiante / Docente desvinculado de programa (EVT-017; EVT-019) |
| **Consultar vinculaciones** | Usuario autenticado | Permite saber en qué programas está un estudiante o docente, y quiénes pertenecen a un programa. (Cubre RF-027, RF-028, RF-032, RF-033, RF-072 y RF-073). | filtro {usuario \| programaAcademico} | Lista<VinculacionPrograma> | — | Vinculaciones consultadas |

### Atributos

| Atributo | Descripción | Tipo | Obligatorio | Longitud | Rango | Formato / reglas | Sensible |
|---|---|---|---|---|---|---|---|
| `id` | Representa el identificador único que le pertenece a cada vinculación registrado en el sistema. | Identificador Único Universal | Sí | — | — | — | No |
| `usuario` | Representa el estudiante o docente vinculado. | Usuario | Sí | — | — | — | No |
| `programaAcademico` | Representa el programa al que se vincula. | ProgramaAcademico | Sí | — | — | — | No |
| `fechaInicio` | Representa desde cuándo existe el vínculo. | Fecha y hora | Sí | — | — | AAAA-MM-DD HH:MM:SS | No |
| `fechaFin` | Representa cuándo terminó el vínculo; vacía significa vigente. | Fecha y hora | No | — | — | AAAA-MM-DD HH:MM:SS | No |

**Combinaciones únicas:**

- Combinación única 1: Indica que no puede existir más de un vínculo vigente del mismo usuario con el mismo programa. (`usuario`, `programaAcademico`, `fechaFin vacía`)

### Políticas

| Número | Descripción | Objeto responsable | Regla responsable | Origen (enunciado) |
|---|---|---|---|---|
| VIN-P-0001 | Asegurar que el usuario vinculado tenga rol ESTUDIANTE o DOCENTE. | Usuario | USU-P-0001 | RN-053; RN-061; RN-054; RN-055; RN-062; RN-063; INV-011; INV-014 |
| VIN-P-0002 | Asegurar que el programa exista y esté activo. | ProgramaAcademico | PRO-P-0001 | RN-046 |
| VIN-P-0003 | Asegurar que no exista otro vínculo vigente del mismo usuario con el mismo programa. | VinculacionPrograma | — | RN-056; RN-064 |
| VIN-P-0004 | Asegurar que el Coordinador de Programa solo gestione vínculos de su programa. | Usuario | USU-P-0016 | RN-112 a RN-115 |
| VIN-P-0005 | Asegurar que la desvinculación conserve el historial (se cierra, no se borra). | VinculacionPrograma | — | RN-057; RN-065; INV-013; INV-016; INV-015 |
| VIN-P-0006 | Asegurar que vinculaciones y desvinculaciones queden auditadas. | RegistroAuditoria | AUD-P-0001 | RN-116; INV-012 |

### Muestreo de datos

| id | usuario | programaAcademico | fechaInicio | fechaFin | Combinación única 1 |
|---|---|---|---|---|---|
| 1 | Valentina Ríos | Ingeniería de Sistemas | 2026-02-03 09:00:00 |  | Valentina-Sistemas |
| 2 | Valentina Ríos | Ingeniería Industrial | 2026-03-01 10:00:00 |  | Valentina-Industrial |
| 3 | Andrés Zapata | Ingeniería de Sistemas | 2026-01-21 08:00:00 |  | Andrés-Sistemas |
| 4 | Andrés Zapata | Ingeniería Industrial | 2026-01-21 08:00:00 | 2026-06-30 18:00:00 | Andrés-Industrial (histórico) |

---

## Calificacion

**Línea de vida:** 4

Objeto de dominio que representa la valoración de 1 a 5 estrellas que un usuario asigna a una publicación (Kudo o Réplica). Un usuario tiene una sola calificación activa por publicación.

### Responsabilidades

| Responsabilidad | Actor | Descripción | Entradas | Salida | Políticas | Evento |
|---|---|---|---|---|---|---|
| **Calificar una publicación** | Usuario autenticado | Permite asignar de 1 a 5 estrellas a un Kudo o Réplica. Si el usuario ya la había calificado, se actualiza la existente. | publicacion; estrellas | Calificacion | CAL-P-0001, PUB-P-0001, PUB-P-0005, CAL-P-0002, CAL-P-0003 | Calificación registrada (EVT-032; RN-236) |
| **Cambiar una calificación** | Usuario autenticado | Permite al usuario modificar las estrellas que ya asignó. | publicacion; estrellas | Calificacion | CAL-P-0001, CAL-P-0004, CAL-P-0003 | Calificación actualizada (EVT-033; RN-237) |
| **Consultar valoraciones** | Usuario autenticado | Muestra el promedio, la cantidad de calificaciones y la calificación propia de una publicación. (Cubre CU-009). | publicacion | Indicador {promedio, cantidad, miCalificacion} | — | Valoraciones consultadas |

### Atributos

| Atributo | Descripción | Tipo | Obligatorio | Longitud | Rango | Formato / reglas | Sensible |
|---|---|---|---|---|---|---|---|
| `id` | Representa el identificador único que le pertenece a cada calificación registrado en el sistema. | Identificador Único Universal | Sí | — | — | — | No |
| `publicacion` | Representa el Kudo o la Réplica calificada. | Publicacion | Sí | — | — | — | No |
| `autor` | Representa quién califica. | Usuario | Sí | — | — | — | No |
| `estrellas` | Representa la valoración asignada. | Entero | Sí | — | 1 a 5 | Solo enteros entre 1 y 5. | No |
| `fechaRegistro` | Representa cuándo se calificó por primera vez. | Fecha y hora | Sí | — | — | AAAA-MM-DD HH:MM:SS | No |
| `fechaModificacion` | Representa cuándo se cambió la calificación por última vez. | Fecha y hora | No | — | — | AAAA-MM-DD HH:MM:SS | No |

**Combinaciones únicas:**

- Combinación única 1: Indica que un usuario solo puede tener una calificación por publicación. (`publicacion`, `autor`)

### Políticas

| Número | Descripción | Objeto responsable | Regla responsable | Origen (enunciado) |
|---|---|---|---|---|
| CAL-P-0001 | Asegurar que las estrellas sean un entero entre 1 y 5. | Calificacion | — | RN-177; INV-030; RN-157; RN-171; RN-175; RN-176 |
| CAL-P-0002 | Asegurar que un usuario tenga una sola calificación activa por publicación. | Calificacion | — | RN-178 a RN-181; INV-031; INV-032; RN-184 |
| CAL-P-0003 | Asegurar que toda calificación o cambio recalcule el indicador y quede auditado. | Publicacion | PUB-P-0001 | RN-185; RN-238 |
| CAL-P-0004 | Asegurar que solo se cambie una calificación existente del propio usuario. | Calificacion | — | RN-183; RN-182 |

### Muestreo de datos

| id | publicacion | autor | estrellas | fechaRegistro | fechaModificacion | Combinación única 1 |
|---|---|---|---|---|---|---|
| 1 | Kudo 1 | Paula Ospina | 5 | 2026-10-15 11:05:00 |  | Kudo1-Paula |
| 2 | Kudo 1 | Jorge Ramírez | 4 | 2026-10-15 13:00:00 |  | Kudo1-Jorge |
| 3 | Kudo 2 | Ana Martínez | 4 | 2026-10-16 10:00:00 |  | Kudo2-Ana |
| 4 | Réplica 5 | Valentina Ríos | 5 | 2026-10-15 12:01:00 |  | Réplica5-Valentina |

---

## Kudo

**Línea de vida:** 4

Objeto de dominio que representa un reconocimiento formal que una persona de la comunidad otorga a otra, clasificado en una categoría. Es una Publicación y es la raíz de una conversación.

### Responsabilidades

| Responsabilidad | Actor | Descripción | Entradas | Salida | Políticas | Evento |
|---|---|---|---|---|---|---|
| **Publicar un Kudo** | Usuario autenticado | Permite reconocer a otra persona: elige destinatario, categoría y escribe el contenido. Queda VISIBLE para la comunidad. | datos {destinatario, categoria, contenido} | Kudo | PUB-P-0002, PUB-P-0003, KUD-P-0001, KUD-P-0002, KUD-P-0003, PUB-P-0004, KUD-P-0004 | Kudo creado (EVT-024; RN-228) |
| **Consultar un Kudo** | Usuario autenticado | Muestra el Kudo completo: autor, destinatario, fecha, categoría, contenido completo, réplicas y calificaciones. | id | Kudo | PUB-P-0001, KUD-P-0005, KUD-P-0004 | Kudo consultado (EVT-025; RN-229) |
| **Consultar los Kudos** | Usuario autenticado | Permite ver el muro de Kudos y filtrar por recibidos o emitidos por una persona, o por categoría. (Cubre CU-003, RF-048, RF-049 y RF-050). | filtro {destinatario \| autor \| categoria} | Lista<Kudo> | KUD-P-0005 | Kudos consultados |

**Hotspots (ver 6.4):**

- *Publicar un Kudo:* H-12: ¿Una persona puede darse un Kudo a sí misma? → No: el Kudo es 'de una persona hacia otra' (archivo 01, sección 8).; H-13: ¿Un Kudo o una Réplica se pueden editar o eliminar? → No: solo se vetan, para conservar la trazabilidad (RN-151, RN-154, RN-174).

### Atributos

| Atributo | Descripción | Tipo | Obligatorio | Longitud | Rango | Formato / reglas | Sensible |
|---|---|---|---|---|---|---|---|
| `(heredados)` | id, autor, contenido, fechaCreacion, estado y promedioEstrellas se heredan de Publicacion. | Publicacion | Sí | — | — | — | No |
| `destinatario` | Representa la persona reconocida. | Usuario | Sí | — | — | Debe ser distinto del autor. | No |
| `categoria` | Representa la categoría del reconocimiento al momento de crearlo. | Categoria | Sí | — | — | Solo categorías activas; se conserva aunque luego se desactive. | No |

### Políticas

| Número | Descripción | Objeto responsable | Regla responsable | Origen (enunciado) |
|---|---|---|---|---|
| KUD-P-0001 | Asegurar que el destinatario exista y tenga la cuenta activa. | Usuario | USU-P-0001 | RN-148; INV-021; RN-058; RN-059; RN-066; RN-067 |
| KUD-P-0002 | Asegurar que el autor y el destinatario sean personas distintas. | Kudo | — | Enunciado, sección 8 ('hacia otra persona'); Supuesto |
| KUD-P-0003 | Asegurar que la categoría exista y esté activa al crear el Kudo. | Categoria | CAT-P-0001 | RN-144; RN-149; INV-017; INV-019; INV-023 |
| KUD-P-0004 | Asegurar que la creación y consulta del Kudo generen eventos auditables y notifiquen al destinatario. | RegistroAuditoria | AUD-P-0001 | RN-160; RN-146 |
| KUD-P-0005 | Asegurar que los Kudos vetados no se muestren a la comunidad (solo a Rector y Coord. Académico). | Publicacion | PUB-P-0005 | RN-155; RN-205 |

### Muestreo de datos

| id (Publicacion) | autor | destinatario | categoria | estado |
|---|---|---|---|---|
| 1 | Andrés Zapata | Valentina Ríos | Excelencia académica | VISIBLE |
| 2 | Valentina Ríos | Andrés Zapata | Trabajo en equipo | VISIBLE |
| 3 | Paula Ospina | Andrés Zapata | Liderazgo | REPORTADO |
| 4 | Valentina Ríos | Mateo Castaño | Trabajo en equipo | VETADO |

---

## Reporte

**Línea de vida:** 4

Objeto de dominio que representa la solicitud de revisión que un usuario hace sobre una publicación que considera inapropiada, la cual es analizada y decidida por el Rector o el Coordinador Académico.

### Responsabilidades

| Responsabilidad | Actor | Descripción | Entradas | Salida | Políticas | Evento |
|---|---|---|---|---|---|---|
| **Reportar una publicación** | Usuario autenticado | Permite a cualquier usuario autenticado reportar un Kudo o Réplica indicando el motivo. | datos {publicacion, motivo, descripcion} | Reporte | PUB-P-0001, PUB-P-0005, RPT-P-0001, RPT-P-0002, RPT-P-0003, RPT-P-0008 | Kudo reportado / Réplica reportada (EVT-026; EVT-030; RN-230; RN-234) |
| **Consultar los reportes** | Rector / Coordinador Académico | Permite a las autoridades ver los reportes, filtrando por estado o publicación. (Cubre CU-012). | filtro | Lista<Reporte> | RPT-P-0004 | Reportes consultados |
| **Iniciar la revisión de un reporte** | Rector / Coordinador Académico | La autoridad toma el reporte para analizarlo; la publicación pasa a EN_REVISION. | id | Reporte | RPT-P-0004, RPT-P-0005, PUB-P-0004 | Revisión iniciada (EVT-035; RN-239) |
| **Aceptar un reporte** | Rector / Coordinador Académico | La autoridad confirma que el contenido incumple las políticas: el reporte queda ACEPTADO y se veta la publicación. | id; observacion | Reporte | RPT-P-0004, RPT-P-0006, RPT-P-0007, RPT-P-0009, RPT-P-0008 | Revisión cerrada / Contenido rechazado (EVT-036; EVT-038; RN-240) |
| **Rechazar un reporte** | Rector / Coordinador Académico | La autoridad concluye que el contenido no incumple: el reporte queda RECHAZADO y, si no quedan otros reportes abiertos, la publicación vuelve a VISIBLE. | id; observacion | Reporte | RPT-P-0004, RPT-P-0006, RPT-P-0007, RPT-P-0010, RPT-P-0008 | Revisión cerrada / Contenido aprobado (EVT-036; EVT-037; RN-242) |

**Hotspots (ver 6.4):**

- *Reportar una publicación:* H-14: ¿Un usuario puede reportar varias veces la misma publicación? → No mientras tenga un reporte abierto sobre ella.

### Atributos

| Atributo | Descripción | Tipo | Obligatorio | Longitud | Rango | Formato / reglas | Sensible |
|---|---|---|---|---|---|---|---|
| `id` | Representa el identificador único que le pertenece a cada reporte registrado en el sistema. | Identificador Único Universal | Sí | — | — | — | No |
| `publicacion` | Representa el Kudo o la Réplica reportada. | Publicacion | Sí | — | — | — | No |
| `reportante` | Representa quién reporta. | Usuario | Sí | — | — | — | No |
| `motivo` | Representa la causa del reporte. | Texto (valor fijo) | Sí | — | — | LENGUAJE_OFENSIVO, ACOSO, DISCRIMINACION, SPAM, INFORMACION_FALSA, OTRO | No |
| `descripcion` | Representa la explicación del reporte; obligatoria si el motivo es OTRO. | Texto | No | 10 a 500 | — | Se debe asegurar la sanitización del dato para garantizar que su contenido no incluya intentos de ataque o instrucciones maliciosas. | No |
| `fechaReporte` | Representa cuándo se reportó. | Fecha y hora | Sí | — | — | AAAA-MM-DD HH:MM:SS | No |
| `estado` | Representa el estado del reporte. | Texto (valor fijo) | Sí | — | — | Valores permitidos: PENDIENTE, EN_REVISION, ACEPTADO, RECHAZADO Inicia en PENDIENTE. Solo cambia mediante las responsabilidades del objeto. | No |
| `revisor` | Representa la autoridad que decidió el reporte. | Usuario | No | — | — | — | No |
| `fechaDecision` | Representa cuándo se decidió. | Fecha y hora | No | — | — | AAAA-MM-DD HH:MM:SS | No |
| `observacionDecision` | Representa la justificación de la decisión. | Texto | No | 10 a 500 | — | Se debe asegurar la sanitización del dato para garantizar que su contenido no incluya intentos de ataque o instrucciones maliciosas. | No |

**Combinaciones únicas:**

- Combinación única 1: Indica que un usuario no puede tener dos reportes pendientes sobre la misma publicación. (`publicacion`, `reportante`, `estado PENDIENTE/EN_REVISION`)

### Políticas

| Número | Descripción | Objeto responsable | Regla responsable | Origen (enunciado) |
|---|---|---|---|---|
| RPT-P-0001 | Asegurar que el motivo sea uno de los permitidos y que la descripción exista si el motivo es OTRO. | Reporte | — | RN-190; CU-010; RN-060; RN-068; RN-186 a RN-189 |
| RPT-P-0002 | Asegurar que un usuario no tenga dos reportes abiertos sobre la misma publicación. | Reporte | — | Supuesto |
| RPT-P-0003 | Asegurar que el primer reporte marque la publicación como REPORTADA. | Publicacion | PUB-P-0004 | RN-202; RN-207; RN-158; RN-172 |
| RPT-P-0004 | Asegurar que solo el Rector y el Coordinador Académico revisen y decidan reportes. | Usuario | USU-P-0016 | RN-243 a RN-248 |
| RPT-P-0005 | Asegurar que solo se inicie la revisión de reportes PENDIENTES. | Reporte | — | RN-211; RN-210 |
| RPT-P-0006 | Asegurar que solo se decidan reportes EN_REVISION y que la decisión sea aceptación o rechazo. | Reporte | — | RN-212 |
| RPT-P-0007 | Asegurar que toda decisión tenga una observación que permita reconstruirla. | Reporte | — | RN-250; POL-007 |
| RPT-P-0008 | Asegurar que todo reporte y decisión de moderación quede auditado. | RegistroAuditoria | AUD-P-0001 | RN-249; RNF-016 |
| RPT-P-0009 | Asegurar que un reporte aceptado produzca el veto de la publicación. | Veto | VET-P-0001 | RN-204; RN-209 |
| RPT-P-0010 | Asegurar que una publicación sin reportes abiertos vuelva a VISIBLE. | Publicacion | PUB-P-0004 | RN-242 |

### Muestreo de datos

| id | publicacion | reportante | motivo | descripcion | fechaReporte | estado | revisor | fechaDecision | observacionDecision |
|---|---|---|---|---|---|---|---|---|---|
| 1 | Kudo 3 | Valentina Ríos | INFORMACION_FALSA |  | 2026-10-21 08:00:00 | PENDIENTE |  |  |  |
| 2 | Kudo 4 | Andrés Zapata | LENGUAJE_OFENSIVO |  | 2026-10-29 07:30:00 | ACEPTADO | Carlos Restrepo | 2026-11-02 09:00:00 | El contenido incumple la política de respeto institucional. |

---

## Replica

**Línea de vida:** 5

Objeto de dominio que representa una intervención en la conversación de un Kudo, que responde a un Kudo o a otra Réplica. Es una Publicación y forma un árbol de conversación sin límite de profundidad.

### Responsabilidades

| Responsabilidad | Actor | Descripción | Entradas | Salida | Políticas | Evento |
|---|---|---|---|---|---|---|
| **Crear una réplica sobre un Kudo** | Usuario autenticado | Permite agregar una intervención directamente a un Kudo. | datos {padre (Kudo), contenido} | Replica | PUB-P-0002, PUB-P-0003, RPL-P-0001, PUB-P-0005, RPL-P-0002, RPL-P-0004 | Réplica creada (EVT-028; RN-232) |
| **Responder una réplica** | Usuario autenticado | Permite responder a otra réplica, generando una réplica hija. | datos {padre (Réplica), contenido} | Replica | PUB-P-0002, PUB-P-0003, RPL-P-0001, PUB-P-0005, RPL-P-0002, RPL-P-0003, RPL-P-0004 | Réplica respondida (EVT-029; RN-233) |
| **Consultar la conversación** | Usuario autenticado | Muestra el árbol completo de réplicas de un Kudo, ordenado por fecha y anidado por padre. (Cubre CU-006 y RF-055). | kudo | Árbol<Replica> | PUB-P-0001 | Conversación consultada |

**Hotspots (ver 6.4):**

- *Crear una réplica sobre un Kudo:* H-13: ¿Un Kudo o una Réplica se pueden editar o eliminar? → No: solo se vetan, para conservar la trazabilidad (RN-151, RN-154, RN-174).

### Atributos

| Atributo | Descripción | Tipo | Obligatorio | Longitud | Rango | Formato / reglas | Sensible |
|---|---|---|---|---|---|---|---|
| `(heredados)` | id, autor, contenido, fechaCreacion, estado y promedioEstrellas se heredan de Publicacion. | Publicacion | Sí | — | — | — | No |
| `padre` | Representa la publicación a la que responde (un Kudo u otra Réplica). | Publicacion | Sí | — | — | — | No |
| `kudoRaiz` | Representa el Kudo al que pertenece toda la conversación. | Kudo | Sí | — | — | Se calcula: si el padre es un Kudo, es ese Kudo; si es una Réplica, es el kudoRaiz del padre. | No |

### Políticas

| Número | Descripción | Objeto responsable | Regla responsable | Origen (enunciado) |
|---|---|---|---|---|
| RPL-P-0001 | Asegurar que el padre exista y sea un Kudo o una Réplica. | Publicacion | PUB-P-0001 | INV-026; INV-027; RN-156; RN-161; RN-162 |
| RPL-P-0002 | Asegurar que el kudoRaiz se derive del padre, para que toda réplica pertenezca a la conversación de un único Kudo. | Replica | — | INV-029 |
| RPL-P-0003 | Asegurar que no haya límite de profundidad ni de cantidad de réplicas, y que el árbol no tenga ciclos (una réplica solo puede responder a publicaciones que ya existen, nunca a sí misma). | Replica | — | RN-163 a RN-167; INV-028; INV-029 |
| RPL-P-0004 | Asegurar que la creación de réplicas notifique al autor del padre y quede auditada. | RegistroAuditoria | AUD-P-0001 | RN-174; RN-232; RN-233 |

### Muestreo de datos

| id (Publicacion) | autor | padre | kudoRaiz | estado |
|---|---|---|---|---|
| 5 | Paula Ospina | Kudo 1 | Kudo 1 | VISIBLE |
| 6 | Valentina Ríos | Réplica 5 | Kudo 1 | VISIBLE |
| 7 | Andrés Zapata | Kudo 2 | Kudo 2 | VISIBLE |

---

## Veto

**Línea de vida:** 5

Objeto de dominio que representa la decisión institucional del Rector o del Coordinador Académico de inhabilitar una publicación por incumplir las políticas, conservando la evidencia de la decisión.

### Responsabilidades

| Responsabilidad | Actor | Descripción | Entradas | Salida | Políticas | Evento |
|---|---|---|---|---|---|---|
| **Vetar una publicación** | Rector / Coordinador Académico | Permite al Rector o al Coordinador Académico vetar un Kudo o Réplica, con o sin reporte previo. La publicación pasa a VETADO. | datos {publicacion, justificacion, reporteOrigen?} | Veto | VET-P-0001, PUB-P-0001, VET-P-0002, VET-P-0003, PUB-P-0004, VET-P-0004, VET-P-0005 | Kudo vetado / Réplica vetada (EVT-027; EVT-031; RN-231; RN-235; RN-241) |
| **Consultar el histórico de vetos** | Rector / Coordinador Académico | Permite ver las decisiones de moderación tomadas. (Cubre CU-015 y RF-068). | filtro | Lista<Veto> | VET-P-0001 | Histórico de vetos consultado |

**Hotspots (ver 6.4):**

- *Vetar una publicación:* H-15: ¿Una publicación puede tener varios vetos o revertirse? → Máximo un veto y no se revierte; la liberación solo ocurre cuando se rechaza un reporte antes del veto.

### Atributos

| Atributo | Descripción | Tipo | Obligatorio | Longitud | Rango | Formato / reglas | Sensible |
|---|---|---|---|---|---|---|---|
| `id` | Representa el identificador único que le pertenece a cada veto registrado en el sistema. | Identificador Único Universal | Sí | — | — | — | No |
| `publicacion` | Representa el Kudo o la Réplica vetada. | Publicacion | Sí | — | — | — | No |
| `autoridad` | Representa quién vetó (Rector o Coordinador Académico). | Usuario | Sí | — | — | — | No |
| `reporteOrigen` | Representa el reporte que originó el veto, si existe. | Reporte | No | — | — | — | No |
| `justificacion` | Representa la razón institucional del veto. | Texto | Sí | 20 a 1000 | — | Se debe asegurar la sanitización del dato para garantizar que su contenido no incluya intentos de ataque o instrucciones maliciosas. | No |
| `fechaVeto` | Representa cuándo se vetó. | Fecha y hora | Sí | — | — | AAAA-MM-DD HH:MM:SS | No |

**Combinaciones únicas:**

- Combinación única 1: Indica que una publicación solo puede tener un veto. (`publicacion`)

### Políticas

| Número | Descripción | Objeto responsable | Regla responsable | Origen (enunciado) |
|---|---|---|---|---|
| VET-P-0001 | Asegurar que solo el Rector o el Coordinador Académico, con cuenta activa, puedan vetar. | Usuario | USU-P-0016 | RN-193 a RN-200; RN-243; INV-033; RN-159; RN-173; RN-191; RN-192 |
| VET-P-0002 | Asegurar que una publicación tenga como máximo un veto. | Veto | — | Supuesto |
| VET-P-0003 | Asegurar que todo veto tenga una justificación que permita reconstruir la decisión. | Veto | — | RN-250; INV-035 |
| VET-P-0004 | Asegurar que al vetar se cierren los reportes abiertos de esa publicación. | Reporte | RPT-P-0006 | RN-212 |
| VET-P-0005 | Asegurar que todo veto quede auditado y se notifique al autor. | RegistroAuditoria | AUD-P-0001 | RN-249; INV-034; RNF-016 |

### Muestreo de datos

| id | publicacion | autoridad | reporteOrigen | justificacion | fechaVeto | Combinación única 1 |
|---|---|---|---|---|---|---|
| 1 | Kudo 4 | Carlos Restrepo | Reporte 2 | El contenido incumple la política de respeto institucional. | 2026-11-02 09:00:00 | Kudo 4 |

---

## 6.5 Justificación del autor (retroalimentación)

> Preguntas de autoevaluación planteadas con apoyo de IA a partir del enunciado. Respuestas propias, revisadas y corregidas.

**1. ¿Qué diferencia hay entre el modelo anémico y el enriquecido?**
El anémico muestra la **estructura** del proyecto (entidades, atributos, relaciones). El enriquecido muestra el **comportamiento** de cada objeto: qué hace (responsabilidades), qué información necesita, con qué objetos colabora, qué reglas protege y qué eventos genera (archivo 01, sección 12).

**2. ¿Cómo funciona la responsabilidad "Publicar un Kudo"?**
La ejecuta un **usuario autenticado** para reconocer a otra persona. Necesita la lista de **categorías activas** y la lista de **usuarios** para elegir el destinatario. Antes de crear el Kudo valida: que el autor tenga la cuenta activa (PUB-P-0002), que el contenido sea válido y esté sanitizado (PUB-P-0003), que el destinatario exista y esté activo (KUD-P-0001), que autor y destinatario sean distintos (KUD-P-0002) y que la categoría exista **y esté activa** (KUD-P-0003). Luego pasa de CREADO a VISIBLE (PUB-P-0004), notifica al destinatario y queda en auditoría (KUD-P-0004).

**3. ¿En qué se diferencia una política de una regla de negocio?**
La **regla de negocio** (RN) es lo que la universidad exige, en lenguaje del negocio, y viene del enunciado. La **política** es cómo el diseño garantiza esa regla: qué objeto la valida, qué excepción produce y qué se hace después. Ejemplo: RN-144 ("las categorías desactivadas no se usan para nuevos Kudos") se garantiza con KUD-P-0003 ("Asegurar que la categoría exista y esté activa"). Una regla puede producir varias políticas; por eso cada política indica su **origen**.

**4. ¿Qué es un evento y por qué se nombra en pasado?**
Un evento es **un hecho que ya ocurrió** en el dominio y que a otros les interesa conocer; por eso se nombra en pasado y no se puede borrar. No es un actor: los actores ejecutan **acciones**, y las acciones producen eventos. Ejemplo: cuando ocurre **"Kudo creado"**, reaccionan la **Notificación** (avisa al destinatario) y el **RegistroAuditoria** (guarda la traza). Se implementa con el patrón **Observer**.

**5. ¿Qué pasa si una autoridad rechaza un reporte?**
El reporte queda **RECHAZADO** con su observación, y la publicación vuelve a **VISIBLE** siempre que no tenga otros reportes abiertos (RPT-P-0010). Si se acepta, la publicación se **veta** (RPT-P-0009).
