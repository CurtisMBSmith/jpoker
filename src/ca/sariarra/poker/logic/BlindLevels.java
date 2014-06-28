package ca.sariarra.poker.logic;

import static ca.sariarra.util.ParamUtil.notNullOrEmpty;

import java.util.Arrays;
import java.util.List;

import ca.sariarra.poker.datastruct.BlindLevel;

public class BlindLevels {

	private final List<BlindLevel> levels;
	private final long msInterval;

	public BlindLevels(final long interval, final BlindLevel... pLevels) {
		notNullOrEmpty("pLevels", pLevels);

		levels = Arrays.asList(pLevels);
		msInterval = interval;
	}

	public BlindLevel getLevel(final long elapsedGameTime) {
		int level = (int) (elapsedGameTime / msInterval);
		if (level >= levels.size()) {
			level = levels.size() - 1;
		}
		return levels.get(level);
	}
}
