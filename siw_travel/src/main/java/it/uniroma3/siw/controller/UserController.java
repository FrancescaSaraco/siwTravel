package it.uniroma3.siw.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Prenotazione;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.repository.PrenotazioneRepository;
import it.uniroma3.siw.repository.UserRepository;
import it.uniroma3.siw.service.CredentialsService;

@Controller
public class UserController {
	@Autowired UserRepository userRepository;
	@Autowired PrenotazioneRepository prenotazioneRepository;
	@Autowired CredentialsService credentialsService;
	
	
	@GetMapping("/visualizzaDettagli")
	public String visualizzaDettagli(Model model) {
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
    	
    	User utente = credentials.getUser();
		List<Prenotazione> prenotazioniEffettuate = this.prenotazioneRepository.findAllByIntestatarioBiglietto(utente);
		
		model.addAttribute("utente", utente);
		model.addAttribute("prenotazioni", prenotazioniEffettuate);
		return "infoUtente.html";
	}
	
	
	
	@GetMapping("/eliminaPrenotazione/{id}")
	public String cancellaPrenotazione(@PathVariable("id") Long id, Model model) {
		Prenotazione prenotazioneDaCancellare = this.prenotazioneRepository.findById(id).get();
	
		this.prenotazioneRepository.delete(prenotazioneDaCancellare);
		return "homePage.html";
	}
	
}

