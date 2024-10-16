package it.uniroma3.siw.controller;

import java.util.List;

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
import it.uniroma3.siw.repository.CittaRepository;
import it.uniroma3.siw.service.CittaService;
import it.uniroma3.siw.service.LuogoDiInteresseService;
import it.uniroma3.siw.validator.CittaValidator;
import jakarta.validation.Valid;

@Controller
public class CittaController {
	@Autowired CittaService cittaService;
	@Autowired LuogoDiInteresseService luogoService;
	@Autowired CittaValidator cittaValidator;
	@Autowired CittaRepository cittaRepository;
	
	
	@GetMapping("/admin/formNewCitta")		// questo è un link
	public String formNewCitta(Model model) {
		model.addAttribute("citta", new Citta());		// istanziamento di una nuova citta con attributo "citta", cioè istanziami una citta e mettila in un attributo che si chiama citta
		return "admin/formNewCitta.html";		// pagina html effettiva 
	}
	
	@PostMapping("/admin/citta") 
	public String newCitta(@Valid @ModelAttribute("citta")Citta citta, BindingResult result, Model model){
		this.cittaValidator.validate(citta, result);
		if(!result.hasErrors()) {
			this.cittaService.save(citta);
			model.addAttribute("citta", citta);
			return "admin/cittaSuccesso.html";
		} else {
			model.addAttribute("citta", citta);
			return "admin/formNewCitta.html";
		}
	}
	
	
	@GetMapping("/admin/cittaSuccesso") 
	public String gone(){
		return "cittaSuccesso.html";
	}
	
	@GetMapping("/admin/elencoCitta") 
	public String getAllCitta(Model model) {
		List<Citta> cittaPresenti = cittaService.findAll();
		model.addAttribute("cittas", cittaPresenti);
//		model.addAttribute("numeroCitta", cittaService.count(cittaPresenti));
		return "admin/elencoCitta.html";
	}
	
	@GetMapping("/ricercaPerNome")
	public String barraRicerca(@RequestParam("nome")String nome, Model model) {
		List<Citta> elencoCittaPerNome = this.cittaService.findByNome(nome);
		model.addAttribute("cittas", elencoCittaPerNome);
		return "cercaCitta.html";
	}

	
	@GetMapping("/listaLuoghiDiInteresse/{id}")
	public String barraRicerca2(@PathVariable("id")Long idcitta, Model model) {
		Citta cittaEffettiva = this.cittaRepository.findById(idcitta).get();
		List<LuogoDiInteresse> luoghi = this.luogoService.findByCitta(cittaEffettiva);
		
		//int numero = this.luogoService.contaLuoghi(cittaEffettiva);
		model.addAttribute("numero", luoghi.size());
		model.addAttribute("luoghi", luoghi);
		return "luoghiDellaCitta.html"; 
	}
	
	// per tornare indietro
	/*@GetMapping("/listaLuoghiDiInteresse/{id}")
	public String comeBack(@PathVariable("id")Long idCitta, Model model) {
		Citta cittaEffettiva = this.cittaRepository.findById(idCitta).get();
		model.addAttribute("luoghi", this.luogoService.findByCitta(cittaEffettiva));
		return "luoghiDellaCitta.html";
	}*/
	
	@GetMapping("/luoghiDellaCitta") 
	public String ottenimentoLuoghiByCitta(Model model){
		return "luoghiDellaCitta.html";
	}
	
	@GetMapping("/admin/listaLuoghiDiInteresse/{id}")
	public String ricercaLuoghiPerAdmin(@PathVariable("id") Long id, Model model) {
		Citta cittaEffettiva = this.cittaRepository.findById(id).get();
		model.addAttribute("luoghi", this.luogoService.findByCitta(cittaEffettiva));
		return "admin/luoghiDellaCittaAdmin.html";
	}
	
}
