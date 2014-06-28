package ca.sariarra.poker.card;

public enum Suit {
	CLUBS("c", "Clubs"),
	SPADES("s", "Spades"),
	DIAMONDS("d", "Diamonds"),
	HEARTS("h", "Hearts");
	
	private final String shortDesc;
	private final String longDesc;

	private Suit (String sDesc, String lDesc) {
		shortDesc = sDesc;
		longDesc = lDesc;
	}
	
	public String toString() {
		return shortDesc;
	}
	
	public String toLongString() {
		return longDesc;
	}
}
