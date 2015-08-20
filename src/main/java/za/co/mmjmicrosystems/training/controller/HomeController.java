package za.co.mmjmicrosystems.training.controller;

import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import za.co.mmjmicrosystems.training.dto.ForgotPasswordForm;
import za.co.mmjmicrosystems.training.dto.SignUpForm;
import za.co.mmjmicrosystems.training.services.UserService;
import za.co.mmjmicrosystems.training.util.FlashUtils;
import za.co.mmjmicrosystems.training.validators.ForgotPasswordFormValidator;
import za.co.mmjmicrosystems.training.validators.SignupFormValidator;


@Controller
public class HomeController {
	
	private static final Log log = LogFactory.getLog(HomeController.class);
	
	private UserService userService;
	private SignupFormValidator signupFormValidator;
	private ForgotPasswordFormValidator forgotPasswordFormValidator;
	
	@Autowired
	public HomeController(UserService userService, SignupFormValidator signupFormValidator,
			ForgotPasswordFormValidator forgotPasswordFormValidator) {
		this.userService = userService;
		this.signupFormValidator = signupFormValidator;
		this.forgotPasswordFormValidator = forgotPasswordFormValidator;
	}
	
	@InitBinder("signupform")
	protected void initSignupBinder(WebDataBinder binder) {
		binder.setValidator(signupFormValidator);
	}
	
	@InitBinder("forgotPasswordForm")
	protected void initForgotPasswordBinder(WebDataBinder binder) {
		binder.setValidator(forgotPasswordFormValidator);
	}
	

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
	public String signup(@ModelAttribute("signupform") @Valid SignUpForm signUpForm, BindingResult result,
			RedirectAttributes redirectAttributes) {
		
		if (result.hasErrors()) {
			return "signup";
		}
		
		userService.signup(signUpForm);
		
		FlashUtils.flash(redirectAttributes, "success", "signupSuccess");
		
		return "redirect:/";
	}
	
	@RequestMapping(value="/forgot-password", method=RequestMethod.GET)
	public String forgotPassword(Model model) {
		
		model.addAttribute("forgotPasswordForm", new ForgotPasswordForm());
		
		return "forgot-password";
	}
	
	@RequestMapping(value="/forgot-password", method=RequestMethod.POST)
	public String forgotPassword(@ModelAttribute("forgotPasswordForm") @Valid ForgotPasswordForm forgotPasswordForm, 
			BindingResult result, RedirectAttributes redirectAttributes) {
		
		if (result.hasErrors())
			return "forgot-password";
		
		userService.forgotPassword(forgotPasswordForm);
		FlashUtils.flash(redirectAttributes, "info", "checkMailResetPassword");
		
		return "redirect:/";
	}

}
