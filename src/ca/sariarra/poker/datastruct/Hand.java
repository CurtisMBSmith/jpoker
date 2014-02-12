package ca.sariarra.poker.datastruct;

import java.util.ArrayList;
import java.util.List;

public class Hand {
	private List<Card> cards;
	private boolean folded;
	
	public Hand() {
		cards = new ArrayList<Card>();
	}
	
	public Card discardCard(Card c) {
		for (int i = 0; i < cards.size(); i++) {
			if (cards.get(i).equals(c)) {
				Card toDisc = cards.remove(i);
				toDisc.discard();
				return toDisc;
			}
		}
		
		return null;
	}
	
	public void resetHand() {
		for (Card c : cards) {
			c.discard();
		}
		
		folded = false;
	}
	
	public void setFolded(boolean fold) {
		folded = fold;
	}
	
	public boolean hasFolded() {
		return folded;
	}
}
