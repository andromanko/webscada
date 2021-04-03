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

@Controller
public class MainController {
    
    @GetMapping("/")
  public String home() {

    return "mainpage";
//    public ModelAndView  home() {
//        
//    	ModelAndView modelAndView = new ModelAndView();
//		modelAndView.setViewName("mainpage");
//		//modelAndView.addObject("login", principal.getName());
//		//modelAndView.addObject("role", principal.toString());
//		return modelAndView;
    	
    	
    }
        
    @GetMapping("/login")
    public String login() {
        return "login";
    }
    
    @GetMapping("/403")
    public String error403() {
        return "/error/403";
    }
    
    @GetMapping("/devices")
    public String devices() {
        return "devices";
    }
    
    @GetMapping("/monitor")
    public String monitor() {
        return "monitor";
    }
    
    @GetMapping("/anonymous")
    public String anonymous() {
        return "anonymous";
    }
}
