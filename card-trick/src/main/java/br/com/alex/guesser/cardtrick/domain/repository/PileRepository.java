package br.com.alex.guesser.cardtrick.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.alex.guesser.cardtrick.domain.model.Card;
import br.com.alex.guesser.cardtrick.domain.model.Pile;

@Repository
public interface PileRepository extends JpaRepository<Pile, Long>{

	List<Pile> getByDeckid(String deck_id);

}

