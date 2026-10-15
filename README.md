# La Botica del Alquimista · Automatización Front-End con Serenity BDD

Taller 2 de Pruebas Front-End. Este proyecto automatiza la historia de usuario **HU-01: Compra en La Botica del Alquimista**
(<https://boticaalquimista.onrender.com/>) con **Java 17, Serenity BDD, Cucumber y el patrón Screenplay**. Las pruebas se
ejecutan en **GitHub Actions** y cada escenario queda vinculado a un **issue** (caso de prueba) dentro de un **GitHub Project**.

| | |
|---|---|
| Aplicación bajo prueba | La Botica del Alquimista (tienda web de pociones, ingredientes y amuletos) |
| Usuario de práctica | `alquimista` / `pocion123` |
| Escenarios automatizados | **20** (9 positivos y 11 negativos), todos en verde |
| Trazabilidad | Cada escenario tiene un tag de requisito (`@req_...`) y un tag de issue (`@Issue-1` … `@Issue-20`) |
| Navegador | Chrome: con ventana en local y *headless* en GitHub Actions |

---

## 1. ¿Qué se hizo en el taller?

1. **Análisis de la historia de usuario.** Se leyeron los criterios de aceptación de la HU-01 (acceso, catálogo, carrito,
   cupones, pago y pedido) y se diseñaron 20 escenarios que cubren casos positivos y negativos de cada funcionalidad.
2. **Escenarios en Gherkin.** Se escribieron en español (`# language: es`) y de forma declarativa: describen *qué* hace el
   usuario ("aplica el cupón", "confirma el pedido"), no *cómo* lo hace en la pantalla.
3. **Inspección del DOM y localizadores.** Se inspeccionó cada página de la aplicación con las herramientas del navegador y
   se escogió para cada elemento el localizador más estable según la pirámide de prioridad (sección 6). Todos se validaron
   con resultado **1 of 1**.
4. **Automatización con Screenplay.** Se construyeron los Targets (localizadores), Tasks (acciones del actor), Questions
   (verificaciones) y Step Definitions, con un actor llamado **"Alquimista"**.
5. **Ejecución local y reportes.** Las pruebas se ejecutan desde `Runner.java` o con Gradle, y Serenity genera un reporte
   HTML con cada paso narrado y capturas de pantalla.
6. **CI/CD y trazabilidad.** Se configuró un pipeline en GitHub Actions que ejecuta los escenarios por tag, y un script que
   crea los 20 issues en GitHub. Al terminar cada ejecución, el pipeline comenta el resultado en cada issue y le pone la
   etiqueta `status:passed` o `status:failed`.

## 2. Tecnologías

| Herramienta | Versión | Para qué se usa |
|---|---|---|
| Java (JDK) | 17 (funciona también con 21) | Lenguaje de las pruebas |
| Gradle (wrapper) | 8.14.3 | Construcción y ejecución (`gradlew`) |
| Serenity BDD | 4.2.32 | Framework de automatización, patrón Screenplay y reportes |
| Cucumber | (incluido en Serenity) | Ejecuta los escenarios escritos en Gherkin |
| Selenium WebDriver | 4.38.0 | Controla el navegador Chrome |
| JUnit 4 + JUnit Platform | 5.14.0 | `Runner.java` con `@RunWith(CucumberWithSerenity.class)` |
| GitHub Actions / Issues / Projects | – | Pipeline, casos de prueba y trazabilidad |

## 3. Estructura del proyecto

```
BoticaAlquimista/
├── .github/
│   ├── workflows/ci.yml                 → Pipeline: Java 17, ejecución por tag, headless, reportes
│   └── scripts/publicar-resultados.sh   → Comenta el resultado en cada issue (status:passed / status:failed)
├── crear-issues.ps1                     → Crea los 20 issues (casos de prueba) con gh issue create
├── build.gradle / settings.gradle       → En la raíz, al mismo nivel que src/
├── gradlew / gradlew.bat / gradle/      → Gradle wrapper (no hay que instalar Gradle)
└── src/test/
    ├── java/botica/
    │   ├── runners/Runner.java                         → Lanza Cucumber con Serenity
    │   ├── stepdefinitions/
    │   │   ├── Hooks.java                              → @Before: OnStage.setTheStage(new OnlineCast())
    │   │   └── CompraBoticaStepDefinitions.java        → Traduce cada paso Gherkin a Tasks y Questions
    │   ├── tasks/                                      → Lo que HACE el actor
    │   │   ├── AbrirLaBotica         ├── IniciarSesion       ├── FiltrarCatalogo
    │   │   ├── BuscarProducto        ├── AgregarProducto     ├── EliminarProducto
    │   │   ├── IrAlCarrito           ├── AplicarCupon        ├── DiligenciarPago
    │   │   └── ConfirmarPedido
    │   ├── questions/                                  → Lo que el actor CONSULTA para validar
    │   │   ├── ElCatalogo (Boolean)                    → login exitoso: la página siguiente es visible
    │   │   ├── ElMensaje (String)                      → errores, avisos, cupones y validaciones
    │   │   ├── SoloSeMuestranProductos (Boolean)       → filtros y búsqueda
    │   │   ├── CantidadDeProductos (Integer)
    │   │   ├── LaTarjetaDelProducto (String)           → categoría, precio y stock
    │   │   ├── ElProductoEstaAgotado (Boolean)
    │   │   ├── SePuedeIrAPagar (Boolean)
    │   │   ├── ElCarrito (String)                      → cupón, descuento, subtotal y unidades
    │   │   └── ElPedido (String)                       → número BOT-XXXX, envío y total
    │   ├── userinterfaces/                             → SOLO localizadores, una clase por página
    │   │   ├── PaginaLogin          (index.html)      ← separada de las páginas posteriores al login
    │   │   ├── PaginaCatalogo       (catalogo.html)   ← página de destino después del login
    │   │   ├── BarraNavegacion      (encabezado común después del login)
    │   │   ├── PaginaCarrito        (carrito.html)
    │   │   ├── PaginaPago           (checkout.html)
    │   │   └── PaginaConfirmacion   (confirmacion.html)
    │   ├── models/DatosEnvio.java                      → Datos del formulario de pago (tabla del Gherkin)
    │   └── utils/Constantes.java                       → URL, actor "Alquimista" y credenciales
    └── resources/
        ├── features/compra_botica.feature              → Los 20 escenarios
        ├── serenity.conf                               → Navegador, entornos, capturas y esperas
        └── serenity.properties                         → Propiedades simples de Serenity
```

## 4. Patrón Screenplay

Screenplay piensa la prueba como una obra de teatro: un **actor** con **habilidades** (navegar en la web) realiza **tareas**
formadas por **interacciones**, y hace **preguntas** sobre el estado de la pantalla.

```
compra_botica.feature → CompraBoticaStepDefinitions → Actor "Alquimista" → Task → Interactions → Target (userinterfaces)
                                                                     └──→ Question → seeThat(...) en el "Entonces"
```

| Pieza | En este proyecto |
|---|---|
| Actor | "Alquimista", creado con `OnStage.theActorCalled("Alquimista")` |
| Habilidad | `BrowseTheWeb`, que entrega `OnlineCast` (un Chrome por escenario) |
| Interacciones | `Open.url`, `Enter.theValue`, `Click.on`, `SelectFromOptions`, `WaitUntil` |
| Tasks | `IniciarSesion`, `AgregarProducto`, `AplicarCupon`, `ConfirmarPedido`, … |
| Questions | `ElCatalogo`, `ElMensaje`, `ElPedido`, … |
| Targets | `PaginaLogin.CAMPO_USUARIO`, `PaginaCarrito.CAMPO_CUPON`, … |

**Reglas que se cumplen**

- Los Step Definitions solo llaman Tasks (`attemptsTo`) y Questions (`should(seeThat(...))`): no tienen localizadores ni Selenium.
- Los localizadores viven únicamente en `userinterfaces`.
- Cero `Thread.sleep`: las esperas son explícitas con `WaitUntil`.
- Cero XPath absolutos: todos empiezan con `//` o son selectores CSS.

**Validación del login.** No basta con presionar "Ingresar": la Question `ElCatalogo` (`Question<Boolean>`) responde
`true` si la grilla de productos del catálogo (página de destino) es visible, y el paso "Entonces" hace
`theActorInTheSpotlight().should(seeThat(ElCatalogo.estaVisible()))`.

## 5. Escenarios y trazabilidad

Cada escenario tiene dos tags: el **requisito** al que pertenece y el **issue** con el que se vincula. La Característica
tiene además el tag `@HU-01`, que heredan los 20 escenarios.

| Issue | Requisito | Escenario | Tipo |
|---|---|---|---|
| `@Issue-1` | `@req_login` | Inicio de sesión exitoso con credenciales válidas | Positivo |
| `@Issue-2` | `@req_login` | Inicio de sesión fallido con contraseña incorrecta | Negativo |
| `@Issue-3` | `@req_catalogo` | El catálogo muestra la categoría, el precio y el stock de cada producto | Positivo |
| `@Issue-4` | `@req_catalogo` | Filtrar el catálogo por categoría | Positivo |
| `@Issue-5` | `@req_catalogo` | Buscar productos por nombre (coincidencia parcial, sin distinguir mayúsculas) | Positivo |
| `@Issue-6` | `@req_catalogo` | Buscar un producto que no existe en el catálogo | Negativo |
| `@Issue-7` | `@req_carrito` | No se puede agregar una cantidad mayor al stock disponible | Negativo |
| `@Issue-8` | `@req_carrito` | Un producto sin stock aparece agotado y no se puede agregar | Negativo |
| `@Issue-9` | `@req_carrito` | No se puede agregar una cantidad fuera del rango de 1 a 5 | Negativo |
| `@Issue-10` | `@req_carrito` | Un producto no puede superar 5 unidades aunque se agregue varias veces | Negativo |
| `@Issue-11` | `@req_carrito` | Eliminar un producto del carrito recalcula el subtotal | Positivo |
| `@Issue-12` | `@req_carrito` | Con el carrito vacío no se puede continuar al pago | Negativo |
| `@Issue-13` | `@req_cupones` | Aplicar el cupón LUNA10 escrito en minúsculas | Positivo |
| `@Issue-14` | `@req_cupones` | El cupón ELIXIR20 descuenta el 20% cuando el subtotal llega a $100.000 | Positivo |
| `@Issue-15` | `@req_cupones` | Aplicar un cupón que no existe | Negativo |
| `@Issue-16` | `@req_cupones` | El cupón ELIXIR20 exige una compra mínima de $100.000 | Negativo |
| `@Issue-17` | `@req_cupones` | Solo se permite un cupón por compra | Negativo |
| `@Issue-18` | `@req_pago` | No se puede confirmar el pedido con un correo inválido | Negativo |
| `@Issue-19` | `@req_pago` | Confirmar el pedido con envío normal (número BOT-XXXX, total $30.015) | Positivo |
| `@Issue-20` | `@req_pago` | Confirmar el pedido con envío express (envío $15.000, total $37.015) | Positivo |

Ejemplo de escenario:

```gherkin
@req_cupones @Issue-13
Escenario: Aplicar el cupon LUNA10 escrito en minusculas
  Dado que el Alquimista inicio sesion en La Botica
  Y agrego 2 unidades de "Elixir de Vitalidad" al carrito
  Cuando aplica el cupon "luna10"
  Entonces deberia ver el cupon "LUNA10" aplicado
  Y el descuento deberia ser de "-$8.400"
```

> Los textos de los pasos se escribieron **sin tildes** para que la consola de Windows los muestre bien. Solo conservan
> tilde los datos entre comillas que deben coincidir con lo que muestra la aplicación (por ejemplo "Poción de Invisibilidad"
> o "Cupón no válido").

## 6. Estrategia de localizadores

Se siguió la pirámide de mantenibilidad vista en clase: **`data-testid` > `id` / `name` > CSS > XPath relativo**.
Cada `Target` tiene un comentario con su prioridad.

| Prioridad | Ejemplos del proyecto |
|---|---|
| 1. Atributo de pruebas | `[data-testid='error-login']`, `[data-testid='cupon-aplicado']`, `[data-testid='numero-pedido']`, `[data-testid='direccion']` |
| 2. `id` / `name` estables | `#usuario`, `[name='clave']`, `#buscar`, `[name='cupon']`, `#btn-aplicar-cupon`, `[name='correo']`, `[name='envio'][value='{0}']` |
| 3. CSS simple | `#form-login button[type='submit']`, `.btn-pagar`, `.grilla-productos`, `.sin-resultados` |
| 4. XPath relativo | `//h1[normalize-space()='Tu carrito']` (el título no tiene id ni clase) |
| 4. XPath con ancla (dinámico) | `//article[.//h3[normalize-space()='{0}']]//button[contains(@class,'btn-agregar')]` + `Target.of("Colgante Lunar")` |

Decisiones importantes:

- En la página de pago varios `id` parecen autogenerados (`inp-x92`, `f-dir-01`, `t-771`), por eso allí se usan `name` y `data-testid`.
- Los localizadores dinámicos usan el nombre del producto como **ancla**: se ubica la tarjeta que contiene ese nombre y desde
  ahí se baja al campo de cantidad, al botón, al precio o al stock de esa misma tarjeta.
- **Serenity decide si un localizador es XPath o CSS intentando compilarlo como XPath.** `input[name='correo']` también es
  XPath válido (aunque no encuentra nada), por eso los selectores por atributo se escriben sin etiqueta: `[name='correo']`.

## 7. Cómo ejecutar las pruebas en local

**Requisitos:** JDK 17 o 21, Google Chrome actualizado, IntelliJ IDEA (o VS Code).

1. Abrir la carpeta del proyecto en IntelliJ (*File → Open*) y esperar a que Gradle sincronice.
2. **Desde el Runner:** clic derecho en `src/test/java/botica/runners/Runner.java` → *Run 'Runner'*. Serenity abre Chrome,
   llena los campos, hace clic, cierra la ventana y muestra el resultado en verde.
3. **Desde la terminal** (en PowerShell, el parámetro `-D...` va entre comillas):

```powershell
.\gradlew.bat clean test                                          # los 20 escenarios (Chrome visible)
.\gradlew.bat clean test "-Dcucumber.filter.tags=@Issue-1"        # un solo issue
.\gradlew.bat clean test "-Dcucumber.filter.tags=@req_cupones"    # un requisito
.\gradlew.bat clean test "-Denvironment=headless"                 # sin ventana, como en GitHub Actions
```

En Linux o macOS: `./gradlew clean test -Dcucumber.filter.tags="@Issue-1"`.

4. **Reporte:** abrir `target/site/serenity/index.html`. Muestra los escenarios por requisito, cada paso narrado
   ("Alquimista aplica el cupón luna10") y las capturas de pantalla de cada acción.

Cada escenario abre un navegador nuevo (`serenity.restart.browser.for.each = scenario`): la tienda guarda la sesión, el
carrito y el stock en el `localStorage` del navegador, así que ningún escenario depende de otro.

## 8. GitHub: repositorio, pipeline y trazabilidad

### 8.1 Repositorio

```powershell
git init
git add .
git commit -m "Automatizacion HU-01 La Botica del Alquimista con Serenity BDD"
git branch -M main
git remote add origin https://github.com/<usuario>/<repositorio>.git
git push -u origin main
```

En *Settings → Actions → General → Workflow permissions* se marca **Read and write permissions**, para que el pipeline
pueda comentar y etiquetar los issues.

### 8.2 Pipeline (GitHub Actions)

`.github/workflows/ci.yml` configura Java 17, instala las dependencias con Gradle y ejecuta en **headless**:

```bash
./gradlew clean test -Dcucumber.filter.tags="<tag>" -Denvironment=headless
```

| Cómo se lanza | Qué ejecuta |
|---|---|
| Automático en cada `push` o *pull request* a `main` | Toda la HU-01 |
| Manual: *Actions → Run workflow* con un tag (`@Issue-5`, `@req_pago`, `@HU-01`) | Los escenarios de ese tag |
| Comentando `/ejecutar` en un issue | Solo el escenario de ese issue |

Al terminar:

- El Runner genera `target/cucumber-reports/cucumber.json`. El script `publicar-resultados.sh` lo lee y, por cada escenario
  ejecutado, **comenta en su issue** si pasó o falló (con fecha y enlace a la ejecución) y le pone la etiqueta
  **`status:passed`** o **`status:failed`**. En los `push` solo se escribe el resumen, para no llenar los issues de comentarios.
- En la página de la ejecución aparece la tabla **"Resultados por issue"**.
- Si el tag no selecciona ningún escenario (por ejemplo, mal escrito), el pipeline falla en lugar de quedar en verde con 0 pruebas.
- Se publican los artefactos `reporte-serenity-<n>` (reporte HTML) y `resultados-pruebas-<n>` (cucumber.json y reportes de Gradle).

### 8.3 Project e issues (TestCases)

1. Crear un **GitHub Project** (*Projects → New project*) y vincularlo al repositorio (*Projects → Link a project*).
2. Instalar GitHub CLI (<https://cli.github.com>) y autenticarse:
   ```powershell
   gh auth login
   gh auth refresh -s project
   ```
3. Crear los 20 issues:
   ```powershell
   .\crear-issues.ps1 -Simular                                        # muestra qué va a crear
   .\crear-issues.ps1 -Repo "<usuario>/<repositorio>" -Proyecto <N>   # los crea y los agrega al Project
   ```
   Si Windows bloquea el script: `powershell -ExecutionPolicy Bypass -File .\crear-issues.ps1 ...`

Cada issue se titula `[@Issue-N] <nombre del escenario>` y lleva:

- Etiquetas `test-case`, `HU-01`, el requisito (`req_login`, `req_catalogo`, `req_carrito`, `req_cupones`, `req_pago`) y `positivo` o `negativo`.
- En el cuerpo: criterio de aceptación, tipo de prueba, tags, el escenario Gherkin y cómo ejecutarlo.
- Si el script se ejecuta dos veces, no duplica issues.

Así queda la trazabilidad completa: **requisito (`@req_...`) → escenario Gherkin → issue (`@Issue-N`) → resultado del pipeline (`status:passed`)**.

## 9. Ajustes sobre la plantilla base

- **`build.gradle`**
  - Se agregó `junit-vintage-engine`: Gradle usa JUnit Platform y el Runner es de JUnit 4. Sin ese motor, Gradle no
    encuentra el Runner y no ejecuta ningún escenario.
  - `serenity-junit` se alineó a 4.2.32, igual que el resto de Serenity (la guía pide la misma versión para todo).
  - Compilación en UTF-8, para que los textos con tilde de la aplicación se lean bien en Windows.
- **`serenity.conf`**
  - Entorno `chrome` (por defecto, con ventana) y entorno `headless` (para GitHub Actions).
  - Ventana de 1920×1080 y un navegador nuevo por escenario.
- **`serenity.properties`**: colores en consola y codificación UTF-8 de los `.feature`.
- **`.gitignore`**: sí se suben `gradlew`, `gradlew.bat` y `gradle/wrapper/gradle-wrapper.jar`, que el pipeline necesita.

## 10. Hallazgos en la aplicación

Durante el análisis aparecieron dos comportamientos que **no cumplen** los criterios de la HU-01. No hacen parte de los
20 escenarios (que pasan), pero quedan documentados como defectos encontrados:

1. **El IVA se calcula antes del descuento** (Funcionalidad 3, criterio 5). Con 2 × Elixir de Vitalidad y el cupón `LUNA10`
   (subtotal $84.000, descuento −$8.400), la aplicación cobra IVA de **$15.960** (19% de $84.000) y total **$99.560**.
   Lo correcto sería IVA de **$14.364** (19% de $75.600) y total **$97.964**.
2. **El carrito permite superar el stock al modificar la cantidad** (Funcionalidad 1, criterio 6). Con 1 × Pluma de Fénix
   (stock 1), cambiar la cantidad a 3 en el carrito muestra "Cantidad actualizada" en lugar de
   "No hay suficiente stock disponible".
