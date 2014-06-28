package ca.sariarra.poker.game.handrank;

import static ca.sariarra.poker.game.handrank.HandRanking.FLUSH;
import static ca.sariarra.poker.game.handrank.HandRanking.FOUR_OF_A_KIND;
import static ca.sariarra.poker.game.handrank.HandRanking.FULL_HOUSE;
import static ca.sariarra.poker.game.handrank.HandRanking.HIGH_CARD;
import static ca.sariarra.poker.game.handrank.HandRanking.PAIR;
import static ca.sariarra.poker.game.handrank.HandRanking.STRAIGHT;
import static ca.sariarra.poker.game.handrank.HandRanking.STRAIGHT_FLUSH;
import static ca.sariarra.poker.game.handrank.HandRanking.THREE_OF_A_KIND;
import static ca.sariarra.poker.game.handrank.HandRanking.TWO_PAIR;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import ca.sariarra.poker.card.Card;
import ca.sariarra.poker.game.handrank.HandRank;

public class TestPositiveHandRankings extends AbstractHandRankTest {

	@Test
	public void testStraightFlushHandRanks() {
		List<Card> testHand;
		HandRank rank;
		HandRank expectedRank;

		testHand = makeCardsFromStrings("Ah", "Kh", "Qh", "Jh", "Th", "6d", "3c");
		rank = STRAIGHT_FLUSH.rankHand(testHand);
		assertNotNull("Failed to find expected straight flush.", rank);

		expectedRank = new HandRank(STRAIGHT_FLUSH, makeCardsFromStrings("Th", "Jh", "Qh", "Kh", "Ah"));
		assertTrue("Hand rank " + rank.toString() + " was not what was expected (" + expectedRank.toString() + ")", expectedRank.equals(rank));

		testHand = makeCardsFromStrings("3h", "7d", "8d", "Jh", "Td", "6d", "9d");
		rank = STRAIGHT_FLUSH.rankHand(testHand);
		assertNotNull("Failed to find expected straight flush.", rank);

		expectedRank = new HandRank(STRAIGHT_FLUSH, makeCardsFromStrings("6d", "7d", "8d", "9d", "Td"));
		assertTrue("Hand rank " + rank.toString() + " was not what was expected (" + expectedRank.toString() + ")", expectedRank.equals(rank));
	}

	@Test
	public void testStraightHandRanks() {
		List<Card> testHand;
		HandRank rank;
		HandRank expectedRank;

		testHand = makeCardsFromStrings("Ah", "Kh", "Qh", "Jh", "Th", "6d", "3c");
		rank = STRAIGHT.rankHand(testHand);
		assertNotNull("Failed to find expected straight.", rank);

		expectedRank = new HandRank(STRAIGHT, makeCardsFromStrings("Th", "Jh", "Qh", "Kh", "Ah"));
		assertTrue("Hand rank " + rank.toString() + " was not what was expected (" + expectedRank.toString() + ")", expectedRank.equals(rank));

		testHand = makeCardsFromStrings("3h", "7d", "8d", "Jh", "Td", "6d", "9d");
		rank = STRAIGHT.rankHand(testHand);
		assertNotNull("Failed to find expected straight.", rank);

		expectedRank = new HandRank(STRAIGHT, makeCardsFromStrings("7d", "8d", "9d", "Td", "Jh"));
		assertTrue("Hand rank " + rank.toString() + " was not what was expected (" + expectedRank.toString() + ")", expectedRank.equals(rank));
	}

