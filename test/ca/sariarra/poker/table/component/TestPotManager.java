package ca.sariarra.poker.table.component;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ca.sariarra.poker.player.ai.MrStupid;

public class TestPotManager {
	private Seat seat1;
	private Seat seat2;
	private Seat seat3;
	private PotManager pot;

	@Before
	public void setUp() {
		seat1 = new Seat(new MrStupid("Stupid1"));
		seat1.setChips(new ChipStack(false, 10000l));

		seat2 = new Seat(new MrStupid("Stupid2"));
		seat2.setChips(new ChipStack(false, 10000l));

		seat3 = new Seat(new MrStupid("Stupid3"));
		seat3.setChips(new ChipStack(false, 4000l));

		pot = new PotManager();
		pot.reset(new Seat[] {seat1, seat2, seat3});
	}

	@Test
	public void testAdd() {
		pot.add(seat1, seat1.bet(1000l));
		assertEquals("Seat 1 chips not what was expected.", 9000l, seat1.getChips());
		assertNotNull("Uncalled bettor is null.", pot.uncalledBettor());
		assertTrue("Seat 1 is not the uncalled bettor.", pot.uncalledBettor() == seat1);
		assertTrue("Has uncalled bettor is false.", pot.hasUncalledBet());
		assertEquals("Uncalled bet amount for seat 1 not what was expected.", 0l, pot.getUncalledBet(seat1));
		assertEquals("Uncalled bet amount for seat 2 not what was expected.", 1000l, pot.getUncalledBet(seat2));

		pot.add(seat2, seat2.bet(1500l));
		assertEquals("Seat 2 chips not what was expected.", 8500l, seat2.getChips());
		assertNotNull("Uncalled bettor is null.", pot.uncalledBettor());
		assertTrue("Seat 2 is not the uncalled bettor.", pot.uncalledBettor() == seat2);
		assertTrue("Has uncalled bettor is false.", pot.hasUncalledBet());
		assertEquals("Uncalled bet amount for seat 1 not what was expected.", 500l, pot.getUncalledBet(seat1));
		assertEquals("Uncalled bet amount for seat 2 not what was expected.", 0l, pot.getUncalledBet(seat2));

		pot.add(seat1, seat1.bet(300l));
		assertEquals("Seat 1 chips not what was expected.", 8700l, seat1.getChips());
		assertNotNull("Uncalled bettor is null.", pot.uncalledBettor());
		assertTrue("Seat 2 is not the uncalled bettor.", pot.uncalledBettor() == seat2);
		assertTrue("Has uncalled bettor is false.", pot.hasUncalledBet());
		assertEquals("Uncalled bet amount for seat 1 not what was expected.", 200l, pot.getUncalledBet(seat1));
		assertEquals("Uncalled bet amount for seat 2 not what was expected.", 0l, pot.getUncalledBet(seat2));
	}

	@Test
	public void testReturnUncalledBet() {
		pot.add(seat1, seat1.bet(1000l));
		assertEquals("Seat 1 chips not what was expected.", 9000l, seat1.getChips());
		assertNotNull("Uncalled bettor is null.", pot.uncalledBettor());
		assertTrue("Seat 1 is not the uncalled bettor.", pot.uncalledBettor() == seat1);
		assertTrue("Has uncalled bettor is false.", pot.hasUncalledBet());
		assertEquals("Uncalled bet amount for seat 1 not what was expected.", 0l, pot.getUncalledBet(seat1));
		assertEquals("Uncalled bet amount for seat 2 not what was expected.", 1000l, pot.getUncalledBet(seat2));

		pot.add(seat2, seat2.bet(1500l));
		assertEquals("Seat 2 chips not what was expected.", 8500l, seat2.getChips());
		assertNotNull("Uncalled bettor is null.", pot.uncalledBettor());
		assertTrue("Seat 2 is not the uncalled bettor.", pot.uncalledBettor() == seat2);
		assertTrue("Has uncalled bettor is false.", pot.hasUncalledBet());
		assertEquals("Uncalled bet amount for seat 1 not what was expected.", 500l, pot.getUncalledBet(seat1));
		assertEquals("Uncalled bet amount for seat 2 not what was expected.", 0l, pot.getUncalledBet(seat2));

		pot.add(seat1, seat1.bet(300l));
		assertEquals("Seat 1 chips not what was expected.", 8700l, seat1.getChips());
		assertNotNull("Uncalled bettor is null.", pot.uncalledBettor());
		assertTrue("Seat 2 is not the uncalled bettor.", pot.uncalledBettor() == seat2);
		assertTrue("Has uncalled bettor is false.", pot.hasUncalledBet());
		assertEquals("Uncalled bet amount for seat 1 not what was expected.", 200l, pot.getUncalledBet(seat1));
		assertEquals("Uncalled bet amount for seat 2 not what was expected.", 0l, pot.getUncalledBet(seat2));

		pot.returnUncalledBet();
		assertEquals("Seat 1 chips not what was expected.", 8700l, seat2.getChips());
		assertNull("Uncalled bettor is not null.", pot.uncalledBettor());
		assertFalse("Has uncalled bettor is true.", pot.hasUncalledBet());
		assertEquals("Uncalled bet amount for seat 1 not what was expected.", 0l, pot.getUncalledBet(seat1));
		assertEquals("Uncalled bet amount for seat 2 not what was expected.", 0l, pot.getUncalledBet(seat2));
	}

