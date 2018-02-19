package ca.cmbs.poker.game;

import ca.cmbs.poker.game.action.HandAction;
import ca.cmbs.poker.game.component.card.Card;
import ca.cmbs.poker.game.component.hand.HandSeat;
import ca.cmbs.poker.game.rank.HandRank;
import ca.cmbs.poker.game.rank.HandRanking;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

import static ca.cmbs.poker.game.action.HandAction.*;
import static ca.cmbs.poker.game.rank.HandRanking.*;

public class NoLimitTexasHoldem extends Game {

    private static final HandAction[] ACTIONS = new HandAction[]{
            POST_BLIND_BETS,
            DEAL_2_HOLE,
            BETTING_ROUND_NO_LIMIT,
            DEAL_3_COMMUNITY,
            BETTING_ROUND_NO_LIMIT,
            DEAL_1_COMMUNITY,
            BETTING_ROUND_NO_LIMIT,
            DEAL_1_COMMUNITY,
            BETTING_ROUND_NO_LIMIT
    };

    private static final HandRanking[] HAND_RANKINGS = new HandRanking[]{
            STRAIGHT_FLUSH,
            FOUR_OF_A_KIND,
            FULL_HOUSE,
            FLUSH,
            STRAIGHT,
            THREE_OF_A_KIND,
            TWO_PAIR,
            PAIR,
            HIGH_CARD
    };

    NoLimitTexasHoldem() {
    }

    @Override
    public HandAction[] handActions() {
        return ACTIONS;
    }

    @Override
    public HandRank rankHand(final List<Card> holeCards, final List<Card> communityCards) {
        HandRank result = null;
        List<Card> handCopy = new LinkedList<>(holeCards);
        handCopy.addAll(communityCards);
        for (HandRanking rank : HAND_RANKINGS) {
            handCopy = new LinkedList<>(handCopy);

            result = rank.rankHand(handCopy);
            if (result != null) {
                break;
            }
        }

        return result;
    }

    @Override
    public Function<List<HandSeat>, List<HandSeat>> rankerFunction() {

        return (seats) -> {
            // Rank the hands
            List<Card> fullHand;
            List<HandSeat> currentWinners = new ArrayList<>();
            HandRank rank;
            HandRank currentBest = null;
            for (HandSeat s : seats) {
                if (s.isFolded()) {
                    continue;
                }

                // TODO If the hand ends before the actual showdown (ie. all players
                // have folded except one), this logic will still try to rank the
                // hand even if there are insufficient cards to do so. This will
                // require a refactor to fix properly, so this is just a patch for now.
                if (s.getCommunityCards().size() < 5) {
                    currentWinners.add(s);
                    continue;
                }

                if (s.getHandRanking() != null) {
                    rank = s.getHandRanking();
                } else {
                    rank = rankHand(s.getHole(), s.getCommunityCards());
                    if (rank == null) {
                        String handString = "[";
                        for (int i = 0; i < s.getHole().size(); i++) {
                            handString += i == 0 ? "" : " " + s.getHole().get(i).toString();
                        }

                        for (int i = 0; i < s.getCommunityCards().size(); i++) {
                            handString += i == 0 ? "" : " " + s.getCommunityCards().get(i).toString();
                        }

                        handString += "]";

                        throw new RuntimeException("Was not able to rank hand: " + handString);
                    }
                }

                if (currentBest == null || currentBest.compareTo(rank) < 0) {
                    currentBest = rank;
                    currentWinners = new ArrayList<>();
                    currentWinners.add(s);
                } else if (currentBest.compareTo(rank) == 0) {
                    currentWinners.add(s);
                }
            }

            return currentWinners;

        };
    }
}
