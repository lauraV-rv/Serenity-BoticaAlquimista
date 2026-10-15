package botica.userinterfaces;

import net.serenitybdd.screenplay.targets.Target;

/**
 * Localizadores EXCLUSIVOS de la pantalla de inicio de sesion (index.html).
 * Los elementos de las paginas posteriores al login viven en sus propias clases.
 */
public class PaginaLogin {

    private PaginaLogin() {
    }

    // Prioridad 2: id estatico y unico
    public static final Target CAMPO_USUARIO = Target.the("campo usuario")
            .locatedBy("#usuario");

    // Prioridad 2: atributo name (el campo no tiene id)
    public static final Target CAMPO_CONTRASENA = Target.the("campo contrasena")
            .locatedBy("[name='clave']");

    // Prioridad 3: CSS simple (unico boton submit del formulario de login)
    public static final Target BOTON_INGRESAR = Target.the("boton Ingresar")
            .locatedBy("#form-login button[type='submit']");

    // Prioridad 1: atributo dedicado de automatizacion
    public static final Target MENSAJE_ERROR = Target.the("mensaje de error de acceso")
            .locatedBy("[data-testid='error-login']");
}
