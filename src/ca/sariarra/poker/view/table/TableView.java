package ca.sariarra.poker.view.table;

import static ca.sariarra.poker.game.action.HandAction.SHOWDOWN;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import ca.sariarra.poker.card.Card;
import ca.sariarra.poker.game.action.HandAction;
import ca.sariarra.poker.player.Player;
import ca.sariarra.poker.table.Table;
import ca.sariarra.poker.table.component.HandActionLog;
import ca.sariarra.poker.table.component.Pot;
import ca.sariarra.poker.view.card.CardView;
import ca.sariarra.poker.view.table.component.PotView;
import ca.sariarra.poker.view.table.component.SeatView;

public class TableView {

	private String tableDesc;
	private final SeatView[] seats;
	private final List<PotView> pots;
	private final HandActionLog actionLog;
	private final HandAction currentAction;
	private final List<CardView> communityCards;

	public TableView(final Table table, final Player playerView) {
		tableDesc = table.getDescription();
		seats = new SeatView[table.getSeats().length];
		for (int i = 0; i < table.getSeats().length; i++) {
			if (table.getSeats()[i] == null) {
				continue;
			}

			if (table.getHandPhase() == SHOWDOWN) {
				seats[i] = new SeatView(table.getSeats()[i], true);
			}
			else {
				seats[i] = new SeatView(table.getSeats()[i], table.getSeats()[i].getPlayer() == playerView);
			}
		}

		pots = new ArrayList<PotView>(4);
		for (Pot pot : table.getCurrentPots()) {
			pots.add(new PotView(pot));
		}

		// Sort the pots from most to least contestors.
		pots.sort(new Comparator<PotView>() {

			@Override
			public int compare(final PotView pot1, final PotView pot2) {
				if (pot1.getContestors().size() < pot2.getContestors().size()) {
					return 1;
				}
				else if (pot1.getContestors().size() > pot2.getContestors().size()) {
					return -1;
				}
				else {
					return 0;
				}
			}
		});

		actionLog = table.getHandActionLog();
		currentAction = table.getHandPhase();
		communityCards = new ArrayList<CardView>(5);
		for (Card card : table.getCommunityCards()) {
			communityCards.add(new CardView(card));
		}
	}

	public String getTableDesc() {
		return tableDesc;
	}

	public SeatView[] getSeats() {
		return seats;
	}

	public List<PotView> getPots() {
		return pots;
	}

	public HandActionLog getActionLog() {
		return actionLog;
	}

	public HandAction getCurrentAction() {
		return currentAction;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		// Add the table description.
		sb.append(tableDesc);
		sb.append('\n');

		// Add the seat views.
		sb.append("========================= SEATS =========================\n");
		for (int i = 0; i <  seats.length; i++) {
			sb.append('[');
			sb.append(i + 1);
			sb.append(']');
			sb.append(' ');
			sb.append(seats[i].toString());
			sb.append('\n');
		}

		// Add the community cards.
		if (communityCards.size() > 0) {
			sb.append("========================= BOARD =========================\n");
			for (int i = 0; i < communityCards.size(); i++) {
				if (i > 0) {
					sb.append(',');
					sb.append(' ');
				}
				sb.append(communityCards.get(i));
			}

			sb.append('\n');
		}

		// Add the pot views.
		if (pots.size() > 0) {
			sb.append("========================= POTS ==========================\n");
			sb.append("Main pot: ");
			sb.append(pots.get(0).toString());
			sb.append('\n');
			for (int i = 1; i < pots.size(); i++) {
				sb.append("Side pot ");
				sb.append(i);
				sb.append(':');
				sb.append(' ');
				sb.append(pots.get(i).toString());
				sb.append('\n');
			}
		}

		// Add the current action.
		if(actionLog.getCurrentRoundActions() != null) {
			sb.append(actionLog.getCurrentRoundActions().toString());
		}

		return sb.toString();
	}
}
