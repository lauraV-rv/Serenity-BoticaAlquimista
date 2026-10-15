package botica.userinterfaces;

import net.serenitybdd.screenplay.targets.Target;

/**
 * Localizadores de la pagina de pedido confirmado (confirmacion.html).
 */
public class PaginaConfirmacion {

    private PaginaConfirmacion() {
    }

    // Prioridad 1: data-testid
    public static final Target NUMERO_PEDIDO = Target.the("numero de pedido")
            .locatedBy("[data-testid='numero-pedido']");

    // Prioridad 2: id
    public static final Target ENVIO_COBRADO = Target.the("valor del envio")
            .locatedBy("#conf-envio");

    // Prioridad 2: id
    public static final Target TOTAL_PAGADO = Target.the("total pagado")
            .locatedBy("#conf-total");
}
