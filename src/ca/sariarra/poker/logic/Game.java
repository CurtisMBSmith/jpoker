package ca.sariarra.poker.logic;

import ca.sariarra.poker.datastruct.Table;


public abstract class Game {
	private Table table;
	private boolean isCashGame;
	private int pauseLength;
	private boolean paused;
	
	public abstract void dealCards();
	
	public abstract boolean bettingRound(int roundNum);
	
	public abstract void runHand();
	
	public abstract void rankHands();
	
	public void runGame() throws InterruptedException {
		while (!hasGameCompleted()) {
			if (paused) {
				if (pauseLength > 0) {
					Thread.sleep(pauseLength);
				}
				else {
					Thread.sleep(500);
				}
			}
			else {
				runHand();
			}
		}
	}
	
	public boolean hasGameCompleted() {
		if (table.isClosed()) {
			return true;
		}
		
		if (isCashGame) {
			return false;
		}
		
		if (table.playersRemaining() == 1) {
			return true;
		}
		else {
			return false;
		}
	}
}
