package it.uniroma3.siw.model;

import java.time.LocalDate;
import java.time.LocalTime;
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
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"identificativoBiglietto"}))
public class Prenotazione {
	public Integer identificativoBiglietto;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long id;
	
	@ManyToOne
	@JoinColumn(name = "intestatario_Biglietto")
	public User intestatarioBiglietto;
	
	@ManyToOne
	public Visita visita;
	
	public LocalTime orario;
	
	public LocalDate data;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getIntestatarioBiglietto() {
		return intestatarioBiglietto;
	}

	public void setIntestatarioBiglietto(User intestatarioBiglietto) {
		this.intestatarioBiglietto = intestatarioBiglietto;
	}

	public Integer getIdentificativoBiglietto() {
		return identificativoBiglietto;
	}

	public void setIdentificativoBiglietto(Integer identificativoBiglietto) {
		this.identificativoBiglietto = identificativoBiglietto;
	}

	@Override
	public int hashCode() {
		return Objects.hash(identificativoBiglietto);
	}

	public Visita getVisita() {
		return visita;
	}

	public void setVisita(Visita visita) {
		this.visita = visita;
	}

	public LocalTime getOrario() {
		return orario;
	}

	public void setOrario(LocalTime orario) {
		this.orario = orario;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Prenotazione other = (Prenotazione) obj;
		return Objects.equals(identificativoBiglietto, other.identificativoBiglietto);
	}
	
	
}
