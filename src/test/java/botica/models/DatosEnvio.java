package botica.models;

import java.util.Map;

/**
 * Datos de envio que el cliente diligencia en la pagina de pago.
 * Se construye a partir de una fila de la tabla de datos del escenario Gherkin.
 */
public class DatosEnvio {

    private final String nombre;
    private final String correo;
    private final String direccion;
    private final String ciudad;
    private final String telefono;
    private final String envio;

    public DatosEnvio(String nombre, String correo, String direccion,
                      String ciudad, String telefono, String envio) {
        this.nombre = nombre;
        this.correo = correo;
        this.direccion = direccion;
        this.ciudad = ciudad;
        this.telefono = telefono;
        this.envio = envio;
    }

    public static DatosEnvio desde(Map<String, String> fila) {
        return new DatosEnvio(
                fila.get("nombre"),
                fila.get("correo"),
                fila.get("direccion"),
                fila.get("ciudad"),
                fila.get("telefono"),
                fila.get("envio"));
    }

    public String getNombre() {
        return nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getEnvio() {
        return envio;
    }

    @Override
    public String toString() {
        return nombre + " (" + ciudad + ", envio " + envio + ")";
    }
}
