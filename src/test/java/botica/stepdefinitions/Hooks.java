package botica.stepdefinitions;

import io.cucumber.java.Before;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;

/**
 * Prepara el escenario antes de cada prueba.
 * OnlineCast es el "elenco": cada actor recibe su propio navegador,
 * que Serenity abre y cierra automaticamente.
 */
public class Hooks {

    @Before
    public void setTheStage() {
        OnStage.setTheStage(new OnlineCast());
    }
}
