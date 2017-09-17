package ca.sariarra.poker.card;

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
}
