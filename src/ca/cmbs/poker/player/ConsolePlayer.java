package ca.cmbs.poker.player;

import ca.cmbs.poker.game.action.AvailableActions;
import ca.cmbs.poker.game.component.hand.HandSeatSnapshot;
import ca.cmbs.poker.game.component.hand.HandSnapshot;
import ca.cmbs.poker.table.PlayerHandSnapshot;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.Comparator;
import java.util.stream.Collectors;

public class ConsolePlayer extends Player {

    private static final String ANSI_ESCAPE_CODES = "\033[H\033[2J";
    private static final String DIVIDER = Collections.nCopies(100, '*').stream().map(Object::toString).collect(Collectors.joining());
    private static final long READER_SLEEP_MILLIS = 30L;
    private final PublishSubject<PlayerAction> playerActionStream;
    private PlayerHandSnapshot currentHand;
    private HandSnapshot handSnapshot;
    private AvailableActions availableActions;
    private int previousLogIndex;

    private ConsolePlayer(final InputStreamReader reader, final String name) {
        super(name);
        this.playerActionStream = PublishSubject.create();

        new Thread(() -> {
            String line;
            try {
                while ((line = readLineOfInput(reader)) != null) {
                    System.out.println("Read line: " + line);
                    try {
                        this.playerActionStream.onNext(PlayerAction.parse(line));
                    } catch (IllegalArgumentException e) {
                        System.out.println("Could not parse action: " + e.getMessage());
                    }
                }
            } catch (IOException e) {
                System.err.println("IOException occurred while reading from system.in: " + e.getMessage());
                e.printStackTrace();
            } catch (InterruptedException e) {
                // Allow the thread to terminate.
            }
        }).start();
    }

    public static ConsolePlayer create(final InputStreamReader inputStreamReader, final String name) {
        return new ConsolePlayer(inputStreamReader, name);
    }

    private String readLineOfInput(final InputStreamReader reader) throws InterruptedException, IOException {
        StringBuilder sb = new StringBuilder();
        while (true) {
            // Read until a string terminator is encountered or the underlying stream is closed.
            if (!reader.ready()) {
                Thread.sleep(READER_SLEEP_MILLIS);
            } else {
                int readCharacter = reader.read();

                if (readCharacter == -1) {
                    return null;
                }

                char character = (char) readCharacter;
                if (character == '\b') {
                    sb.deleteCharAt(sb.length() - 1);
                } else if (character == '\n') {
                    return sb.toString().trim();
                } else {
                    sb.append(character);
                }
            }
        }
    }

    @Override
    protected void handleHandSnapshot(final PlayerHandSnapshot handSnapshot) {
        currentHand = handSnapshot;
        updateConsole();
    }

    @Override
    protected void handleGameHandSnapshot(final HandSnapshot handSnapshot) {
        this.handSnapshot = handSnapshot;
    }

    @Override
    protected void promptForAction(final AvailableActions actions) {
        this.availableActions = actions;
        updateConsole();
        this.previousLogIndex = this.handSnapshot == null ? -1 : this.handSnapshot.getActionLogSize();
    }

    @Override
    public Observable<PlayerAction> playerActionStream() {
        return playerActionStream;
    }

    private void updateConsole() {
        // Remove previously printed details
        System.out.print(ANSI_ESCAPE_CODES);
        System.out.flush();

        StringBuilder sb = new StringBuilder();
        sb.append('\n');
        sb.append(printHeader());
        sb.append(printSeats());

        sb.append('\n');
        sb.append("Community cards: ");
        sb.append(currentHand.getCommunityCards());
        sb.append('\n');
        sb.append("Actions: \n").append(handSnapshot.getActionsSince(previousLogIndex)).append('\n');

        if (availableActions != null && handSnapshot.getSeatNumber() == this.seatNumber) {
            sb.append("It is your turn to act: ").append(availableActions.getActions());
            if (!availableActions.getErrorInPreviousAction().isEmpty()) {
                sb.append('\n').append(availableActions.getErrorInPreviousAction());
            }
        }

        System.out.print(sb.toString());
    }

    private String printSeats() {
        StringBuilder sb = new StringBuilder();
        this.handSnapshot.getHandSeatSnapshots().sort(Comparator.comparing(HandSeatSnapshot::getSeatNumber));
        this.handSnapshot.getHandSeatSnapshots().stream().map(snapshot -> {
            StringBuilder builder = new StringBuilder();
            builder.append("** ");
            if (snapshot.getPlayerID() == this.getID()) {
                builder.append(snapshot.toStringForPlayer(currentHand));
            } else {
                builder.append(snapshot.toString());
            }
            builder.append('\n');
            return builder.toString();
        }).forEach(sb::append);

        return sb.toString();
    }

    private String printHeader() {
        StringBuilder sb = new StringBuilder();
        sb.append(DIVIDER);
        sb.append('\n');
        sb.append("** ");
        sb.append(this.handSnapshot.getHandStats());
        sb.append('\n');
        sb.append(DIVIDER);
        sb.append('\n');

        return sb.toString();
    }

}
