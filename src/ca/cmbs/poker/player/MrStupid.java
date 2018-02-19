package ca.cmbs.poker.player;

import ca.cmbs.poker.game.action.AvailableActions;
import ca.cmbs.poker.game.component.hand.HandSeatSnapshot;
import ca.cmbs.poker.game.component.hand.HandSnapshot;
import ca.cmbs.poker.table.ChipStack;
import ca.cmbs.poker.table.PlayerHandSnapshot;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

import java.util.LinkedList;
import java.util.Random;

import static ca.cmbs.poker.player.PlayerActionType.BET;
import static ca.cmbs.poker.player.PlayerActionType.RAISE;

public class MrStupid extends Player {
    private BehaviorSubject<PlayerAction> actionStream;
    private PlayerHandSnapshot currentHand;
    private ChipStack currentChips;
    private int seatNumber;
    private HandSnapshot currentHandSnapshot;

    protected MrStupid(final String name) {
        super(name);
        this.actionStream = BehaviorSubject.create();
    }

    public static MrStupid create(final String name) {
        return new MrStupid(name);
    }

    @Override
    protected void handleHandSnapshot(final PlayerHandSnapshot handSnapshot) {
        this.currentHand = handSnapshot;
    }

    @Override
    protected void promptForAction(final AvailableActions availableActions) {
        Random rand = new Random();

        int result = rand.nextInt(availableActions.getActions().size());
        PlayerActionType action = new LinkedList<>(availableActions.getActions()).get(result);

        PlayerAction playerAction;
        if (action == BET || action == RAISE) {
            ChipStack randomBet = getRandomBet(availableActions.getMinRaise(), availableActions.getMaxRaise());
            playerAction = PlayerAction.of(action, String.valueOf(randomBet.doubleValue()));
        } else {
            playerAction = PlayerAction.of(action);
        }

        actionStream.onNext(playerAction);
    }

    private ChipStack getRandomBet(final ChipStack minRaise, final ChipStack maxRaise) {
        Random rand = new Random();
        double maximum = maxRaise != null && maxRaise.lessThan(currentChips) ? maxRaise.doubleValue() : currentChips.doubleValue();
        double amount = rand.nextDouble() * maximum;

        if (minRaise != null && minRaise.doubleValue() > amount) {
            amount = minRaise.doubleValue();
        }

        return ChipStack.of(amount);
    }

    @Override
    protected void handleGameHandSnapshot(final HandSnapshot handSnapshot) {
        currentChips = handSnapshot.getHandSeatSnapshots().stream()
                .filter(seat -> seat.getSeatNumber() == this.seatNumber)
                .findFirst()
                .map(HandSeatSnapshot::getChips)
                .orElse(ChipStack.zero());
        this.currentHandSnapshot = handSnapshot;
    }

    @Override
    public Observable<PlayerAction> playerActionStream() {
        return actionStream;
    }
}
