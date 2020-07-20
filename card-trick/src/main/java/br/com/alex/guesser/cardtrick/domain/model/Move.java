package br.com.alex.guesser.cardtrick.domain.model;

public class Move {
	
	private String deckId;
	private int round;
	private int pile;
	
	public String getDeckId() {
		return deckId;
	}
	public void setDeckId(String deckId) {
		this.deckId = deckId;
	}
	public int getRound() {
		return round;
	}
	public void setRound(int round) {
		this.round = round;
	}
	public int getPile() {
		return pile;
	}
	public void setPile(int pile) {
		this.pile = pile;
	}
	
	

}
