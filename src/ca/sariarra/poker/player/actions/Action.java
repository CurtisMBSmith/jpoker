package ca.sariarra.poker.player.actions;

import ca.sariarra.poker.player.Player;

public class Action {

	private PlayerAction action;
	private final Player player;
	private long betAmount;
	private boolean foldConfirm;

	public Action(final Player player, final PlayerAction action) {
		this.player = player;
		this.action = action;
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

	public void setFoldConfirm(final boolean foldConfirm) {
		this.foldConfirm = foldConfirm;
	}

	public Player getPlayer() {
		return player;
	}
}
