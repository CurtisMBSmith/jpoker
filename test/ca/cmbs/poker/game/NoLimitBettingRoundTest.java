package ca.cmbs.poker.game;

import ca.cmbs.poker.exception.NoSeatAvailableException;
import ca.cmbs.poker.game.action.AvailableActions;
import ca.cmbs.poker.game.component.betting.BettingRounds;
import ca.cmbs.poker.game.component.betting.BlindLevel;
import ca.cmbs.poker.game.component.betting.PotHolder;
import ca.cmbs.poker.game.component.hand.HandActionLog;
import ca.cmbs.poker.game.component.hand.HandSeat;
import ca.cmbs.poker.game.component.hand.HandSnapshot;
import ca.cmbs.poker.player.Player;
import ca.cmbs.poker.player.PlayerAction;
import ca.cmbs.poker.player.PlayerActionType;
import ca.cmbs.poker.table.ChipStack;
import ca.cmbs.poker.table.PlayerHandSnapshot;
import ca.cmbs.poker.table.Table;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NoLimitBettingRoundTest {

    private Table table;
    private PotHolder potHolder;
    private BlindLevel level;

    @BeforeEach
    public void setUp() {
        table = new Table(9, "Test", null);
        level = BlindLevel.create(ChipStack.of(1), ChipStack.of(2));
        potHolder = new PotHolder();
    }

    @Test
    public void headsUp_firstRound_raiseRaiseCall() throws NoSeatAvailableException {
        HandActionLog handActionLog = new HandActionLog();

        table.seatPlayer(new DummyPlayer("Test1", PlayerAction.of(PlayerActionType.RAISE, "10")), ChipStack.of(100));
        table.seatPlayer(new DummyPlayer("Test2", PlayerAction.of(PlayerActionType.RAISE, "6"), PlayerAction.of(PlayerActionType.CALL)), ChipStack.of(100));


        List<HandSeat> seats = table.playersForHand().stream()
                .map(seat -> HandSeat.create(seat, Collections.emptyList(), Observable.empty(), Game.noLimitTexasHoldem()::rankHand))
                .collect(Collectors.toList());

        potHolder.add(seats.get(1).wager(level.getSmallBlind()));
        potHolder.add(seats.get(0).wager(level.getBigBlind()));

        assertEquals(ChipStack.of(3), potHolder.getTotalSize());

        BettingRounds.noLimitBettingRound(seats, potHolder, handActionLog, level, (seatNum) -> {
        });

        assertAll(
                () -> assertEquals(ChipStack.of(24), potHolder.getTotalSize()),
                () -> assertFalse(seats.get(0).isFolded()),
                () -> assertFalse(seats.get(0).isAllIn()),
                () -> assertEquals(ChipStack.of(88), seats.get(0).getCurrentChips()),
                () -> assertFalse(seats.get(1).isFolded()),
                () -> assertFalse(seats.get(1).isAllIn()),
                () -> assertEquals(ChipStack.of(88), seats.get(1).getCurrentChips())
        );

    }

    @Test
    public void onePlayerLeft() throws NoSeatAvailableException {
        HandActionLog handActionLog = new HandActionLog();

        // Set up some actions, but since all players are folded/all-in already,
        // no actions should be executed.
        table.seatPlayer(new DummyPlayer("Test1", PlayerAction.of(PlayerActionType.CALL)), ChipStack.of(10));
        table.seatPlayer(new DummyPlayer("Test2", PlayerAction.of(PlayerActionType.BET, "2")), ChipStack.of(2));
        table.seatPlayer(new DummyPlayer("Test3", PlayerAction.of(PlayerActionType.RAISE, "6"), PlayerAction.of(PlayerActionType.CALL)), ChipStack.of(10));
        table.seatPlayer(new DummyPlayer("Test4", PlayerAction.of(PlayerActionType.FOLD)), ChipStack.of(10));

        List<HandSeat> seats = table.playersForHand().stream()
                .map(seat -> HandSeat.create(seat, Collections.emptyList(), Observable.empty(), Game.noLimitTexasHoldem()::rankHand))
                .collect(Collectors.toList());

        potHolder.add(seats.get(0).wager(level.getSmallBlind()));
        potHolder.add(seats.get(1).wager(level.getBigBlind()));
        seats.get(0).fold();
        seats.get(2).fold();
        potHolder.add(seats.get(3).wager(ChipStack.of(2)));

        assertEquals(ChipStack.of(5), potHolder.getTotalSize());

        BettingRounds.noLimitBettingRound(seats, potHolder, handActionLog, level, (seatNum) -> {
        });

        assertAll(
                () -> assertEquals(ChipStack.of(5), potHolder.getTotalSize()),
                () -> assertTrue(seats.get(0).isFolded()),
                () -> assertFalse(seats.get(0).isAllIn()),
                () -> assertEquals(ChipStack.of(9), seats.get(0).getCurrentChips()),
                () -> assertFalse(seats.get(1).isFolded()),
                () -> assertTrue(seats.get(1).isAllIn()),
                () -> assertEquals(ChipStack.zero(), seats.get(1).getCurrentChips()),
                () -> assertTrue(seats.get(2).isFolded()),
                () -> assertFalse(seats.get(2).isAllIn()),
                () -> assertEquals(ChipStack.of(10), seats.get(2).getCurrentChips()),
                () -> assertFalse(seats.get(3).isFolded()),
                () -> assertFalse(seats.get(3).isAllIn()),
                () -> assertEquals(ChipStack.of(8), seats.get(3).getCurrentChips())
        );
    }

    private class DummyPlayer extends Player {
        private final BehaviorSubject<PlayerAction> actions;
        private final List<PlayerAction> actionList;
        private int actionInd;

        DummyPlayer(final String name, final PlayerAction... actions) {
            super(name);
            this.actions = BehaviorSubject.create();
            this.actionList = Arrays.asList(actions);
            this.actionInd = 0;
        }

        @Override
        protected void handleHandSnapshot(final PlayerHandSnapshot handSnapshot) {
        }

        @Override
        protected void promptForAction(final AvailableActions handSnapshot) {
            this.actions.onNext(actionList.get(actionInd++));
        }

        @Override
        protected void handleGameHandSnapshot(final HandSnapshot handSnapshot) {
        }

        @Override
        public Observable<PlayerAction> playerActionStream() {
            return actions;
        }
    }
}
