package it.uniroma3.siw.model;

import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"luogoDiInteresse"}))
public class Visita {
	public Double costoBiglietto;
	public List<String> giorniApertura;
	public LocalTime orarioApertura;
	public LocalTime orarioChiusura;
	public String descrizione;
	public Boolean prenotabile;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long id;  
	
	@ManyToOne
	@JoinColumn(name = "luogoDiInteresse")
	public LuogoDiInteresse luogoDiInteresse;

	public List<String> getGiorniApertura() {
		return giorniApertura;
	}

	public void setGiorniApertura(List<String> giorniApertura) {
		this.giorniApertura = giorniApertura;
	}

	public LocalTime getOrarioApertura() {
		return orarioApertura;
	}

	public void setOrarioApertura(LocalTime orarioApertura) {
		this.orarioApertura = orarioApertura;
	}

	public LocalTime getOrarioChiusura() {
		return orarioChiusura;
	}

	public void setOrarioChiusura(LocalTime orarioChiusura) {
		this.orarioChiusura = orarioChiusura;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LuogoDiInteresse getLuogoDinteresse() {
		return luogoDiInteresse;
	}

	public void setLuogoDinteresse(LuogoDiInteresse luogoDinteresse) {
		this.luogoDiInteresse = luogoDinteresse;
	}
	
	public Boolean getPrenotabile() {
		return prenotabile;
	}
	public void setPrenotabile(Boolean prenotabile) {
		this.prenotabile = prenotabile;
	}

	public Double getCostoBiglietto() {
		return costoBiglietto;
	}

	public void setCostoBiglietto(Double costoBiglietto) {
		this.costoBiglietto = costoBiglietto;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(luogoDiInteresse);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Visita other = (Visita) obj;
		return Objects.equals(luogoDiInteresse, other.luogoDiInteresse);
	}
	
	
}
