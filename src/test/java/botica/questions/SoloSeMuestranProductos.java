package botica.questions;

import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.targets.Target;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static botica.userinterfaces.PaginaCatalogo.CATEGORIAS_DE_PRODUCTOS;
import static botica.userinterfaces.PaginaCatalogo.NOMBRES_DE_PRODUCTOS;

/**
 * Responde true si TODOS los productos visibles en el catalogo cumplen una condicion
 * (pertenecer a una categoria o contener un texto en su nombre) y hay al menos uno.
 */
public class SoloSeMuestranProductos implements Question<Boolean> {

    private final Target textosDeLasTarjetas;
    private final Predicate<String> condicion;

    public SoloSeMuestranProductos(Target textosDeLasTarjetas, Predicate<String> condicion) {
        this.textosDeLasTarjetas = textosDeLasTarjetas;
        this.condicion = condicion;
    }

    @Override
    public Boolean answeredBy(Actor actor) {
        List<String> textos = textosDeLasTarjetas.resolveAllFor(actor).stream()
                .map(WebElementFacade::getText)
                .map(String::trim)
                .collect(Collectors.toList());
        return !textos.isEmpty() && textos.stream().allMatch(condicion);
    }

    public static Question<Boolean> deLaCategoria(String categoria) {
        return new SoloSeMuestranProductos(CATEGORIAS_DE_PRODUCTOS,
                categoriaMostrada -> categoriaMostrada.equals(categoria));
    }

    /** La busqueda no distingue mayusculas de minusculas, por eso se compara en minusculas. */
    public static Question<Boolean> cuyoNombreContiene(String texto) {
        return new SoloSeMuestranProductos(NOMBRES_DE_PRODUCTOS,
                nombre -> nombre.toLowerCase().contains(texto.toLowerCase()));
    }
}
