package ca.sariarra.poker.card;

public enum Rank {
	TWO("2", "Two", 2),
	THREE("3", "Three", 3),
	FOUR("4", "Four", 4),
	FIVE("5", "Five", 5),
	SIX("6", "Six", 6),
	SEVEN("7", "Seven", 7),
	EIGHT("8", "Eight", 8),
	NINE("9", "Nine", 9),
	TEN("T", "Ten", 10),
	JACK("J", "Jack", 11),
	QUEEN("Q", "Queen", 12),
	KING("K", "King", 13),
	ACE("A", "Ace", 1);

	private final String shortDesc;
	private final String longDesc;
	private final int value;

	private Rank(final String sDesc, final String lDesc, final int val) {
		shortDesc = sDesc;
		longDesc = lDesc;
		value = val;
	}

	@Override
	public String toString() {
		return shortDesc;
	}

	public String toLongString() {
		return longDesc;
	}

	public int getValue() {
		return value;
	}
}
