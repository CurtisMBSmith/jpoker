package ca.sariarra.poker.logic;

import static ca.sariarra.poker.logic.HandAction.BETTING_ROUND_NO_LIMIT;
import static ca.sariarra.poker.logic.HandAction.DEAL_1_COMMUNITY;
import static ca.sariarra.poker.logic.HandAction.DEAL_2_HOLE;
import static ca.sariarra.poker.logic.HandAction.DEAL_3_COMMUNITY;
import static ca.sariarra.poker.logic.HandAction.POST_BLIND_BETS;

import java.util.List;

import ca.sariarra.poker.datastruct.Seat;

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
			}
		);
	}

	@Override
	public List<Seat> determineWinner(final CommunityCards commCards,
			final Seat... contenders) {
		// TODO Auto-generated method stub
		return null;
	}

}
