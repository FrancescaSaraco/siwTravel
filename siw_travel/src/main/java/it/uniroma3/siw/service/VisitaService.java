package it.uniroma3.siw.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.LuogoDiInteresse;
import it.uniroma3.siw.model.Visita;
import it.uniroma3.siw.repository.VisitaRepository;
import jakarta.transaction.Transactional;

@Service
public class VisitaService {
@Autowired VisitaRepository visitaGRepository;
	
	@Transactional
	public Visita save(Visita visita) {
		return this.visitaGRepository.save(visita);
	}
	
	public Visita findByLuogoDiInteresse(LuogoDiInteresse luogo) {
		return this.visitaGRepository.findByLuogoDiInteresse(luogo);
	}
	
	public Optional<Visita> findById(Long id) {
		return this.visitaGRepository.findById(id);
	}
}
