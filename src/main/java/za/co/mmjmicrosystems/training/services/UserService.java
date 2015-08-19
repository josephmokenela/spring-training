package za.co.mmjmicrosystems.training.services;

import za.co.mmjmicrosystems.training.dto.SignUpForm;


public interface UserService {
	
	public void signup(SignUpForm signUpForm);
	
	public void verify(String verificationCode);

}
