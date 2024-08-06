package it.uniroma3.siw.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Citta;
import it.uniroma3.siw.model.LuogoDiInteresse;

public interface LuogoDiInteresseRepository extends CrudRepository<LuogoDiInteresse, Long>{
	public  List<LuogoDiInteresse> findAll();
	  
	  public LuogoDiInteresse save(LuogoDiInteresse luogoDiInteresse);
	  
	  public boolean existsByCittaAndIndirizzo(Citta citta, String indirizzo);
	  
	  public List<LuogoDiInteresse> findByCitta(Citta citta);
	  
	  public Optional<LuogoDiInteresse> findById(Long id);
	  
	  public LuogoDiInteresse findByNome(String nome);
}
