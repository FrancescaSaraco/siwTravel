package it.uniroma3.siw.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Prenotazione;

public interface PrenotazioneRepository extends CrudRepository<Prenotazione, Long> {
	
	public Prenotazione save(Prenotazione prenotazione);
	
	public Optional<Prenotazione> findById(Long id);
	
	public boolean existsByIdentificativoBiglietto(Integer id);
}
