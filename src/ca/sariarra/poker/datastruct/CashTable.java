package ca.sariarra.poker.datastruct;

import ca.sariarra.poker.logic.PokerGame;

public class CashTable extends Table {

	public CashTable(final int numSeats, final boolean pIsCashTable, final PokerGame pGame,
			final long pTableNum) {
		super(numSeats, pIsCashTable, pGame, pTableNum);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean dealNextHand() {
		// TODO Auto-generated method stub
		return false;
	}

}
