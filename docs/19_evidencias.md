# 19. Evidencias de pruebas — UCO Kudos

Capturas de las pruebas de la API con **Bruno** y del **Front-End**, ordenadas por flujo. Cada una indica qué se probó, qué se esperaba y qué se obtuvo.

> Las pruebas se hicieron antes de cambiar el usuario Administrador de los datos de prueba. Por eso en algunas capturas aparece **Laura Gómez**: es el mismo usuario (`…0005-000000000001`) que ahora es **Tomás Gallo**.

## Resumen

| Grupo | Casos | Éxito esperado | Error esperado | Resultado |
|---|---|---|---|---|
| Sesión y usuarios | 5 | 1 | 4 | ✅ Todos como se esperaba |
| Kudos | 5 | 2 | 3 | ✅ |
| Réplicas | 3 | 2 | 1 | ✅ |
| Calificaciones | 5 | 3 | 2 | ✅ |
| Reportes y moderación | 9 | 6 | 3 | ✅ |
| Vetos | 4 | 1 | 3 | ✅ |
| Peticiones mal formadas | 3 | 0 | 3 | ✅ |
| Catálogos y permisos | 4 | 2 | 2 | ✅ |
| Notificaciones y auditoría | 4 | 4 | 0 | ✅ |
| Front-End | 8 | — | — | ✅ |
| **Total** | **50** | | | |

Además, la sección 19.11 muestra 4 **hallazgos** que aparecieron durante las pruebas y cómo se corrigieron.

---

## 19.1 Sesión y usuarios

| # | Prueba | Esperado | Obtenido |
|---|---|---|---|
| 1 | Iniciar sesión con correo y clave correctos | 200 y token | ✅ 200 |
| 2 | Petición con un token inválido | 401 | ✅ 401 |
| 3 | Iniciar sesión con clave incorrecta | 400 | ✅ 400 |
| 4 | Iniciar sesión con una cuenta pendiente de activar | 400, indicando qué falta | ✅ 400 |
| 5 | Petición sin sesión (`GET /kudos` sin token) | 401 "Debe iniciar sesión…" | ✅ 401 |

![Login](evidencias/pruebas/api_01_login.png)
![Token inválido](evidencias/pruebas/api_02_token_invalido.png)
![Clave incorrecta](evidencias/pruebas/api_03_clave_incorrecta.png)
![Cuenta pendiente](evidencias/pruebas/api_04_cuenta_pendiente.png)
![Sin sesión](evidencias/pruebas/api_10_sin_sesion.png)

## 19.2 Kudos

| # | Prueba | Regla | Esperado | Obtenido |
|---|---|---|---|---|
| 6 | Dar un Kudo | CU-001 | 200, Kudo VISIBLE | ✅ 200 |
| 7 | Consultar la conversación de un Kudo | CU-006 | 200, réplicas con su Kudo raíz | ✅ 200 |
| 8 | Darse un Kudo a sí mismo | Nadie se reconoce a sí mismo | 400 | ✅ 400 |
| 9 | Kudo con una categoría inactiva | Solo categorías activas | 400 | ✅ 400 |
| 10 | Kudo con contenido que solo tiene HTML (`<b></b>`) | Se sanea el contenido; queda vacío | 400 | ✅ 400 |

![Dar Kudo](evidencias/pruebas/api_05_dar_kudo.png)
![Conversación](evidencias/pruebas/api_06_conversacion.png)
![Kudo a sí mismo](evidencias/pruebas/api_07_kudo_a_si_mismo.png)
![Categoría inactiva](evidencias/pruebas/api_08_categoria_inactiva.png)
![Contenido vacío](evidencias/pruebas/api_09_contenido_vacio.png)

## 19.3 Réplicas

| # | Prueba | Esperado | Obtenido |
|---|---|---|---|
| 11 | Responder un Kudo | 200; `idKudoRaiz` = el Kudo | ✅ 200 |
| 12 | Responder una réplica | 200; conserva el `idKudoRaiz` original | ✅ 200 |
| 13 | Responder un Kudo vetado | 400 "La publicación está vetada…" | ✅ 400 |

![Responder Kudo](evidencias/pruebas/api_11_responder_kudo.png)
![Responder réplica](evidencias/pruebas/api_12_responder_replica.png)
![Responder vetado](evidencias/pruebas/api_13_responder_vetado.png)

## 19.4 Calificaciones

