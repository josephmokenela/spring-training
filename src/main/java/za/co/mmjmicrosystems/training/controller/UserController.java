package za.co.mmjmicrosystems.training.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import za.co.mmjmicrosystems.training.dto.UserEditForm;
import za.co.mmjmicrosystems.training.entities.User;
import za.co.mmjmicrosystems.training.services.UserService;
import za.co.mmjmicrosystems.training.util.FlashUtils;

@Controller
public class UserController {
	
	private UserService userService;
	
	
	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}



	@RequestMapping("/users/{verificationCode}/verify")
	public String verify(@PathVariable("verificationCode") String verificationCode, RedirectAttributes redirectAttributes,
			HttpServletRequest request) throws ServletException {
		
		userService.verify(verificationCode);
		FlashUtils.flash(redirectAttributes, "success", "verificationSuccess");
		request.logout();
		
		return "redirect:/";
	}
	
	@RequestMapping(value="/users/{userId}")
	public String getById(@PathVariable("userId") long userId, Model model) {
		model.addAttribute("user", userService.findOne(userId));
		
		return "user";
	}
	
	@RequestMapping(value="/users/{userId}/edit")
	public String edit(@PathVariable("userId") long userId, Model model) {
		
		User user = userService.findOne(userId);
		UserEditForm userEditForm = new UserEditForm();
		userEditForm.setName(user.getName());
		userEditForm.setRoles(user.getRoles());
		model.addAttribute(userEditForm);
		
		return "user-edit";
	}
	
	@RequestMapping(value="/users/{userId}/edit", method=RequestMethod.POST)
	public String edit(@PathVariable("userId") long userId, @ModelAttribute("userEditForm") @Valid UserEditForm userEditForm, 
			BindingResult result, RedirectAttributes redirectAttributes, HttpServletRequest request) throws ServletException {
		
		if (result.hasErrors())
			return "user-edit";
		
		userService.update(userId, userEditForm);
		FlashUtils.flash(redirectAttributes, "success", "editSuccessful");
		request.logout();
		
		return "redirect:/";
	}

}
