# 2. Contexto del problema — UCO Kudos

## 2.1 Situación actual

Hoy la Universidad **no tiene un sistema centralizado** para los reconocimientos. Se hacen por:

- Correo electrónico.
- Redes sociales.
- Conversaciones informales.
- Eventos institucionales.

## 2.2 Problemas que genera

| Problema | Consecuencia |
|---|---|
| No hay trazabilidad | No se sabe quién reconoció a quién, cuándo ni por qué. |
| No hay historial | Los reconocimientos se pierden con el tiempo; no queda memoria institucional. |
| No hay indicadores | No se puede medir la participación ni qué categorías se reconocen más. |
| No hay estrategia unificada | Cada dependencia reconoce a su manera, sin reglas comunes ni moderación. |

## 2.3 Situación deseada

La Universidad quiere una plataforma que permita:

| Necesidad | Cómo la resuelve UCO Kudos |
|---|---|
| Reconocer personas | Un usuario activo crea un Kudo hacia otra persona, con una categoría y un contenido. |
| Compartir reconocimientos | Los Kudos visibles se pueden consultar por la comunidad y por destinatario. |
| Participar en conversaciones | Réplicas sobre un Kudo o sobre otra réplica, guardando el Kudo raíz de la conversación. |
| Calificar contenido | De 1 a 5 estrellas sobre Kudos y réplicas; una calificación por usuario, que se puede cambiar. |
| Moderar contenido | Cualquier usuario reporta; el Rector o el Coordinador Académico revisa y decide; si acepta, la publicación se veta. |
| Medir participación | Promedios y totales calculados desde las calificaciones; auditoría de cada operación. |
| Gestionar información académica | Facultades, programas y vinculación de estudiantes y docentes a programas. |
| Administrar la estructura organizacional | Coordinadores de facultad y de programa con historial (fecha de inicio y fin). |

## 2.4 Restricciones generales

La plataforma debe respetar:

1. **La estructura organizacional oficial:** facultades, programas y coordinadores.
2. **Las políticas de aprobación:** cada rol lo aprueba un rol superior (ver [4. Actores](04_actores.md)).
3. **La seguridad:** correo institucional, contraseña cifrada con BCrypt y sesión con token.
4. **La moderación:** solo las autoridades vetan; un veto no se revierte.
5. **El acceso:** cada operación valida la sesión, que la cuenta esté activa y el permiso del rol.

## 2.5 Supuestos del análisis

Los vacíos del enunciado se resolvieron con estos supuestos (detalle en [5. Modelo anémico](05_modelo_anemico.md) y en el registro de hallazgos del [6. Modelo enriquecido](06_modelo_enriquecido.md)):

1. Hay **una sola universidad**; no se modela la entidad `Universidad`.
2. El usuario se identifica por su **correo institucional**; no se guarda número de documento.
3. El celular usa el indicativo fijo **+57**.
4. Un Kudo es **de una persona hacia otra**: nadie se reconoce a sí mismo.
5. Kudos y réplicas **no se editan ni se eliminan**: solo se vetan, para conservar la trazabilidad.
6. El envío de códigos de verificación es **simulado** (queda en el registro de la aplicación).
