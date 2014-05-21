package ca.sariarra.poker.datastruct;

import ca.sariarra.poker.logic.PokerGame;

public class TournamentTable extends Table {
	private final int tourneyTableNum;

	public TournamentTable(final int numSeats, final boolean pIsCashTable, final PokerGame pGame,
			final long pTableNum, final int pTourneyTableNum) {
		super(numSeats, pIsCashTable, pGame, pTableNum);

		tourneyTableNum = pTourneyTableNum;
	}

	@Override
	protected boolean dealNextHand() {
		// TODO Auto-generated method stub
		return false;
	}

}
