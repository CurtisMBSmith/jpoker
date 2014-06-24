package ca.sariarra.poker.datastruct;

import static ca.sariarra.util.ParamUtil.ensureNotNull;

import java.util.ArrayList;
import java.util.List;

import ca.sariarra.poker.logic.Action;
import ca.sariarra.poker.logic.AvailableActions;

public class Seat {
	private final Player player;
	private Hand hand;
	private ChipStack chipStack;
	private boolean sittingOut;

	public Seat(final Player p) {
		ensureNotNull("Player", p);
		player = p;
		sittingOut = true;
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

	public Action getPlayerAction(final AvailableActions actions) {
		return player.doAction(actions);
	}

	public void fold() {
		hand.setFolded(true);
	}

}
