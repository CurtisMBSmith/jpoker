package ca.sariarra.poker.datastruct.handrank;

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
import static ca.sariarra.poker.types.Suit.CLUBS;
import static ca.sariarra.poker.types.Suit.DIAMONDS;
import static ca.sariarra.poker.types.Suit.HEARTS;
import static ca.sariarra.poker.types.Suit.SPADES;

import java.util.List;

import ca.sariarra.poker.datastruct.Card;
import ca.sariarra.poker.types.Rank;
import ca.sariarra.poker.types.Suit;

public class AbstractHandRankTest {

	public List<Card> makeCardsFromStrings(final String... cards) {

		return null;
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
