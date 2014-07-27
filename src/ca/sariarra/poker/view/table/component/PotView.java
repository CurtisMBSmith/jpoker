package ca.sariarra.poker.view.table.component;

import java.util.ArrayList;
import java.util.List;

import ca.sariarra.poker.hand.component.Pot;
import ca.sariarra.poker.table.component.Seat;

public class PotView {

	private final List<SeatView> contestors;
	private final long size;

	public PotView(final Pot pot) {
		this.contestors = new ArrayList<SeatView>(5);
		for (Seat seat : pot.getContestors()) {
			this.contestors.add(new SeatView(seat));
		}

		this.size = pot.getPotSize();
	}

	public List<SeatView> getContestors() {
		return contestors;
	}

	public long getSize() {
		return size;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(size);
		sb.append(" - ");
		for (int i = 0; i < contestors.size(); i++) {
			if (i != 0) {
				sb.append(", ");
			}
			sb.append(contestors.get(i).getPlayerView().toString());
		}

		return sb.toString();
	}
}
