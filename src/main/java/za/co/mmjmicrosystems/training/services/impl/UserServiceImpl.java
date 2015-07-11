package za.co.mmjmicrosystems.training.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import za.co.mmjmicrosystems.training.dto.SignUpForm;
import za.co.mmjmicrosystems.training.entities.User;
import za.co.mmjmicrosystems.training.repositories.UserRepository;
import za.co.mmjmicrosystems.training.services.UserService;



@Service
public class UserServiceImpl implements UserService {
	
	private UserRepository userRepository;
	
	@Autowired
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}



	@Override
	public void signup(SignUpForm signUpForm) {
		User user = new User();
		user.setEmail(signUpForm.getEmail());
		user.setName(signUpForm.getName());
		user.setPassword(signUpForm.getPassword());
		userRepository.save(user);
		
	}

}
