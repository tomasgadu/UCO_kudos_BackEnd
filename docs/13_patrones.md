# 13. Patrones utilizados — UCO Kudos

Cada patrón se usó para resolver un problema concreto del proyecto, no por usarlo.

## 13.1 Patrones de diseño (GoF)

| Patrón | Tipo | Dónde | Problema que resuelve |
|---|---|---|---|
| **Template Method** | Comportamiento | `FachadaBase.ejecutarEnTransaccion` y `ejecutarConsulta` | Todas las fachadas repetían lo mismo: abrir conexión, iniciar transacción, confirmar, deshacer si falla y cerrar. La plantilla tiene esos pasos fijos y cada fachada solo aporta la operación (una lambda). |
| **Abstract Factory** | Creación | `DAOFactory` + `PostgreSqlDAOFactory` + `FactoriaEnum` | El negocio pide `daoFactory.obtenerKudoDAO()` sin saber que la base es PostgreSQL. Cambiar de motor = crear otra fábrica, sin tocar el negocio. |
| **Builder** | Creación | `XxxDominio.Builder` en todo el dominio | Objetos inmutables con muchos atributos, sin constructores de 10 parámetros. |
| **Observer** | Comportamiento | `PublicadorEventos`, `SuscriptorEvento`, `AuditoriaSuscriptor`, `NotificacionSuscriptor` | El negocio publica "Kudo creado" y no sabe quién reacciona. Agregar una reacción nueva (por ejemplo, un correo) = otro suscriptor, sin tocar el negocio. |
| **Singleton** | Creación | `UtilTexto.getUtilTexto()`, las reglas (`obtenerInstancia()`), `GestorSesiones`, los ensambladores | Clases sin estado propio, o con un estado que debe ser único (las sesiones). Una sola instancia evita crear objetos repetidos. |
| **Strategy** | Comportamiento | Interfaz `Regla<T>` con una clase por regla | Cada validación es una clase intercambiable con el mismo método `ejecutar()`. El negocio las combina como piezas. |
| **Facade** | Estructura | Capa `negocio.fachada` | El controlador ve una sola operación (`crearKudo`) y no los pasos internos. |
| **Polimorfismo** (herencia) | — | `PublicacionDominio` → `KudoDominio`, `ReplicaDominio` | Calificar, reportar y vetar funcionan igual para Kudos y réplicas. `obtenerKudoRaiz()` y `conEstado()` se resuelven en cada hija. |

## 13.2 Patrones de arquitectura y de datos

| Patrón | Dónde | Para qué |
|---|---|---|
| **Arquitectura en capas** | Controlador, Fachada, Negocio, Dominio, DAO | Separar responsabilidades (sección 12) |
| **DAO** (Data Access Object) | `dao.datos.entidad.postgresql` | Aislar el SQL del resto del código |
| **DTO** (Data Transfer Object) | `dto` | Definir lo que entra y sale por la API sin exponer el dominio |
| **Assembler / Mapper** | `EnsambladorDTO`, `EnsambladorEntidad` | Convertir entre DTO, Dominio y Entidad en un solo lugar |
| **Herencia por tablas** (*class table inheritance*) | Tablas `publicacion` + `kudo` / `replica` | Guardar la herencia del dominio en la base relacional |
| **Catálogo de mensajes** | `CatalogoMensajes` | Todos los textos en un lugar; nada de textos sueltos en el código |

## 13.3 Principios SOLID

| Principio | Ejemplo en el código |
|---|---|
| **S** — Responsabilidad única | Cada regla valida una sola cosa (`AsegurarNombreCategoriaNoExistaRegla`). Cada capa tiene un solo trabajo. |
| **O** — Abierto/cerrado | Un suscriptor nuevo se agrega sin modificar `PublicadorEventos`; un motor de base de datos nuevo, sin modificar el negocio. |
| **L** — Sustitución de Liskov | Donde se espera una `PublicacionDominio` sirve un Kudo o una Réplica (`GestorPublicaciones`, calificaciones, vetos). |
| **I** — Segregación de interfaces | DAOs con interfaces pequeñas (`CrearDAO`, `ConsultarDAO`, `ActualizarDAO`): cada DAO implementa solo lo que necesita. |
| **D** — Inversión de dependencias | El negocio depende de `DAOFactory` (abstracción), no de `PostgreSqlDAOFactory`. |

## 13.4 Otros principios

| Principio | Ejemplo |
|---|---|
| **DRY** | `Regla.validarCantidadDatos`, `FachadaBase`, `api.js` en el Front |
| **KISS** | Una sola universidad (sin entidad `Universidad`); Front sin framework |
| **Fail fast** | Las reglas se validan antes de tocar la base de datos |
| **Inmutabilidad** | Dominio sin *setters*; cada cambio crea un objeto nuevo |
