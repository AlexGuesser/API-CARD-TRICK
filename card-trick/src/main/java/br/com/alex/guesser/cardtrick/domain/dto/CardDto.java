package br.com.alex.guesser.cardtrick.domain.dto;

import br.com.alex.guesser.cardtrick.domain.model.Card;

public class CardDto {
	
	private String code;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public static CardDto converter(Card card) {
		
		CardDto cardDto = new CardDto();
		cardDto.code = card.getCode();
		
		return cardDto;
		
	}

}
