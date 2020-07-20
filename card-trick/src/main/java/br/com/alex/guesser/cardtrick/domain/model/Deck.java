package br.com.alex.guesser.cardtrick.domain.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Deck implements Serializable {

	@Id
	private String deckId;
	@OneToOne
	private Pile pile1;
	@OneToOne
	private Pile pile2;
	@OneToOne
	private Pile pile3;
	private int round;

	public Deck() {

	}

	public String getDeckId() {
		return deckId;
	}

	public void setDeckId(String deckId) {
		this.deckId = deckId;
	}

	public Pile getPile1() {
		return pile1;
	}

	public void setPile1(Pile pile1) {
		this.pile1 = pile1;
	}

	public Pile getPile2() {
		return pile2;
	}

	public void setPile2(Pile pile2) {
		this.pile2 = pile2;
	}

	public Pile getPile3() {
		return pile3;
	}

	public void setPile3(Pile pile3) {
		this.pile3 = pile3;
	}

	public int getNumberOfplays() {
		return round;
	}

	public void setNumberOfplays(int numberOfplays) {
		this.round = numberOfplays;
	}

	public static List<Card> converter(List<String> codeCards, String deck_id, Integer numberOfPlay) {

		List<Card> cardsModel = new ArrayList<>();
		codeCards.forEach((code) -> cardsModel.add(new Card(code, deck_id, numberOfPlay)));

		return cardsModel;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((deckId == null) ? 0 : deckId.hashCode());
		result = prime * result + ((pile1 == null) ? 0 : pile1.hashCode());
		result = prime * result + ((pile2 == null) ? 0 : pile2.hashCode());
		result = prime * result + ((pile3 == null) ? 0 : pile3.hashCode());
		result = prime * result + round;
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
		Deck other = (Deck) obj;
		if (deckId == null) {
			if (other.deckId != null)
				return false;
		} else if (!deckId.equals(other.deckId))
			return false;
		if (pile1 == null) {
			if (other.pile1 != null)
				return false;
		} else if (!pile1.equals(other.pile1))
			return false;
		if (pile2 == null) {
			if (other.pile2 != null)
				return false;
		} else if (!pile2.equals(other.pile2))
			return false;
		if (pile3 == null) {
			if (other.pile3 != null)
				return false;
		} else if (!pile3.equals(other.pile3))
			return false;
		if (round != other.round)
			return false;
		return true;
	}

}
