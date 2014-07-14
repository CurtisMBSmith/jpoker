package ca.sariarra.poker.view.table.component;

import java.util.ArrayList;
import java.util.List;

import ca.sariarra.poker.game.action.HandAction;
import ca.sariarra.poker.table.component.HandActionLog;
import ca.sariarra.poker.table.component.HandActionLog.ActionWrapper;
import ca.sariarra.poker.view.player.action.ActionView;

public class HandActionLogView {

	private final List<ActionWrapperView> actions;

	public HandActionLogView(final HandActionLog handActionLog) {
		actions = new ArrayList<ActionWrapperView>(handActionLog.getActions().size());
		for (ActionWrapper wrapper : handActionLog.getActions()) {
			actions.add(new ActionWrapperView(wrapper));
		}
	}

	public List<ActionWrapperView> getActions() {
		return actions;
	}

	public class ActionWrapperView {

		private final ActionView playerAction;
		private final HandAction handAction;

		private ActionWrapperView(final ActionWrapper actionWrapper) {
			this.playerAction = actionWrapper.getAction() != null ? new ActionView(actionWrapper.getAction()) : null;
			this.handAction = actionWrapper.getHandAction();
		}

		public ActionView getPlayerAction() {
			return playerAction;
		}

		public HandAction getHandAction() {
			return handAction;
		}

		@Override
		public String toString() {
			return playerAction != null ? playerAction.toString() : handAction.toString();
		}
	}
}
