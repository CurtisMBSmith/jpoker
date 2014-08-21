package ca.sariarra.poker.table.component;

import java.util.Date;

public class BreakManager {

	private final long breakDuration;
	private final long breakInterval;
	private Date breakStart;

	public BreakManager(final long breakInterval, final long breakDuration) {
		this.breakInterval =  breakInterval;
		this.breakDuration = breakDuration;
	}

	/**
	 * Starts the break if the {@code millisecondsElapsedSinceLastBreak} is greater than, or equal to the
	 * {@link breakInterval} field. It also sets the {@link breakStart} field which will be checked to see if the break
	 * has ended.
	 *
	 * @param millisecondsElapsedSinceLastBreak The number of milliseconds that have elapsed since the last break. Note
	 * that it is up to the caller to keep track of the time when play begins/resumes.
	 * @return The {@link java.util.Date} representing the start of the break or null if it is not time for the break
	 * to start yet.
	 */
	public Date startBreak(final long millisecondsElapsedSinceLastBreak) {
		if (breakStart != null) {
			return breakStart;
		}

		if (millisecondsElapsedSinceLastBreak >= breakInterval) {
			breakStart = new Date();
		}

		return breakStart;
	}

	/**
	 * Stop the currently running break. This will return a {@link java.util.Date} object representing the time that
	 * the break ended if stopping the break was successful. If the break was not stopped for some reason (ie. there
	 * was time remaining and it wasn't forced to end, or no break was running), {@code null} will be returned.
	 *
	 * @param forceStop If true, the break will be ended (if one is currently running) regardless of how much time is
	 * left in the break.
	 * @return The {@link java.util.Date} representing the point in time that the break ended, or {@code null} if there
	 * was no break to stop.
	 */
	public Date stopBreak(final boolean forceStop) {
		if (breakStart == null) {
			return null;
		}

		Date currentTime = new Date();

		if (currentTime.getTime() - breakStart.getTime() > breakDuration || forceStop) {
			breakStart = null;
			return currentTime;
		}

		return null;
	}

	/**************************************************************************
	 * ACCESSORS
	 **************************************************************************/

	public long getBreakDuration() {
		return breakDuration;
	}

	public long getBreakInterval() {
		return breakInterval;
	}
}
