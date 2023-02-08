package domain;

import java.awt.*;
import java.io.Serializable;
import java.util.*;

import domain.casillas.Box;
import domain.pieces.*;

/**
 * Clase que representa logicamente un equipo de fichas
 * 
 * @author Wilmer Rodríguez, Miguel Monroy
 * @version 1.0.0
 */
public class Team implements Serializable {
	private static final long serialVersionUID = 1L;
	private int prisioners;
	private int winners;
	private Color color;
	private ArrayList<Piece> pieces;
	private Box home;

	/**
	 * Constructor de la clase Team
	 * 
	 * @param color  de tipo Color es el color del equipo
	 * @param pieces de tipo String[] son el tipo de fichas del equipo
	 * @param home   de tio Box es la casilla de inicio del equipo
	 */
	public Team(Color color, String[] pieces, Box home) {
		this.pieces = new ArrayList<Piece>();
		this.prisioners = pieces.length;
		this.winners = 0;
		this.color = color;
		this.home = home;
		createPieces(pieces);
	}

	/**
	 * Crea las piezas que conforman al grupo
	 * 
	 * @param pieces de tipo String[] donde tiene el tipo de fichas para ponerlas en
	 *               el grupo
	 */
	private void createPieces(String[] pieces) {
		for (String piece : pieces) {
			Piece newPiece;
			switch (piece) {
			case "Saltarina":
				newPiece = new Saltarina(color, home);
				break;
			case "Ventajosa":
				newPiece = new Ventajosa(color, home);
				break;
			default:
				newPiece = new Piece(color, home);
			}
			addPiece(newPiece);
		}
	}

	/**
	 * Agrega una pieza indicada al grupo
	 * 
	 * @param piece dato de tipo Piece que ingresara al grupo
	 */
	public void addPiece(Piece piece) {
		pieces.add(piece);
	}

	/**
	 * Remueve la ficha indicada del grupo
	 */
	public void removePiece(Piece piece) {
		pieces.remove(piece);
	}

	/**
	 * Calcula la cantidad de fichas ganadoras o que estan en la prision
	 */
	private void state() {
		winners = 0;
		prisioners = 0;
		for (Piece piece : pieces) {
			if (piece.isWinner()) {
				winners += 1;
			} else if (piece.isPrisioner()) {
				prisioners += 1;
			}
		}
	}

	/**
	 * Calcula la cantidad de casillas que les falta por recorrer a las piezas
	 * 
	 * @return stepts de tipo int
	 */
	public int calculateStepsTotal() {
		int steps = 0;
		for (Piece piece : pieces) {
			steps += 72 - piece.getSteps();
		}
		return steps;
	}

	/**
	 * Obtiene las fichas que estan libres
	 * 
	 * @return freePieces de tipo ArrayList<Piece>
	 */
	public ArrayList<Piece> freePieces() {
		ArrayList<Piece> freePieces = new ArrayList<>();
		for (Piece piece : pieces) {
			if (!piece.isPrisioner() && !piece.isWinner()) {
				freePieces.add(piece);
			}
		}
		return freePieces;
	}

	/**
	 * Devuelve la cantidad de fichas del grupo que son ganadoras
	 * 
	 * @return winners de tipo int
	 */
	public int getWinners() {
		state();
		return winners;
	}

	/**
	 * Devuelve la cantidad de fichas del grupo que son prisioneras
	 * 
	 * @return prisioner de tipo int
	 */
	public int getPrisioners() {
		state();
		return prisioners;
	}
}
