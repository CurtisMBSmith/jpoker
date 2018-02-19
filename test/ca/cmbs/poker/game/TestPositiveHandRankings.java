package ca.cmbs.poker.game;

import ca.cmbs.poker.game.component.card.Card;
import ca.cmbs.poker.game.rank.HandRank;
import org.junit.jupiter.api.Test;

import java.util.List;

import static ca.cmbs.poker.game.rank.HandRanking.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestPositiveHandRankings {

	@Test
	public void testStraightFlushHandRanks() {
		List<Card> testHand;
		HandRank rank;
		HandRank expectedRank;

        testHand = Card.fromString("Ah", "Kh", "Qh", "Jh", "Th", "6d", "3c");
        rank = STRAIGHT_FLUSH.rankHand(testHand);
        assertNotNull(rank, "Failed to find expected straight flush.");

        expectedRank = new HandRank(STRAIGHT_FLUSH, Card.fromString("Th", "Jh", "Qh", "Kh", "Ah"));
        assertTrue(expectedRank.equals(rank), "Hand rank " + rank.toString() + " was not what was expected (" + expectedRank.toString() + ")");

        testHand = Card.fromString("3h", "7d", "8d", "Jh", "Td", "6d", "9d");
        rank = STRAIGHT_FLUSH.rankHand(testHand);
        assertNotNull(rank, "Failed to find expected straight flush.");

        expectedRank = new HandRank(STRAIGHT_FLUSH, Card.fromString("6d", "7d", "8d", "9d", "Td"));
        assertTrue(expectedRank.equals(rank), "Hand rank " + rank.toString() + " was not what was expected (" + expectedRank.toString() + ")");
    }

	@Test
	public void testStraightHandRanks() {
		List<Card> testHand;
		HandRank rank;
		HandRank expectedRank;

        testHand = Card.fromString("Ah", "Kh", "Qh", "Jh", "Th", "6d", "3c");
        rank = STRAIGHT.rankHand(testHand);
        assertNotNull(rank, "Failed to find expected straight.");

        expectedRank = new HandRank(STRAIGHT, Card.fromString("Th", "Jh", "Qh", "Kh", "Ah"));
        assertTrue(expectedRank.equals(rank), "Hand rank " + rank.toString() + " was not what was expected (" + expectedRank.toString() + ")");

        testHand = Card.fromString("3h", "7d", "8d", "Jh", "Td", "6d", "9d");
        rank = STRAIGHT.rankHand(testHand);
        assertNotNull(rank, "Failed to find expected straight.");

        expectedRank = new HandRank(STRAIGHT, Card.fromString("7d", "8d", "9d", "Td", "Jh"));
        assertTrue(expectedRank.equals(rank), "Hand rank " + rank.toString() + " was not what was expected (" + expectedRank.toString() + ")");

        testHand = Card.fromString("Ac", "4d", "2d", "Qd", "Jd", "3h", "5s");
        rank = STRAIGHT.rankHand(testHand);
        assertNotNull(rank, "Failed to find expected straight.");

        expectedRank = new HandRank(STRAIGHT, Card.fromString("Ac", "2d", "3h", "4d", "5s"));
        assertTrue(expectedRank.equals(rank), "Hand rank " + rank.toString() + " was not what was expected (" + expectedRank.toString() + ")");
    }

	@Test
	public void testFlushHandRanks() {
		List<Card> testHand;
		HandRank rank;
		HandRank expectedRank;

        testHand = Card.fromString("Ah", "Kh", "Qh", "Jh", "Th", "6d", "3c");
        rank = FLUSH.rankHand(testHand);
        assertNotNull(rank, "Failed to find expected flush.");

        expectedRank = new HandRank(FLUSH, Card.fromString("Ah", "Kh", "Qh", "Jh", "Th"));
        assertTrue(expectedRank.equals(rank), "Hand rank " + rank.toString() + " was not what was expected (" + expectedRank.toString() + ")");

        testHand = Card.fromString("3h", "7d", "8d", "Jh", "Td", "6d", "9d");
        rank = FLUSH.rankHand(testHand);
        assertNotNull(rank, "Failed to find expected flush.");

        expectedRank = new HandRank(FLUSH, Card.fromString("Td", "9d", "8d", "7d", "6d"));
        assertTrue(expectedRank.equals(rank), "Hand rank " + rank.toString() + " was not what was expected (" + expectedRank.toString() + ")");
    }

	@Test
	public void testFourOfAKindHandRanks() {
		List<Card> testHand;
		HandRank rank;
		HandRank expectedRank;

        testHand = Card.fromString("Kh", "Kd", "Qh", "Kc", "Ks", "6d", "3c");
        rank = FOUR_OF_A_KIND.rankHand(testHand);
        assertNotNull(rank, "Failed to find expected four of a kind.");

        expectedRank = new HandRank(FOUR_OF_A_KIND, Card.fromString("Kh", "Kd", "Kc", "Ks", "Qh"));
        assertTrue(expectedRank.equals(rank), "Hand rank " + rank.toString() + " was not what was expected (" + expectedRank.toString() + ")");

        testHand = Card.fromString("3h", "7d", "7c", "7h", "Td", "7s", "9d");
        rank = FOUR_OF_A_KIND.rankHand(testHand);
        assertNotNull(rank, "Failed to find expected four of a kind.");

        expectedRank = new HandRank(FOUR_OF_A_KIND, Card.fromString("7d", "7c", "7h", "7s", "Td"));
        assertTrue(expectedRank.equals(rank), "Hand rank " + rank.toString() + " was not what was expected (" + expectedRank.toString() + ")");
    }

	@Test
	public void testFullHouseHandRanks() {
		List<Card> testHand;
		HandRank rank;
		HandRank expectedRank;

        testHand = Card.fromString("Kh", "Kd", "Qh", "Kc", "Qs", "6d", "3c");
        rank = FULL_HOUSE.rankHand(testHand);
        assertNotNull(rank, "Failed to find expected full house.");

        expectedRank = new HandRank(FULL_HOUSE, Card.fromString("Kh", "Kd", "Kc", "Qh", "Qs"));
        assertTrue(expectedRank.equals(rank), "Hand rank " + rank.toString() + " was not what was expected (" + expectedRank.toString() + ")");

        testHand = Card.fromString("3h", "7d", "7c", "7h", "Td", "Ts", "9d");
        rank = FULL_HOUSE.rankHand(testHand);
        assertNotNull(rank, "Failed to find expected full house.");

        expectedRank = new HandRank(FULL_HOUSE, Card.fromString("7d", "7c", "7h", "Td", "Ts"));
        assertTrue(expectedRank.equals(rank), "Hand rank " + rank.toString() + " was not what was expected (" + expectedRank.toString() + ")");
    }

	@Test
	public void testThreeOfAKindHandRanks() {
		List<Card> testHand;
		HandRank rank;
		HandRank expectedRank;

        testHand = Card.fromString("Kh", "Kd", "Qh", "Kc", "5s", "6d", "3c");
        rank = THREE_OF_A_KIND.rankHand(testHand);
        assertNotNull(rank, "Failed to find expected three of a kind.");

        expectedRank = new HandRank(THREE_OF_A_KIND, Card.fromString("Kh", "Kd", "Kc", "Qh", "6d"));
        assertTrue(expectedRank.equals(rank), "Hand rank " + rank.toString() + " was not what was expected (" + expectedRank.toString() + ")");

        testHand = Card.fromString("3h", "7d", "7c", "7h", "Td", "2s", "9d");
        rank = THREE_OF_A_KIND.rankHand(testHand);
        assertNotNull(rank, "Failed to find expected three of a kind.");

        expectedRank = new HandRank(THREE_OF_A_KIND, Card.fromString("7d", "7c", "7h", "Td", "9d"));
        assertTrue(expectedRank.equals(rank), "Hand rank " + rank.toString() + " was not what was expected (" + expectedRank.toString() + ")");
    }

	@Test
	public void testTwoPairHandRanks() {
		List<Card> testHand;
		HandRank rank;
		HandRank expectedRank;

        testHand = Card.fromString("4h", "4d", "Qh", "Kc", "Ks", "6d", "3c");
        rank = TWO_PAIR.rankHand(testHand);
        assertNotNull(rank, "Failed to find expected two pair.");

        expectedRank = new HandRank(TWO_PAIR, Card.fromString("Kh", "Kd", "4d", "4h", "Qh"));
        assertTrue(expectedRank.equals(rank), "Hand rank " + rank.toString() + " was not what was expected (" + expectedRank.toString() + ")");

        testHand = Card.fromString("3h", "7d", "7c", "Th", "Td", "Js", "9d");
        rank = TWO_PAIR.rankHand(testHand);
        assertNotNull(rank, "Failed to find expected two pair.");

        expectedRank = new HandRank(TWO_PAIR, Card.fromString("Th", "Td", "7c", "7d", "Js"));
        assertTrue(expectedRank.equals(rank), "Hand rank " + rank.toString() + " was not what was expected (" + expectedRank.toString() + ")");
    }

	@Test
	public void testPairHandRanks() {
		List<Card> testHand;
		HandRank rank;
		HandRank expectedRank;

        testHand = Card.fromString("4h", "4d", "Qh", "Kc", "2s", "6d", "3c");
        rank = PAIR.rankHand(testHand);
        assertNotNull(rank, "Failed to find expected pair.");

        expectedRank = new HandRank(PAIR, Card.fromString("4h", "4d", "Kc", "Qh", "6d"));
        assertTrue(expectedRank.equals(rank), "Hand rank " + rank.toString() + " was not what was expected (" + expectedRank.toString() + ")");

        testHand = Card.fromString("3h", "7d", "7c", "Th", "Ad", "Js", "9d");
        rank = PAIR.rankHand(testHand);
        assertNotNull(rank, "Failed to find expected pair.");

        expectedRank = new HandRank(PAIR, Card.fromString("7d", "7c", "Ad", "Js", "Th"));
        assertTrue(expectedRank.equals(rank), "Hand rank " + rank.toString() + " was not what was expected (" + expectedRank.toString() + ")");

        testHand = Card.fromString("Ac", "Kh", "Td", "9c", "4s", "Ah", "3d");
        rank = PAIR.rankHand(testHand);
        assertNotNull(rank, "Failed to rank hand.");

        expectedRank = new HandRank(PAIR, Card.fromString("Ah", "Ac", "Kh", "Td", "9c"));
        assertTrue(expectedRank.equals(rank), "Hand rank " + rank.toString() + " was not what was expected (" + expectedRank.toString() + ")");
    }

	@Test
	public void testHighCardHandRanks() {
		List<Card> testHand;
		HandRank rank;
		HandRank expectedRank;

        testHand = Card.fromString("4h", "7d", "Qh", "Kc", "2s", "6d", "3c");
        rank = HIGH_CARD.rankHand(testHand);
        assertNotNull(rank, "Failed to find expected pair.");

        expectedRank = new HandRank(HIGH_CARD, Card.fromString("Kc", "Qh", "7d", "6d", "4h"));
        assertTrue(expectedRank.equals(rank), "Hand rank " + rank.toString() + " was not what was expected (" + expectedRank.toString() + ")");

        testHand = Card.fromString("3h", "7d", "4c", "Th", "Ad", "Js", "9d");
        rank = HIGH_CARD.rankHand(testHand);
        assertNotNull(rank, "Failed to find expected pair.");

        expectedRank = new HandRank(HIGH_CARD, Card.fromString("Ad", "Js", "Th", "9d", "7d"));
        assertTrue(expectedRank.equals(rank), "Hand rank " + rank.toString() + " was not what was expected (" + expectedRank.toString() + ")");
    }
}
