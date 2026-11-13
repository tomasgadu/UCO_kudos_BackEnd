# 1. Introducción — UCO Kudos

## 1.1 ¿Qué es UCO Kudos?

UCO Kudos es la **plataforma institucional de reconocimientos** de la Universidad Católica de Oriente. Permite que un integrante de la comunidad universitaria reconozca públicamente a otro mediante un **Kudo**, y que la comunidad converse, califique y modere esos reconocimientos.

Un **Kudo** es un reconocimiento formal de una persona hacia otra por conductas destacadas, logros académicos o institucionales, aportes a la comunidad o comportamientos alineados con los valores de la universidad.

## 1.2 Alcance de la solución

| Área | Qué cubre |
|---|---|
| Estructura organizacional | Facultades, programas académicos y sus coordinadores |
| Usuarios y roles | Registro con correo institucional y celular, verificación, aprobación según el rol y activación de la cuenta |
| Categorías | Clasificación de los Kudos (activas e inactivas) |
| Reconocimientos | Crear y consultar Kudos e historial por persona |
| Conversaciones | Réplicas sobre un Kudo o sobre otra réplica (árbol de conversación) |
| Calificaciones | De 1 a 5 estrellas, una por usuario y publicación |
| Moderación | Reportes, revisión y vetos |
| Auditoría y notificaciones | Registro de cada operación y avisos al usuario afectado |

## 1.3 Componentes entregados

| Componente | Descripción |
|---|---|
| Documentación | Este conjunto de archivos Markdown en `docs/` (secciones 1 a 17) |
| Back-End | API REST en Java + Spring Boot con PostgreSQL (este repositorio) |
| Front-End | Interfaz web (repositorio `uco-kudos-frontend`) |
| Manual de puesta a punto | Pasos para ejecutar la solución desde cero |

## 1.4 Cómo leer esta documentación

| Sección | Contenido |
|---|---|
| [2. Contexto](02_contexto.md) | El problema actual y la situación deseada |
| [3. Objetivos](03_objetivos.md) | Qué busca la plataforma y qué busca el proyecto |
| [4. Actores](04_actores.md) | Quién usa la plataforma y qué puede hacer cada uno |
| [5. Modelo anémico](05_modelo_anemico.md) | Estructura del dominio: entidades, atributos y relaciones |
| [6. Modelo enriquecido](06_modelo_enriquecido.md) | Responsabilidades, políticas y reglas de cada objeto |
| [7. Eventos](07_eventos.md) | Hechos que ocurren en el dominio y sus reacciones |
| [8. Políticas](08_politicas.md) | Catálogo de políticas y su trazabilidad al enunciado |
| [10. Diagramas UML](10_diagramas_uml.md) | Casos de uso, clases, secuencia, actividad y estado |
| [11. Modelo de datos](11_modelo_datos.md) | Modelo relacional y scripts de PostgreSQL |
