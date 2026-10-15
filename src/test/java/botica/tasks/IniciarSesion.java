package botica.tasks;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;

import static botica.userinterfaces.PaginaLogin.BOTON_INGRESAR;
import static botica.userinterfaces.PaginaLogin.CAMPO_CONTRASENA;
import static botica.userinterfaces.PaginaLogin.CAMPO_USUARIO;
import static net.serenitybdd.screenplay.Tasks.instrumented;

/**
 * Inicia sesion con un usuario y una contrasena.
 * El resultado (catalogo o mensaje de error) lo verifican las Questions.
 */
public class IniciarSesion implements Task {

    private final String usuario;
    private final String clave;

    public IniciarSesion(String usuario, String clave) {
        this.usuario = usuario;
        this.clave = clave;
    }

    @Override
    @Step("{0} inicia sesion con el usuario #usuario")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Enter.theValue(usuario).into(CAMPO_USUARIO),
                Enter.theValue(clave).into(CAMPO_CONTRASENA),
                Click.on(BOTON_INGRESAR)
        );
    }

    public static IniciarSesion conCredenciales(String usuario, String clave) {
        return instrumented(IniciarSesion.class, usuario, clave);
    }
}
