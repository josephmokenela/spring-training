package za.co.mmjmicrosystems.training.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import za.co.mmjmicrosystems.training.dto.ResetPasswordForm;

@Component
public class ResetPasswordValidator extends LocalValidatorFactoryBean {
	
	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.isAssignableFrom(ResetPasswordForm.class);
	}
	
	@Override
	public void validate(Object obj, Errors errors, final Object...validationHints) {
		
		if (!errors.hasErrors()) {
			ResetPasswordForm resetPasswordForm = (ResetPasswordForm) obj;
			if (!(resetPasswordForm.getPassword().equals(resetPasswordForm.getRetypePassword())))
				errors.reject("passwordsDoNotMatch");
			
		}
	}

}
