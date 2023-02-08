package domain;

import java.util.*;

import domain.casillas.Box;
import domain.exceptions.BonusException;
import domain.exceptions.PoobchisException;
import domain.pieces.Piece;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;

/**
 * Clase que se encarga de organizar toda la logica del poobchis
 * 
 * @author Wilmer Rodríguez, Miguel Monroy
 * @version 1.0.0
 */
public class Poobchis implements Serializable {
	private static final long serialVersionUID = 1L;
	public final static Color[] colorsDefault = { Color.yellow, Color.blue, Color.red, Color.green };
	private static Poobchis poobchis;
	private Player player;
	private int turn, penalty;
	private ArrayList<Player> players;
	private ArrayList<Dice> dices;
	private Board board;
	private boolean par;
	private int bonus;

	/**
	 * Constructor de la clase Poobchis
	 */
	private Poobchis() {
		this.board = new Board(colorsDefault);
		this.players = new ArrayList<Player>();
		this.dices = new ArrayList<Dice>();
		this.turn = 0;
		this.penalty = 0;
		this.bonus = 0;
		this.par = false;
		// Se crean los dados
		createDices();
	}

	/**
	 * Crea una nueva instancia de poobchis y asigna los equipos a cada jugador
	 * junto con los comodines para el tablero
	 * 
	 * @param players de tipo int que son la cantidad de jugadores
	 * 
	 * @param pieces de tipo String[] son el tipo de fichas de los grupos
	 * 
	 * @param comodines de tipo String[] son los tipos de comodines para el tablero
	 */
	public static void newPoobchis(String[] names, int players, String[] pieces, String[] comodines) {
		poobchis = new Poobchis();
		// Se crean los jugadores y se les pasa las fichas
		getPoobchis().createPlayers(names, players, pieces);
		// Se generan los comodines en el tablero
		getPoobchis().getBoard().setComodines(comodines);
		getPoobchis().getBoard().generateComodines();
	}

	/**
	 * Obtiene la instancia actual del poobchis
	 * 
	 * @return instace de tipo Poobchis
	 */
	public static Poobchis getPoobchis() {
		if (poobchis == null) {
			return new Poobchis();
		}
		return poobchis;
	}

	/**
	 * Crea los jugadores del juego
	 * 
	 * @param names de tipo String[] son los nombres de los jugadores
	 * 
	 * @param players de tipo int es la cantidad de jugadores del tablero
	 * 
	 * @param pieces de tipo String[] son los tipos de fichas que estaran en el
	 * tablero
	 */
	private void createPlayers(String[] names, int players, String[] pieces) {
		Color[] color = new Color[] { Color.yellow, Color.red, Color.green, Color.blue };
		if (pieces == null) {
			pieces = new String[] { "Normal", "Normal", "Normal", "Normal" };
		}
		for (int i = 0; i < players; i++) {
			Color colorPlayer = color[i];
			Box home = board.getHome(colorPlayer);
			Player player = new Player(names[i] ,colorPlayer, pieces, home);
			if (i == 0) {
				this.player = player;
			}
			this.players.add(player);
		}
	}

	/**
	 * Crea los dados del juego
	 */
	private void createDices() {
		for (int i = 0; i < 2; i++) {
			dices.add(new Dice());
		}
	}

	/**
	 * Mueve una ficha una cantidad de casillas indicadas
	 * 
	 * @param piece de tipo Piece, sera la ficha a mover
	 * 
	 * @param number de tipo int es el numero de casillas que se movera la ficha
	 * 
	 * @throw PoobchisException
	 */
	public void move(Piece piece, Dice dice) throws PoobchisException {
		if (!(dice.isPlay())) {
			try {
				board.movePiece(piece, dice.getValue());
			} catch (BonusException e) {
				dice.play();
				if (e.getMessage() == BonusException.BONUS_KILL) {
					bonus = 20;
				} else if (e.getMessage() == BonusException.BONUS_WINNER) {
					bonus = 10;
				}
				throw e;
			}
			dice.play();
			bonus = 0;
			freePiece();
			winner();
			turn();
		} else {
			throw new PoobchisException(PoobchisException.DADO_JUGADO);
		}
	}

	/**
	 * Lanza los dados del juego
	 */
	public void throwDices() {
		for (Dice dice : dices) {
			dice.throwDice();
		}
		evaluatePlay();
	}

	/**
	 * Evalua los valores de los dados y aplica normas de parchis antes de poder
	 * mover una ficha
	 */
	public void evaluatePlay() {
		evaluatePar();
		if (penalty == 3) {
			board.moveToHome(board.getLastPiece());
			for(Dice dice : dices) {
				dice.play();
			}
			penalty = 0;
			par = false;
		} else {
			freePiece();
			isPosibleMove();
		}
		turn();
	}

