package presentation;

import javax.swing.*;
import java.awt.*;
//import java.io.File;
import java.net.URL;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

/**
 * Clase que contiene la representacion grafica de los principales menus
 * 
 * @author Wilmer Rodríguez, Miguel Monroy
 * @version 1.0.0
 */
public class MainMenuGUI extends JPanel {

	private String[] selectedPieces = new String[4];
	private String[] selectedComodines = new String[2];

	private Image image;
	private JButton play, exit, playerVsPlayer, playerVsMachine, back, continueGame, start;
	private JPanel options, playersMenu, select, pieces, bonus, actualPanel;
	private JComboBox<String> piece1, piece2, piece3, piece4, bonus1, bonus2;
	private JComboBox<Integer> numPlayers;
	private BoardMenu board;

	/**
	 * Constructor de la clase MainMenuGUI
	 */
	public MainMenuGUI() {
		prepareElements();
		prepareActions();
	}

	/**
	 * Prepara los elementos que conformaran el menu principal
	 */
	private void prepareElements() {
		setLayout(new GridBagLayout());
		prepareMain();
		preparePlay();
		prepareSelect();
		image = loadImage("https://i.pinimg.com/originals/10/57/11/10571124e2b4d9c3f0733589a4121e96.gif");
	}

	/**
	 * Prepara los elementos del primer menu
	 */
	private void prepareMain() {
		options = new JPanel();
		options.setOpaque(false);
		options.setLayout(new GridLayout(4, 1, 70, 80));

		play = new JButton("Juego Nuevo");
		play.setBackground(new Color(255, 140, 0));

		continueGame = new JButton("Continuar juego");
		continueGame.setBackground(new Color(255, 140, 0));

		exit = new JButton("Salir");
		exit.setBackground(new Color(255, 140, 0));

		options.add(play);
		options.add(continueGame);
		options.add(exit);

		actualPanel = options;
		add(options);
	}

	/**
	 * Prepara los elementos para el segundo menu
	 */
	private void preparePlay() {
		playersMenu = new JPanel();
		playersMenu.setOpaque(false);
		playersMenu.setLayout(new GridLayout(3, 1, 10, 60));

		playerVsPlayer = new JButton("Jugador vs Jugador");
		playerVsPlayer.setBackground(new Color(255, 140, 0));

		playerVsMachine = new JButton("Jugador vs Maquina");
		playerVsMachine.setBackground(new Color(255, 140, 0));

		back = new JButton("Atras");
		back.setBackground(new Color(255, 140, 0));

		playersMenu.add(playerVsPlayer);
		playersMenu.add(playerVsMachine);
		playersMenu.add(back);
	}

