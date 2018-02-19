package ca.cmbs.poker.table;

import ca.cmbs.poker.game.component.card.Card;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PlayerHandSnapshot {
    private final List<Card> holeCards;
    private final List<Card> exposedCards;
    private final List<Card> communityCards;

    public PlayerHandSnapshot(final List<Card> holeCards, final List<Card> exposedCards, final List<Card> communityCards) {
        this.holeCards = holeCards;
        this.exposedCards = exposedCards;
        this.communityCards = communityCards;
    }

    public static PlayerHandSnapshot takeSnapshot(final List<Card> holeCards) {
        return takeSnapshot(holeCards, Collections.emptyList());
    }

    public static PlayerHandSnapshot takeSnapshot(final List<Card> holeCards, final List<Card> exposedCards) {
        return takeSnapshot(holeCards, exposedCards, Collections.emptyList());
    }

    public static PlayerHandSnapshot takeSnapshot(final List<Card> holeCards, final List<Card> exposedCards, final List<Card> communityCards) {
        return new PlayerHandSnapshot(holeCards, exposedCards, communityCards);
    }

    public List<Card> getHoleCards() {
        return holeCards;
    }

    public List<Card> getExposedCards() {
        return exposedCards;
    }

    public List<Card> getAllCards() {
        return Stream.concat(holeCards.stream(), exposedCards.stream()).collect(Collectors.toList());
    }

    public List<Card> getCommunityCards() {
        return communityCards;
    }
}
