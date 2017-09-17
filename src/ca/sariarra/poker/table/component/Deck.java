package ca.sariarra.poker.table.component;

import ca.sariarra.poker.card.Card;
import ca.sariarra.poker.card.Rank;
import ca.sariarra.poker.card.Suit;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Deck {
	private final List<Card> deck;
	private List<Card> discards;

	public Deck() {
		Card[] rawCards = new Card[] {
                new Card(Rank.ACE, Suit.HEARTS), new Card(Rank.ACE, Suit.DIAMONDS), new Card(Rank.ACE, Suit.CLUBS), new Card(Rank.ACE, Suit.SPADES),
                new Card(Rank.KING, Suit.HEARTS), new Card(Rank.KING, Suit.DIAMONDS), new Card(Rank.KING, Suit.CLUBS), new Card(Rank.KING, Suit.SPADES),
                new Card(Rank.QUEEN, Suit.HEARTS), new Card(Rank.QUEEN, Suit.DIAMONDS), new Card(Rank.QUEEN, Suit.CLUBS), new Card(Rank.QUEEN, Suit.SPADES),
                new Card(Rank.JACK, Suit.HEARTS), new Card(Rank.JACK, Suit.DIAMONDS), new Card(Rank.JACK, Suit.CLUBS), new Card(Rank.JACK, Suit.SPADES),
                new Card(Rank.TEN, Suit.HEARTS), new Card(Rank.TEN, Suit.DIAMONDS), new Card(Rank.TEN, Suit.CLUBS), new Card(Rank.TEN, Suit.SPADES),
                new Card(Rank.NINE, Suit.HEARTS), new Card(Rank.NINE, Suit.DIAMONDS), new Card(Rank.NINE, Suit.CLUBS), new Card(Rank.NINE, Suit.SPADES),
                new Card(Rank.EIGHT, Suit.HEARTS), new Card(Rank.EIGHT, Suit.DIAMONDS), new Card(Rank.EIGHT, Suit.CLUBS), new Card(Rank.EIGHT, Suit.SPADES),
                new Card(Rank.SEVEN, Suit.HEARTS), new Card(Rank.SEVEN, Suit.DIAMONDS), new Card(Rank.SEVEN, Suit.CLUBS), new Card(Rank.SEVEN, Suit.SPADES),
                new Card(Rank.SIX, Suit.HEARTS), new Card(Rank.SIX, Suit.DIAMONDS), new Card(Rank.SIX, Suit.CLUBS), new Card(Rank.SIX, Suit.SPADES),
                new Card(Rank.FIVE, Suit.HEARTS), new Card(Rank.FIVE, Suit.DIAMONDS), new Card(Rank.FIVE, Suit.CLUBS), new Card(Rank.FIVE, Suit.SPADES),
                new Card(Rank.FOUR, Suit.HEARTS), new Card(Rank.FOUR, Suit.DIAMONDS), new Card(Rank.FOUR, Suit.CLUBS), new Card(Rank.FOUR, Suit.SPADES),
                new Card(Rank.THREE, Suit.HEARTS), new Card(Rank.THREE, Suit.DIAMONDS), new Card(Rank.THREE, Suit.CLUBS), new Card(Rank.THREE, Suit.SPADES),
                new Card(Rank.TWO, Suit.HEARTS), new Card(Rank.TWO, Suit.DIAMONDS), new Card(Rank.TWO, Suit.CLUBS), new Card(Rank.TWO, Suit.SPADES),
        };

		deck = new ArrayList<Card>(rawCards.length);
		for (Card c : rawCards) {
			deck.add(c);
		}

		discards = new ArrayList<Card>();

		shuffle();
	}

	public Card deal() {
		if (deck.isEmpty()) {
			reshuffle();
		}

		if (deck.size() == 0) {
			throw new RuntimeException("The deck is out of cards.");
		}

        return deck.remove(deck.size() - 1);
    }

	public void discard(final Card c) {
		discards.add(c);
	}

	public void shuffle() {
		if (deck.size() == 0) {
			return;
		}

		Random rand = new Random();

		// Run through the deck three times, randomizing the order of the cards.
		for (int j = 0; j < 3; j++) {
			for (int i = 0; i < deck.size(); i++) {
				swapCards(i, rand.nextInt(deck.size()));
			}
		}
	}

	private void swapCards(final int startIndex, final int swapIndex) {
		Card temp = deck.get(swapIndex);
		deck.set(swapIndex, deck.get(startIndex));
		deck.set(startIndex, temp);
	}

	public void reshuffle() {
		deck.addAll(discards);
		discards = new ArrayList<Card>();
		shuffle();
	}

	public int cardsLeftInDeck() {
		return deck.size();
	}

	public int cardsInDiscards() {
		return discards.size();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Card c : deck) {
			sb.append(c.toString());
			sb.append(' ');
		}

		return sb.toString();
	}

	public boolean isEmpty() {
		return deck.isEmpty() && discards.isEmpty();
	}

	public int size() {
		return deck.size();
	}
}
