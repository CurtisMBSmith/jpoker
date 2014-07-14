package ca.sariarra.poker.player;

import java.util.List;

import ca.sariarra.poker.card.Card;
import ca.sariarra.poker.player.actions.AvailableActions;
import ca.sariarra.poker.player.actions.StandardAction;

public interface IPlayer {

	public List<Card> doDiscard(int discardLimit);

	public StandardAction doAction(AvailableActions availableActions);
}
