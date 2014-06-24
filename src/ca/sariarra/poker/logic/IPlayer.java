package ca.sariarra.poker.logic;

import java.util.List;

import ca.sariarra.poker.datastruct.Card;

public interface IPlayer {

	public List<Card> doDiscard(int discardLimit);

	public Action doAction(AvailableActions availableActions);
}
