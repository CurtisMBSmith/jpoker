package ca.cmbs.poker.game.action;

import ca.cmbs.poker.player.PlayerAction;
import ca.cmbs.poker.player.PlayerActionType;
import ca.cmbs.poker.table.ChipStack;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class AvailableActions {

    private final Set<PlayerActionType> actionSet;
    private final ChipStack toCall;
    private final ChipStack minRaise;
    private final ChipStack maxRaise;
    private final PlayerActionType defaultAction;
    private String errorInPreviousAction;

    public AvailableActions(final ChipStack toCall, final ChipStack minRaise, final ChipStack maxRaise,
                            final PlayerActionType... actions) {
        this.actionSet = new HashSet<>(Arrays.asList(actions));
        this.toCall = toCall;
        this.minRaise = minRaise;
		this.maxRaise = maxRaise;
        this.defaultAction = toCall.greaterThan(ChipStack.zero()) ? PlayerActionType.FOLD : PlayerActionType.CHECK;

        this.errorInPreviousAction = "";
    }

    public Set<PlayerActionType> getActions() {
        return actionSet;
    }

    public ChipStack getToCall() {
        return toCall;
    }

    public ChipStack getMinRaise() {
        return minRaise;
    }

    public ChipStack getMaxRaise() {
        return maxRaise;
    }

	public String getErrorInPreviousAction() {
		return errorInPreviousAction;
	}

    public ValidatedAction validate(final PlayerAction pAction) {
        if (!actionSet.contains(pAction.getType())) {
            errorInPreviousAction = "Action " + pAction.getType() + " is not valid.";
            return ValidatedAction.invalid();
        }

        switch (pAction.getType()) {
            case CHECK:
                if (toCall != null && toCall.greaterThan(ChipStack.zero())) {
                    errorInPreviousAction = "Cannot check - Current bet is " + toCall + ".";
                    return ValidatedAction.invalid();
                }
                return ValidatedAction.of(PlayerActionType.CHECK);
            case BET:
            case RAISE:
            ChipStack chips;
            try {
                chips = ChipStack.of(Double.parseDouble(pAction.getArgs()[0]));
            } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
                errorInPreviousAction = "Could not parse first action arg to a decimal value.";
                return ValidatedAction.invalid();
            }

            if (toCall == null || toCall.equals(ChipStack.zero())) {
                // Adjust the bet value.
                if (minRaise != null && chips.lessThan(minRaise)) {
                    return ValidatedAction.of(PlayerActionType.BET, minRaise);
                } else if (maxRaise != null && chips.greaterThan(maxRaise)) {
                    return ValidatedAction.of(PlayerActionType.BET, maxRaise);
                }
                return ValidatedAction.of(PlayerActionType.BET, chips);
            } else {
                // Adjust the bet value.
                if (chips.lessThan(toCall)) {
                    return ValidatedAction.of(PlayerActionType.CALL, toCall);
                } else if (minRaise != null && chips.lessThan(minRaise.add(toCall))) {
                    return ValidatedAction.of(PlayerActionType.RAISE, minRaise.add(toCall));
                } else if (maxRaise != null && chips.greaterThan(maxRaise.add(toCall))) {
                    return ValidatedAction.of(PlayerActionType.RAISE, maxRaise.add(toCall));
                } else if (chips.equals(toCall)) {
                    return ValidatedAction.of(PlayerActionType.CALL);
                }

                return ValidatedAction.of(PlayerActionType.RAISE, chips);
            }
            case CALL:
            if (toCall == null || toCall.equals(ChipStack.zero())) {
                return ValidatedAction.of(PlayerActionType.CHECK);
            } else {
                return ValidatedAction.of(PlayerActionType.CALL, toCall);
            }
            case FOLD:
            return ValidatedAction.of(PlayerActionType.FOLD);
            default:
                errorInPreviousAction = "Unverifiable action, please try again.";
                return ValidatedAction.invalid();
        }
    }

    public PlayerActionType getDefaultAction() {
        return defaultAction;
    }
}
