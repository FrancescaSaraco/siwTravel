package it.uniroma3.siw.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Prenotazione;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.repository.PrenotazioneRepository;
import jakarta.transaction.Transactional;

@Service
public class PrenotazioneService {
	@Autowired PrenotazioneRepository prenotazioneRepository;
	
	@Transactional
	public Prenotazione save(Prenotazione prenotazione) {
		return this.prenotazioneRepository.save(prenotazione);
	}
	
	public Optional<Prenotazione> findById(Long id) {
		return this.prenotazioneRepository.findById(id);
	}
	
	public boolean existByIdentificativoBiglietto(Integer id) {
		return this.prenotazioneRepository.existsByIdentificativoBiglietto(id);
	}
	
	public Long count() {
		return this.prenotazioneRepository.count();
	}
	public Integer effettuatePerUtente(User utente) {
		return this.prenotazioneRepository.findByIntestatarioBiglietto(utente).size();
	}  
}
