package ca.sariarra.poker.hand.component;

import java.util.ArrayList;
import java.util.List;

import ca.sariarra.poker.table.component.Seat;

public class PotManager {
	private final List<Pot> pots;

	public PotManager() {
		pots = new ArrayList<Pot>();
		pots.add(new Pot());
	}

	public void add(final Seat seat, long amount) {
		Pot pot;
		Pot newPot;
		for (int i = 0; i < pots.size() && amount > 0; i++) {
			pot = pots.get(i);
			newPot = null;

			if (pot.isClosed()) {
				if (pot.getUncalledBet(seat) == 0) {
					continue;
				}
				else {
					if (pot.getUncalledBet(seat) > amount) {
						newPot = pot.addChips(amount, seat);
						pots.add(i, newPot);
						amount = 0;
					}
					else {
						amount -= pot.getUncalledBet(seat);
						pot.addChips(pot.getUncalledBet(seat), seat);
					}
				}
			}
			else {
				newPot = pot.addChips(amount, seat);
				amount = 0;

				// If newPot is not null then the seat was put all in by their
				// bet, so a new pot will be returned.
				if (newPot != null) {
					pots.add(newPot);
				}
			}
		}
	}

	public long getUncalledBet(final Seat seat) {
		long result = 0;
		for (Pot pot : pots) {
			result += pot.getUncalledBet(seat);
		}

		return result;
	}

	public Long getTotalSize() {
		long size = 0;
		for (Pot pot : pots) {
			size += pot.getPotSize();
		}

		return size;
	}

	public void returnUncalledBet() {
		pots.get(pots.size() - 1).returnUncalledBet();
	}

	public List<Pot> getPots() {
		return pots;
	}

}
