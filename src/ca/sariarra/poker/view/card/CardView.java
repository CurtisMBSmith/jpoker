package ca.sariarra.poker.view.card;

import ca.sariarra.poker.card.Card;
import ca.sariarra.poker.card.Rank;
import ca.sariarra.poker.card.Suit;

public class CardView {
	private final Rank rank;
	private final Suit suit;

	public CardView(final Card card) {
		rank = card.rank();
		suit = card.suit();
	}

	public Rank getRank() {
		return rank;
	}

	public Suit getSuit() {
		return suit;
	}

	@Override
	public String toString() {
		return rank.toString() + suit.toString();
	}

	public String toLongString() {
		return rank.toLongString() + " of " + suit.toLongString();
	}
}
