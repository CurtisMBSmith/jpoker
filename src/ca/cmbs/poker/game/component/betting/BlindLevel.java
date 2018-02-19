package ca.cmbs.poker.game.component.betting;

import ca.cmbs.poker.table.ChipStack;

public class BlindLevel {

    private final ChipStack smallBlind;
    private final ChipStack bigBlind;

    private final ChipStack ante;

    public BlindLevel(final ChipStack smallBlind, final ChipStack bigBlind, final ChipStack ante) {
        this.smallBlind = smallBlind;
        this.bigBlind = bigBlind;
        this.ante = ante;
    }

    public static BlindLevel create(final ChipStack smallBlind, final ChipStack bigBlind) {
        return create(smallBlind, bigBlind, null);
    }

    public static BlindLevel create(final ChipStack smallBlind, final ChipStack bigBlind, final ChipStack ante) {
        return new BlindLevel(smallBlind, bigBlind, ante);
    }

    public ChipStack getSmallBlind() {
        return smallBlind;
    }

    public ChipStack getBigBlind() {
        return bigBlind;
    }

    public ChipStack getAnte() {
        return ante;
    }

    public boolean hasAnte() {
        return ante != null;
    }

    @Override
    public String toString() {
        return "Small: " + smallBlind + ", Big: " + bigBlind + (ante != null ? " Ante: " + ante : "");
    }
}
