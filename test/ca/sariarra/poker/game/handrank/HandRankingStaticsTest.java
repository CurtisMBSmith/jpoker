package ca.sariarra.poker.game.handrank;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import ca.sariarra.poker.card.Card;
import ca.sariarra.poker.game.handrank.HandRanking;

public class HandRankingStaticsTest extends AbstractHandRankTest {

	@Test
	public void testOrderCardsByRank() {
		List<Card> testHand = makeCardsFromStrings("4h", "4d", "Qh", "Kc", "Ks", "6d", "3c");
		List<Card> expected = makeCardsFromStrings("3c", "4h", "4d", "6d", "Qh", "Kc", "Ks");

		HandRanking.sortByRank(testHand);
		for (int i = 0; i < testHand.size(); i++) {
			assertTrue("Card " + testHand.get(i).toString() + " in wrong place (Exp: "
					+ expected.get(i).toString() + ")",
					testHand.get(i).rank() == expected.get(i).rank());
		}

		testHand = makeCardsFromStrings("7h", "4d", "Ah", "Kc", "2s", "6d", "3c");
		expected = makeCardsFromStrings("2s", "3c", "4d", "6d", "7h", "Kc", "Ah");

		HandRanking.sortByRank(testHand);
		for (int i = 0; i < testHand.size(); i++) {
			assertTrue("Card " + testHand.get(i).toString() + " in wrong place (Exp: "
					+ expected.get(i).toString() + ")",
					testHand.get(i).rank() == expected.get(i).rank());
		}
	}
}
