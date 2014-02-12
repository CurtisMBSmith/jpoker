package ca.sariarra.poker.datastruct.handrank;

import static ca.sariarra.util.ParamUtil.ensureNotNull;

import java.util.Arrays;
import java.util.Comparator;

import ca.sariarra.poker.datastruct.Card;
import ca.sariarra.poker.logic.AceHighComparator;

public class Flush extends HandRank {

	private Flush(boolean isHigh, Card[] hand) {
		super(isHigh, hand);
	}

	public static HandRank doTheseCardsMakeThisHandRank(Card[] hand, boolean isHighRank) {
		ensureNotNull("Hand", hand);
		if (hand.length < 5) {
			return null;
		}
		
		Arrays.sort(hand, new Comparator<Card>() {
			@Override
			public int compare(Card o1, Card o2) {
				if (o1.getSuit() == o2.getSuit()) {
					return (new AceHighComparator()).compare(o1, o2);
				}
				else {
					return o1.getSuit().compareTo(o2.getSuit());					
				}
			}	
		});
		
		int streak = 1;
		int i;
		for (i = hand.length - 2; i >= 0; i--) {
			if (hand[i].getSuit() == hand[i + 1].getSuit()) {
				streak++;
				if (streak == 5) {
					break;
				}
			}
			else {
				streak = 1;
			}
		}
		
		if (streak >= 5) {
			return new Flush(true, Arrays.copyOfRange(hand, i, i + 5));
		}
		
		return null;
	}

	@Override
	public int compareTo(HandRank other) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public String toString() {
		//TODO
		return super.toString();
	}
}
