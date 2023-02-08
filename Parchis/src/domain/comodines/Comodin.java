package domain.comodines;

import java.io.Serializable;

import javax.swing.ImageIcon;

import domain.exceptions.BonusException;

/**
 * Clase abstracta que se encarga de la logica de un comodin
 * 
 * @author Wilmer Rodríguez, Miguel Monroy
 * @version 1.0.0
 */
public abstract class Comodin implements Serializable {
	private static final long serialVersionUID = 1L;
	protected String name;
	protected boolean play;

	/**
	 * Obtiene el nombre del comodin
	 * 
	 * @return name de tipo String
	 */
	public abstract String getName();

	/**
	 * Usa el comodin
	 * 
	 * @throw BonusException
	 */
	public abstract void use() throws BonusException;

	/**
	 * Verifica si el comodin ya fue jugado
	 * 
	 * @return play de tipo boolean
	 */
	public abstract boolean isPlay();
}