	@Test
	public void testFlushHandRanks() {
		List<Card> testHand;
		HandRank rank;
		HandRank expectedRank;

		testHand = makeCardsFromStrings("Ah", "Kh", "Qh", "Jh", "Th", "6d", "3c");
		rank = FLUSH.rankHand(testHand);
		assertNotNull("Failed to find expected flush.", rank);

		expectedRank = new HandRank(FLUSH, makeCardsFromStrings("Th", "Jh", "Qh", "Kh", "Ah"));
		assertTrue("Hand rank " + rank.toString() + " was not what was expected (" + expectedRank.toString() + ")", expectedRank.equals(rank));

		testHand = makeCardsFromStrings("3h", "7d", "8d", "Jh", "Td", "6d", "9d");
		rank = FLUSH.rankHand(testHand);
		assertNotNull("Failed to find expected flush.", rank);

		expectedRank = new HandRank(FLUSH, makeCardsFromStrings("6d", "7d", "8d", "9d", "Td"));
		assertTrue("Hand rank " + rank.toString() + " was not what was expected (" + expectedRank.toString() + ")", expectedRank.equals(rank));
	}

	@Test
	public void testFourOfAKindHandRanks() {
		List<Card> testHand;
		HandRank rank;
		HandRank expectedRank;

		testHand = makeCardsFromStrings("Kh", "Kd", "Qh", "Kc", "Ks", "6d", "3c");
		rank = FOUR_OF_A_KIND.rankHand(testHand);
		assertNotNull("Failed to find expected four of a kind.", rank);

		expectedRank = new HandRank(FOUR_OF_A_KIND, makeCardsFromStrings("Kh", "Kd", "Kc", "Ks", "Qh"));
		assertTrue("Hand rank " + rank.toString() + " was not what was expected (" + expectedRank.toString() + ")", expectedRank.equals(rank));

		testHand = makeCardsFromStrings("3h", "7d", "7c", "7h", "Td", "7s", "9d");
		rank = FOUR_OF_A_KIND.rankHand(testHand);
		assertNotNull("Failed to find expected four of a kind.", rank);

		expectedRank = new HandRank(FOUR_OF_A_KIND, makeCardsFromStrings("7d", "7c", "7h", "7s", "Td"));
		assertTrue("Hand rank " + rank.toString() + " was not what was expected (" + expectedRank.toString() + ")", expectedRank.equals(rank));
	}

	@Test
	public void testFullHouseHandRanks() {
		List<Card> testHand;
		HandRank rank;
		HandRank expectedRank;

		testHand = makeCardsFromStrings("Kh", "Kd", "Qh", "Kc", "Qs", "6d", "3c");
		rank = FULL_HOUSE.rankHand(testHand);
		assertNotNull("Failed to find expected full house.", rank);

		expectedRank = new HandRank(FULL_HOUSE, makeCardsFromStrings("Kh", "Kd", "Kc", "Qh", "Qs"));
		assertTrue("Hand rank " + rank.toString() + " was not what was expected (" + expectedRank.toString() + ")", expectedRank.equals(rank));

		testHand = makeCardsFromStrings("3h", "7d", "7c", "7h", "Td", "Ts", "9d");
		rank = FULL_HOUSE.rankHand(testHand);
		assertNotNull("Failed to find expected full house.", rank);

		expectedRank = new HandRank(FULL_HOUSE, makeCardsFromStrings("7d", "7c", "7h", "Td", "Ts"));
		assertTrue("Hand rank " + rank.toString() + " was not what was expected (" + expectedRank.toString() + ")", expectedRank.equals(rank));
	}

	@Test
	public void testThreeOfAKindHandRanks() {
		List<Card> testHand;
		HandRank rank;
		HandRank expectedRank;

		testHand = makeCardsFromStrings("Kh", "Kd", "Qh", "Kc", "Ks", "6d", "3c");
		rank = THREE_OF_A_KIND.rankHand(testHand);
		assertNotNull("Failed to find expected three of a kind.", rank);

		expectedRank = new HandRank(THREE_OF_A_KIND, makeCardsFromStrings("Kh", "Kd", "Kc", "Qh", "6d"));
		assertTrue("Hand rank " + rank.toString() + " was not what was expected (" + expectedRank.toString() + ")", expectedRank.equals(rank));

		testHand = makeCardsFromStrings("3h", "7d", "7c", "7h", "Td", "7s", "9d");
		rank = THREE_OF_A_KIND.rankHand(testHand);
		assertNotNull("Failed to find expected three of a kind.", rank);

		expectedRank = new HandRank(THREE_OF_A_KIND, makeCardsFromStrings("7d", "7c", "7h", "Td", "9d"));
		assertTrue("Hand rank " + rank.toString() + " was not what was expected (" + expectedRank.toString() + ")", expectedRank.equals(rank));
	}

