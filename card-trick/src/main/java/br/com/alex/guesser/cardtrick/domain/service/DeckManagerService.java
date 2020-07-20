package br.com.alex.guesser.cardtrick.domain.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
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
public class DeckManagerService {

	@Autowired
	DeckRepository deckRepository;

	@Autowired
	CardRepository cardRepository;

	@Autowired
	PileRepository pileRepository;

	public Deck createAndSaveOnDatabase() {

		List<String> allCards = Arrays.asList("AS", "2S", "3S", "4S", "5S", "6S", "7S", "8S", "9S", "10S", "JS", "QS",
				"KS", "AC", "2C", "3C", "4C", "5C", "6C", "7C", "8C", "9C", "10C", "JC", "QC", "KC", "AH", "2H", "3H",
				"4H", "5H", "6H", "7H", "8H", "9H", "10H", "JH", "QH", "KH", "AD", "2D", "3D", "4D", "5D", "6D", "7D",
				"8D", "9D", "10D", "JD", "QD", "KD");

		Collections.shuffle(allCards);

		List<String> codeCards = new ArrayList();

		codeCards.addAll(allCards.subList(0, 21));

		String idString = Long.toString(System.currentTimeMillis());

		String deckId = Base64.getEncoder().encodeToString(idString.getBytes());

		List<Card> cards = Deck.converter(codeCards, deckId, 0);

		Deck deck = new Deck();
		deck.setDeckId(deckId);

		cards.forEach((card) -> cardRepository.save(card));

		Pile pile1 = new Pile(cards.subList(0, 7), 1, deckId);
		Pile pile2 = new Pile(cards.subList(7, 14), 2, deckId);
		Pile pile3 = new Pile(cards.subList(14, 21), 3, deckId);

		pileRepository.save(pile1);
		pileRepository.save(pile2);
		pileRepository.save(pile3);

		deck.setPile1(pile1);
		deck.setPile2(pile2);
		deck.setPile3(pile3);
		deck.setNumberOfplays(0);

		deckRepository.save(deck);

		return deck;

	}

}
