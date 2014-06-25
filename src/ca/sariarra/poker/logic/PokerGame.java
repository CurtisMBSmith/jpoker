package ca.sariarra.poker.logic;

import java.util.List;

import ca.sariarra.poker.datastruct.BlindLevel;
import ca.sariarra.poker.datastruct.Card;
import ca.sariarra.poker.datastruct.Seat;

public abstract class PokerGame {
	private final GameRotation gameRotation;
	private final BlindLevels blindLevels;
	private final HandAction[] handActions;

	protected PokerGame(final GameRotation rotation, final BlindLevels levels, final HandAction[] actions) {
		gameRotation = rotation;
		blindLevels = levels;
		handActions = actions;
	}

	protected PokerGame(final BlindLevels levels, final HandAction[] actions) {
		this(null, levels, actions);
	}

	protected PokerGame getGameByElapsedTime(final long elapsedGameTime) {
		if (gameRotation == null) {
			return this;
		}

		return gameRotation.getGame(elapsedGameTime);
	}

	protected BlindLevel getBlindLevelByElapsedTime(final long elapsedGameTime) {
		return blindLevels.getLevel(elapsedGameTime);
	}

	public HandAction[] getHandActions() {
		return handActions;
	}

	public HandDetails getHandDetails(final long elapsedGameTime) {
		BlindLevel level = getBlindLevelByElapsedTime(elapsedGameTime);
		PokerGame game = getGameByElapsedTime(elapsedGameTime);
		HandAction[] actions = game.getHandActions();

		return new HandDetails(level, game, actions);
	}

	public abstract List<Seat> determineWinners(List<Card> communityCards, Seat... contenders);
}
