package za.co.mmjmicrosystems.training.services.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import za.co.mmjmicrosystems.training.dto.SignUpForm;
import za.co.mmjmicrosystems.training.dto.UserDetailsImpl;
import za.co.mmjmicrosystems.training.entities.User;
import za.co.mmjmicrosystems.training.repositories.UserRepository;
import za.co.mmjmicrosystems.training.services.UserService;



@Service
@Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
public class UserServiceImpl implements UserService, UserDetailsService {
	
	private UserRepository userRepository;
	
	@Autowired
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}



	@Override
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void signup(SignUpForm signUpForm) {
		User user = new User();
		user.setEmail(signUpForm.getEmail());
		user.setName(signUpForm.getName());
		user.setPassword(signUpForm.getPassword());
		userRepository.save(user);
		
	}



	@Override
	public UserDetails loadUserByUsername(String email)
			throws UsernameNotFoundException {
		User user = userRepository.findByEmail(email);
		
		if (user == null) {
			throw new UsernameNotFoundException(email);
		}
		
		return new UserDetailsImpl(user);
	}

}
