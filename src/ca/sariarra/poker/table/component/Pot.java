package ca.sariarra.poker.table.component;

import java.util.List;

public class Pot {

	private final long amount;
	private final List<Seat> contestors;

	public Pot(final long amount, final List<Seat> contestors) {
		this.amount = amount;
		this.contestors = contestors;
	}

	public long getAmount() {
		return amount;
	}

	public List<Seat> getContestors() {
		return contestors;
	}

}
