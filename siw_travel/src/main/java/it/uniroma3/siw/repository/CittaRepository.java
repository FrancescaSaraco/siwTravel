package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Citta;

public interface CittaRepository extends CrudRepository<Citta, Long> {
  public  List<Citta> findAll();
  
  public Citta save(Citta citta);
  
  public boolean existsByStatoAndNome(String stato, String nome);

  public List<Citta> findByNome(String nome);
  
  public Citta findByStatoAndNome(String stato, String nome);
  
}
  