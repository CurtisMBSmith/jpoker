package ca.sariarra.poker.game;

import java.util.ArrayList;
import java.util.List;

import ca.sariarra.poker.card.Card;
import ca.sariarra.poker.game.action.HandAction;
import ca.sariarra.poker.game.component.GameRotation;
import ca.sariarra.poker.game.component.HandDetails;
import ca.sariarra.poker.game.handrank.HandRank;
import ca.sariarra.poker.game.handrank.HandRanking;
import ca.sariarra.poker.table.component.BlindLevel;
import ca.sariarra.poker.table.component.BlindLevelManager;
import ca.sariarra.poker.table.component.Seat;

public abstract class PokerGame {
	private final GameRotation gameRotation;
	private final BlindLevelManager blindLevels;
	private final HandAction[] handActions;
	private final HandRanking[] orderedHandRanks;

	protected PokerGame(final GameRotation rotation, final BlindLevelManager levels, final HandAction[] actions, final HandRanking[] orderedHandRanks) {
		gameRotation = rotation;
		blindLevels = levels;
		handActions = actions;
		this.orderedHandRanks = orderedHandRanks;
	}

	protected PokerGame(final BlindLevelManager levels, final HandAction[] actions, final HandRanking[] orderedHandRanks) {
		this(null, levels, actions, orderedHandRanks);
	}

	public PokerGame getGameByElapsedTime(final long elapsedGameTime) {
		if (gameRotation == null) {
			return this;
		}

		return gameRotation.getGame(elapsedGameTime);
	}

	public BlindLevel getBlindLevelByElapsedTime(final long elapsedGameTime) {
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
		List<Card> handCopy;
		for (HandRanking rank : orderedHandRanks) {
			handCopy = copyHand(hand);

			result = rank.rankHand(handCopy);
			if (result != null) {
				break;
			}
		}

		return result;
	}

	private List<Card> copyHand(final List<Card> hand) {
		List<Card> handCopy = new ArrayList<Card>(hand.size());
		for (Card card : hand) {
			handCopy.add(card);
		}

		return handCopy;
	}

	public abstract List<Seat> determineWinners(List<Card> communityCards, List<Seat> contenders);
}