| # | Prueba | Esperado | Obtenido |
|---|---|---|---|
| 14 | Calificar un Kudo con 5 estrellas | 200 | ✅ 200 |
| 15 | Cambiar la calificación a 3 (mismo usuario) | 200; misma calificación, con fecha de modificación | ✅ 200 |
| 16 | Consultar el resumen | Promedio, cantidad y mi calificación | ✅ 200 (promedio 4.0, cantidad 3) |
| 17 | Calificar con 7 estrellas | 400 "entre 1 y 5" | ✅ 400 |
| 18 | Calificar una publicación vetada | 400 | ✅ 400 |

![Calificar](evidencias/pruebas/api_14_calificar.png)
![Cambiar calificación](evidencias/pruebas/api_15_cambiar_calificacion.png)
![Resumen](evidencias/pruebas/api_16_resumen_calificaciones.png)
![Estrellas inválidas](evidencias/pruebas/api_17_estrellas_invalidas.png)
![Calificar vetada](evidencias/pruebas/api_18_calificar_vetada.png)

## 19.5 Reportes y moderación

| # | Prueba | Esperado | Obtenido |
|---|---|---|---|
| 19 | Reportar un Kudo (SPAM) | 200, reporte PENDIENTE | ✅ 200 |
| 20 | Reportar otra vez la misma publicación | 400 "Ya tiene un reporte abierto…" | ✅ 400 |
| 21 | Reportar con motivo OTRO y descripción corta | 400 | ✅ 400 |
| 22 | Consultar reportes con un rol sin permiso | 400 "Su rol no tiene permiso…" | ✅ 400 |
| 23 | Rector consulta los reportes pendientes | 200 | ✅ 200 |
| 24 | Tomar un reporte en revisión | 200, EN_REVISION y revisor asignado | ✅ 200 |
| 25 | Rechazar el reporte con observación | 200, RECHAZADO (la publicación vuelve a VISIBLE) | ✅ 200 |
| 26 | Tomar otro reporte en revisión | 200, EN_REVISION | ✅ 200 |
| 27 | Aceptar el reporte | 200, ACEPTADO (la publicación queda VETADA) | ✅ 200 |

![Reportar](evidencias/pruebas/api_19_reportar.png)
![Reporte duplicado](evidencias/pruebas/api_20_reporte_duplicado.png)
![OTRO sin descripción](evidencias/pruebas/api_21_reporte_otro_sin_descripcion.png)
![Sin permiso](evidencias/pruebas/api_22_reportes_sin_permiso.png)
![Pendientes](evidencias/pruebas/api_23_reportes_pendientes.png)
![En revisión](evidencias/pruebas/api_24_tomar_en_revision.png)
![Rechazar](evidencias/pruebas/api_25_rechazar_reporte.png)
![En revisión 2](evidencias/pruebas/api_26_tomar_en_revision_2.png)
![Aceptar](evidencias/pruebas/api_27_aceptar_reporte.png)

## 19.6 Vetos

| # | Prueba | Esperado | Obtenido |
|---|---|---|---|
| 28 | El Rector veta una publicación directamente | 200 "La publicación fue vetada." | ✅ 200 |
| 29 | Veto con justificación corta | 400 "al menos 20 caracteres" | ✅ 400 |
| 30 | Vetar una publicación ya vetada | 400 "ya fue vetada" | ✅ 400 |
| 31 | Vetar con un rol sin permiso (Administrador) | 400 "Su rol no tiene permiso…" | ✅ 400 |

![Vetar](evidencias/pruebas/api_28_vetar.png)
![Justificación corta](evidencias/pruebas/api_29_veto_justificacion_corta.png)
![Ya vetada](evidencias/pruebas/api_30_vetar_ya_vetada.png)
![Sin permiso](evidencias/pruebas/api_31_vetar_sin_permiso.png)

## 19.7 Peticiones mal formadas

| # | Prueba | Esperado | Obtenido |
|---|---|---|---|
| 32 | `POST` a una ruta que solo acepta `GET` | 405 | ✅ 405 |
| 33 | Id que no es un UUID (`/kudos/abc`) | 400 | ✅ 400 |
| 34 | JSON mal formado | 400 "Revise el cuerpo de la petición" | ✅ 400 |

![405](evidencias/pruebas/api_32_metodo_no_permitido.png)
![Id inválido](evidencias/pruebas/api_33_id_invalido.png)
![JSON mal formado](evidencias/pruebas/api_34_json_mal_formado.png)

## 19.8 Catálogos y permisos

