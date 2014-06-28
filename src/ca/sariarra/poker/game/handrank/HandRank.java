package ca.sariarra.poker.game.handrank;

import java.util.List;

import ca.sariarra.poker.card.Card;

public class HandRank implements Comparable<HandRank> {
	public static final int HAND_SIZE = 5;

	private final HandRanking rank;
	private final List<Card> cards;

	public HandRank(final HandRanking rank, final List<Card> hand) {
		this.rank = rank;
		this.cards = hand;
	}

	public HandRanking getRank() {
		return rank;
	}

	public List<Card> getCards() {
		return cards;
	}

	@Override
	public int compareTo(final HandRank other) {
		return rank.getComparator().compare(this, other);
	}

	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof HandRank)) {
			return false;
		}

		return this.compareTo((HandRank) other) == 0;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append('[');
		for (Card c : cards) {
			if (sb.length() != 1) {
				sb.append(' ');
			}
			sb.append(c.toString());
		}
		sb.append(']');
		sb.append(" - (");
		sb.append(rank.toString());
		sb.append(')');

		return sb.toString();
	}
}
