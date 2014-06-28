package ca.sariarra.poker.game.component;

import ca.sariarra.poker.game.PokerGame;
import ca.sariarra.poker.game.action.HandAction;
import ca.sariarra.poker.table.component.BlindLevel;

public class HandDetails {
	private final BlindLevel level;
	private final PokerGame game;
	private final HandAction[] actions;

	public HandDetails(final BlindLevel level, final PokerGame game, final HandAction[] actions) {
		this.level = level;
		this.game = game;
		this.actions = actions;
	}

	public BlindLevel getLevel() {
		return level;
	}

	public PokerGame getGame() {
		return game;
	}

	public HandAction[] getActions() {
		return actions;
	}

	public HandAction getAction(final int stageNum) {
		if (stageNum < 0 || stageNum >= actions.length) {
			return null;
		}

		return actions[stageNum];
	}

}
