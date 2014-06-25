package ca.sariarra.poker.datastruct.handrank;

import java.util.List;

import ca.sariarra.poker.datastruct.Card;
import ca.sariarra.poker.datastruct.HandRanking;

public class HandRank implements Comparable<HandRank> {
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


}
