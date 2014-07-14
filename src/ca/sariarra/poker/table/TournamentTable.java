package ca.sariarra.poker.table;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ca.sariarra.poker.game.PokerGame;
import ca.sariarra.poker.table.component.BlindLevel;
import ca.sariarra.poker.table.component.Seat;

public class TournamentTable extends Table {
	private final int tourneyTableNum;

	public TournamentTable(final int numSeats, final PokerGame pGame,
			final long pTableNum, final int pTourneyTableNum) {
		super(numSeats, false, pGame, pTableNum);

		tourneyTableNum = pTourneyTableNum;
	}

	@Override
	protected boolean dealNextHand() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected void setSeatsForHand() {
		List<Seat> seatsInHand = new ArrayList<Seat>(10);
		for (int i = 1; i <= seats.length; i++) {
			if (seats[(button + i) % seats.length] == null) {
				continue;
			}

			if (seats[(button + i) % seats.length].getPlayer() == null) {
				seats[(button + i) % seats.length] = null;
				continue;
			}

			seatsInHand.add(seats[(button + i) % seats.length]);
		}

		seatsForHand = seatsInHand.toArray(new Seat[seatsInHand.size()]);
	}

	@Override
	protected BlindLevel getBlindLevel(final Date time) {
		return game.getBlindLevelByElapsedTime(getElapsedGametime());
	}

	@Override
	protected void closeHand() {
		for (int i = 0; i < seats.length; i++) {
			if (seats[i] != null) {
				if (seats[i].getChipStack().isEmpty()) {
					seats[i] = null;
				}
			}
		}
	}

	@Override
	protected boolean gameIsOver() {
		return getHandCounter() > 1;
	}

	@Override
	public String getDescription() {
		return "Tournament table # " + tourneyTableNum + " - Hand # " + getHandCounter();
	}
}
