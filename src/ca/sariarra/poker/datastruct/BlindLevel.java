package ca.sariarra.poker.datastruct;

public class BlindLevel {
	private Double bigBlind;
	private Double smallBlind;
	private Double ante;	
	
	public BlindLevel(Double bigBlind, Double smallBlind, Double ante) {
		this.bigBlind = bigBlind;
		this.smallBlind = smallBlind;
		this.ante = ante;
	}
	
	public Double getBigBlind() {
		return bigBlind;
	}
	
	public Double getSmallBlind() {
		return smallBlind;
	}
	
	public Double getAnte() {
		return ante;
	}
	
}
