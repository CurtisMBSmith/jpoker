package ca.sariarra.poker.logic;

import java.util.List;

import ca.sariarra.poker.datastruct.Card;
import ca.sariarra.poker.datastruct.handrank.HandRank;

public interface Rankable {

	/**
	 * Returns a HandRank iff the cards parameter make this rank.
	 *
	 * @param cards The candidate cards to make the hand.
	 * @return A HandRank with the HandRanking that implemented this interface,
	 *  or null if the cards do not make that HandRanking.
	 */
	public HandRank rankHand(List<Card> cards);
}
