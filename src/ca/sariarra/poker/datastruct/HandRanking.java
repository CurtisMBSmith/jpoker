package ca.sariarra.poker.datastruct;

import static ca.sariarra.poker.datastruct.handrank.HandRank.HAND_SIZE;
import static ca.sariarra.poker.types.Rank.ACE;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import ca.sariarra.poker.datastruct.handrank.HandRank;
import ca.sariarra.poker.logic.Rankable;
import ca.sariarra.poker.types.Rank;
import ca.sariarra.poker.types.Suit;

public enum HandRanking implements Rankable {

	STRAIGHT_FLUSH(new Comparator<HandRank>() {

		@Override
		public int compare(final HandRank o1, final HandRank o2) {
			int comparedByRankings = o1.getRank().compareTo(o2.getRank());
			if (comparedByRankings == 0) {
				return o1.getCards().get(0).getRank().compareTo(o2.getCards().get(0).getRank());
			}
			else {
				return comparedByRankings;
			}
		}
	})
	{
		@Override
		public HandRank rankHand(final List<Card> cards) {
			// Group cards by suit.
			Map<Suit, List<Card>> cardsBySuit = groupCardsBySuit(cards);

			// Check if any grouping makes a straight. If so, we have a straight
			// flush.
			HandRank result = null;
			List<Card> cardsOfSuit;
			for (Entry<Suit, List<Card>> grouping : cardsBySuit.entrySet()) {
				cardsOfSuit = grouping.getValue();

				if (cardsOfSuit.size() >= HAND_SIZE) {
					List<Card> cardsOrderedByRank = orderCardsByRank(cardsOfSuit, true);

					if (cardsOrderedByRank.size() < 5) {
						continue;
					}

					int streak = 1;
					int i = cardsOrderedByRank.size() - 1;
					while (i >= 0 && streak < 5) {
						i--;

						if (cards.get(i + 1).getRank().getValue() - cards.get(i).getRank().getValue() == 1) {
							streak++;
						}
						else {
							streak = 1;
						}
					}

					if (streak == 5) {
						HandRank interimResult = new HandRank(this, cardsOrderedByRank.subList(i, i + 5));

						if (result == null || interimResult.compareTo(result) > 0) {
							result = interimResult;
						}
					}
				}
			}

			return result;
		}
	},
	FOUR_OF_A_KIND(new Comparator<HandRank>() {

		@Override
		public int compare(final HandRank o1, final HandRank o2) {
			int comparedByRankings = o1.getRank().compareTo(o2.getRank());
			if (comparedByRankings == 0) {
				int secondaryComparison = o1.getCards().get(0).getRank().compareTo(o2.getCards().get(0).getRank());
				if (secondaryComparison == 0) {
					return o1.getCards().get(4).getRank().compareTo(o2.getCards().get(4).getRank());
				}
				else {
					return secondaryComparison;
				}
			}
			else {
				return comparedByRankings;
			}
		}
	})
	{
		@Override
		public HandRank rankHand(final List<Card> cards) {
			Map<Rank, List<Card>> cardsByRank = groupCardsByRank(cards, 4);

			Rank fourOfAKindRank = null;
			for (Entry<Rank, List<Card>> entry : cardsByRank.entrySet()) {
				if (entry.getValue().size() >= 4) {
					if (fourOfAKindRank == null || entry.getKey().compareTo(fourOfAKindRank) > 0) {
						fourOfAKindRank = entry.getKey();
					}
				}
			}

			HandRank res = null;
			if (fourOfAKindRank != null) {
				List<Card> result = cardsByRank.get(fourOfAKindRank);

				// Find the kicker.
				List<Card> cardsCopy = new ArrayList<Card>(cards.size());
				Collections.copy(cardsCopy, cards);

				sortByRank(cardsCopy);
				for (int i = cardsCopy.size() - 1; i >= 0; i--) {
					if (cardsCopy.get(i).getRank() != fourOfAKindRank) {
						result.add(cardsCopy.get(i));
						break;
					}
				}

				res = new HandRank(this, result);
			}

			return res;
		}
	},
	FULL_HOUSE(new Comparator<HandRank>() {

		@Override
		public int compare(final HandRank o1, final HandRank o2) {
			int comparedByRankings = o1.getRank().compareTo(o2.getRank());
			if (comparedByRankings == 0) {
				int secondaryComparison = o1.getCards().get(0).getRank().compareTo(o2.getCards().get(0).getRank());
				if (secondaryComparison == 0) {
					return o1.getCards().get(3).getRank().compareTo(o2.getCards().get(3).getRank());
				}
				else {
					return secondaryComparison;
				}
			}
			else {
				return comparedByRankings;
			}
		}

	})
	{
		@Override
		public HandRank rankHand(final List<Card> cards) {
			Map<Rank, List<Card>> cardsByRank = groupCardsByRank(cards, 3);

			Rank threeOfAKindRank = null;
			for (Entry<Rank, List<Card>> entry : cardsByRank.entrySet()) {
				if (entry.getValue().size() >= 3) {
					if (threeOfAKindRank == null || entry.getKey().compareTo(threeOfAKindRank) > 0) {
						threeOfAKindRank = entry.getKey();
					}
				}
			}

			HandRank res = null;
			if (threeOfAKindRank != null) {
				List<Card> result = cardsByRank.get(threeOfAKindRank);
				result = result.subList(0, 3);

				// Find the kicker.
				List<Card> cardsCopy = new ArrayList<Card>(cards.size());
				Collections.copy(cardsCopy, cards);

				// Remove any cards of the three-of-a-kind rank.
				removeCardsOfRank(cardsCopy, threeOfAKindRank);

				cardsByRank = groupCardsByRank(cardsCopy, 2);
				Rank twoOfAKindRank = null;
				for (Entry<Rank, List<Card>> entry : cardsByRank.entrySet()) {
					if (entry.getValue().size() >= 2) {
						if (twoOfAKindRank == null || entry.getKey().compareTo(twoOfAKindRank) > 0) {
							twoOfAKindRank = entry.getKey();
						}
					}
				}

				if (twoOfAKindRank != null) {
					List<Card> highTwo = cardsByRank.get(twoOfAKindRank);
					highTwo = highTwo.subList(0, 2);

					result.addAll(highTwo);

					res = new HandRank(this, result);
				}
			}

			return res;
		}
	},
	FLUSH(new Comparator<HandRank>() {

		@Override
		public int compare(final HandRank o1, final HandRank o2) {
			int comparedByRankings = o1.getRank().compareTo(o2.getRank());
			if (comparedByRankings == 0) {
				int cardByCardComparison = 0;
				for (int i = HAND_SIZE - 1; i >= 0; i--) {
					cardByCardComparison = o1.getCards().get(i).compareTo(o2.getCards().get(i));
					if (cardByCardComparison != 0) {
						break;
					}
				}

				return cardByCardComparison;
			}
			else {
				return comparedByRankings;
			}
		}
	})
	{
		@Override
		public HandRank rankHand(final List<Card> cards) {
			// Group cards by suit.
			Map<Suit, List<Card>> cardsBySuit = groupCardsBySuit(cards);

			HandRank result = null;
			List<Card> cardsOfSuit;
			for (Entry<Suit, List<Card>> grouping : cardsBySuit.entrySet()) {
				cardsOfSuit = grouping.getValue();

				if (cardsOfSuit.size() >= HAND_SIZE) {
					HandRank interimResult = new HandRank(this, cardsOfSuit.subList(
							cardsOfSuit.size() - HAND_SIZE, cardsOfSuit.size()));

					if (result == null || interimResult.compareTo(result) > 0) {
						result = interimResult;
					}
				}
			}

			return result;
		}
	},
	STRAIGHT(new Comparator<HandRank>() {

		@Override
		public int compare(final HandRank o1, final HandRank o2) {
			int comparedByRankings = o1.getRank().compareTo(o2.getRank());
			if (comparedByRankings == 0) {
				return o1.getCards().get(0).getRank().compareTo(o2.getCards().get(0).getRank());
			}
			else {
				return comparedByRankings;
			}
		}
	})
	{
		@Override
		public HandRank rankHand(final List<Card> cards) {
			List<Card> cardsCopy = new ArrayList<Card>(cards.size());
			Collections.copy(cardsCopy, cards);
			List<Card> cardsOrderedByRank = orderCardsByRank(cards, true);

			if (cardsOrderedByRank.size() < 5) {
				return null;
			}

			int streak = 1;
			int i = cardsOrderedByRank.size() - 1;
			while (i >= 0 && streak < 5) {
				i--;

				if (cards.get(i + 1).getRank().getValue() - cards.get(i).getRank().getValue() == 1) {
					streak++;
				}
				else {
					streak = 1;
				}
			}

			if (streak == 5) {
				return new HandRank(this, cardsOrderedByRank.subList(i, i + 5));
			}

			return null;
		}
	},
	THREE_OF_A_KIND(new Comparator<HandRank>() {

		@Override
		public int compare(final HandRank o1, final HandRank o2) {
			int comparedByRankings = o1.getRank().compareTo(o2.getRank());
			if (comparedByRankings == 0) {
				int secondaryComparison = o1.getCards().get(0).getRank().compareTo(o2.getCards().get(0).getRank());
				if (secondaryComparison == 0) {
					int tertiaryComparison = o1.getCards().get(3).getRank().compareTo(o2.getCards().get(3).getRank());
					if (tertiaryComparison == 0) {
						return o1.getCards().get(4).getRank().compareTo(o2.getCards().get(4).getRank());
					}
					else {
						return tertiaryComparison;
					}
				}
				else {
					return secondaryComparison;
				}
			}
			else {
				return comparedByRankings;
			}
		}
	})
	{
		@Override
		public HandRank rankHand(final List<Card> cards) {
			Map<Rank, List<Card>> cardsByRank = groupCardsByRank(cards, 3);

			Rank threeOfAKindRank = null;
			for (Entry<Rank, List<Card>> entry : cardsByRank.entrySet()) {
				if (entry.getValue().size() >= 3) {
					if (threeOfAKindRank == null || entry.getKey().compareTo(threeOfAKindRank) > 0) {
						threeOfAKindRank = entry.getKey();
					}
				}
			}

			HandRank res = null;
			if (threeOfAKindRank != null) {
				List<Card> result = cardsByRank.get(threeOfAKindRank);
				result = result.subList(0, 3);

				// Find the kickers.
				List<Card> cardsCopy = new ArrayList<Card>(cards.size());
				Collections.copy(cardsCopy, cards);

				sortByRank(cardsCopy);
				for (int i = cardsCopy.size() - 1; i >= 0; i--) {
					if (cardsCopy.get(i).getRank() != threeOfAKindRank) {
						result.add(cardsCopy.get(i));
					}

					if (result.size() == HAND_SIZE) {
						break;
					}
				}

				res = new HandRank(this, result);
			}

			return res;
		}
	},
	TWO_PAIR(new Comparator<HandRank>() {

		@Override
		public int compare(final HandRank o1, final HandRank o2) {
			int comparedByRankings = o1.getRank().compareTo(o2.getRank());
			if (comparedByRankings == 0) {
				int secondaryComparison = o1.getCards().get(0).getRank().compareTo(o2.getCards().get(0).getRank());
				if (secondaryComparison == 0) {
					int tertiaryComparison = o1.getCards().get(2).getRank().compareTo(o2.getCards().get(2).getRank());
					if (tertiaryComparison == 0) {
						return o1.getCards().get(4).getRank().compareTo(o2.getCards().get(4).getRank());
					}
					else {
						return tertiaryComparison;
					}
				}
				else {
					return secondaryComparison;
				}
			}
			else {
				return comparedByRankings;
			}
		}

	})
	{
		@Override
		public HandRank rankHand(final List<Card> cards) {
			Map<Rank, List<Card>> cardsByRank = groupCardsByRank(cards, 2);

			Rank topTwoOfAKindRank = null;
			for (Entry<Rank, List<Card>> entry : cardsByRank.entrySet()) {
				if (entry.getValue().size() >= 2) {
					if (topTwoOfAKindRank == null || entry.getKey().compareTo(topTwoOfAKindRank) > 0) {
						topTwoOfAKindRank = entry.getKey();
					}
				}
			}

			HandRank res = null;
			if (topTwoOfAKindRank != null) {
				List<Card> result = cardsByRank.get(topTwoOfAKindRank);
				result = result.subList(0, 2);

				// Find the second pair.
				List<Card> cardsCopy = new ArrayList<Card>(cards.size());
				Collections.copy(cardsCopy, cards);

				// Remove any cards of the first two-of-a-kind rank.
				removeCardsOfRank(cardsCopy, topTwoOfAKindRank);

				cardsByRank = groupCardsByRank(cardsCopy, 2);
				Rank bottomTwoOfAKindRank = null;
				for (Entry<Rank, List<Card>> entry : cardsByRank.entrySet()) {
					if (entry.getValue().size() >= 2) {
						if (bottomTwoOfAKindRank == null || entry.getKey().compareTo(bottomTwoOfAKindRank) > 0) {
							bottomTwoOfAKindRank = entry.getKey();
						}
					}
				}

				if (bottomTwoOfAKindRank != null) {
					List<Card> bottomTwo = cardsByRank.get(bottomTwoOfAKindRank);
					bottomTwo = bottomTwo.subList(0, 2);

					result.addAll(bottomTwo);

					res = new HandRank(this, result);

					// Find the kicker
					cardsCopy = new ArrayList<Card>(cards.size());
					Collections.copy(cardsCopy, cards);
					removeCardsOfRank(cardsCopy, topTwoOfAKindRank);
					removeCardsOfRank(cardsCopy, bottomTwoOfAKindRank);

					sortByRank(cardsCopy);
					for (int i = cardsCopy.size() - 1; i >= 0; i--) {
						if (cardsCopy.get(i).getRank() != topTwoOfAKindRank
								&& cardsCopy.get(i).getRank() != bottomTwoOfAKindRank) {
							result.add(cardsCopy.get(i));
						}

						if (result.size() == HAND_SIZE) {
							break;
						}
					}
				}
			}

			return res;
		}
	},
	PAIR(new Comparator<HandRank>() {

		@Override
		public int compare(final HandRank o1, final HandRank o2) {
			int comparedByRankings = o1.getRank().compareTo(o2.getRank());
			if (comparedByRankings == 0) {
				int secondaryComparison = o1.getCards().get(0).getRank().compareTo(o2.getCards().get(0).getRank());
				if (secondaryComparison == 0) {
					int tertiaryComparison = o1.getCards().get(2).getRank().compareTo(o2.getCards().get(2).getRank());
					if (tertiaryComparison == 0) {
						int quaternaryComparison = o1.getCards().get(3).getRank().compareTo(o2.getCards().get(3).getRank());
						if (quaternaryComparison == 0) {
							return o1.getCards().get(4).getRank().compareTo(o2.getCards().get(4).getRank());
						}
						else {
							return quaternaryComparison;
						}
					}
					else {
						return tertiaryComparison;
					}
				}
				else {
					return secondaryComparison;
				}
			}
			else {
				return comparedByRankings;
			}
		}
	})
	{
		@Override
		public HandRank rankHand(final List<Card> cards) {
			Map<Rank, List<Card>> cardsByRank = groupCardsByRank(cards, 2);

			Rank pairRank = null;
			for (Entry<Rank, List<Card>> entry : cardsByRank.entrySet()) {
				if (entry.getValue().size() >= 3) {
					if (pairRank == null || entry.getKey().compareTo(pairRank) > 0) {
						pairRank = entry.getKey();
					}
				}
			}

			HandRank res = null;
			if (pairRank != null) {
				List<Card> result = cardsByRank.get(pairRank);
				result = result.subList(0, 2);

				// Find the kickers.
				List<Card> cardsCopy = new ArrayList<Card>(cards.size());
				Collections.copy(cardsCopy, cards);

				sortByRank(cardsCopy);
				for (int i = cardsCopy.size() - 1; i >= 0; i--) {
					if (cardsCopy.get(i).getRank() != pairRank) {
						result.add(cardsCopy.get(i));
					}

					if (result.size() == HAND_SIZE) {
						break;
					}
				}

				res = new HandRank(this, result);
			}

			return res;
		}
	},
	HIGH_CARD(new Comparator<HandRank>() {

		@Override
		public int compare(final HandRank o1, final HandRank o2) {
			int comparedByRankings = o1.getRank().compareTo(o2.getRank());
			if (comparedByRankings == 0) {
				int result = 0;
				for (int i = 0; i < HAND_SIZE; i++) {
					result = o1.getCards().get(i).getRank().compareTo(o2.getCards().get(i).getRank());
					if (result != 0) {
						break;
					}
				}

				return result;
			}
			else {
				return comparedByRankings;
			}
		}

	})
	{
		@Override
		public HandRank rankHand(final List<Card> cards) {
			List<Card> cardsCopy = new ArrayList<Card>(cards.size());
			Collections.copy(cardsCopy, cards);

			return new HandRank(this, cardsCopy.subList(cardsCopy.size() - HAND_SIZE, cardsCopy.size()));
		}
	};

