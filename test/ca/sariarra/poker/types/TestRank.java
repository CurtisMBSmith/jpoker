package ca.sariarra.poker.types;

import static ca.sariarra.poker.types.Rank.ACE;
import static ca.sariarra.poker.types.Rank.EIGHT;
import static ca.sariarra.poker.types.Rank.FIVE;
import static ca.sariarra.poker.types.Rank.FOUR;
import static ca.sariarra.poker.types.Rank.JACK;
import static ca.sariarra.poker.types.Rank.KING;
import static ca.sariarra.poker.types.Rank.NINE;
import static ca.sariarra.poker.types.Rank.QUEEN;
import static ca.sariarra.poker.types.Rank.SEVEN;
import static ca.sariarra.poker.types.Rank.SIX;
import static ca.sariarra.poker.types.Rank.TEN;
import static ca.sariarra.poker.types.Rank.THREE;
import static ca.sariarra.poker.types.Rank.TWO;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TestRank {

	@Test
	public void TestRankOrdering() {
		Rank[] expectedOrdering = new Rank[] {TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING, ACE};

		for (int i = 1; i < expectedOrdering.length; i++) {
			assertTrue(expectedOrdering[i - 1] + " is greater than " + expectedOrdering[i], expectedOrdering[i - 1].compareTo(expectedOrdering[i]) < 0);
		}
	}
}
