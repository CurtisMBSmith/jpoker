package ca.cmbs.poker.game.component.hand;

import ca.cmbs.poker.game.Game;
import ca.cmbs.poker.game.action.HandAction;
import ca.cmbs.poker.game.component.betting.BettingRounds;
import ca.cmbs.poker.game.component.betting.BlindLevel;
import ca.cmbs.poker.game.component.betting.Pot;
import ca.cmbs.poker.game.component.betting.PotHolder;
import ca.cmbs.poker.game.component.betting.Wager;
import ca.cmbs.poker.game.component.card.Card;
import ca.cmbs.poker.game.component.card.Deck;
import ca.cmbs.poker.table.ChipStack;
import ca.cmbs.poker.table.Seat;
import io.reactivex.subjects.BehaviorSubject;

import java.time.Instant;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class HandOfPlay implements Runnable {


    private final List<HandSeat> seats;
    private final Game game;
    private final BlindLevel level;
    private final BehaviorSubject<HandSnapshot> handSnapshotSubject;

    private final Deck deck;
    private final PotHolder potHolder;
    private final List<Card> communityCards;

    private final HandStats handStats;
    private final HandActionLog handActionLog;

    public HandOfPlay(final List<Seat> seats, final BlindLevel level, final int handNum, final HandActionLog actionLog,
                      final Game game) {
        this.game = game;
        this.level = level;
        if (seats.size() < 2 || seats.stream().anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException("Must have two or more seats to start a hand.");
        }

        this.handStats = HandStats.create(Instant.now(), level, handNum, "Texas Hold 'Em");

        this.handSnapshotSubject = BehaviorSubject.create();
        this.deck = new Deck();
        this.communityCards = new LinkedList<>();
        this.seats = seats.stream().map(seat -> HandSeat.create(seat, communityCards, handSnapshotSubject, game::rankHand)).collect(Collectors.toList());
        this.potHolder = new PotHolder();
        this.handActionLog = actionLog;
    }

    @Override
    public void run() {
        handActionLog.log(String.format("<<< BEGIN HAND %d >>>", handStats.getHandNumber()));
        for (HandAction action : game.handActions()) {
            handActionLog.log(String.format("<<< %s >>>", action));
            if (action.execute(this)) {
                this.resolveHand();
                return;
            }
        }
        this.resolveHand();
    }

    public boolean limitBettingRound(boolean bigStreet) {
        return false;
    }

    public boolean noLimitBettingRound() {
        BettingRounds.noLimitBettingRound(seats, potHolder, handActionLog, level,
                turnNo -> handSnapshotSubject.onNext(HandSnapshot.create(this, turnNo)));
        return !isHandContested();
    }

    public boolean potLimitBettingRound() {
        return false;
    }

    public void resolveHand() {
        List<HandSeat> winners;
        List<Pot> potsByContestors = potHolder.getPots();

        for (HandSeat seat : seats) {
            if (!seat.isFolded() && communityCards.size() >= 3) {
                handActionLog.log(seat.getPlayerName() + " shows " + seat.getHole() + " for " + seat.getHandRanking());
            }
        }

        for (Pot pot : potsByContestors) {
            winners = game.rankerFunction().apply(pot.getContestors());

            divideWinningsAmongWinners(pot.getPotSize(), winners);
        }

        handSnapshotSubject.onNext(HandSnapshot.create(this));
        seats.forEach(HandSeat::adjustSeatChips);
    }

    private void divideWinningsAmongWinners(final ChipStack pot, final List<HandSeat> winners) {
        if (pot.equals(ChipStack.zero())) {
            return;
        }

        if (winners.size() == 0) {
            throw new RuntimeException("Pot of size " + pot + " being divided between no players.");
        }

        List<ChipStack> dividedPot = pot.divide(winners.size());
        for (int i = 0; i < winners.size(); i++) {
            winners.get(i).addChips(dividedPot.get(i));
        }
    }

    public void postAntes() {
        if (!level.hasAnte()) {
            return;
        }

        handActionLog.log(String.format("Players post ante of %s.", level.getAnte()));
        seats.stream().map(seat -> seat.wager(level.getAnte())).forEach(potHolder::add);
        handSnapshotSubject.onNext(HandSnapshot.create(this));
    }

    public void postSmallBlind() {
        int smallBlindIndex = seats.size() == 2 ? 1 : 0;

        Wager wager = seats.get(smallBlindIndex).wager(level.getSmallBlind());
        handActionLog.log(String.format("%s posts small blind of %s.", seats.get(smallBlindIndex).getPlayerName(),
                wager.getAmount() + (wager.isAllIn() ? " and is all in." : ".")));
        potHolder.add(wager);
        handSnapshotSubject.onNext(HandSnapshot.create(this));
    }

    public void postBigBlind() {
        int bigBlindIndex = seats.size() == 2 ? 0 : 1;

        Wager wager = seats.get(bigBlindIndex).wager(level.getBigBlind());
        handActionLog.log(String.format("%s posts big blind of %s.", seats.get(bigBlindIndex).getPlayerName(),
                wager.getAmount() + (wager.isAllIn() ? " and is all in." : ".")));
        potHolder.add(wager);
        handSnapshotSubject.onNext(HandSnapshot.create(this));
    }

    public void dealHole(final int numCards) {
        IntStream.rangeClosed(1, numCards).forEach(i -> seats.forEach(seat -> seat.addHole(deck.deal())));
    }

    public void dealExposed(final int numCards) {
        IntStream.rangeClosed(1, numCards).forEach(i -> seats.forEach(seat -> seat.addExposed(deck.deal())));
    }

    public void dealCommunity(final int numCards) {
        if (numCards < 0) {
            throw new IllegalArgumentException("Number must not be negative.");
        }

        if (deck.size() < numCards) {
            throw new RuntimeException("Deck has insufficient cards to deal " + numCards + " community cards (Deck Size = " + deck.size() + ".");
        }


        List<Card> cards = IntStream.rangeClosed(1, numCards).mapToObj(i -> deck.deal()).collect(Collectors.toList());
        handActionLog.log(String.format("Dealt %s community cards.", cards));
        communityCards.addAll(cards);
    }

    public void drawAction(int i) {
    }

    public List<HandSeat> getSeats() {
        return seats;
    }

    public List<Card> getCommunityCards() {
        return communityCards;
    }

    public HandStats getHandStats() {
        return handStats;
    }

    private boolean isHandContested() {
        return seats.stream().filter(seat -> !seat.isFolded()).count() > 1L;
    }

    public List<String> getLog() {
        return this.handActionLog.getMessages();
    }
}
