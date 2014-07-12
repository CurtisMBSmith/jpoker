package ca.sariarra.poker.table.component;

import java.util.ArrayList;
import java.util.List;

import ca.sariarra.poker.game.action.HandAction;
import ca.sariarra.poker.player.actions.Action;

public class HandActionLog {

	List<ActionWrapper> actions;

	public HandActionLog() {
		actions = new ArrayList<ActionWrapper>();
	}

	public void appendPlayerAction(final Action act) {
		actions.add(new ActionWrapper(act));
	}

	public void appendHandAction(final HandAction act) {
		actions.add(new ActionWrapper(act));
	}

	public List<ActionWrapper> getActions() {
		return actions;
	}

	public class ActionWrapper {

		private final Action action;
		private final HandAction handAction;

		private ActionWrapper(final Action action) {
			this.action = action;
			this.handAction = null;
		}

		private ActionWrapper(final HandAction action) {
			this.action = null;
			this.handAction = action;
		}

		public Action getAction() {
			return action;
		}

		public HandAction getHandAction() {
			return handAction;
		}

		@Override
		public String toString() {
			return action != null ? action.toString() : handAction.toString();
		}
	}
}
