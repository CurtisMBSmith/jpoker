package ca.sariarra.poker.player.human;

import static ca.sariarra.poker.player.actions.PlayerAction.BET;
import static ca.sariarra.poker.player.actions.PlayerAction.CALL;
import static ca.sariarra.poker.player.actions.PlayerAction.CHECK;
import static ca.sariarra.poker.player.actions.PlayerAction.FOLD;
import static ca.sariarra.poker.player.actions.PlayerAction.RAISE;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import ca.sariarra.poker.card.Card;
import ca.sariarra.poker.player.Player;
import ca.sariarra.poker.player.actions.AvailableActions;
import ca.sariarra.poker.player.actions.PlayerAction;
import ca.sariarra.poker.player.actions.StandardAction;
import ca.sariarra.poker.view.table.TableView;

public class ConsolePlayer extends Player {
	private final BufferedReader br;

	public ConsolePlayer(final BufferedReader br, final String name) {
		super(name);
		this.br = br;
	}

	@Override
	public List<Card> doDiscard(final int discardLimit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StandardAction doAction(final AvailableActions availableActions) {
		PlayerAction[] actions = availableActions.getActions();
		System.out.print("It's your turn, please select an action: ");
		for (int i = 0; i < actions.length; i++) {
			if (i > 0) {
				System.out.print(", ");
			}

			System.out.print(actions[i].toString());
		}

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StandardAction result = null;
		while (result == null) {

			try {
				result = decodeInputToAction(br.readLine());
			}
			catch (IOException e) {
				throw new RuntimeException("IOException while retrieving console input.", e);
			}

			if (result == null) {
				System.out.print("Invalid action, please retry: ");
			}
		}

		if (result.getAction() == BET || result.getAction() == RAISE) {
			try {
				while(result.getBetAmount() <= 0l) {
					System.out.print("Please enter a valid bet amount between " + availableActions.getMinRaise()
							+ " and " + availableActions.getMaxRaise() + ": ");
					result.setBetAmount(decodeInputToBetAmount(br.readLine()));
				}
			}
			catch (IOException e) {
				throw new RuntimeException("IOException while retrieving console input.", e);
			}
		}

		return result;
	}

	private long decodeInputToBetAmount(final String input) {
		long result;
		try {
			result = Long.parseLong(input);
		}
		catch (NumberFormatException e) {
			result = -1l;
		}

		return result;
	}

	private StandardAction decodeInputToAction(final String input) {
		StandardAction result = null;

		String[] inputSp = input.split(" ");
		if (inputSp.length == 0) {
			return result;
		}

		// Decode the string to an action.
		if (inputSp[0].equalsIgnoreCase("BET") || inputSp[0].equalsIgnoreCase("B")) {
			result = new StandardAction(this, BET);
			if (inputSp.length > 1) {
				result.setBetAmount(decodeInputToBetAmount(inputSp[1]));
			}
		}
		else if (inputSp[0].equalsIgnoreCase("RAISE") || inputSp[0].equalsIgnoreCase("R")) {
			result = new StandardAction(this, RAISE);
			if (inputSp.length > 1) {
				result.setBetAmount(decodeInputToBetAmount(inputSp[1]));
			}
		}
		else if (inputSp[0].equalsIgnoreCase("CHECK") || inputSp[0].equalsIgnoreCase("CH")) {
			result = new StandardAction(this, CHECK);
		}
		else if (inputSp[0].equalsIgnoreCase("CALL") || inputSp[0].equalsIgnoreCase("CA")) {
			result = new StandardAction(this, CALL);
		}
		else if (inputSp[0].equalsIgnoreCase("FOLD") || inputSp[0].equalsIgnoreCase("F")) {
			result = new StandardAction(this, FOLD);
			result.setFoldConfirm(true);
		}

		return result;
	}

	@Override
	public void updateTableView(final TableView view) {
		System.out.println(view.toString());
		try {
			br.readLine();
		} catch (IOException e) {
			throw new RuntimeException("IOException while reading from System.in", e);
		}
	}

}
