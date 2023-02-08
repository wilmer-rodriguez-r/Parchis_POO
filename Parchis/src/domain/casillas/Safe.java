package domain.casillas;

import java.awt.*;
import java.io.Serializable;

import domain.pieces.Piece;

/**
 * Clase que representa logicamente una casilla seguro
 * 
 * @author Wilmer Rodríguez, Miguel Monroy
 * @version 1.0.0
 */
public class Safe extends Box implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor de la clase Safe
	 * 
	 * @param num   de tipo int que es el numero de la casilla en el tablero
	 * @param color de tipo Color que es el color de la casilla
	 */
	public Safe(int num, Color color) {
		super(num);
		block = false;
		warzone = false;
		free = true;
		this.color = color;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Piece capture(Color color) {
		if (block && color == this.color) {
			for (int i = pieces.size() - 1; i > -1; i--) {
				if (color != pieces.get(i).getColor()) {
					return pieces.get(i);
				}
			}
		}
		return null;
	}
}
