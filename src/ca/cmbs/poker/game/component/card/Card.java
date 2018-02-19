package ca.cmbs.poker.game.component.card;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

    private static Card fromString(String cardStr) {
        if (cardStr.length() != 2) {
            throw new IllegalArgumentException("Card string must be exactly 2 characters");
        }

        return new Card(Rank.fromChar(cardStr.charAt(0)), Suit.fromChar(cardStr.charAt(1)));
    }

    public int hashCode() {
        return Integer.hashCode(rank.ordinal()) + Integer.hashCode(suit.ordinal());
    }

    public int compareTo(Card other) {
		return rank.compareTo(other.rank());
	}

    public static List<Card> fromString(String... cards) {
        return Arrays.stream(cards).map(Card::fromString).collect(Collectors.toList());
    }

    public static boolean isCard(final String cardString) {
        try {
            fromString(cardString);
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;

    }

    public boolean equals(Object o) {
        if (o instanceof Card) {
            Card other = (Card) o;

            return rank == other.rank();
        }

        return false;
    }
}
