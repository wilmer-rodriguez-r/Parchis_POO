package domain;

/**
 * Clase que se encarga de construir el tablero logico
 * 
 * @author Wilmer Rodríguez, Miguel Monroy
 * @version 1.0.0
 */
import java.util.*;

import domain.casillas.Box;
import domain.casillas.Finish;
import domain.casillas.Home;
import domain.casillas.Safe;
import domain.comodines.*;
import domain.exceptions.BonusException;
import domain.exceptions.PoobchisException;
import domain.pieces.Piece;

//import pruebaPresentacion.Prueba;

import java.awt.*;
import java.io.Serializable;

public class Board implements Serializable {
	private static final long serialVersionUID = 1L;
	private ArrayList<Box> mainBoard;
	private HashMap<Color, ArrayList<Box>> ladders;
	private HashMap<Color, Home> homes;
	private HashMap<Color, ArrayList<Box>> roads;
	private Piece lastPiece;
	private ArrayList<String> comodines;
	private int maxComodines;

	/**
	 * Constructor de la clase board
	 */
	public Board(Color[] colors) {
		int start = 12, firstSafe = 5, secondSafe = 0, numColor = 0;
		mainBoard = new ArrayList<>();
		ladders = new HashMap<>();
		homes = new HashMap<Color, Home>();
		maxComodines = 0;
		comodines = new ArrayList<>();
		// Crear el tablero principal
		for (int i = 1; i <= 68; i++) {
			start++;
			firstSafe++;
			secondSafe++;
			if (start == 17) {
				mainBoard.add(new Safe(i, colors[numColor]));
				numColor++;
				start = 0;
			} else if (firstSafe == 17) {
				mainBoard.add(new Safe(i, Color.gray));
				firstSafe = 0;
			} else if (secondSafe == 17) {
				mainBoard.add(new Safe(i, Color.gray));
				secondSafe = 0;
			} else {
				mainBoard.add(new Box(i));
			}
		}
		// Crear los pasillos y agregar las casas
		for (Color color : colors) {
			ArrayList<Box> ladder = new ArrayList<Box>();
			for (int i = 1; i <= 8; i++) {
				ladder.add((i != 8) ? new Safe(i, color) : new Finish(i, color));
			}
			ladders.put(color, ladder);
			homes.put(color, new Home(0, color));
		}
		// Calcula los caminos que deben tomar cada color
		roads(colors);
		// Calcula las posiciones de cada casilla
		position();
	}

	/**
	 * Construye los caminos que deberia recorrer las fichas de un color
	 * 
	 * @param colors es un arreglo de tipo Color que tiene los colores del tablero
	 *               actual
	 */
	private void roads(Color[] colors) {
		roads = new HashMap<Color, ArrayList<Box>>();
		int start = 4;
		for (Color color : colors) {
			ArrayList<Box> road = new ArrayList<Box>();
			for (int i = 0; i < 64; i++) {
				road.add(mainBoard.get((start + i) % 68));
			}
			ArrayList<Box> ladder = ladders.get(color);
			road.addAll(ladder);
			roads.put(color, road);
			start += 17;
		}
	}

