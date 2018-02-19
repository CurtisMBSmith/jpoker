package ca.cmbs.poker.game.component.betting;

import ca.cmbs.poker.game.component.hand.HandSeat;
import ca.cmbs.poker.table.ChipStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class Pot {


    private final Map<HandSeat, ChipStack> wagers;
    private boolean closed;
    private ChipStack highestBet;

    public Pot() {
        this.highestBet = ChipStack.zero();
        this.wagers = new HashMap<>();
        this.closed = false;
    }

    public List<HandSeat> getContestors() {
        return wagers.keySet().stream().filter(seat -> !seat.isFolded()).collect(Collectors.toList());
    }

    public Pot addChips(final Wager bet) {
        Pot pot = null;
        HandSeat bettor = bet.getSeat();
        ChipStack amountToAdd = bet.getAmount();

        wagers.compute(bettor, (seat, chips) -> chips == null ? amountToAdd : chips.add(amountToAdd));

        if (wagers.get(bettor).greaterThan(highestBet)) {
            highestBet = wagers.get(bettor);
        } else if (wagers.get(bettor).lessThan(highestBet)) {
            if (!bettor.isAllIn()) {
                throw new RuntimeException("A seat should not be adding less chips than it has to call if it's not all-in.");
            }

            ChipStack newHighWager = wagers.get(bettor);
            pot = new Pot();
            List<HandSeat> seatsToRemove = new ArrayList<>();
            for (Map.Entry<HandSeat, ChipStack> wager : wagers.entrySet()) {
                if (wager.getValue().remove(newHighWager).greaterThan(ChipStack.zero())) {
                    pot.addChips(Wager.create(wager.getValue().remove(newHighWager), wager.getKey()));
                    wager.setValue(newHighWager);
                }

                if (wager.getValue().lessThanOrEqualTo(ChipStack.zero())) {
                    seatsToRemove.add(wager.getKey());
                }
            }

            for (HandSeat toRemove : seatsToRemove) {
                wagers.remove(toRemove);
            }
            highestBet = newHighWager;
        }

        if (bettor.isAllIn()) {
            closed = true;
            if (pot == null) {
                pot = new Pot();
            }
        }

        return pot;
    }

    public ChipStack getUncalledBet(final HandSeat seat) {
        if (wagers.containsKey(seat)) {
            return highestBet.remove(wagers.get(seat));
        } else {
            return highestBet;
        }
    }

    public boolean isClosed() {
        return closed;
    }

    public ChipStack getPotSize() {
        return wagers.values().stream().reduce(ChipStack.zero(), ChipStack::add);
    }

    public void returnUncalledBet() {
        List<HandSeat> bettorsWithHighestWager = new ArrayList<>();
        for (Entry<HandSeat, ChipStack> wager : wagers.entrySet()) {
            if (wager.getValue() == highestBet) {
                bettorsWithHighestWager.add(wager.getKey());
            }
        }

        if (bettorsWithHighestWager.size() == 1) {
            ChipStack secondHighest = ChipStack.zero();
            for (Entry<HandSeat, ChipStack> wager : wagers.entrySet()) {
                if (wager.getKey() == bettorsWithHighestWager.get(0)) {
                    continue;
                }

                if (wager.getValue().greaterThan(secondHighest)) {
                    secondHighest = wager.getValue();
                }
            }

            if (highestBet.remove(secondHighest).greaterThan(ChipStack.zero())) {
                bettorsWithHighestWager.get(0).addChips(highestBet.remove(secondHighest));
                highestBet = secondHighest;
                wagers.put(bettorsWithHighestWager.get(0), highestBet);
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Pot: ");
        sb.append(getPotSize());
        sb.append(" chips [");
        List<HandSeat> contestors = getContestors();
        for (int i = 0; i < contestors.size(); i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(contestors.get(i).getPlayerName());
        }
        sb.append(']');
        return sb.toString();
    }
}
