package ca.sariarra.poker.card;

public enum Suit {
    CLUBS('c', "Clubs"),
    SPADES('s', "Spades"),
    DIAMONDS('d', "Diamonds"),
    HEARTS('h', "Hearts");

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
}
