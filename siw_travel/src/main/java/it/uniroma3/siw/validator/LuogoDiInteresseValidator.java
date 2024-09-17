package it.uniroma3.siw.validator;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Citta;
import it.uniroma3.siw.model.LuogoDiInteresse;
import it.uniroma3.siw.repository.LuogoDiInteresseRepository;

@Component
public class LuogoDiInteresseValidator implements Validator {

	@Autowired
	private LuogoDiInteresseRepository luogoDiInteresseRepository;

	@Override
	public void validate(Object o, Errors errors) {
		LuogoDiInteresse luogoDiInteresse = (LuogoDiInteresse)o;
		if (luogoDiInteresse != null 
				&& luogoDiInteresseRepository.existsByCittaAndIndirizzo(luogoDiInteresse.getCitta(), luogoDiInteresse.getIndirizzo())){
			errors.reject("luogoDiInteresse.duplicate");
		}
	}
	
	@Override
	public boolean supports(Class<?> aClass) {
		return LuogoDiInteresse.class.equals(aClass);
	}
	
}
