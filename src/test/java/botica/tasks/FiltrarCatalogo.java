package botica.tasks;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.SelectFromOptions;

import static botica.userinterfaces.PaginaCatalogo.LISTA_CATEGORIAS;
import static net.serenitybdd.screenplay.Tasks.instrumented;

/**
 * Filtra el catalogo por una categoria (Pociones, Ingredientes, Amuletos o Todas).
 */
public class FiltrarCatalogo implements Task {

    private final String categoria;

    public FiltrarCatalogo(String categoria) {
        this.categoria = categoria;
    }

    @Override
    @Step("{0} filtra el catalogo por la categoria #categoria")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                SelectFromOptions.byVisibleText(categoria).from(LISTA_CATEGORIAS)
        );
    }

    public static FiltrarCatalogo porCategoria(String categoria) {
        return instrumented(FiltrarCatalogo.class, categoria);
    }
}
