package ca.sariarra.poker.datastruct.handrank;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import ca.sariarra.poker.datastruct.Card;
import ca.sariarra.poker.datastruct.Rank;
import ca.sariarra.poker.datastruct.Suit;

public class HighCardTest {

	@Test
	public void testHighCardCheckingPositiveCases() {
		boolean wantHighRank = true;
		HandRank res = HighCard.doTheseCardsMakeThisHandRank(new Card[] {
				new Card(Rank.ACE, Suit.CLUBS),
				new Card(Rank.TWO, Suit.CLUBS),
				new Card(Rank.THREE, Suit.SPADES),
				new Card(Rank.FOUR, Suit.CLUBS),
				new Card(Rank.FIVE, Suit.CLUBS),
				new Card(Rank.KING, Suit.DIAMONDS),
				new Card(Rank.TEN, Suit.HEARTS)
		}, wantHighRank);
		assertIsHighCard(res, false, new Card[] {
				new Card(Rank.ACE, Suit.CLUBS),
				new Card(Rank.KING, Suit.DIAMONDS),
				new Card(Rank.TEN, Suit.HEARTS),
				new Card(Rank.FIVE, Suit.CLUBS),
				new Card(Rank.FOUR, Suit.CLUBS)
		});
		
		res = HighCard.doTheseCardsMakeThisHandRank(new Card[] {
				new Card(Rank.TWO, Suit.CLUBS),
				new Card(Rank.KING, Suit.CLUBS),
				new Card(Rank.THREE, Suit.HEARTS),
				new Card(Rank.TEN, Suit.SPADES),
				new Card(Rank.FIVE, Suit.SPADES),
				new Card(Rank.SIX, Suit.DIAMONDS),
				new Card(Rank.JACK, Suit.CLUBS)
		}, wantHighRank);
		assertIsHighCard(res, false, new Card[] {
				new Card(Rank.KING, Suit.CLUBS),
				new Card(Rank.JACK, Suit.CLUBS),
				new Card(Rank.TEN, Suit.SPADES),
				new Card(Rank.SIX, Suit.DIAMONDS),
				new Card(Rank.FIVE, Suit.SPADES)
		});

	}
	
	
	private void assertIsHighCard(HandRank r, boolean expectToFail, Card[] expCards) {
		if (expectToFail) {
			assertNull("Hand rank not null when it should be.", r);
		}
		else {
			assertNotNull("Hand rank null when it should not be.", r);
		}
		
		assertTrue("Hand rank not instance of expected class.", 
				r instanceof HighCard);
		
		boolean cardFound;
		for (Card c : expCards) {
			cardFound = false;
			for (Card hc : r.getCards()) {
				if (hc.equals(c)) {
					cardFound = true;
				}
			}
			assertTrue("Did not find a card that was expected: " + c.toString(), cardFound);
		}
	}
}
