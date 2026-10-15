package botica.questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.targets.Target;

import static botica.userinterfaces.PaginaCatalogo.CATEGORIA_DEL_PRODUCTO;
import static botica.userinterfaces.PaginaCatalogo.PRECIO_DEL_PRODUCTO;
import static botica.userinterfaces.PaginaCatalogo.STOCK_DEL_PRODUCTO;

/**
 * Lee un dato (categoria, precio o stock) de la tarjeta de un producto del catalogo.
 */
public class LaTarjetaDelProducto implements Question<String> {

    private final Target dato;

    public LaTarjetaDelProducto(Target dato) {
        this.dato = dato;
    }

    @Override
    public String answeredBy(Actor actor) {
        return dato.resolveFor(actor).waitUntilVisible().getText().trim();
    }

    public static Question<String> categoria(String producto) {
        return new LaTarjetaDelProducto(CATEGORIA_DEL_PRODUCTO.of(producto));
    }

    public static Question<String> precio(String producto) {
        return new LaTarjetaDelProducto(PRECIO_DEL_PRODUCTO.of(producto));
    }

    public static Question<String> stock(String producto) {
        return new LaTarjetaDelProducto(STOCK_DEL_PRODUCTO.of(producto));
    }
}
