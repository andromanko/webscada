package webscada.rest.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import webscada.api.dto.UserDto;
//import webscada.api.dto.UserPetIdsDto;
import webscada.api.services.IUserService;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private IUserService userService;

//	@Autowired
//	Principal principal;//=getPrincipal();
	
	//Principal principal;
	
	@GetMapping
	public ModelAndView findUsers() {
		
		List<UserDto> users = userService.getUsers();
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("usersPage");
		modelAndView.addObject("title", "My Users:");
		modelAndView.addObject("usersList", users);
		return modelAndView;
	}

	@GetMapping(value = "/{id}")
	public ModelAndView findUser(@PathVariable int id) {
		UserDto user = userService.findUser(id);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("userPage");
		modelAndView.addObject("user", user);
		return modelAndView;
	}

	@GetMapping(value = "/name/{login}")
	public UserDto findUserByLogin(@PathVariable String login) {
		return userService.findUserByLogin(login);
	}

	// ===============create=================

	@GetMapping(value = "/add")
	public ModelAndView createUser() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("usersFormPage");
		modelAndView.addObject("user", new UserDto());
		return modelAndView;
	}

	@PostMapping(value = "/add")
	public UserDto createUserSubmit(UserDto user) {
		return this.userService.createUser(user);
	}

	// ===============update=================

	@GetMapping(value = "/upd")
	public ModelAndView updateUser() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("usersFormPageUpd");
		modelAndView.addObject("user", new UserDto());
		return modelAndView;
	}

    @PostMapping(value = "/upd")
    public void updateUser(UserDto user, @RequestParam(value = "file", required = false) MultipartFile file) {
        this.userService.updateUser(user.getLogin(), user, file);
    }

	// ================================

	@DeleteMapping(value = "/{id}")
	public void deleteUser(@PathVariable int id) {
		this.userService.deleteUser(id);
	}

//	@PatchMapping()
//	public void assingPetToUser(@RequestBody UserPetIdsDto ids) {
//		this.userService.assingPetToUser(ids);
//	}

//	@GetMapping("/book")
//	public ModelAndView search(@RequestParam(value = "isbn", required = false) String isbn) {
//		ModelAndView modelAndView = new ModelAndView();
//		userService.getBookByIsbn(isbn);
//		return modelAndView;
//	}
}
