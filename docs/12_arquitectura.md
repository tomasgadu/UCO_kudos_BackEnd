# 12. Arquitectura — UCO Kudos

## 12.1 Vista general

La solución tiene dos aplicaciones independientes, cada una en su repositorio:

```
┌───────────────────────┐   HTTP + JSON    ┌───────────────────────────┐   JDBC   ┌──────────────┐
│ Front-End             │ ───────────────► │ Back-End (API REST)        │ ───────► │ PostgreSQL   │
│ HTML + CSS + JS       │ ◄─────────────── │ Spring Boot, puerto 8080   │ ◄─────── │ esquema kudos│
│ Live Server :5173     │  Bearer token    │                            │          │              │
└───────────────────────┘                  └───────────────────────────┘          └──────────────┘
```

| Repositorio | Contenido |
|---|---|
| `UCO_kudos_BackEnd` | API REST, base de datos (`db/`) y documentación (`docs/`) |
| `UCO_kudos_FrontEnd` | Páginas web que consumen la API |

## 12.2 Back-End: arquitectura en capas

```
Controlador  →  Fachada  →  Negocio  →  Dominio
                                ↓
                               DAO  →  PostgreSQL
           Transversal (utilitarios, excepciones, mensajes) — usada por todas
```

| Capa | Paquete | Responsabilidad | No hace |
|---|---|---|---|
| **Controlador** | `controlador` | Recibe la petición HTTP, obtiene el usuario de la sesión, llama a la fachada y arma la respuesta `{ mensajes, datos, estado }`. Traduce excepciones a códigos HTTP. | Reglas de negocio ni SQL |
| **Fachada** | `negocio.fachada` | Punto de entrada de cada caso de uso. Abre la conexión, maneja la **transacción** (confirmar o deshacer) y convierte DTO ↔ dominio. | Decidir reglas |
| **Negocio** | `negocio.negocio` | Orquesta el caso de uso: aplica reglas, usa el dominio, guarda con los DAOs y publica eventos. | Saber de HTTP |
| **Dominio** | `dominio` | Objetos con datos + comportamiento + invariantes (sección 9). | Saber de base de datos o HTTP |
| **DAO** | `dao` | Acceso a datos con JDBC y SQL a mano. Un DAO por tabla. | Reglas de negocio |
| **Transversal** | `transversal` | Utilitarios (`UtilTexto`, `UtilUUID`, `UtilFecha`, `UtilClave`), excepciones por capa y `CatalogoMensajes`. | — |

**Regla de dependencias:** cada capa solo conoce a la de abajo. El dominio no depende de nadie, así que se puede probar sin base de datos ni Spring.

### Objetos que viajan entre capas

| Objeto | Capa | Para qué |
|---|---|---|
| **DTO** (`dto`) | Controlador ↔ Fachada | Lo que entra y sale por la API (JSON) |
| **Dominio** (`dominio`) | Fachada ↔ Negocio | Lógica y reglas |
| **Entidad** (`entidad`) | Negocio ↔ DAO | Espejo de una fila de la tabla |
| **Ensambladores** | Fachada y Negocio | Convierten DTO ↔ Dominio ↔ Entidad |

Separar DTO y Entidad permite cambiar la base de datos sin cambiar el contrato de la API, y al revés.

## 12.3 Recorrido de una petición: dar un Kudo

1. **Front:** `api.post("/kudos", { idDestinatario, idCategoria, contenido })` con `Authorization: Bearer <token>`.
2. **`KudoControlador`:** obtiene el id del autor desde el token (`GestorSesiones`) y llama a `KudoFachadaImpl.crearKudo`.
3. **`FachadaBase.ejecutarEnTransaccion`:** pide una conexión a `DAOFactory`, inicia la transacción y convierte el DTO a dominio.
4. **`KudoNegocioImpl`:** valida que el autor esté activo, que el destinatario exista y que la categoría esté activa; sanea el contenido; llama a `KudoDominio.crear(...)` y luego a `publicar()`.
5. **DAO:** inserta en `publicacion` y en `kudo` (herencia por tablas, en la misma transacción).
6. **Eventos:** `PublicadorEventos` avisa a `AuditoriaSuscriptor` (registro de auditoría) y a `NotificacionSuscriptor` (aviso al destinatario).
7. **Fachada:** confirma la transacción. Si algo falló en cualquier paso, la deshace: no queda nada a medias.
8. **Controlador:** responde `200` con el mensaje "Kudo creado" y el Kudo en `datos`.

