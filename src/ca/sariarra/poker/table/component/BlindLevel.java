package ca.sariarra.poker.table.component;

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

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		if (smallBlind > 0) {
			sb.append("Small blind: ");
			sb.append(smallBlind);
		}

		if (bigBlind > 0) {
			if (sb.length() > 0) {
				sb.append(", ");
			}

			sb.append("Big blind: ");
			sb.append(bigBlind);
		}

		if (ante > 0) {
			if (sb.length() > 0) {
				sb.append(", ");
			}

			sb.append("Ante: ");
			sb.append(ante);
		}

		return sb.toString();
	}
}
