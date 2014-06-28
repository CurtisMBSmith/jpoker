package ca.sariarra.poker.datastruct;

import static ca.sariarra.util.ParamUtil.notNull;
import ca.sariarra.poker.types.Rank;
import ca.sariarra.poker.types.Suit;

public class Card implements Comparable<Card> {
	private Rank rank;
	private Suit suit;
	private Deck parentDeck;

	public Card(Rank aRank, Suit aSuit, Deck parent) {
		notNull("Rank", aRank);
		notNull("Suit", aSuit);
		notNull("Parent Deck", parent);
		
		rank = aRank;
		suit = aSuit;
		parentDeck = parent;
	}
	
	public Card(Rank aRank, Suit aSuit) {
		notNull("Rank", aRank);
		notNull("Suit", aSuit);
		
		rank = aRank;
		suit = aSuit;
	}
	
	public Rank rank() {
		return rank;
	}
	
	public Suit suit() {
		return suit;
	}
	
	public String toString() {
		return rank.toString() + suit.toString();
	}
	
	public String toLongString() {
		return rank.toLongString() + " of " + suit.toLongString();
	}
	
	public void discard() {
		parentDeck.discard(this);
	}
	
	public boolean equals(Object o) {
		if (o instanceof Card) {
			Card other = (Card) o;
			
			return rank == other.rank() && suit == other.suit();
		}
		
		return false;
	}
	
	public int compareTo(Card other) {
		return rank.compareTo(other.rank());
	}
}
