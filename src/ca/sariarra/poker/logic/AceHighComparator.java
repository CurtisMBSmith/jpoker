package ca.sariarra.poker.logic;

import java.util.Comparator;

import ca.sariarra.poker.datastruct.Card;
import ca.sariarra.poker.types.Rank;

public class AceHighComparator implements Comparator<Card> {

	@Override
	public int compare(Card o1, Card o2) {
		if (o1.rank() == Rank.ACE && o2.rank() == Rank.ACE) {
			return 0;
		}
		else if (o1.rank() == Rank.ACE && o2.rank() != Rank.ACE) {
			return 1;
		}
		else if (o2.rank() == Rank.ACE && o1.rank() != Rank.ACE) {
			return -1;
		}
		else {
			return o1.rank().compareTo(o2.rank());
		}
	}
	
}