package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.model.Citta;
import it.uniroma3.siw.model.LuogoDiInteresse;
import it.uniroma3.siw.model.Visita;
import it.uniroma3.siw.service.CittaService;
import it.uniroma3.siw.service.LuogoDiInteresseService;
import it.uniroma3.siw.service.VisitaService;
import it.uniroma3.siw.validator.LuogoDiInteresseValidator;
import jakarta.validation.Valid;

@Controller
public class LuogoDiInteresseController {
	@Autowired 
	LuogoDiInteresseService luogoDiInteresseService;
	@Autowired
	CittaService cittaService;
	@Autowired
	VisitaService visitaService;
	@Autowired LuogoDiInteresseValidator luogoDiInteresseValidator;
	
	@GetMapping("/admin/formNewLuogoDiInteresse")
	public String formNewLuogo(Model model) {
		model.addAttribute("luogo", new LuogoDiInteresse());
		model.addAttribute("cittas", cittaService.findAll());		// tra gli apici devo mettere il nome della variabile che uso per scrollare la lista in html
		return "admin/formNewLuogoDiInteresse.html";
	}
	
	@PostMapping("/admin/luogo") 
	public String newLuogo(@RequestParam("indirizzo")String indirizzo,@RequestParam("tipologia")String tipologia,
			@RequestParam("valutazione")Integer valutazione, @RequestParam("nome")String nome, 
			@RequestParam("nomeCitta")String nomeCitta, @RequestParam("descrizione")String descrizione, 
			@Valid @ModelAttribute("luogo")LuogoDiInteresse luogo,BindingResult result, Model model){
		
		Citta citta = cittaService.findByNome(nomeCitta);
		
		if(citta == null) {
			model.addAttribute("luogo", luogo);
			model.addAttribute("cittas", cittaService.findAll());
			model.addAttribute("messaggioErrore", "Inserire una citta'!");
			return "admin/formNewLuogoDiInteresse.html";
		} else {
			luogo.setIndirizzo(indirizzo);
			luogo.setCitta(citta);
			
			this.luogoDiInteresseValidator.validate(luogo, result);
			
			
			if(!result.hasErrors()) {
				luogo.setNome(nome);
				luogo.setTipologia(tipologia);
				luogo.setValutazione(valutazione);
				luogo.setDescrizione(descrizione);
				this.luogoDiInteresseService.save(luogo);
				return "admin/luogoSuccesso.html";
			} else {
				model.addAttribute("luogo", luogo);
				model.addAttribute("cittas", cittaService.findAll());
				return "admin/formNewLuogoDiInteresse.html";
			}
		}
	}
	
	@GetMapping("/admin/luogoSuccesso") 
	public String andato(){
		return "admin/luogoSuccesso.html";
	}
	
	@GetMapping("/elencoLuoghi") 
	public String getAllLuoghi(Model model) {
//		List<Citta> cittaPresenti = cittaService.findAll();
		model.addAttribute("luoghi", luogoDiInteresseService.findAll());
//		model.addAttribute("numeroLuoghi", cittaService.count(cittaPresenti));
		return "elencoLuoghi.html";
	} 
	
	@GetMapping("/luoghiDellaCitta/{id}")		
	public String ritornaLuogoEffettivo(@PathVariable("id")Long id, Model model) {
		LuogoDiInteresse luogo = this.luogoDiInteresseService.findById(id).get();		
		model.addAttribute("luogo", luogo);
		return "luogoInfo.html";		 
	}
	  
	@GetMapping("/visita/luogoId={id}") 
	public String ritornaFormPrenotazione(@PathVariable("id")Long id, Model model){
		LuogoDiInteresse luogo = this.luogoDiInteresseService.findById(id).get();
		Visita visita = this.visitaService.findByLuogoDiInteresse(luogo);
		model.addAttribute("visita", visita);
		return "dettagliVisita.html";
	}
	
	
	@GetMapping("/admin/showFormAggiornaLuogo/{id}")
	public String showFormAggiornaLuogo(@PathVariable("id") Long id, Model model) {
		LuogoDiInteresse luogo = this.luogoDiInteresseService.findById(id).get();
		model.addAttribute("luogo", luogo);
		return "admin/formAggiornaLuogo.html";
	}
	
	@PostMapping("/admin/formAggiornaLuogo/{id}")
	public String formAggiornaLuogo(@PathVariable("id") Long id, @ModelAttribute LuogoDiInteresse nuovoLuogo, Model model) {
		LuogoDiInteresse luogo = this.luogoDiInteresseService.findById(id).get();
		
		if(!nuovoLuogo.getNome().equals(luogo.getNome())) {
			luogo.setNome(nuovoLuogo.getNome());
		}
		
		if(!nuovoLuogo.getDescrizione().equals(luogo.getDescrizione())) {
			luogo.setDescrizione(nuovoLuogo.getDescrizione());
		}
		
		if(!nuovoLuogo.getTipologia().equals(luogo.getTipologia())) {
			luogo.setTipologia(nuovoLuogo.getTipologia());
		}
		
		if(!nuovoLuogo.getValutazione().equals(luogo.getValutazione())) {
			luogo.setValutazione(nuovoLuogo.getValutazione());
		}
		
		if(!nuovoLuogo.getUrlImage().equals(luogo.getUrlImage())) {
			luogo.setUrlImage(nuovoLuogo.getUrlImage());
		}
		
		this.luogoDiInteresseService.save(luogo);
		return "admin/index.html";
	}
	
	
}
