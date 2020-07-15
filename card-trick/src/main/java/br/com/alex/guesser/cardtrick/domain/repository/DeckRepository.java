package br.com.alex.guesser.cardtrick.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.alex.guesser.cardtrick.domain.model.Deck;

@Repository
public interface DeckRepository extends JpaRepository<Deck, String>{



}
