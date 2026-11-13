# 3. Objetivos — UCO Kudos

## 3.1 Objetivo general

Diseñar e implementar una plataforma institucional que permita a la comunidad de la Universidad Católica de Oriente **reconocer, conversar, calificar y moderar** reconocimientos, conservando su historial y aplicando las reglas de la institución.

## 3.2 Objetivos de la plataforma

| Objetivo | Qué significa | Cómo se cumple |
|---|---|---|
| **Reconocimiento** | Reconocer públicamente a quien aporta valor a la comunidad. | Crear Kudo (CU-001) con destinatario, categoría activa y contenido. |
| **Visibilidad** | Que la comunidad conozca los reconocimientos. | Consultar Kudos, historial por persona y conversación (CU-002, CU-003, CU-006). |
| **Participación** | Interacción entre los miembros. | Réplicas (CU-004, CU-005) y calificaciones (CU-007, CU-008). |
| **Trazabilidad** | Mantener el histórico de la actividad. | Auditoría de cada operación; vinculaciones y coordinaciones con fechas; nada se borra. |
| **Gobernanza** | Mecanismos de control y moderación. | Aprobación por roles, reportes, revisión y veto; permisos por rol. |

## 3.3 Objetivos académicos del proyecto

| Área | Objetivo | Dónde se evidencia |
|---|---|---|
| Análisis | Levantar requisitos, restricciones, eventos, reglas y políticas. | Secciones 5, 6, 7 y 8 |
| Diseño | Modelar clases, colaboraciones, entidades y servicios. | Secciones 5, 6, 9 y 10 |
| Arquitectura | Aplicar el estilo en capas, SOLID, bajo acoplamiento y alta cohesión. | Secciones 12 y 13 y el código del Back-End |
| Calidad | Clean Code, refactorización, análisis estático y pruebas. | Secciones 14, 15 y 16 |

## 3.4 Objetivos específicos

1. Identificar los actores, entidades, relaciones, estados y eventos del dominio a partir del enunciado.
2. Construir el modelo anémico y luego el enriquecido, asignando a cada objeto sus responsabilidades y políticas.
3. Diseñar un modelo relacional en PostgreSQL que respete las reglas de unicidad e integridad.
4. Implementar un Back-End en capas (Controlador, Fachada, Negocio, Dominio y DAO) con Spring Boot.
5. Aplicar patrones de diseño donde resuelvan un problema concreto: Template Method, Abstract Factory, Builder y Observer.
6. Validar el comportamiento con pruebas de la API en Bruno y pruebas unitarias de las reglas.
7. Documentar las decisiones para poder justificarlas en la sustentación.
