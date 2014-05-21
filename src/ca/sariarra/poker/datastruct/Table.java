package ca.sariarra.poker.datastruct;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import ca.sariarra.poker.logic.HandDetails;
import ca.sariarra.poker.logic.PokerGame;

public abstract class Table implements Runnable {
	private static final long SLEEP_DURATION = 3000;

	private final Seat[] seats;
	private final List<Player> waitList;
	private final boolean isCashTable;
	private final long tableNum;

	private final long tableStart;
	private final long breakTime;
	private boolean closed;
	private int button;
	private final PokerGame game;

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
	}

	@Override
	public void run() {
		while (!closed) {
			if (dealNextHand()) {
				HandDetails actions = game.getHandDetails(new Date().getTime() - tableStart - breakTime);
				runHand(actions);
			}
			else {
				try {
					Thread.sleep(SLEEP_DURATION);
				}
				catch (InterruptedException e) {
					throw new RuntimeException("Table " + tableNum + "'s run thread interrupted while sleeping.");
				}
			}
		}

	}

	private void runHand(final HandDetails actions) {
		// TODO Auto-generated method stub

	}

	protected abstract boolean dealNextHand();

	public void addPlayer(final Player p) {
		if (p == null) {
			throw new IllegalArgumentException("Player must not be null.");
		}

		Random rand = new Random();

		int j = rand.nextInt(seats.length);
		for (int i = 0; i < seats.length; i++) {
			if (seats[(i + j) % seats.length] == null) {
				seats[(i + j) % seats.length] = new Seat(p);
				return;
			}
		}
	}

	public void moveButton() {
		for (int i = 1; i < seats.length; i++) {
			if (seats[(button + i) % seats.length] == null) {
				continue;
			}
			if (isCashTable && seats[(button + i) % seats.length].isSittingOut()) {
				continue;
			}
			button = (button + i) % seats.length;
			break;
		}
	}

	public int playersRemaining() {
		int count = 0;
		for (Seat s : seats) {
			if (s != null && !s.getChipStack().isEmpty()) {
				count++;
			}
		}

		return count;
	}

	public boolean isCashTable() {
		return isCashTable;
	}

	public boolean isClosed() {
		return closed;
	}

	public void setClosed(final boolean close) {
		closed = close;
	}

	public Seat[] getSeats() {
		return seats;
	}

	public List<Player> getWaitList() {
		return waitList;
	}

	/*
	 * Accessors/Mutators
	 */
}
