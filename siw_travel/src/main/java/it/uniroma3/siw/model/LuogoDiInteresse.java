package it.uniroma3.siw.model;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"citta", "indirizzo"}))	//rappresenta le chiavi, cioè che nella colonna non posso avere piu' tuple di questo tipo combinate
public class LuogoDiInteresse {
	public String indirizzo;
	public String tipologia;
	public String nome;
	public Integer valutazione;
	@Column(length=1000)
	public String descrizione;
	
	// da molti luoghi di interesse a un'unica citta
	@ManyToOne
	@JoinColumn(name = "Citta", nullable = false)
	public Citta citta;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long id;
	
	
	public String getIndirizzo() {
		return indirizzo;
	}
	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}
	public String getTipologia() {
		return tipologia;
	}
	public void setTipologia(String tipologia) {
		this.tipologia = tipologia;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Integer getValutazione() {
		return valutazione;
	}
	public void setValutazione(Integer valutazione) {
		this.valutazione = valutazione;
	}
	public Citta getCitta() {
		return citta;
	}
	public void setCitta(Citta citta) {
		this.citta = citta;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	@Override
	public int hashCode() {
		return Objects.hash(citta.getNome(), indirizzo, citta.getStato());		// vedere se lasciare citta.getNome() o citta
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LuogoDiInteresse other = (LuogoDiInteresse) obj;
		return Objects.equals(citta.getNome(), other.citta.getNome()) && Objects.equals(indirizzo, other.indirizzo) && Objects.equals(citta.getStato(), other.citta.getStato());
	}
	
	
}