	/**
	 * Evalua los valores de los dados para ver si es posible sacar una ficha
	 */
	private void freePiece() {
		try {
			int total = 0;
			for (Dice dice : dices) {
				if (!dice.isPlay()) {
					total += dice.getValue();
					if (dice.getValue() == 5) {
						board.moveToBoard(player.getColor());
						dice.play();
					}
				}
			}
			if (total == 5) {
				board.moveToBoard(player.getColor());
				dices.get(0).play();
				dices.get(1).play();
			}
		} catch (PoobchisException e) {
		}
	}

	/**
	 * Evalua si los valores de los dados son pares
	 */
	private void evaluatePar() {
		if (dices.get(0).getValue() == dices.get(1).getValue()) {
			par = true;
			penalty++;
		} else {
			par = false;
			penalty = 0;
		}
	}

	/**
	 * Mira si entre las fichas del jugador actual se puede mover los dados que
	 * salieron
	 */
	private void isPosibleMove() {
		ArrayList<Piece> pieces = player.getFreePieces();
		for (Dice dice : dices) {
			if ((pieces.size() == 0 || !board.someMovePiece(pieces, dice.getValue()) && !dice.isPlay())) {
				dice.play();
			}
		}
	}

	/**
	 * Retorna el jugador que esta jugando actualmente
	 * @return player de tipo Player
	 */
	public Player actualPlayer() {
		return player;
	}

	/**
	 * Pasa de turno el tablero
	 */
	public void turn() {
		if (dices.get(0).isPlay() && dices.get(1).isPlay() && !par) {
			for (Piece piece : player.getFreePieces()) {
				piece.power();
			}
			turn++;
			player = players.get(turn % players.size());
		}
	}

	/**
	 * Verifica si los dados fueron jugados
	 * @return flag de tipo boolean
	 */
	public boolean isPlayDices() {
		if (dices.get(0).isPlay() && dices.get(1).isPlay()) {
			return true;
		}
		return false;
	}
	/**
	 * La idea seria que lanzara una excepcion si encuentra un ganador para evitar
	 * tantas comprobaciones
	 * 
	 * @throw PoobchisException
	 */
	public void winner() throws PoobchisException{
		for (Player player : players) {
			if (player.getTeam().getWinners() == 4) {
				throw new PoobchisException(PoobchisException.WINNER);
			}
		}
	}

	/**
	 * Obtiene el tablero asociado al juego Poobchis
	 * @return board de tipo Board
	 */
	public Board getBoard() {
		return board;
	}

	/**
	 * Setea el atributo dices
	 * 
	 * @param dices de tipo ArrayList<Dice> que son los nuevos dados del juego
	 */
	public void setDices(ArrayList<Dice> dices) {
		this.dices = dices;
	}

	/**
	 * Obtiene los dados del juego
	 * 
	 * @return dices de tipo ArrayList<Dice>
	 */
	public ArrayList<Dice> getDices() {
		return dices;
	}

	/**
	 * Obtiene el valor del atributo bonus
	 * @return bonus de tipo int
	 */
	public int getBonus() {
		return bonus;
	}

	/**
	 * Permite abrir un archivo seleccionado
	 * 
	 * @param file de tipo File que sera el archivo a abrir
	 * 
	 * @throw PoobchisException
	 */
	public void open(File file) throws PoobchisException {
		if (!file.isFile())
			throw new PoobchisException(PoobchisException.NO_SELECCIONO + file.getName());
		if (!file.getName().contains(".dat"))
			throw new PoobchisException(PoobchisException.NO_TIPO_DAT);
		try {
			ObjectInputStream reader = new ObjectInputStream(new FileInputStream(file.getPath()));
			Poobchis poobchis = (Poobchis) reader.readObject();
			Poobchis.poobchis = poobchis;
			reader.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new PoobchisException(PoobchisException.NO_PUDO_ABRIR);
		}
	}

	/**
	 * Permite guardar en un archivo
	 * 
	 * @param file de tipo File sera el archivo donde se guardara
	 * 
	 * @throw PoobchisException
	 */
	public void save(File file) throws PoobchisException {
		if (file.isFile())
			throw new PoobchisException(PoobchisException.YA_EXISTE + file.getName());
		if (!file.getName().contains(".dat"))
			throw new PoobchisException(PoobchisException.NO_TIPO_DAT);
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file.getPath()));
			out.writeObject(poobchis);
			out.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
			throw new PoobchisException(PoobchisException.NO_GUARDO);
		}
	}
}