	/**
	 * Calcula las posiciones de cada casilla en el tablero
	 */
	private void position() {
		// Arreglo de constantes que son las ubicaciones en el tablero
		int[][] positionsBoard = { { 12, 7 }, { 7, 8 }, { 8, 14 }, { 14, 12 } },
				positionsLadders = { { 20, 10 }, { 10, 1 }, { 1, 10 }, { 10, 20 } };
		int start = 8;
		Color[] colors = Poobchis.colorsDefault;
		for (int i = 0; i < 4; i++) {
			homes.get(colors[i]).setPosition(14 * (((i + 2) % 3) / 2), 14 * (i / 2));
			int x = positionsBoard[i][0], y = positionsBoard[i][1];
			for (int j = 0; j < 17; j++) {
				mainBoard.get(start % 68).setPosition(x, y);
				if (i % 2 == 0) {
					y += (j < 7 && j != 7 && j != 8) ? -1 * (int) Math.pow(-1, i / 2)
							: 1 * (int) Math.pow(-1, i / 2) * (j / 9);
					x += (j == 7 || j == 8) ? -2 * (int) Math.pow(-1, ((i + 1) % 5) / 2) : 0;
				} else {
					x += (j < 7 && j != 7 && j != 8) ? -1 * (int) Math.pow(-1, i / 2)
							: 1 * (int) Math.pow(-1, i / 2) * (j / 9);
					y += (j == 7 || j == 8) ? -2 * (int) Math.pow(-1, ((i + 1) % 5) / 2) : 0;
				}
				start++;
			}
			x = positionsLadders[i][0];
			y = positionsLadders[i][1];
			for (int j = 0; j < 8; j++) {
				ladders.get(colors[i]).get(j).setPosition(x, y);
				if (j != 6) {
					if (i % 2 == 0) {
						x += (i == 0) ? -1 : 1;
					} else {
						y += (i == 1) ? 1 : -1;
					}
				} else {
					if (i % 2 == 0) {
						x += (i == 0) ? -2 : 2;
					} else {
						y += (i == 1) ? 2 : -2;
					}
				}
			}
		}
	}

	/**
	 * Mueve la pieza a travez del mapa
	 * 
	 * @param piece  de tipo Piece es la ficha a mover
	 * 
	 * @param number de tipo int es el numero de casillas que se mueve
	 * 
	 * @throw PoobchisException
	 */
	public void movePiece(Piece piece, int number) throws PoobchisException {
		Color color = piece.getColor();
		ArrayList<Box> road = roads.get(color);
		Box startBox = piece.getBox();
		int numBoxFinal = (road.indexOf(startBox) + number < 0) ? 0 : road.indexOf(startBox) + number;
		Box finalBox = road.get(numBoxFinal);
		try {
			if (isFreePath(startBox, finalBox, road) || piece.isJump()) {
				lastPiece = piece;
				startBox.removePiece(piece);
				finalBox.putPiece(piece);
				piece.move(finalBox, number);
				if (finalBox.capture(piece.getColor()) != null) {
					moveToHome(finalBox.capture(piece.getColor()));
					throw new BonusException(BonusException.BONUS_KILL);
				} else if (piece.isWinner()) {
					throw new BonusException(BonusException.BONUS_WINNER);
				}
			} else {
				throw new PoobchisException(PoobchisException.BLOQUEO);
			}
		} catch (BonusException e) {
			if (e.getMessage() == BonusException.COMODIN_AVANZAR) {
				finalBox.deleteComodin();
				maxComodines--;
				movePiece(piece, number + 5);
			} else if (e.getMessage() == BonusException.COMODIN_RETROCEDER) {
				finalBox.deleteComodin();
				maxComodines--;
				movePiece(piece, number - 5);
			}
			generateComodines();
			throw e;
		}
	}

