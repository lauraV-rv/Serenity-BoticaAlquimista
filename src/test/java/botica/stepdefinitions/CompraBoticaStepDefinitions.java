package botica.stepdefinitions;

import botica.models.DatosEnvio;
import botica.questions.CantidadDeProductos;
import botica.questions.ElCarrito;
import botica.questions.ElCatalogo;
import botica.questions.ElMensaje;
import botica.questions.ElPedido;
import botica.questions.ElProductoEstaAgotado;
import botica.questions.LaTarjetaDelProducto;
import botica.questions.SePuedeIrAPagar;
import botica.questions.SoloSeMuestranProductos;
import botica.tasks.AbrirLaBotica;
import botica.tasks.AgregarProducto;
import botica.tasks.AplicarCupon;
import botica.tasks.BuscarProducto;
import botica.tasks.ConfirmarPedido;
import botica.tasks.DiligenciarPago;
import botica.tasks.EliminarProducto;
import botica.tasks.IrAlCarrito;
import botica.tasks.FiltrarCatalogo;
import botica.tasks.IniciarSesion;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;

import java.util.List;
import java.util.Map;

import static botica.utils.Constantes.ACTOR;
import static botica.utils.Constantes.CLAVE_VALIDA;
import static botica.utils.Constantes.USUARIO_VALIDO;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.actors.OnStage.theActorCalled;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.matchesPattern;

/**
 * Traduce los pasos Gherkin de compra_botica.feature a Tareas y Preguntas del actor.
 * Regla: aqui NO hay localizadores ni llamadas a Selenium; solo se invocan
 * Tasks (attemptsTo) y Questions (should + seeThat).
 */
public class CompraBoticaStepDefinitions {

    // ------------------------------------------------------------------ Dado

    @Dado("que el Alquimista esta en la pagina de inicio de sesion")
    public void queElAlquimistaEstaEnLaPaginaDeInicioDeSesion() {
        theActorCalled(ACTOR).attemptsTo(
                AbrirLaBotica.enLaPaginaDeInicio()
        );
    }

    @Dado("que el Alquimista inicio sesion en La Botica")
    public void queElAlquimistaInicioSesionEnLaBotica() {
        theActorCalled(ACTOR).attemptsTo(
                AbrirLaBotica.enLaPaginaDeInicio(),
                IniciarSesion.conCredenciales(USUARIO_VALIDO, CLAVE_VALIDA)
        );
        theActorInTheSpotlight().should(seeThat(ElCatalogo.estaVisible()));
    }

    @Dado("agrego {int} unidad(es) de {string} al carrito")
    public void agregoUnidadesDeAlCarrito(Integer cantidad, String producto) {
        theActorInTheSpotlight().attemptsTo(
                AgregarProducto.alCarrito(producto, cantidad)
        );
    }

    @Dado("aplico el cupon {string}")
    public void aplicoElCupon(String codigo) {
        theActorInTheSpotlight().attemptsTo(
                AplicarCupon.conCodigo(codigo)
        );
    }

    // ---------------------------------------------------------------- Cuando

    @Cuando("inicia sesion con el usuario {string} y la contrasena {string}")
    public void iniciaSesionConElUsuarioYLaContrasena(String usuario, String clave) {
        theActorInTheSpotlight().attemptsTo(
                IniciarSesion.conCredenciales(usuario, clave)
        );
    }

    @Cuando("filtra el catalogo por la categoria {string}")
    public void filtraElCatalogoPorLaCategoria(String categoria) {
        theActorInTheSpotlight().attemptsTo(
                FiltrarCatalogo.porCategoria(categoria)
        );
    }

    @Cuando("busca productos con el texto {string}")
    public void buscaProductosConElTexto(String texto) {
        theActorInTheSpotlight().attemptsTo(
                BuscarProducto.porNombre(texto)
        );
    }

    @Cuando("intenta agregar {int} unidades de {string} al carrito")
    public void intentaAgregarUnidadesDeAlCarrito(Integer cantidad, String producto) {
        theActorInTheSpotlight().attemptsTo(
                AgregarProducto.alCarrito(producto, cantidad)
        );
    }

