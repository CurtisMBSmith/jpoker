package ca.sariarra.poker.table.component;

import static ca.sariarra.util.ParamUtil.notNullOrEmpty;

import java.util.Arrays;
import java.util.List;

public class BlindLevelManager {

	private final List<BlindLevel> levels;
	private final long msInterval;

	public BlindLevelManager(final long interval, final BlindLevel... levels) {
		notNullOrEmpty("levels", levels);

		this.levels = Arrays.asList(levels);
		msInterval = interval;
	}

	public BlindLevel getLevel(final long elapsedGameTime) {
		int level = (int) (elapsedGameTime / msInterval);
		if (level >= levels.size()) {
			level = levels.size() - 1;
		}
		return levels.get(level);
	}

	public BlindLevel getCurrentLevel(final BlindLevel currentLevel,
			final long elapsedLevelTime) {
		if (currentLevel == null) {
			return getNextLevel(currentLevel);
		}

		if (currentLevel.getDuration() > elapsedLevelTime) {
			return currentLevel;
		}
		else {
			return getNextLevel(currentLevel);
		}
	}

	public BlindLevel getNextLevel(final BlindLevel currentLevel) {
		if (currentLevel == null || !levels.contains(currentLevel)) {
			return levels.size() > 0 ? levels.get(0) : null;
		}

		int currentLvlInd = levels.indexOf(currentLevel);
		if (currentLvlInd == levels.size() - 1) {
			return currentLevel;
		}
		else {
			return levels.get(currentLvlInd + 1);
		}
	}
}
