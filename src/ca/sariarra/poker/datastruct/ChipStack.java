package ca.sariarra.poker.datastruct;

public class ChipStack {
	private long amount;
	private boolean allowPennies;
	
	public ChipStack() {
		amount = 0;
		allowPennies = false;
	}
	
	public ChipStack(boolean pAllowPennies) {
		amount = 0;
		allowPennies = pAllowPennies;
	}
	
	public void addChips(long pAmount) {
		amount += pAmount;
	}
	
	public boolean isEmpty() {
		return amount == 0;
	}
	
	public long removeChips(long pAmount)  {
		long res;
		
		res = pAmount >= amount ? amount : pAmount;
		amount -= res;
		return res;
	}
	public String toString() {
		if (allowPennies) {
			return "" + (amount / 100) + (amount % 100 < 10 ? ".0" : ".") + (amount % 100);
		}
		else {
			return "" + amount;
		}
	}
}
