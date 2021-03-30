package webscada.rest.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import webscada.api.dto.UserDto;
import webscada.api.services.IUserService;

@Controller
@RequestMapping(value = "/signup")
public class SignupController {

    @Autowired
    private IUserService userService;
    
    @Autowired
    AuthenticationManager authenticationManager;
    
    @GetMapping
    public String signupUser(Model model) {
        model.addAttribute("dto", new UserDto());
        return "signup";
    }

    @PostMapping
    public String submitCreatingUser(@ModelAttribute UserDto dto, Model model, HttpServletRequest request) {
        try {
            model.addAttribute("dto", dto);
            UserDto dtoNew = userService.createUser(dto);
            //здесь пробуем войти новоприбывшим юзером
            authWithAuthManager(request, dto.getLogin(), dtoNew.getPassword());
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "/error/createEntityError";
        }
        return "signupResult";
    }
    
    //TODO
    private void authWithAuthManager(HttpServletRequest request, String username, String password) {
        
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
        authToken.setDetails(new WebAuthenticationDetails(request));
        
        Authentication authentication = authenticationManager.authenticate(authToken);
        
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
