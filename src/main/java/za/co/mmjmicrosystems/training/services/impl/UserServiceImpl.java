package za.co.mmjmicrosystems.training.services.impl;


import javax.mail.MessagingException;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.validation.BindingResult;

import za.co.mmjmicrosystems.training.dto.ForgotPasswordForm;
import za.co.mmjmicrosystems.training.dto.ResetPasswordForm;
import za.co.mmjmicrosystems.training.dto.SignUpForm;
import za.co.mmjmicrosystems.training.dto.UserDetailsImpl;
import za.co.mmjmicrosystems.training.dto.UserEditForm;
import za.co.mmjmicrosystems.training.entities.User;
import za.co.mmjmicrosystems.training.entities.User.Role;
import za.co.mmjmicrosystems.training.mail.MailSender;
import za.co.mmjmicrosystems.training.mail.MockMailSender;
import za.co.mmjmicrosystems.training.repositories.UserRepository;
import za.co.mmjmicrosystems.training.services.UserService;
import za.co.mmjmicrosystems.training.util.FlashUtils;



@Service
@Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
public class UserServiceImpl implements UserService, UserDetailsService {
	
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	private UserRepository userRepository;
	private PasswordEncoder passwordEncoder;
	private MailSender mailSender;
	
	@Autowired
	public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, MailSender mailSender) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.mailSender = mailSender;
	}



	@Override
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void signup(SignUpForm signUpForm) {
		final User user = new User();
		user.setEmail(signUpForm.getEmail());
		user.setName(signUpForm.getName());
		user.setPassword(passwordEncoder.encode(signUpForm.getPassword().trim()));
		user.getRoles().add(Role.UNVERIFIED);
		user.setVerificationCode(RandomStringUtils.randomAlphanumeric(16));
		userRepository.save(user);
		
		TransactionSynchronizationManager.registerSynchronization(
			    new TransactionSynchronizationAdapter() {
			        @Override
			        public void afterCommit() {
			    		try {
			    			String verifyLink = FlashUtils.hostUrl() + "/users/" + user.getVerificationCode() + "/verify";
			    			mailSender.send(user.getEmail(), FlashUtils.getMessage("verifySubject"), FlashUtils.getMessage("verifyEmail", verifyLink));
			    			logger.info("Verification mail to " + user.getEmail() + " queued.");
						} catch (MessagingException e) {
							logger.error(ExceptionUtils.getStackTrace(e));
						}
			        }
		    });
		
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
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void verify(String verificationCode) {
		
		long loggedInUserId = FlashUtils.getSessionUser().getId();
		User user = userRepository.findOne(loggedInUserId);
		FlashUtils.validate(user.getRoles().contains(Role.UNVERIFIED), "alreadyVerified");
		FlashUtils.validate(user.getVerificationCode().equals(verificationCode), "incorrect", "Verification code");
		
		user.getRoles().remove(Role.UNVERIFIED);
		user.setVerificationCode(null);
		userRepository.save(user);
	}



	@Override
	public void forgotPassword(ForgotPasswordForm forgotPasswordForm) {
		
		final User user = userRepository.findByEmail(forgotPasswordForm.getEmail());
		final String forgotPasswordCode = RandomStringUtils.randomAlphanumeric(User.RANDOM_CODE_LENGTH);
		
		user.setForgotPasswordCode(forgotPasswordCode);
		final User savedUser = userRepository.save(user);
		
		TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
			
			@Override
			public void afterCommit() {
				try {
					mailForgotPasswordLink(savedUser);
				} catch(MessagingException ex) {
					logger.error(ExceptionUtils.getStackTrace(ex));
				}
			}
		});
		
	}
	
	private void mailForgotPasswordLink(User user) throws MessagingException {
		
		String forgotPasswordLink = FlashUtils.hostUrl() + "/reset-password/" + user.getForgotPasswordCode();
		mailSender.send(user.getEmail(), FlashUtils.getMessage("forgotPasswordSubject"), 
				FlashUtils.getMessage("forgotPasswordEmail",forgotPasswordLink));
	}



	@Override
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void resetPassword(String forgotPasswordCode,
			ResetPasswordForm resetPasswordForm, BindingResult result) {
		
		final User user = userRepository.findByForgotPasswordCode(forgotPasswordCode);
		if (user == null)
			result.reject("invalidForgotPassword");
		
		if (result.hasErrors())
			return;
		
		user.setForgotPasswordCode(null);
		user.setPassword(passwordEncoder.encode(resetPasswordForm.getPassword().trim()));
		userRepository.save(user);
	}



	@Override
	public User findOne(long userId) {
		
		User loggedIn = FlashUtils.getSessionUser();
		User user = userRepository.findOne(userId);
		
		if (loggedIn == null || loggedIn.getId() != user.getId() && !loggedIn.isAdmin())
			//hide the email id
			user.setEmail("Confidential");
		
		return user;
	}



	@Override
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void update(long userId, UserEditForm userEditForm) {
		
		User loggedIn = FlashUtils.getSessionUser();
		FlashUtils.validate(loggedIn.isAdmin() || loggedIn.getId() == userId, "noPermissions");
		User user = userRepository.findOne(userId);
		user.setName(userEditForm.getName());
		
		if (loggedIn.isAdmin())
			user.setRoles(userEditForm.getRoles());
		
		userRepository.save(user);
		
	}

}
