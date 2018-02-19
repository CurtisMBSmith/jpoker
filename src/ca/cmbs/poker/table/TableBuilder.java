package ca.cmbs.poker.table;

import ca.cmbs.poker.game.Game;

public class TableBuilder {

    private int numSeats;
    private String name;
    private Game game;

    private TableBuilder(final int numSeats) {
        this.numSeats = numSeats;
    }

    public static TableBuilder createTableBuilder(int numSeats) {
        return new TableBuilder(numSeats);
    }

    public TableBuilder withName(final String name) {
        this.name = name;
        return this;
    }

    public TableBuilder withGame(final Game game) {
        this.game = game;
        return this;
    }

    public Table build() {
        return new Table(numSeats, name, game);
    }
}
