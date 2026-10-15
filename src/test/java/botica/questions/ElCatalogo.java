package botica.questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

import static botica.userinterfaces.PaginaCatalogo.GRILLA_DE_PRODUCTOS;
import static net.serenitybdd.screenplay.questions.WebElementQuestion.the;

/**
 * Verifica el exito del login: un elemento de la pagina SIGUIENTE (la grilla del catalogo) es visible.
 * isVisible() espera un poco a que el elemento aparezca, lo que cubre la redireccion.
 */
public class ElCatalogo implements Question<Boolean> {

    @Override
    public Boolean answeredBy(Actor actor) {
        return the(GRILLA_DE_PRODUCTOS).answeredBy(actor).isVisible();
    }

    public static Question<Boolean> estaVisible() {
        return new ElCatalogo();
    }
}
