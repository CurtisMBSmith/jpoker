package ca.cmbs.poker.table;

import ca.cmbs.poker.exception.NoSeatAvailableException;
import ca.cmbs.poker.game.Game;
import ca.cmbs.poker.game.component.betting.BlindLevel;
import ca.cmbs.poker.game.component.hand.HandActionLog;
import ca.cmbs.poker.game.component.hand.HandOfPlay;
import ca.cmbs.poker.player.Player;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


public class Table implements Runnable {
    private final int numSeats;
    private final String name;
    private final Game game;
    private final Seat[] seats;
    private final BlindLevel blindLevel;
    private final HandActionLog actionLog;
    private int handNum;

    public Table(final int numSeats, final String name, final Game game) {
        this.numSeats = numSeats;
        this.name = name;
        this.game = game;
        this.seats = new Seat[numSeats];
        this.handNum = 1;
        this.blindLevel = BlindLevel.create(ChipStack.of(10), ChipStack.of(20));
        this.actionLog = new HandActionLog();
    }


    @Override
    public void run() {
        while (runNextHand()) {
            HandOfPlay hand = game.createHand(playersForHand(), blindLevel, handNum++, actionLog);
            hand.run();
        }
    }

    public List<Seat> playersForHand() {
        return Arrays.stream(seats).filter(Objects::nonNull)
                .filter(seat -> ChipStack.zero().lessThan(seat.getChips()))
                .collect(Collectors.toList());
    }

    public void seatPlayer(final Player player, final ChipStack chips) throws NoSeatAvailableException {
        for (int i = 0; i < seats.length; i++) {
            if (seats[i] == null) {
                seats[i] = new Seat(player, i, chips);
                player.setSeatNumber(seats[i].getSeatNumber());
                return;
            }
        }

        throw new NoSeatAvailableException();
    }

    private boolean runNextHand() {
        return Arrays.stream(seats).filter(seat -> ChipStack.zero().lessThan(seat.getChips())).count() > 1L;
    }
}
