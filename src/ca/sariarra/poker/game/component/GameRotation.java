package ca.sariarra.poker.game.component;

import static ca.sariarra.util.ParamUtil.notNullOrEmpty;

import java.util.Arrays;
import java.util.List;

import ca.sariarra.poker.game.PokerGame;

public class GameRotation {

	private final List<PokerGame> games;
	private final long msInterval;

	public GameRotation(final long interval, final PokerGame... gameList) {
		notNullOrEmpty("gameList", gameList);

		games = Arrays.asList(gameList);
		msInterval = interval;
	}

	public PokerGame getGame(final long elapsedGameTime) {
		return games.get((int) ((elapsedGameTime / msInterval) % games.size()));
	}
}
