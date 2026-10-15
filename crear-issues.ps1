<#
.SYNOPSIS
    Crea en GitHub los 20 issues (casos de prueba) de la HU-01 de La Botica del Alquimista,
    con trazabilidad 1 a 1 hacia los escenarios de src/test/resources/features/compra_botica.feature.

.DESCRIPTION
    Cada issue lleva en el titulo el tag del escenario ([@Issue-N]); asi el pipeline (ci.yml)
    puede ejecutar el escenario cuando se comenta /ejecutar en el issue.
    Requisitos: GitHub CLI (https://cli.github.com) autenticado con "gh auth login".

.PARAMETER Repo
    Repositorio destino "usuario/repositorio". Si se omite, se usa el repositorio de la carpeta actual.

.PARAMETER Proyecto
    (Opcional) Numero del GitHub Project donde se agregaran los issues.
    Requiere el permiso de proyectos: gh auth refresh -s project

.PARAMETER Owner
    (Opcional) Dueno del GitHub Project (usuario u organizacion). Por defecto "@me".

.PARAMETER Simular
    Muestra lo que se crearia sin crear nada.

.EXAMPLE
    .\crear-issues.ps1
.EXAMPLE
    .\crear-issues.ps1 -Repo "mi-usuario/BoticaAlquimista" -Proyecto 1
#>
param(
    [string]$Repo = "",
    [string]$Proyecto = "",
    [string]$Owner = "@me",
    [switch]$Simular
)

$ErrorActionPreference = "Stop"
[Console]::OutputEncoding = [System.Text.Encoding]::UTF8
$OutputEncoding = [System.Text.Encoding]::UTF8

if (-not (Get-Command gh -ErrorAction SilentlyContinue)) {
    Write-Host "No se encontro GitHub CLI (gh). Instalalo desde https://cli.github.com y ejecuta 'gh auth login'." -ForegroundColor Red
    exit 1
}

$repoArgs = @()
if ($Repo -ne "") { $repoArgs = @("--repo", $Repo) }

# ---------------------------------------------------------------------------
# Casos de prueba (uno por escenario del feature)
# ---------------------------------------------------------------------------
$casos = @(
    @{
        Numero    = 1
        Tag       = '@Issue-1'
        Requisito = '@req_login'
        Area      = 'Acceso'
        Tipo      = 'Positivo'
        Titulo    = 'Inicio de sesion exitoso con credenciales validas'
        Criterio  = 'Acceso 1: para comprar hay que iniciar sesion con usuario y contrasena validos.'
        Gherkin   = @'
@req_login @Issue-1
Escenario: Inicio de sesion exitoso con credenciales validas
  Dado que el Alquimista esta en la pagina de inicio de sesion
  Cuando inicia sesion con el usuario "alquimista" y la contrasena "pocion123"
  Entonces deberia ver el catalogo de productos
'@
    },
    @{
        Numero    = 2
        Tag       = '@Issue-2'
        Requisito = '@req_login'
        Area      = 'Acceso'
        Tipo      = 'Negativo'
        Titulo    = 'Inicio de sesion fallido con contrasena incorrecta'
        Criterio  = 'Acceso 2: si las credenciales son incorrectas, se muestra el mensaje "Usuario o contrasena incorrectos".'
        Gherkin   = @'
@req_login @Issue-2
Escenario: Inicio de sesion fallido con contrasena incorrecta
  Dado que el Alquimista esta en la pagina de inicio de sesion
  Cuando inicia sesion con el usuario "alquimista" y la contrasena "veneno999"
  Entonces deberia ver el mensaje de error "Usuario o contraseña incorrectos"
'@
    },
    @{
        Numero    = 3
        Tag       = '@Issue-3'
        Requisito = '@req_catalogo'
        Area      = 'Funcionalidad 1 - Catalogo'
        Tipo      = 'Positivo'
        Titulo    = 'El catalogo muestra la categoria, el precio y el stock de cada producto'
        Criterio  = 'Funcionalidad 1, criterio 1: el catalogo muestra el nombre, la categoria, el precio y el stock disponible de cada producto.'
        Gherkin   = @'
@req_catalogo @Issue-3
Escenario: El catalogo muestra la categoria, el precio y el stock de cada producto
  Dado que el Alquimista inicio sesion en La Botica
  Cuando consulta el producto "Pluma de Fénix" en el catalogo
  Entonces el producto "Pluma de Fénix" deberia mostrar la categoria "Ingredientes", el precio "$95.000" y un stock de 1 unidad
'@
    },
    @{
        Numero    = 4
        Tag       = '@Issue-4'
        Requisito = '@req_catalogo'
        Area      = 'Funcionalidad 1 - Catalogo'
        Tipo      = 'Positivo'
        Titulo    = 'Filtrar el catalogo por categoria'
        Criterio  = 'Funcionalidad 1, criterio 2: se puede filtrar por categoria (Pociones, Ingredientes, Amuletos).'
        Gherkin   = @'
@req_catalogo @Issue-4
Escenario: Filtrar el catalogo por categoria
  Dado que el Alquimista inicio sesion en La Botica
  Cuando filtra el catalogo por la categoria "Amuletos"
  Entonces deberia ver unicamente productos de la categoria "Amuletos"
  Y deberia ver 3 productos en el catalogo
'@
    },
    @{
        Numero    = 5
        Tag       = '@Issue-5'
        Requisito = '@req_catalogo'
        Area      = 'Funcionalidad 1 - Catalogo'
        Tipo      = 'Positivo'
        Titulo    = 'Buscar productos por nombre con coincidencia parcial y sin distinguir mayusculas'
        Criterio  = 'Funcionalidad 1, criterio 3: se puede buscar por nombre; la busqueda acepta coincidencia parcial y no distingue mayusculas de minusculas.'
        Gherkin   = @'
@req_catalogo @Issue-5
Escenario: Buscar productos por nombre con coincidencia parcial y sin distinguir mayusculas
  Dado que el Alquimista inicio sesion en La Botica
  Cuando busca productos con el texto "LUNA"
  Entonces deberia ver unicamente productos cuyo nombre contiene "luna"
  Y deberia ver 2 productos en el catalogo
'@
    },
    @{
        Numero    = 6
        Tag       = '@Issue-6'
        Requisito = '@req_catalogo'
        Area      = 'Funcionalidad 1 - Catalogo'
        Tipo      = 'Negativo'
        Titulo    = 'Buscar un producto que no existe en el catalogo'
        Criterio  = 'Funcionalidad 1, criterio 3: una busqueda sin coincidencias no muestra productos.'
        Gherkin   = @'
@req_catalogo @Issue-6
Escenario: Buscar un producto que no existe en el catalogo
  Dado que el Alquimista inicio sesion en La Botica
  Cuando busca productos con el texto "piedra filosofal"
  Entonces deberia ver el resultado de busqueda "No se encontraron productos."
'@
    },
    @{
        Numero    = 7
        Tag       = '@Issue-7'
        Requisito = '@req_carrito'
        Area      = 'Funcionalidad 1 - Carrito'
        Tipo      = 'Negativo'
        Titulo    = 'No se puede agregar una cantidad mayor al stock disponible'
        Criterio  = 'Funcionalidad 1, criterio 6: la cantidad nunca puede superar el stock disponible; si se intenta, se muestra "No hay suficiente stock disponible".'
        Gherkin   = @'
@req_carrito @Issue-7
Escenario: No se puede agregar una cantidad mayor al stock disponible
  Dado que el Alquimista inicio sesion en La Botica
  Cuando intenta agregar 3 unidades de "Poción de Invisibilidad" al carrito
  Entonces deberia ver el aviso "No hay suficiente stock disponible"
  Y el carrito deberia tener 0 unidades
'@
    },
    @{
        Numero    = 8
        Tag       = '@Issue-8'
        Requisito = '@req_carrito'
        Area      = 'Funcionalidad 1 - Carrito'
        Tipo      = 'Negativo'
        Titulo    = 'Un producto sin stock aparece agotado y no se puede agregar'
        Criterio  = 'Funcionalidad 1, criterio 7: un producto sin stock aparece como "Agotado" y no se puede agregar.'
        Gherkin   = @'
@req_carrito @Issue-8
Escenario: Un producto sin stock aparece agotado y no se puede agregar
  Dado que el Alquimista inicio sesion en La Botica
  Cuando consulta el producto "Colgante Lunar" en el catalogo
  Entonces el producto "Colgante Lunar" deberia aparecer como agotado y sin opcion de agregarlo
'@
    },
    @{
        Numero    = 9
        Tag       = '@Issue-9'
        Requisito = '@req_carrito'
        Area      = 'Funcionalidad 1 - Carrito'
        Tipo      = 'Negativo'
        Titulo    = 'No se puede agregar una cantidad fuera del rango de 1 a 5'
        Criterio  = 'Funcionalidad 1, criterio 4: se puede agregar un producto con una cantidad entre 1 y 5.'
        Gherkin   = @'
@req_carrito @Issue-9
Escenario: No se puede agregar una cantidad fuera del rango de 1 a 5
  Dado que el Alquimista inicio sesion en La Botica
  Cuando intenta agregar 6 unidades de "Raíz de Mandrágora" al carrito
  Entonces deberia ver el aviso "La cantidad debe estar entre 1 y 5"
  Y el carrito deberia tener 0 unidades
'@
    },
    @{
        Numero    = 10
        Tag       = '@Issue-10'
        Requisito = '@req_carrito'
        Area      = 'Funcionalidad 1 - Carrito'
        Tipo      = 'Negativo'
        Titulo    = 'Un producto no puede superar 5 unidades aunque se agregue varias veces'
        Criterio  = 'Funcionalidad 1, criterio 5: el total de unidades de un producto en el carrito nunca puede superar 5, aunque se agregue varias veces.'
        Gherkin   = @'
@req_carrito @Issue-10
Escenario: Un producto no puede superar 5 unidades aunque se agregue varias veces
  Dado que el Alquimista inicio sesion en La Botica
  Y agrego 3 unidades de "Raíz de Mandrágora" al carrito
  Cuando intenta agregar 3 unidades de "Raíz de Mandrágora" al carrito
  Entonces deberia ver el aviso "Solo puedes llevar hasta 5 unidades de este producto"
  Y el carrito deberia tener 3 unidades
'@
    },
    @{
        Numero    = 11
        Tag       = '@Issue-11'
        Requisito = '@req_carrito'
        Area      = 'Funcionalidad 1 - Carrito'
        Tipo      = 'Positivo'
        Titulo    = 'Eliminar un producto del carrito recalcula el subtotal'
        Criterio  = 'Funcionalidad 1, criterios 8 y 9: cada linea muestra su subtotal, el carrito suma las lineas y se puede eliminar un producto.'
        Gherkin   = @'
@req_carrito @Issue-11
Escenario: Eliminar un producto del carrito recalcula el subtotal
  Dado que el Alquimista inicio sesion en La Botica
  Y agrego 2 unidades de "Poción de Sueño Profundo" al carrito
  Y agrego 1 unidad de "Amuleto de Protección" al carrito
  Cuando elimina "Amuleto de Protección" de su carrito
  Entonces deberia ver el mensaje del carrito "Se eliminó Amuleto de Protección del carrito"
  Y el subtotal del carrito deberia ser "$37.000"
'@
    },
    @{
        Numero    = 12
        Tag       = '@Issue-12'
        Requisito = '@req_carrito'
        Area      = 'Funcionalidad 1 - Carrito'
        Tipo      = 'Negativo'
        Titulo    = 'Con el carrito vacio no se puede continuar al pago'
        Criterio  = 'Funcionalidad 1, criterio 10: con el carrito vacio se muestra "Tu carrito esta vacio" y no se puede ir al pago.'
        Gherkin   = @'
@req_carrito @Issue-12
Escenario: Con el carrito vacio no se puede continuar al pago
  Dado que el Alquimista inicio sesion en La Botica
  Cuando abre su carrito sin haber agregado productos
  Entonces deberia ver la indicacion "Tu carrito está vacío"
  Y no deberia poder continuar al pago
'@
    },
    @{
        Numero    = 13
        Tag       = '@Issue-13'
        Requisito = '@req_cupones'
        Area      = 'Funcionalidad 2 - Cupones de descuento'
        Tipo      = 'Positivo'
        Titulo    = 'Aplicar el cupon LUNA10 escrito en minusculas'
        Criterio  = 'Funcionalidad 2, criterios 1 y 3: el cupon LUNA10 da 10% de descuento sobre el subtotal y los codigos no distinguen mayusculas de minusculas.'
        Gherkin   = @'
@req_cupones @Issue-13
Escenario: Aplicar el cupon LUNA10 escrito en minusculas
  Dado que el Alquimista inicio sesion en La Botica
  Y agrego 2 unidades de "Elixir de Vitalidad" al carrito
  Cuando aplica el cupon "luna10"
  Entonces deberia ver el cupon "LUNA10" aplicado
  Y el descuento deberia ser de "-$8.400"
'@
    },
    @{
        Numero    = 14
        Tag       = '@Issue-14'
        Requisito = '@req_cupones'
        Area      = 'Funcionalidad 2 - Cupones de descuento'
        Tipo      = 'Positivo'
        Titulo    = 'El cupon ELIXIR20 descuenta el 20% cuando el subtotal llega a $100.000'
        Criterio  = 'Funcionalidad 2, criterio 2: ELIXIR20 da 20% de descuento si el subtotal es de $100.000 o mas.'
        Gherkin   = @'
@req_cupones @Issue-14
Escenario: El cupon ELIXIR20 descuenta el 20% cuando el subtotal llega a $100.000
  Dado que el Alquimista inicio sesion en La Botica
  Y agrego 2 unidades de "Poción de Invisibilidad" al carrito
  Cuando aplica el cupon "ELIXIR20"
  Entonces deberia ver el cupon "ELIXIR20" aplicado
  Y el descuento deberia ser de "-$26.000"
'@
    },
    @{
        Numero    = 15
        Tag       = '@Issue-15'
        Requisito = '@req_cupones'
        Area      = 'Funcionalidad 2 - Cupones de descuento'
        Tipo      = 'Negativo'
        Titulo    = 'Aplicar un cupon que no existe'
        Criterio  = 'Funcionalidad 2, criterio 4: un codigo inexistente muestra "Cupon no valido".'
        Gherkin   = @'
@req_cupones @Issue-15
Escenario: Aplicar un cupon que no existe
  Dado que el Alquimista inicio sesion en La Botica
  Y agrego 1 unidad de "Polvo de Luna" al carrito
  Cuando aplica el cupon "DRAGON50"
  Entonces deberia ver el mensaje del cupon "Cupón no válido"
'@
    },
    @{
        Numero    = 16
        Tag       = '@Issue-16'
        Requisito = '@req_cupones'
        Area      = 'Funcionalidad 2 - Cupones de descuento'
        Tipo      = 'Negativo'
        Titulo    = 'El cupon ELIXIR20 exige una compra minima de $100.000'
        Criterio  = 'Funcionalidad 2, criterio 2: si el subtotal no llega a $100.000, ELIXIR20 muestra "Este cupon requiere una compra minima de $100.000".'
        Gherkin   = @'
@req_cupones @Issue-16
Escenario: El cupon ELIXIR20 exige una compra minima de $100.000
  Dado que el Alquimista inicio sesion en La Botica
  Y agrego 1 unidad de "Raíz de Mandrágora" al carrito
  Cuando aplica el cupon "ELIXIR20"
  Entonces deberia ver el mensaje del cupon "Este cupón requiere una compra mínima de $100.000"
'@
    },
    @{
        Numero    = 17
        Tag       = '@Issue-17'
        Requisito = '@req_cupones'
        Area      = 'Funcionalidad 2 - Cupones de descuento'
        Tipo      = 'Negativo'
        Titulo    = 'Solo se permite un cupon por compra'
        Criterio  = 'Funcionalidad 2, criterio 5: solo se permite un cupon por compra; si ya hay uno, se muestra "Solo se permite un cupon por compra".'
        Gherkin   = @'
@req_cupones @Issue-17
Escenario: Solo se permite un cupon por compra
  Dado que el Alquimista inicio sesion en La Botica
  Y agrego 2 unidades de "Elixir de Vitalidad" al carrito
  Y aplico el cupon "LUNA10"
  Cuando aplica el cupon "ELIXIR20"
  Entonces deberia ver el mensaje del cupon "Solo se permite un cupón por compra"
  Y deberia ver el cupon "LUNA10" aplicado
'@
    },
    @{
        Numero    = 18
        Tag       = '@Issue-18'
        Requisito = '@req_pago'
        Area      = 'Funcionalidad 3 - Pago y pedido'
        Tipo      = 'Negativo'
        Titulo    = 'No se puede confirmar el pedido con un correo invalido'
        Criterio  = 'Funcionalidad 3, criterios 1 y 2: los datos de envio son obligatorios y el correo debe tener un formato valido.'
        Gherkin   = @'
@req_pago @Issue-18
Escenario: No se puede confirmar el pedido con un correo invalido
  Dado que el Alquimista inicio sesion en La Botica
  Y agrego 1 unidad de "Poción de Sueño Profundo" al carrito
  Cuando intenta confirmar el pedido con los siguientes datos de envio:
    | nombre         | correo             | direccion       | ciudad | telefono   | envio  |
    | Nicolas Flamel | nflamel-botica.com | Calle 13 # 7-77 | Bogota | 3001234567 | normal |
  Entonces deberia ver el mensaje del pago "Revisa los campos marcados"
  Y deberia ver el error "Ingresa un correo válido" en el campo "correo"
'@
    },
    @{
        Numero    = 19
        Tag       = '@Issue-19'
        Requisito = '@req_pago'
        Area      = 'Funcionalidad 3 - Pago y pedido'
        Tipo      = 'Positivo'
        Titulo    = 'Confirmar el pedido con envio normal'
        Criterio  = 'Funcionalidad 3, criterios 4 a 7: envio normal de $8.000, IVA del 19%, total con separador de miles, numero de pedido BOT-XXXX y carrito vacio al confirmar.'
        Gherkin   = @'
@req_pago @Issue-19
Escenario: Confirmar el pedido con envio normal
  Dado que el Alquimista inicio sesion en La Botica
  Y agrego 1 unidad de "Poción de Sueño Profundo" al carrito
  Cuando confirma el pedido con los siguientes datos de envio:
    | nombre         | correo             | direccion       | ciudad | telefono   | envio  |
    | Nicolas Flamel | nflamel@botica.com | Calle 13 # 7-77 | Bogota | 3001234567 | normal |
  Entonces deberia ver un numero de pedido con formato BOT-XXXX
  Y el total pagado deberia ser "$30.015"
  Y el carrito deberia tener 0 unidades
'@
    },
    @{
        Numero    = 20
        Tag       = '@Issue-20'
        Requisito = '@req_pago'
        Area      = 'Funcionalidad 3 - Pago y pedido'
        Tipo      = 'Positivo'
        Titulo    = 'Confirmar el pedido con envio express'
        Criterio  = 'Funcionalidad 3, criterios 4 a 7: el envio express cuesta $15.000 siempre y se suma al total con IVA.'
        Gherkin   = @'
@req_pago @Issue-20
Escenario: Confirmar el pedido con envio express
  Dado que el Alquimista inicio sesion en La Botica
  Y agrego 1 unidad de "Poción de Sueño Profundo" al carrito
  Cuando confirma el pedido con los siguientes datos de envio:
    | nombre         | correo             | direccion       | ciudad | telefono   | envio   |
    | Nicolas Flamel | nflamel@botica.com | Calle 13 # 7-77 | Bogota | 3001234567 | express |
  Entonces deberia ver un numero de pedido con formato BOT-XXXX
  Y el envio cobrado deberia ser "$15.000"
  Y el total pagado deberia ser "$37.015"
'@
    }
)

$plantilla = @'
## Caso de prueba __TAG__

| Campo | Valor |
|---|---|
| Historia de usuario | HU-01 Compra en La Botica del Alquimista |
| Requisito | `__REQ__` - __AREA__ |
| Criterio de aceptacion | __CRITERIO__ |
| Tipo de prueba | __TIPO__ |
| Tags del escenario | `__REQ__` `__TAG__` |
| Feature | `src/test/resources/features/compra_botica.feature` |

### Escenario Gherkin

```gherkin
__GHERKIN__
```

### Como ejecutarlo

- **Local:** `./gradlew test -Dcucumber.filter.tags="__TAG__"`
- **GitHub Actions:** Actions > *Pruebas Front-End - La Botica del Alquimista* > Run workflow > tag `__TAG__`
- **Desde este issue:** comentar `/ejecutar` (el pipeline responde aqui con el resultado)
'@

# ---------------------------------------------------------------------------
# Etiquetas (labels) usadas para clasificar los casos
# ---------------------------------------------------------------------------
$etiquetas = @(
    @{ Nombre = "test-case";    Color = "5319E7"; Descripcion = "Caso de prueba automatizado con Serenity BDD" },
    @{ Nombre = "HU-01";        Color = "0E8A16"; Descripcion = "Compra en La Botica del Alquimista" },
    @{ Nombre = "req_login";    Color = "1D76DB"; Descripcion = "Acceso" },
    @{ Nombre = "req_catalogo"; Color = "1D76DB"; Descripcion = "Catalogo" },
    @{ Nombre = "req_carrito";  Color = "1D76DB"; Descripcion = "Carrito" },
    @{ Nombre = "req_cupones";  Color = "1D76DB"; Descripcion = "Cupones de descuento" },
    @{ Nombre = "req_pago";     Color = "1D76DB"; Descripcion = "Pago y pedido" },
    @{ Nombre = "positivo";     Color = "C2E0C6"; Descripcion = "Escenario positivo" },
    @{ Nombre = "negativo";     Color = "F9D0C4"; Descripcion = "Escenario negativo" }
)

if (-not $Simular) {
    Write-Host "Creando etiquetas..." -ForegroundColor Cyan
    foreach ($e in $etiquetas) {
        gh label create $e.Nombre --color $e.Color --description $e.Descripcion --force @repoArgs | Out-Null
        if ($LASTEXITCODE -ne 0) { throw "No se pudo crear la etiqueta $($e.Nombre)" }
    }

    # Titulos existentes, para no duplicar issues si el script se ejecuta dos veces
    $existentes = @(gh issue list @repoArgs --state all --limit 500 --json title --jq ".[].title")
} else {
    $existentes = @()
}

$temporal = Join-Path ([System.IO.Path]::GetTempPath()) "issue-botica.md"
$utf8SinBom = New-Object System.Text.UTF8Encoding($false)
$creados = @()

foreach ($c in $casos) {
    $titulo = "[" + $c.Tag + "] " + $c.Titulo
    $prefijo = "[" + $c.Tag + "]"

    if (@($existentes | Where-Object { $_ -like ("*" + [WildcardPattern]::Escape($prefijo) + "*") }).Count -gt 0) {
        Write-Host ("Ya existe un issue con " + $prefijo + "; se omite.") -ForegroundColor Yellow
        continue
    }

    $cuerpo = $plantilla.Replace("__TAG__", $c.Tag).Replace("__REQ__", $c.Requisito).Replace("__AREA__", $c.Area).Replace("__CRITERIO__", $c.Criterio).Replace("__TIPO__", $c.Tipo).Replace("__GHERKIN__", $c.Gherkin)
    $tipoEtiqueta = $c.Tipo.ToLower()
    $requisitoEtiqueta = $c.Requisito.TrimStart("@")

    if ($Simular) {
        Write-Host ("[Simulacion] " + $titulo + "  (etiquetas: test-case, HU-01, " + $requisitoEtiqueta + ", " + $tipoEtiqueta + ")")
        continue
    }

    [System.IO.File]::WriteAllText($temporal, $cuerpo, $utf8SinBom)
    $url = gh issue create @repoArgs --title $titulo --body-file $temporal --label "test-case" --label "HU-01" --label $requisitoEtiqueta --label $tipoEtiqueta
    if ($LASTEXITCODE -ne 0) { throw "No se pudo crear el issue $($c.Tag)" }
    $url = ($url | Select-Object -Last 1).Trim()
    Write-Host ("Creado " + $titulo + " -> " + $url) -ForegroundColor Green
    $creados += [PSCustomObject]@{ Tag = $c.Tag; Requisito = $c.Requisito; Issue = $url }

    if ($Proyecto -ne "") {
        gh project item-add $Proyecto --owner $Owner --url $url | Out-Null
        if ($LASTEXITCODE -ne 0) {
            Write-Host ("  No se pudo agregar al proyecto " + $Proyecto + ". Ejecuta: gh auth refresh -s project") -ForegroundColor Yellow
        }
    }
}

if (Test-Path $temporal) { Remove-Item $temporal -Force }

if ($creados.Count -gt 0) {
    Write-Host ""
    Write-Host "Trazabilidad escenario -> issue:" -ForegroundColor Cyan
    foreach ($x in $creados) {
        Write-Host ("  " + $x.Tag.PadRight(11) + $x.Requisito.PadRight(15) + $x.Issue)
    }
}
Write-Host "Listo." -ForegroundColor Cyan
