package ca.sariarra.poker.table.component;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ca.sariarra.poker.hand.component.Pot;
import ca.sariarra.poker.hand.component.PotManager;
import ca.sariarra.poker.player.ai.MrStupid;

public class TestPotManager {
	private Seat seat1;
	private Seat seat2;
	private Seat seat3;
	private PotManager pot;

	@Before
	public void setUp() {
		seat1 = new Seat(new MrStupid("Stupid1"));
		seat1.addChips(10000l);

		seat2 = new Seat(new MrStupid("Stupid2"));
		seat2.addChips(10000l);

		seat3 = new Seat(new MrStupid("Stupid3"));
		seat3.addChips(4000l);

		pot = new PotManager();
	}

	@Test
	public void testAdd() {
		pot.add(seat1, seat1.bet(1000l));
		assertEquals("Seat 1 chips not what was expected.", 9000l, seat1.getChips());
		assertEquals("Uncalled bet amount for seat 1 not what was expected.", 0l, pot.getUncalledBet(seat1));
		assertEquals("Uncalled bet amount for seat 2 not what was expected.", 1000l, pot.getUncalledBet(seat2));

		pot.add(seat2, seat2.bet(1500l));
		assertEquals("Seat 2 chips not what was expected.", 8500l, seat2.getChips());
		assertEquals("Uncalled bet amount for seat 1 not what was expected.", 500l, pot.getUncalledBet(seat1));
		assertEquals("Uncalled bet amount for seat 2 not what was expected.", 0l, pot.getUncalledBet(seat2));

		pot.add(seat1, seat1.bet(700l));
		assertEquals("Seat 1 chips not what was expected.", 8300l, seat1.getChips());
		assertEquals("Uncalled bet amount for seat 1 not what was expected.", 0l, pot.getUncalledBet(seat1));
		assertEquals("Uncalled bet amount for seat 2 not what was expected.", 200l, pot.getUncalledBet(seat2));
	}

	@Test
	public void testReturnUncalledBet() {
		seat3.fold();

		pot.add(seat1, seat1.bet(1000l));
		assertEquals("Seat 1 chips not what was expected.", 9000l, seat1.getChips());
		assertEquals("Uncalled bet amount for seat 1 not what was expected.", 0l, pot.getUncalledBet(seat1));
		assertEquals("Uncalled bet amount for seat 2 not what was expected.", 1000l, pot.getUncalledBet(seat2));

		pot.add(seat2, seat2.bet(1500l));
		assertEquals("Seat 2 chips not what was expected.", 8500l, seat2.getChips());
		assertEquals("Uncalled bet amount for seat 1 not what was expected.", 500l, pot.getUncalledBet(seat1));
		assertEquals("Uncalled bet amount for seat 2 not what was expected.", 0l, pot.getUncalledBet(seat2));

		pot.add(seat1, seat1.bet(700l));
		assertEquals("Seat 1 chips not what was expected.", 8300l, seat1.getChips());
		assertEquals("Uncalled bet amount for seat 1 not what was expected.", 0l, pot.getUncalledBet(seat1));
		assertEquals("Uncalled bet amount for seat 2 not what was expected.", 200l, pot.getUncalledBet(seat2));

		pot.returnUncalledBet();
		assertEquals("Seat 1 chips not what was expected.", 8500l, seat2.getChips());
		assertEquals("Seat 2 chips not what was expected.", 8500l, seat2.getChips());
		assertEquals("Uncalled bet amount for seat 1 not what was expected.", 0l, pot.getUncalledBet(seat1));
		assertEquals("Uncalled bet amount for seat 2 not what was expected.", 0l, pot.getUncalledBet(seat2));
	}

