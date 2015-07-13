package za.co.mmjmicrosystems.training.validators;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import za.co.mmjmicrosystems.training.dto.SignUpForm;
import za.co.mmjmicrosystems.training.entities.User;
import za.co.mmjmicrosystems.training.repositories.UserRepository;

@Component
public class SignupFormValidator extends LocalValidatorFactoryBean {
	
	private UserRepository userRepository;
	
	@Resource
	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.isAssignableFrom(SignUpForm.class);
	}
	
	@Override
	public void validate(Object object, Errors errors, final Object...validationHints) {
		
		super.validate(object, errors, validationHints);
		
		if (!errors.hasErrors()) {
			SignUpForm signUpForm = (SignUpForm) object;
			User user = userRepository.findByEmail(signUpForm.getEmail());
			if (user != null)
				errors.rejectValue("email", "emailNotUnque");
		}
	}

}
