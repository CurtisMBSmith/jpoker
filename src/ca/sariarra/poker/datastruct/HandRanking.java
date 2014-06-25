package ca.sariarra.poker.datastruct;

import static ca.sariarra.poker.types.Rank.ACE;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ca.sariarra.poker.datastruct.handrank.HandRank;
import ca.sariarra.poker.logic.Rankable;
import ca.sariarra.poker.types.Rank;

public enum HandRanking implements Rankable {

	STRAIGHT_FLUSH(new Comparator<HandRank>() {

		@Override
		public int compare(final HandRank o1, final HandRank o2) {
			// TODO Auto-generated method stub
			return 0;
		}

	})
	{
		@Override
		public HandRank rankHand(final List<Card> cards) {
			// TODO
			return null;
		}
	},
	FOUR_OF_A_KIND(new Comparator<HandRank>() {

		@Override
		public int compare(final HandRank o1, final HandRank o2) {
			// TODO Auto-generated method stub
			return 0;
		}

	})
	{
		@Override
		public HandRank rankHand(final List<Card> cards) {
			// TODO
			return null;
		}
	},
	FULL_HOUSE(new Comparator<HandRank>() {

		@Override
		public int compare(final HandRank o1, final HandRank o2) {
			// TODO Auto-generated method stub
			return 0;
		}

	})
	{
		@Override
		public HandRank rankHand(final List<Card> cards) {
			// TODO
			return null;
		}
	},
	FLUSH(new Comparator<HandRank>() {

		@Override
		public int compare(final HandRank o1, final HandRank o2) {
			// TODO Auto-generated method stub
			return 0;
		}

	})
	{
		@Override
		public HandRank rankHand(final List<Card> cards) {
			// TODO
			return null;
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
			// TODO Auto-generated method stub
			return 0;
		}

	})
	{
		@Override
		public HandRank rankHand(final List<Card> cards) {
			// TODO
			return null;
		}
	},
	TWO_PAIR(new Comparator<HandRank>() {

		@Override
		public int compare(final HandRank o1, final HandRank o2) {
			// TODO Auto-generated method stub
			return 0;
		}

	})
	{
		@Override
		public HandRank rankHand(final List<Card> cards) {
			// TODO
			return null;
		}
	},
	PAIR(new Comparator<HandRank>() {

		@Override
		public int compare(final HandRank o1, final HandRank o2) {
			// TODO Auto-generated method stub
			return 0;
		}

	})
	{
		@Override
		public HandRank rankHand(final List<Card> cards) {
			// TODO
			return null;
		}
	},
	HIGH_CARD(new Comparator<HandRank>() {

		@Override
		public int compare(final HandRank o1, final HandRank o2) {
			// TODO Auto-generated method stub
			return 0;
		}

	})
	{
		@Override
		public HandRank rankHand(final List<Card> cards) {
			// TODO
			return null;
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
		List<Card> result = new ArrayList<Card>(cards.size());
		Collections.copy(result, cards);

		result.sort(new Comparator<Card>() {
			@Override
			public int compare(final Card o1, final Card o2) {
				return o1.getRank().compareTo(o2.getRank());
			}
		});

		// Remove duplicates.
		Rank current = null;
		for (Card c : result) {
			if (current == null || current != c.getRank()) {
				current = c.getRank();
			}
			else {
				result.remove(c);
			}
		}

		// Add an ace to the end as well if aces can be high and low.
		if (aceHiLow && result.size() > 0 && result.get(0).getRank() == ACE) {
			result.add(result.get(0));
		}

		return result;
	}
}
