package ca.sariarra.poker.logic;

import static ca.sariarra.poker.logic.PlayerAction.BET;
import static ca.sariarra.poker.logic.PlayerAction.RAISE;

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

	public boolean validate(final Action pAction) {
		if (!actionSet.contains(pAction.getAction())) {
			errorInPreviousAction = "Action " + pAction.getAction() + " is not valid.";
		}

		if (toCall != null && toCall > 0) {
			if (pAction.getAction() == BET || pAction.getAction() == RAISE) {
				if (pAction.getBetAmount() < toCall) {
					pAction.setBetAmount(toCall);
					return true;
				}
			}


		}
		// TODO Auto-generated method stub
		return false;
	}
}