	@Test
	public void testGroupPotsByContestors() {
		List<Pot> result;
		Pot exp;

		pot.add(seat1, seat1.bet(1000l));
		assertEquals("Seat 1 chips not what was expected.", 9000l, seat1.getChips());
		assertNotNull("Uncalled bettor is null.", pot.uncalledBettor());
		assertTrue("Seat 1 is not the uncalled bettor.", pot.uncalledBettor() == seat1);
		assertTrue("Has uncalled bettor is false.", pot.hasUncalledBet());
		assertEquals("Uncalled bet amount for seat 1 not what was expected.", 0l, pot.getUncalledBet(seat1));
		assertEquals("Uncalled bet amount for seat 2 not what was expected.", 1000l, pot.getUncalledBet(seat2));
		assertEquals("Uncalled bet amount for seat 3 not what was expected.", 1000l, pot.getUncalledBet(seat3));
		assertEquals("Pot total not what was expected", 1000l, pot.getSize().longValue());

		result = pot.groupPotsByContestors();
		exp = findPotInList(result, 1000l, Collections.singletonList(seat1));
		assertNotNull("Did not find pot of expected amount with expected contestors.", exp);

		pot.add(seat2, seat2.bet(1500l));
		assertEquals("Seat 2 chips not what was expected.", 8500l, seat2.getChips());
		assertNotNull("Uncalled bettor is null.", pot.uncalledBettor());
		assertTrue("Seat 2 is not the uncalled bettor.", pot.uncalledBettor() == seat2);
		assertTrue("Has uncalled bettor is false.", pot.hasUncalledBet());
		assertEquals("Uncalled bet amount for seat 1 not what was expected.", 500l, pot.getUncalledBet(seat1));
		assertEquals("Uncalled bet amount for seat 2 not what was expected.", 0l, pot.getUncalledBet(seat2));
		assertEquals("Uncalled bet amount for seat 3 not what was expected.", 1500l, pot.getUncalledBet(seat3));
		assertEquals("Pot total not what was expected", 2500l, pot.getSize().longValue());

		result = pot.groupPotsByContestors();
		exp = findPotInList(result, 2000l, Arrays.asList(seat1, seat2));
		assertNotNull("Did not find pot of expected amount with expected contestors.", exp);
		exp = findPotInList(result, 500l, Collections.singletonList(seat2));
		assertNotNull("Did not find pot of expected amount with expected contestors.", exp);

		pot.add(seat3, seat3.bet(4500l));
		assertEquals("Seat 3 chips not what was expected.", 0l, seat3.getChips());
		assertTrue("Seat 3 is not all in.", seat3.isAllIn());
		assertNotNull("Uncalled bettor is null.", pot.uncalledBettor());
		assertTrue("Seat 3 is not the uncalled bettor.", pot.uncalledBettor() == seat3);
		assertTrue("Has uncalled bettor is false.", pot.hasUncalledBet());
		assertEquals("Uncalled bet amount for seat 1 not what was expected.", 3000l, pot.getUncalledBet(seat1));
		assertEquals("Uncalled bet amount for seat 2 not what was expected.", 2500l, pot.getUncalledBet(seat2));
		assertEquals("Uncalled bet amount for seat 3 not what was expected.", 0l, pot.getUncalledBet(seat3));
		assertEquals("Pot total not what was expected", 6500l, pot.getSize().longValue());

		result = pot.groupPotsByContestors();
		exp = findPotInList(result, 3000l, Arrays.asList(seat1, seat2, seat3));
		assertNotNull("Did not find pot of expected amount with expected contestors.", exp);
		exp = findPotInList(result, 1000l, Arrays.asList(seat2, seat3));
		assertNotNull("Did not find pot of expected amount with expected contestors.", exp);
		exp = findPotInList(result, 2500l, Collections.singletonList(seat3));
		assertNotNull("Did not find pot of expected amount with expected contestors.", exp);

		pot.add(seat1, seat1.bet(4000l));
		assertEquals("Seat 1 chips not what was expected.", 5000l, seat1.getChips());
		assertNotNull("Uncalled bettor is null.", pot.uncalledBettor());
		assertTrue("Seat 1 is not the uncalled bettor.", pot.uncalledBettor() == seat1);
		assertTrue("Has uncalled bettor is false.", pot.hasUncalledBet());
		assertEquals("Uncalled bet amount for seat 1 not what was expected.", 0l, pot.getUncalledBet(seat1));
		assertEquals("Uncalled bet amount for seat 2 not what was expected.", 3500l, pot.getUncalledBet(seat2));
		assertEquals("Uncalled bet amount for seat 3 not what was expected.", 1000l, pot.getUncalledBet(seat3));
		assertEquals("Pot total not what was expected", 10500l, pot.getSize().longValue());

		result = pot.groupPotsByContestors();
		exp = findPotInList(result, 4500l, Arrays.asList(seat1, seat2, seat3));
		assertNotNull("Did not find pot of expected amount with expected contestors.", exp);
		exp = findPotInList(result, 5000l, Arrays.asList(seat1, seat3));
		assertNotNull("Did not find pot of expected amount with expected contestors.", exp);
		exp = findPotInList(result, 1000l, Arrays.asList(seat1));
		assertNotNull("Did not find pot of expected amount with expected contestors.", exp);
	}

	@SuppressWarnings("unchecked")
	private <T> void assertEachInList(final List<T> expected, final List<T> actual) {
		for (T exp : expected) {
			assertTrue("Failed to find " + (T) exp.toString() + " in list.", actual.contains(exp));
		}
	}

	private Pot findPotInList(final List<Pot> pots, final Long amount, final List<Seat> contestors) {
		for (Pot p : pots) {
			if (doesPotHaveExpectedContestors(p, contestors)) {
				return p;
			}
		}

		return null;
	}

	private boolean doesPotHaveExpectedContestors(final Pot pot, final List<Seat> contestors) {
		if (pot.getContestors().size() != contestors.size()) {
			return false;
		}

		for (Seat s : contestors) {
			if (!pot.getContestors().contains(s)) {
				return false;
			}
		}

		return true;
	}
}
