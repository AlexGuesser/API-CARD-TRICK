package br.com.alex.guesser.cardtrick.domain.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.alex.guesser.cardtrick.domain.model.Card;
import br.com.alex.guesser.cardtrick.domain.model.Deck;
import br.com.alex.guesser.cardtrick.domain.model.Pile;
import br.com.alex.guesser.cardtrick.domain.repository.CardRepository;
import br.com.alex.guesser.cardtrick.domain.repository.DeckRepository;
import br.com.alex.guesser.cardtrick.domain.repository.PileRepository;

@Service
public class PileManagerService {

	@Autowired
	DeckRepository deckRepository;

	@Autowired
	CardRepository cardRepository;

	@Autowired
	PileRepository pileRepository;

	public Deck reorganizePilesAndSaveOnDatabase(Deck deck, String deckId, int round, int pile) {

		List<Card> cardsGrouped = new ArrayList();

		groupPilesAccordingToApointedPile(deck, pile, cardsGrouped);

		List<Card> newPile1 = new ArrayList();
		List<Card> newPile2 = new ArrayList();
		List<Card> newPile3 = new ArrayList();

		reorganizePiles(cardsGrouped, newPile1, newPile2, newPile3);

		List<Card> cardsReorganized = new ArrayList();

		insertNewPilesToCardsReorganized(newPile1, newPile2, newPile3, cardsReorganized);

		List<Card> newCardsReorganized = new ArrayList();

		saveNewCardsOnDatabaseAndInsertIntoNewCardsReorganized(round, cardsReorganized, newCardsReorganized);

		cleanNewPilesAndAddNewCards(newPile1, newPile2, newPile3, newCardsReorganized);

		Long idPile1 = deck.getPile1().getId();
		Long idPile2 = deck.getPile2().getId();
		Long idPile3 = deck.getPile3().getId();

		Pile pile1 = pileRepository.getOne(idPile1);
		Pile pile2 = pileRepository.getOne(idPile2);
		Pile pile3 = pileRepository.getOne(idPile3);

		pile1.setCards(newPile1);
		pile2.setCards(newPile2);
		pile3.setCards(newPile3);

		pileRepository.save(pile1);
		pileRepository.save(pile2);
		pileRepository.save(pile3);

		deck.setNumberOfplays(round);

		deckRepository.save(deck);

		return deck;

	}

	private void cleanNewPilesAndAddNewCards(List<Card> newPile1, List<Card> newPile2, List<Card> newPile3,
			List<Card> newCardsReorganized) {
		newPile1.clear();
		newPile2.clear();
		newPile3.clear();

		newPile1.addAll(newCardsReorganized.subList(0, 7));
		newPile2.addAll(newCardsReorganized.subList(7, 14));
		newPile3.addAll(newCardsReorganized.subList(14, 21));
	}

	private void saveNewCardsOnDatabaseAndInsertIntoNewCardsReorganized(int round, List<Card> cardsReorganized,
			List<Card> newCardsReorganized) {
		cardsReorganized.forEach((card) -> {

			Card newCard = new Card();
			newCard.setCode(card.getCode());
			newCard.setDeck_id(card.getDeck_id());
			newCard.setNumberOfPlay(round);
			cardRepository.save(newCard);
			newCardsReorganized.add(newCard);

		});
	}

	private void insertNewPilesToCardsReorganized(List<Card> newPile1, List<Card> newPile2, List<Card> newPile3,
			List<Card> cardsReorganized) {
		cardsReorganized.addAll(newPile1);
		cardsReorganized.addAll(newPile2);
		cardsReorganized.addAll(newPile3);
	}

	private void reorganizePiles(List<Card> cardsGrouped, List<Card> newPile1, List<Card> newPile2,
			List<Card> newPile3) {
		for (int i = 0; i <= 20; i += 3) {

			Card card = cardsGrouped.get(i);
			newPile1.add(card);

		}

		for (int i = 1; i <= 20; i += 3) {

			Card card = cardsGrouped.get(i);
			newPile2.add(card);

		}

		for (int i = 2; i <= 20; i += 3) {

			Card card = cardsGrouped.get(i);
			newPile3.add(card);

		}
	}

	private void groupPilesAccordingToApointedPile(Deck deck, int pile, List<Card> cardsGrouped) {

		switch (pile) {

		case 1:

			cardsGrouped.addAll(deck.getPile2().getCards());
			cardsGrouped.addAll(deck.getPile1().getCards());
			cardsGrouped.addAll(deck.getPile3().getCards());
			break;

		case 2:

			cardsGrouped.addAll(deck.getPile1().getCards());
			cardsGrouped.addAll(deck.getPile2().getCards());
			cardsGrouped.addAll(deck.getPile3().getCards());
			break;

		case 3:

			cardsGrouped.addAll(deck.getPile1().getCards());
			cardsGrouped.addAll(deck.getPile3().getCards());
			cardsGrouped.addAll(deck.getPile2().getCards());
			break;
		}
	}

}
