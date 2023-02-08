package presentation;

import domain.*;
import domain.exceptions.PoobchisException;
import domain.pieces.Piece;

import java.awt.*;
import java.awt.event.*;
import java.io.Serializable;

import javax.swing.*;

/**
 * Boton que hace de representacion grafica de las fichas
 * 
 * @author Wilmer Rodríguez, Miguel Monroy
 * @version 1.0.0
 */
public class ButtonPiece extends JButton implements ActionListener {

	private Piece piece;

	/**
	 * Constructor de la clase ButtonPiece
	 * 
	 * @param piece  de tipo Piece que es la ficha a la cual representara el boto
	 * @param enable de tipo boolean que activa o desactiva el boton
	 */
	public ButtonPiece(Piece piece, boolean enable) {
		this.piece = piece;
		ImageIcon image = new ImageIcon(piece.getName() + ".png");
		this.setIcon(new ImageIcon(image.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
		this.setBackground(piece.getColor());
		this.setEnabled(enable);
		this.addActionListener(this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			if (Poobchis.getPoobchis().getBonus() != 0) {
				int selection = JOptionPane.showOptionDialog(this, "Mover Bonus", "", JOptionPane.YES_NO_CANCEL_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, new String[] { "Bonus" }, "Bonus");
				if (selection != -1) {
					try {
						Poobchis.getPoobchis().move(piece, new Dice(Poobchis.getPoobchis().getBonus()));
					} catch (PoobchisException ex) {
						JOptionPane.showMessageDialog(this, ex.getMessage());
					}
				}
			} else {
				int selection = JOptionPane.showOptionDialog(this, "Mover Cantidad", "", JOptionPane.YES_NO_CANCEL_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, new String[] { "Dado 1", "Dado 2" }, "opcion 1");
				if (selection != -1) {
					try {
						Dice dice = Poobchis.getPoobchis().getDices().get(selection);
						Poobchis.getPoobchis().move(piece, dice);
					} catch (PoobchisException ex) {
						JOptionPane.showMessageDialog(this, ex.getMessage());
					}
				}
			}
		}catch(Exception exception) {
			Log.record(exception);
		}
		BoardGUI.getBoard().cleanAndShow();
		BoardMenu.getInstance().prepareOptions();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(3, 3);
	}
}