package br.com.alex.guesser.cardtrick.domain;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alex.guesser.cardtrick.domain.dto.DeckDto;
import br.com.alex.guesser.cardtrick.domain.model.Deck;
import br.com.alex.guesser.cardtrick.domain.model.Move;
import br.com.alex.guesser.cardtrick.domain.repository.CardRepository;
import br.com.alex.guesser.cardtrick.domain.repository.DeckRepository;
import br.com.alex.guesser.cardtrick.domain.repository.PileRepository;
import br.com.alex.guesser.cardtrick.domain.service.DeckManagerService;
import br.com.alex.guesser.cardtrick.domain.service.PileManagerService;

@RestController
@RequestMapping("/game")
public class MainController {

	@Autowired
	DeckRepository deckRepository;

	@Autowired
	CardRepository cardRepository;

	@Autowired
	PileRepository pileRepository;

	@Autowired
	DeckManagerService deckManager;

	@Autowired
	PileManagerService pileManager;

	@GetMapping
	public DeckDto createAndShowDeck() {

		Deck deck = deckManager.createAndSaveOnDatabase();
		return DeckDto.converter(deck);

	}

	@PostMapping("/plays")
	public ResponseEntity<DeckDto> play(@RequestBody Move move) {

		List<Integer> possiblePiles = Arrays.asList(1, 2, 3);

		String deckId = move.getDeckId();
		int round = move.getRound();
		int pile = move.getPile();

		if (deckNotFound(deckId)) {

			return ResponseEntity.badRequest().build();

		}

		Deck deck = deckRepository.getOne(deckId);

		int oldNumberOfPlays = deck.getNumberOfplays();

		if (roundIsInvalid(round, oldNumberOfPlays)) {

			return ResponseEntity.badRequest().build();

		}

		if (pointedPileIsInvalid(possiblePiles, pile)) {

			return ResponseEntity.badRequest().build();

		}

		Deck newDeck = new Deck();
		newDeck = pileManager.reorganizePilesAndSaveOnDatabase(deck, deckId, round, pile);

		DeckDto deckDto = DeckDto.converter(newDeck);

		if (round == 3) {
			showTheChosenCard(deckDto);
		}

		return ResponseEntity.ok(deckDto);

	}

	private boolean pointedPileIsInvalid(List<Integer> possiblePiles, int pile) {
		return !possiblePiles.contains(pile);
	}

	private void showTheChosenCard(DeckDto deckDto) {
		deckDto.setYourCardIs(deckDto.getPile2().getCardsDto().get(3).getCode());
	}

	private boolean deckNotFound(String deckId) {
		return deckRepository.findById(deckId).isEmpty();
	}

	private boolean roundIsInvalid(int round, int oldNumberOfPlays) {
		return round != oldNumberOfPlays + 1 || round == 4;
	}

}
