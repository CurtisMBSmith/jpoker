package ca.sariarra.poker.datastruct.handrank;

import static ca.sariarra.util.ParamUtil.ensureNotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ca.sariarra.poker.datastruct.Card;
import ca.sariarra.poker.datastruct.Rank;

public class Straight extends HandRank {

	private Straight(boolean isHigh, Card[] hand) {
		super(isHigh, hand);
	}

	public static HandRank doTheseCardsMakeThisHandRank(Card[] hand, boolean isHighRank) {
		ensureNotNull("Hand", hand);
		if (hand.length < 5) {
			return null;
		}
		
		Arrays.sort(hand);
		
		List<Card> handAsList = new ArrayList<Card>();
		for (Card c : hand) {
			handAsList.add(c);
		}
		
		// If the first card is an Ace, add it to the end as well to account
		// for Ace-high straights.
		if (handAsList.get(0).getRank() == Rank.ACE) {
			handAsList.add(handAsList.get(0));
		}
		
		// Iterate backwards through the hand, keeping track of the largest
		// streak. If one of length 5 is found, return it as a straight
		int streak = 1;
		for (int i = handAsList.size() - 2; i >= 0; i--) {
			if (handAsList.get(i + 1).getRank().getValue() - handAsList.get(i).getRank().getValue() == 1
					|| (handAsList.get(i + 1).getRank() == Rank.ACE && handAsList.get(i).getRank() == Rank.KING)) {
				streak++;
			}
			else {
				streak = 1;
			}
			
			if (streak == 5) {
				return new Straight(isHighRank, handAsList.subList(i, i + 5).toArray(new Card[]{}));
			}
		}
		
		return null;
	}

	@Override
	public int compareTo(HandRank other) {
		// TODO Auto-generated method stub
		return 0;
	}
}
