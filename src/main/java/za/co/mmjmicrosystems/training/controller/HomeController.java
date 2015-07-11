package za.co.mmjmicrosystems.training.controller;

import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import za.co.mmjmicrosystems.training.dto.SignUpForm;


@Controller
public class HomeController {
	
	private static final Log log = LogFactory.getLog(HomeController.class);
	
	@RequestMapping("/")
	public String home() {
		
		log.info("Inside the home method");
		
		return "home";
	}
	
	@RequestMapping(value="/signup", method=RequestMethod.GET)
	public String signup(Model model) {
		
		model.addAttribute("signupform", new SignUpForm());
		
		return "signup";
	}
	
	@RequestMapping(value="/signup", method=RequestMethod.POST)
	public String signup(@ModelAttribute("signupform") @Valid SignUpForm signUpForm, BindingResult result) {
		
		if (result.hasErrors()) {
			return "signup";
		}
		
		log.info("Name: " + signUpForm.getName());
		log.info("Email: " + signUpForm.getEmail());
		log.info("Password: " + signUpForm.getPassword());
		
		//userService.signup(signUpForm);
		
		return "redirect:/";
	}

}
