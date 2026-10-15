package botica.tasks;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Enter;

import static botica.userinterfaces.PaginaCatalogo.CAMPO_BUSCAR;
import static net.serenitybdd.screenplay.Tasks.instrumented;

/**
 * Busca productos del catalogo por su nombre (coincidencia parcial).
 */
public class BuscarProducto implements Task {

    private final String texto;

    public BuscarProducto(String texto) {
        this.texto = texto;
    }

    @Override
    @Step("{0} busca productos con el texto #texto")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Enter.theValue(texto).into(CAMPO_BUSCAR)
        );
    }

    public static BuscarProducto porNombre(String texto) {
        return instrumented(BuscarProducto.class, texto);
    }
}
