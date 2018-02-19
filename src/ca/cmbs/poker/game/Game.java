package ca.cmbs.poker.game;

import ca.cmbs.poker.game.action.HandAction;
import ca.cmbs.poker.game.component.betting.BlindLevel;
import ca.cmbs.poker.game.component.card.Card;
import ca.cmbs.poker.game.component.hand.HandActionLog;
import ca.cmbs.poker.game.component.hand.HandOfPlay;
import ca.cmbs.poker.game.component.hand.HandSeat;
import ca.cmbs.poker.game.rank.HandRank;
import ca.cmbs.poker.table.Seat;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public abstract class Game {

    public static NoLimitTexasHoldem noLimitTexasHoldem() {
        return new NoLimitTexasHoldem();
    }

    public HandOfPlay createHand(final List<Seat> seats, final BlindLevel level, final int handNum, final HandActionLog actionLog) {
        return new HandOfPlay(seats, level, handNum, actionLog, this);
    }

    public abstract HandAction[] handActions();

    public abstract HandRank rankHand(final List<Card> holeCards, final List<Card> communityCards);

    public HandRank rankHand(final List<Card> holeCards) {
        return rankHand(holeCards, Collections.emptyList());
    }

    public abstract Function<List<HandSeat>, List<HandSeat>> rankerFunction();
}
