package ca.sariarra.poker.game.handrank;

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
import static ca.sariarra.poker.card.Suit.CLUBS;
import static ca.sariarra.poker.card.Suit.DIAMONDS;
import static ca.sariarra.poker.card.Suit.HEARTS;
import static ca.sariarra.poker.card.Suit.SPADES;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import ca.sariarra.poker.card.Card;
import ca.sariarra.poker.card.Rank;
import ca.sariarra.poker.card.Suit;

public class AbstractHandRankTest {

	public List<Card> makeCardsFromStrings(final String... cards) {
		List<Card> results = new CopyOnWriteArrayList<Card>();
		for (String c : cards) {
			results.add(new Card(getRankFromChar(c.charAt(0)), getSuitFromChar(c.charAt(1))));
		}
		return results;
	}

	public Rank getRankFromChar(final char ch) {
		switch (ch) {
		case 'A':
			return ACE;
		case 'K':
			return KING;
		case 'Q':
			return QUEEN;
		case 'J':
			return JACK;
		case 'T':
			return TEN;
		case '9':
			return NINE;
		case '8':
			return EIGHT;
		case '7':
			return SEVEN;
		case '6':
			return SIX;
		case '5':
			return FIVE;
		case '4':
			return FOUR;
		case '3':
			return THREE;
		case '2':
			return TWO;
		default:
			throw new IllegalArgumentException("Invalid rank: " + ch + ".");
		}
	}

	public Suit getSuitFromChar(final char ch) {
		switch (ch) {
		case 'h':
			return HEARTS;
		case 'd':
			return DIAMONDS;
		case 's':
			return SPADES;
		case 'c':
			return CLUBS;
		default:
			throw new IllegalArgumentException("Invalid suit: " + ch + ".");
		}
	}
}
