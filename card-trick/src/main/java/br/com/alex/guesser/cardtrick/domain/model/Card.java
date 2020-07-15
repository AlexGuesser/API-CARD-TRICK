package br.com.alex.guesser.cardtrick.domain.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Card {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String deck_id;
	private String code;
	private Integer numberOfPlay;
	
	
	public Card() {
		
	}

	public Card(String code,String deck_id,Integer numberOfPlay) {
		
		this.code = code;
		this.deck_id = deck_id;
		this.numberOfPlay = numberOfPlay;
		
	}
	

	public Integer getNumberOfPlay() {
		return numberOfPlay;
	}

	public void setNumberOfPlay(Integer numberOfPlay) {
		this.numberOfPlay = numberOfPlay;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDeck_id() {
		return deck_id;
	}

	public void setDeck_id(String deck_id) {
		this.deck_id = deck_id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Card other = (Card) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		
		return this.code + " " + this.id;
	}
	
	
	

}
