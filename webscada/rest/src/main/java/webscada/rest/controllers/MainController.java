package webscada.rest.controllers;

import java.net.URL;
import java.net.URLClassLoader;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import webscada.api.dto.UserDto;
import webscada.entity.User;

@Controller
public class MainController {
    
//    @GetMapping("/")
//  public String home(Principal principal) {
//
//   return "mainpage";
//    }
	@GetMapping("/")
    public ModelAndView  home(Principal principal) {
        
    	ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("mainpage");
		modelAndView.addObject("title", "Main Page");
			String name=principal!=null ? principal.getName() : "not authorized";
			String role = "none";
		modelAndView.addObject("login", name);
		modelAndView.addObject("role", role);
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
