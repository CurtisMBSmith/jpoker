package ca.cmbs.poker.game.component.hand;

import ca.cmbs.poker.game.action.AvailableActions;
import ca.cmbs.poker.game.component.betting.Wager;
import ca.cmbs.poker.game.component.card.Card;
import ca.cmbs.poker.game.rank.HandRank;
import ca.cmbs.poker.player.PlayerAction;
import ca.cmbs.poker.table.ChipStack;
import ca.cmbs.poker.table.PlayerHandSnapshot;
import ca.cmbs.poker.table.Seat;
import io.reactivex.Observable;
import io.reactivex.subjects.ReplaySubject;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.function.BiFunction;

public class HandSeat {

    private final Seat seat;
    private final BiFunction<List<Card>, List<Card>, HandRank> rankerFunction;
    private final ChipStack startingChips;
    private final List<Card> holeCards;
    private final List<Card> exposedCards;
    private final List<Card> communityCards;
    private final ReplaySubject<PlayerHandSnapshot> playerHandSnapshotObservable;
    private final ReplaySubject<AvailableActions> availableActionsObservable;
    private final Observable<PlayerAction> playerActionObservable;

    private boolean folded;
    private ChipStack currentChips;
    private boolean allIn;

    private HandSeat(final Seat seat, final List<Card> communityCards, final Observable<HandSnapshot> handSnapshotObservable,
                     final BiFunction<List<Card>, List<Card>, HandRank> rankerFunction) {
        this.seat = seat;
        this.rankerFunction = rankerFunction;
        this.holeCards = new LinkedList<>();
        this.exposedCards = new LinkedList<>();
        this.playerHandSnapshotObservable = ReplaySubject.create();
        this.availableActionsObservable = ReplaySubject.create();
        this.communityCards = communityCards;
        this.currentChips = seat.getChips();
        this.startingChips = seat.getChips();
        this.folded = false;
        this.playerActionObservable = seat.getPlayer().playerActionStream();

        seat.getPlayer().setUpHandSnapshots(playerHandSnapshotObservable);
        seat.getPlayer().subscribeToHandSnapshotPipeline(handSnapshotObservable);
        seat.getPlayer().setUpActionPipeline(availableActionsObservable);
    }

    public static HandSeat create(final Seat seat, final List<Card> communityCards, final Observable<HandSnapshot> handSnapshotObservable,
                                  final BiFunction<List<Card>, List<Card>, HandRank> rankerFunction) {
        return new HandSeat(seat, communityCards, handSnapshotObservable, rankerFunction);
    }

    public void addHole(final Card card) {
        if (folded) {
            return;
        }

        this.holeCards.add(card);
        this.playerHandSnapshotObservable.onNext(PlayerHandSnapshot.takeSnapshot(holeCards, exposedCards, communityCards));
    }

    public void addExposed(final Card card) {
        if (folded) {
            return;
        }

        this.exposedCards.add(card);
        this.playerHandSnapshotObservable.onNext(PlayerHandSnapshot.takeSnapshot(holeCards, exposedCards, communityCards));
    }

    public List<Card> getHole() {
        return this.holeCards;
    }

    public List<Card> getExposed() {
        return this.exposedCards;
    }

    public int getSeatNumber() {
        return seat.getSeatNumber();
    }

    public String getPlayerName() {
        return this.seat.getPlayer().getName();
    }

    public boolean isFolded() {
        return folded;
    }

    public ChipStack getCurrentChips() {
        return this.currentChips;
    }

    public UUID getPlayerID() {
        return this.seat.getPlayer().getID();
    }

    public Wager wager(final ChipStack amount) {
        if (currentChips.lessThan(amount)) {
            currentChips = ChipStack.zero();
            return Wager.create(currentChips, this);
        } else {
            currentChips = currentChips.remove(amount);
            return Wager.create(amount, this);
        }
    }

    public void addChips(final ChipStack chips) {
        this.currentChips = this.currentChips.add(chips);
    }

    public boolean isAllIn() {
        return this.currentChips.equals(ChipStack.zero());
    }

    public void fold() {
        this.folded = true;
    }

    public Observable<PlayerAction> getPlayerActionObservable() {
        return this.playerActionObservable;
    }

    public void relayAvailableActions(final AvailableActions actions) {
        this.availableActionsObservable.onNext(actions);
    }

    public List<Card> getCommunityCards() {
        return new LinkedList<>(communityCards);
    }

    public HandRank getHandRanking() {
        return rankerFunction.apply(holeCards, communityCards);
    }

    public void adjustSeatChips() {
        seat.adjustChips(this.currentChips.difference(this.startingChips));
    }

    public BiFunction<List<Card>, List<Card>, HandRank> getRankerFunction() {
        return rankerFunction;
    }
}
