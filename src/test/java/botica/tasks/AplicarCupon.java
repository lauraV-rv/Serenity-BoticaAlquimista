package botica.tasks;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static botica.userinterfaces.PaginaCarrito.BOTON_APLICAR_CUPON;
import static botica.userinterfaces.PaginaCarrito.CAMPO_CUPON;
import static botica.userinterfaces.PaginaCarrito.MENSAJE_CUPON;
import static botica.utils.Constantes.TIEMPO_MAXIMO_ESPERA;
import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

/**
 * Aplica un cupon de descuento en el carrito.
 * La aplicacion "consulta el grimorio de cupones" con una espera aleatoria,
 * por eso la tarea termina cuando aparece el mensaje de respuesta del cupon.
 */
public class AplicarCupon implements Task {

    private final String codigo;

    public AplicarCupon(String codigo) {
        this.codigo = codigo;
    }

    @Override
    @Step("{0} aplica el cupon #codigo")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                IrAlCarrito.desdeElMenu(),
                Enter.theValue(codigo).into(CAMPO_CUPON),
                Click.on(BOTON_APLICAR_CUPON),
                WaitUntil.the(MENSAJE_CUPON, isVisible()).forNoMoreThan(TIEMPO_MAXIMO_ESPERA).seconds()
        );
    }

    public static AplicarCupon conCodigo(String codigo) {
        return instrumented(AplicarCupon.class, codigo);
    }
}
