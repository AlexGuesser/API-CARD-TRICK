package br.com.alex.guesser.cardtrick.deckofcardsapi.controller.modelForm;

import java.util.List;

public class DrawedDeckForm {
	
	private boolean sucess;
	private List<CardForm> cards;
	private String deck_id;
	private int remaining;
	
	public boolean isSucess() {
		return sucess;
	}
	public void setSucess(boolean sucess) {
		this.sucess = sucess;
	}
	public List<CardForm> getCards() {
		return cards;
	}
	public void setCards(List<CardForm> cards) {
		this.cards = cards;
	}
	public String getDeck_id() {
		return deck_id;
	}
	public void setDeck_id(String deck_id) {
		this.deck_id = deck_id;
	}
	public int getRemaining() {
		return remaining;
	}
	public void setRemaining(int remaining) {
		this.remaining = remaining;
	}
	
	
	

}
