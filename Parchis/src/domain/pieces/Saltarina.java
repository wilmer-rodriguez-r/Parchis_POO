package domain.pieces;

import java.awt.Color;

import domain.casillas.Box;

/**
 * Clase que representa una ficha de tipo Saltarina
 * 
 * @author Wilmer Rodríguez, Miguel Monroy
 * @version 1.0.0
 */
public class Saltarina extends Piece {
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor de la clase Saltarina
	 * 
	 * @param color de tipo Color, es el color de la ficha
	 * @param box   de tipo Box, la casilla donde inicia la ficha
	 */
	public Saltarina(Color color, Box box) {
		super(color, box);
		jump = true;
		name = "Saltarina";
	}
}
