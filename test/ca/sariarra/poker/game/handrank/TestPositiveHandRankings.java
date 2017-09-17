package ca.sariarra.poker.game.handrank;

import ca.sariarra.poker.card.Card;
import org.junit.Test;

import java.util.List;

import static ca.sariarra.poker.game.handrank.HandRanking.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class TestPositiveHandRankings {

	@Test
	public void testStraightFlushHandRanks() {
		List<Card> testHand;
		HandRank rank;
		HandRank expectedRank;

        testHand = Card.fromString("Ah", "Kh", "Qh", "Jh", "Th", "6d", "3c");
        rank = STRAIGHT_FLUSH.rankHand(testHand);
        assertNotNull("Failed to find expected straight flush.", rank);

        expectedRank = new HandRank(STRAIGHT_FLUSH, Card.fromString("Th", "Jh", "Qh", "Kh", "Ah"));
        assertTrue("Hand rank " + rank.toString() + " was not what was expected (" + expectedRank.toString() + ")", expectedRank.equals(rank));

        testHand = Card.fromString("3h", "7d", "8d", "Jh", "Td", "6d", "9d");
        rank = STRAIGHT_FLUSH.rankHand(testHand);
        assertNotNull("Failed to find expected straight flush.", rank);

        expectedRank = new HandRank(STRAIGHT_FLUSH, Card.fromString("6d", "7d", "8d", "9d", "Td"));
        assertTrue("Hand rank " + rank.toString() + " was not what was expected (" + expectedRank.toString() + ")", expectedRank.equals(rank));
    }

	@Test
	public void testStraightHandRanks() {
		List<Card> testHand;
		HandRank rank;
		HandRank expectedRank;

        testHand = Card.fromString("Ah", "Kh", "Qh", "Jh", "Th", "6d", "3c");
        rank = STRAIGHT.rankHand(testHand);
        assertNotNull("Failed to find expected straight.", rank);

        expectedRank = new HandRank(STRAIGHT, Card.fromString("Th", "Jh", "Qh", "Kh", "Ah"));
        assertTrue("Hand rank " + rank.toString() + " was not what was expected (" + expectedRank.toString() + ")", expectedRank.equals(rank));

        testHand = Card.fromString("3h", "7d", "8d", "Jh", "Td", "6d", "9d");
        rank = STRAIGHT.rankHand(testHand);
        assertNotNull("Failed to find expected straight.", rank);

        expectedRank = new HandRank(STRAIGHT, Card.fromString("7d", "8d", "9d", "Td", "Jh"));
        assertTrue("Hand rank " + rank.toString() + " was not what was expected (" + expectedRank.toString() + ")", expectedRank.equals(rank));

        testHand = Card.fromString("Ac", "4d", "2d", "Qd", "Jd", "3h", "5s");
        rank = STRAIGHT.rankHand(testHand);
        assertNotNull("Failed to find expected straight.", rank);

        expectedRank = new HandRank(STRAIGHT, Card.fromString("Ac", "2d", "3h", "4d", "5s"));
        assertTrue("Hand rank " + rank.toString() + " was not what was expected (" + expectedRank.toString() + ")", expectedRank.equals(rank));
    }

	@Test
	public void testFlushHandRanks() {
		List<Card> testHand;
		HandRank rank;
		HandRank expectedRank;

        testHand = Card.fromString("Ah", "Kh", "Qh", "Jh", "Th", "6d", "3c");
        rank = FLUSH.rankHand(testHand);
        assertNotNull("Failed to find expected flush.", rank);

        expectedRank = new HandRank(FLUSH, Card.fromString("Th", "Jh", "Qh", "Kh", "Ah"));
        assertTrue("Hand rank " + rank.toString() + " was not what was expected (" + expectedRank.toString() + ")", expectedRank.equals(rank));

        testHand = Card.fromString("3h", "7d", "8d", "Jh", "Td", "6d", "9d");
        rank = FLUSH.rankHand(testHand);
        assertNotNull("Failed to find expected flush.", rank);

        expectedRank = new HandRank(FLUSH, Card.fromString("6d", "7d", "8d", "9d", "Td"));
        assertTrue("Hand rank " + rank.toString() + " was not what was expected (" + expectedRank.toString() + ")", expectedRank.equals(rank));
    }

	@Test
	public void testFourOfAKindHandRanks() {
		List<Card> testHand;
		HandRank rank;
		HandRank expectedRank;

        testHand = Card.fromString("Kh", "Kd", "Qh", "Kc", "Ks", "6d", "3c");
        rank = FOUR_OF_A_KIND.rankHand(testHand);
        assertNotNull("Failed to find expected four of a kind.", rank);

        expectedRank = new HandRank(FOUR_OF_A_KIND, Card.fromString("Kh", "Kd", "Kc", "Ks", "Qh"));
        assertTrue("Hand rank " + rank.toString() + " was not what was expected (" + expectedRank.toString() + ")", expectedRank.equals(rank));

        testHand = Card.fromString("3h", "7d", "7c", "7h", "Td", "7s", "9d");
        rank = FOUR_OF_A_KIND.rankHand(testHand);
        assertNotNull("Failed to find expected four of a kind.", rank);

        expectedRank = new HandRank(FOUR_OF_A_KIND, Card.fromString("7d", "7c", "7h", "7s", "Td"));
        assertTrue("Hand rank " + rank.toString() + " was not what was expected (" + expectedRank.toString() + ")", expectedRank.equals(rank));
    }

	@Test
	public void testFullHouseHandRanks() {
		List<Card> testHand;
		HandRank rank;
		HandRank expectedRank;

        testHand = Card.fromString("Kh", "Kd", "Qh", "Kc", "Qs", "6d", "3c");
        rank = FULL_HOUSE.rankHand(testHand);
        assertNotNull("Failed to find expected full house.", rank);

        expectedRank = new HandRank(FULL_HOUSE, Card.fromString("Kh", "Kd", "Kc", "Qh", "Qs"));
        assertTrue("Hand rank " + rank.toString() + " was not what was expected (" + expectedRank.toString() + ")", expectedRank.equals(rank));

        testHand = Card.fromString("3h", "7d", "7c", "7h", "Td", "Ts", "9d");
        rank = FULL_HOUSE.rankHand(testHand);
        assertNotNull("Failed to find expected full house.", rank);

        expectedRank = new HandRank(FULL_HOUSE, Card.fromString("7d", "7c", "7h", "Td", "Ts"));
        assertTrue("Hand rank " + rank.toString() + " was not what was expected (" + expectedRank.toString() + ")", expectedRank.equals(rank));
    }

	@Test
	public void testThreeOfAKindHandRanks() {
		List<Card> testHand;
		HandRank rank;
		HandRank expectedRank;

        testHand = Card.fromString("Kh", "Kd", "Qh", "Kc", "Ks", "6d", "3c");
        rank = THREE_OF_A_KIND.rankHand(testHand);
        assertNotNull("Failed to find expected three of a kind.", rank);

        expectedRank = new HandRank(THREE_OF_A_KIND, Card.fromString("Kh", "Kd", "Kc", "Qh", "6d"));
        assertTrue("Hand rank " + rank.toString() + " was not what was expected (" + expectedRank.toString() + ")", expectedRank.equals(rank));

        testHand = Card.fromString("3h", "7d", "7c", "7h", "Td", "7s", "9d");
        rank = THREE_OF_A_KIND.rankHand(testHand);
        assertNotNull("Failed to find expected three of a kind.", rank);

        expectedRank = new HandRank(THREE_OF_A_KIND, Card.fromString("7d", "7c", "7h", "Td", "9d"));
        assertTrue("Hand rank " + rank.toString() + " was not what was expected (" + expectedRank.toString() + ")", expectedRank.equals(rank));
    }

	@Test
	public void testTwoPairHandRanks() {
		List<Card> testHand;
		HandRank rank;
		HandRank expectedRank;

        testHand = Card.fromString("4h", "4d", "Qh", "Kc", "Ks", "6d", "3c");
        rank = TWO_PAIR.rankHand(testHand);
        assertNotNull("Failed to find expected two pair.", rank);

        expectedRank = new HandRank(TWO_PAIR, Card.fromString("Kh", "Kd", "4d", "4h", "Qh"));
        assertTrue("Hand rank " + rank.toString() + " was not what was expected (" + expectedRank.toString() + ")", expectedRank.equals(rank));

        testHand = Card.fromString("3h", "7d", "7c", "Th", "Td", "Js", "9d");
        rank = TWO_PAIR.rankHand(testHand);
        assertNotNull("Failed to find expected two pair.", rank);

        expectedRank = new HandRank(TWO_PAIR, Card.fromString("Th", "Td", "7c", "7d", "Js"));
        assertTrue("Hand rank " + rank.toString() + " was not what was expected (" + expectedRank.toString() + ")", expectedRank.equals(rank));
    }

	@Test
	public void testPairHandRanks() {
		List<Card> testHand;
		HandRank rank;
		HandRank expectedRank;

        testHand = Card.fromString("4h", "4d", "Qh", "Kc", "2s", "6d", "3c");
        rank = PAIR.rankHand(testHand);
        assertNotNull("Failed to find expected pair.", rank);

        expectedRank = new HandRank(PAIR, Card.fromString("4h", "4d", "Kc", "Qh", "6d"));
        assertTrue("Hand rank " + rank.toString() + " was not what was expected (" + expectedRank.toString() + ")", expectedRank.equals(rank));

        testHand = Card.fromString("3h", "7d", "7c", "Th", "Ad", "Js", "9d");
        rank = PAIR.rankHand(testHand);
        assertNotNull("Failed to find expected pair.", rank);

        expectedRank = new HandRank(PAIR, Card.fromString("7d", "7c", "Ad", "Js", "Th"));
        assertTrue("Hand rank " + rank.toString() + " was not what was expected (" + expectedRank.toString() + ")", expectedRank.equals(rank));

        testHand = Card.fromString("Ac", "Kh", "Td", "9c", "4s", "Ah", "3d");
        rank = PAIR.rankHand(testHand);
        assertNotNull("Failed to rank hand.", rank);

        expectedRank = new HandRank(PAIR, Card.fromString("Ah", "Ac", "Kh", "Td", "9c"));
        assertTrue("Hand rank " + rank.toString() + " was not what was expected (" + expectedRank.toString() + ")", expectedRank.equals(rank));
    }

	@Test
	public void testHighCardHandRanks() {
		List<Card> testHand;
		HandRank rank;
		HandRank expectedRank;

        testHand = Card.fromString("4h", "7d", "Qh", "Kc", "2s", "6d", "3c");
        rank = HIGH_CARD.rankHand(testHand);
        assertNotNull("Failed to find expected pair.", rank);

        expectedRank = new HandRank(HIGH_CARD, Card.fromString("4h", "6d", "7d", "Qh", "Kc"));
        assertTrue("Hand rank " + rank.toString() + " was not what was expected (" + expectedRank.toString() + ")", expectedRank.equals(rank));

        testHand = Card.fromString("3h", "7d", "4c", "Th", "Ad", "Js", "9d");
        rank = HIGH_CARD.rankHand(testHand);
        assertNotNull("Failed to find expected pair.", rank);

        expectedRank = new HandRank(HIGH_CARD, Card.fromString("7d", "9d", "Th", "Js", "Ad"));
        assertTrue("Hand rank " + rank.toString() + " was not what was expected (" + expectedRank.toString() + ")", expectedRank.equals(rank));
    }
}