	/**
	 * Comprueba si la ficha de un color tiene el camino libre
	 * 
	 * @param actualBox de tipo Box es la casilla donde se encuentra la ficha
	 * 
	 * @param finalBox  de tipo Box es la casilla donde deberia terminar la ficha
	 * 
	 * @param road      de tipo ArrayList<Box> es el camino de casillas que toma la
	 *                  ficha
	 * 
	 * @return flag de tipo boolean
	 */
	public boolean isFreePath(Box actualBox, Box finalBox, ArrayList<Box> road) {
		int actualPosition = road.indexOf(actualBox);
		int finalPosition = road.indexOf(finalBox);
		for (int i = finalPosition; i > actualPosition; i--) {
			if (road.get(i).isBlock()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Dado una lista de fichas, comprueba si alguna de esta pueden mover
	 * determinado valor
	 * 
	 * @param pieces de tipo ArrayList<Piece> es la lista de fichas que evaluaremos
	 * 
	 * @param num    de tipo int que sera el numero que movera la ficha
	 * 
	 * @return boolean de tipo boolean que determina si existe la ficha capaz de
	 *         moverse
	 */
	public boolean someMovePiece(ArrayList<Piece> pieces, int num) {
		for (Piece piece : pieces) {
			ArrayList<Box> road = roads.get(piece.getColor());
			Box startBox = piece.getBox(), finalBox = road.get(road.indexOf(startBox) + num);
			if (isFreePath(startBox, finalBox, road) && !piece.isPrisioner() && !piece.isWinner()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Mueve una ficha a la casa correspondiente a su color
	 * 
	 * @param piece de tipo Piece es la ficha a mover
	 */
	public void moveToHome(Piece piece) {
		try {
			if (piece != null) {
				Color color = piece.getColor();
				Box home = homes.get(color);
				piece.getBox().removePiece(piece);
				home.putPiece(piece);
				piece.move(home, -piece.getSteps());
			}
		} catch (BonusException e) {
		}
	}

	/**
	 * Saca una ficha de la casa
	 * 
	 * @param color de tipo Color que es la casa donde se sacara la ficha
	 * 
	 * @throw PoobchisException
	 */
	public void moveToBoard(Color color) throws PoobchisException {
		Box home = homes.get(color);
		ArrayList<Box> road = roads.get(color);
		if (home.getPieces().size() != 0) {
			Piece pieceCapture = road.get(0).capture(color);
			Piece piece = home.getPieces().get(0);
			if (road.get(0).isBlock() && pieceCapture != null) {
				moveToHome(pieceCapture);
				lastPiece = piece;
				home.removePiece(piece);
				road.get(0).putPiece(piece);
				piece.move(road.get(0), 1);
			} else if (!road.get(0).isBlock()) {
				lastPiece = piece;
				home.removePiece(piece);
				road.get(0).putPiece(piece);
				piece.move(road.get(0), 1);
			} else {
				throw new PoobchisException(PoobchisException.NO_SACAR_CASA);
			}
		} else {
			// lanzar error cuando no hay mas fichas para sacar o no se puedan sacar
			throw new PoobchisException(PoobchisException.NO_SACAR_CASA);
		}
	}

	/**
	 * Genera los comodines que van a estar en el tablero
	 */
	public void generateComodines() {
		while (maxComodines != 4 && comodines.size() != 0) {
			Box boxRandom = mainBoard.get((int) (Math.random() * 64));
			String comodinRandom = comodines.get((int) (Math.random() * comodines.size()));
			Comodin newComodin;
			if (boxRandom.getColor().equals(Color.white)) {
				switch (comodinRandom) {
				case "Avanzar":
					newComodin = new Avanzar();
					break;
				case "Retroceder":
					newComodin = new Retroceder();
					break;
				default:
					newComodin = null;
				}
				maxComodines++;
				boxRandom.setComodin(newComodin);
			}
		}
	}

	/**
	 * Añade los comodines que se usaran en el tablero
	 * 
	 * @param comodines de tipo String[] que son los tipos de comodines que estaran
	 *                  en tablero
	 */
	public void setComodines(String[] comodines) {
		if (comodines != null) {
			for (String comodin : comodines) {
				if (comodin != "Ninguno") {
					this.comodines.add(comodin);
				}
			}
		}
	}

	/**
	 * Devuelve la casilla casa que le corresponde a un color, siempre y cuando
	 * exista este color
	 * 
	 * @param color de tipo Color que corresponde al color de un jugador
	 */
	public Box getHome(Color color) {
		return homes.get(color);
	}

	/**
	 * Obtiene la ultima ficha que se jugo en el tablero
	 * 
	 * @return piece de tipo Piece que es la ultima ficha que se movio
	 */
	public Piece getLastPiece() {
		return lastPiece;
	}

	/**
	 * Obtiene las casas del tablero
	 * 
	 * @return homes de tipo HashMap<Color, Home>
	 */
	public HashMap<Color, Home> getHomes() {
		return homes;
	}

	/**
	 * Obtiene las escaleras del tablero
	 * 
	 * @return ladders de tipo HashMap<Color, ArrayList<Box>>
	 */
	public HashMap<Color, ArrayList<Box>> getLadders() {
		return ladders;
	}

	/**
	 * Obtiene las casillas principales del tablero
	 * 
	 * @return mainBoard de tipo ArrayList<Box>
	 */
	public ArrayList<Box> getMainBoard() {
		return mainBoard;
	}
}
