package ca.sariarra.poker.datastruct;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Table {
	private Seat[] seats;
	private List<Player> waitList;
	private boolean isCashTable;
	private boolean closed;
	private int button;
	
	public Table(int numSeats, boolean pIsCashTable) {
		if (numSeats <= 0) {
			throw new IllegalArgumentException("Number of seats must be positive.");
		}
		
		isCashTable = pIsCashTable;
		seats = new Seat[numSeats];
		waitList = new ArrayList<Player>();
		closed = false;
		button = 0;
	}
	
	public void addPlayer(Player p) {
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
	
	public Seat[] getSeats() {
		return seats;
	}
	
	public List<Player> getWaitList() {
		return waitList;
	}
	
	public boolean isCashTable() {
		return isCashTable;
	}
	
	/**
	 * 
	 */
	public void runTable() {
		while (true) {
			
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

	public boolean isClosed() {
		return closed;
	}
	
	public void setClosed(boolean close) {
		closed = close;
	}
}
