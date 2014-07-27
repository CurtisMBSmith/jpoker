package ca.sariarra.poker.hand;

import static ca.sariarra.poker.game.action.HandAction.SHOWDOWN;
import static ca.sariarra.poker.player.actions.ForcedBet.ANTE;
import static ca.sariarra.poker.player.actions.ForcedBet.BIG_BLIND;
import static ca.sariarra.poker.player.actions.ForcedBet.SMALL_BLIND;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ca.sariarra.poker.card.Card;
import ca.sariarra.poker.game.PokerGame;
import ca.sariarra.poker.game.action.HandAction;
import ca.sariarra.poker.hand.bettinground.NoLimitBettingRound;
import ca.sariarra.poker.hand.component.HandActionLog;
import ca.sariarra.poker.hand.component.Pot;
import ca.sariarra.poker.hand.component.PotManager;
import ca.sariarra.poker.player.actions.PostAction;
import ca.sariarra.poker.player.actions.ShowAction;
import ca.sariarra.poker.table.Table;
import ca.sariarra.poker.table.component.BlindLevel;
import ca.sariarra.poker.table.component.Deck;
import ca.sariarra.poker.table.component.Seat;

public class HandOfPlay implements Runnable {

	private final List<Card> communityCards;
	private final Deck deck;
	protected Seat[] seatsForHand;
	private HandAction currentHandPhase;
	protected final PokerGame handGame;
	private final long TURN_TIMEOUT = 600000;
	private final PotManager pot;
	private final Date handStart;
	private final HandActionLog handActionLog;
	private final HandAction[] handActions;
	private final Table table;
	private final BlindLevel level;
	private final int handNum;
	private int bettingRoundNum;

	public HandOfPlay(final Seat[] seatsForHand, final Table table, final PokerGame handGame,
			final HandAction[] handActions, final BlindLevel level, final int handNum) {
		this.deck = new Deck();
		this.communityCards = new ArrayList<Card>(5);
		this.currentHandPhase = null;
		this.handStart = new Date();
		this.pot = new PotManager();
		this.handGame = handGame;
		this.handActions = handActions;
		this.handActionLog = new HandActionLog();
		this.seatsForHand = seatsForHand;
		this.table = table;
		this.level = level;
		this.handNum = handNum;
		this.bettingRoundNum = 0;
	}

	@Override
	public void run() {
		for (HandAction action : handActions) {
			currentHandPhase = action;
			handActionLog.appendHandAction(action);
			table.updatePlayers();

			if (action == SHOWDOWN) {
				break;
			}

			action.execute(this);
			if (!isHandContested()) {
				currentHandPhase = SHOWDOWN;
				handActionLog.appendHandAction(SHOWDOWN);
				break;
			}
		}

		resolveHand();
	}

	private boolean isHandContested() {
		int stillInHand = 0;
		for (Seat seat : seatsForHand) {
			if (!seat.isFolded()) {
				stillInHand++;
			}
		}

		return stillInHand > 1;
	}

	public void resolveHand() {
		List<Seat> winners;
		List<Pot> potsByContestors = pot.getPots();

		for (Seat seat : seatsForHand) {
			if (!seat.isFolded()) {
				handActionLog.appendPlayerAction(new ShowAction(seat));
			}
		}

		for (Pot pot : potsByContestors) {
			winners = handGame.determineWinners(communityCards, pot.getContestors());

			divideWinningsAmongWinners(pot.getPotSize(), winners);
		}

		table.updatePlayers();
	}

	private void divideWinningsAmongWinners(final Long pot, final List<Seat> winners) {
		if (winners.size() == 0) {
			throw new RuntimeException("Pot of size " + pot + " being divided between no players.");
		}

		long amountForEachPlayer = pot / winners.size();
		for (Seat winner : winners) {
			winner.addChips(amountForEachPlayer);
		}

		// If there is any remainder, give it to the first player in the list.
		long remainder = pot - amountForEachPlayer;
		if (remainder != 0) {
			winners.get(0).addChips(remainder);
		}
	}

