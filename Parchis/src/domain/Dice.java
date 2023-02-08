package domain;

import java.io.Serializable;

import javax.swing.ImageIcon;

/**
 * Clase que representa logicamente un dado
 * 
 * @author Wilmer Rodríguez, Miguel Monroy
 * @version 1.0.0
 */
public class Dice implements Serializable {
	private static final long serialVersionUID = 1L;
	private int value;
	private boolean play;

	/**
	 * Constructor de la clase Dice
	 */
	public Dice() {
		value = 0;
		play = true;
	}

	/**
	 * Constructor de la clase Dice
	 * 
	 * @param num de tipo int que sera el valor del dado
	 */
	public Dice(int num) {
		value = num;
		play = false;
	}

	/**
	 * Lanza el dado y obtiene un valor entre 1 y 6
	 * 
	 * @return value de tipo int que corresponde al valor del dado actual
	 */
	public int throwDice() {
		play = false;
		value = (int) (Math.random() * 6 + 1);
		return value;
	}

	/**
	 * Obtiene el valor del dado
	 * 
	 * @return value de tipo int
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Cambia el valor de play cuando se juega el dado
	 */
	public void play() {
		this.play = !play;
	}

	/**
	 * Obtiene si el dado ya fue jugado o no
	 * 
	 * @return play de tipo boolean
	 */
	public boolean isPlay() {
		return play;
	}

	/**
	 * Cambia el valor del dado por uno dado
	 * 
	 * @param value de tipo int que es el nuevo valor del dado
	 */
	public void changeValue(int value) {
		this.value = value;
		this.play = false;
	}
}
