package presentation;

import domain.Poobchis;
import domain.exceptions.PoobchisException;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * Clase que crea todos el frame del juego, junto con los menus y el tablero
 * 
 * @author Wilmer Rodríguez, Miguel Monroy
 * @version 1.0.0
 */
public class ParchisGUI extends JFrame {

	private MainMenuGUI mainMenu;
	private BoardMenu board;
	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JMenuItem newGame, open, save, exit;
	private Poobchis poobchis;
	private static ParchisGUI gui;

	/**
	 * Constructor de la clase ParchisGUI
	 */
	protected ParchisGUI() {
		prepareElements();
		prepareAcciones();
	}

	/**
	 * Obtiene la instancia actual de la clase
	 * @return gui de tipo ParchisGUI
	 */
	public static ParchisGUI getGUI() {
		if (gui == null) {
			gui = new ParchisGUI();
		}
		return gui;
	}

	/**
	 * Crea una nueva instancia de ParchisGUI y la hace visible
	 */
	public static void newGUI() {
		gui = new ParchisGUI();
		gui.setVisible(true);
	}

	/**
	 * Prepara los elementos del Frame
	 */
	private void prepareElements() {
		setTitle("POOBCHIS");
		pack();
		setResizable(false);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		prepareElementsMain();
		prepareElementsMenu();
	}

	/**
	 * Prepara los elementos del menu principal
	 */
	private void prepareElementsMain() {
		mainMenu = new MainMenuGUI();
		add(mainMenu);
		validate();
		repaint();
	}

	/**
	 * Prepara los elementos de la barra de menus
	 */
	private void prepareElementsMenu() {
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		fileMenu = new JMenu("Archivo");
		menuBar.add(fileMenu);

		newGame = new JMenuItem("Nuevo");
		open = new JMenuItem("Abrir");
		save = new JMenuItem("Guardar");
		exit = new JMenuItem("Salir");

		fileMenu.add(newGame);
		fileMenu.add(open);
		fileMenu.add(save);
		fileMenu.add(exit);

		menuBar.add(fileMenu);
	}

	/**
	 * Prepara el menu del tablero a la hora de jugar
	 */
	private void prepareElementsBoard() {
		board = BoardMenu.getInstance();
	}

	/**
	 * Prepara las acciones de la barra de menus
	 */
	private void prepareAcciones() {
		prepareAccionesMenu();
	}

	/**
	 * Asigna los listener a cada buton del los menus
	 */
	private void prepareAccionesMenu() {
		newGame.addActionListener(e -> newGame());
		open.addActionListener(e -> open());
		save.addActionListener(e -> save());
		exit.addActionListener(e -> exit());
	}

	/**
	 * Crea un nuevo juego poobchis
	 */
	private void newGame() {
		remove(board);
		mainMenu.remove(mainMenu.getActualPanel());
		mainMenu.playerVsPlayer();
		add(mainMenu);
	}

	/**
	 * Abre un archivo guardado de Poobchis
	 */
	protected void open() {
		JFileChooser jfile = new JFileChooser();
		if (jfile.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			File file = jfile.getSelectedFile();
			try {
				Poobchis.getPoobchis().open(file);
				BoardMenu.getInstance().prepareOptions();
				BoardGUI.getBoard().cleanAndShow();
			} catch (PoobchisException e) {
				JOptionPane.showMessageDialog(this, e.getMessage());
			}
		}
		remove(mainMenu);
		prepareElementsBoard();
		add(board);
		validate();
		repaint();
	}

	/**
	 * Guarda la instancia actual de Poobchis en un archivo
	 */
	private void save() {
		JFileChooser jfile = new JFileChooser();
		if (jfile.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
			File file = jfile.getSelectedFile();
			try {
				poobchis.save(file);
			} catch (PoobchisException e) {
				JOptionPane.showMessageDialog(this, e.getMessage());
			}
		}
	}
	
	/**
	 * Guarda y vuelve al menu principal del juego
	 */
	protected void saveAndBack() {
		save();
		remove(board);
		add(mainMenu);
		mainMenu.back();
	}
	
	/**
	 * Sale del juego, antes de salir pide confirmación
	 */
	private void exit() {
		if (JOptionPane.showConfirmDialog(rootPane, "Desea terminar el programa?", "Salir",
				JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
			System.exit(0);
		}
	}

	/**
	 * Metodo que crea la nueva instacia del Poobchis con los parametros dados
	 * 
	 * @param numJugadores de tipo int que es la cantidad de jugadores
	 * 
	 * @param pieces de tipo String[] que son los tipos de piezas para los equipos
	 * 
	 * @param comodines de tipo String[] que son los tipos de comodines para el
	 * tablero
	 */
	public void playerVsPlayer(String[] names,int numJugadores, String[] pieces, String[] comodines) {
		Poobchis.newPoobchis(names, numJugadores, pieces, comodines);
		poobchis = Poobchis.getPoobchis();
		prepareElementsBoard();
		BoardMenu.getInstance().prepareOptions();
		remove(mainMenu);
		add(board);
		validate();
		repaint();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(1140, 1010);
	}

	/**
	 * main que crea la instancia del ParchisGUI
	 * @param args de tipo String[]
	 */
	public static void main(String[] args) {
		ParchisGUI.newGUI();
	}
}