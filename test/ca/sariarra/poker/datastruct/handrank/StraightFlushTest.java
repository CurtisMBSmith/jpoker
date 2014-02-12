package ca.sariarra.poker.datastruct.handrank;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import ca.sariarra.poker.datastruct.Card;
import ca.sariarra.poker.datastruct.Rank;
import ca.sariarra.poker.datastruct.Suit;

public class StraightFlushTest {

	@Test
	public void testHighStraightFlushCheckingPositiveCases() {
		boolean wantHighRank = true;
		HandRank res = StraightFlush.doTheseCardsMakeThisHandRank(new Card[] {
				new Card(Rank.ACE, Suit.CLUBS, null),
				new Card(Rank.TWO, Suit.CLUBS, null),
				new Card(Rank.THREE, Suit.CLUBS, null),
				new Card(Rank.FOUR, Suit.CLUBS, null),
				new Card(Rank.FIVE, Suit.CLUBS, null),
				new Card(Rank.SIX, Suit.DIAMONDS, null),
				new Card(Rank.TEN, Suit.CLUBS, null)
		}, wantHighRank);
		assertIsStraightFlush(res, false, new Card[] {
				new Card(Rank.ACE, Suit.CLUBS, null),
				new Card(Rank.TWO, Suit.CLUBS, null),
				new Card(Rank.THREE, Suit.CLUBS, null),
				new Card(Rank.FOUR, Suit.CLUBS, null),
				new Card(Rank.FIVE, Suit.CLUBS, null)
		});
	}
	
	private void assertIsStraightFlush(HandRank r, boolean expectToFail, Card[] expCards) {
		if (expectToFail) {
			assertNull("Hand rank not null when it should be.", r);
		}
		else {
			assertNotNull("Hand rank null when it should not be.", r);
		}
		
		assertTrue("Hand rank not instance of expected class.", 
				r instanceof StraightFlush);
		
		boolean cardFound;
		for (Card c : expCards) {
			cardFound = false;
			for (Card hc : r.getCards()) {
				if (hc.equals(c)) {
					cardFound = true;
				}
			}
			assertTrue("Did not find a card that was expected.", cardFound);
		}
	}
}
