package ca.sariarra.poker.datastruct.handrank;

import static ca.sariarra.util.ParamUtil.ensureNotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import ca.sariarra.poker.datastruct.Card;
import ca.sariarra.poker.types.Rank;
import ca.sariarra.poker.types.Suit;

public class StraightFlush extends HandRank {
	
	private StraightFlush(boolean isHigh, Card[] hand) {
		super(isHigh, hand);
	}

	public static HandRank doTheseCardsMakeThisHandRank(Card[] hand, boolean isHighRank) {
		ensureNotNull("Hand", hand);
		if (hand.length < 5) {
			return null;
		}
		
		// Separate the cards in the hand by suit. 
		Map<Suit, List<Card>> handToSuitMap = new HashMap<Suit, List<Card>>(8);
		for (Card c : hand) {
			if (!handToSuitMap.containsKey(c.getSuit())) {
				handToSuitMap.put(c.getSuit(), new ArrayList<Card>(8));				
			}
			handToSuitMap.get(c.getSuit()).add(c);
		}
		
		// Check the cards by suit to see if there are any candidates for a
		// straight flush, removing any entities that do not make a straight
		// flush.
		for (Entry<Suit, List<Card>> entry : handToSuitMap.entrySet()) {
			if (entry.getValue().size() < 5) {
				handToSuitMap.remove(entry.getKey());
			}
		}
		
		// If there are no candidates left, return null.
		if (handToSuitMap.isEmpty()) {
			return null;
		}
		
		// Determine the best straight flush possible.
		for (Entry<Suit, List<Card>> entry : handToSuitMap.entrySet()) {
			// Sort the list. The natural ordering is by card rank from A-K.
			Collections.sort(entry.getValue());
			
			// If the first card is an Ace, add a copy to the end of the array
			// to account for the ace-high straight flush.
			if (entry.getValue().get(0).getRank() == Rank.ACE) {
				entry.getValue().add(entry.getValue().get(0));
				
			}
		}
		
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int compareTo(HandRank other) {
		// TODO Auto-generated method stub
		return 0;
	}

}
