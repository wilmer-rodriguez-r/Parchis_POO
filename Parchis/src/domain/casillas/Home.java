package domain.casillas;

import java.awt.*;
import java.io.Serializable;

/**
 * Clase que representa logicamente una casilla casa del tablero
 * 
 * @author Wilmer Rodríguez, Miguel Monroy
 * @version 1.0.0
 */
public class Home extends Box implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor de la clase Home
	 * 
	 * @param num   de tipo int que es el numero de la casilla en el tablero
	 * @param color de tipo Color que es el color de la casilla
	 */
	public Home(int num, Color color) {
		super(num);
		this.color = color;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void state() {
		free = (pieces.size() == 0) ? true : false;
		block = (pieces.size() == 4) ? true : false;
	}
}
