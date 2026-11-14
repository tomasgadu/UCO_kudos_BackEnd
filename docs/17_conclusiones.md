# 17. Conclusiones — UCO Kudos

## 17.1 Resultados

1. Se construyó una plataforma **completamente funcional**: registro con aprobación y verificación, Kudos, conversaciones en árbol, calificaciones, reportes, vetos, notificaciones, auditoría y administración de la estructura.
2. El **Back-End** sigue una arquitectura en capas con dominio enriquecido, transacciones por caso de uso, eventos (Observer) y seguridad por permisos.
3. El **Front-End** es sencillo y funcional: 11 páginas que consumen la API y muestran a cada rol solo lo que le corresponde.
4. La calidad se verificó con **80 pruebas unitarias** (100 % exitosas), pruebas de integración con Bruno y **SonarQube** con el Quality Gate en *Passed*.

## 17.2 Aprendizajes

1. **Primero el dominio, después el código.** El modelo anémico mostró la estructura; el enriquecido asignó responsabilidades. Cuando llegó el código, cada clase ya sabía qué le tocaba.
2. **El dominio enriquecido protege las reglas.** Al vivir dentro del objeto (`vetar()`, `activar()`), una regla no se puede saltar desde otra parte.
3. **Las capas facilitan los cambios.** Corregir el filtro de estado o un issue de Sonar tocó pocas clases y no rompió nada, gracias a la separación y a las pruebas.
4. **La seguridad va en el Back.** El Front puede ocultar opciones, pero solo el Back decide; se comprobó abriendo a mano una página sin permiso.
5. **El análisis estático encuentra lo que no se ve a simple vista:** un nulo posible, un índice sin validar, un log manipulable.
6. **Datos de prueba realistas** (roles, matriz de aprobaciones) ayudaron a encontrar casos que el enunciado no decía explícitamente, por ejemplo quién aprueba a un estudiante.

## 17.3 Limitaciones

| Limitación | Impacto | Posible mejora |
|---|---|---|
| Sesiones en memoria | Al reiniciar el Back hay que iniciar sesión de nuevo | Guardar las sesiones en la base de datos o usar JWT |
| Envío de códigos simulado | El código de verificación aparece en el log y no llega por correo o SMS | Integrar un servicio de correo o SMS |
| Suspender o desactivar cuentas | Los estados existen en el modelo, pero no tienen endpoint | Agregar la gestión de cuentas por la matriz de gestión |
| Ver una réplica reportada desde Moderación | El enlace solo abre conversaciones de Kudos | Endpoint para consultar una publicación por id |
| Cobertura total del 10 % | DAOs y controladores solo tienen pruebas de integración manuales | Pruebas de integración automáticas con una base de datos de prueba |

## 17.4 Trabajo futuro

1. Indicadores y reportes de participación (Kudos por facultad, por categoría, por periodo).
2. Notificaciones por correo.
3. Despliegue en la nube (Back en un servicio de contenedores y Front como sitio estático).
4. Pruebas automáticas del Front.
