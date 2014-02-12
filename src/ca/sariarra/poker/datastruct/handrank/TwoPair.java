package ca.sariarra.poker.datastruct.handrank;

import static ca.sariarra.util.ParamUtil.ensureNotNull;

import java.util.Arrays;

import ca.sariarra.poker.datastruct.Card;
import ca.sariarra.poker.datastruct.Rank;
import ca.sariarra.poker.logic.AceHighComparator;

public class TwoPair extends HandRank {

	private TwoPair(boolean isHigh, Card[] hand) {
		super(isHigh, hand);
	}

	public static HandRank doTheseCardsMakeThisHandRank(Card[] hand, boolean isHighRank) {
		ensureNotNull("Hand", hand);
		if (hand.length < 5) {
			return null;
		}
		
		Arrays.sort(hand, new AceHighComparator());
		
		int streak = 1;
		int i;
		for (i = hand.length - 2; i >= 0; i--) {
			if (hand[i].getRank() == hand[i + 1].getRank()) {
				streak++;
				if (streak == 2) {
					break;
				}
			}
			else {
				streak = 1;
			}
		}
		
		if (streak == 2) {
			streak = 1;
			int j;
			Rank topPRank = hand[i].getRank();
			for (j = hand.length - 2; j >= 0; j--) {
				if (hand[j].getRank() == topPRank) {
					continue;
				}
				
				if (hand[j].getRank() == hand[j + 1].getRank()) {
					streak++;
					if (streak == 2) {
						break;
					}
				}
				else {
					streak = 1;
				}
			}
			
			if (streak == 2) {
				Card[] retHand = new Card[5];
				retHand[0] = hand[i];
				retHand[1] = hand[i + 1];
				retHand[2] = hand[j];
				retHand[3] = hand[j + 1];
				int rem = 4;
				
				for (int k = hand.length - 1; k >= 0; k--) {
					if ((k >= i && k <= i + 1) || (k >= j && k <= j + 1)) {
						continue;
					}
					
					retHand[rem] = hand[k];
					rem++;
					if (rem > 4) {
						break;
					}
				}
				
				return new TwoPair(true, retHand);
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