	private final Comparator<HandRank> comparator;

	private HandRanking(final Comparator<HandRank> comparator) {
		this.comparator = comparator;
	}

	public Comparator<HandRank> getComparator() {
		return comparator;
	}

	public static List<Card> orderCardsByRank(final List<Card> cards, final boolean aceHiLow) {
		sortByRank(cards);
		removeDuplicateRanks(cards);

		// Add an ace to the end as well if aces can be high and low.
		if (aceHiLow && cards.size() > 0 && cards.get(0).getRank() == ACE) {
			cards.add(cards.get(0));
		}

		return cards;
	}

	public static void removeDuplicateRanks(final List<Card> cards) {
		for (int i = 0; i < cards.size(); i++) {
			for (int j = cards.size() - 1; j > i; j--) {
				if (cards.get(j).getRank() == cards.get(i).getRank()) {
					cards.remove(j);
				}
			}
		}
	}

	public static void removeCardsOfRank(final List<Card> cards, final Rank rank) {
		for (Card c : cards) {
			if (c.getRank() == rank) {
				cards.remove(c);
			}
		}
	}

	public static void sortByRank(final List<Card> cards) {
		cards.sort(new Comparator<Card>() {
			@Override
			public int compare(final Card o1, final Card o2) {
				return o1.getRank().compareTo(o2.getRank());
			}
		});
	}

	public static Map<Suit, List<Card>> groupCardsBySuit(final List<Card> cards) {
		Map<Suit, List<Card>> cardsGroupedBySuit = new HashMap<Suit, List<Card>>(4);
		for (Suit suit : Suit.values()) {
			List<Card> cardsBySuit = new ArrayList<Card>(7);
			for (Card card : cards) {
				if (card.getSuit() == suit) {
					cardsBySuit.add(card);
				}
			}

			cardsGroupedBySuit.put(suit, cardsBySuit);
		}

		return cardsGroupedBySuit;
	}

	public static Map<Rank, List<Card>> groupCardsByRank(final List<Card> cards, final int inclusionThreshold) {
		Map<Rank, List<Card>> cardsGroupedByRank = new HashMap<Rank, List<Card>>(4);
		for (Rank rank : Rank.values()) {
			List<Card> cardsByRank = new ArrayList<Card>(7);
			for (Card card : cards) {
				if (card.getRank() == rank) {
					cardsByRank.add(card);
				}
			}

			if (cardsByRank.size() >= inclusionThreshold) {
				cardsGroupedByRank.put(rank, cardsByRank);
			}
		}

		return cardsGroupedByRank;
	}
}