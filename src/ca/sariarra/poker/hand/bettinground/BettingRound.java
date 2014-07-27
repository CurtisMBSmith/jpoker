package ca.sariarra.poker.hand.bettinground;

import static ca.sariarra.poker.player.actions.PlayerAction.BET;
import static ca.sariarra.poker.player.actions.PlayerAction.CHECK;
import static ca.sariarra.poker.player.actions.PlayerAction.FOLD;
import static ca.sariarra.poker.player.actions.PlayerAction.RAISE;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import ca.sariarra.poker.hand.component.HandActionLog;
import ca.sariarra.poker.hand.component.PotManager;
import ca.sariarra.poker.player.actions.AvailableActions;
import ca.sariarra.poker.player.actions.StandardAction;
import ca.sariarra.poker.table.Table;
import ca.sariarra.poker.table.component.BlindLevel;
import ca.sariarra.poker.table.component.Seat;

public abstract class BettingRound implements Runnable {

	protected final Seat[] seats;
	protected final PotManager potMgr;
	protected final HandActionLog log;
	protected final BlindLevel level;
	protected final int roundNum;
	private final long TURN_TIMEOUT;
	private final Table table;
	private final int startSeat;

	protected List<Seat> playerTurns;
	protected Seat currentTurn;

	public BettingRound(final Seat[] seats, final PotManager potMgr, final HandActionLog log,
			final BlindLevel level, final int roundNum, final int startSeat,
			final long turnTimeout, final Table table) {
		this.seats = seats;
		this.potMgr = potMgr;
		this.log = log;
		this.roundNum = roundNum;
		this.TURN_TIMEOUT = turnTimeout;
		this.table = table;
		this.level = level;
		this.startSeat = startSeat;

		this.playerTurns = new LinkedList<Seat>();
		addPlayerTurns(startSeat);
		currentTurn = null;
	}

	public abstract boolean areMoreTurnsPossible();

	public abstract AvailableActions determineAllowableActions(Seat turn);

	@Override
	public void run() {

		AvailableActions actions;
		StandardAction pAction;
		while (!playerTurns.isEmpty()) {
			if (!canMoreThanOnePlayerBet()) {
				break;
			}

			currentTurn = playerTurns.remove(0);

			actions = determineAllowableActions(currentTurn);

			long turnStart = System.currentTimeMillis();
			pAction = currentTurn.getPlayerAction(actions);
			while (!actions.validate(pAction)) {
				if (System.currentTimeMillis() - turnStart > TURN_TIMEOUT) {
					if (potMgr.getUncalledBet(currentTurn) > 0) {
						pAction = new StandardAction(currentTurn.getPlayer(), FOLD);
					}
					else {
						pAction = new StandardAction(currentTurn.getPlayer(), CHECK);
					}

					break;
				}

				pAction = currentTurn.getPlayerAction(actions);

			}

			resolvePlayerAction(currentTurn, pAction);

			if (pAction.getAction() == BET || pAction.getAction() == RAISE) {
				if (areMoreTurnsPossible()) {
					addPlayerTurns();
				}
			}

			table.updatePlayers();
		}
	}

	private void resolvePlayerAction(final Seat seat, final StandardAction action) {
		switch(action.getAction()) {
		case CHECK:
			break;
		case CALL:
		case RAISE:
		case BET:
			potMgr.add(seat, seat.bet(action.getBetAmount()));
			break;
		case FOLD:
			seat.fold();
			break;
		}

		log.appendPlayerAction(action);
	}

	protected void addPlayerTurns() {
		addPlayerTurns(startSeat);
	}

	private void addPlayerTurns(final int startSeat) {
		for (int i = 0; i < seats.length; i++) {
			if (seats[(i + startSeat) % seats.length].isAllIn()
					|| seats[(i + startSeat) % seats.length].isFolded()) {
				continue;
			}

			if (playerTurns.contains(seats[(i + startSeat) % seats.length])) {
				continue;
			}

			if (currentTurn != null) {
				if (seats[(i + startSeat) % seats.length] == currentTurn) {
					continue;
				}
			}

			playerTurns.add(seats[(i + startSeat) % seats.length]);
		}
	}

	private boolean canMoreThanOnePlayerBet() {
		List<Seat> playersThatCanStillBet = new ArrayList<Seat>();
		for (Seat seat : seats) {
			if (seat.isAllIn()) {
				continue;
			}

			if (seat.isFolded()) {
				continue;
			}

			playersThatCanStillBet.add(seat);
		}

		return playersThatCanStillBet.size() > 1;
	}
}
