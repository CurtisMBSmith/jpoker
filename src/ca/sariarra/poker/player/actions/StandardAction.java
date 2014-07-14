package ca.sariarra.poker.player.actions;

import ca.sariarra.poker.player.Player;

public class StandardAction extends Action {

	private PlayerAction action;
	private final Player player;
	private long betAmount;
	private boolean foldConfirm;

	public StandardAction(final Player player, final PlayerAction action) {
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

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append('[');
		sb.append(player.getName());
		sb.append(']');
		sb.append(' ');
		sb.append(action.toString());
		if (betAmount > 0) {
			sb.append(' ');
			sb.append(betAmount);
		}
		sb.append('.');

		return sb.toString();
	}
}
