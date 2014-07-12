package ca.sariarra.poker.view.player.action;

import ca.sariarra.poker.player.actions.Action;
import ca.sariarra.poker.player.actions.PlayerAction;
import ca.sariarra.poker.view.table.component.PlayerView;

public class ActionView {

	private final PlayerAction action;
	private final long betAmount;
	private final PlayerView player;

	public ActionView(final Action action) {
		this.action = action.getAction();
		this.betAmount = action.getBetAmount();
		this.player = new PlayerView(action.getPlayer());
	}

	public PlayerAction getAction() {
		return action;
	}

	public long getBetAmount() {
		return betAmount;
	}

	public PlayerView getPlayer() {
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