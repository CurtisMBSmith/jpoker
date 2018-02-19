package ca.cmbs.poker.game.component.hand;

import ca.cmbs.poker.game.component.card.Card;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class HandSnapshot {

    private final List<HandSeatSnapshot> handSeatSnapshots;
    private final List<Card> communityCards;
    private final HandStats handStats;
    private final List<String> log;
    private final int seatNumber;

    private HandSnapshot(final HandOfPlay hand) {
        this(hand, -1);
    }

    public HandSnapshot(final HandOfPlay hand, final int seatNumber) {
        this.handSeatSnapshots = hand.getSeats().stream().map(HandSeatSnapshot::create).collect(Collectors.toList());
        this.communityCards = new LinkedList<>(hand.getCommunityCards());
        this.handStats = hand.getHandStats();
        this.log = hand.getLog();
        this.seatNumber = seatNumber;
    }

    public static HandSnapshot create(final HandOfPlay hand) {
        return new HandSnapshot(hand);
    }

    public static HandSnapshot create(final HandOfPlay newHand, final int seatNumber) {
        return new HandSnapshot(newHand, seatNumber);
    }

    public List<HandSeatSnapshot> getHandSeatSnapshots() {
        return handSeatSnapshots;
    }

    public List<Card> getCommunityCards() {
        return communityCards;
    }

    public HandStats getHandStats() {
        return handStats;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public String getActionsSince(final int startIndex) {
        List<String> subList = startIndex < 0 ? this.log : this.log.subList(startIndex, this.log.size());
        return subList.stream().collect(Collectors.joining("\n"));
    }

    public int getActionLogSize() {
        return this.log.size();
    }
}
