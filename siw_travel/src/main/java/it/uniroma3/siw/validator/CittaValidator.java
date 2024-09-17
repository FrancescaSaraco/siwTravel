package it.uniroma3.siw.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Citta;
import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.repository.CittaRepository;

@Component
public class CittaValidator implements Validator {

	@Autowired
	private CittaRepository cittaRepository;

	@Override
	public void validate(Object o, Errors errors) {
		Citta citta = (Citta)o;
		if (citta != null 
				&& cittaRepository.existsByStatoAndNome(citta.getStato(), citta.getNome())){
			errors.reject("citta.duplicate");
		} 
		else if (citta.getNome() == null || citta.getStato() == null) {
			errors.reject("citta.nulla");
		}
	}
	@Override
	public boolean supports(Class<?> aClass) {
		return Citta.class.equals(aClass);
	}
	
}
