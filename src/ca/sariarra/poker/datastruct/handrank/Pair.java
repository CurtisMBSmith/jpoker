package ca.sariarra.poker.datastruct.handrank;

import static ca.sariarra.util.ParamUtil.ensureNotNull;

import java.util.Arrays;

import ca.sariarra.poker.datastruct.Card;
import ca.sariarra.poker.logic.AceHighComparator;

public class Pair extends HandRank {

	private Pair(boolean isHigh, Card[] hand) {
		super(isHigh, hand);
	}

	public static HandRank doTheseCardsMakeThisHandRank(Card[] hand, boolean isHighRank) {
		ensureNotNull("Hand", hand);
		if (hand.length < 2) {
			return null;
		}
		
		Arrays.sort(hand, new AceHighComparator());
		
		int i = hand.length - 1;
		boolean pairFound = false;
		while (i >= 1) {
			if (hand[i].getRank() == hand[i-1].getRank()) {
				pairFound = true;
				break;
			}
			
			i--;
		}
		
		if (pairFound) {
			Card[] pairHand = new Card[5];
			pairHand[0] = hand[i];
			pairHand[1] = hand[i - 1];
			int handPos = 2;
			for (int k = hand.length - 1; k >= 0 && handPos < 5; k--) {
				if (k == i || k == i - 1) {
					continue;
				}
				
				pairHand[handPos] = hand[k];
				handPos++;
			}
			
			return new Pair(true, pairHand);
		}

		return null;
	}
	
	@Override
	public int compareTo(HandRank other) {
		// TODO Auto-generated method stub
		return 0;
	}

}
