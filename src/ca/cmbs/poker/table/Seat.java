package ca.cmbs.poker.table;

import ca.cmbs.poker.player.Player;

public class Seat {

    private final Player player;
    private final int seatNumber;
    private ChipStack chips;

    public Seat(final Player player, final int seatNumber, final ChipStack chips) {
        this.player = player;
        this.seatNumber = seatNumber;
        this.chips = chips;
    }

    public void completeHand() {
        // TODO
    }

    public Player getPlayer() {
        return this.player;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public ChipStack getChips() {
        return chips;
    }

    public void adjustChips(final ChipStack delta) {
        this.chips = this.chips.add(delta);
    }
}
