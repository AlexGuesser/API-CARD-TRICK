package br.com.alex.guesser.cardtrick.domain.dto;

import java.util.ArrayList;
import java.util.List;

import br.com.alex.guesser.cardtrick.domain.model.Pile;

public class PileDto {

	private Integer id;
	private List<CardDto> cards;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public List<CardDto> getCardsDto() {
		return cards;
	}
	public void setCardsDto(List<CardDto> cardsDto) {
		this.cards = cardsDto;
	}
	
	public static PileDto converter(Pile pile) {
		
		PileDto pileDto = new PileDto();
		pileDto.setId(pile.getNumber());
		List<CardDto> cardsDto = new ArrayList();
		pile.getCards().forEach((card) -> cardsDto.add(CardDto.converter(card)));
		pileDto.setCardsDto(cardsDto);
		
		return pileDto;
		
	}
	
	
	
}
