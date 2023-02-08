package domain.pieces;

import java.awt.*;
import java.io.Serializable;

import javax.swing.ImageIcon;

import domain.casillas.Box;
import domain.exceptions.BonusException;

/**
 * Clase que representa logicamente una ficha
 * 
 * @author Wilmer Rodríguez, Miguel Monroy
 * @version 1.0.0
 */
public class Piece implements Serializable {
	private static final long serialVersionUID = 1L;
	protected Color color;
	protected boolean winner;
	protected boolean prisioner;
	protected int steps;
	protected Box box;
	protected boolean jump;
	protected String name;

	/**
	 * Constructor de Piece
	 * 
	 * @param color de tipo Color, es el color de la ficha
	 * @param box   de tipo Box, la casilla donde inicia la ficha
	 */
	public Piece(Color color, Box box) {
		try {
			this.color = color;
			this.box = box;
			this.box.putPiece(this);
			winner = false;
			prisioner = true;
			steps = 0;
			jump = false;
			name = "Piece";
		} catch (BonusException e) {
		}
	}

	/**
	 * Mueve la ficha cambiando los valores del box y de los pasos que recorre la
	 * ficha
	 * 
	 * @param newBox tipo Box es la nueva casilla donde estara la ficha
	 * 
	 * @param steps  es un dato tipo int que da la cantidad de pasos que se mueve la
	 *               ficha
	 */
	public void move(Box newBox, int steps) {
		this.box = newBox;
		this.steps += steps;
		state();
	}

	/**
	 * Ve si la ficha es prisionera o si ya gano
	 */
	public void state() {
		winner = (steps != 72) ? false : true;
		prisioner = (steps == 0) ? true : false;
	}

	/**
	 * Activa el pode de la ficha
	 */
	public void power() {
	}

	/**
	 * Obtiene el color actual de la ficha
	 * 
	 * @return color un dato tipo Color con el color de la ficha
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Obtiene la casilla actual de la ficha
	 * 
	 * @return box un dato tipo Box con la casilla de la ficha
	 */
	public Box getBox() {
		return box;
	}

	/**
	 * Obtiene la cantidad de pasos recorridos por la ficha
	 * 
	 * @return steps un dato entero con el valor recorrido
	 */
	public int getSteps() {
		return steps;
	}

	/**
	 * Obtiene si la ficha ya gano o no
	 * 
	 * @return winner un tipo booleano
	 */
	public boolean isWinner() {
		return winner;
	}

	/**
	 * Obtiene si la ficha es prisionera o no
	 * 
	 * @return winner un tipo booleano
	 */
	public boolean isPrisioner() {
		return prisioner;
	}

	/**
	 * Pregunta si la ficha puede o no saltar bloqueos
	 * 
	 * @return jump de tipo boolean
	 */
	public boolean isJump() {
		return jump;
	}

	/**
	 * Obtiene el nombre del tipo de ficha
	 * 
	 * @return name de tipo String
	 */
	public String getName() {
		return name;
	}
}
