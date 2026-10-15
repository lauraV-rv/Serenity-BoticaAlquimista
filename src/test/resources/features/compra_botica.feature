# language: es
@HU-01
Característica: HU-01 Compra en La Botica del Alquimista
  Como cliente de La Botica del Alquimista
  quiero buscar productos, armar mi carrito, aplicar un cupon de descuento y confirmar mi pedido
  para comprar mis articulos de alquimia pagando el valor correcto.

  # ---------------------------------------------------------------
  # Acceso
  # ---------------------------------------------------------------
  @req_login @Issue-1
  Escenario: Inicio de sesion exitoso con credenciales validas
    Dado que el Alquimista esta en la pagina de inicio de sesion
    Cuando inicia sesion con el usuario "alquimista" y la contrasena "pocion123"
    Entonces deberia ver el catalogo de productos

  @req_login @Issue-2
  Escenario: Inicio de sesion fallido con contrasena incorrecta
    Dado que el Alquimista esta en la pagina de inicio de sesion
    Cuando inicia sesion con el usuario "alquimista" y la contrasena "veneno999"
    Entonces deberia ver el mensaje de error "Usuario o contraseña incorrectos"

  # ---------------------------------------------------------------
  # Funcionalidad 1: Catalogo
  # ---------------------------------------------------------------
  @req_catalogo @Issue-3
  Escenario: El catalogo muestra la categoria, el precio y el stock de cada producto
    Dado que el Alquimista inicio sesion en La Botica
    Cuando consulta el producto "Pluma de Fénix" en el catalogo
    Entonces el producto "Pluma de Fénix" deberia mostrar la categoria "Ingredientes", el precio "$95.000" y un stock de 1 unidad

  @req_catalogo @Issue-4
  Escenario: Filtrar el catalogo por categoria
    Dado que el Alquimista inicio sesion en La Botica
    Cuando filtra el catalogo por la categoria "Amuletos"
    Entonces deberia ver unicamente productos de la categoria "Amuletos"
    Y deberia ver 3 productos en el catalogo

  @req_catalogo @Issue-5
  Escenario: Buscar productos por nombre con coincidencia parcial y sin distinguir mayusculas
    Dado que el Alquimista inicio sesion en La Botica
    Cuando busca productos con el texto "LUNA"
    Entonces deberia ver unicamente productos cuyo nombre contiene "luna"
    Y deberia ver 2 productos en el catalogo

  @req_catalogo @Issue-6
  Escenario: Buscar un producto que no existe en el catalogo
    Dado que el Alquimista inicio sesion en La Botica
    Cuando busca productos con el texto "piedra filosofal"
    Entonces deberia ver el resultado de busqueda "No se encontraron productos."

  # ---------------------------------------------------------------
  # Funcionalidad 1: Carrito
  # ---------------------------------------------------------------
  @req_carrito @Issue-7
  Escenario: No se puede agregar una cantidad mayor al stock disponible
    Dado que el Alquimista inicio sesion en La Botica
    Cuando intenta agregar 3 unidades de "Poción de Invisibilidad" al carrito
    Entonces deberia ver el aviso "No hay suficiente stock disponible"
    Y el carrito deberia tener 0 unidades

  @req_carrito @Issue-8
  Escenario: Un producto sin stock aparece agotado y no se puede agregar
    Dado que el Alquimista inicio sesion en La Botica
    Cuando consulta el producto "Colgante Lunar" en el catalogo
    Entonces el producto "Colgante Lunar" deberia aparecer como agotado y sin opcion de agregarlo

  @req_carrito @Issue-9
  Escenario: No se puede agregar una cantidad fuera del rango de 1 a 5
    Dado que el Alquimista inicio sesion en La Botica
    Cuando intenta agregar 6 unidades de "Raíz de Mandrágora" al carrito
    Entonces deberia ver el aviso "La cantidad debe estar entre 1 y 5"
    Y el carrito deberia tener 0 unidades

  @req_carrito @Issue-10
  Escenario: Un producto no puede superar 5 unidades aunque se agregue varias veces
    Dado que el Alquimista inicio sesion en La Botica
    Y agrego 3 unidades de "Raíz de Mandrágora" al carrito
    Cuando intenta agregar 3 unidades de "Raíz de Mandrágora" al carrito
    Entonces deberia ver el aviso "Solo puedes llevar hasta 5 unidades de este producto"
    Y el carrito deberia tener 3 unidades

  @req_carrito @Issue-11
  Escenario: Eliminar un producto del carrito recalcula el subtotal
    Dado que el Alquimista inicio sesion en La Botica
    Y agrego 2 unidades de "Poción de Sueño Profundo" al carrito
    Y agrego 1 unidad de "Amuleto de Protección" al carrito
    Cuando elimina "Amuleto de Protección" de su carrito
    Entonces deberia ver el mensaje del carrito "Se eliminó Amuleto de Protección del carrito"
    Y el subtotal del carrito deberia ser "$37.000"

  @req_carrito @Issue-12
  Escenario: Con el carrito vacio no se puede continuar al pago
    Dado que el Alquimista inicio sesion en La Botica
    Cuando abre su carrito sin haber agregado productos
    Entonces deberia ver la indicacion "Tu carrito está vacío"
    Y no deberia poder continuar al pago

  # ---------------------------------------------------------------
  # Funcionalidad 2: Cupones de descuento
  # ---------------------------------------------------------------
  @req_cupones @Issue-13
  Escenario: Aplicar el cupon LUNA10 escrito en minusculas
    Dado que el Alquimista inicio sesion en La Botica
    Y agrego 2 unidades de "Elixir de Vitalidad" al carrito
    Cuando aplica el cupon "luna10"
    Entonces deberia ver el cupon "LUNA10" aplicado
    Y el descuento deberia ser de "-$8.400"

  @req_cupones @Issue-14
  Escenario: El cupon ELIXIR20 descuenta el 20% cuando el subtotal llega a $100.000
    Dado que el Alquimista inicio sesion en La Botica
    Y agrego 2 unidades de "Poción de Invisibilidad" al carrito
    Cuando aplica el cupon "ELIXIR20"
    Entonces deberia ver el cupon "ELIXIR20" aplicado
    Y el descuento deberia ser de "-$26.000"

  @req_cupones @Issue-15
  Escenario: Aplicar un cupon que no existe
    Dado que el Alquimista inicio sesion en La Botica
    Y agrego 1 unidad de "Polvo de Luna" al carrito
    Cuando aplica el cupon "DRAGON50"
    Entonces deberia ver el mensaje del cupon "Cupón no válido"

  @req_cupones @Issue-16
  Escenario: El cupon ELIXIR20 exige una compra minima de $100.000
    Dado que el Alquimista inicio sesion en La Botica
    Y agrego 1 unidad de "Raíz de Mandrágora" al carrito
    Cuando aplica el cupon "ELIXIR20"
    Entonces deberia ver el mensaje del cupon "Este cupón requiere una compra mínima de $100.000"

  @req_cupones @Issue-17
  Escenario: Solo se permite un cupon por compra
    Dado que el Alquimista inicio sesion en La Botica
    Y agrego 2 unidades de "Elixir de Vitalidad" al carrito
    Y aplico el cupon "LUNA10"
    Cuando aplica el cupon "ELIXIR20"
    Entonces deberia ver el mensaje del cupon "Solo se permite un cupón por compra"
    Y deberia ver el cupon "LUNA10" aplicado

  # ---------------------------------------------------------------
  # Funcionalidad 3: Pago y pedido
  # ---------------------------------------------------------------
  @req_pago @Issue-18
  Escenario: No se puede confirmar el pedido con un correo invalido
    Dado que el Alquimista inicio sesion en La Botica
    Y agrego 1 unidad de "Poción de Sueño Profundo" al carrito
    Cuando intenta confirmar el pedido con los siguientes datos de envio:
      | nombre         | correo             | direccion       | ciudad | telefono   | envio  |
      | Nicolas Flamel | nflamel-botica.com | Calle 13 # 7-77 | Bogota | 3001234567 | normal |
    Entonces deberia ver el mensaje del pago "Revisa los campos marcados"
    Y deberia ver el error "Ingresa un correo válido" en el campo "correo"

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
