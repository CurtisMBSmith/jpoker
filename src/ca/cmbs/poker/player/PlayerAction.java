package ca.cmbs.poker.player;

import java.util.Arrays;

public class PlayerAction {

    private final PlayerActionType type;
    private final String[] args;

    private PlayerAction(final PlayerActionType type, final String... args) {
        this.type = type;
        this.args = args;
    }

    public static PlayerAction parse(final String action) {
        PlayerActionType type = PlayerActionType.determineType(action)
                .orElseThrow(() -> new IllegalArgumentException("Could not parse action string " + action + " to an action type."));
        String[] actionArgs = PlayerActionType.extractArgs(action);
        if (!type.validateArgs(actionArgs)) {
            throw new IllegalArgumentException("Args: " + Arrays.toString(actionArgs) + " are not valid for action type: " + type);
        }

        return new PlayerAction(type, actionArgs);
    }

    public static PlayerAction of(final PlayerActionType type, final String... args) {
        return new PlayerAction(type, args);
    }

    public PlayerActionType getType() {
        return type;
    }

    public String[] getArgs() {
        return args;
    }
}
