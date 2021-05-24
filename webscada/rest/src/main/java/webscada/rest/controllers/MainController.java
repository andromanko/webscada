package webscada.rest.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import webscada.api.services.IUserService;

@Controller
public class MainController {

	@Autowired
	IUserService userService;

	@GetMapping("/")
	public ModelAndView home(Principal principal) {

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("mainpage");
		modelAndView.addObject("title", "Main Page");

		modelAndView.addObject("login", principal != null ? principal.getName() : "not authorized");

		modelAndView.addObject("role",
				SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString());
		return modelAndView;

	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/403")
	public String error403() {
		return "error/403";
	}

	@GetMapping("/anonymous")
	public String anonymous() {
		return "anonymous";
	}
}
