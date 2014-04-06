package ca.sariarra.poker.datastruct.handrank;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import ca.sariarra.poker.datastruct.Card;
import ca.sariarra.poker.types.Rank;
import ca.sariarra.poker.types.Suit;

public class FlushTest {

	@Test
	public void testHighFlushCheckingPositiveCases() {
		boolean wantHighRank = true;
		HandRank res = Flush.doTheseCardsMakeThisHandRank(new Card[] {
				new Card(Rank.ACE, Suit.CLUBS),
				new Card(Rank.TWO, Suit.CLUBS),
				new Card(Rank.THREE, Suit.CLUBS),
				new Card(Rank.FOUR, Suit.CLUBS),
				new Card(Rank.FIVE, Suit.CLUBS),
				new Card(Rank.ACE, Suit.DIAMONDS),
				new Card(Rank.TEN, Suit.CLUBS)
		}, wantHighRank);
		assertIsFlush(res, false, new Card[] {
				new Card(Rank.ACE, Suit.CLUBS),
				new Card(Rank.THREE, Suit.CLUBS),
				new Card(Rank.FOUR, Suit.CLUBS),
				new Card(Rank.FIVE, Suit.CLUBS),
				new Card(Rank.TEN, Suit.CLUBS)
		});
		
		res = Flush.doTheseCardsMakeThisHandRank(new Card[] {
				new Card(Rank.THREE, Suit.CLUBS),
				new Card(Rank.KING, Suit.CLUBS),
				new Card(Rank.THREE, Suit.HEARTS),
				new Card(Rank.TEN, Suit.HEARTS),
				new Card(Rank.EIGHT, Suit.HEARTS),
				new Card(Rank.SIX, Suit.HEARTS),
				new Card(Rank.JACK, Suit.HEARTS)
		}, wantHighRank);
		assertIsFlush(res, false, new Card[] {
				new Card(Rank.JACK, Suit.HEARTS),
				new Card(Rank.TEN, Suit.HEARTS),
				new Card(Rank.EIGHT, Suit.HEARTS),
				new Card(Rank.SIX, Suit.HEARTS),
				new Card(Rank.THREE, Suit.HEARTS)
		});
	}
	
	private void assertIsFlush(HandRank r, boolean expectToFail, Card[] expCards) {
		if (expectToFail) {
			assertNull("Hand rank not null when it should be.", r);
		}
		else {
			assertNotNull("Hand rank null when it should not be.", r);
		}
		
		assertTrue("Hand rank not instance of expected class.", 
				r instanceof Flush);
		
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
