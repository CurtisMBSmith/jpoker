package ca.cmbs.poker;

import ca.cmbs.poker.exception.NoSeatAvailableException;
import ca.cmbs.poker.game.Game;
import ca.cmbs.poker.player.ConsolePlayer;
import ca.cmbs.poker.player.MrStupid;
import ca.cmbs.poker.table.ChipStack;
import ca.cmbs.poker.table.Table;
import ca.cmbs.poker.table.TableBuilder;

import java.io.IOException;
import java.io.InputStreamReader;

public class Poker {

    public static void main(String... args) throws NoSeatAvailableException, IOException {

        Table table = TableBuilder.createTableBuilder(9)
                .withName("Console Poker Tournament")
                .withGame(Game.noLimitTexasHoldem())
                .build();

        table.seatPlayer(MrStupid.create("Vicks"), ChipStack.of(1500L));
        table.seatPlayer(MrStupid.create("Wedge"), ChipStack.of(1500L));
        table.seatPlayer(MrStupid.create("Chewbacca"), ChipStack.of(1500L));
        table.seatPlayer(MrStupid.create("Han"), ChipStack.of(1500L));
        table.seatPlayer(MrStupid.create("C3PO"), ChipStack.of(1500L));
        table.seatPlayer(MrStupid.create("Luke"), ChipStack.of(1500L));
        table.seatPlayer(MrStupid.create("R2D2"), ChipStack.of(1500L));
        table.seatPlayer(MrStupid.create("Leia"), ChipStack.of(1500L));

        try (InputStreamReader inputStreamReader = new InputStreamReader(System.in)) {
            table.seatPlayer(ConsolePlayer.create(inputStreamReader, "Bork"), ChipStack.of(1500L));
            table.run();
        }
    }
}
