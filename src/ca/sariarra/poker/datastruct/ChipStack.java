package ca.sariarra.poker.datastruct;

public class ChipStack {
	private long amount;
	private final boolean allowPennies;

	public ChipStack() {
		this(false, 0);
	}

	public ChipStack(final boolean pAllowPennies) {
		this(pAllowPennies, 0);
	}

	public ChipStack(final boolean pAllowPennies, final long startingStack) {
		this.amount = startingStack;
		this.allowPennies = pAllowPennies;
	}

	public boolean isEmpty() {
		return amount == 0;
	}

	public long removeChips(final long pAmount)  {
		long res;

		res = pAmount >= amount ? amount : pAmount;
		amount -= res;
		return res;
	}
	@Override
	public String toString() {
		if (allowPennies) {
			return "" + (amount / 100) + (amount % 100 < 10 ? ".0" : ".") + (amount % 100);
		}
		else {
			return "" + amount;
		}
	}

	public void collect(final long pAmount) {
		amount += pAmount;
	}
}
