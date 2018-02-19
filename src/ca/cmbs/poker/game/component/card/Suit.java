package ca.cmbs.poker.game.component.card;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum Suit {
    CLUBS('c', "Clubs"),
    SPADES('s', "Spades"),
    DIAMONDS('d', "Diamonds"),
    HEARTS('h', "Hearts");

    private static final Map<Character, Suit> CHARS_TO_SUITS;

    static {
        CHARS_TO_SUITS = Arrays.stream(Suit.values()).collect(Collectors.toMap(Suit::toChar, Function.identity()));
    }

    private final char character;
    private final String name;

    Suit(char character, String name) {
        this.character = character;
        this.name = name;
    }

    public char toChar() {
        return character;
    }

    public String getName() {
        return name;
    }


    /**
     * Given a character, return the Suit associated to that character.
     *
     * @param character The character to be mapped to a suit.
     * @return The Suit associated with the character if one exists.
     * @throws IllegalArgumentException If the character cannot be mapped to a Suit.
     */
    public static Suit fromChar(char character) {
        if (CHARS_TO_SUITS.get(character) == null) {
            throw new IllegalArgumentException("No mapping of character " + character + " to a suit.");
        }

        return CHARS_TO_SUITS.get(character);
    }
}
