package ca.sariarra.poker.card;

import java.util.Objects;

public class Card implements Comparable<Card> {
    private final Rank rank;
    private final Suit suit;

    public Card(Rank rank, Suit suit) {
        Objects.requireNonNull(rank, "Rank must not be null.");
        Objects.requireNonNull(suit, "Suit must not be null.");

        this.rank = rank;
        this.suit = suit;
    }
	
	public Rank rank() {
		return rank;
	}
	
	public Suit suit() {
		return suit;
	}
	
	public String toString() {
        return Character.toString(rank.toChar()) + suit.toChar();
    }

    public String description() {
        return rank.getName() + " of " + suit.getName();
    }
	
	public boolean equals(Object o) {
		if (o instanceof Card) {
			Card other = (Card) o;
			
			return rank == other.rank() && suit == other.suit();
		}
		
		return false;
	}

    public int hashCode() {
        return Integer.hashCode(rank.ordinal()) + Integer.hashCode(suit.ordinal());
    }

    public int compareTo(Card other) {
		return rank.compareTo(other.rank());
	}
}
