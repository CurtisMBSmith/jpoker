package ca.sariarra.poker;

import ca.sariarra.poker.game.NoLimitTexasHoldEm;
import ca.sariarra.poker.player.ai.MrStupid;
import ca.sariarra.poker.player.human.ConsolePlayer;
import ca.sariarra.poker.table.Table;
import ca.sariarra.poker.table.TournamentTable;
import ca.sariarra.poker.table.component.BlindLevel;
import ca.sariarra.poker.table.component.BlindLevels;

public class Poker {

	public static void main(final String[] args) throws InterruptedException {

		Table table = new TournamentTable(6, new NoLimitTexasHoldEm(getLevels()), 1l, 1);
		table.seatPlayer(new MrStupid("Idiot"), 1500l);
		table.seatPlayer(new MrStupid("Stupid"), 1500l);
		table.seatPlayer(new MrStupid("Moron"), 1500l);
		table.seatPlayer(new MrStupid("Dimwit"), 1500l);
		table.seatPlayer(new MrStupid("Buffoon"), 1500l);
		table.seatPlayer(new ConsolePlayer("Sariarra"), 1500l);

		table.run();
	}

	public static BlindLevels getLevels() {
		BlindLevels levels = new BlindLevels(90000l, new BlindLevel(20l, 10l, 0l));
		return levels;
	}
}