	@Test
	public void testTwoPairHandRanks() {
		List<Card> testHand;
		HandRank rank;
		HandRank expectedRank;

		testHand = makeCardsFromStrings("4h", "4d", "Qh", "Kc", "Ks", "6d", "3c");
		rank = TWO_PAIR.rankHand(testHand);
		assertNotNull("Failed to find expected two pair.", rank);

		expectedRank = new HandRank(TWO_PAIR, makeCardsFromStrings("Kh", "Kd", "4d", "4h", "Qh"));
		assertTrue("Hand rank " + rank.toString() + " was not what was expected (" + expectedRank.toString() + ")", expectedRank.equals(rank));

		testHand = makeCardsFromStrings("3h", "7d", "7c", "Th", "Td", "Js", "9d");
		rank = TWO_PAIR.rankHand(testHand);
		assertNotNull("Failed to find expected two pair.", rank);

		expectedRank = new HandRank(TWO_PAIR, makeCardsFromStrings("Th", "Td", "7c", "7d", "Js"));
		assertTrue("Hand rank " + rank.toString() + " was not what was expected (" + expectedRank.toString() + ")", expectedRank.equals(rank));
	}

	@Test
	public void testPairHandRanks() {
		List<Card> testHand;
		HandRank rank;
		HandRank expectedRank;

		testHand = makeCardsFromStrings("4h", "4d", "Qh", "Kc", "2s", "6d", "3c");
		rank = PAIR.rankHand(testHand);
		assertNotNull("Failed to find expected pair.", rank);

		expectedRank = new HandRank(PAIR, makeCardsFromStrings("4h", "4d", "Kc", "Qh", "6d"));
		assertTrue("Hand rank " + rank.toString() + " was not what was expected (" + expectedRank.toString() + ")", expectedRank.equals(rank));

		testHand = makeCardsFromStrings("3h", "7d", "7c", "Th", "Ad", "Js", "9d");
		rank = PAIR.rankHand(testHand);
		assertNotNull("Failed to find expected pair.", rank);

		expectedRank = new HandRank(PAIR, makeCardsFromStrings("7d", "7c", "Ad", "Js", "Th"));
		assertTrue("Hand rank " + rank.toString() + " was not what was expected (" + expectedRank.toString() + ")", expectedRank.equals(rank));
	}

	@Test
	public void testHighCardHandRanks() {
		List<Card> testHand;
		HandRank rank;
		HandRank expectedRank;

		testHand = makeCardsFromStrings("4h", "7d", "Qh", "Kc", "2s", "6d", "3c");
		rank = HIGH_CARD.rankHand(testHand);
		assertNotNull("Failed to find expected pair.", rank);

		expectedRank = new HandRank(HIGH_CARD, makeCardsFromStrings("4h", "6d", "7d", "Qh", "Kc"));
		assertTrue("Hand rank " + rank.toString() + " was not what was expected (" + expectedRank.toString() + ")", expectedRank.equals(rank));

		testHand = makeCardsFromStrings("3h", "7d", "4c", "Th", "Ad", "Js", "9d");
		rank = HIGH_CARD.rankHand(testHand);
		assertNotNull("Failed to find expected pair.", rank);

		expectedRank = new HandRank(HIGH_CARD, makeCardsFromStrings("7d", "9d", "Th", "Js", "Ad"));
		assertTrue("Hand rank " + rank.toString() + " was not what was expected (" + expectedRank.toString() + ")", expectedRank.equals(rank));
	}
}
