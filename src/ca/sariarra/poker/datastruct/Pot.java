package ca.sariarra.poker.datastruct;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Pot {
	private Map<Seat, Long> wagers;
	private Seat highestBettor;

	public Pot() {
		reset();
	}

	public void reset() {
		wagers = new HashMap<Seat, Long>(10);
		highestBettor = null;
	}

	public void add(final Seat seat, final long amount) {
		if (wagers.containsKey(seat)) {
			wagers.put(seat, wagers.get(seat) + amount);
		}
		else {
			wagers.put(seat, amount);
		}

		// Adjust the highest bettor.
		highestBettor = null;
		long highestWager = 0;
		for (Entry<Seat, Long> wager : wagers.entrySet()) {
			if (wager.getKey().isFolded()) {
				continue;
			}

			if (wager.getValue() > highestWager) {
				highestWager = wager.getValue();
				highestBettor = wager.getKey();
			}
		}
	}

	public boolean hasUncalledBet() {
		if (highestBettor == null) {
			return false;
		}

		long highestWager = wagers.get(highestBettor);
		for (Entry<Seat, Long> wager : wagers.entrySet()) {
			if (wager.getKey().isAllIn()) {
				continue;
			}
			if (wager.getKey().isFolded()) {
				continue;
			}

			if (wager.getValue() < highestWager) {
				return true;
			}
		}
		return false;
	}

	public Seat uncalledBettor() {
		return highestBettor;
	}

	public long getUncalledBet(final Seat seat) {
		if (highestBettor == null) {
			return 0;
		}

		long highWager = wagers.get(highestBettor);
		if (!wagers.containsKey(seat)) {
			return highWager;
		}

		return highWager - wagers.get(seat);
	}

	public Long getSize() {
		long size = 0;
		for (Entry<Seat, Long> wager : wagers.entrySet()) {
			size += wager.getValue();
		}

		return size;
	}

	public void returnUncalledBet() {
		if (!hasUncalledBet()) {
			return;
		}

		long highWager = wagers.get(highestBettor);
		long secondHighest = 0;
		for (Entry<Seat, Long> wager : wagers.entrySet()) {
			if (wager.getKey() == highestBettor) {
				continue;
			}

			if (wager.getValue() > secondHighest) {
				secondHighest = wager.getValue();
			}
		}

		if (highWager - secondHighest > 0) {
			highestBettor.giveWinnings(highWager - secondHighest);
		}
	}

}