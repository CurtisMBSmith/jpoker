package ca.sariarra.poker.game.handrank;

import static ca.sariarra.poker.game.handrank.HandRanking.PAIR;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ca.sariarra.poker.card.Card;
import ca.sariarra.poker.game.NoLimitTexasHoldEm;
import ca.sariarra.poker.game.PokerGame;

public class TestHoldEmRankOrdering extends AbstractHandRankTest {

	private PokerGame holdEm;

	@Before
	public void setUp() {
		holdEm = new NoLimitTexasHoldEm(null);
	}

	@Test
	public void testPair() {
		HandRank rank;
		HandRank expectedRank;
		List<Card> cards = makeCardsFromStrings("Ac", "Kh", "Td", "9c", "4s", "Ah", "3d");
		rank = holdEm.rankHand(cards);
		assertNotNull("Failed to rank hand.", rank);

		expectedRank = new HandRank(PAIR, makeCardsFromStrings("9c", "Td", "Kh", "Ac", "Ah"));
		assertTrue("Hand rank " + rank.toString() + " was not what was expected (" + expectedRank.toString() + ")", expectedRank.equals(rank));

		cards = makeCardsFromStrings("8d", "4c", "Qs", "Th", "7s", "6c", "Td");
		rank = holdEm.rankHand(cards);
		assertNotNull("Failed to rank hand.", rank);

		expectedRank = new HandRank(PAIR, makeCardsFromStrings("7s", "8d", "Qs", "Th", "Td"));
		assertTrue("Hand rank " + rank.toString() + " was not what was expected (" + expectedRank.toString() + ")", expectedRank.equals(rank));
	}
}
