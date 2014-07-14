package ca.sariarra.poker.player.actions;

import ca.sariarra.poker.table.component.Hand;
import ca.sariarra.poker.table.component.Seat;

public class ShowAction extends Action {
	private final String playerName;
	private final Hand hand;

	public ShowAction(final Seat seat) {
		playerName = seat.getPlayer().getName();
		hand = seat.getHand();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(playerName);
		sb.append(" shows ");
		sb.append(hand.toString());

		return sb.toString();
	}
}
