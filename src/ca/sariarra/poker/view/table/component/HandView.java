package ca.sariarra.poker.view.table.component;

import java.util.ArrayList;
import java.util.List;

import ca.sariarra.poker.card.Card;
import ca.sariarra.poker.table.component.Hand;
import ca.sariarra.poker.view.card.CardView;

public class HandView {
	private final List<CardView> holeCards;
	private final List<CardView> exposedCards;
	private final boolean folded;

	public HandView(final Hand hand, final boolean showHoleCards) {
		holeCards = new ArrayList<CardView>(5);

		if (showHoleCards) {
			for (Card card : hand.getHoleCards()) {
				holeCards.add(new CardView(card));
			}
		}

		exposedCards = new ArrayList<CardView>(5);
		for (Card card : hand.getExposedCards()) {
			exposedCards.add(new CardView(card));
		}

		folded = hand.hasFolded();
	}

	public List<CardView> getHoleCards() {
		return holeCards;
	}

	public List<CardView> getExposedCards() {
		return exposedCards;
	}

	public boolean isFolded() {
		return folded;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		if (!holeCards.isEmpty()) {
			sb.append('[');
			for (int i = 0; i < holeCards.size(); i++) {
				if (i != 0) {
					sb.append(", ");
				}

				sb.append(holeCards.get(i).toString());
			}

			sb.append(']');
		}

		if (sb.length() != 0) {
			sb.append(' ');
		}

		if (exposedCards.size() > 0) {
			sb.append(' ');

			for (int i = 0; i < exposedCards.size(); i++) {
				if (i != 0) {
					sb.append(", ");
				}

				sb.append(exposedCards.get(i).toString());
			}
		}


		return sb.toString();
	}
}