    @Cuando("consulta el producto {string} en el catalogo")
    public void consultaElProductoEnElCatalogo(String producto) {
        theActorInTheSpotlight().attemptsTo(
                BuscarProducto.porNombre(producto)
        );
    }

    @Cuando("aplica el cupon {string}")
    public void aplicaElCupon(String codigo) {
        theActorInTheSpotlight().attemptsTo(
                AplicarCupon.conCodigo(codigo)
        );
    }

    @Cuando("elimina {string} de su carrito")
    public void eliminaDeSuCarrito(String producto) {
        theActorInTheSpotlight().attemptsTo(
                EliminarProducto.delCarrito(producto)
        );
    }

    @Cuando("abre su carrito sin haber agregado productos")
    public void abreSuCarritoSinHaberAgregadoProductos() {
        theActorInTheSpotlight().attemptsTo(
                IrAlCarrito.desdeElMenu()
        );
    }

    @Cuando("intenta confirmar el pedido con los siguientes datos de envio:")
    public void intentaConfirmarElPedidoConLosSiguientesDatosDeEnvio(DataTable tabla) {
        List<Map<String, String>> filas = tabla.asMaps(String.class, String.class);
        theActorInTheSpotlight().attemptsTo(
                DiligenciarPago.conLosDatos(DatosEnvio.desde(filas.get(0)))
        );
    }

    @Cuando("confirma el pedido con los siguientes datos de envio:")
    public void confirmaElPedidoConLosSiguientesDatosDeEnvio(DataTable tabla) {
        List<Map<String, String>> filas = tabla.asMaps(String.class, String.class);
        DatosEnvio datos = DatosEnvio.desde(filas.get(0));
        theActorInTheSpotlight().attemptsTo(
                ConfirmarPedido.conLosDatos(datos)
        );
    }

    // -------------------------------------------------------------- Entonces

    @Entonces("deberia ver el catalogo de productos")
    public void deberiaVerElCatalogoDeProductos() {
        theActorInTheSpotlight().should(seeThat(ElCatalogo.estaVisible()));
    }

    @Entonces("deberia ver el mensaje de error {string}")
    public void deberiaVerElMensajeDeError(String mensaje) {
        theActorInTheSpotlight().should(seeThat(ElMensaje.deErrorDeAcceso(), equalTo(mensaje)));
    }

    @Entonces("deberia ver unicamente productos de la categoria {string}")
    public void deberiaVerUnicamenteProductosDeLaCategoria(String categoria) {
        theActorInTheSpotlight().should(seeThat(SoloSeMuestranProductos.deLaCategoria(categoria)));
    }

    @Entonces("deberia ver unicamente productos cuyo nombre contiene {string}")
    public void deberiaVerUnicamenteProductosCuyoNombreContiene(String texto) {
        theActorInTheSpotlight().should(seeThat(SoloSeMuestranProductos.cuyoNombreContiene(texto)));
    }

    @Entonces("deberia ver {int} productos en el catalogo")
    public void deberiaVerProductosEnElCatalogo(Integer cantidad) {
        theActorInTheSpotlight().should(seeThat(CantidadDeProductos.enElCatalogo(), equalTo(cantidad)));
    }

    @Entonces("deberia ver el aviso {string}")
    public void deberiaVerElAviso(String aviso) {
        theActorInTheSpotlight().should(seeThat(ElMensaje.delCatalogo(), equalTo(aviso)));
    }

    @Entonces("el carrito deberia tener {int} unidades")
    public void elCarritoDeberiaTenerUnidades(Integer unidades) {
        theActorInTheSpotlight().should(seeThat(ElCarrito.cantidadDeUnidades(), equalTo(String.valueOf(unidades))));
    }

    @Entonces("el producto {string} deberia aparecer como agotado y sin opcion de agregarlo")
    public void elProductoDeberiaAparecerComoAgotado(String producto) {
        theActorInTheSpotlight().should(seeThat(ElProductoEstaAgotado.llamado(producto)));
    }

