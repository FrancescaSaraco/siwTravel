package it.uniroma3.siw.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.model.Citta;
import it.uniroma3.siw.service.CittaService;
import it.uniroma3.siw.service.LuogoDiInteresseService;

@Controller
public class CittaController {
	@Autowired CittaService cittaService;
	@Autowired LuogoDiInteresseService luogoService;
	
	
	@GetMapping("/admin/formNewCitta")		// questo è un link
	public String formNewCitta(Model model) {
		model.addAttribute("citta", new Citta());		// istanziamento di una nuova citta con attributo "citta", cioè istanziami una citta e mettila in un attributo che si chiama citta
		return "admin/formNewCitta.html";		// pagina html effettiva 
	}
	
	@PostMapping("/admin/citta") 
	public String newCitta(@ModelAttribute("citta")Citta citta, Model model){
		if(citta.getStato().isEmpty() || citta.getNome().isEmpty()) {
			model.addAttribute("messaggioErrore", "I due campi sono obbligatori!");
			return "admin/formNewCitta.html";
		}
		if(!cittaService.existsByStatoAndNome(citta.getStato(), citta.getNome())) {
			this.cittaService.save(citta);
			model.addAttribute("citta", citta);
			return "admin/cittaSuccesso.html";
		} else {
			model.addAttribute("messaggioErrore", "Questa citta' e' gia' stata inserita!");
			return "admin/formNewCitta.html";
		}
	}
	
//	@GetMapping("/")  
//	public String index(/* Model model */) {
////		List<Citta> cittas = this.cittaService.findAll();
////		int numeroCitta = this.cittaService.count(cittas);
////		model.addAttribute("numeroCitta", numeroCitta);
//		return "homePage.html";
//	}
	
	@GetMapping("/admin/cittaSuccesso") 
	public String gone(){
		return "cittaSuccesso.html";
	}
	
	@GetMapping("/admin/elencoCitta") 
	public String getAllCitta(Model model) {
//		List<Citta> cittaPresenti = cittaService.findAll();
		model.addAttribute("cittas", cittaService.findAll());
//		model.addAttribute("numeroCitta", cittaService.count(cittaPresenti));
		return "elencoCitta.html";
	}
	
	
	@GetMapping("/listaLuoghiDiInteresse")
	public String barraRicerca(@ModelAttribute("citta")String citta, Model model) {
		Citta cittaEffettiva = this.cittaService.findByNome(citta);
		model.addAttribute("luoghi", this.luogoService.findByCitta(cittaEffettiva));
		return "luoghiDellaCitta.html";
	}
	
	@GetMapping("/luoghiDellaCitta") 
	public String ottenimentoLuoghiByCitta(Model model){
		return "luoghiDellaCitta.html";
	}
	  
//	@GetMapping("/admin/index")  
//	public String index2() {
//		return "index.html";
//	}
	
	// ogni indirizzo URL corrisponde a una certa pagina html che non per forza ha lo stesso nome dell'URL, vedi POST
	
}
