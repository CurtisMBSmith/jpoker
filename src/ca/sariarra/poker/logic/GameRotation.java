package ca.sariarra.poker.logic;

import static ca.sariarra.util.ParamUtil.ensureNotNullOrEmpty;

import java.util.Arrays;
import java.util.List;

public class GameRotation {

	private final List<PokerGame> games;
	private final long msInterval;

	public GameRotation(final long interval, final PokerGame... gameList) {
		ensureNotNullOrEmpty("gameList", gameList);

		games = Arrays.asList(gameList);
		msInterval = interval;
	}

	public PokerGame getGame(final long elapsedGameTime) {
		return games.get((int) ((elapsedGameTime / msInterval) % games.size()));
	}
}
