package ca.sariarra.poker.hand.bettinground;

import static ca.sariarra.poker.player.actions.PlayerAction.BET;
import static ca.sariarra.poker.player.actions.PlayerAction.CALL;
import static ca.sariarra.poker.player.actions.PlayerAction.CHECK;
import static ca.sariarra.poker.player.actions.PlayerAction.FOLD;
import static ca.sariarra.poker.player.actions.PlayerAction.RAISE;
import ca.sariarra.poker.hand.component.HandActionLog;
import ca.sariarra.poker.hand.component.PotManager;
import ca.sariarra.poker.player.actions.AvailableActions;
import ca.sariarra.poker.player.actions.PlayerAction;
import ca.sariarra.poker.table.Table;
import ca.sariarra.poker.table.component.BlindLevel;
import ca.sariarra.poker.table.component.Seat;

public class NoLimitBettingRound extends BettingRound {

	public NoLimitBettingRound(final Seat[] seats, final PotManager potMgr,
			final HandActionLog log, final BlindLevel level, final int roundNum,
			final long turnTimeout, final Table table) {
		super(seats, potMgr, log, level, roundNum, (roundNum == 1 && seats.length > 2) ? 2 : 0 , turnTimeout, table);
	}

	@Override
	public boolean areMoreTurnsPossible() {
		return true;
	}

	@Override
	public AvailableActions determineAllowableActions(final Seat turn) {
		AvailableActions actions;
		if (potMgr.hasUncalledBet()) {
			actions = new AvailableActions(new PlayerAction[] {CALL, RAISE, FOLD},
					potMgr.getUncalledBet(turn), level.getBigBlind(), null);
		}
		else {
			actions = new AvailableActions(new PlayerAction[] {CHECK, BET, FOLD},
					null, level.getBigBlind(), null);
		}

		return actions;
	}

}
