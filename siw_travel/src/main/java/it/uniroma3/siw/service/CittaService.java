package it.uniroma3.siw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Citta;
import it.uniroma3.siw.repository.CittaRepository;
import jakarta.transaction.Transactional;

@Service
public class CittaService {
	@Autowired
	CittaRepository cittaRepository;
	
	public List<Citta> findAll() {
		return this.cittaRepository.findAll();
	}
	
	@Transactional	// si fa con insert, update e delete
	public Citta save(Citta citta) {
		return this.cittaRepository.save(citta);
	}
	
	public boolean existsByStatoAndNome(String stato, String nome) {
		return this.cittaRepository.existsByStatoAndNome(stato, nome);
	}
	
	public List<Citta> findByNome(String nome) {
		return this.cittaRepository.findByNome(nome);
	}
	
	public int count(List<Citta> cittas) {
		if(cittas == null) {
			return 0;
		}
		return cittas.size();
	}
	
}
