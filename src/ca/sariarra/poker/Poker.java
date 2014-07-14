package ca.sariarra.poker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import ca.sariarra.poker.game.NoLimitTexasHoldEm;
import ca.sariarra.poker.player.ai.MrStupid;
import ca.sariarra.poker.player.human.ConsolePlayer;
import ca.sariarra.poker.table.Table;
import ca.sariarra.poker.table.TournamentTable;
import ca.sariarra.poker.table.component.BlindLevel;
import ca.sariarra.poker.table.component.BlindLevels;

public class Poker {

	public static void main(final String[] args) throws InterruptedException {
		BufferedReader br = null;
		br = new BufferedReader(new InputStreamReader(System.in));

		Table table = new TournamentTable(6, new NoLimitTexasHoldEm(getLevels()), 1l, 1);
		table.seatPlayer(new MrStupid("Idiot"), 1500l);
		table.seatPlayer(new MrStupid("Stupid"), 1500l);
		table.seatPlayer(new MrStupid("Moron"), 1500l);
		table.seatPlayer(new MrStupid("Dimwit"), 1500l);
		table.seatPlayer(new MrStupid("Buffoon"), 1500l);
		table.seatPlayer(new ConsolePlayer(br, "Sariarra"), 1500l);

		try {
			table.run();
		}
		finally {
			try {
				if (br != null) {
					br.close();
				}
			} catch (IOException e) {
				System.err.println("Failed to close system.in reader.");
				e.printStackTrace();
			}
		}
	}

	public static BlindLevels getLevels() {
		BlindLevels levels = new BlindLevels(90000l, new BlindLevel(20l, 10l, 0l));
		return levels;
	}
}
