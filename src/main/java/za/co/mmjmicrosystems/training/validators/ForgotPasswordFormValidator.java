package za.co.mmjmicrosystems.training.validators;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import za.co.mmjmicrosystems.training.dto.ForgotPasswordForm;
import za.co.mmjmicrosystems.training.entities.User;
import za.co.mmjmicrosystems.training.repositories.UserRepository;

@Component
public class ForgotPasswordFormValidator extends LocalValidatorFactoryBean {
	
	private UserRepository userRepository;
	
	@Resource
	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}


	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.isAssignableFrom(ForgotPasswordForm.class);
	}

	
	@Override
	public void validate(Object obj, Errors errors, final Object...validationHints) {
		
		super.validate(obj, errors, validationHints);
		
		if(!errors.hasErrors()) {
			ForgotPasswordForm forgotPasswordForm = (ForgotPasswordForm) obj;
			User user = userRepository.findByEmail(forgotPasswordForm.getEmail());
			if (user == null) {
				errors.rejectValue("email", "not found");
			}
		}
		
	}
}
