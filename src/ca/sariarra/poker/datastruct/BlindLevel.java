package ca.sariarra.poker.datastruct;

public class BlindLevel {
	private final long bigBlind;
	private final long smallBlind;
	private final long ante;

	public BlindLevel(final long bigBlind, final long smallBlind, final long ante) {
		this.bigBlind = bigBlind;
		this.smallBlind = smallBlind;
		this.ante = ante;
	}

	public long getBigBlind() {
		return bigBlind;
	}

	public long getSmallBlind() {
		return smallBlind;
	}

	public long getAnte() {
		return ante;
	}

}
