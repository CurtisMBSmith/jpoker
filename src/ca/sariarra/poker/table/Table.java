package ca.sariarra.poker.table;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import ca.sariarra.poker.game.PokerGame;
import ca.sariarra.poker.game.component.HandDetails;
import ca.sariarra.poker.hand.HandOfPlay;
import ca.sariarra.poker.player.Player;
import ca.sariarra.poker.table.component.BlindLevel;
import ca.sariarra.poker.table.component.Seat;
import ca.sariarra.poker.view.table.TableView;

public abstract class Table implements Runnable {

	private static final long SLEEP_DURATION = 3000;

	protected final Seat[] seats;
	private final List<Player> waitList;
	private final boolean isCashTable;
	private final long tableNum;
	private int handCounter;
	private final long tableStart;
	private final long breakTime;
	private boolean closed;
	protected int button;
	protected final PokerGame game;
	private HandOfPlay currentHand;

	public Table(final int numSeats, final boolean pIsCashTable, final PokerGame pGame, final long pTableNum) {
		if (numSeats <= 0) {
			throw new IllegalArgumentException("Number of seats must be positive.");
		}

		seats = new Seat[numSeats];
		isCashTable = pIsCashTable;
		game = pGame;
		tableNum = pTableNum;
		waitList = new ArrayList<Player>();
		closed = false;
		button = 0;
		tableStart = new Date().getTime();
		breakTime = 0;
		handCounter = 0;
	}

	@Override
	public void run() {
		for (Seat s : seats) {
			s.setSittingOut(false);
		}

		while (!closed) {
			if (gameIsOver()) {
				return;
			}

			if (dealNextHand()) {
				moveButton();
				Seat[] seatsForHand = setSeatsForHand();

				HandDetails actions = game.getHandDetails(new Date().getTime() - tableStart - breakTime);
				currentHand = new HandOfPlay(seatsForHand, this, actions.getGame(), actions.getActions(), actions.getLevel(), ++handCounter);
				currentHand.run();
				closeHand();

				resetTableState();
			}
			else {
				try {
					Thread.sleep(SLEEP_DURATION);
				}
				catch (InterruptedException e) {
					throw new RuntimeException("Table " + tableNum + "'s run thread interrupted while sleeping.", e);
				}
			}
		}

	}

	protected abstract void closeHand();

	private void resetTableState() {
		for (Seat seat : seats) {
			seat.resetForHand();
		}

		// If any seat on the table is now empty, remove the seat.
		for (int i = 0; i < seats.length; i++) {
			if (seats[i] == null) {
				continue;
			}

			if (seats[i].getPlayer() == null) {
				seats[i] = null;
			}
		}
	}

	protected abstract boolean gameIsOver();

	protected abstract boolean dealNextHand();

	protected abstract Seat[] setSeatsForHand();

	protected abstract BlindLevel getBlindLevel(Date time);

	public abstract String getDescription();

	public void updatePlayers() {
		for (Seat seat : seats) {
			if (seat != null) {
				seat.updateTableState(new TableView(this, seat.getPlayer()));
			}
		}
	}



	public void seatPlayer(final Player player, final long chips) {
		if (player == null) {
			throw new IllegalArgumentException("Player must not be null.");
		}

		Random rand = new Random();

		int j = rand.nextInt(seats.length);
		for (int i = 0; i < seats.length; i++) {
			if (seats[(i + j) % seats.length] == null) {
				Seat seat = new Seat(player);
				seat.addChips(chips);
				seats[(i + j) % seats.length] = seat;
				return;
			}
		}
	}

	/**
	 * Moves the dealer button to the next player in the hand.
	 */
	public abstract void moveButton();

	public int playersRemaining() {
		int count = 0;
		for (Seat s : seats) {
			if (s != null && !s.getChipStack().isEmpty()) {
				count++;
			}
		}

		return count;
	}

	public long getElapsedGametime() {
		return new Date().getTime() - tableStart;
	}

	public int getNumberOfPlayersOnTable() {
		int num = 0;
		for (Seat seat : seats) {
			if (seat != null) {
				num++;
			}
		}

		return num;
	}

	/*
	 * Accessors/Mutators
	 */

	public boolean isCashTable() {
		return isCashTable;
	}

	public boolean isClosed() {
		return closed;
	}

	public void setClosed(final boolean close) {
		closed = close;
	}

	public List<Player> getWaitList() {
		return waitList;
	}

	public Seat[] getSeats() {
		return seats;
	}

	public int getHandCounter() {
		return handCounter;
	}

	public HandOfPlay getCurrentHand() {
		return currentHand;
	}

}
