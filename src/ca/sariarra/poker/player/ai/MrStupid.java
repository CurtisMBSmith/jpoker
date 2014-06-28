package ca.sariarra.poker.player.ai;

import java.util.List;

import ca.sariarra.poker.card.Card;
import ca.sariarra.poker.player.Player;
import ca.sariarra.poker.player.actions.Action;
import ca.sariarra.poker.player.actions.AvailableActions;

public class MrStupid extends Player {

	public MrStupid() {
		super("MrStupid");
	}

	public MrStupid(final String name) {
		super(name);
	}

	@Override
	public List<Card> doDiscard(final int discardLimit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Action doAction(final AvailableActions availableActions) {
		// TODO Auto-generated method stub
		return null;
	}

}
