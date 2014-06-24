package ca.sariarra.poker.datastruct;

import java.util.ArrayList;
import java.util.List;

public class Hand {
	private List<Card> exposedCards;
	private List<Card> holeCards;
	private boolean folded;

	public Hand() {
		exposedCards = new ArrayList<Card>(4);
		holeCards = new ArrayList<Card>(4);
	}

	public Card discardCard(final Card c) {
		for (int i = 0; i < exposedCards.size(); i++) {
			if (exposedCards.get(i).equals(c)) {
				Card toDisc = exposedCards.remove(i);
				toDisc.discard();
				return toDisc;
			}
		}

		for (int i = 0; i < holeCards.size(); i++) {
			if (holeCards.get(i).equals(c)) {
				Card toDisc = holeCards.remove(i);
				toDisc.discard();
				return toDisc;
			}
		}

		return null;
	}

	public void resetHand() {
		for (Card c : exposedCards) {
			c.discard();
		}
		exposedCards = new ArrayList<Card>(4);

		for (Card c : holeCards) {
			c.discard();
		}
		holeCards = new ArrayList<Card>(4);

		folded = false;
	}

	public void setFolded(final boolean fold) {
		folded = fold;
	}

	public boolean hasFolded() {
		return folded;
	}

	public void addHole(final Card card) {
		holeCards.add(card);
	}

	public List<Card> getExposedCards() {
		return exposedCards;
	}

	public void addExposed(final Card card) {
		exposedCards.add(card);
	}
}
