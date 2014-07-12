package ca.sariarra.poker.view.table.component;

import ca.sariarra.poker.player.Player;

public class PlayerView {

	private final String name;

	public PlayerView(final Player player) {
		this.name = player.getName();
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return name;
	}
}
