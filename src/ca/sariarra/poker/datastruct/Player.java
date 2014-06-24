package ca.sariarra.poker.datastruct;

import java.util.List;

import ca.sariarra.poker.logic.Action;
import ca.sariarra.poker.logic.AvailableActions;
import ca.sariarra.poker.logic.IPlayer;

public abstract class Player implements IPlayer {

	@Override
	public abstract List<Card> doDiscard(int discardLimit);

	@Override
	public abstract Action doAction(AvailableActions availableActions);
}
