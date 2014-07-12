package ca.sariarra.poker.view.table.component;

import ca.sariarra.poker.table.component.ChipStack;

public class ChipStackView {

	private final long totalChips;
	private final boolean pennyBase;

	public ChipStackView(final ChipStack chipStack) {
		this.totalChips = chipStack.getTotal();
		this.pennyBase = chipStack.pennyBase();
	}

	public long getTotalChips() {
		return totalChips;
	}

	public boolean allIn() {
		return totalChips == 0l;
	}

	@Override
	public String toString() {
		if (pennyBase) {
			return "" + (totalChips / 100) + (totalChips % 100 < 10 ? ".0" : ".") + (totalChips % 100);
		}
		else {
			return "" + totalChips;
		}
	}
}
