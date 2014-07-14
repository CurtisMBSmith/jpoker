package ca.sariarra.poker.player;

import java.util.List;

import ca.sariarra.poker.card.Card;
import ca.sariarra.poker.player.actions.AvailableActions;
import ca.sariarra.poker.player.actions.StandardAction;
import ca.sariarra.poker.view.table.TableView;

public abstract class Player implements IPlayer {

	private final String name;

	protected Player(final String name) {
		this.name = name;
	}

	@Override
	public abstract List<Card> doDiscard(int discardLimit);

	@Override
	public abstract StandardAction doAction(AvailableActions availableActions);

	public abstract void updateTableView(TableView view);

	public String getName() {
		return name;
	}
}
