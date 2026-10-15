package botica.userinterfaces;

import net.serenitybdd.screenplay.targets.Target;

/**
 * Encabezado que comparten todas las paginas posteriores al login
 * (catalogo, carrito, pago y confirmacion).
 */
public class BarraNavegacion {

    private BarraNavegacion() {
    }

    // Prioridad 2: id
    public static final Target ENLACE_CARRITO = Target.the("enlace al carrito")
            .locatedBy("#enlace-carrito");

    // Prioridad 1: data-testid
    public static final Target CONTADOR_CARRITO = Target.the("contador de unidades del carrito")
            .locatedBy("[data-testid='contador-carrito']");
}
