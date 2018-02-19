package ca.cmbs.poker.game.component.card;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Deck {
	private final List<Card> deck;
	private List<Card> discards;

	public Deck() {
        deck = Arrays.stream(Rank.values())
                .flatMap(rank -> Arrays.stream(Suit.values())
                        .map(suit -> new Card(rank, suit)))
                .collect(Collectors.toList());

        discards = new LinkedList<>();

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

    private void shuffle() {
        if (deck.size() == 0) {
			return;
		}

		Random rand = new Random();

        Collections.shuffle(deck, rand);
    }

    private void reshuffle() {
        deck.addAll(discards);
        discards = new LinkedList<>();
        shuffle();
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
