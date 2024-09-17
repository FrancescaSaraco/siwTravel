package it.uniroma3.siw.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Prenotazione;
import it.uniroma3.siw.model.User;

public interface PrenotazioneRepository extends CrudRepository<Prenotazione, Long> {
	
	public Prenotazione save(Prenotazione prenotazione);
	
	public Optional<Prenotazione> findById(Long id);
	
	public boolean existsByIdentificativoBiglietto(Integer id);
	
	public List<Prenotazione> findAllByIntestatarioBiglietto(User user);
}
