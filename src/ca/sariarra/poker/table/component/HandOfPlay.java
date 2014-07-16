package ca.sariarra.poker.table.component;

import static ca.sariarra.poker.game.action.HandAction.SHOWDOWN;
import static ca.sariarra.poker.player.actions.ForcedBet.ANTE;
import static ca.sariarra.poker.player.actions.ForcedBet.BIG_BLIND;
import static ca.sariarra.poker.player.actions.ForcedBet.SMALL_BLIND;
import static ca.sariarra.poker.player.actions.PlayerAction.BET;
import static ca.sariarra.poker.player.actions.PlayerAction.CALL;
import static ca.sariarra.poker.player.actions.PlayerAction.CHECK;
import static ca.sariarra.poker.player.actions.PlayerAction.FOLD;
import static ca.sariarra.poker.player.actions.PlayerAction.RAISE;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ca.sariarra.poker.card.Card;
import ca.sariarra.poker.game.PokerGame;
import ca.sariarra.poker.game.action.HandAction;
import ca.sariarra.poker.player.actions.AvailableActions;
import ca.sariarra.poker.player.actions.PlayerAction;
import ca.sariarra.poker.player.actions.PostAction;
import ca.sariarra.poker.player.actions.ShowAction;
import ca.sariarra.poker.player.actions.StandardAction;
import ca.sariarra.poker.table.Table;

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

	public HandOfPlay(final Table table, final PokerGame handGame, final HandAction[] handActions,
			final BlindLevel level, final int handNum) {
		deck = new Deck();
		communityCards = new ArrayList<Card>(5);
		currentHandPhase = null;
		handStart = new Date();
		pot = new PotManager();
		this.handGame = handGame;
		this.handActions = handActions;
		handActionLog = new HandActionLog();
		this.table = table;
		this.level = level;
		this.handNum = handNum;
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
		List<Pot> potsByContestors = pot.groupPotsByContestors();

		for (Seat seat : seatsForHand) {
			if (!seat.isFolded()) {
				handActionLog.appendPlayerAction(new ShowAction(seat));
			}
		}

		for (Pot pot : potsByContestors) {
			winners = handGame.determineWinners(communityCards, pot.getContestors());

			divideWinningsAmongWinners(pot.getAmount(), winners);
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

	/**
	 * Initiates a betting round based on the type param.
	 * Types:
	 * 	 - 0: No limit
	 *   - 1: Pot limit
	 *   - 2: Limit
	 *   - 3: Limit (big bet street)
	 *
	 * @param type The type in range [0,3]
	 */
	private void bettingRound(final int type) {
		if (type < 0 || type > 3) {
			throw new IllegalArgumentException("Type of betting round must be between 0 and 3");
		}

		int playersInHandNotAllIn = 0;
		for (Seat seat : seatsForHand) {
			if (seat.isFolded() || seat.isAllIn()) {
				continue;
			}

			playersInHandNotAllIn++;
		}

		if (playersInHandNotAllIn <= 1) {
			return;
		}

		int turn;
		AvailableActions actions;
		StandardAction pAction;
		Seat seatTurn;
		int ind = 0;
		if (pot.hasUncalledBet()) {
			for (ind = 0; ind < seatsForHand.length; ind++) {
				if (seatsForHand[ind] == pot.uncalledBettor()) {
					break;
				}
			}

			ind = (ind + 1) % seatsForHand.length;
		}
		for (int i = 0 + ind; i < seatsForHand.length + ind || pot.hasUncalledBet(); i++) {
			turn = i % seatsForHand.length;
			seatTurn = seatsForHand[turn];
			if (pot.uncalledBettor() == seatTurn && i + ind >= seatsForHand.length) {
				break;
			}
			if (seatTurn.isFolded()) {
				continue;
			}

			if (seatTurn.isAllIn()) {
				continue;
			}

			// TODO Lambda-ize?
			switch(type) {
			case 0:
				if (pot.hasUncalledBet()) {
					actions = new AvailableActions(new PlayerAction[] {CALL, RAISE, FOLD},
							pot.getUncalledBet(seatTurn), level.getBigBlind(), null);
				}
				else {
					actions = new AvailableActions(new PlayerAction[] {CHECK, BET, FOLD},
							null, level.getBigBlind(), null);
				}
				break;
			case 1:
				if (pot.hasUncalledBet()) {
					actions = new AvailableActions(new PlayerAction[] {CALL, RAISE, FOLD},
							pot.getUncalledBet(seatTurn), level.getBigBlind(), pot.getSize());
				}
				else {
					actions = new AvailableActions(new PlayerAction[] {CHECK, BET, FOLD},
							null, level.getBigBlind(), pot.getSize());
				}
				break;
			case 2:
				if (pot.hasUncalledBet()) {
					if (i / seatsForHand.length >= 3) {
						actions = new AvailableActions(new PlayerAction[] {CALL, FOLD},
								pot.getUncalledBet(seatTurn), null, null);
					}
					else {
						actions = new AvailableActions(new PlayerAction[] {CALL, RAISE, FOLD},
								pot.getUncalledBet(seatTurn), level.getBigBlind(), level.getBigBlind());
					}
				}
				else {
					actions = new AvailableActions(new PlayerAction[] {CHECK, BET, FOLD},
							null, level.getBigBlind(), level.getBigBlind());
				}
				break;
			case 3:
				if (pot.hasUncalledBet()) {
					if (i / seatsForHand.length >= 3) {
						actions = new AvailableActions(new PlayerAction[] {CALL, FOLD},
								pot.getUncalledBet(seatTurn), null, null);
					}
					else {
						actions = new AvailableActions(new PlayerAction[] {CALL, RAISE, FOLD},
								pot.getUncalledBet(seatTurn), level.getBigBlind()* 2, level.getBigBlind() * 2);
					}
				}
				else {
					actions = new AvailableActions(new PlayerAction[] {CHECK, BET, FOLD},
							null, level.getBigBlind() * 2, level.getBigBlind() * 2);
				}
				break;
			default: throw new RuntimeException("Illegal limit type param. THIS SHOULD NOT HAPPEN.");
			}

			long turnStart = System.currentTimeMillis();
			pAction = seatTurn.getPlayerAction(actions);
			while (!actions.validate(pAction)) {
				if (System.currentTimeMillis() - turnStart > TURN_TIMEOUT) {
					if (pot.hasUncalledBet()) {
						pAction = new StandardAction(seatTurn.getPlayer(), FOLD);
					}
					else {
						pAction = new StandardAction(seatTurn.getPlayer(), CHECK);
					}

					break;
				}

				pAction = seatTurn.getPlayerAction(actions);
			}

			resolvePlayerAction(seatTurn, pAction);
			table.updatePlayers();
		}

		pot.returnUncalledBet();
		table.updatePlayers();
	}

	private void resolvePlayerAction(final Seat seat, final StandardAction action) {
		switch(action.getAction()) {
		case CHECK:
			break;
		case CALL:
		case RAISE:
		case BET:
			pot.add(seat, seat.bet(action.getBetAmount()));
			break;
		case FOLD:
			seat.fold();
			break;
		}

		handActionLog.appendPlayerAction(action);
	}

	public void limitBettingRound(final boolean bigStreet) {
		bettingRound(bigStreet ? 3 : 2);
	}

	public void potLimitBettingRound() {
		bettingRound(1);
	}

	public void noLimitBettingRound() {
		bettingRound(0);
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
		return pot.groupPotsByContestors();
	}
}
