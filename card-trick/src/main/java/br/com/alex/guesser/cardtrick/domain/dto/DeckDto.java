package br.com.alex.guesser.cardtrick.domain.dto;

import br.com.alex.guesser.cardtrick.domain.model.Deck;
import br.com.alex.guesser.cardtrick.domain.model.Pile;

public class DeckDto {
	
	private String deck_id;
	private PileDto pile1;
	private PileDto pile2;
	private PileDto pile3;
	private String yourCardIs = "";
	
	public String getDeck_id() {
		return deck_id;
	}
	public void setDeck_id(String deck_id) {
		this.deck_id = deck_id;
	}
	
	public PileDto getPile1() {
		return pile1;
	}
	public void setPile1(PileDto pile1) {
		this.pile1 = pile1;
	}
	public PileDto getPile2() {
		return pile2;
	}
	public void setPile2(PileDto pile2) {
		this.pile2 = pile2;
	}
	public PileDto getPile3() {
		return pile3;
	}
	public void setPile3(PileDto pile3) {
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
		deckDto.setPile1(PileDto.converter(deck.getPile1()));
		deckDto.setPile2(PileDto.converter(deck.getPile2()));
		deckDto.setPile3(PileDto.converter(deck.getPile3()));
		
		
		
		return deckDto;
	}
	
	

}
