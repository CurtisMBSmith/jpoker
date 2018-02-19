package ca.cmbs.poker.player;

import ca.cmbs.poker.game.action.AvailableActions;
import ca.cmbs.poker.game.component.hand.HandSnapshot;
import ca.cmbs.poker.table.PlayerHandSnapshot;
import io.reactivex.Observable;

import java.util.UUID;

public abstract class Player {

    private final String name;
    private final UUID id;
    protected int seatNumber;

    protected Player(final String name) {
        this.name = name;
        this.id = UUID.randomUUID();
        this.seatNumber = -1;
    }

    public Observable<PlayerAction> actions() {
        return null;
    }

    public void setUpHandSnapshots(final Observable<PlayerHandSnapshot> handSnapshotObservable) {
        handSnapshotObservable.subscribe(this::handleHandSnapshot);
    }

    public void setUpActionPipeline(final Observable<AvailableActions> availableActionObservable) {
        availableActionObservable.subscribe(this::promptForAction);
    }

    public void subscribeToHandSnapshotPipeline(final Observable<HandSnapshot> handSnapshotObservable) {
        handSnapshotObservable.subscribe(this::handleGameHandSnapshot);
    }

    protected abstract void handleHandSnapshot(final PlayerHandSnapshot handSnapshot);

    protected abstract void promptForAction(final AvailableActions handSnapshot);

    protected abstract void handleGameHandSnapshot(final HandSnapshot handSnapshot);

    public abstract Observable<PlayerAction> playerActionStream();

    public String getName() {
        return name;
    }

    public UUID getID() {
        return this.id;
    }

    public void setSeatNumber(final int seatNumber) {
        this.seatNumber = seatNumber;
    }
}
