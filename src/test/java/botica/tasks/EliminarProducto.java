package botica.tasks;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static botica.userinterfaces.PaginaCarrito.BOTON_ELIMINAR_PRODUCTO;
import static botica.userinterfaces.PaginaCarrito.MENSAJE_CARRITO;
import static botica.utils.Constantes.TIEMPO_MAXIMO_ESPERA;
import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

/**
 * Elimina un producto del carrito. Termina cuando el carrito muestra su mensaje.
 */
public class EliminarProducto implements Task {

    private final String producto;

    public EliminarProducto(String producto) {
        this.producto = producto;
    }

    @Override
    @Step("{0} elimina #producto de su carrito")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                IrAlCarrito.desdeElMenu(),
                Click.on(BOTON_ELIMINAR_PRODUCTO.of(producto)),
                WaitUntil.the(MENSAJE_CARRITO, isVisible()).forNoMoreThan(TIEMPO_MAXIMO_ESPERA).seconds()
        );
    }

    public static EliminarProducto delCarrito(String producto) {
        return instrumented(EliminarProducto.class, producto);
    }
}
