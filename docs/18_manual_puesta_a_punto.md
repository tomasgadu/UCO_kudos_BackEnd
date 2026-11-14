# Manual de puesta a punto — UCO Kudos

Pasos para ejecutar la solución completa desde cero en un computador con Windows. En Linux o macOS los pasos son los mismos; solo cambian los comandos de la terminal donde se indica.

| Sección | Requisito de la rúbrica |
|---|---|
| [1. Requisitos previos](#1-requisitos-previos) | MAN-001 |
| [2. Instalación](#2-instalación) | MAN-002 |
| [3. Configuración de la base de datos](#3-configuración-de-la-base-de-datos) | MAN-003 |
| [4. Variables de entorno](#4-variables-de-entorno) | MAN-004 |
| [5. Ejecución](#5-ejecución) | MAN-005 |
| [6. Pruebas](#6-pruebas) | MAN-006 |
| [7. Despliegue](#7-despliegue) | MAN-007 |
| [8. Problemas frecuentes](#8-problemas-frecuentes) | — |

---

## 1. Requisitos previos

| Herramienta | Versión | Para qué |
|---|---|---|
| Git | Cualquiera reciente | Descargar los repositorios |
| JDK (Java) | 27 (la versión definida en `pom.xml`) | Compilar y ejecutar el Back-End |
| PostgreSQL | 16 | Base de datos (incluye *pgAdmin* y *psql*) |
| Eclipse IDE (o Spring Tool Suite) | 2025 o posterior | Ejecutar el Back-End y las pruebas |
| Visual Studio Code | Cualquiera reciente | Ejecutar el Front-End |
| Extensión **Live Server** (Ritwick Dey) | — | Servir el Front-End en el puerto 5173 |
| Bruno *(opcional)* | — | Probar la API directamente |
| Navegador | Chrome, Edge o Firefox | Usar la aplicación |

Maven **no** es necesario instalarlo: el proyecto trae el *Maven Wrapper* (`mvnw` / `mvnw.cmd`).

Puertos que deben estar libres: **5432** (PostgreSQL), **8080** (Back-End) y **5173** (Front-End).

---

## 2. Instalación

### 2.1 Descargar los repositorios

En una terminal, dentro de la carpeta donde se quiere trabajar:

```
git clone https://github.com/tomasgadu/UCO_kudos_BackEnd.git
git clone https://github.com/tomasgadu/UCO_kudos_FrontEnd.git
```

### 2.2 Importar el Back-End en Eclipse

1. *File → Import → Maven → Existing Maven Projects*.
2. En *Root Directory* seleccionar la carpeta `UCO_kudos_BackEnd` → *Finish*.
3. Esperar a que Maven descargue las dependencias (barra inferior derecha).
4. Si Eclipse marca errores de versión de Java: clic derecho en el proyecto → *Properties → Java Build Path → Libraries* y seleccionar el JDK 27.

### 2.3 Abrir el Front-End

1. En Visual Studio Code: *File → Open Folder → UCO_kudos_FrontEnd*.
2. Instalar la extensión **Live Server** si no está instalada.

El Front-End no necesita instalar dependencias: son archivos HTML, CSS y JavaScript.

---

## 3. Configuración de la base de datos

### 3.1 Crear la base de datos

Con *pgAdmin*: clic derecho en *Databases → Create → Database…* → nombre `kudos` → *Save*.

O con *psql*:

```
psql -U postgres -c "CREATE DATABASE kudos;"
```

### 3.2 Ejecutar los scripts

Los scripts están en `UCO_kudos_BackEnd/db/` y se ejecutan **en este orden**:

| Orden | Script | Qué hace |
|---|---|---|
| 1 | `01_esquema_kudos.sql` | Crea el esquema `kudos` con todas las tablas, llaves y restricciones (borra el esquema si ya existía) |
| 2 | `02_datos_muestreo_kudos.sql` | Carga los datos de prueba: roles, facultades, programas, usuarios, categorías, Kudos y reportes |
| 3 *(opcional)* | `03_pruebas_restricciones.sql` | Comprueba que la base rechaza datos inválidos (llaves, únicos, chequeos) |

Con *pgAdmin*: seleccionar la base `kudos` → *Query Tool* → abrir cada script → *Execute* (F5).

Con *psql*, desde la carpeta `UCO_kudos_BackEnd`:

```
psql -U postgres -d kudos -f db/01_esquema_kudos.sql
psql -U postgres -d kudos -f db/02_datos_muestreo_kudos.sql
```

Para volver a dejar la base como al inicio, basta con ejecutar otra vez los scripts 1 y 2.

### 3.3 Usuarios de prueba

Todos usan la clave **`Kudos2026*`**, excepto el Administrador, que usa **`Kudos2026`**.

| Usuario | Correo | Rol | Para probar |
|---|---|---|---|
| Tomás Gallo | `tomas.gallo@uco.edu.co` | Administrador | Administración, auditoría |
| Carlos Restrepo | `carlos.restrepo@uco.edu.co` | Rector | Moderación y vetos |
| Paula Ospina | `paula.ospina@uco.edu.co` | Coordinador de Programa (Ing. de Sistemas) | Aprobar estudiantes |
| Valentina Ríos | `valentina.rios@uco.edu.co` | Estudiante | Dar y recibir Kudos |
| Mateo Castaño | `mateo.castano@uco.edu.co` | Estudiante con cuenta pendiente | Flujo de aprobación y verificación |

---

## 4. Variables de entorno

La conexión a la base de datos **no** está escrita en el código ni en `application.properties`: se lee de variables de entorno, para que la clave nunca quede en el repositorio.

| Variable | Valor de ejemplo | Descripción |
|---|---|---|
| `KUDOS_DB_URL` | `jdbc:postgresql://localhost:5432/kudos?currentSchema=kudos` | Dirección de la base de datos y esquema |
| `KUDOS_DB_USUARIO` | `postgres` | Usuario de PostgreSQL |
| `KUDOS_DB_CLAVE` | *(la clave de tu PostgreSQL)* | Clave de ese usuario |
| `KUDOS_GOOGLE_CLIENT_ID` | `xxxx.apps.googleusercontent.com` | ID de cliente de Google para iniciar sesión con Google (ver 4.5). Sin ella, el inicio con Google responde un error controlado y el resto funciona igual. |

### 4.1 En Eclipse

1. *Run → Run Configurations… → Spring Boot App* (o *Java Application*) → `UcoKudosBackEndApplication`.
2. Pestaña **Environment** → *Add…* y agregar las cuatro variables.
3. *Apply*.

### 4.2 En la terminal (para ejecutar con Maven o el `.jar`)

Windows (PowerShell), válido para esa ventana:

```
$env:KUDOS_DB_URL="jdbc:postgresql://localhost:5432/kudos?currentSchema=kudos"
$env:KUDOS_DB_USUARIO="postgres"
$env:KUDOS_DB_CLAVE="tu_clave"
$env:KUDOS_GOOGLE_CLIENT_ID="tu_id_de_cliente.apps.googleusercontent.com"
```

Linux / macOS:

```
export KUDOS_DB_URL="jdbc:postgresql://localhost:5432/kudos?currentSchema=kudos"
export KUDOS_DB_USUARIO="postgres"
export KUDOS_DB_CLAVE="tu_clave"
export KUDOS_GOOGLE_CLIENT_ID="tu_id_de_cliente.apps.googleusercontent.com"
```

### 4.3 Otros parámetros (`src/main/resources/application.properties`)

| Propiedad | Valor | Para qué |
|---|---|---|
| `server.port` | `8080` | Puerto del Back-End |
| `kudos.cors.origen-permitido` | `http://localhost:5173` | Único origen desde el que el navegador puede llamar a la API (CORS) |

### 4.4 Front-End (`.vscode/settings.json`)

| Propiedad | Valor | Para qué |
|---|---|---|
| `liveServer.settings.port` | `5173` | Debe coincidir con el origen permitido por CORS |
| `liveServer.settings.host` | `localhost` | Con `127.0.0.1` el navegador lo toma como otro origen y bloquea las peticiones |

La dirección del Back-End está en `js/api.js` (`API_URL = "http://localhost:8080/api/v1"`).

### 4.5 Inicio de sesión con Google

1. Entrar a [Google Cloud Console](https://console.cloud.google.com) y crear un proyecto (por ejemplo, `UCO Kudos`).
2. *APIs y servicios → Pantalla de consentimiento de OAuth*: tipo **Externo**, nombre de la aplicación y correo de soporte. Agregar como **usuarios de prueba** los Gmail que van a ingresar.
3. *Credenciales → Crear credenciales → ID de cliente de OAuth*, tipo **Aplicación web**, con los orígenes de JavaScript autorizados `http://localhost:5173` y `http://localhost`.
4. Copiar el **ID de cliente** en dos lugares:
   1. Back-End: la variable `KUDOS_GOOGLE_CLIENT_ID` (punto 4.1 o 4.2).
   2. Front-End: la constante `GOOGLE_CLIENT_ID`, en la primera línea de `js/login.js`.
5. Permitir el dominio del correo. El parámetro `DOMINIO_CORREO_INSTITUCIONAL` acepta varios dominios separados por coma; en los datos de prueba ya viene `uco.edu.co,gmail.com`. En una base de datos creada antes:

   ```sql
   UPDATE kudos.parametro_sistema
   SET valor = 'uco.edu.co,gmail.com'
   WHERE clave = 'DOMINIO_CORREO_INSTITUCIONAL';
   ```

6. El correo de Google debe pertenecer a un usuario **registrado y activo**: Google solo comprueba quién es la persona, no crea cuentas. Para probar, se puede crear el usuario con el registro normal o con un `INSERT` en la tabla `usuario`.

---

## 5. Ejecución

Siempre en este orden: **PostgreSQL → Back-End → Front-End**.

### 5.1 Back-End

En Eclipse: clic derecho en `UcoKudosBackEndApplication` → *Run As → Spring Boot App* (o *Java Application*).

O desde la terminal, en la carpeta `UCO_kudos_BackEnd` y con las variables de entorno ya definidas:

```
mvnw.cmd spring-boot:run        (Windows)
./mvnw spring-boot:run          (Linux / macOS)
```

**Comprobar:** la consola muestra `Started UcoKudosBackEndApplication` y en el navegador `http://localhost:8080/api/v1/estado` responde un JSON.

### 5.2 Front-End

En Visual Studio Code: clic derecho en `index.html` → **Open with Live Server**.

**Comprobar:** se abre `http://localhost:5173/index.html` y se puede iniciar sesión con Tomás.

### 5.3 Primer recorrido sugerido

1. Entrar con **Valentina** → *Kudos* → dar un Kudo a otra persona.
2. Abrir *Ver conversación* → responder, calificar y reportar.
3. Entrar con **Carlos** → *Moderación* → tomar el reporte en revisión → aceptar (la publicación queda vetada).
4. Entrar con **Tomás** → *Auditoría* → ver el registro de todo lo anterior.

---

## 6. Pruebas

### 6.1 Pruebas unitarias (JUnit)

En Eclipse: clic derecho en `src/test/java` → *Run As → JUnit Test*. Resultado esperado: **80 pruebas, 0 errores, 0 fallas**.

Con cobertura: clic derecho en `src/test/java` → *Coverage As → JUnit Test* → pestaña *Coverage*.

Desde la terminal:

```
mvnw.cmd test        (Windows)
./mvnw test          (Linux / macOS)
```

### 6.2 Pruebas de la API (Bruno)

1. Abrir Bruno y crear una colección con la URL base `http://localhost:8080/api/v1`.
2. En la colección, pestaña *Auth*: tipo **Bearer Token** con el valor `{{tokenTomas}}`. Las peticiones usan *Inherit*.
3. Petición de login `POST /sesiones` con `{ "correoInstitucional": "tomas.gallo@uco.edu.co", "clave": "Kudos2026" }` y, en *Script → Post Response*:
   ```javascript
   bru.setVar("tokenTomas", res.body.datos[0].token);
   ```
4. Ejecutar el login primero y luego las demás peticiones. Los casos probados están en la sección [16.4](16_pruebas.md).

### 6.3 Pruebas de restricciones de la base de datos

Ejecutar `db/03_pruebas_restricciones.sql` sobre la base `kudos`: cada caso inválido debe ser rechazado por PostgreSQL.

### 6.4 Análisis estático

El repositorio está conectado a **SonarQube Cloud** con análisis automático: cada `push` a `main` se analiza solo. Resultados en la sección [15](15_analisis_estatico.md).

---

## 7. Despliegue

### 7.1 Generar el ejecutable del Back-End

En la carpeta `UCO_kudos_BackEnd`:

```
mvnw.cmd clean package        (Windows)
./mvnw clean package          (Linux / macOS)
```

Se genera `target/UCO_kudos_BackEnd-0.0.1-SNAPSHOT.jar`, que incluye el servidor web, así que no necesita un Tomcat aparte.

### 7.2 Ejecutar el `.jar` en el servidor

1. El servidor necesita **Java 27** y acceso a un **PostgreSQL 16** con los scripts del punto 3 ya ejecutados.
2. Definir las tres variables de entorno del punto 4 con los datos de ese PostgreSQL.
3. Si el Front-End se publica en otra dirección, indicar ese origen para CORS al arrancar:
   ```
   java -jar UCO_kudos_BackEnd-0.0.1-SNAPSHOT.jar --kudos.cors.origen-permitido=https://kudos.midominio.edu.co
   ```
4. Para cambiar el puerto: `--server.port=9090`.

### 7.3 Publicar el Front-End

El Front-End son archivos estáticos, así que se pueden publicar en cualquier servidor web (Nginx, Apache, IIS) o en un servicio de sitios estáticos.

1. Cambiar `API_URL` en `js/api.js` por la dirección pública del Back-End.
2. Copiar todos los archivos (`*.html`, `css/`, `js/`) a la carpeta pública del servidor.
3. Verificar que la dirección del Front-End sea la misma configurada como origen permitido en el Back-End.

### 7.4 Recomendaciones para producción

1. Usar **HTTPS** en el Front-End y en el Back-End.
2. Crear un usuario de PostgreSQL solo para la aplicación, con permisos únicamente sobre el esquema `kudos`.
3. No cargar `02_datos_muestreo_kudos.sql`: crear solo el administrador inicial.
4. Tener en cuenta que las sesiones están en memoria: si el Back-End se reinicia, los usuarios deben iniciar sesión de nuevo.

---

## 8. Problemas frecuentes

| Síntoma | Causa | Solución |
|---|---|---|
| "No fue posible conectarse con la base de datos…" | PostgreSQL apagado o variables de entorno sin configurar | Iniciar PostgreSQL y revisar las tres variables del punto 4 |
| El Front muestra "No se pudo conectar con el servidor" | El Back-End no está corriendo, o la página está abierta con `127.0.0.1` o en otro puerto | Iniciar el Back-End y abrir `http://localhost:5173` |
| En la consola del navegador (F12) aparece un error de **CORS** | El origen del Front-End no coincide con `kudos.cors.origen-permitido` | Usar `localhost:5173` o ajustar la propiedad |
| `Port 8080 was already in use` | Otra instancia del Back-End sigue abierta | Detenerla en Eclipse (botón rojo) o cambiar `server.port` |
| Después de reiniciar el Back-End todo responde 401 | Las sesiones se guardan en memoria | Cerrar sesión e iniciar de nuevo |
| "Su cuenta aún no está activa" | Falta aprobar la solicitud o verificar el correo o el celular | Aprobar con el rol correspondiente y verificar con el código |
| El botón de Google no aparece | Sin conexión a internet o el script de Google bloqueado | Revisar la conexión y la consola del navegador (F12) |
| Google muestra "Error 400: origin_mismatch" | El origen del Front no está autorizado en el ID de cliente | Agregar `http://localhost:5173` y `http://localhost` en los orígenes autorizados |
| "El inicio de sesión con Google no está configurado" | Falta la variable `KUDOS_GOOGLE_CLIENT_ID` en el Back-End | Agregarla (punto 4.1) y reiniciar el Back-End |
| Con Google responde "No existe una cuenta registrada con el correo…" | El Gmail no pertenece a ningún usuario de Kudos | Registrar ese usuario o usar un correo registrado |
| No llega el código de verificación | El envío es simulado | El código aparece en la consola del Back-End |
| Un estudiante nuevo no aparece en Solicitudes | Lo aprueba el Coordinador del programa elegido, no el Administrador | Entrar con el coordinador de ese programa (Paula para Ing. de Sistemas) |
| El navegador muestra una versión vieja de una página | Caché del navegador | Recargar con **Ctrl + F5** |
