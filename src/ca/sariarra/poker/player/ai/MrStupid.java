package ca.sariarra.poker.player.ai;

import static ca.sariarra.poker.player.actions.PlayerAction.BET;
import static ca.sariarra.poker.player.actions.PlayerAction.FOLD;
import static ca.sariarra.poker.player.actions.PlayerAction.RAISE;

import java.util.List;
import java.util.Random;

import ca.sariarra.poker.card.Card;
import ca.sariarra.poker.player.Player;
import ca.sariarra.poker.player.actions.Action;
import ca.sariarra.poker.player.actions.AvailableActions;
import ca.sariarra.poker.view.table.TableView;
import ca.sariarra.poker.view.table.component.SeatView;

public class MrStupid extends Player {
	private long currentChips;

	public MrStupid() {
		super("MrStupid");
	}

	public MrStupid(final String name) {
		super(name);
	}

	@Override
	public List<Card> doDiscard(final int discardLimit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Action doAction(final AvailableActions availableActions) {
		Random rand = new Random();

		int result = rand.nextInt(availableActions.getActions().length);
		Action act = new Action(this, availableActions.getActions()[result]);

		if (act.getAction() == BET || act.getAction() == RAISE) {
			result = rand.nextInt(availableActions.getMaxRaise() != null
					? availableActions.getMaxRaise().intValue() : (int) currentChips) + 1;
			act.setBetAmount(result);
		}
		else if (act.getAction() == FOLD) {
			act.setFoldConfirm(true);
		}


		return act;
	}

	@Override
	public void updateTableView(final TableView view) {
		for (SeatView seat : view.getSeats()) {
			if (seat.getPlayerView().getName().equals(getName())) {
				currentChips = seat.getChipStack().getTotalChips();
			}
		}
	}

}
