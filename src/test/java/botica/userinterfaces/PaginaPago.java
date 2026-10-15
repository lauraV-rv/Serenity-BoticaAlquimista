package botica.userinterfaces;

import net.serenitybdd.screenplay.targets.Target;

/**
 * Localizadores del formulario de envio y pago (checkout.html).
 * Varios ids de esta pagina parecen autogenerados (inp-x92, f-dir-01, t-771, c1),
 * por eso se prefieren los atributos estables name y data-testid.
 *
 * Nota: los selectores CSS por atributo se escriben sin etiqueta ([name='x'] y no input[name='x'])
 * porque Serenity trata como XPath todo texto que compile como XPath, e input[name='x'] lo es.
 */
public class PaginaPago {

    private PaginaPago() {
    }

    // Prioridad 2: id estable
    public static final Target CAMPO_NOMBRE = Target.the("campo nombre completo")
            .locatedBy("#nombreCompleto");

    // Prioridad 2: name (el id "inp-x92" parece autogenerado)
    public static final Target CAMPO_CORREO = Target.the("campo correo electronico")
            .locatedBy("[name='correo']");

    // Prioridad 1: data-testid
    public static final Target CAMPO_DIRECCION = Target.the("campo direccion")
            .locatedBy("[data-testid='direccion']");

    // Prioridad 2: name
    public static final Target CAMPO_CIUDAD = Target.the("campo ciudad")
            .locatedBy("[name='ciudad']");

    // Prioridad 2: name
    public static final Target CAMPO_TELEFONO = Target.the("campo telefono")
            .locatedBy("[name='telefono']");

    // Prioridad 2: name + value. {0} = normal | express
    public static final Target OPCION_ENVIO = Target.the("opcion de envio {0}")
            .locatedBy("[name='envio'][value='{0}']");

    // Prioridad 1: data-testid
    public static final Target MENSAJE_PAGO = Target.the("mensaje del formulario de pago")
            .locatedBy("[data-testid='mensaje-checkout']");

    // Prioridad 3: CSS por clase + atributo data-campo. {0} = nombre, correo, direccion, ciudad o telefono
    public static final Target ERROR_DEL_CAMPO = Target.the("error del campo {0}")
            .locatedBy(".error-campo[data-campo='{0}']");

    // Prioridad 2: id
    public static final Target BOTON_CONFIRMAR = Target.the("boton confirmar pedido")
            .locatedBy("#btn-confirmar");
}
