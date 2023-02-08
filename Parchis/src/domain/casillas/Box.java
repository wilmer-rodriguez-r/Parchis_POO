package domain.casillas;

import java.util.*;

import domain.comodines.Comodin;
import domain.exceptions.BonusException;
import domain.pieces.Piece;

import java.awt.Color;
import java.io.Serializable;

/**
 * Clase que se encarga de la logica de una casilla
 * 
 * @author Wilmer Rodríguez, Miguel Monroy
 * @version 1.0.0
 */
public class Box implements Serializable {
	private static final long serialVersionUID = 1L;

	protected ArrayList<Piece> pieces;
	protected boolean block;
	protected boolean free;
	protected boolean warzone;
	protected Color color;
	protected int num;
	protected int[] position;
	protected Comodin comodin;

	/**
	 * Constructor de la clase Box
	 * 
	 * @param num de tipo num que es el numero de la casilla en el tablero
	 */
	public Box(int num) {
		pieces = new ArrayList<Piece>();
		block = false;
		free = true;
		warzone = true;
		this.num = num;
		color = Color.white;
	}

	/**
	 * Pone una pieza en la casilla, si esta lo permite
	 * 
	 * @param piece de tipo Piece que es la pieza que entrara en la casilla
	 * 
	 * @throw BonusException
	 */
	public void putPiece(Piece piece) throws BonusException {
		if (comodin == null) {
			pieces.add(piece);
			state();
		} else {
			comodin.use();
		}
	}

	/**
	 * Quita una pieza en especifico de la casilla
	 * 
	 * @param piece de tipo Piece que sera la ficha a quitar de la casilla
	 */
	public void removePiece(Piece piece) {
		pieces.remove(piece);
		state();
	}

	/**
	 * Calcula si la casilla esta libre, bloqueada o ninguno de los dos
	 */
	protected void state() {
		free = (pieces.size() == 0) ? true : false;
		block = (pieces.size() == 2) ? true : false;
	}

	/**
	 * Pregunta si la casilla esta libre o no
	 * 
	 * @return boolean
	 */
	public boolean isFree() {
		return free;
	}

	/**
	 * Pregunta si la casilla esta con bloque o no
	 * 
	 * @return boolean
	 */
	public boolean isBlock() {
		return block;
	}

	/**
	 * Verifica si en la casilla hay una pieza que se pueda caputurar
	 * 
	 * @param color de tipo Color va ser el color de la ficha que captura
	 * 
	 * @return piece de tipo Piece que es la ficha que se puede capturar
	 */
	public Piece capture(Color color) {
		if (warzone && !free) {
			for (Piece piece : pieces) {
				if (piece.getColor() != color) {
					return piece;
				}
			}
		}
		return null;
	}

	/**
	 * Obtiene las fichas de la casilla
	 * 
	 * @return pieces de tipo ArrayList<Piece> que es la lista con las fichas
	 */
	public ArrayList<Piece> getPieces() {
		return pieces;
	}

	/**
	 * Devuelve el valor que tiene asignado la casilla
	 */
	public int getNum() {
		return num;
	}

	/**
	 * Asigna la posicion de la casilla en el tablero
	 * 
	 * @param x de tipo int es la posicion en el eje x
	 * 
	 * @param y de tipo int es la posicion en el eje y, crece en direccion opuesta
	 *          al eje cartesiano
	 */
	public void setPosition(int x, int y) {
		this.position = new int[] { x, y };
	}

	/**
	 * Obtiene la posicion de la casilla
	 * 
	 * @return position de tipo int[]
	 */
	public int[] getPosition() {
		return position;
	}

	/**
	 * Obtiene el color de la casilla actual
	 * 
	 * @return color de tipo Color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Obtiene el comodin que tiene la casilla
	 * 
	 * @return comodin de tipo Comodin
	 */
	public Comodin getComodin() {
		return comodin;
	}

	/**
	 * Asigna un comodin a la casilla, en caso de que ya tenga no coloca
	 * 
	 * @param comodin de tipo Comodin
	 */
	public void setComodin(Comodin comodin) {
		if (comodin != null) {
			this.comodin = comodin;
		}
	}

	/**
	 * Elimina el comodin de la casilla, siempre y cuando este jugado el comodin
	 */
	public void deleteComodin() {
		if (comodin.isPlay()) {
			this.comodin = null;
		}
	}
}
