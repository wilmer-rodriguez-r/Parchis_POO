package domain;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import domain.casillas.Box;
import domain.exceptions.PoobchisException;
import domain.pieces.Piece;

import java.io.Serializable;
import java.util.*;

/**
 * Clase con pruebas unitarias para la parte logica del poobchis
 * 
 * @author Wilmer Rodríguez, Miguel Monroy
 * @version 1.0.0
 */
public class PoobchisTest implements Serializable {
	private static final long serialVersionUID = 1L;

	private Poobchis poobchis;

	@Before
	public void prepareElements() {
		Poobchis.newPoobchis(new String[] { "1", "2" }, 2, null, null);
		poobchis = Poobchis.getPoobchis();
	}

	@Test
	public void shouldMoveToHome() {
		Player player = poobchis.actualPlayer();
		ArrayList<Dice> dices = new ArrayList<>();
		Dice dice0 = new Dice(5);
		Dice dice1 = new Dice(5);
		dices.add(dice0);
		dices.add(dice1);
		poobchis.setDices(dices);
		poobchis.evaluatePlay();
		Box actualBox = poobchis.getBoard().getLastPiece().getBox();
		dice0.changeValue(4);
		dice1.changeValue(4);
		poobchis.evaluatePlay();
		dice0.changeValue(3);
		dice1.changeValue(3);
		poobchis.evaluatePlay();
		Player newPlayer = poobchis.actualPlayer();
		assertNotEquals(player, newPlayer);
		assertEquals(player.getFreePieces().size(), 1);
		assertEquals(actualBox.getPieces().size(), 1);
	}

	@Test
	public void shouldMoveToHomeSecond() {
		Player player = poobchis.actualPlayer();
		ArrayList<Dice> dices = new ArrayList<>();
		Dice dice0 = new Dice(4);
		Dice dice1 = new Dice(4);
		dices.add(dice0);
		dices.add(dice1);
		poobchis.setDices(dices);
		poobchis.evaluatePlay();
		dice0.changeValue(3);
		dice1.changeValue(3);
		poobchis.evaluatePlay();
		dice0.changeValue(5);
		dice1.changeValue(5);
		poobchis.evaluatePlay();
		Player newPlayer = poobchis.actualPlayer();
		assertNotEquals(player, newPlayer);
		assertEquals(player.getFreePieces().size(), 0);
		dice0.changeValue(3);
		dice1.changeValue(3);
		poobchis.evaluatePlay();
		dice0.changeValue(2);
		dice1.changeValue(2);
		poobchis.evaluatePlay();
		dice0.changeValue(1);
		dice1.changeValue(1);
		poobchis.evaluatePlay();
		player = poobchis.actualPlayer();
		assertNotEquals(player, newPlayer);
		assertEquals(newPlayer.getFreePieces().size(), 0);
	}

	@Test
	public void shouldMoveToBoard() {
		ArrayList<Dice> dices = new ArrayList<>();
		Dice dice0 = new Dice(5);
		Dice dice1 = new Dice(5);
		Player player = poobchis.actualPlayer();
		dices.add(dice0);
		dices.add(dice1);
		poobchis.setDices(dices);
		poobchis.evaluatePlay();
		assertEquals(2, player.getFreePieces().size());
	}

	@Test
	public void shouldNoMoveToBoard() {
		ArrayList<Dice> dices = new ArrayList<>();
		Dice dice0 = new Dice(4);
		Dice dice1 = new Dice(4);
		Player player = poobchis.actualPlayer();
		dices.add(dice0);
		dices.add(dice1);
		poobchis.setDices(dices);
		poobchis.evaluatePlay();
		assertEquals(0, player.getFreePieces().size());
	}

	@Test
	public void shouldChangePlayer() {
		try {
			Player player = poobchis.actualPlayer();
			ArrayList<Dice> dices = new ArrayList<>();
			Dice dice0 = new Dice(5);
			Dice dice1 = new Dice(3);
			dices.add(dice0);
			dices.add(dice1);
			poobchis.setDices(dices);
			poobchis.evaluatePlay();
			ArrayList<Piece> pieces = player.getFreePieces();
			poobchis.move(pieces.get(0), dice1);
			Player newPlayer = poobchis.actualPlayer();
			assertNotEquals(player, newPlayer);
		} catch (PoobchisException e) {
		}
	}

	@Test
	public void shouldNoChangePlayer() {
		try {
			Player player = poobchis.actualPlayer();
			ArrayList<Dice> dices = new ArrayList<>();
			Dice dice0 = new Dice(5);
			Dice dice1 = new Dice(5);
			dices.add(dice0);
			dices.add(dice1);
			poobchis.setDices(dices);
			poobchis.evaluatePlay();
			ArrayList<Piece> pieces = player.getFreePieces();
			poobchis.move(pieces.get(0), dice1);
			Player newPlayer = poobchis.actualPlayer();
			assertEquals(player, newPlayer);
		} catch (PoobchisException e) {
		}
	}

