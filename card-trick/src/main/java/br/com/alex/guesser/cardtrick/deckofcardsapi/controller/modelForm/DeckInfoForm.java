package br.com.alex.guesser.cardtrick.deckofcardsapi.controller.modelForm;

public class DeckInfoForm {
	
	private boolean sucess;
	private String deck_id;
	private boolean shuffled;
	private int remaining;
	
	public boolean isSucess() {
		return sucess;
	}
	public void setSucess(boolean sucess) {
		this.sucess = sucess;
	}
	public String getDeck_id() {
		return deck_id;
	}
	public void setDeck_id(String deck_id) {
		this.deck_id = deck_id;
	}
	public boolean isShuffled() {
		return shuffled;
	}
	public void setShuffled(boolean shuffled) {
		this.shuffled = shuffled;
	}
	public int getRemaining() {
		return remaining;
	}
	public void setRemaining(int remaining) {
		this.remaining = remaining;
	}
	
	@Override
	public String toString() {

		return this.deck_id + " com: " + this.remaining + " cartas";
	}
	
	

}
