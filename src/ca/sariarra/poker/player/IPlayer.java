package ca.sariarra.poker.player;

import java.util.List;

import ca.sariarra.poker.card.Card;
import ca.sariarra.poker.player.actions.Action;
import ca.sariarra.poker.player.actions.AvailableActions;

public interface IPlayer {

	public List<Card> doDiscard(int discardLimit);

	public Action doAction(AvailableActions availableActions);
}
