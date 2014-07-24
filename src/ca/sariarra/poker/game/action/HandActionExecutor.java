package ca.sariarra.poker.game.action;

import ca.sariarra.poker.hand.HandOfPlay;

public interface HandActionExecutor {

	/**
	 * Executes the hand action on the table.
	 *
	 * @param handOfPlay The poker table.
	 */
	public void execute(HandOfPlay handOfPlay);
}
