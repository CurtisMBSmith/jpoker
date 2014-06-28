package ca.sariarra.poker.table;

import static ca.sariarra.poker.game.action.HandAction.SHOWDOWN;
import static ca.sariarra.poker.player.actions.PlayerAction.BET;
import static ca.sariarra.poker.player.actions.PlayerAction.CALL;
import static ca.sariarra.poker.player.actions.PlayerAction.CHECK;
import static ca.sariarra.poker.player.actions.PlayerAction.FOLD;
import static ca.sariarra.poker.player.actions.PlayerAction.RAISE;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import ca.sariarra.poker.card.Card;
import ca.sariarra.poker.game.PokerGame;
import ca.sariarra.poker.game.action.HandAction;
import ca.sariarra.poker.game.component.HandDetails;
import ca.sariarra.poker.player.Player;
import ca.sariarra.poker.player.actions.Action;
import ca.sariarra.poker.player.actions.AvailableActions;
import ca.sariarra.poker.player.actions.PlayerAction;
import ca.sariarra.poker.table.component.BlindLevel;
import ca.sariarra.poker.table.component.Deck;
import ca.sariarra.poker.table.component.Pot;
import ca.sariarra.poker.table.component.PotManager;
import ca.sariarra.poker.table.component.Seat;

public abstract class Table implements Runnable {
	private static final long SLEEP_DURATION = 3000;

	private final Deck deck;
	private final List<Card> communityCards;
	protected final Seat[] seats;
	protected Seat[] seatsForHand;
	private final List<Player> waitList;
	private final boolean isCashTable;
	private final long tableNum;
	private PotManager pot;

	private final long tableStart;
	private final long breakTime;
	private final long TURN_TIMEOUT = 600000;
	private boolean closed;
	protected int button;
	private final PokerGame game;

	private Date handStart;

	public Table(final int numSeats, final boolean pIsCashTable, final PokerGame pGame, final long pTableNum) {
		if (numSeats <= 0) {
			throw new IllegalArgumentException("Number of seats must be positive.");
		}

		seats = new Seat[numSeats];
		isCashTable = pIsCashTable;
		game = pGame;
		tableNum = pTableNum;

		waitList = new ArrayList<Player>();
		closed = false;
		button = 0;
		tableStart = new Date().getTime();
		breakTime = 0;
		deck = new Deck();
		communityCards = new ArrayList<Card>(5);
	}

	@Override
	public void run() {
		while (!closed) {
			if (dealNextHand()) {
				setUpTableForNewHand();

				HandDetails actions = game.getHandDetails(new Date().getTime() - tableStart - breakTime);
				runHand(actions.getActions());
				resolveHand(actions.getGame());
				closeHand();

				resetTableState();
			}
			else {
				try {
					Thread.sleep(SLEEP_DURATION);
				}
				catch (InterruptedException e) {
					throw new RuntimeException("Table " + tableNum + "'s run thread interrupted while sleeping.", e);
				}
			}
		}

	}

	protected abstract void closeHand();

	private void resolveHand(final PokerGame game) {
		List<Seat> winners;
		List<Pot> potsByContestors = pot.groupPotsByContestors();
		for (Pot pot : potsByContestors) {
			winners = game.determineWinners(communityCards, pot.getContestors());

			divideWinningsAmongWinners(pot.getAmount(), winners);
		}
	}

	private void divideWinningsAmongWinners(final Long pot, final List<Seat> winners) {
		if (winners.size() == 0) {
			throw new RuntimeException("Pot of size " + pot + " being divided between no players.");
		}

		long amountForEachPlayer = pot / winners.size();
		for (Seat winner : winners) {
			winner.giveWinnings(amountForEachPlayer);
		}

		// If there is any remainder, give it to the first player in the list.
		long remainder = pot - amountForEachPlayer;
		if (remainder != 0) {
			winners.get(0).giveWinnings(remainder);
		}
	}

	private void resetTableState() {
		// Remove the cards of any player that participated in the hand.
		for (Seat s : seatsForHand) {
			s.getHand().resetHand();
		}

		// Return the community cards to the deck.
		for (Card c : communityCards) {
			c.discard();
		}
		communityCards.clear();

		deck.reshuffle();
		deck.shuffle();
		if (deck.size() < 52) {
			throw new RuntimeException("Deck does not have 52 cards after reset.");
		}

		// If any seat on the table is now empty, remove the seat.
		for (int i = 0; i < seats.length; i++) {
			if (seats[i] == null) {
				continue;
			}

			if (seats[i].getPlayer() == null) {
				seats[i] = null;
			}
		}
	}

	private void setUpTableForNewHand() {
		handStart = new Date();
		moveButton();
		setSeatsForHand();
		pot.reset(seatsForHand);
		deck.shuffle();
	}

