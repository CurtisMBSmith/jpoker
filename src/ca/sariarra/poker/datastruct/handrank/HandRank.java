package ca.sariarra.poker.datastruct.handrank;

import ca.sariarra.poker.datastruct.Card;

public abstract class HandRank implements Comparable<HandRank> {
	private final boolean highRank;
	private final Card[] cards;
	
	protected HandRank(boolean isHighRank, Card[] hand) {
		highRank = isHighRank;
		cards = hand;
	}

	public boolean isHighRank() {
		return highRank;
	}
	
	public abstract int compareTo(HandRank other);

	public Card[] getCards() {
		return cards;
	}
	
}
