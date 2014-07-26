package ca.sariarra.poker.hand.component;

import java.util.List;

import ca.sariarra.poker.table.component.Seat;

public class Pot {

	private long amount;
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

	public void addChips(final long amountToAdd) {
		amount += amountToAdd;
	}

}
