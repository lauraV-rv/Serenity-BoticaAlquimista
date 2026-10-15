package botica.questions;

import botica.userinterfaces.PaginaConfirmacion;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.targets.Target;

/**
 * Lee los datos del pedido confirmado: numero (BOT-XXXX) y total pagado.
 */
public class ElPedido implements Question<String> {

    private final Target dato;

    public ElPedido(Target dato) {
        this.dato = dato;
    }

    @Override
    public String answeredBy(Actor actor) {
        return dato.resolveFor(actor).waitUntilVisible().getText().trim();
    }

    public static Question<String> numero() {
        return new ElPedido(PaginaConfirmacion.NUMERO_PEDIDO);
    }

    public static Question<String> envioCobrado() {
        return new ElPedido(PaginaConfirmacion.ENVIO_COBRADO);
    }

    public static Question<String> totalPagado() {
        return new ElPedido(PaginaConfirmacion.TOTAL_PAGADO);
    }
}
