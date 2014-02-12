package ca.sariarra.poker.datastruct;

import static ca.sariarra.util.ParamUtil.ensureNotNull;

public class Card implements Comparable<Card> {
	private Rank rank;
	private Suit suit;
	private Deck parentDeck;

	public Card(Rank aRank, Suit aSuit, Deck parent) {
		ensureNotNull("Rank", aRank);
		ensureNotNull("Suit", aSuit);
		ensureNotNull("Parent Deck", parent);
		
		rank = aRank;
		suit = aSuit;
		parentDeck = parent;
	}
	
	public Card(Rank aRank, Suit aSuit) {
		ensureNotNull("Rank", aRank);
		ensureNotNull("Suit", aSuit);
		
		rank = aRank;
		suit = aSuit;
	}
	
	public Rank getRank() {
		return rank;
	}
	
	public Suit getSuit() {
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
			
			return rank == other.getRank() && suit == other.getSuit();
		}
		
		return false;
	}
	
	public int compareTo(Card other) {
		return rank.compareTo(other.getRank());
	}
}
