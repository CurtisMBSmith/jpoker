package ca.sariarra.poker.logic;

import java.util.Comparator;

import ca.sariarra.poker.datastruct.Card;
import ca.sariarra.poker.datastruct.Rank;

public class AceHighComparator implements Comparator<Card> {

	@Override
	public int compare(Card o1, Card o2) {
		if (o1.getRank() == Rank.ACE && o2.getRank() == Rank.ACE) {
			return 0;
		}
		else if (o1.getRank() == Rank.ACE && o2.getRank() != Rank.ACE) {
			return 1;
		}
		else if (o2.getRank() == Rank.ACE && o1.getRank() != Rank.ACE) {
			return -1;
		}
		else {
			return o1.getRank().compareTo(o2.getRank());
		}
	}
	
}