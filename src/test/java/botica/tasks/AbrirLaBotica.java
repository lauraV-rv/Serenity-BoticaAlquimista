package botica.tasks;

import botica.userinterfaces.PaginaLogin;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static botica.utils.Constantes.TIEMPO_MAXIMO_ESPERA;
import static botica.utils.Constantes.URL_BOTICA;
import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

/**
 * Abre La Botica del Alquimista en la pagina de inicio de sesion.
 */
public class AbrirLaBotica implements Task {

    @Override
    @Step("{0} abre La Botica del Alquimista")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Open.url(URL_BOTICA),
                WaitUntil.the(PaginaLogin.CAMPO_USUARIO, isVisible())
                        .forNoMoreThan(TIEMPO_MAXIMO_ESPERA).seconds()
        );
    }

    public static AbrirLaBotica enLaPaginaDeInicio() {
        return instrumented(AbrirLaBotica.class);
    }
}
