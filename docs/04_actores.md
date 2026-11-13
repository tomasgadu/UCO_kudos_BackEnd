# 4. Actores — UCO Kudos

## 4.1 Actores del sistema

En UCO Kudos todos los actores son **usuarios** de la plataforma. Lo que los diferencia es su **rol**. Un usuario tiene **un solo rol** a la vez, y eso cumple la exclusividad de roles por estructura (decisión D1 del [modelo anémico](05_modelo_anemico.md)).

| Actor | Descripción | Puede vetar |
|---|---|---|
| **Administrador del Sistema** | Administración técnica: estructura organizacional, categorías y parámetros. No participa en la moderación de contenido. | No |
| **Rector** | Máxima autoridad institucional en la plataforma. | Sí |
| **Coordinador Académico** | Autoridad académica institucional. | Sí |
| **Coordinador de Facultad** | Responsable de una facultad. | No |
| **Coordinador de Programa** | Responsable de un programa académico. | No |
| **Docente** | Integrante de la comunidad académica; puede enseñar en varios programas. | No |
| **Estudiante** | Integrante de la comunidad estudiantil; puede estudiar varios programas. | No |
| **Visitante** | Persona sin sesión. Solo se registra, inicia sesión o consulta catálogos públicos. | No |

## 4.2 Matriz de aprobaciones

Cada registro crea una **solicitud** que debe aprobar el rol superior. Aprobar **no** activa la cuenta: además se deben verificar el correo y el celular.

| Rol solicitado | Lo aprueba | Ámbito |
|---|---|---|
| Rector | Administrador | — |
| Coordinador Académico | Rector | — |
| Coordinador de Facultad | Coordinador Académico | Una facultad |
| Coordinador de Programa | Coordinador de Facultad | Un programa |
| Docente | Coordinador de Programa | Un programa |
| Estudiante | Coordinador de Programa | Un programa |

El Administrador y el Rector no se auto-registran: los registra un Administrador. La matriz se guarda como **dato** (`Rol.rolAprobador`), no como condiciones en el código.

## 4.3 Permisos adicionales por rol

Cualquier usuario con cuenta activa puede dar Kudos, replicar, calificar y reportar. Algunos roles tienen permisos extra:

| Permiso | Administrador | Rector | Coord. Académico | Demás roles |
|---|---|---|---|---|
| Gestionar estructura (facultades, programas) | ✅ | | | |
| Gestionar categorías | ✅ | ✅ | ✅ | |
| Gestionar parámetros | ✅ | | | |
| Consultar auditoría | ✅ | ✅ | ✅ | |
| Revisar reportes | | ✅ | ✅ | |
| Vetar contenido | | ✅ | ✅ | |

En el código, estos permisos están en el enum `CodigoRol`. Cada endpoint protegido valida el permiso antes de ejecutar la operación; si falta, responde **401**.

## 4.4 Actores en los casos de uso

Para el diagrama de casos de uso ([10. Diagramas UML](10_diagramas_uml.md)) los roles se agrupan según lo que pueden hacer:

| Actor UML | Roles que agrupa | Qué agrega |
|---|---|---|
| Visitante | Sin sesión | Registrarse, verificar contacto, iniciar sesión |
| Usuario autenticado | Todos los roles | Kudos, réplicas, calificaciones, reportes, perfil, notificaciones |
| Aprobador | Rector y los Coordinadores | Aprobar o rechazar solicitudes de su matriz |
| Autoridad | Rector y Coordinador Académico | Revisar reportes y vetar |
| Administrador | Administrador | Estructura, categorías, parámetros y auditoría |

Todos heredan de **Usuario autenticado** (generalización): un Rector también puede dar Kudos.
