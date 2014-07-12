package ca.sariarra.poker.view.table.component;

import ca.sariarra.poker.table.component.Seat;

public class SeatView {
	private final HandView hand;
	private final ChipStackView chipStack;
	private final PlayerView playerView;
	private final boolean sittingOut;

	public SeatView(final Seat seat, final boolean showHiddenCards) {
		hand = new HandView(seat.getHand(), showHiddenCards);
		chipStack = new ChipStackView(seat.getChipStack());
		playerView = new PlayerView(seat.getPlayer());
		sittingOut = seat.isSittingOut();
	}

	public SeatView(final Seat seat) {
		this(seat, false);
	}

	public HandView getHand() {
		return hand;
	}

	public ChipStackView getChipStack() {
		return chipStack;
	}

	public PlayerView getPlayerView() {
		return playerView;
	}

	public boolean isSittingOut() {
		return sittingOut;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append(playerView.toString());
		for (int i = sb.length(); i < 20; i++) {
			sb.append(' ');
		}
		sb.append(" [");
		sb.append(hand.isFolded() ? 'F' : ' ');
		sb.append(chipStack.allIn() ? 'A' : ' ');
		sb.append(sittingOut ? 'S' : ' ');
		sb.append(']');

		sb.append(" (");
		sb.append(chipStack.toString());
		sb.append(" chips)");

		String handString = hand.toString();
		if (!handString.isEmpty()) {
			sb.append(' ');
			sb.append('{');
			sb.append(handString);
			sb.append('}');
		}

		return sb.toString();
	}
}
