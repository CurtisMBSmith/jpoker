package ca.cmbs.poker.game.component.hand;

import ca.cmbs.poker.game.component.card.Card;
import ca.cmbs.poker.game.rank.HandRank;
import ca.cmbs.poker.table.ChipStack;
import ca.cmbs.poker.table.PlayerHandSnapshot;

import java.util.List;
import java.util.UUID;
import java.util.function.BiFunction;

public class HandSeatSnapshot {

    private final int holeCards;
    private final List<Card> exposedCards;
    private final int seatNumber;
    private final String playerName;
    private final boolean folded;
    private final ChipStack currentChips;
    private final UUID playerID;
    private final BiFunction<List<Card>, List<Card>, HandRank> rankerFunction;

    private HandSeatSnapshot(final HandSeat handSeat) {
        this.holeCards = handSeat.getHole().size();
        this.exposedCards = handSeat.getExposed();
        this.seatNumber = handSeat.getSeatNumber();
        this.playerName = handSeat.getPlayerName();
        this.folded = handSeat.isFolded();
        this.currentChips = handSeat.getCurrentChips();
        this.playerID = handSeat.getPlayerID();
        this.rankerFunction = handSeat.getRankerFunction();
    }

    public static HandSeatSnapshot create(final HandSeat handSeat) {
        return new HandSeatSnapshot(handSeat);
    }

    public int getSeatNumber() {
        return this.seatNumber;
    }

    @Override
    public String toString() {
        return toStringForPlayer(null);
    }

    public String toStringForPlayer(PlayerHandSnapshot handSnapshot) {
        StringBuilder sb = new StringBuilder();
        sb.append('(');
        sb.append(seatNumber + 1);
        sb.append(") [");
        sb.append(playerName.length() > 20 ? playerName.substring(0, 18) + "..." : playerName);
        sb.append("] ");
        sb.append(currentChips);
        sb.append(" - ");
        sb.append(appendCards(handSnapshot));
        sb.append(' ');
        return sb.toString();
    }

    private String appendCards(PlayerHandSnapshot handSnapshot) {
        if (this.folded) {
            return "[FOLDED]";
        }

        StringBuilder sb = new StringBuilder();
        if (handSnapshot != null) {
            sb.append(handSnapshot.getHoleCards());
        } else {
            sb.append('[');
            for (int i = 0; i < holeCards; i++) {
                sb.append("**");
                if (i != holeCards - 1) {
                    sb.append(", ");
                }
            }
            sb.append("] ");
        }

        if (!exposedCards.isEmpty()) {
            sb.append(exposedCards);
        }

        return sb.toString();
    }

    public UUID getPlayerID() {
        return this.playerID;
    }

    public ChipStack getChips() {
        return currentChips;
    }

    public BiFunction<List<Card>, List<Card>, HandRank> getRankerFunction() {
        return rankerFunction;
    }
}
