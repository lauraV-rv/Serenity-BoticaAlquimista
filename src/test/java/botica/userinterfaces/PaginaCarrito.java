package botica.userinterfaces;

import net.serenitybdd.screenplay.targets.Target;

/**
 * Localizadores de la vista del carrito y del bloque de cupones (carrito.html).
 *
 * Nota: los selectores CSS por atributo se escriben sin etiqueta ([name='x'] y no input[name='x'])
 * porque Serenity trata como XPath todo texto que compile como XPath, e input[name='x'] lo es.
 */
public class PaginaCarrito {

    private PaginaCarrito() {
    }

    // Prioridad 4: XPath por texto (el h1 no tiene id ni clase)
    public static final Target TITULO = Target.the("titulo del carrito")
            .locatedBy("//h1[normalize-space()='Tu carrito']");

    // Prioridad 2: name
    public static final Target CAMPO_CUPON = Target.the("campo del cupon")
            .locatedBy("[name='cupon']");

    // Prioridad 2: id
    public static final Target BOTON_APLICAR_CUPON = Target.the("boton aplicar cupon")
            .locatedBy("#btn-aplicar-cupon");

    // Prioridad 2: id
    public static final Target MENSAJE_CUPON = Target.the("mensaje del cupon")
            .locatedBy("#mensaje-cupon");

    // Prioridad 1: data-testid
    public static final Target CUPON_APLICADO = Target.the("cupon aplicado")
            .locatedBy("[data-testid='cupon-aplicado']");

    // Prioridad 1: data-testid
    public static final Target DESCUENTO = Target.the("valor del descuento")
            .locatedBy("[data-testid='descuento']");

    // Prioridad 1: data-testid
    public static final Target MENSAJE_CARRITO = Target.the("mensaje del carrito")
            .locatedBy("[data-testid='mensaje-carrito']");

    // Prioridad 1: data-testid
    public static final Target CARRITO_VACIO = Target.the("indicacion de carrito vacio")
            .locatedBy("[data-testid='carrito-vacio']");

    // Prioridad 2: id
    public static final Target SUBTOTAL = Target.the("subtotal del carrito")
            .locatedBy("#subtotal");

    // Prioridad 2: atributo aria-label con el nombre del producto. {0} = nombre del producto
    public static final Target BOTON_ELIMINAR_PRODUCTO = Target.the("boton eliminar {0}")
            .locatedBy("[aria-label='Eliminar {0}']");

    // Prioridad 3: CSS simple (clase unica del boton)
    public static final Target BOTON_IR_A_PAGAR = Target.the("boton ir a pagar")
            .locatedBy(".btn-pagar");
}
