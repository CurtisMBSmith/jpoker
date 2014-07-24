package ca.sariarra.poker.hand;

import java.util.LinkedList;
import java.util.List;

import ca.sariarra.poker.game.action.HandAction;
import ca.sariarra.poker.player.actions.Action;

public class HandActionLog {

	List<HandRound> actions;

	public HandActionLog() {
		actions = new LinkedList<HandRound>();
	}

	public HandRound getCurrentRoundActions() {
		if (actions.size() == 0) {
			return null;
		}
		else {
			return actions.get(actions.size() - 1);
		}
	}

	public void appendPlayerAction(final Action action) {
		if (actions.size() > 0) {
			actions.get(actions.size() - 1).addAction(action);
		}
	}

	public void appendHandAction(final HandAction act) {
		actions.add(new HandRound(act));
	}

	public List<HandRound> getActions() {
		return actions;
	}

	public class HandRound {
		private final HandAction roundAction;
		private final List<Action> actionsThisRound;

		private HandRound(final HandAction act) {
			roundAction = act;
			actionsThisRound = new LinkedList<Action>();
		}

		private void addAction(final Action action) {
			actionsThisRound.add(action);
		}

		public List<Action> getActions() {
			return actionsThisRound;
		}

		public HandAction getRoundAction() {
			return roundAction;
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();

			sb.append("********** " + roundAction.toString() + " **********\n");
			for (Action act : actionsThisRound) {
				sb.append(act.toString());
				sb.append('\n');
			}

			return sb.toString();
		}
	}

	private class ActionWrapper {


	}
}
