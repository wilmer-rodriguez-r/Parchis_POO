package domain.exceptions;

import java.io.Serializable;

/**
 * Clase que se encarga de las excepciones que dan bonus en el juego
 * 
 * @author Wilmer Rodríguez, Miguel Monroy
 * @version 1.0.0
 */
public class BonusException extends PoobchisException implements Serializable {
	private static final long serialVersionUID = 1L;
	public static String BONUS_WINNER = "Puede mover 10 con la ficha que desee";
	public static String BONUS_KILL = "Puede mover 20 con la ficha que desee";
	public static String COMODIN_AVANZAR = "Avanza 5 casillas";
	public static String COMODIN_RETROCEDER = "Retrocede 5 casillas";

	/**
	 * Constructor de la clase BonusException
	 * 
	 * @param message de tipo String que es el mensaje que tendra la excepcion
	 */
	public BonusException(String message) {
		super(message);
	}
}
