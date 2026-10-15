package botica.userinterfaces;

import net.serenitybdd.screenplay.targets.Target;

/**
 * Localizadores de la pagina principal despues del login: el catalogo (catalogo.html).
 *
 * Nota: los selectores CSS por atributo se escriben sin etiqueta ([name='x'] y no input[name='x'])
 * porque Serenity trata como XPath todo texto que compile como XPath, e input[name='x'] lo es.
 */
public class PaginaCatalogo {

    private PaginaCatalogo() {
    }

    // Prioridad 3: CSS simple. La grilla de productos solo existe en el catalogo:
    // si es visible, el login fue exitoso (elemento de la pagina siguiente).
    public static final Target GRILLA_DE_PRODUCTOS = Target.the("grilla de productos del catalogo")
            .locatedBy(".grilla-productos");

    // Prioridad 2: id
    public static final Target CAMPO_BUSCAR = Target.the("campo de busqueda por nombre")
            .locatedBy("#buscar");

    // Prioridad 2: name
    public static final Target LISTA_CATEGORIAS = Target.the("lista de categorias")
            .locatedBy("[name='categoria']");

    // Prioridad 1: data-testid
    public static final Target MENSAJE_CATALOGO = Target.the("mensaje del catalogo")
            .locatedBy("[data-testid='mensaje-catalogo']");

    // Prioridad 3: CSS simple (listas de elementos)
    public static final Target TARJETAS_DE_PRODUCTOS = Target.the("tarjetas de productos")
            .locatedBy(".grilla-productos .tarjeta-producto");

    public static final Target NOMBRES_DE_PRODUCTOS = Target.the("nombres de los productos")
            .locatedBy(".grilla-productos .nombre-producto");

    public static final Target CATEGORIAS_DE_PRODUCTOS = Target.the("categorias de los productos")
            .locatedBy(".grilla-productos .categoria-producto");

    // Prioridad 3: CSS simple
    public static final Target SIN_RESULTADOS = Target.the("mensaje sin resultados")
            .locatedBy(".sin-resultados");

    // Prioridad 4: XPath relativo con ancla. Localizadores dinamicos: {0} se reemplaza por el nombre del producto con Target.of(...)
    // Ancla: el <h3> con el nombre; desde ahi se baja al campo o boton de esa misma tarjeta.
    public static final Target CANTIDAD_DEL_PRODUCTO = Target.the("cantidad de {0}")
            .locatedBy("//article[.//h3[normalize-space()='{0}']]//input[contains(@class,'cantidad')]");

    public static final Target BOTON_AGREGAR_DEL_PRODUCTO = Target.the("boton agregar de {0}")
            .locatedBy("//article[.//h3[normalize-space()='{0}']]//button[contains(@class,'btn-agregar')]");

    public static final Target CATEGORIA_DEL_PRODUCTO = Target.the("categoria de {0}")
            .locatedBy("//article[.//h3[normalize-space()='{0}']]//p[contains(@class,'categoria-producto')]");

    public static final Target PRECIO_DEL_PRODUCTO = Target.the("precio de {0}")
            .locatedBy("//article[.//h3[normalize-space()='{0}']]//p[contains(@class,'precio')]");

    public static final Target STOCK_DEL_PRODUCTO = Target.the("stock de {0}")
            .locatedBy("//article[.//h3[normalize-space()='{0}']]//p[contains(@class,'stock')]/span");
}
