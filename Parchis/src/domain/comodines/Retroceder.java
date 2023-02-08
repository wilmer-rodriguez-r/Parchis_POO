package domain.comodines;

import java.io.Serializable;

import javax.swing.ImageIcon;

import domain.exceptions.BonusException;

/**
 * Clase que se encarga de la logica del comodin Retroceder
 * 
 * @author Wilmer Rodríguez, Miguel Monroy
 * @version 1.0.0
 */
public class Retroceder extends Comodin implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/*
	 * Constructor de la clase Retroceder
	 */
	public Retroceder() {
		name = "Retroceder";
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
		throw new BonusException(BonusException.COMODIN_RETROCEDER);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isPlay() {
		return play;
	}
}
