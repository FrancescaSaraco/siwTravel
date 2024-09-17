package it.uniroma3.siw.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.repository.CredentialsRepository;

@Component
public class CredentialsValidator implements Validator {

	@Autowired
	private CredentialsRepository credenzialiRepository;

	@Override
	public void validate(Object o, Errors errors) {
		Credentials cred = (Credentials)o;
		if (cred.getUsername()!=null 
				&& credenzialiRepository.existsByUsername(cred.getUsername())){
			errors.reject("credentials.duplicate");
		}
	}
	@Override
	public boolean supports(Class<?> aClass) {
		return Credentials.class.equals(aClass);
	}
	
}
