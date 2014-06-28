package ca.sariarra.poker.game;

import static ca.sariarra.poker.game.action.HandAction.BETTING_ROUND_NO_LIMIT;
import static ca.sariarra.poker.game.action.HandAction.DEAL_1_COMMUNITY;
import static ca.sariarra.poker.game.action.HandAction.DEAL_2_HOLE;
import static ca.sariarra.poker.game.action.HandAction.DEAL_3_COMMUNITY;
import static ca.sariarra.poker.game.action.HandAction.POST_BLIND_BETS;
import static ca.sariarra.poker.game.handrank.HandRanking.FLUSH;
import static ca.sariarra.poker.game.handrank.HandRanking.FOUR_OF_A_KIND;
import static ca.sariarra.poker.game.handrank.HandRanking.FULL_HOUSE;
import static ca.sariarra.poker.game.handrank.HandRanking.HIGH_CARD;
import static ca.sariarra.poker.game.handrank.HandRanking.PAIR;
import static ca.sariarra.poker.game.handrank.HandRanking.STRAIGHT;
import static ca.sariarra.poker.game.handrank.HandRanking.STRAIGHT_FLUSH;
import static ca.sariarra.poker.game.handrank.HandRanking.THREE_OF_A_KIND;
import static ca.sariarra.poker.game.handrank.HandRanking.TWO_PAIR;

import java.util.ArrayList;
import java.util.List;

import ca.sariarra.poker.card.Card;
import ca.sariarra.poker.game.action.HandAction;
import ca.sariarra.poker.game.handrank.HandRank;
import ca.sariarra.poker.game.handrank.HandRanking;
import ca.sariarra.poker.table.component.BlindLevels;
import ca.sariarra.poker.table.component.Seat;

public class NoLimitTexasHoldEm extends PokerGame {

	public NoLimitTexasHoldEm(final BlindLevels levels) {
		super(levels, new HandAction[] {
				POST_BLIND_BETS,
				DEAL_2_HOLE,
				BETTING_ROUND_NO_LIMIT,
				DEAL_3_COMMUNITY,
				BETTING_ROUND_NO_LIMIT,
				DEAL_1_COMMUNITY,
				BETTING_ROUND_NO_LIMIT,
				DEAL_1_COMMUNITY,
				BETTING_ROUND_NO_LIMIT
			},
			new HandRanking[] {
				STRAIGHT_FLUSH,
				FOUR_OF_A_KIND,
				FULL_HOUSE,
				FLUSH,
				STRAIGHT,
				THREE_OF_A_KIND,
				TWO_PAIR,
				PAIR,
				HIGH_CARD
			}
		);
	}

	@Override
	public List<Seat> determineWinners(final List<Card> communityCards, final List<Seat> contenders) {

		// Rank the hands
		List<Card> fullHand;
		List<Seat> currentWinners = new ArrayList<Seat>();
		HandRank rank;
		HandRank currentBest = null;
		for (Seat s : contenders) {
			if (s.isFolded()) {
				continue;
			}

			if (s.getHandRanking() != null) {
				rank = s.getHandRanking();
			}
			else {
				fullHand = new ArrayList<Card>();
				fullHand.addAll(s.getHand().getCards());
				fullHand.addAll(communityCards);

				rank = rankHand(fullHand);
				if (rank == null) {
					String handString = "[";
					for (int i = 0; i < fullHand.size(); i++) { handString += i == 0 ? "" : " "; fullHand.get(i).toString(); }
					handString += "]";

					throw new RuntimeException("Was not able to rank hand: " + handString);
				}

				s.setHandRanking(rank);
			}

			if (currentBest == null || currentBest.compareTo(rank) < 0) {
				currentBest = rank;
				currentWinners = new ArrayList<Seat>();
				currentWinners.add(s);
			}
			else if (currentBest != null && currentBest.compareTo(rank) == 0) {
				currentWinners.add(s);
			}
		}

		return currentWinners;
	}

}
