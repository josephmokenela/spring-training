package za.co.mmjmicrosystems.training.services;

import org.springframework.validation.BindingResult;

import za.co.mmjmicrosystems.training.dto.ForgotPasswordForm;
import za.co.mmjmicrosystems.training.dto.ResetPasswordForm;
import za.co.mmjmicrosystems.training.dto.SignUpForm;


public interface UserService {
	
	public void signup(SignUpForm signUpForm);
	
	public void verify(String verificationCode);

	public void forgotPassword(ForgotPasswordForm forgotPasswordForm);

	public void resetPassword(String forgotPasswordCode,
			ResetPasswordForm resetPasswordForm, BindingResult result);

}


