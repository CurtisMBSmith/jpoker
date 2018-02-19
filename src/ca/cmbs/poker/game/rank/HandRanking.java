package ca.cmbs.poker.game.rank;

import ca.cmbs.poker.game.component.card.Card;
import ca.cmbs.poker.game.component.card.Rank;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.TreeMap;
import java.util.stream.Collectors;

import static ca.cmbs.poker.game.rank.HandRank.HAND_SIZE;

public enum HandRanking implements Rankable {

    HIGH_CARD((o1, o2) -> {

        int comparedByRankings = o1.getRank().compareTo(o2.getRank());
        if (comparedByRankings == 0) {
            int result = 0;
            for (int i = 0; i < HAND_SIZE; i++) {
                result = o1.getCards().get(i).rank().compareTo(o2.getCards().get(i).rank());
                if (result != 0) {
                    break;
                }
            }

            return result;
        } else {
            return comparedByRankings;
        }
    }) {
        @Override
		public HandRank rankHand(final List<Card> cards) {
            return new HandRank(HandRanking.HIGH_CARD, cards.stream()
                    .collect(Collectors.groupingBy(Card::rank, Collectors.toList()))
                    .entrySet().stream()
                    .sorted((entry1, entry2) -> -entry1.getKey().compareTo(entry2.getKey()))
                    .map(entry -> entry.getValue().get(0))
                    .collect(Collectors.toList())
                    .subList(0, 5));
        }
    },
    PAIR((o1, o2) -> {
        int comparedByRankings = o1.getRank().compareTo(o2.getRank());
        if (comparedByRankings == 0) {
            int secondaryComparison = o1.getCards().get(0).rank().compareTo(o2.getCards().get(0).rank());
            if (secondaryComparison == 0) {
                int tertiaryComparison = o1.getCards().get(2).rank().compareTo(o2.getCards().get(2).rank());
                if (tertiaryComparison == 0) {
                    int quaternaryComparison = o1.getCards().get(3).rank().compareTo(o2.getCards().get(3).rank());
                    if (quaternaryComparison == 0) {
                        return o1.getCards().get(4).rank().compareTo(o2.getCards().get(4).rank());
                    } else {
                        return quaternaryComparison;
                    }
                } else {
                    return tertiaryComparison;
                }
            } else {
                return secondaryComparison;
            }
        } else {
            return comparedByRankings;
        }
    }) {
        @Override
		public HandRank rankHand(final List<Card> cards) {
            return cards.stream()
                    .collect(Collectors.groupingBy(Card::rank, Collectors.toList()))
                    .entrySet().stream()
                    .filter(entry -> entry.getValue().size() >= 2)
                    .max(Comparator.comparing(Entry::getKey))
                    .map(pair -> {
                        List<Card> otherCards = cards.stream()
                                .filter(card -> !card.rank().equals(pair.getKey()))
                                .sorted(Comparator.comparing(Card::rank).reversed())
                                .collect(Collectors.toList());
                        pair.getValue().addAll(otherCards.subList(0, 3));
                        return new HandRank(HandRanking.PAIR, pair.getValue());
                    }).orElse(null);
        }
    },
    TWO_PAIR((o1, o2) -> {
        int comparedByRankings = o1.getRank().compareTo(o2.getRank());
        if (comparedByRankings == 0) {
            int secondaryComparison = o1.getCards().get(0).rank().compareTo(o2.getCards().get(0).rank());
            if (secondaryComparison == 0) {
                int tertiaryComparison = o1.getCards().get(2).rank().compareTo(o2.getCards().get(2).rank());
                if (tertiaryComparison == 0) {
                    return o1.getCards().get(4).rank().compareTo(o2.getCards().get(4).rank());
                } else {
                    return tertiaryComparison;
                }
            } else {
                return secondaryComparison;
            }
        } else {
            return comparedByRankings;
        }
    }) {
        @Override
		public HandRank rankHand(final List<Card> cards) {
            return cards.stream()
                    .collect(Collectors.groupingBy(Card::rank, Collectors.toList()))
                    .entrySet().stream()
                    .filter(entry -> entry.getValue().size() >= 2)
                    .max(Comparator.comparing(Entry::getKey))
                    .flatMap(pair ->
                            cards.stream()
                                    .filter(card -> !card.rank().equals(pair.getKey()))
                                    .collect(Collectors.groupingBy(Card::rank, Collectors.toList()))
                                    .entrySet().stream()
                                    .filter(entry -> entry.getValue().size() >= 2)
                                    .max(Comparator.comparing(Entry::getKey))
                                    .map(secondPair -> {
                                        List<Card> resultingHand = pair.getValue().subList(0, 2);
                                        resultingHand.addAll(secondPair.getValue().subList(0, 2));

                                        cards.stream().filter(card -> card.rank() != pair.getKey() && card.rank() != secondPair.getKey())
                                                .max(Comparator.comparing(Card::rank)).ifPresent(card -> resultingHand.add(card));

                                        return new HandRank(HandRanking.TWO_PAIR, resultingHand);
                                    })
                    ).orElse(null);
        }
    },
    THREE_OF_A_KIND((o1, o2) -> {
        int comparedByRankings = o1.getRank().compareTo(o2.getRank());
        if (comparedByRankings == 0) {
            int secondaryComparison = o1.getCards().get(0).rank().compareTo(o2.getCards().get(0).rank());
            if (secondaryComparison == 0) {
                int tertiaryComparison = o1.getCards().get(3).rank().compareTo(o2.getCards().get(3).rank());
                if (tertiaryComparison == 0) {
                    return o1.getCards().get(4).rank().compareTo(o2.getCards().get(4).rank());
                } else {
                    return tertiaryComparison;
                }
            } else {
                return secondaryComparison;
            }
        } else {
            return comparedByRankings;
        }
    }) {
        @Override
		public HandRank rankHand(final List<Card> cards) {
            return cards.stream()
                    .collect(Collectors.groupingBy(Card::rank, Collectors.toList()))
                    .entrySet().stream()
                    .filter(entry -> entry.getValue().size() >= 3)
                    .max(Comparator.comparing(Entry::getKey))
                    .map(trip -> {
                        List<Card> otherRanks = cards.stream().filter(card -> !card.rank().equals(trip.getKey()))
                                .sorted(Comparator.comparing(Card::rank).reversed()).collect(Collectors.toList());
                        trip.getValue().add(otherRanks.get(0));
                        trip.getValue().add(otherRanks.get(1));

                        return new HandRank(HandRanking.THREE_OF_A_KIND, trip.getValue());
                    }).orElse(null);
        }
    },
    STRAIGHT((o1, o2) -> {
        int comparedByRankings = o1.getRank().compareTo(o2.getRank());
        if (comparedByRankings == 0) {
            return o1.getCards().get(0).rank().compareTo(o2.getCards().get(0).rank());
        } else {
            return comparedByRankings;
        }
    }) {
        @Override
		public HandRank rankHand(final List<Card> cards) {
            TreeMap<Rank, List<Card>> cardsByRank = cards.stream()
                    .collect(Collectors.groupingBy(Card::rank, () -> new TreeMap<>(Comparator.reverseOrder()), Collectors.toList()));

            int sequence = 0;
            Rank previous = null;
            for (Entry<Rank, List<Card>> rank : cardsByRank.entrySet()) {
                sequence++;
                if (previous != null && previous.ordinal() - rank.getKey().ordinal() > 1) {
                    sequence = 1;
                }

                if ((rank.getKey() == Rank.TWO && sequence == 4 && cardsByRank.firstKey() == Rank.ACE) || sequence == 5) {
                    List<Card> straight = new LinkedList<>();
                    if (rank.getKey() == Rank.TWO) {
                        straight.add(cardsByRank.firstEntry().getValue().get(0));
                    }
                    straight.add(rank.getValue().get(0));
                    Rank lastKey = rank.getKey();
                    while (straight.size() < 5) {
                        straight.add(cardsByRank.lowerEntry(lastKey).getValue().get(0));
                        lastKey = cardsByRank.lowerKey(lastKey);
                    }

                    return new HandRank(HandRanking.STRAIGHT, straight);
                }

                previous = rank.getKey();
            }

            return null;
        }
    },
    FLUSH((o1, o2) -> {
        int comparedByRankings = o1.getRank().compareTo(o2.getRank());
        if (comparedByRankings == 0) {
            int cardByCardComparison = 0;
            for (int i = 0; i < HAND_SIZE; i++) {
                cardByCardComparison = o1.getCards().get(i).compareTo(o2.getCards().get(i));
                if (cardByCardComparison != 0) {
                    break;
                }
            }

            return cardByCardComparison;
        } else {
            return comparedByRankings;
        }
    }) {
        @Override
		public HandRank rankHand(final List<Card> cards) {
            return cards.stream()
                    .collect(Collectors.groupingBy(Card::suit, Collectors.toList()))
                    .entrySet().stream()
                    .filter(entry -> entry.getValue().size() >= HAND_SIZE)
                    .findFirst()
                    .map(flush -> new HandRank(HandRanking.FLUSH, flush.getValue().stream()
                            .sorted(Comparator.reverseOrder())
                            .collect(Collectors.toList())
                            .subList(0, 5))).orElse(null);
        }
    },
    FULL_HOUSE((o1, o2) -> {
        int comparedByRankings = o1.getRank().compareTo(o2.getRank());
        if (comparedByRankings == 0) {
            int secondaryComparison = o1.getCards().get(0).rank().compareTo(o2.getCards().get(0).rank());
            if (secondaryComparison == 0) {
                return o1.getCards().get(3).rank().compareTo(o2.getCards().get(3).rank());
            } else {
                return secondaryComparison;
            }
        } else {
            return comparedByRankings;
        }
    }) {
        @Override
		public HandRank rankHand(final List<Card> cards) {
            return cards.stream()
                    .collect(Collectors.groupingBy(Card::rank, Collectors.toList()))
                    .entrySet().stream()
                    .filter(entry -> entry.getValue().size() >= 3)
                    .max(Comparator.comparing(Entry::getKey))
                    .flatMap(trips ->
                            cards.stream()
                                    .filter(card -> !card.rank().equals(trips.getKey()))
                                    .collect(Collectors.groupingBy(Card::rank, Collectors.toList()))
                                    .entrySet().stream()
                                    .filter(entry -> entry.getValue().size() >= 2)
                                    .max(Comparator.comparing(Entry::getKey))
                                    .map(pair -> {
                                        List<Card> resultingHand = trips.getValue().subList(0, 3);
                                        resultingHand.addAll(pair.getValue().subList(0, 2));
                                        return new HandRank(HandRanking.FULL_HOUSE, resultingHand);
                                    })
                    ).orElse(null);
        }
    },
    FOUR_OF_A_KIND((o1, o2) -> {
        int comparedByRankings = o1.getRank().compareTo(o2.getRank());
        if (comparedByRankings == 0) {
            int secondaryComparison = o1.getCards().get(0).rank().compareTo(o2.getCards().get(0).rank());
            if (secondaryComparison == 0) {
                return o1.getCards().get(4).rank().compareTo(o2.getCards().get(4).rank());
            } else {
                return secondaryComparison;
            }
        } else {
            return comparedByRankings;
        }
    }) {
        @Override
		public HandRank rankHand(final List<Card> cards) {
            return cards.stream()
                    .collect(Collectors.groupingBy(Card::rank, Collectors.toList()))
                    .entrySet().stream()
                    .filter(entry -> entry.getValue().size() >= 4)
                    .max(Comparator.comparing(Entry::getKey))
                    .map(quad -> {
                        cards.stream().filter(card -> !card.rank().equals(quad.getKey()))
                                .max(Comparator.comparing(Card::rank))
                                .ifPresent(card -> quad.getValue().add(card));
                        return new HandRank(HandRanking.FOUR_OF_A_KIND, quad.getValue());
                    }).orElse(null);
        }
    },
    STRAIGHT_FLUSH((o1, o2) -> {
        int comparedByRankings = o1.getRank().compareTo(o2.getRank());
        if (comparedByRankings == 0) {
            return o1.getCards().get(0).rank().compareTo(o2.getCards().get(0).rank());
        } else {
            return comparedByRankings;
        }
    }) {
        @Override
		public HandRank rankHand(final List<Card> cards) {
            return cards.stream()
                    .collect(Collectors.groupingBy(Card::suit, Collectors.toList()))
                    .entrySet().stream()
                    .filter(entry -> entry.getValue().size() >= HAND_SIZE)
                    .map(Entry::getValue)
                    .map(HandRanking.STRAIGHT::rankHand)
                    .filter(Objects::nonNull)
                    .max(HandRank::compareTo)
                    .map(result -> new HandRank(HandRanking.STRAIGHT_FLUSH, result.getCards()))
                    .orElse(null);
        }
    };

	private final Comparator<HandRank> comparator;

    HandRanking(final Comparator<HandRank> comparator) {
        this.comparator = comparator;
    }

	public Comparator<HandRank> getComparator() {
		return comparator;
	}


}
