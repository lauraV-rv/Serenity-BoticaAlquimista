package botica.tasks;

import botica.models.DatosEnvio;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static botica.userinterfaces.PaginaConfirmacion.NUMERO_PEDIDO;
import static botica.utils.Constantes.TIEMPO_MAXIMO_ESPERA;
import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

/**
 * Diligencia el pago y confirma el pedido.
 * Termina cuando la pagina de confirmacion muestra el numero de pedido.
 */
public class ConfirmarPedido implements Task {

    private final DatosEnvio datos;

    public ConfirmarPedido(DatosEnvio datos) {
        this.datos = datos;
    }

    @Override
    @Step("{0} confirma el pedido a nombre de #datos")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                DiligenciarPago.conLosDatos(datos),
                WaitUntil.the(NUMERO_PEDIDO, isVisible()).forNoMoreThan(TIEMPO_MAXIMO_ESPERA).seconds()
        );
    }

    public static ConfirmarPedido conLosDatos(DatosEnvio datos) {
        return instrumented(ConfirmarPedido.class, datos);
    }
}
