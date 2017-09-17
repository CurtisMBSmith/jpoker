package ca.sariarra.poker.card;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum Rank {

    TWO('2', "Two", 2),
    THREE('3', "Three", 3),
    FOUR('4', "Four", 4),
    FIVE('5', "Five", 5),
    SIX('6', "Six", 6),
    SEVEN('7', "Seven", 7),
    EIGHT('8', "Eight", 8),
    NINE('9', "Nine", 9),
    TEN('T', "Ten", 10),
    JACK('J', "Jack", 11),
    QUEEN('Q', "Queen", 12),
    KING('K', "King", 13),
    ACE('A', "Ace", 1);

    private static final Map<Character, Rank> CHARS_TO_RANKS;

    static {
        CHARS_TO_RANKS = Arrays.stream(Rank.values()).collect(Collectors.toMap(Rank::toChar, Function.identity()));
    }

    private final char character;
    private final String name;
    private final int value;


    Rank(final char character, final String name, final int val) {
        this.character = character;
        this.name = name;
        this.value = val;
    }

    public char toChar() {
        return character;
    }

    public String getName() {
        return name;
    }

	public int getValue() {
		return value;
	}

    /**
     * Given a character, return the Rank associated to that character.
     *
     * @param character The character to be mapped to a rank.
     * @return The Rank associated with the character if one exists.
     * @throws IllegalArgumentException If the character cannot be mapped to a Rank.
     */
    public static Rank fromChar(char character) {
        if (CHARS_TO_RANKS.get(character) == null) {
            throw new IllegalArgumentException("No mapping of character " + character + " to a rank.");
        }

        return CHARS_TO_RANKS.get(character);
    }
}
