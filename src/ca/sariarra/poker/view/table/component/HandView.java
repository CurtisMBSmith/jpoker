package ca.sariarra.poker.view.table.component;

import ca.sariarra.poker.card.Card;
import ca.sariarra.poker.table.component.Hand;

import java.util.LinkedList;
import java.util.List;

public class HandView {
    private final List<Card> holeCards;
    private final List<Card> exposedCards;
    private final boolean folded;

	public HandView(final Hand hand, final boolean showHoleCards) {
        holeCards = showHoleCards ? new LinkedList<>(hand.getHoleCards()) : new LinkedList<>();
        exposedCards = new LinkedList<>(hand.getExposedCards());

		folded = hand.hasFolded();
	}

    public List<Card> getHoleCards() {
        return holeCards;
    }

    public List<Card> getExposedCards() {
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