	/**
	 * Deal the specified number of community cards.
	 *
	 * @param number The number of community cards to be dealt.
	 */
	public void dealCommunity(final int number) {
		if (number < 0) {
			throw new IllegalArgumentException("Number must not be negative.");
		}

		for (int i = 0; i < number; i++) {
			if (deck.isEmpty()) {
				throw new RuntimeException("Unable to deal community card - Deck is empty.");
			}

			communityCards.add(deck.deal());
		}
	}

	/**
	 * Deal the specified number of hole cards to the table.
	 *
	 * @param number The number of hole cards to be dealt.
	 */
	public void dealHole(final int number) {
		for (int i = 0; i < number; i++) {
			for (Seat s : seatsForHand) {
				if (!s.isFolded()) {
					s.addHoleCard(deck.deal());
				}
			}
		}
	}

	/**
	 * Deals the specified number of cards to the specified seat.
	 *
	 * @param number The number of cards to deal.
	 * @param seat The seat that will receive the cards.
	 */
	public void dealHole(final int number, final Seat seat) {
		for (int i = 0; i < number; i++) {
			seat.addHoleCard(deck.deal());
		}
	}

	/**
	 * Deal the specified number of hole cards to the table.
	 *
	 * @param number The number of hole cards to be dealt.
	 */
	public void dealExposed(final int number) {
		for (int i = 0; i < number; i++) {
			for (Seat s : seatsForHand) {
				if (!s.isFolded()) {
					s.addHoleCard(deck.deal());
				}
			}
		}
	}

	public void postAntes() {
		long ante = level.getAnte();
		if (ante == 0) {
			return;
		}

		for (Seat s : seatsForHand) {
			pot.add(s, s.bet(ante));
			handActionLog.appendPlayerAction(new PostAction(s.getPlayer(), ANTE, ante));
		}

		table.updatePlayers();
	}

	public void postSmallBlind() {
		long sb = level.getSmallBlind();
		if (sb == 0) {
			return;
		}

		pot.add(seatsForHand[0], seatsForHand[0].bet(sb));
		handActionLog.appendPlayerAction(new PostAction(seatsForHand[0].getPlayer(), SMALL_BLIND, sb));
		table.updatePlayers();
	}

	public void postBigBlind() {
		long bb = level.getBigBlind();
		if (bb == 0) {
			return;
		}

		pot.add(seatsForHand[1], seatsForHand[1].bet(bb));
		handActionLog.appendPlayerAction(new PostAction(seatsForHand[1].getPlayer(), BIG_BLIND, bb));
		table.updatePlayers();
	}

	public void limitBettingRound(final boolean bigStreet) {
		// TODO
	}

	public void potLimitBettingRound() {
		// TODO
	}

	public void noLimitBettingRound() {
		bettingRoundNum++;
		new NoLimitBettingRound(seatsForHand, pot, handActionLog,
				level, bettingRoundNum, TURN_TIMEOUT, table).run();
		pot.returnUncalledBet();
	}

	public void drawAction(final int drawLimit) {
		for (Seat s : seatsForHand) {
			if (s.isFolded()) {
				continue;
			}

			List<Card> discarded = s.getDiscards(drawLimit);
			for (Card c : discarded) {
				c.discard();
			}

			dealHole(discarded.size(), s);
		}
	}

	public Seat[] getSeatsForHand() {
		return seatsForHand;
	}

	public HandAction getPhase() {
		return currentHandPhase;
	}

	public HandActionLog getHandActionLog() {
		return handActionLog;
	}

	public int getHandNum() {
		return handNum;
	}

	public Date getHandStart() {
		return handStart;
	}

	public List<Card> getCommunityCards() {
		return communityCards;
	}

	public List<Pot> getCurrentPots() {
		return pot.getPots();
	}
}