	@Test
	public void shouldMoveInBoard() {
		try {
			Player player = poobchis.actualPlayer();
			ArrayList<Dice> dices = new ArrayList<>();
			Dice dice0 = new Dice(5);
			Dice dice1 = new Dice(5);
			dices.add(dice0);
			dices.add(dice1);
			poobchis.setDices(dices);
			poobchis.evaluatePlay();
			dice0.changeValue(3);
			dice1.changeValue(1);
			poobchis.evaluatePlay();
			ArrayList<Piece> pieces = player.getFreePieces();
			poobchis.move(pieces.get(0), dice0);
			poobchis.move(pieces.get(0), dice1);
			assertEquals(pieces.get(0).getBox().getNum(), 9);
			dice0.changeValue(5);
			dice1.changeValue(5);
			poobchis.evaluatePlay();
			dice0.changeValue(5);
			dice1.changeValue(5);
			poobchis.evaluatePlay();
			Player newPlayer = poobchis.actualPlayer();
			assertEquals(newPlayer.getFreePieces().size(), 2);
		} catch (PoobchisException e) {
		}
	}

	@Test
	public void shouldNoMoveInBoard() {
		try {
			Player player = poobchis.actualPlayer();
			ArrayList<Dice> dices = new ArrayList<>();
			Dice dice0 = new Dice(5);
			Dice dice1 = new Dice(5);
			dices.add(dice0);
			dices.add(dice1);
			poobchis.setDices(dices);
			poobchis.evaluatePlay();
			dice0.changeValue(3);
			dice1.changeValue(1);
			poobchis.evaluatePlay();
			ArrayList<Piece> pieces = player.getFreePieces();
			poobchis.move(pieces.get(0), dice0);
			poobchis.move(pieces.get(0), dice1);
			assertEquals(pieces.get(0).getBox().getNum(), 9);
			dice0.changeValue(5);
			dice1.changeValue(5);
			poobchis.evaluatePlay();
			dice0.changeValue(3);
			dice1.changeValue(3);
			poobchis.evaluatePlay();
			Player newPlayer = poobchis.actualPlayer();
			ArrayList<Piece> newPieces = newPlayer.getFreePieces();
			poobchis.move(newPieces.get(0), dice0);
			poobchis.move(newPieces.get(0), dice1);
			dice0.changeValue(4);
			dice1.changeValue(1);
			poobchis.evaluatePlay();
			assertEquals(poobchis.actualPlayer(), player);
			dice0.changeValue(30);
			dice1.changeValue(30);
			poobchis.evaluatePlay();
			poobchis.move(pieces.get(1), dice0);
			poobchis.move(pieces.get(1), dice1);
			assertEquals(pieces.get(1).getBox().getNum(), 14);
		} catch (PoobchisException e) {
		}
	}

	@Test
	public void shouldCapturePiece() {
		try {
			Player player = poobchis.actualPlayer();
			ArrayList<Dice> dices = new ArrayList<>();
			Dice dice0 = new Dice(5);
			Dice dice1 = new Dice(5);
			dices.add(dice0);
			dices.add(dice1);
			poobchis.setDices(dices);
			poobchis.evaluatePlay();
			dice0.changeValue(3);
			dice1.changeValue(1);
			poobchis.evaluatePlay();
			ArrayList<Piece> pieces = player.getFreePieces();
			poobchis.move(pieces.get(0), dice0);
			poobchis.move(pieces.get(0), dice1);
			assertEquals(pieces.get(0).getBox().getNum(), 9);
			dice0.changeValue(5);
			dice1.changeValue(5);
			poobchis.evaluatePlay();
			dice0.changeValue(11);
			dice1.changeValue(27);
			poobchis.evaluatePlay();
			Player newPlayer = poobchis.actualPlayer();
			ArrayList<Piece> newPieces = newPlayer.getFreePieces();
			poobchis.move(newPieces.get(0), dice0);
			poobchis.move(newPieces.get(0), dice1);
			assertEquals(player, poobchis.actualPlayer());
			assertEquals(pieces.get(0).getBox().getNum(), 0);
		} catch (PoobchisException e) {

		}
	}

