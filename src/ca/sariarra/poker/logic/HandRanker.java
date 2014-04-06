package ca.sariarra.poker.logic;

import java.util.List;

import ca.sariarra.poker.datastruct.Card;
import ca.sariarra.poker.datastruct.handrank.HandRank;

public class HandRanker {
	
	public static HandRank rankHighHand(Card[] cards, HandRank[] rankOrder) {
		HandRank result = null;
		for (HandRank rank : high ? highRanks : lowRanks) {
			result = rank.doTheseCardsMakeThisHandRank(cards, true);
			if ()
		}
		return result;
	}
	
	
}
