package webscada.rest.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import webscada.api.dto.UserDto;
import webscada.api.services.IUserService;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private IUserService userService;

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
		ModelAndView modelAndView = new ModelAndView("usersFormPage");
		new UserDto();
		UserDto userDto = UserDto.builder().login("please enter Your login").email("please enter Your e-mail")
				.info("please enter Your info").password("please enter Your password").build();

		modelAndView.addObject("user", userDto);
		return modelAndView;
	}

	@PostMapping(value = "/add")
	public ModelAndView createUserSubmit(UserDto user) {
		ModelAndView modelAndView = new ModelAndView("userPage");

		modelAndView.addObject("user", this.userService.createUser(user));
		return modelAndView;
	}

	// ===============update=================

	@GetMapping(value = "/upd")
	public ModelAndView updateUser(UserDto user) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("usersFormPageUpd");
		modelAndView.addObject("user", user);
		return modelAndView;
	}

	@PostMapping(value = "/upd")
	public RedirectView updateUser(UserDto user, @RequestParam(value = "file", required = false) MultipartFile file) {
		this.userService.updateUser(user.getLogin(), user, file);
		return new RedirectView("/users");
	}

	// ================================
	@PostMapping(value = "/del")
	public RedirectView deleteUser(UserDto user) {
		this.userService.deleteUser(user.getId());
		return new RedirectView("/users");
	}

	@GetMapping(value = "/del")
	public ModelAndView deleteUser() {
		return this.findUsers();
	}
}