	@Test
	public void shouldNoCapturePiece() {
		try {
			Player player = poobchis.actualPlayer();
			ArrayList<Dice> dices = new ArrayList<>();
			Dice dice0 = new Dice(4);
			Dice dice1 = new Dice(1);
			dices.add(dice0);
			dices.add(dice1);
			poobchis.setDices(dices);
			poobchis.evaluatePlay();
			ArrayList<Piece> pieces = player.getFreePieces();
			dice0.changeValue(5);
			dice1.changeValue(5);
			poobchis.evaluatePlay();
			ArrayList<Piece> newPieces = poobchis.actualPlayer().getFreePieces();
			dice0.changeValue(3);
			dice1.changeValue(31);
			poobchis.evaluatePlay();
			poobchis.move(newPieces.get(0), dice0);
			poobchis.move(newPieces.get(0), dice1);
			assertEquals(pieces.get(0).getBox().getNum(), 5);
			assertEquals(newPieces.get(0).getBox().getNum(), 5);
		} catch (PoobchisException e) {
		}
	}

	@Test
	public void shouldCapturePieceStart() {
		try {
			Player player = poobchis.actualPlayer();
			ArrayList<Dice> dices = new ArrayList<>();
			Dice dice0 = new Dice(5);
			Dice dice1 = new Dice(34);
			dices.add(dice0);
			dices.add(dice1);
			poobchis.setDices(dices);
			poobchis.evaluatePlay();
			ArrayList<Piece> pieces = player.getFreePieces();
			poobchis.move(pieces.get(0), dice1);
			dice0.changeValue(5);
			dice1.changeValue(5);
			poobchis.evaluatePlay();
			ArrayList<Piece> newPieces = poobchis.actualPlayer().getFreePieces();
			assertEquals(pieces.get(0).getBox().getNum(), 0);
			assertEquals(newPieces.get(0).getBox().getNum(), 39);
		} catch (PoobchisException e) {
		}
	}

	@Test
	public void shouldCapturePieceStart2() {
		try {
			Player player = poobchis.actualPlayer();
			ArrayList<Dice> dices = new ArrayList<>();
			Dice dice0 = new Dice(5);
			Dice dice1 = new Dice(5);
			dices.add(dice0);
			dices.add(dice1);
			poobchis.setDices(dices);
			poobchis.evaluatePlay();
			dice0.changeValue(2);
			dice1.changeValue(1);
			poobchis.evaluatePlay();
			dice0.changeValue(34);
			dice1.changeValue(34);
			ArrayList<Piece> pieces = player.getFreePieces();
			poobchis.move(pieces.get(0), dice1);
			poobchis.move(pieces.get(1), dice0);
			dice0.changeValue(5);
			dice1.changeValue(5);
			poobchis.evaluatePlay();
			ArrayList<Piece> newPieces = poobchis.actualPlayer().getFreePieces();
			assertEquals(pieces.get(0).getBox().getNum(), 0);
			assertEquals(newPieces.get(0).getBox().getNum(), 39);
		} catch (PoobchisException e) {
		}
	}

	@Test
	public void shouldNoCapturePieceStart() {
		try {
			Player player = poobchis.actualPlayer();
			ArrayList<Dice> dices = new ArrayList<>();
			Dice dice0 = new Dice(5);
			Dice dice1 = new Dice(17);
			dices.add(dice0);
			dices.add(dice1);
			poobchis.setDices(dices);
			poobchis.evaluatePlay();
			ArrayList<Piece> pieces = player.getFreePieces();
			poobchis.move(pieces.get(0), dice1);
			assertNotEquals(player, poobchis.actualPlayer());
			dice0.changeValue(4);
			dice1.changeValue(1);
			poobchis.evaluatePlay();
			ArrayList<Piece> newPieces = poobchis.actualPlayer().getFreePieces();
			assertEquals(pieces.get(0).getBox().getNum(), 22);
			assertEquals(newPieces.get(0).getBox().getNum(), 22);
		} catch (PoobchisException e) {
		}
	}

	@Test
	public void shouldMoveToBoardAfter() {
		try {
			Player player = poobchis.actualPlayer();
			ArrayList<Dice> dices = new ArrayList<>();
			Dice dice0 = new Dice(5);
			Dice dice1 = new Dice(5);
			dices.add(dice0);
			dices.add(dice1);
			poobchis.setDices(dices);
			poobchis.evaluatePlay();
			dice0.changeValue(5);
			dice1.changeValue(3);
			poobchis.evaluatePlay();
			ArrayList<Piece> pieces = player.getFreePieces();
			poobchis.move(pieces.get(0), dice1);
			pieces = player.getFreePieces();
			assertEquals(pieces.get(0).getBox().getNum(), 8);
			assertEquals(pieces.get(1).getBox().getNum(), 5);
			assertEquals(pieces.get(2).getBox().getNum(), 5);
		} catch (PoobchisException e) {
		}
	}
}
