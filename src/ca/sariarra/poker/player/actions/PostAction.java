package ca.sariarra.poker.player.actions;

import ca.sariarra.poker.player.Player;

public class PostAction extends Action {

	private final String playerName;
	private final ForcedBet forcedBet;
	private final long amount;

	public PostAction(final Player player, final ForcedBet forcedBet, final long amount) {
		this.playerName = player.getName();
		this.forcedBet = forcedBet;
		this.amount = amount;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(playerName);
		sb.append(" posts ");
		sb.append(forcedBet.toString());
		sb.append(" of ");
		sb.append(amount);

		return sb.toString();
	}
}
