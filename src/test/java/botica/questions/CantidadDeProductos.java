package botica.questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

import static botica.userinterfaces.PaginaCatalogo.TARJETAS_DE_PRODUCTOS;

/**
 * Cuenta cuantas tarjetas de producto muestra el catalogo.
 */
public class CantidadDeProductos implements Question<Integer> {

    @Override
    public Integer answeredBy(Actor actor) {
        return TARJETAS_DE_PRODUCTOS.resolveAllFor(actor).size();
    }

    public static Question<Integer> enElCatalogo() {
        return new CantidadDeProductos();
    }
}
