package br.com.alex.guesser.cardtrick.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import br.com.alex.guesser.cardtrick.deckofcardsapi.controller.modelForm.CardForm;
import br.com.alex.guesser.cardtrick.deckofcardsapi.controller.modelForm.DeckInfoForm;
import br.com.alex.guesser.cardtrick.deckofcardsapi.controller.modelForm.DrawedDeckForm;
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

		RestTemplate restTemplate = new RestTemplate();
		DeckInfoForm deckInfo = restTemplate
				.getForObject("https://deckofcardsapi.com/api/deck/new/shuffle/?deck_count=1", DeckInfoForm.class);

		DrawedDeckForm drawedDeck = restTemplate.getForObject(
				"https://deckofcardsapi.com/api/deck/" + deckInfo.getDeck_id() + "/draw/?count=21",
				DrawedDeckForm.class);

		List<CardForm> cardsForm = drawedDeck.getCards();
		// cardsForm.forEach((cardForm) -> System.out.println(cardForm));
		
		String deck_id = deckInfo.getDeck_id();

		List<Card> cards = Deck.converter(cardsForm,deck_id,0);

		Deck deck = new Deck();
		deck.setDeck_id(deckInfo.getDeck_id());
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
	public DeckDto play(@RequestParam String deck_id, @RequestParam int numberOfPlay, @RequestParam int pointedPile) {

		Deck deck = deckRepository.getOne(deck_id);
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
		
		
		System.out.println("newPile1 após reorganizar: " + newPile1);
		System.out.println("newPile2 após reorganizar: " + newPile2);
		System.out.println("newPile3 após reorganizar: " + newPile3);
		
		
		Pile pile1 = pileRepository.getOne(idPile1);
		Pile pile2 = pileRepository.getOne(idPile2);
		Pile pile3 = pileRepository.getOne(idPile3);
		
		pile1.setCards(newPile1);
		pile2.setCards(newPile2);
		pile3.setCards(newPile3);
		
		System.out.println("Pile2 atualizado" + pile2.getCards());
		
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

		return deckDto;

	}

}
