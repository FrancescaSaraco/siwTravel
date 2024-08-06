package it.uniroma3.siw.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.LuogoDiInteresse;
import it.uniroma3.siw.model.Visita;

public interface VisitaRepository extends CrudRepository<Visita, Long>{
	public Visita save(Visita persona);
	
	public Visita findByLuogoDiInteresse(LuogoDiInteresse luogo);
	
	public Optional<Visita> findById(Long id);
}
