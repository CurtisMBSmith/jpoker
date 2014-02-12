package ca.sariarra.poker.logic;

import java.util.List;

import ca.sariarra.poker.datastruct.Card;
import ca.sariarra.poker.datastruct.handrank.HandRank;

public class HandRanker {
	private List<? extends HandRank> highRanks;
	private List<? extends HandRank> lowRanks;
	
	public HandRanker(List<? extends HandRank> pHighRanks, List<? extends HandRank> pLowRanks) {
		highRanks = pHighRanks;
		lowRanks = pLowRanks;
	}

	public HandRank rankHand(Card[] cards, HandRank[] rankOrder, boolean high) {
		HandRank result = null;
		for (HandRank rank : high ? highRanks : lowRanks) {
			result = rank.doTheseCardsMakeThisHandRank(cards, true);
			if ()
		}
		return result;
	}
	
	
}
