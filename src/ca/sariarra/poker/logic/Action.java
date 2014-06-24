package ca.sariarra.poker.logic;

public class Action {

	private final PlayerAction action;
	private long betAmount;

	public Action(final PlayerAction action) {
		this.action = action;
	}

	public Action(final PlayerAction action, final long betAmount) {
		this(action);
		this.betAmount = betAmount;
	}

	public PlayerAction getAction() {
		return action;
	}

	public long getBetAmount() {
		return betAmount;
	}

	public void setBetAmount(final long amount) {
		betAmount = amount;
	}
}
