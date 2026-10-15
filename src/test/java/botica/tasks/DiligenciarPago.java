package botica.tasks;

import botica.models.DatosEnvio;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static botica.userinterfaces.PaginaCarrito.BOTON_IR_A_PAGAR;
import static botica.userinterfaces.PaginaPago.BOTON_CONFIRMAR;
import static botica.userinterfaces.PaginaPago.CAMPO_CIUDAD;
import static botica.userinterfaces.PaginaPago.CAMPO_CORREO;
import static botica.userinterfaces.PaginaPago.CAMPO_DIRECCION;
import static botica.userinterfaces.PaginaPago.CAMPO_NOMBRE;
import static botica.userinterfaces.PaginaPago.CAMPO_TELEFONO;
import static botica.userinterfaces.PaginaPago.OPCION_ENVIO;
import static botica.utils.Constantes.TIEMPO_MAXIMO_ESPERA;
import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

/**
 * Va del carrito al pago, diligencia los datos de envio y presiona "Confirmar pedido".
 * No espera la confirmacion: la usan tanto el pedido exitoso (ConfirmarPedido)
 * como los escenarios de validacion del formulario.
 */
public class DiligenciarPago implements Task {

    private final DatosEnvio datos;

    public DiligenciarPago(DatosEnvio datos) {
        this.datos = datos;
    }

    @Override
    @Step("{0} diligencia el pago a nombre de #datos")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                IrAlCarrito.desdeElMenu(),
                Click.on(BOTON_IR_A_PAGAR),
                WaitUntil.the(CAMPO_NOMBRE, isVisible()).forNoMoreThan(TIEMPO_MAXIMO_ESPERA).seconds(),
                Enter.theValue(datos.getNombre()).into(CAMPO_NOMBRE),
                Enter.theValue(datos.getCorreo()).into(CAMPO_CORREO),
                Enter.theValue(datos.getDireccion()).into(CAMPO_DIRECCION),
                Enter.theValue(datos.getCiudad()).into(CAMPO_CIUDAD),
                Enter.theValue(datos.getTelefono()).into(CAMPO_TELEFONO),
                Click.on(OPCION_ENVIO.of(datos.getEnvio())),
                Click.on(BOTON_CONFIRMAR)
        );
    }

    public static DiligenciarPago conLosDatos(DatosEnvio datos) {
        return instrumented(DiligenciarPago.class, datos);
    }
}
