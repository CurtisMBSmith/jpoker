package ca.cmbs.poker.game.rank;

import ca.cmbs.poker.game.component.card.Card;

import java.util.List;

public interface Rankable {

	/**
	 * Returns a HandRank iff the cards parameter make this rank.
	 *
	 * @param cards The candidate cards to make the hand.
	 * @return A HandRank with the HandRanking that implemented this interface,
	 *  or null if the cards do not make that HandRanking.
	 */
    HandRank rankHand(List<Card> cards);
}
