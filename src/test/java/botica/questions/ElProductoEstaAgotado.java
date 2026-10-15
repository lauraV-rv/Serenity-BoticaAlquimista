package botica.questions;

import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

import static botica.userinterfaces.PaginaCatalogo.BOTON_AGREGAR_DEL_PRODUCTO;

/**
 * Responde true si la tarjeta del producto muestra "Agotado" y su boton esta deshabilitado,
 * es decir, el producto no se puede agregar al carrito.
 */
public class ElProductoEstaAgotado implements Question<Boolean> {

    private final String producto;

    public ElProductoEstaAgotado(String producto) {
        this.producto = producto;
    }

    @Override
    public Boolean answeredBy(Actor actor) {
        WebElementFacade boton = BOTON_AGREGAR_DEL_PRODUCTO.of(producto).resolveFor(actor);
        return boton.getText().trim().equals("Agotado") && !boton.isEnabled();
    }

    public static Question<Boolean> llamado(String producto) {
        return new ElProductoEstaAgotado(producto);
    }
}
