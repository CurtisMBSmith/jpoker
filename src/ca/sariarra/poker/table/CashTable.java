package ca.sariarra.poker.table;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ca.sariarra.poker.game.PokerGame;
import ca.sariarra.poker.table.component.BlindLevel;
import ca.sariarra.poker.table.component.Seat;

public class CashTable extends Table {
	private final BlindLevel level;

	public CashTable(final int numSeats, final boolean pIsCashTable, final PokerGame pGame,
			final long pTableNum, final BlindLevel level) {
		super(numSeats, pIsCashTable, pGame, pTableNum);
		this.level = level;
	}

	@Override
	protected boolean dealNextHand() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected Seat[] setSeatsForHand() {
		List<Seat> seatsInHand = new ArrayList<Seat>(10);
		for (int i = 1; i <= seats.length; i++) {
			if (seats[button + i % seats.length] == null) {
				continue;
			}

			if (seats[button + i % seats.length].isSittingOut()) {
				continue;
			}

			if (seats[button + i % seats.length].getPlayer() == null) {
				seats[button + i % seats.length] = null;
				continue;
			}

			seatsInHand.add(seats[button + i % seats.length]);
		}

		return seatsInHand.toArray(new Seat[seatsInHand.size()]);
	}

	@Override
	protected BlindLevel getBlindLevel(final Date time) {
		return level;
	}

	@Override
	protected void closeHand() {
		// TODO Auto-generated method stub

	}

	@Override
	protected boolean gameIsOver() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getDescription() {
		return "Cash table";
	}

	@Override
	public void moveButton() {
		// TODO Auto-generated method stub

	}


}
