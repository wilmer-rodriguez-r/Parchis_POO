package presentation;

import javax.swing.*;

import domain.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

/**
 * Representacion grafica del menu que aparece a la hora de jugar
 * 
 * @author Wilmer Rodríguez, Miguel Monroy
 * @version 1.0.0
 */
public class BoardMenu extends JPanel {
	private JPanel options, statistics, dices;
	private BoardGUI boardGUI;
	private JButton throwDices, color, back;
	private static BoardMenu boardMenu;

	/**
	 * Constructor de la clase BoardMenu
	 */
	private BoardMenu() {
		throwDices = new JButton("Lanzar dados");
		back = new JButton("Guardar y volver");
		prepareElements();
		prepareBoard();
		prepareActionsBoard();
	}

	/**
	 * Obtiene la instancia de BoardMenu
	 * 
	 * @return boardMenu de tipo BoardMenu
	 */
	public static BoardMenu getInstance() {
		if (boardMenu == null) {
			boardMenu = new BoardMenu();
		}
		return boardMenu;
	}

	/**
	 * Prepara los listener de cada boton
	 */
	private void prepareActionsBoard() {
		throwDices.addActionListener(e -> throwDices());
		back.addActionListener(e -> backAndSave());
	}

	/**
	 * Lanza los dados del tabler y actualiza su representacion grafica
	 */
	private void throwDices() {
		Poobchis.getPoobchis().throwDices();
		prepareOptions();
		BoardGUI.getBoard().cleanAndShow();
	}

	/**
	 * Guarda el juego actual y vuelve al menu principal
	 */
	private void backAndSave() {
		ParchisGUI.getGUI().saveAndBack();
	}

	/**
	 * Prepara las opciones del menu lateral del tablero
	 */
	private void prepareElements() {
		setLayout(new BorderLayout());
		setBackground(new Color(38, 38, 38));
		options = new JPanel();
		prepareOptions();
	}

	/**
	 * Prepara la representacion grafica del tablero
	 */
	private void prepareBoard() {
		boardGUI = BoardGUI.getBoard();
		add(boardGUI);
		validate();
		repaint();
	}

	/**
	 * Crea y dibuja los botones del lateral
	 */
	public void prepareOptions() {
		options.removeAll();

		options.setOpaque(false);
		options.setLayout(new BoxLayout(options, BoxLayout.Y_AXIS));
		options.setAlignmentY(Component.CENTER_ALIGNMENT);

		color = new JButton();
		color.setEnabled(false);
		color.setBackground(Poobchis.getPoobchis().actualPlayer().getColor());

		JLabel actualPlayer = new JLabel("Es el turno de : " + Poobchis.getPoobchis().actualPlayer().getName());
		actualPlayer.setForeground(Color.WHITE);
		JLabel actualWinnerPieces = new JLabel(
				"Fichas ganadoras = " + Poobchis.getPoobchis().actualPlayer().getTeam().getWinners());
		actualWinnerPieces.setForeground(Color.WHITE);
		JLabel actualPrisionerPieces = new JLabel(
				"Fichas en la carcel = " + Poobchis.getPoobchis().actualPlayer().getTeam().getPrisioners());
		actualPrisionerPieces.setForeground(Color.WHITE);
		JLabel actualSteps = new JLabel("Casillas que faltan por recorrer = "
				+ Poobchis.getPoobchis().actualPlayer().getTeam().calculateStepsTotal());
		actualSteps.setForeground(Color.WHITE);

		options.add(actualPlayer);
		options.add(color);
		options.add(actualPrisionerPieces);
		options.add(actualWinnerPieces);
		options.add(actualSteps);

		throwDices.setBackground(Color.BLACK);
		throwDices.setForeground(new Color(65, 105, 225));
		throwDices.setFont(getFont().deriveFont(Font.BOLD));

		if (!Poobchis.getPoobchis().isPlayDices()) {
			throwDices.setEnabled(false);
		} else {
			throwDices.setEnabled(true);
		}

		options.add(throwDices);

		dices = new JPanel();
		dices.setAlignmentX(Component.LEFT_ALIGNMENT);
		dices.setOpaque(false);
		dices.setLayout(new FlowLayout());
		for (Dice dice : Poobchis.getPoobchis().getDices()) {
			JButton diceButton = new JButton();
			diceButton.setPreferredSize(new Dimension(50, 50));
			ImageIcon image = new ImageIcon(dice.getValue() + ".png");
			diceButton.setIcon(new ImageIcon(image.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
			diceButton.setEnabled(false);
			if (dice.isPlay()) {
				diceButton.setBackground(Color.gray);
			} else {
				diceButton.setBackground(Color.DARK_GRAY);
			}
			dices.add(diceButton);
		}

		back.setBackground(Color.BLACK);
		back.setForeground(new Color(65, 105, 225));
		back.setFont(getFont().deriveFont(Font.BOLD));
		back.setAlignmentY(Component.CENTER_ALIGNMENT);

		options.add(dices);
		options.add(back);

		add(options, BorderLayout.EAST);
		this.revalidate();
		this.repaint();
	}
}
