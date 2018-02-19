package ca.cmbs.poker.game.component.hand;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class HandActionLog {

    private final List<String> log;

    public HandActionLog() {
        this.log = new LinkedList<>();
    }

    public void log(final String msg) {
        this.log.add(msg);
    }

    @Override
    public String toString() {
        return log.stream().collect(Collectors.joining("\n"));
    }

    public List<String> getMessages() {
        return new LinkedList<>(log);
    }
}
