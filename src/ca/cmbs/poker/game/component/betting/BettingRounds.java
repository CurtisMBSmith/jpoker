package ca.cmbs.poker.game.component.betting;

import ca.cmbs.poker.game.action.AvailableActions;
import ca.cmbs.poker.game.action.ValidatedAction;
import ca.cmbs.poker.game.component.hand.HandActionLog;
import ca.cmbs.poker.game.component.hand.HandSeat;
import ca.cmbs.poker.player.PlayerActionType;
import ca.cmbs.poker.table.ChipStack;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;

import static ca.cmbs.poker.player.PlayerActionType.CHECK;

public class BettingRounds {
    private static final long TURN_TIMEOUT_MILLIS = 2000000L;

    private BettingRounds() {
        throw new RuntimeException("Do not instantiate");
    }

    public static void noLimitBettingRound(final List<HandSeat> seats, final PotHolder potHolder, final HandActionLog handActionLog,
                                           final BlindLevel level, final Consumer<Integer> tableUpdateConsumer) {
        int startPosition = determineStartPosition(seats, potHolder);
        int numTurns = 0;

        for (int i = startPosition; canAnyPlayersAct(seats, potHolder) && (atLeastOnePlayerToCall(seats, potHolder) || numTurns < seats.size()); i = (i + 1) % seats.size()) {

            numTurns++;
            HandSeat currentTurn = seats.get(i);
            if (currentTurn.isFolded() || currentTurn.isAllIn()) {
                continue;
            }

            tableUpdateConsumer.accept(currentTurn.getSeatNumber());

            AvailableActions actions;
            if (potHolder.getUncalledBet(currentTurn).greaterThan(ChipStack.zero())) {
                actions = new AvailableActions(potHolder.getUncalledBet(currentTurn), level.getBigBlind(), null,
                        PlayerActionType.CALL, PlayerActionType.RAISE, PlayerActionType.FOLD);
            } else {
                actions = new AvailableActions(ChipStack.zero(), level.getBigBlind(), null,
                        CHECK, PlayerActionType.BET, PlayerActionType.FOLD);
            }

            ValidatedAction action;
            try {
                action = getPlayerAction(currentTurn, actions);
            } catch (NoSuchElementException | TimeoutException e) {
                handActionLog.log(String.format("Player %s timed out.", currentTurn.getPlayerName()));
                action = ValidatedAction.of(actions.getDefaultAction());
            }
            resolveAction(action, currentTurn, potHolder, handActionLog);
        }

        potHolder.returnUncalledBet();
    }


    private static ValidatedAction getPlayerAction(final HandSeat currentTurn, final AvailableActions availableActions) throws NoSuchElementException, TimeoutException {
        Instant startOfHand = Instant.now();
        ValidatedAction action = ValidatedAction.invalid();
        while (!action.isValid()) {
            currentTurn.relayAvailableActions(availableActions);
            Instant now = Instant.now();
            long timeoutRemaining = (TURN_TIMEOUT_MILLIS - Duration.between(startOfHand, now).toMillis()) > 0L ?
                    TURN_TIMEOUT_MILLIS - Duration.between(startOfHand, now).toMillis() : 1L;
            action = availableActions.validate(currentTurn.getPlayerActionObservable().timeout(timeoutRemaining, TimeUnit.MILLISECONDS).blockingFirst());
            if (action.isValid()) {
                return action;
            }
        }

        return ValidatedAction.of(availableActions.getDefaultAction());
    }

    private static void resolveAction(final ValidatedAction action, final HandSeat currentTurn, final PotHolder potHolder, final HandActionLog actionLog) {
        if (!action.isValid()) {
            throw new IllegalArgumentException("This method should not be called with an invalid action.");
        }

        switch (action.getActionType()) {
            case CHECK:
                actionLog.log(currentTurn.getPlayerName() + " checks.");
                break;
            case CALL:
            case RAISE:
            case BET:
                String verb = mapActionTypeToString(action.getActionType());
                Wager wager = currentTurn.wager(action.getAmount());
                potHolder.add(wager);
                actionLog.log(currentTurn.getPlayerName() + verb + wager.getAmount() + (wager.isAllIn() ? " and is all in." : "."));
                break;
            case FOLD:
                currentTurn.fold();
                actionLog.log(currentTurn.getPlayerName() + mapActionTypeToString(action.getActionType()) + ".");
                break;
        }
    }

    private static boolean canAnyPlayersAct(final List<HandSeat> seatList, final PotHolder pots) {
        return seatList.stream().filter(seat -> !seat.isAllIn() && !seat.isFolded()).count() > 1;
    }

    private static boolean atLeastOnePlayerToCall(final List<HandSeat> seats, final PotHolder potHolder) {
        return seats.stream().filter(seat -> !seat.isAllIn() && !seat.isFolded())
                .filter(seat -> potHolder.getUncalledBet(seat).greaterThan(ChipStack.zero()))
                .count() > 0;
    }

    private static int determineStartPosition(final List<HandSeat> seatList, final PotHolder pots) {
        for (int i = 0; i < seatList.size(); i++) {
            if (pots.getUncalledBet(seatList.get(i)).equals(ChipStack.zero())) {
                return (i + 1) % seatList.size();
            }
        }

        return 0;
    }

    private static String mapActionTypeToString(final PlayerActionType actionType) {
        switch (actionType) {
            case BET:
                return " bets ";
            case RAISE:
                return " raises ";
            case CALL:
                return " calls ";
            case FOLD:
                return " folds";
            case CHECK:
            default:
                return " checks";
            case DISCARD:
                return " discards ";
        }
    }
}
