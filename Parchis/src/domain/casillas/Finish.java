package domain.casillas;

import java.awt.*;
import java.io.Serializable;

/**
 * Clase que representa logicamente una casilla del final
 * 
 * @author Wilmer Rodríguez, Miguel Monroy
 * @version 1.0.0
 */
public class Finish extends Box implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor de la clase Finish
	 * @param num de tipo int que es el numero de la casilla en el tablero
	 * @param color de tipo Color que es el color de la casilla
	 */
	public Finish(int num, Color color) {
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
