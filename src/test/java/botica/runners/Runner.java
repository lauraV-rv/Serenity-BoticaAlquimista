package botica.runners;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

/**
 * Ejecuta los escenarios de compra_botica.feature con Serenity BDD.
 *
 * Por defecto corre toda la HU-01. Para ejecutar un solo issue o requisito:
 *   ./gradlew test -Dcucumber.filter.tags="@Issue-1"
 *   ./gradlew test -Dcucumber.filter.tags="@req_cupones"
 * (la propiedad del sistema tiene prioridad sobre el valor de "tags" de esta clase).
 */
@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        // cucumber.json lo usa el pipeline para publicar el resultado de cada escenario en su issue
        plugin = {"pretty", "json:target/cucumber-reports/cucumber.json"},
        features = "src/test/resources/features",
        glue = "botica.stepdefinitions",
        tags = "@HU-01",
        snippets = CucumberOptions.SnippetType.CAMELCASE
)
public class Runner {
}
