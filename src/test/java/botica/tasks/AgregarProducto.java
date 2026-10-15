package botica.tasks;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static botica.userinterfaces.PaginaCatalogo.BOTON_AGREGAR_DEL_PRODUCTO;
import static botica.userinterfaces.PaginaCatalogo.CANTIDAD_DEL_PRODUCTO;
import static botica.userinterfaces.PaginaCatalogo.MENSAJE_CATALOGO;
import static botica.utils.Constantes.TIEMPO_MAXIMO_ESPERA;
import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

/**
 * Agrega al carrito una cantidad de un producto desde su tarjeta en el catalogo.
 * Termina cuando el catalogo muestra su mensaje (exito o error).
 */
public class AgregarProducto implements Task {

    private final String producto;
    private final Integer cantidad;

    public AgregarProducto(String producto, Integer cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }

    @Override
    @Step("{0} agrega #cantidad unidad(es) de #producto al carrito")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Enter.theValue(String.valueOf(cantidad)).into(CANTIDAD_DEL_PRODUCTO.of(producto)),
                Click.on(BOTON_AGREGAR_DEL_PRODUCTO.of(producto)),
                WaitUntil.the(MENSAJE_CATALOGO, isVisible()).forNoMoreThan(TIEMPO_MAXIMO_ESPERA).seconds()
        );
    }

    public static AgregarProducto alCarrito(String producto, Integer cantidad) {
        return instrumented(AgregarProducto.class, producto, cantidad);
    }
}
