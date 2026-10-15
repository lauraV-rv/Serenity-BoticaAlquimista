package botica.questions;

import botica.userinterfaces.BarraNavegacion;
import botica.userinterfaces.PaginaCarrito;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.targets.Target;

/**
 * Lee la informacion del carrito: cupon aplicado, descuento y unidades del contador.
 */
public class ElCarrito implements Question<String> {

    private final Target dato;

    public ElCarrito(Target dato) {
        this.dato = dato;
    }

    @Override
    public String answeredBy(Actor actor) {
        return dato.resolveFor(actor).waitUntilVisible().getText().trim();
    }

    public static Question<String> cuponAplicado() {
        return new ElCarrito(PaginaCarrito.CUPON_APLICADO);
    }

    public static Question<String> descuento() {
        return new ElCarrito(PaginaCarrito.DESCUENTO);
    }

    public static Question<String> subtotal() {
        return new ElCarrito(PaginaCarrito.SUBTOTAL);
    }

    /** Contador del menu superior; esta en todas las paginas despues del login. */
    public static Question<String> cantidadDeUnidades() {
        return new ElCarrito(BarraNavegacion.CONTADOR_CARRITO);
    }
}
