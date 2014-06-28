package ca.sariarra.poker.game;

import java.util.List;

import ca.sariarra.poker.card.Card;
import ca.sariarra.poker.game.action.HandAction;
import ca.sariarra.poker.game.component.GameRotation;
import ca.sariarra.poker.game.component.HandDetails;
import ca.sariarra.poker.game.handrank.HandRank;
import ca.sariarra.poker.game.handrank.HandRanking;
import ca.sariarra.poker.table.component.BlindLevel;
import ca.sariarra.poker.table.component.BlindLevels;
import ca.sariarra.poker.table.component.Seat;

public abstract class PokerGame {
	private final GameRotation gameRotation;
	private final BlindLevels blindLevels;
	private final HandAction[] handActions;
	private final HandRanking[] orderedHandRanks;

	protected PokerGame(final GameRotation rotation, final BlindLevels levels, final HandAction[] actions, final HandRanking[] orderedHandRanks) {
		gameRotation = rotation;
		blindLevels = levels;
		handActions = actions;
		this.orderedHandRanks = orderedHandRanks;
	}

	protected PokerGame(final BlindLevels levels, final HandAction[] actions, final HandRanking[] orderedHandRanks) {
		this(null, levels, actions, orderedHandRanks);
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

	public HandRank rankHand(final List<Card> hand) {
		HandRank result = null;
		for (HandRanking rank : orderedHandRanks) {
			result = rank.rankHand(hand);
			if (result != null) {
				break;
			}
		}

		return result;
	}

	public abstract List<Seat> determineWinners(List<Card> communityCards, List<Seat> contenders);
}