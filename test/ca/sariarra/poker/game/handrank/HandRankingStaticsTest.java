package ca.sariarra.poker.game.handrank;

import ca.sariarra.poker.card.Card;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class HandRankingStaticsTest {

	@Test
	public void testOrderCardsByRank() {
        List<Card> testHand = Card.fromString("4h", "4d", "Qh", "Kc", "Ks", "6d", "3c");
        List<Card> expected = Card.fromString("3c", "4h", "4d", "6d", "Qh", "Kc", "Ks");

		HandRanking.sortByRank(testHand);
		for (int i = 0; i < testHand.size(); i++) {
			assertTrue("Card " + testHand.get(i).toString() + " in wrong place (Exp: "
					+ expected.get(i).toString() + ")",
					testHand.get(i).rank() == expected.get(i).rank());
		}

        testHand = Card.fromString("7h", "4d", "Ah", "Kc", "2s", "6d", "3c");
        expected = Card.fromString("2s", "3c", "4d", "6d", "7h", "Kc", "Ah");

		HandRanking.sortByRank(testHand);
		for (int i = 0; i < testHand.size(); i++) {
			assertTrue("Card " + testHand.get(i).toString() + " in wrong place (Exp: "
					+ expected.get(i).toString() + ")",
					testHand.get(i).rank() == expected.get(i).rank());
		}
	}
}