| # | Prueba | Esperado | Obtenido |
|---|---|---|---|
| 35 | El Administrador crea una categoría | 200 | ✅ 200 |
| 36 | Crear una categoría sin sesión | 401 | ✅ 401 |
| 37 | El Administrador registra una facultad | 200 | ✅ 200 |
| 38 | Registrar una facultad con un rol sin `GESTIONAR_ESTRUCTURA` | 400 "Su rol no tiene permiso…" | ✅ 400 |

![Crear categoría](evidencias/pruebas/api_35_crear_categoria.png)
![Sin sesión](evidencias/pruebas/api_36_categoria_sin_sesion.png)
![Registrar facultad](evidencias/pruebas/api_37_registrar_facultad.png)
![Sin permiso](evidencias/pruebas/api_38_facultad_sin_permiso.png)

## 19.9 Notificaciones y auditoría

| # | Prueba | Esperado | Obtenido |
|---|---|---|---|
| 39 | Consultar mis notificaciones | 200; aviso de "publicación vetada" (creado por el Observer) | ✅ 200 |
| 40 | Marcar una notificación como leída | 200, `leida: true` | ✅ 200 |
| 41 | Consultar la auditoría | 200; registro de cada operación | ✅ 200 |
| 42 | Filtrar la auditoría por `PUBLICACION_VETADA` | 200; valor anterior VISIBLE → nuevo VETADO | ✅ 200 |

![Notificaciones](evidencias/pruebas/api_39_notificaciones.png)
![Marcar leída](evidencias/pruebas/api_40_marcar_leida.png)
![Auditoría](evidencias/pruebas/api_41_auditoria.png)
![Auditoría filtrada](evidencias/pruebas/api_42_auditoria_filtrada.png)

## 19.10 Front-End

| # | Pantalla | Qué se comprueba |
|---|---|---|
| 43 | Iniciar sesión | Formulario de acceso |
| 44 | Inicio del Administrador | Menú con Solicitudes, Administración y Auditoría |
| 45 | Inicio de un Estudiante | Menú reducido: solo Kudos, Notificaciones y Mi perfil |
| 46 | Cuenta pendiente | El mensaje indica qué falta para activar la cuenta |
| 47 | Muro de Kudos | Dar un Kudo y lista de reconocimientos con su categoría |
| 48 | Conversación | Árbol de réplicas, calificación, reportar (motivo obligatorio) |
| 49 | Administración | Categorías con editar, activar y desactivar |
| 50 | Auditoría | Registro de operaciones con usuario, acción y valores anterior y nuevo |

![Login](evidencias/pruebas/front_01_login.png)
![Inicio Administrador](evidencias/pruebas/front_02_inicio_administrador.png)
![Inicio Estudiante](evidencias/pruebas/front_03_inicio_estudiante.png)
![Cuenta pendiente](evidencias/pruebas/front_04_cuenta_pendiente.png)
![Muro](evidencias/pruebas/front_05_muro.png)
![Conversación](evidencias/pruebas/front_06_conversacion.png)
![Administración](evidencias/pruebas/front_07_administracion.png)
![Auditoría](evidencias/pruebas/front_08_auditoria.png)

## 19.11 Hallazgos durante las pruebas

Problemas que aparecieron mientras se probaba y cómo se resolvieron. Son evidencia de la **refactorización** que pide la rúbrica.

| Hallazgo | Causa | Corrección |
|---|---|---|
| Una petición a una ruta equivocada respondía **500** | No había manejo para método no permitido ni para cuerpos mal formados | `AbstractRestController` ahora responde 405 / 400 / 404 (ver casos 32–34) |
| El Front mostraba "No se pudo conectar con el servidor" | La página estaba abierta con `127.0.0.1`, un origen distinto al permitido por CORS | `.vscode/settings.json` fija `localhost:5173`; documentado en el manual |
| La lista de programas del registro salía **vacía** | Sin filtro, el estado se convertía en `NO_DEFINIDO` y se filtraba por él | `UtilTexto.textoDeEnum` en los ensambladores, con su prueba JUnit |
| Se podía reportar sin elegir el motivo (venía uno preseleccionado) | La lista del Front no tenía una opción vacía | Opción "Seleccione el motivo"; el Back rechaza el motivo vacío |

![Error 500](evidencias/pruebas/hallazgo_01_error_500.png)
![Sin conexión](evidencias/pruebas/hallazgo_02_sin_conexion.png)
![Programas vacíos](evidencias/pruebas/hallazgo_03_programas_vacios.png)
![Motivo por defecto](evidencias/pruebas/hallazgo_04_motivo_por_defecto.png)