    @Entonces("deberia ver el cupon {string} aplicado")
    public void deberiaVerElCuponAplicado(String cupon) {
        theActorInTheSpotlight().should(seeThat(ElCarrito.cuponAplicado(), equalTo(cupon)));
    }

    @Entonces("el descuento deberia ser de {string}")
    public void elDescuentoDeberiaSerDe(String descuento) {
        theActorInTheSpotlight().should(seeThat(ElCarrito.descuento(), equalTo(descuento)));
    }

    @Entonces("deberia ver el mensaje del cupon {string}")
    public void deberiaVerElMensajeDelCupon(String mensaje) {
        theActorInTheSpotlight().should(seeThat(ElMensaje.delCupon(), equalTo(mensaje)));
    }

    @Entonces("deberia ver un numero de pedido con formato BOT-XXXX")
    public void deberiaVerUnNumeroDePedidoConFormatoBot() {
        theActorInTheSpotlight().should(seeThat(ElPedido.numero(), matchesPattern("BOT-\\d{4}")));
    }

    @Entonces("el producto {string} deberia mostrar la categoria {string}, el precio {string} y un stock de {int} unidad(es)")
    public void elProductoDeberiaMostrarSusDatos(String producto, String categoria, String precio, Integer stock) {
        theActorInTheSpotlight().should(
                seeThat(LaTarjetaDelProducto.categoria(producto), equalTo(categoria)),
                seeThat(LaTarjetaDelProducto.precio(producto), equalTo(precio)),
                seeThat(LaTarjetaDelProducto.stock(producto), equalTo(String.valueOf(stock)))
        );
    }

    @Entonces("deberia ver el resultado de busqueda {string}")
    public void deberiaVerElResultadoDeBusqueda(String mensaje) {
        theActorInTheSpotlight().should(seeThat(ElMensaje.deBusquedaSinResultados(), equalTo(mensaje)));
    }

    @Entonces("deberia ver el mensaje del carrito {string}")
    public void deberiaVerElMensajeDelCarrito(String mensaje) {
        theActorInTheSpotlight().should(seeThat(ElMensaje.delCarrito(), equalTo(mensaje)));
    }

    @Entonces("el subtotal del carrito deberia ser {string}")
    public void elSubtotalDelCarritoDeberiaSer(String subtotal) {
        theActorInTheSpotlight().should(seeThat(ElCarrito.subtotal(), equalTo(subtotal)));
    }

    @Entonces("deberia ver la indicacion {string}")
    public void deberiaVerLaIndicacion(String indicacion) {
        theActorInTheSpotlight().should(seeThat(ElMensaje.deCarritoVacio(), containsString(indicacion)));
    }

    @Entonces("no deberia poder continuar al pago")
    public void noDeberiaPoderContinuarAlPago() {
        theActorInTheSpotlight().should(seeThat(SePuedeIrAPagar.desdeElCarrito(), equalTo(false)));
    }

    @Entonces("deberia ver el mensaje del pago {string}")
    public void deberiaVerElMensajeDelPago(String mensaje) {
        theActorInTheSpotlight().should(seeThat(ElMensaje.delPago(), equalTo(mensaje)));
    }

    @Entonces("deberia ver el error {string} en el campo {string}")
    public void deberiaVerElErrorEnElCampo(String error, String campo) {
        theActorInTheSpotlight().should(seeThat(ElMensaje.deErrorEnElCampo(campo), equalTo(error)));
    }

    @Entonces("el envio cobrado deberia ser {string}")
    public void elEnvioCobradoDeberiaSer(String envio) {
        theActorInTheSpotlight().should(seeThat(ElPedido.envioCobrado(), equalTo(envio)));
    }

    @Entonces("el total pagado deberia ser {string}")
    public void elTotalPagadoDeberiaSer(String total) {
        theActorInTheSpotlight().should(seeThat(ElPedido.totalPagado(), equalTo(total)));
    }
}
