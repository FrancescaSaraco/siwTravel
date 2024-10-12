package it.uniroma3.siw.controller;


import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.LuogoDiInteresse;
import it.uniroma3.siw.model.Prenotazione;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.model.Visita;
import it.uniroma3.siw.repository.VisitaRepository;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.LuogoDiInteresseService;
import it.uniroma3.siw.service.PrenotazioneService;
import it.uniroma3.siw.service.UserService;
import it.uniroma3.siw.service.VisitaService;
import it.uniroma3.siw.validator.VisitaValidator;
import jakarta.validation.Valid;

@Controller
public class VisitaController {
	@Autowired VisitaService visitaService;
	@Autowired LuogoDiInteresseService luogoDiInteresse;
	@Autowired PrenotazioneService prenotazioneService;
	@Autowired UserService userService;
	@Autowired CredentialsService credentialsService;
	@Autowired VisitaValidator visitaValidator;
	@Autowired VisitaRepository visitaRepository;
	
	@GetMapping("/admin/formNewVisita")
	public String ottieniForm(Model model) {
		model.addAttribute("visita", new Visita());
		model.addAttribute("luoghi", this.luogoDiInteresse.findAll());
		return "admin/formNewVisita.html";  
	}
	
	@PostMapping("/admin/visita")
	public String aggiungiVisita(@RequestParam("orarioApertura")LocalTime orarioApertura, @RequestParam("orarioChiusura")LocalTime orarioChiusura, 
			@RequestParam("descrizione")String descrizione, @RequestParam("nomeLuogo")String nomeLuogo,
			@RequestParam("prenotabile")Boolean prenotabile,
			@RequestParam("giorniApertura")List<String> giorniApertura, @RequestParam("costoBiglietto")Double costoBiglietto,
			@Valid @ModelAttribute("visita")Visita visita,BindingResult result, Model model) {
		
		LuogoDiInteresse luogo = this.luogoDiInteresse.findByNome(nomeLuogo);
		visita.setLuogoDinteresse(luogo);
		
		this.visitaValidator.validate(visita, result);
		
		if(!result.hasErrors()) {
			visita.setCostoBiglietto(costoBiglietto);
			visita.setDescrizione(descrizione);
			visita.setGiorniApertura(giorniApertura);
			visita.setOrarioApertura(orarioApertura);
			visita.setOrarioChiusura(orarioChiusura);
			visita.setPrenotabile(prenotabile);
	
			this.visitaService.save(visita);
			return "admin/visitaSuccesso.html";  
		} else {
			model.addAttribute("visita", visita);
			model.addAttribute("luoghi", this.luogoDiInteresse.findAll());
			return "admin/formNewVisita.html"; 
		}
		
	}
	
	@GetMapping("/dettagliVisita/{id}")
	public String descrizioneLuogo(@PathVariable("id")Long id,Model model) {
		Visita visita = this.visitaService.findById(id).get();
		model.addAttribute("visita", visita);
		return "dettagliVisita.html";
	}
	

	
	@GetMapping("/prenotazione/visitaId={id}")
	public String prenotabileOno(@PathVariable("id")Long id, Model model) {
		Visita visita = this.visitaService.findById(id).get();
		model.addAttribute("visita", visita);
		model.addAttribute("prenotazione", new Prenotazione());
		model.addAttribute("disponibili", this.generaOrariDsponibili(visita.getOrarioApertura().toString(), visita.getOrarioChiusura().toString()));
		return "formNewPrenotazione.html";
	}
	
	
	private Integer generaIdentificativo() {
		int num = 0;
		while(this.prenotazioneService.existByIdentificativoBiglietto(num)) {
			num++;
		}
		return num;
	}
	
	@PostMapping("/prenotazione/{id}") 
	public String formNewPrenotazione(@ModelAttribute("prenotazione")Prenotazione prenotazione,
			@ModelAttribute("orarioScelto") LocalTime orario,
			@PathVariable("id")Long idVisita, Model model){		// quando ti logghi la mail viene presa in automatico con la classe UserDetails di JPA
		
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credentials credenziali = this.credentialsService.getCredentials(userDetails.getUsername());
				
		Visita visitaEntita = this.visitaRepository.findById(idVisita).get();
		
			prenotazione.setIdentificativoBiglietto(generaIdentificativo());
			
			
			prenotazione.setVisita(visitaEntita);
			prenotazione.setOrario(orario);
			
			
			User persona = this.userService.findByEmail(credenziali.getUser().getEmail());
		
			
			prenotazione.setIntestatarioBiglietto(persona);
			this.prenotazioneService.save(prenotazione);
			
			return "prenotazioneSuccesso.html"; 
		  
	}
	
	
	// 8:58
	// 0123 sono gli indici (4)
	
	
	private String approssimazionePerDifetto(String ora) {
		String ore;
		String minuti;
		if(ora.length() == 4) {
			ore = ora.substring(0,1);		// l'ultimo indice non e' compreso nella substring
			minuti = ora.substring(2,4);
		} else {
			ore = ora.substring(0,2);		// l'ultimo indice non e' compreso nella substring, qui è come se fosse 10:20
			minuti = ora.substring(3,5);
		}
		
		Integer i = Integer.parseInt(minuti);
		if(i>0 && i<30) {
			minuti = "00";
		} else if(i>30){
			minuti = "30";
		}
		return ore + ":" + minuti;
	}
	
	private String approssimazionePerEccesso(String ora) {
		String ore;
		String minuti;
		if(ora.length() == 4) {
			ore = ora.substring(0,1);		// l'ultimo indice non e' compreso nella substring
			minuti = ora.substring(2,4);
		} else {
			ore = ora.substring(0,2);		// l'ultimo indice non e' compreso nella substring, qui è come se fosse 10:20
			minuti = ora.substring(3,5);
		}
		
		Integer i = Integer.parseInt(minuti);		// trasforma da stringa a intero
		if(i>0 && i<30) {
			minuti = "30";
		} else if(i>30){
			Integer oraNuova = Integer.parseInt(ore);
			oraNuova++;
			ore = Integer.toString(oraNuova);
			minuti = "00";
		}
		return ore + ":" + minuti;
	}
	
	
	private String aggiorna30min(String ora) {
		String ore;
		String minuti;
		
		if(ora.length() == 4) {
			ore = ora.substring(0,1);		
			minuti = ora.substring(2,4);
		} else {
			ore = ora.substring(0,2);		
			minuti = ora.substring(3,5);
		}
		
		if(minuti.equals("00")) {
			minuti = "30";
		} else {
			minuti = "00";
			Integer oraNuova = Integer.parseInt(ore);
			oraNuova++;
			ore = Integer.toString(oraNuova);
			if(ore.length()==1) {
				ore = "0" + ore;
			}
		}
		
		return ore + ":" + minuti;
	}
	
	
	private List<String> generaOrariDsponibili(String orarioInizio, String orarioFine) {
		List<String> orariDisponibili = new ArrayList<>();
		
		String inizio = this.approssimazionePerEccesso(orarioInizio);
		String fine = this.approssimazionePerDifetto(orarioFine);
		  
		while(!(inizio.equals(fine))) {
			orariDisponibili.add(inizio);
			inizio = this.aggiorna30min(inizio);
		}
		
		return orariDisponibili;	
	}
}
