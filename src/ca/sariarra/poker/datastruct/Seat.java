package ca.sariarra.poker.datastruct;

import static ca.sariarra.util.ParamUtil.ensureNotNull;

public class Seat {
	private Player player;
	private Hand hand;
	private ChipStack chipStack;
	private boolean sittingOut;
	
	public Seat(Player p) {
		ensureNotNull("Player", p);
		player = p;
		sittingOut = true;
	}
	
	public void setChips(ChipStack cs) {
		if (chipStack == null) {
			chipStack = cs;
		}
	}
	
	public ChipStack getChipStack() {
		return chipStack;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public Hand getHand() {
		return hand;
	}
	
	public void setSittingOut(boolean sitting) {
		sittingOut = sitting;
	}
	
	public boolean isSittingOut() {
		return sittingOut;
	}
}
