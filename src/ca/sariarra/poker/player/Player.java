package ca.sariarra.poker.player;

import java.util.List;

import ca.sariarra.poker.card.Card;
import ca.sariarra.poker.player.actions.Action;
import ca.sariarra.poker.player.actions.AvailableActions;

public abstract class Player implements IPlayer {

	@Override
	public abstract List<Card> doDiscard(int discardLimit);

	@Override
	public abstract Action doAction(AvailableActions availableActions);
}
