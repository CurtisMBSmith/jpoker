package ca.cmbs.poker.game;

import ca.cmbs.poker.game.component.card.Card;
import ca.cmbs.poker.game.rank.HandRank;
import ca.cmbs.poker.game.rank.HandRanking;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static ca.cmbs.poker.game.rank.HandRanking.PAIR;
import static ca.cmbs.poker.game.rank.HandRanking.STRAIGHT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestHoldEmRankOrdering {

    private Game holdEm;

    @BeforeEach
    public void setUp() {
        holdEm = Game.noLimitTexasHoldem();
    }

	@Test
	public void testPair() {
		HandRank rank;
		HandRank expectedRank;
        List<Card> cards = Card.fromString("Ac", "Kh", "Td", "9c", "4s", "Ah", "3d");
        rank = holdEm.rankHand(cards);
        assertNotNull(rank, "Failed to rank hand.");

        expectedRank = new HandRank(PAIR, Card.fromString("Ah", "Ac", "Kh", "Td", "9c"));
        assertTrue(expectedRank.equals(rank), "Hand rank " + rank.toString() + " was not what was expected (" + expectedRank.toString() + ")");

        cards = Card.fromString("8d", "4c", "Qs", "Th", "7s", "6c", "Td");
        rank = holdEm.rankHand(cards);
        assertNotNull(rank, "Failed to rank hand.");

        expectedRank = new HandRank(PAIR, Card.fromString("Td", "Th", "Qs", "8d", "7s"));
        assertTrue(expectedRank.equals(rank), "Hand rank " + rank.toString() + " was not what was expected (" + expectedRank.toString() + ")");
    }

    @Test
    public void liveHand1() {
        List<Card> communityCards = Card.fromString("Ad", "5s", "Kd", "Qd", "7h");
        runTest(Card.fromString("3c", "5c"), communityCards, new HandRank(HandRanking.PAIR, Card.fromString("5s", "5c", "Ad", "Kd", "Qd")));
    }

    @Test
    public void liveHand2() {
        List<Card> communityCards = Card.fromString("Ad", "5s", "Kd", "Qd", "7h");
        runTest(Card.fromString("Kh", "9c"), communityCards, new HandRank(HandRanking.PAIR, Card.fromString("Kh", "Kd", "Ad", "Qd", "9c")));
    }

    @Test
    public void liveHand3() {
        List<Card> communityCards = Card.fromString("Ad", "5s", "Kd", "Qd", "7h");
        runTest(Card.fromString("4d", "9h"), communityCards, new HandRank(HandRanking.HIGH_CARD, Card.fromString("Ad", "Kd", "Qd", "9h", "7h")));
    }

	@Test
	public void testRankOrder() {
        List<Card> communityCards = Card.fromString("5s", "6d", "Qs", "2d", "8d");
        HandRank rank1 = runTest(Card.fromString("Kd", "6h"), communityCards, new HandRank(PAIR, Card.fromString("6h", "6d", "Kd", "Qs", "8d")));
        HandRank rank2 = runTest(Card.fromString("7d", "4c"), communityCards, new HandRank(STRAIGHT, Card.fromString("4c", "5s", "6d", "7d", "8d")));

        assertTrue(rank2.compareTo(rank1) > 0,
                rank2.toString() + " is not greater than " + rank1.toString() + ".");
    }

    private HandRank runTest(final List<Card> holeCards, final List<Card> communityCards, final HandRank expRank) {
        HandRank rank = holdEm.rankHand(holeCards, communityCards);
        assertNotNull(rank);
        assertEquals(expRank, rank);

        return rank;
    }
}
