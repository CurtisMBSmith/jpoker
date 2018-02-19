package ca.cmbs.poker.component.card;

import ca.cmbs.poker.game.component.card.Rank;
import org.junit.jupiter.api.Test;

import static ca.cmbs.poker.game.component.card.Rank.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestRank {

	@Test
	public void TestRankOrdering() {
		Rank[] expectedOrdering = new Rank[] {TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING, ACE};

		for (int i = 1; i < expectedOrdering.length; i++) {
            assertTrue(expectedOrdering[i - 1].compareTo(expectedOrdering[i]) < 0, expectedOrdering[i - 1] + " is greater than " + expectedOrdering[i]);
        }
    }
}
