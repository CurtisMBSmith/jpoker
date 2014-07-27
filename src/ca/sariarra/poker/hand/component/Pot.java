package ca.sariarra.poker.hand.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import ca.sariarra.poker.table.component.Seat;

public class Pot {

	private final Map<Seat, Long> wagers;
	private boolean closed;
	private long highestBet;

	public Pot() {
		this.highestBet = 0;
		this.wagers = new HashMap<Seat, Long>();
		this.closed = false;
	}

	public List<Seat> getContestors() {
		List<Seat> contestors = new ArrayList<Seat>();
		for (Entry<Seat, Long> wager : wagers.entrySet()) {
			if (wager.getKey().isFolded()) {
				continue;
			}

			contestors.add(wager.getKey());
		}
		return contestors;
	}

	public Pot addChips(final long amountToAdd, final Seat seat) {
		Pot newPot = null;

		if (wagers.containsKey(seat)) {
			wagers.put(seat, wagers.get(seat) + amountToAdd);
		}
		else {
			wagers.put(seat, amountToAdd);
		}

		if (wagers.get(seat) > highestBet) {
			highestBet = wagers.get(seat);
		}
		else if (wagers.get(seat) < highestBet) {
			if (!seat.isAllIn()) {
				throw new RuntimeException("A seat should not be adding less chips than it has to call if it's not all-in.");
			}

			long newHighWager = wagers.get(seat);
			newPot = new Pot();
			List<Seat> seatsToRemove = new ArrayList<Seat>();
			for (Entry<Seat, Long> wager : wagers.entrySet()) {
				if (wager.getValue() - newHighWager > 0) {
					newPot.addChips(wager.getValue() - newHighWager, wager.getKey());
					wager.setValue(newHighWager);
				}

				if (wager.getValue() <= 0) {
					seatsToRemove.add(wager.getKey());
				}
			}

			for (Seat toRemove : seatsToRemove) {
				wagers.remove(toRemove);
			}
			highestBet = newHighWager;
		}

		if (seat.isAllIn()) {
			closed = true;
			if (newPot == null) {
				newPot = new Pot();
			}
		}

		return newPot;
	}

	public long getUncalledBet(final Seat seat) {
		if (wagers.containsKey(seat)) {
			return highestBet - wagers.get(seat);
		}
		else {
			return highestBet;
		}
	}

	public boolean isClosed() {
		return closed;
	}

	public long getPotSize() {
		long size = 0;
		for (Entry<Seat, Long> wager : wagers.entrySet()) {
			size += wager.getValue();
		}

		return size;
	}

	public void returnUncalledBet() {
		List<Seat> bettorsWithHighestWager = new ArrayList<Seat>();
		for (Entry<Seat, Long> wager : wagers.entrySet()) {
			if (wager.getValue() == highestBet) {
				bettorsWithHighestWager.add(wager.getKey());
			}
		}

		if (bettorsWithHighestWager.size() == 1) {
			long secondHighest = 0;
			for (Entry<Seat, Long> wager : wagers.entrySet()) {
				if (wager.getKey() == bettorsWithHighestWager.get(0)) {
					continue;
				}

				if (wager.getValue() > secondHighest) {
					secondHighest = wager.getValue();
				}
			}

			if (highestBet - secondHighest > 0) {
				bettorsWithHighestWager.get(0).addChips(highestBet - secondHighest);
				highestBet = secondHighest;
				wagers.put(bettorsWithHighestWager.get(0), highestBet);
			}
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Pot: ");
		sb.append(getPotSize());
		sb.append(" chips [");
		List<Seat> contestors = getContestors();
		for (int i = 0; i < contestors.size(); i++) {
			if (i > 0) {
				sb.append(", ");
			}
			sb.append(contestors.get(i).getPlayer().getName());
		}
		sb.append(']');
		return sb.toString();
	}
}