	protected abstract boolean dealNextHand();

	protected abstract void setSeatsForHand();

	protected abstract BlindLevel getBlindLevel(Date time);

	private void runHand(final HandAction[] handActions) {
		for (HandAction action : handActions) {
			if (action == SHOWDOWN) {
				break;
			}

			action.execute(this);
			if (!isHandContested()) {
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

	public void addPlayer(final Player p) {
		if (p == null) {
			throw new IllegalArgumentException("Player must not be null.");
		}

		Random rand = new Random();

		int j = rand.nextInt(seats.length);
		for (int i = 0; i < seats.length; i++) {
			if (seats[(i + j) % seats.length] == null) {
				seats[(i + j) % seats.length] = new Seat(p);
				return;
			}
		}
	}

	public void moveButton() {
		for (int i = 1; i < seats.length; i++) {
			if (seats[(button + i) % seats.length] == null) {
				continue;
			}
			if (isCashTable && seats[(button + i) % seats.length].isSittingOut()) {
				continue;
			}
			button = (button + i) % seats.length;
			break;
		}
	}

	public int playersRemaining() {
		int count = 0;
		for (Seat s : seats) {
			if (s != null && !s.getChipStack().isEmpty()) {
				count++;
			}
		}

		return count;
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
		long ante = getBlindLevel(handStart).getAnte();
		if (ante == 0) {
			return;
		}

		for (Seat s : seatsForHand) {
			pot.add(s, s.bet(ante));
		}
	}

	public void postSmallBlind() {
		long sb = getBlindLevel(handStart).getSmallBlind();
		if (sb == 0) {
			return;
		}

		pot.add(seatsForHand[0], seatsForHand[0].bet(sb));
	}

	public void postBigBlind() {
		long bb = getBlindLevel(handStart).getBigBlind();
		if (bb == 0) {
			return;
		}

		pot.add(seatsForHand[0], seatsForHand[0].bet(bb));
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

		BlindLevel currentLevel = getBlindLevel(handStart);

		int turn;
		AvailableActions actions;
		Action pAction;
		Seat seatTurn;
		for (int i = 0; i < seatsForHand.length && pot.hasUncalledBet(); i++) {
			turn = i % seatsForHand.length;
			seatTurn = seatsForHand[turn];
			if (pot.uncalledBettor() == seatTurn) {
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
							pot.getUncalledBet(seatTurn), currentLevel.getBigBlind(), null);
				}
				else {
					actions = new AvailableActions(new PlayerAction[] {CHECK, BET, FOLD},
							null, currentLevel.getBigBlind(), null);
				}
				break;
			case 1:
				if (pot.hasUncalledBet()) {
					actions = new AvailableActions(new PlayerAction[] {CALL, RAISE, FOLD},
							pot.getUncalledBet(seatTurn), currentLevel.getBigBlind(), pot.getSize());
				}
				else {
					actions = new AvailableActions(new PlayerAction[] {CHECK, BET, FOLD},
							null, currentLevel.getBigBlind(), pot.getSize());
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
								pot.getUncalledBet(seatTurn), currentLevel.getBigBlind(), currentLevel.getBigBlind());
					}
				}
				else {
					actions = new AvailableActions(new PlayerAction[] {CHECK, BET, FOLD},
							null, currentLevel.getBigBlind(), currentLevel.getBigBlind());
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
								pot.getUncalledBet(seatTurn), currentLevel.getBigBlind()* 2, currentLevel.getBigBlind() * 2);
					}
				}
				else {
					actions = new AvailableActions(new PlayerAction[] {CHECK, BET, FOLD},
							null, currentLevel.getBigBlind() * 2, currentLevel.getBigBlind() * 2);
				}
				break;
			default: throw new RuntimeException("Illegal limit type param. THIS SHOULD NOT HAPPEN.");
			}

			long turnStart = System.currentTimeMillis();
			pAction = seatTurn.getPlayerAction(actions);
			while (!actions.validate(pAction)) {
				if (System.currentTimeMillis() - turnStart > TURN_TIMEOUT) {
					if (pot.hasUncalledBet()) {
						pAction = new Action(FOLD);
					}
					else {
						pAction = new Action(CHECK);
					}

					break;
				}

				pAction = seatTurn.getPlayerAction(actions);
			}

			resolvePlayerAction(seatTurn, pAction);
		}

		pot.returnUncalledBet();
	}

	private void resolvePlayerAction(final Seat seat, final Action action) {
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

	/*
	 * Accessors/Mutators
	 */

	public boolean isCashTable() {
		return isCashTable;
	}

	public boolean isClosed() {
		return closed;
	}

	public void setClosed(final boolean close) {
		closed = close;
	}

	public List<Player> getWaitList() {
		return waitList;
	}


}
