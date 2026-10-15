package botica.questions;

import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

import static botica.userinterfaces.PaginaCarrito.BOTON_IR_A_PAGAR;

/**
 * Responde true solo si el boton "Ir a pagar" se ve y esta habilitado.
 * Se usan los metodos "Currently" para no esperar a que aparezca.
 */
public class SePuedeIrAPagar implements Question<Boolean> {

    @Override
    public Boolean answeredBy(Actor actor) {
        WebElementFacade boton = BOTON_IR_A_PAGAR.resolveFor(actor);
        return boton.isCurrentlyVisible() && boton.isCurrentlyEnabled();
    }

    public static Question<Boolean> desdeElCarrito() {
        return new SePuedeIrAPagar();
    }
}
