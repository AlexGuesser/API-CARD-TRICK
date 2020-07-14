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
	PileRepository PileRepository;

	@GetMapping
	public Deck showDeck() {

		RestTemplate restTemplate = new RestTemplate();
		DeckInfoForm deckInfo = restTemplate
				.getForObject("https://deckofcardsapi.com/api/deck/new/shuffle/?deck_count=1", DeckInfoForm.class);

		DrawedDeckForm drawedDeck = restTemplate.getForObject(
				"https://deckofcardsapi.com/api/deck/" + deckInfo.getDeck_id() + "/draw/?count=21",
				DrawedDeckForm.class);

		List<CardForm> cardsForm = drawedDeck.getCards();
		// cardsForm.forEach((cardForm) -> System.out.println(cardForm));

		List<Card> cards = Deck.converter(cardsForm);

		Deck deck = new Deck();
		deck.setDeck_id(deckInfo.getDeck_id());
		deck.setAllCards(cards);

		cards.forEach((card) -> cardRepository.save(card));
			

		

		Pile pile1 = new Pile(cards.subList(0, 7), 1);
		Pile pile2 = new Pile(cards.subList(7, 14), 2);
		Pile pile3 = new Pile(cards.subList(14, 21), 3);

		PileRepository.save(pile1);
		PileRepository.save(pile2);
		PileRepository.save(pile3);

		deck.setPile1(pile1);
		deck.setPile2(pile2);
		deck.setPile3(pile3);
		deck.setNumberOfplays(0);

		return deckRepository.save(deck);

	}

	@GetMapping("/plays")
	public DeckDto play(@RequestParam String deck_id, @RequestParam int numberOfPlay, @RequestParam int pointedPile) {

		Deck deck = deckRepository.getOne(deck_id);
		List<Card> cardsReorganized = new ArrayList();

		switch (pointedPile) {

		case 1:
			
			deck.getPile2().getCards().forEach((cardPile2) -> cardsReorganized.add(cardPile2));
			deck.getPile1().getCards().forEach((cardPile1) -> cardsReorganized.add(cardPile1));
			deck.getPile3().getCards().forEach((cardPile3) -> cardsReorganized.add(cardPile3));
			
			
			/*
			 cardsReorganized.addAll(deck.getPile2().getCards());
			 cardsReorganized.addAll(deck.getPile1().getCards());
			 cardsReorganized.addAll(deck.getPile3().getCards());
			 */
			 
			break;

		case 2:
			
			deck.getPile1().getCards().forEach((cardPile1) -> cardsReorganized.add(cardPile1));
			deck.getPile2().getCards().forEach((cardPile2) -> cardsReorganized.add(cardPile2));
			deck.getPile3().getCards().forEach((cardPile3) -> cardsReorganized.add(cardPile3));
			
			
			/*
			 cardsReorganized.addAll(deck.getPile1().getCards());
			 cardsReorganized.addAll(deck.getPile2().getCards());
			 cardsReorganized.addAll(deck.getPile3().getCards());
			 */
			 
			break;

		case 3:
			
			deck.getPile1().getCards().forEach((cardPile1) -> cardsReorganized.add(cardPile1));
			deck.getPile3().getCards().forEach((cardPile3) -> cardsReorganized.add(cardPile3));
			deck.getPile2().getCards().forEach((cardPile2) -> cardsReorganized.add(cardPile2));
			
			
			/*
			 cardsReorganized.addAll(deck.getPile1().getCards());
			 cardsReorganized.addAll(deck.getPile3().getCards());
			 cardsReorganized.addAll(deck.getPile2().getCards());
			 */
			 
			break;
		}

		Pile newPile1 = new Pile(new ArrayList<Card>(), 1);
		Pile newPile2 = new Pile(new ArrayList<Card>(), 2);
		Pile newPile3 = new Pile(new ArrayList<Card>(), 3);

		for (int i = 0; i <= 20; i += 3) {

			Card card = cardsReorganized.get(i);
			newPile1.getCards().add(card);

		}

		for (int i = 1; i <= 20; i += 3) {

			Card card = cardsReorganized.get(i);
			newPile2.getCards().add(card);

		}

		for (int i = 2; i <= 20; i += 3) {

			Card card = cardsReorganized.get(i);
			newPile3.getCards().add(card);

		}

		deck.setNumberOfplays(numberOfPlay);
		deck.setAllCards(cardsReorganized);
		deck.setPile1(newPile1);
		deck.setPile2(newPile2);
		deck.setPile3(newPile3);

		DeckDto deckDto = DeckDto.converter(deck);

		if (numberOfPlay == 3) {
			deckDto.setYourCardIs(deckDto.getPile2().getCards().get(3).getCode());
		}

		return deckDto;

	}

}
