package ca.sariarra.poker.game.action;

import ca.sariarra.poker.table.Table;

public interface HandActionExecutor {

	/**
	 * Executes the hand action on the table.
	 *
	 * @param table The poker table.
	 */
	public void execute(Table table);
}
