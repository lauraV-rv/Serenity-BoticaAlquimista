package botica.questions;

import botica.userinterfaces.PaginaCarrito;
import botica.userinterfaces.PaginaCatalogo;
import botica.userinterfaces.PaginaLogin;
import botica.userinterfaces.PaginaPago;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.targets.Target;

/**
 * Lee el texto de los mensajes de error o alerta que muestra la aplicacion.
 */
public class ElMensaje implements Question<String> {

    private final Target mensaje;

    public ElMensaje(Target mensaje) {
        this.mensaje = mensaje;
    }

    @Override
    public String answeredBy(Actor actor) {
        return mensaje.resolveFor(actor).waitUntilVisible().getText().trim();
    }

    /** Mensaje de la pantalla de login (credenciales incorrectas). */
    public static Question<String> deErrorDeAcceso() {
        return new ElMensaje(PaginaLogin.MENSAJE_ERROR);
    }

    /** Mensaje del catalogo al intentar agregar un producto. */
    public static Question<String> delCatalogo() {
        return new ElMensaje(PaginaCatalogo.MENSAJE_CATALOGO);
    }

    /** Respuesta del bloque de cupones del carrito. */
    public static Question<String> delCupon() {
        return new ElMensaje(PaginaCarrito.MENSAJE_CUPON);
    }

    /** Mensaje del catalogo cuando la busqueda no encuentra productos. */
    public static Question<String> deBusquedaSinResultados() {
        return new ElMensaje(PaginaCatalogo.SIN_RESULTADOS);
    }

    /** Mensaje del carrito (producto eliminado, cantidad actualizada, avisos). */
    public static Question<String> delCarrito() {
        return new ElMensaje(PaginaCarrito.MENSAJE_CARRITO);
    }

    /** Indicacion que aparece cuando el carrito no tiene productos. */
    public static Question<String> deCarritoVacio() {
        return new ElMensaje(PaginaCarrito.CARRITO_VACIO);
    }

    /** Mensaje general del formulario de pago. */
    public static Question<String> delPago() {
        return new ElMensaje(PaginaPago.MENSAJE_PAGO);
    }

    /** Error de validacion de un campo del formulario de pago (nombre, correo, direccion, ciudad, telefono). */
    public static Question<String> deErrorEnElCampo(String campo) {
        return new ElMensaje(PaginaPago.ERROR_DEL_CAMPO.of(campo));
    }
}
