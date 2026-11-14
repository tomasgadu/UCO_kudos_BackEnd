# 14. Calidad del software — UCO Kudos

Cómo se atiende cada criterio de calidad de la rúbrica (CAL-001 a CAL-007).

| Criterio | Cómo se logra | Evidencia |
|---|---|---|
| **CAL-001 Legibilidad** | Nombres en español que dicen lo que hacen (`AsegurarNombreCategoriaNoExistaRegla`, `obtenerKudoRaiz`); métodos cortos; mensajes centralizados en `CatalogoMensajes`; mismo estilo en todo el código. | Código fuente; SonarQube sin *code smells* abiertos |
| **CAL-002 Mantenibilidad** | Capas con una responsabilidad cada una; un cambio de regla toca una sola clase; textos en un catálogo. | Sección 12; SonarQube con calificación A |
| **CAL-003 Reutilización** | Reglas reutilizables (`Regla<T>`), `FachadaBase`, `UtilTexto`/`UtilUUID`/`UtilFecha`, `ValidadorCampos`; en el Front, `api.js` y `ui.js`. | Secciones 12 y 13 |
| **CAL-004 Bajo acoplamiento** | El negocio depende de `DAOFactory` y no de PostgreSQL; eventos con Observer; el Front solo conoce la API por `api.js`. | Sección 13.3 (D de SOLID) |
| **CAL-005 Alta cohesión** | Cada clase agrupa lo de un solo concepto: el ciclo de vida de la publicación vive en `PublicacionDominio`; los permisos en `CodigoRol`. | Sección 9 |
| **CAL-006 Testabilidad** | Dominio sin dependencias externas, así que se prueba sin base de datos; reglas con instancia única fácil de llamar. | 85 pruebas JUnit, 100 % exitosas (sección 16) |
| **CAL-007 Evolución** | Agregar un suscriptor, un rol, un permiso o un motor de base de datos no obliga a reescribir lo existente; la matriz de aprobaciones es un dato. | Sección 13.3 (O de SOLID) |

## 14.1 Clean Code aplicado

1. **Nombres con intención:** `estaVetada()`, `puedeRecibirReplicas()`, `esCargoUnico()`.
2. **Funciones pequeñas** que hacen una sola cosa: cada regla tiene un único método `ejecutar`.
3. **Sin números ni textos mágicos:** `MINIMO_ESTRELLAS`, `MAXIMO_ESTRELLAS`, `CatalogoMensajes`.
4. **Sin código comentado ni comentarios innecesarios:** el código se explica por sus nombres.
5. **Manejo de errores con excepciones propias** por capa, nunca códigos de error devueltos.
6. **Sin nulos en el dominio:** valores por defecto (`UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo`).
7. **Inmutabilidad** en el dominio.

## 14.2 Refactorizaciones realizadas

| Refactorización | Antes | Después |
|---|---|---|
| Transacciones en una plantilla | Cada fachada repetía `try / commit / rollback / close` | `FachadaBase` (Template Method) |
| Validación de la cantidad de datos de una regla | Acceso directo a `datos[n]` (Sonar: posible `ArrayIndexOutOfBounds`) | `Regla.validarCantidadDatos` |
| Nulos en `aplicarMayusculaInicial` | Posible `NullPointerException` (Sonar Blocker) | Validación al inicio |
| Datos del usuario en el log | Riesgo de *Log Injection* (Sonar Security) | `UtilTexto.limpiarParaRegistro` |
| Filtro por estado `NO_DEFINIDO` | Una consulta sin estado devolvía la lista vacía | `UtilTexto.textoDeEnum` en los ensambladores |
| Códigos HTTP | Peticiones mal formadas respondían 500 | 400 / 405 / 404 en `AbstractRestController` |
| Comentarios de documentación | Javadoc que repetía lo que el código ya decía | Se eliminaron; los nombres explican el código |

## 14.3 Revisión de código

- **Análisis estático** con SonarQube Cloud en cada `push` (sección [15](15_analisis_estatico.md)): Quality Gate en *Passed*, 0 issues.
- **Pruebas** unitarias y de integración (sección [16](16_pruebas.md)).
- **Commits pequeños** y con mensaje descriptivo (`feat`, `fix`, `docs`, `test`, `refactor`), que permiten revisar cada cambio por separado.
