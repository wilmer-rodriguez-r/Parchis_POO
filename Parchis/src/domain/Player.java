package domain;

import java.awt.*;
import java.io.Serializable;
import java.util.*;

import domain.casillas.Box;
import domain.pieces.Piece;

/**
 * Clase que representa logicamente un jugador
 * 
 * @author Wilmer Rodríguez, Miguel Monroy
 * @version 1.0.0
 */
public class Player implements Serializable {
	private static final long serialVersionUID = 1L;

	private Color color;
	private Team team;
	private String name;

	/**
	 * Constructor de la clase Player
	 * 
	 * @param name   de tipo String es el nombre del jugador
	 * @param color  de tipo Color que sera el color del jugador
	 * @param pieces de tipo int que sera la cantidad de fichas del jugador
	 */
	public Player(String name, Color color, String[] pieces, Box home) {
		this.name = name;
		this.color = color;
		team = new Team(color, pieces, home);
	}

	/**
	 * Obtiene las fichas libre del jugador
	 */
	public ArrayList<Piece> getFreePieces() {
		return team.freePieces();
	}

	/**
	 * Obtiene el color del jugador
	 * 
	 * @return color de tipo Colo es el color del jugador
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Obtiene el equipo del jugador
	 * 
	 * @return team de tipo Team
	 */
	public Team getTeam() {
		return team;
	}

	/**
	 * Obtiene el nombre del jugador
	 * 
	 * @return name de tipo String
	 */
	public String getName() {
		return name;
	}
}