package ca.sariarra.poker.card;

import static ca.sariarra.poker.card.Rank.ACE;
import static ca.sariarra.poker.card.Rank.EIGHT;
import static ca.sariarra.poker.card.Rank.FIVE;
import static ca.sariarra.poker.card.Rank.FOUR;
import static ca.sariarra.poker.card.Rank.JACK;
import static ca.sariarra.poker.card.Rank.KING;
import static ca.sariarra.poker.card.Rank.NINE;
import static ca.sariarra.poker.card.Rank.QUEEN;
import static ca.sariarra.poker.card.Rank.SEVEN;
import static ca.sariarra.poker.card.Rank.SIX;
import static ca.sariarra.poker.card.Rank.TEN;
import static ca.sariarra.poker.card.Rank.THREE;
import static ca.sariarra.poker.card.Rank.TWO;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import ca.sariarra.poker.card.Rank;

public class TestRank {

	@Test
	public void TestRankOrdering() {
		Rank[] expectedOrdering = new Rank[] {TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING, ACE};

		for (int i = 1; i < expectedOrdering.length; i++) {
			assertTrue(expectedOrdering[i - 1] + " is greater than " + expectedOrdering[i], expectedOrdering[i - 1].compareTo(expectedOrdering[i]) < 0);
		}
	}
}
