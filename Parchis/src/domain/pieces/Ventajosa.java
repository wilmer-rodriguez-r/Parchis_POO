package domain.pieces;

import java.awt.Color;

import domain.Dice;
import domain.Poobchis;
import domain.casillas.Box;
import domain.exceptions.PoobchisException;

/**
 * Clase que representa una ficha de tipo Ventajosa
 * 
 * @author Wilmer Rodríguez, Miguel Monroy
 * @version 1.0.0
 */
public class Ventajosa extends Piece {
	private static final long serialVersionUID = 1L;
	private int turn;
	
	/**
	 * Constructor de la clase Ventajosa
	 * 
	 * @param color de tipo Color, es el color de la ficha
	 * @param box   de tipo Box, la casilla donde inicia la ficha
	 */
	public Ventajosa(Color color, Box box) {
		super(color, box);
		name = "Ventajosa";
		turn = 0;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void power() {
		turn += (!prisioner && !winner)? 1 : 0;
		if (turn == 2) {
			try {
				Poobchis.getPoobchis().getBoard().movePiece(this, 3);;
			} catch (PoobchisException e) {
			} finally {
				turn = 0;
			}
		}
	}
}
