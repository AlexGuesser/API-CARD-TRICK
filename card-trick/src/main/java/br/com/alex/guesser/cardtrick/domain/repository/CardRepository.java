package br.com.alex.guesser.cardtrick.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.alex.guesser.cardtrick.domain.model.Card;

@Repository
public interface CardRepository extends JpaRepository<Card, String>{

}
