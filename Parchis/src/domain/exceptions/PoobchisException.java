package domain.exceptions;

import java.io.Serializable;

/**
 * Clase que se encarga de las excepciones
 * 
 * @author Wilmer Rodríguez, Miguel Monroy
 * @version 1.0.0
 */
public class PoobchisException extends Exception implements Serializable {
	private static final long serialVersionUID = 1L;
	public static String WINNER = "Ha ganado el juego";
	public static String BLOQUEO = "Hay un bloqueo en el camino";
	public static String DADO_JUGADO = "Este dado ya fue jugado";
	public static String NO_SACAR_CASA = "No se puede sacar de la casa";
	public static String NO_GUARDO = "El archivo no se puedo guardar";
	public static String NO_PUDO_ABRIR = "El archivo no se puedo abrir";
	public static String NO_SELECCIONO = "No existe en el destino el archivo ";
	public static String NO_TIPO_DAT = "El archivo no es de tipo .dat";
	public static String YA_EXISTE = "Ya existe en el destino el archivo ";

	/**
	 * Constructor de la clase PoobchisException
	 * @param message de tipo String que es el mensaje de la excepcion
	 */
	public PoobchisException(String message) {
		super(message);
	}
}