	/**
	 * Prepara el menu de seleccion de fichas
	 */
	private void prepareSelect() {
		select = new JPanel();
		select.setOpaque(false);
		select.setLayout(new BoxLayout(select, BoxLayout.Y_AXIS));
		// Donde se elegira la cantidad de jugadores en el juego
		JPanel players = new JPanel();
		players.setLayout(new BoxLayout(players, BoxLayout.Y_AXIS));
		players.setOpaque(false);
		JLabel selectPlayers = new JLabel("Seleccione la cantidad de jugadores");
		selectPlayers.setForeground(Color.white);
		numPlayers = new JComboBox<Integer>(new Integer[] { 2, 3, 4 });
		((JLabel) numPlayers.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
		players.add(selectPlayers);
		players.add(numPlayers);
		// Crear la seleccion de piezas
		pieces = new JPanel();
		pieces.setLayout(new BoxLayout(pieces, BoxLayout.Y_AXIS));
		pieces.setOpaque(false);
		JLabel pieceSelect1 = new JLabel("Seleccione los tipos de ficha");
		pieceSelect1.setForeground(Color.white);
		String[] typePiece = { "Normal", "Saltarina", "Ventajosa" };
		String[] typeBonus = { "Ninguno", "Avanzar", "Retroceder" };
		piece1 = new JComboBox<String>(typePiece);
		((JLabel) piece1.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
		piece2 = new JComboBox<String>(typePiece);
		((JLabel) piece2.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
		piece3 = new JComboBox<String>(typePiece);
		((JLabel) piece3.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
		piece4 = new JComboBox<String>(typePiece);
		((JLabel) piece4.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
		pieces.add(pieceSelect1);
		pieces.add(piece1);
		pieces.add(piece2);
		pieces.add(piece3);
		pieces.add(piece4);
		// Crea la parte de seleccion de fichas
		JLabel bonusSelect = new JLabel("Seleccione los tipos de bonus");
		bonusSelect.setForeground(Color.WHITE);
		bonusSelect.setAlignmentX(Component.CENTER_ALIGNMENT);
		bonus = new JPanel();
		bonus.setLayout(new BoxLayout(bonus, BoxLayout.Y_AXIS));
		bonus.setOpaque(false);
		bonus1 = new JComboBox<String>(typeBonus);
		((JLabel) bonus1.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
		bonus2 = new JComboBox<String>(typeBonus);
		((JLabel) bonus2.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
		bonus.add(bonusSelect);
		bonus.add(bonus1);
		bonus.add(bonus2);
		// crea el boton que inicia el juego
		start = new JButton("Comenzar");
		start.setBounds(-400, 0, 50, 50);
		// Añade todo al JPanel de settings
		select.add(Box.createVerticalGlue());
		select.add(players);
		select.add(Box.createRigidArea(new Dimension(0, 100)));
		select.add(pieces);
		select.add(Box.createRigidArea(new Dimension(0, 100)));
		select.add(bonus);
		select.add(Box.createRigidArea(new Dimension(0, 100)));
		select.add(start);
	}

	/**
	 * Asigana los listener a cada boton de los menus
	 */
	private void prepareActions() {
		play.addActionListener(e -> play());
		continueGame.addActionListener(e -> continueGame());
		exit.addActionListener(e -> exit());
		playerVsPlayer.addActionListener(e -> playerVsPlayer());
		playerVsMachine.addActionListener(e -> playerVsMachine());
		back.addActionListener(e -> back());
		start.addActionListener(e -> start());
	}

	/**
	 * Quita el panel principal de opciones y pone el de seleccion de tipo de juego
	 */
	private void play() {
		remove(options);
		actualPanel = playersMenu;
		add(playersMenu);
		validate();
		repaint();
	}

	/**
	 * Carga un juego guardado y repinta el Frame
	 */
	private void continueGame() {
		ParchisGUI.getGUI().open();
	}

	/**
	 * Metodo que sale del juego y antes pide confirmacion
	 */
	private void exit() {
		if (JOptionPane.showConfirmDialog(this, "Desea terminar el programa?", "Salir",
				JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
			System.exit(0);
		}
	}

	/**
	 * Quita el panel de seleccion de tipo de juego y pasa al panel de seleccion de
	 * tipo de fichas y comodines
	 */
	protected void playerVsPlayer() {
		remove(playersMenu);
		actualPanel = select;
		add(select);
		validate();
		repaint();
	}

	/**
	 * Crea un tablero con un jugador maquina
	 */
	private void playerVsMachine() {
		JOptionPane.showMessageDialog(this, "Esta funcion aun esta en construccion", "Notificacion",
				JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Empieza el juego con la selecciones escogidas en el menu
	 */
	private void start() {
		String pS1 = (String) piece1.getSelectedItem();
		String pS2 = (String) piece2.getSelectedItem();
		String pS3 = (String) piece3.getSelectedItem();
		String pS4 = (String) piece4.getSelectedItem();

		selectedPieces[0] = pS1;
		selectedPieces[1] = pS2;
		selectedPieces[2] = pS3;
		selectedPieces[3] = pS4;

		String bS1 = (String) bonus1.getSelectedItem();
		String bS2 = (String) bonus2.getSelectedItem();

		selectedComodines[0] = bS1;
		selectedComodines[1] = bS2;

		int num = (Integer) numPlayers.getSelectedItem();
		String[] names = new String[num];
		for (int i = 0; i < num; i++) {
			String name = JOptionPane.showInputDialog("Ingrese su nombre");
			names[i] = name;
		}
		ParchisGUI.getGUI().playerVsPlayer(names, num, selectedPieces, selectedComodines);
	}

	/**
	 * Vuelve del menu de seleccion de tipo de juego a el menu principal
	 */
	protected void back() {
		remove(actualPanel);
		add(options);
		validate();
		repaint();
	}

	/**
	 * Obtiene el panel que se esta presentando actualmente
	 * 
	 * @return actualPanel de tipo JPanel
	 */
	public JPanel getActualPanel() {
		return actualPanel;
	}

	/**
	 * Carga la imagen que es el fondo de los menus
	 * 
	 * @param url de tipo String que es la direccion web donde se pide la imagen
	 */
	private Image loadImage(String url) {
		try {
			getToolkit();
			final Image img = Toolkit.getDefaultToolkit().createImage(new URL(url));
			getToolkit();
			Toolkit.getDefaultToolkit().prepareImage(img, -1, -1, null);
			return img;
		} catch (Exception e) {
			return null;
		}

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, this);
	}
}
