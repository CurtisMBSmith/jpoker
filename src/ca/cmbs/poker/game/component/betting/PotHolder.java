package ca.cmbs.poker.game.component.betting;

import ca.cmbs.poker.game.component.hand.HandSeat;
import ca.cmbs.poker.table.ChipStack;

import java.util.ArrayList;
import java.util.List;

public class PotHolder {

    private final List<Pot> pots;

    public PotHolder() {
        pots = new ArrayList<>();
        pots.add(new Pot());
    }

    public void add(final Wager wager) {
        Pot pot;
        Pot newPot;
        ChipStack amount = wager.getAmount();
        HandSeat seat = wager.getSeat();
        for (int i = 0; i < pots.size() && amount.greaterThan(ChipStack.zero()); i++) {
            pot = pots.get(i);

            if (pot.isClosed() && pot.getUncalledBet(seat).greaterThan(ChipStack.zero())) {
                if (pot.getUncalledBet(seat).greaterThan(amount)) {
                    newPot = pot.addChips(Wager.create(amount, seat));
                    pots.add(i, newPot);
                    amount = ChipStack.zero();
                } else {
                    amount = amount.remove(pot.getUncalledBet(seat));
                    pot.addChips(Wager.create(pot.getUncalledBet(seat), seat));
                }
            } else {
                newPot = pot.addChips(Wager.create(amount, seat));
                amount = ChipStack.zero();

                // If newPot is not null then the seat was put all in by their
                // bet, so a new pot will be returned.
                if (newPot != null) {
                    pots.add(newPot);
                }
            }
        }
    }

    public ChipStack getUncalledBet(final HandSeat seat) {
        return pots.stream().map(pot -> pot.getUncalledBet(seat)).reduce(ChipStack.zero(), ChipStack::add);
    }

    public ChipStack getTotalSize() {
        return pots.stream().map(Pot::getPotSize).reduce(ChipStack.zero(), ChipStack::add);
    }

    public void returnUncalledBet() {
        pots.get(pots.size() - 1).returnUncalledBet();
    }

    public List<Pot> getPots() {
        return pots;
    }
}
