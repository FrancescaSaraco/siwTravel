package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

@Controller
public class LuogoDiInteresseController {
	@Autowired 
	LuogoDiInteresseService luogoDiInteresseService;
	@Autowired
	CittaService cittaService;
	@Autowired
	VisitaService visitaService;
	
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
			@ModelAttribute("luogo")LuogoDiInteresse luogo, Model model){
		luogo.setIndirizzo(indirizzo);
		luogo.setNome(nome);
		luogo.setTipologia(tipologia);
		luogo.setValutazione(valutazione);
		luogo.setDescrizione(descrizione);
		Citta citta = cittaService.findByNome(nomeCitta);
		luogo.setCitta(citta);
		this.luogoDiInteresseService.save(luogo);
		return "admin/luogoSuccesso.html";
	}
	
//	@GetMapping("/index")  
//	public String index() {
//		return "index.html";
//	}
	
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
	
	@GetMapping("/luoghiDellaCitta/{id}")		// perchè mi trovo in quella pagina, nell'href si mette l'URL non la pagina html
	public String ritornaLuogoEffettivo(@PathVariable("id")Long id, Model model) {
		LuogoDiInteresse luogo = this.luogoDiInteresseService.findById(id).get();		// grazie al get tramite quell'id ottengo proprio il luogo che corrisponde a quell'id
		model.addAttribute("luogo", luogo);
		return "luogoInfo.html";		// tutto ciò che viene selezionato nell'URL suddetto viene stampato in questa pagina html
	}
	  
	@GetMapping("/visita/luogoId={id}") 
	public String ritornaFormPrenotazione(@PathVariable("id")Long id, Model model){
		LuogoDiInteresse luogo = this.luogoDiInteresseService.findById(id).get();
		Visita visita = this.visitaService.findByLuogoDiInteresse(luogo);
		model.addAttribute("visita", visita);
//		if (visita != null) {
//			model.addAttribute("luogo", luogo);
//		}
		return "dettagliVisita.html";
	}
	
	
}
