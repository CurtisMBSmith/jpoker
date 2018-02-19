package ca.cmbs.poker.player;

import ca.cmbs.poker.game.component.card.Card;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

import static ca.cmbs.util.ValidationUtils.IS_NUMERIC_VALIDATOR;

public enum PlayerActionType {
    CHECK(0, "CH", "CHK", "CHEK", "CHCK", "CHEC"),
    CALL(0, "CA", "CL", "CLL"),
    BET(1, IS_NUMERIC_VALIDATOR, "B", "BE", "BT"),
    RAISE(1, IS_NUMERIC_VALIDATOR, "R", "RA", "RAI", "RAS", "RAIS"),
    FOLD(0, "F", "FO", "FL", "FD", "FLD"),
    DISCARD(1, Card::isCard, "D", "DIS", "DISC"),;

    public static final String ACTION_DELIMITER = " ";
    private final Set<String> alternatives;
    private final int minRequiredArgs;
    private final Predicate<String> argValidator;

    PlayerActionType(final int minRequiredArgs, final Predicate<String> argValidator, final String... alternatives) {
        this.alternatives = new HashSet<>(Arrays.asList(alternatives));
        this.minRequiredArgs = minRequiredArgs;
        this.argValidator = argValidator;
    }

    PlayerActionType(final int minRequiredArgs, final String... alternatives) {
        this(minRequiredArgs, (arg) -> true, alternatives);
    }

    public static Optional<PlayerActionType> determineType(final String actionString) {
        String[] tokenizedString = actionString.split(ACTION_DELIMITER);
        if (tokenizedString.length < 1) {
            return Optional.empty();
        }

        return Arrays.stream(values())
                .filter(type -> type.matchesAlternatives(tokenizedString[0]))
                .filter(type -> type.meetsMinRequiredArgs(tokenizedString))
                .findFirst();
    }

    public static String[] extractArgs(final String actionString) {
        String[] tokenizedString = actionString.split(ACTION_DELIMITER);
        if (tokenizedString.length <= 1) {
            return new String[0];
        }

        return Arrays.copyOfRange(tokenizedString, 1, tokenizedString.length);
    }

    public boolean validateArgs(final String[] args) {
        return Arrays.stream(args).allMatch(this.argValidator);
    }

    private boolean meetsMinRequiredArgs(final String[] tokenizedString) {
        return tokenizedString.length - 1 >= minRequiredArgs;
    }

    private boolean matchesAlternatives(final String typeString) {
        return this.toString().equalsIgnoreCase(typeString) || alternatives.contains(typeString.toUpperCase());
    }


}
