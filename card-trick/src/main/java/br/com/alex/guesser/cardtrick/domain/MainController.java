package br.com.alex.guesser.cardtrick.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.alex.guesser.cardtrick.domain.dto.DeckDto;
import br.com.alex.guesser.cardtrick.domain.model.Card;
import br.com.alex.guesser.cardtrick.domain.model.Deck;
import br.com.alex.guesser.cardtrick.domain.model.Pile;
import br.com.alex.guesser.cardtrick.domain.repository.CardRepository;
import br.com.alex.guesser.cardtrick.domain.repository.DeckRepository;
import br.com.alex.guesser.cardtrick.domain.repository.PileRepository;

@RestController
@RequestMapping("/game")
public class MainController {

	@Autowired
	DeckRepository deckRepository;

	@Autowired
	CardRepository cardRepository;

	@Autowired
	PileRepository pileRepository;

	@GetMapping
	public DeckDto showDeck() {

		List<String> allCards = Arrays.asList("AS","2S","3S","4S","5S","6S","7S","8S","9S","10S","JS","QS","KS",
											  "AC","2C","3C","4C","5C","6C","7C","8C","9C","10C","JC","QC","KC",
											  "AH","2H","3H","4H","5H","6H","7H","8H","9H","10H","JH","QH","KH",
											  "AD","2D","3D","4D","5D","6D","7D","8D","9D","10D","JD","QD","KD");
		
		Collections.shuffle(allCards);
		
		List<String> codeCards = new ArrayList();
		
		codeCards.addAll(allCards.subList(0, 21));
		
		String idString = Long.toString(System.currentTimeMillis());
		
		String deck_id = Base64.getEncoder().encodeToString(idString.getBytes());
		
		List<Card> cards = Deck.converter(codeCards,deck_id,0);

		Deck deck = new Deck();
		deck.setDeck_id(deck_id);
		deck.setAllCards(cards);

		cards.forEach((card) -> cardRepository.save(card));

		Pile pile1 = new Pile(cards.subList(0, 7), 1,deck_id);
		Pile pile2 = new Pile(cards.subList(7, 14), 2,deck_id);
		Pile pile3 = new Pile(cards.subList(14, 21), 3,deck_id);

		pileRepository.save(pile1);
		pileRepository.save(pile2);
		pileRepository.save(pile3);

		deck.setPile1(pile1);
		deck.setPile2(pile2);
		deck.setPile3(pile3);
		deck.setNumberOfplays(0);

		deckRepository.save(deck);
		
		return DeckDto.converter(deck);

	}

	@GetMapping("/plays")
	public ResponseEntity<DeckDto> play(@RequestParam String deck_id, @RequestParam  int numberOfPlay, @RequestParam  int pointedPile) {

		if(deckRepository.findById(deck_id).isEmpty()){
			
			return ResponseEntity.badRequest().build();
			
		};
		
		Deck deck = deckRepository.getOne(deck_id);
		
		int oldNumberOfPlays = deck.getNumberOfplays();
		
		if(numberOfPlay != oldNumberOfPlays +1 || numberOfPlay==4){
			
			return ResponseEntity.badRequest().build();
			
		}
		
		
		
		List<Card> cardsReorganized = new ArrayList();
		
		Long idPile1 = deck.getPile1().getId();
		Long idPile2 = deck.getPile2().getId();
		Long idPile3 = deck.getPile3().getId();
		
		System.out.println("Pile1 inical" + deck.getPile1().getCards());
		System.out.println("Pile2 inical" + deck.getPile2().getCards());
		System.out.println("Pile3 inical" + deck.getPile3().getCards());

		switch (pointedPile) {

		case 1:
			
			cardsReorganized.addAll(deck.getPile2().getCards());
			cardsReorganized.addAll(deck.getPile1().getCards());
			cardsReorganized.addAll(deck.getPile3().getCards()); 
			break;

		case 2:
			
			cardsReorganized.addAll(deck.getPile1().getCards());
			cardsReorganized.addAll(deck.getPile2().getCards());
			cardsReorganized.addAll(deck.getPile3().getCards()); 
			break;

		case 3:
			
			cardsReorganized.addAll(deck.getPile1().getCards());
			cardsReorganized.addAll(deck.getPile3().getCards());
			cardsReorganized.addAll(deck.getPile2().getCards());
			break;
		}

		List<Card> newPile1 = new ArrayList();
		List<Card> newPile2 = new ArrayList();
		List<Card> newPile3 = new ArrayList();
		

		for (int i = 0; i <= 20; i += 3) {

			Card card = cardsReorganized.get(i);
			newPile1.add(card);

		}

		for (int i = 1; i <= 20; i += 3) {

			Card card = cardsReorganized.get(i);
			newPile2.add(card);

		}

		for (int i = 2; i <= 20; i += 3) {

			Card card = cardsReorganized.get(i);
			newPile3.add(card);

		}
		
		cardsReorganized.clear();
		cardsReorganized.addAll(newPile1);
		cardsReorganized.addAll(newPile2);
		cardsReorganized.addAll(newPile3);
		
		List<Card> newCardsReorganized = new ArrayList();
		
		cardsReorganized.forEach((card) -> {
			
			Card newCard = new Card();
			newCard.setCode(card.getCode());
			newCard.setDeck_id(card.getDeck_id());
			newCard.setNumberOfPlay(numberOfPlay);
			cardRepository.save(newCard);
			newCardsReorganized.add(newCard);
			
		});
		
		newPile1.clear();
		newPile2.clear();
		newPile3.clear();
		
		newPile1.addAll(newCardsReorganized.subList(0, 7));
		newPile2.addAll(newCardsReorganized.subList(7, 14));
		newPile3.addAll(newCardsReorganized.subList(14, 21));		
		
		Pile pile1 = pileRepository.getOne(idPile1);
		Pile pile2 = pileRepository.getOne(idPile2);
		Pile pile3 = pileRepository.getOne(idPile3);
		
		pile1.setCards(newPile1);
		pile2.setCards(newPile2);
		pile3.setCards(newPile3);
		
		pileRepository.save(pile1);
		pileRepository.save(pile2);
		pileRepository.save(pile3);
		
		deck.setNumberOfplays(numberOfPlay);
		deck.setAllCards(cardsReorganized);
		
		
		deckRepository.save(deck);

		DeckDto deckDto = DeckDto.converter(deck);

		if (numberOfPlay == 3) {
			deckDto.setYourCardIs(deckDto.getPile2().getCardsDto().get(3).getCode());
		}

		return ResponseEntity.ok(deckDto);

	}

}
