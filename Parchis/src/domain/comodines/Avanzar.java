package domain.comodines;

import java.io.Serializable;

import javax.swing.ImageIcon;

import domain.exceptions.BonusException;

/**
 * Clase que se encarga de la logica del comodin Avanzar
 * 
 * @author Wilmer Rodríguez, Miguel Monroy
 * @version 1.0.0
 */
public class Avanzar extends Comodin implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor de la clase Avanzar
	 */
	public Avanzar() {
		name = "Avanzar";
		play = false;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getName() {
		return name;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void use() throws BonusException{
		play = true;
		throw new BonusException(BonusException.COMODIN_AVANZAR);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isPlay() {
		return play;
	}
}
