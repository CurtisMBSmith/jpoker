package ca.sariarra.poker.player.actions;

public class Action {

	private PlayerAction action;
	private long betAmount;
	private boolean foldConfirm;

	public Action(final PlayerAction action) {
		this.action = action;
	}

	public Action(final PlayerAction action, final long betAmount) {
		this(action);
		this.betAmount = betAmount;
	}

	public Action(final PlayerAction action, final boolean confirmFold) {
		this(action, 0);
		this.foldConfirm = confirmFold;
	}

	public PlayerAction getAction() {
		return action;
	}

	public void setAction(final PlayerAction newAction) {
		action = newAction;
	}

	public long getBetAmount() {
		return betAmount;
	}

	public void setBetAmount(final long amount) {
		betAmount = amount;
	}

	public boolean getFoldConfirm() {
		return foldConfirm;
	}
}
