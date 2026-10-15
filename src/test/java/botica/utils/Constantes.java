package botica.utils;

/**
 * Datos fijos de la aplicacion bajo prueba (URL, actor y credenciales de practica).
 */
public final class Constantes {

    private Constantes() {
    }

    public static final String URL_BOTICA = "https://boticaalquimista.onrender.com/";

    public static final String ACTOR = "Alquimista";

    public static final String USUARIO_VALIDO = "alquimista";
    public static final String CLAVE_VALIDA = "pocion123";

    /** Segundos maximos de espera explicita (render.com puede tardar en responder la primera vez). */
    public static final int TIEMPO_MAXIMO_ESPERA = 60;
}