	@Test
	public void testGroupPotsByContestors() {
		List<Pot> result;
		Pot exp;

		pot.add(seat1, seat1.bet(1000l));
		assertEquals("Seat 1 chips not what was expected.", 9000l, seat1.getChips());
		assertEquals("Uncalled bet amount for seat 1 not what was expected.", 0l, pot.getUncalledBet(seat1));
		assertEquals("Uncalled bet amount for seat 2 not what was expected.", 1000l, pot.getUncalledBet(seat2));
		assertEquals("Uncalled bet amount for seat 3 not what was expected.", 1000l, pot.getUncalledBet(seat3));
		assertEquals("Pot total not what was expected", 1000l, pot.getTotalSize().longValue());

		result = pot.getPots();
		exp = findPotInList(result, 1000l, Collections.singletonList(seat1));
		assertNotNull("Did not find pot of expected amount with expected contestors.", exp);

		pot.add(seat2, seat2.bet(1500l));
		assertEquals("Seat 2 chips not what was expected.", 8500l, seat2.getChips());
		assertEquals("Uncalled bet amount for seat 1 not what was expected.", 500l, pot.getUncalledBet(seat1));
		assertEquals("Uncalled bet amount for seat 2 not what was expected.", 0l, pot.getUncalledBet(seat2));
		assertEquals("Uncalled bet amount for seat 3 not what was expected.", 1500l, pot.getUncalledBet(seat3));
		assertEquals("Pot total not what was expected", 2500l, pot.getTotalSize().longValue());

		result = pot.getPots();
		exp = findPotInList(result, 2500l, Arrays.asList(seat1, seat2));
		assertNotNull("Did not find pot of expected amount with expected contestors.", exp);

		pot.add(seat3, seat3.bet(4500l));
		assertEquals("Seat 3 chips not what was expected.", 0l, seat3.getChips());
		assertTrue("Seat 3 is not all in.", seat3.isAllIn());
		assertEquals("Uncalled bet amount for seat 1 not what was expected.", 3000l, pot.getUncalledBet(seat1));
		assertEquals("Uncalled bet amount for seat 2 not what was expected.", 2500l, pot.getUncalledBet(seat2));
		assertEquals("Uncalled bet amount for seat 3 not what was expected.", 0l, pot.getUncalledBet(seat3));
		assertEquals("Pot total not what was expected", 6500l, pot.getTotalSize().longValue());

		result = pot.getPots();
		exp = findPotInList(result, 6500l, Arrays.asList(seat1, seat2, seat3));
		assertNotNull("Did not find pot of expected amount with expected contestors.", exp);

		pot.add(seat1, seat1.bet(4000l));
		assertEquals("Seat 1 chips not what was expected.", 5000l, seat1.getChips());
		assertEquals("Uncalled bet amount for seat 1 not what was expected.", 0l, pot.getUncalledBet(seat1));
		assertEquals("Uncalled bet amount for seat 2 not what was expected.", 3500l, pot.getUncalledBet(seat2));
		assertEquals("Uncalled bet amount for seat 3 not what was expected.", 1000l, pot.getUncalledBet(seat3));
		assertEquals("Pot total not what was expected", 10500l, pot.getTotalSize().longValue());

		result = pot.getPots();
		exp = findPotInList(result, 9500l, Arrays.asList(seat1, seat2, seat3));
		assertNotNull("Did not find pot of expected amount with expected contestors.", exp);
		exp = findPotInList(result, 1000l, Arrays.asList(seat1));
		assertNotNull("Did not find pot of expected amount with expected contestors.", exp);
	}

	@Test
	public void testPlayerAllInBetLessThanToCall() {
		List<Pot> result;
		Pot exp;
		Seat s1 = new Seat(new MrStupid("Stupid1"));
		s1.addChips(284);

		Seat s2 = new Seat(new MrStupid("Stupid2"));
		s2.addChips(1500);

		Seat s3 = new Seat(new MrStupid("Stupid3"));
		s3.addChips(1490);

		Seat s4 = new Seat(new MrStupid("Stupid4"));
		s4.addChips(274);

		Seat s5 = new Seat(new MrStupid("Stupid5"));
		s5.addChips(3942);

		Seat s6 = new Seat(new MrStupid("Stupid6"));
		s6.addChips(1500);

		// Post blinds.
		pot.add(s4, s4.bet(10));
		pot.add(s5, s5.bet(20));
		result = pot.getPots();
		exp = findPotInList(result, 30l, Arrays.asList(s4, s5));
		assertNotNull("Did not find pot of expected amount with expected contestors.", exp);

		// Do the betting round.
		pot.add(s6, s6.bet(20));
		pot.add(s1, s1.bet(20));
		s2.fold();
		pot.add(s3, s3.bet(621));
		pot.add(s4, s4.bet(274));
		s5.fold();
		s6.fold();
		s1.fold();

		pot.returnUncalledBet();

		assertChipstackAsExpected(s1, 264, 1);
		assertChipstackAsExpected(s2, 1500, 2);
		assertChipstackAsExpected(s3, 1216, 3);
		assertChipstackAsExpected(s4, 0, 4);
		assertChipstackAsExpected(s5, 3922, 5);
		assertChipstackAsExpected(s6, 1480, 6);

		result = pot.getPots();
		exp = findPotInList(result, 618l, Arrays.asList(s3, s4));
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

	private void assertChipstackAsExpected(final Seat seat, final long expected, final int seatNum) {
		assertEquals("Seat " + seatNum + " chips not what was expected.", expected, seat.getChips());
	}
}
