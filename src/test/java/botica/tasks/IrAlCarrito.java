package botica.tasks;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static botica.userinterfaces.BarraNavegacion.ENLACE_CARRITO;
import static botica.userinterfaces.PaginaCarrito.TITULO;
import static botica.utils.Constantes.TIEMPO_MAXIMO_ESPERA;
import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

/**
 * Abre el carrito desde el menu superior.
 */
public class IrAlCarrito implements Task {

    @Override
    @Step("{0} abre su carrito")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Click.on(ENLACE_CARRITO),
                WaitUntil.the(TITULO, isVisible()).forNoMoreThan(TIEMPO_MAXIMO_ESPERA).seconds()
        );
    }

    public static IrAlCarrito desdeElMenu() {
        return instrumented(IrAlCarrito.class);
    }
}
