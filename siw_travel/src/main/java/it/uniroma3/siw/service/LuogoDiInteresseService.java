package it.uniroma3.siw.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Citta;
import it.uniroma3.siw.model.LuogoDiInteresse;
import it.uniroma3.siw.repository.LuogoDiInteresseRepository;
import jakarta.transaction.Transactional;

@Service
public class LuogoDiInteresseService {
	@Autowired 
	LuogoDiInteresseRepository luogoRepository;
	
	public List<LuogoDiInteresse> findAll() {
		return this.luogoRepository.findAll();
	}
	
	@Transactional	// si fa con insert, update e delete
	public LuogoDiInteresse save(LuogoDiInteresse luogo) {
		return this.luogoRepository.save(luogo);
	}
	
	public boolean existsByCittaAndIndirizzo(Citta citta, String indirizzo) {
		return this.luogoRepository.existsByCittaAndIndirizzo(citta, indirizzo);
	}
	
	public List<LuogoDiInteresse> findByCitta(Citta citta) {
		return this.luogoRepository.findByCitta(citta);
	}
	
	public Optional<LuogoDiInteresse> findById(Long id) {
		return this.luogoRepository.findById(id);
	}
	
	public LuogoDiInteresse findByNome(String nome) {
		return this.luogoRepository.findByNome(nome);
	}
}
