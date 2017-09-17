package ca.sariarra.poker.game.handrank;

import ca.sariarra.poker.card.Card;
import ca.sariarra.poker.game.NoLimitTexasHoldEm;
import ca.sariarra.poker.game.PokerGame;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static ca.sariarra.poker.game.handrank.HandRanking.PAIR;
import static ca.sariarra.poker.game.handrank.HandRanking.STRAIGHT;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class TestHoldEmRankOrdering {

	private PokerGame holdEm;

	@Before
	public void setUp() {
		holdEm = new NoLimitTexasHoldEm(null);
	}

	@Test
	public void testPair() {
		HandRank rank;
		HandRank expectedRank;
        List<Card> cards = Card.fromString("Ac", "Kh", "Td", "9c", "4s", "Ah", "3d");
        rank = holdEm.rankHand(cards);
        assertNotNull("Failed to rank hand.", rank);

        expectedRank = new HandRank(PAIR, Card.fromString("Ah", "Ac", "Kh", "Td", "9c"));
        assertTrue("Hand rank " + rank.toString() + " was not what was expected (" + expectedRank.toString() + ")", expectedRank.equals(rank));

        cards = Card.fromString("8d", "4c", "Qs", "Th", "7s", "6c", "Td");
        rank = holdEm.rankHand(cards);
        assertNotNull("Failed to rank hand.", rank);

        expectedRank = new HandRank(PAIR, Card.fromString("Td", "Th", "Qs", "8d", "7s"));
        assertTrue("Hand rank " + rank.toString() + " was not what was expected (" + expectedRank.toString() + ")", expectedRank.equals(rank));
    }

	@Test
	public void testRankOrder() {
		HandRank rank1;
		HandRank expectedRank1;
		List<Card> hand1;

		HandRank rank2;
		HandRank expectedRank2;
		List<Card> hand2;

        hand1 = Card.fromString("5s", "6d", "Qs", "2d", "8d", "Kd", "6h");
        rank1 = holdEm.rankHand(hand1);
        assertNotNull("Failed to rank hand.", rank1);

        expectedRank1 = new HandRank(PAIR, Card.fromString("6h", "6d", "Kd", "Qs", "8d"));
        assertTrue("Hand rank " + rank1.toString() + " was not what was expected (" + expectedRank1.toString() + ")", expectedRank1.equals(rank1));

        hand2 = Card.fromString("5s", "6d", "Qs", "2d", "8d", "7d", "4c");
        rank2 = holdEm.rankHand(hand2);
        assertNotNull("Failed to rank hand.", rank2);

        expectedRank2 = new HandRank(STRAIGHT, Card.fromString("4c", "5s", "6d", "7d", "8d"));
        assertTrue("Hand rank " + rank2.toString() + " was not what was expected (" + expectedRank2.toString() + ")", expectedRank2.equals(rank2));

		assertTrue(expectedRank2.toString() + " is not greater than " + expectedRank1.toString() + ".",
				expectedRank2.compareTo(expectedRank1) > 0);
	}
}
