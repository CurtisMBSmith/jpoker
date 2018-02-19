package ca.cmbs.poker.game.component.hand;

import ca.cmbs.poker.game.component.betting.BlindLevel;

import java.time.Duration;
import java.time.Instant;

public class HandStats {

    private final Instant start;
    private final BlindLevel level;
    private final int handNum;
    private final String gameType;

    private HandStats(final Instant start, final BlindLevel level, final int handNum, final String gameType) {
        this.start = start;
        this.level = level;
        this.handNum = handNum;
        this.gameType = gameType;
    }

    public static HandStats create(final Instant start, BlindLevel level, final int handNum, final String gameType) {
        return new HandStats(start, level, handNum, gameType);
    }

    public Duration elapsedTime() {
        return Duration.between(start, Instant.now());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Hand number ");
        sb.append(handNum);
        sb.append(" of game type ");
        sb.append(gameType);
        sb.append(" started at ");
        sb.append(start);
        sb.append(' ');
        sb.append('(');
        sb.append(elapsedTime());
        sb.append(')');
        sb.append(" Blinds: ");
        sb.append(level);

        return sb.toString();
    }

    public int getHandNumber() {
        return handNum;
    }
}
