package br.com.alex.guesser.cardtrick.domain.dto;

import javax.persistence.OneToOne;

import br.com.alex.guesser.cardtrick.domain.model.Deck;
import br.com.alex.guesser.cardtrick.domain.model.Pile;

public class DeckDto {
	
	private String deck_id;
	private Pile pile1;
	private Pile pile2;
	private Pile pile3;
	private String yourCardIs = "";
	
	public String getDeck_id() {
		return deck_id;
	}
	public void setDeck_id(String deck_id) {
		this.deck_id = deck_id;
	}
	public Pile getPile1() {
		return pile1;
	}
	public void setPile1(Pile pile1) {
		this.pile1 = pile1;
	}
	public Pile getPile2() {
		return pile2;
	}
	public void setPile2(Pile pile2) {
		this.pile2 = pile2;
	}
	public Pile getPile3() {
		return pile3;
	}
	public void setPile3(Pile pile3) {
		this.pile3 = pile3;
	}
	public String getYourCardIs() {
		return yourCardIs;
	}
	public void setYourCardIs(String yourCardIs) {
		this.yourCardIs = yourCardIs;
	}
	public static DeckDto converter(Deck deck) {
		
		DeckDto deckDto = new DeckDto();
		deckDto.setDeck_id(deck.getDeck_id());
		deckDto.setPile1(deck.getPile1());
		deckDto.setPile2(deck.getPile2());
		deckDto.setPile3(deck.getPile3());
		
		
		return deckDto;
	}
	
	

}
