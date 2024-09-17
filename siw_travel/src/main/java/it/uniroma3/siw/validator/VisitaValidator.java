package it.uniroma3.siw.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Visita;
import it.uniroma3.siw.repository.VisitaRepository;

@Component
public class VisitaValidator implements Validator {

	@Autowired
	private VisitaRepository visitaRepository;

	@Override
	public void validate(Object o, Errors errors) {
		Visita visita = (Visita)o;
		if (visita != null 
				&& visitaRepository.existsByLuogoDiInteresse(visita.getLuogoDinteresse())){
			errors.reject("visita.duplicate");
		}
	}
	@Override
	public boolean supports(Class<?> aClass) {
		return Visita.class.equals(aClass);
	}
	
}
