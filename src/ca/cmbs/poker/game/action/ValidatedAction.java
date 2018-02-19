package ca.cmbs.poker.game.action;

import ca.cmbs.poker.player.PlayerActionType;
import ca.cmbs.poker.table.ChipStack;

public class ValidatedAction {

    private final PlayerActionType actionType;
    private final ChipStack amount;

    private ValidatedAction(final PlayerActionType actionType) {
        this(actionType, null);
    }

    private ValidatedAction(final PlayerActionType actionType, final ChipStack amount) {
        this.actionType = actionType;
        this.amount = amount;
    }

    public static ValidatedAction of(final PlayerActionType actionType) {
        return new ValidatedAction(actionType);
    }

    public static ValidatedAction of(final PlayerActionType actionType, final ChipStack amount) {
        return new ValidatedAction(actionType, amount);
    }

    public static ValidatedAction invalid() {
        return new ValidatedAction(null);
    }

    public PlayerActionType getActionType() {
        return actionType;
    }

    public ChipStack getAmount() {
        return amount;
    }

    public boolean isValid() {
        return actionType != null;
    }

    @Override
    public String toString() {
        return actionType + (amount != null ? " " + amount : "");
    }
}
