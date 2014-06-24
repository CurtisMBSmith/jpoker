package ca.sariarra.poker.logic;

import ca.sariarra.poker.datastruct.Table;

public interface HandActionExecutor {

	/**
	 * Executes the hand action on the table.
	 *
	 * @param table The poker table.
	 */
	public void execute(Table table);
}
