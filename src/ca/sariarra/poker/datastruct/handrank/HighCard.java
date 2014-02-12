package ca.sariarra.poker.datastruct.handrank;

import static ca.sariarra.util.ParamUtil.ensureNotNull;

import java.util.Arrays;

import ca.sariarra.poker.datastruct.Card;
import ca.sariarra.poker.logic.AceHighComparator;

public class HighCard extends HandRank {

	private HighCard(boolean isHigh, Card[] hand) {
		super(isHigh, hand);
	}

	public static HandRank doTheseCardsMakeThisHandRank(Card[] hand, boolean isHighRank) {
		ensureNotNull("Hand", hand);
		if (hand.length < 5) {
			return null;
		}
		
		Arrays.sort(hand, new AceHighComparator());
		
		return new HighCard(true, Arrays.copyOfRange(hand, hand.length - 5, hand.length));
	}

	@Override
	public int compareTo(HandRank other) {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
