package ca.sariarra.poker.player.actions;

import static ca.sariarra.poker.player.actions.PlayerAction.BET;
import static ca.sariarra.poker.player.actions.PlayerAction.CALL;
import static ca.sariarra.poker.player.actions.PlayerAction.CHECK;
import static ca.sariarra.poker.player.actions.PlayerAction.RAISE;

import java.util.HashSet;
import java.util.Set;

public class AvailableActions {

	private final PlayerAction[] actions;
	private final Set<PlayerAction> actionSet;
	private final Long toCall;
	private final Long minRaise;
	private final Long maxRaise;
	private String errorInPreviousAction;

	public AvailableActions(final PlayerAction[] actions, final Long toCall, final Long minRaise, final Long maxRaise) {
		this.actions = actions;
		this.actionSet = new HashSet<PlayerAction>(actions.length);
		for (PlayerAction act : actions) {
			actionSet.add(act);
		}

		this.toCall = toCall;
		this.minRaise = minRaise;
		this.maxRaise = maxRaise;
		this.errorInPreviousAction = "";
	}

	public PlayerAction[] getActions() {
		return actions;
	}

	public Long getToCall() {
		return toCall;
	}

	public Long getMinRaise() {
		return minRaise;
	}

	public Long getMaxRaise() {
		return maxRaise;
	}

	public String getErrorInPreviousAction() {
		return errorInPreviousAction;
	}

	public boolean validate(final StandardAction pAction) {
		if (!actionSet.contains(pAction.getAction())) {
			errorInPreviousAction = "Action " + pAction.getAction() + " is not valid.";
		}

		// Validate the action itself.
		switch(pAction.getAction()) {
		case CHECK:
			if (toCall != null && toCall > 0) {
				errorInPreviousAction = "Cannot check - Current bet is " + toCall + ".";
				return false;
			}
			return true;
		case BET:
		case RAISE:
			if (toCall == null || toCall == 0) {
				pAction.setAction(BET);

				// Adjust the bet value.
				if (minRaise != null && pAction.getBetAmount() < minRaise) {
					pAction.setBetAmount(minRaise);
				}
				else if (maxRaise != null && pAction.getBetAmount() > maxRaise) {
					pAction.setBetAmount(maxRaise);
				}
				return true;
			}
			else {
				// Adjust the bet value.
				if (pAction.getBetAmount() < toCall) {
					pAction.setAction(CALL);
					pAction.setBetAmount(toCall);
				}
				else if (minRaise != null && pAction.getBetAmount() < minRaise + toCall) {
					pAction.setAction(RAISE);
					pAction.setBetAmount(minRaise + toCall);
				}
				else if (maxRaise != null && pAction.getBetAmount() > maxRaise + toCall) {
					pAction.setAction(RAISE);
					pAction.setBetAmount(maxRaise + toCall);
				}
				else if (pAction.getBetAmount() == toCall){
					pAction.setAction(CALL);
				}

				return true;
			}

		case CALL:
			if (toCall == null || toCall == 0) {
				pAction.setAction(CHECK);
			}
			else {
				pAction.setBetAmount(toCall);
			}
			return true;
		case FOLD:
			if (pAction.getFoldConfirm()) {
				return true;
			}
			errorInPreviousAction = "Are you sure you want to fold?";
			return false;
		default:
			errorInPreviousAction = "Unverifiable action, please try again.";
			return false;
		}
	}
}
