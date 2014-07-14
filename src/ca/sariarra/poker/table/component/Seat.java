package ca.sariarra.poker.table.component;

import static ca.sariarra.util.ParamUtil.notNull;

import java.util.ArrayList;
import java.util.List;

import ca.sariarra.poker.card.Card;
import ca.sariarra.poker.game.handrank.HandRank;
import ca.sariarra.poker.player.Player;
import ca.sariarra.poker.player.actions.AvailableActions;
import ca.sariarra.poker.player.actions.StandardAction;
import ca.sariarra.poker.view.table.TableView;

public class Seat {
	private final Player player;
	private final Hand hand;
	private ChipStack chipStack;
	private boolean sittingOut;

	public Seat(final Player p) {
		notNull("Player", p);
		player = p;
		sittingOut = true;
		hand = new Hand();
		chipStack = new ChipStack();
	}

	public void resetForHand() {
		hand.resetHand();
	}

	public void setChips(final ChipStack cs) {
		if (chipStack == null) {
			chipStack = cs;
		}
	}

	public ChipStack getChipStack() {
		return chipStack;
	}

	public boolean isAllIn() {
		return chipStack.isEmpty();
	}

	public Player getPlayer() {
		return player;
	}

	public Hand getHand() {
		return hand;
	}

	public void setSittingOut(final boolean sitting) {
		sittingOut = sitting;
	}

	public boolean isSittingOut() {
		return sittingOut;
	}

	public boolean isFolded() {
		return hand.hasFolded();
	}

	public void addHoleCard(final Card card) {
		hand.addHole(card);
	}

	public void addExposedCard(final Card card) {
		hand.addExposed(card);
	}

	public List<Card> getDiscards(final int max) {
		if (player == null) {
			return new ArrayList<Card>();
		}

		return player.doDiscard(max);
	}

	public long bet(final long amount) {
		return chipStack.removeChips(amount);
	}

	public StandardAction getPlayerAction(final AvailableActions actions) {
		return player.doAction(actions);
	}

	public void fold() {
		hand.setFolded(true);
	}

	public void addChips(final long amount) {
		chipStack.collect(amount);
	}

	public HandRank getHandRanking() {
		return hand.getRanking();
	}

	public void setHandRanking(final HandRank rank) {
		hand.setRanking(rank);
	}

	public long getChips() {
		return chipStack.getTotal();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append(player.getName());
		for (int i = sb.length(); i < 20; i++) {
			sb.append(' ');
		}
		sb.append(" [");
		sb.append(hand.hasFolded() ? 'F' : ' ');
		sb.append(chipStack.isEmpty() ? 'A' : ' ');
		sb.append(sittingOut ? 'S' : ' ');
		sb.append(']');

		sb.append(" (");
		sb.append(chipStack.getTotal());
		sb.append(" chips)");

		return sb.toString();
	}

	public void updateTableState(final TableView tableView) {
		player.updateTableView(tableView);
	}

}
