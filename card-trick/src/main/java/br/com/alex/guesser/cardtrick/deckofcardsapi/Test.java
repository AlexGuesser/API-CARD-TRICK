package br.com.alex.guesser.cardtrick.deckofcardsapi;

import java.util.List;

import org.springframework.web.client.RestTemplate;

import br.com.alex.guesser.cardtrick.deckofcardsapi.controller.modelForm.CardForm;
import br.com.alex.guesser.cardtrick.deckofcardsapi.controller.modelForm.DeckInfoForm;
import br.com.alex.guesser.cardtrick.deckofcardsapi.controller.modelForm.DrawedDeckForm;

public class Test {

	public static void main(String[] args) {
		
		RestTemplate restTemplate= new RestTemplate();
		DeckInfoForm deckInfo = restTemplate.getForObject("https://deckofcardsapi.com/api/deck/new/shuffle/?deck_count=1", DeckInfoForm.class);
		System.out.println(deckInfo);
		
		DrawedDeckForm drawedDeck = restTemplate.getForObject("https://deckofcardsapi.com/api/deck/"+ deckInfo.getDeck_id() +"/draw/?count=21", DrawedDeckForm.class);
		
		List<CardForm> cards = drawedDeck.getCards();
		cards.forEach((card) -> System.out.println(card));
		
	}
	
}
