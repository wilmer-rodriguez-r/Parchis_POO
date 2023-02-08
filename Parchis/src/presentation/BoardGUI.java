package presentation;

import domain.*;

import java.awt.*;
import java.io.Serializable;
import java.util.*;
import javax.swing.*;

import domain.casillas.Box;
import domain.casillas.Home;
import domain.pieces.Piece;

/**
 * Representacion grafica del tablero de parchis
 * 
 * @author Wilmer Rodríguez, Miguel Monroy
 * @version 1.0.0
 */
public class BoardGUI extends JPanel {

	private static BoardGUI board;
	private GridBagConstraints constraints;

	/**
	 * Constructor de la clase BoardGUI
	 */
	private BoardGUI() {
		setLayout(new GridBagLayout());
		constraints = new GridBagConstraints();
		cleanAndShow();
	}

	/**
	 * Obtiene la instancia actual del BoardGUI
	 * 
	 * @return board de tipo BoardGUI
	 */
	public static BoardGUI getBoard() {
		if (board == null) {
			board = new BoardGUI();
		}
		return board;
	}

	/**
	 * Limpia todos los componentes y vuelve a pintarlos
	 */
	public void cleanAndShow() {
		this.removeAll();
		paintHomes();
		paintLadders();
		paintMainBoard();
		this.revalidate();
		this.repaint();
	}

	/**
	 * Pinta las casas del tablero
	 */
	private void paintHomes() {
		HashMap<Color, Home> homes = Poobchis.getPoobchis().getBoard().getHomes();
		for (Color color : homes.keySet()) {
			JPanel panel = new JPanel();
			panel.setLayout(new GridLayout(2, 2));
			for (Piece piece : homes.get(color).getPieces()) {
				ButtonPiece button = new ButtonPiece(piece, false);
				panel.add(button);
			}
			panel.setBackground(color);
			panel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
			constraints.gridx = homes.get(color).getPosition()[0];
			constraints.gridy = homes.get(color).getPosition()[1];
			constraints.gridwidth = 8;
			constraints.gridheight = 8;
			constraints.fill = GridBagConstraints.BOTH;
			this.add(panel, constraints);
		}
	}

	/**
	 * Pinta las escaleras del tablero
	 */
	private void paintLadders() {
		HashMap<Color, ArrayList<Box>> ladders = Poobchis.getPoobchis().getBoard().getLadders();
		for (Color color : ladders.keySet()) {
			ArrayList<Box> ladder = ladders.get(color);
			for (Box box : ladder) {
				JPanel panel = new JPanel();
				GridLayout gridLayout = null;
				boolean visible = true;
				panel.setBackground(color);
				panel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
				constraints.gridx = box.getPosition()[0];
				constraints.gridy = box.getPosition()[1];
				if (box.getNum() != ladder.size()) {
					constraints.gridwidth = (Color.yellow.equals(color) || Color.red.equals(color)) ? 1 : 2;
					constraints.gridheight = (Color.yellow.equals(color) || Color.red.equals(color)) ? 2 : 1;
					gridLayout = (Color.yellow.equals(color) || Color.red.equals(color)) ? new GridLayout(1, 2)
							: new GridLayout(2, 1);
				} else {
					constraints.gridwidth = 2;
					constraints.gridheight = 2;
					gridLayout = new GridLayout(2, 2);
					visible = false;
				}
				panel.setLayout(gridLayout);
				for (Piece piece : box.getPieces()) {
					ButtonPiece button;
					if (Poobchis.getPoobchis().actualPlayer().getColor() == piece.getColor()) {
						button = new ButtonPiece(piece, visible);
					} else {
						button = new ButtonPiece(piece, false);
					}
					panel.add(button);
				}
				constraints.fill = GridBagConstraints.BOTH;
				this.add(panel, constraints);
			}
		}
	}

	/**
	 * Pinta las casillas del camino principal del tablero
	 */
	private void paintMainBoard() {
		ArrayList<Box> mainBoard = Poobchis.getPoobchis().getBoard().getMainBoard();
		int x = 1, y = 2, aux = 0;
		for (int i = 8; i < mainBoard.size() + 8; i++) {
			JPanel panel = new JPanel();
			Box box = mainBoard.get(i % 68);
			panel.setBackground(box.getColor());
			panel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
			if (box.getComodin() != null) {
				ImageIcon image = new ImageIcon(box.getComodin().getName() + ".png");
				BackGroundImage backGround = new BackGroundImage(image);
				panel.add(backGround);
			}
			if ((i - 8) % 17 == 0) {
				x = (aux % 2 == 0) ? 1 : 2;
				y = (aux % 2 == 0) ? 2 : 1;
				constraints.weighty = (aux % 2 == 0) ? 0.2 : 1.0;
				constraints.weightx = (aux % 2 == 0) ? 1.0 : 0.2;
				constraints.gridwidth = (aux % 2 == 0) ? 2 : 1;
				constraints.gridheight = (aux % 2 == 0) ? 1 : 2;
				aux++;
			}
			panel.setLayout(new GridLayout(x, y));
			for (Piece piece : box.getPieces()) {
				ButtonPiece button;
				if (Poobchis.getPoobchis().actualPlayer().getColor() == piece.getColor()) {
					button = new ButtonPiece(piece, true);
				} else {
					button = new ButtonPiece(piece, false);
				}
				panel.add(button);
			}
			constraints.gridx = mainBoard.get(i % 68).getPosition()[0];
			constraints.gridy = mainBoard.get(i % 68).getPosition()[1];
			constraints.fill = GridBagConstraints.BOTH;
			this.add(panel, constraints);
		}
		constraints.weighty = 0;
		constraints.weightx = 0;
	}

	/**
	 * Clase que se encarga de pintar las imagenes en un JPanel
	 * 
	 * @author Wilmer Rodríguez, Miguel Monroy
	 * @version 1.0.0
	 */
	private class BackGroundImage extends JPanel {

		private Image image;

		/**
		 * Constructor de la clase BackGroundImage
		 */
		public BackGroundImage(ImageIcon image) {
			this.image = image.getImage();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void paint(Graphics g) {
			g.drawImage(image, 0, 0, 15, 15, this);
			setOpaque(false);
			super.paint(g);
		}
	}
}