El diagrama de secuencia de este flujo está en la [sección 10.3](10_diagramas_uml.md).

## 12.4 Manejo de errores

Cada capa tiene su excepción (`KudosDominioExcepcion`, `KudosNegocioExcepcion`, `KudosDatosExcepcion`, `KudosFachadaExcepcion`…), todas hijas de `KudosExcepcion`, con un **mensaje para el usuario** y un **mensaje técnico**.

`AbstractRestController` las convierte en respuestas HTTP:

| Situación | Código |
|---|---|
| Sin sesión o token inválido (`KudosSeguridadExcepcion`) | 401 |
| Regla de negocio incumplida, dato inválido o rol sin permiso (`KudosExcepcion`) | 400 |
| JSON mal formado o UUID inválido | 400 |
| Método HTTP no permitido | 405 |
| Ruta inexistente | 404 |
| Cualquier otro error | 500, con un código de seguimiento en el log |

El usuario siempre ve un mensaje claro; el detalle técnico queda solo en el log.

## 12.5 Seguridad

| Aspecto | Cómo se resuelve |
|---|---|
| Contraseñas | Cifradas con **BCrypt** (`UtilClave`); nunca se guardan ni se devuelven en texto plano. |
| Sesión | Al iniciar sesión se crea un token aleatorio guardado en memoria (`GestorSesiones`), vigente 8 horas. Se envía como `Authorization: Bearer <token>`. |
| Inicio de sesión con Google | El Front obtiene un token de Google (Google Identity Services) y lo envía a `POST /sesiones/google`. `VerificadorTokenGoogle` lo valida con Google (`tokeninfo`), comprueba que sea para el ID de cliente de la aplicación (`KUDOS_GOOGLE_CLIENT_ID`) y que el correo esté verificado. Después se aplican las mismas validaciones del inicio de sesión normal (usuario registrado y cuenta activa). |
| Permisos | Cada operación protegida llama a `AsegurarUsuarioTienePermisoRegla` con el permiso que exige. |
| CORS | Solo se aceptan peticiones del origen del Front (`kudos.cors.origen-permitido`). |
| Contenido | Se quitan etiquetas HTML (`SanearContenidoPublicacionRegla`) y el Front muestra todo con `textContent` (contra XSS). |
| Logs | Se limpian los saltos de línea antes de escribir datos del usuario (contra *Log Injection*). |
| Credenciales de la base de datos | En variables de entorno, nunca en el repositorio. |

## 12.6 Front-End

| Archivo | Responsabilidad |
|---|---|
| `js/api.js` | Único punto que llama al Back (`fetch`, token, errores) |
| `js/sesion.js` | Token y usuario en `sessionStorage`; permisos para el menú |
| `js/ui.js` | Funciones de pantalla reutilizables |
| `js/menu.js` | Menú según el rol |
| `js/<página>.js` | Lógica de cada pantalla |

El Front solo **muestra u oculta** opciones según el rol. Todas las validaciones y permisos se aplican otra vez en el Back.

## 12.7 Decisiones de arquitectura

| Decisión | Alternativa descartada | Por qué |
|---|---|---|
| JDBC con DAOs propios | JPA / Hibernate | Es el estilo visto en el curso; se controla cada consulta y se entiende qué SQL se ejecuta. |
| Transacción en la fachada | Una transacción por DAO | Un caso de uso (por ejemplo, aceptar un reporte → vetar → notificar) se guarda completo o no se guarda. |
| Sesiones en memoria | JWT | Más simple de explicar; cerrar sesión borra el token al instante. Limitación: al reiniciar el Back se pierden las sesiones. |
| Front sin framework | Angular / React | Prioridad en el funcionamiento; cada línea se puede explicar. |
| Dos repositorios | Uno solo | Así lo pide el enunciado; cada aplicación se despliega por separado. |
