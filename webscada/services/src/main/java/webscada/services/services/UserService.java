package webscada.services.services;

import java.io.IOException;

import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

//import webscada.api.dao.IPetJPADao;
import webscada.api.dao.IUserJPADao;
import webscada.api.dao.IRoleJPADao;
import webscada.api.dto.UserDto;
//import eu.it.academy.api.dto.UserPetIdsDto;
import webscada.api.mappers.UserMapper;
import webscada.api.services.IUserService;
//import eu.it.academy.entities.Pet;
import webscada.entity.User;
import webscada.entity.Role;
import webscada.services.utils.LogoFileUploader;
//import eu.it.academy.web.BookDetails;
//import eu.it.academy.web.WebScraper;
import lombok.extern.slf4j.Slf4j;
import webscada.api.utils.IEmailSender;

@Slf4j
@Service
public class UserService implements IUserService {

	@Autowired
	private IUserJPADao userJPADao;
	@Autowired
	private IRoleJPADao roleJPADao;
	@Autowired
	private IEmailSender emailSender;
//
//    @Autowired
//    private IPetJPADao petJPADao;
//    
	@Autowired
	private PasswordEncoder passwordEncoder;

//    @Autowired
//    WebScraper webScraper;

	@Override
	public UserDto findUser(long id) {
		User user = this.userJPADao.findById(id).orElse(null);
		return (user != null) ? UserMapper.mapUserDto(user) : null;
	}

	@Override
	public UserDto findUserByLogin(String login) {
		return UserMapper.mapUserDto(this.userJPADao.findByLogin(login));
	}

	@Override
	public UserDto createUser(UserDto userDto) {// throws Exception {

		User user = new User(); // вот! создали ЮЗЕРА
		user.setLogin(userDto.getLogin());
		user.setEmail(userDto.getEmail());
		user.setEnabled(true);
		User userByLogin = this.userJPADao.findByLogin(userDto.getLogin());
		User userByEmail = this.userJPADao.findByEmail(userDto.getEmail());
		if (((userByLogin) != null) || ((userByEmail) != null)) {
			log.error("Failed to registerUser. The Login or Email already exists");
			// TODO переделать! Если юзер или мэйл существует
			return null;
		}

		user.setPassword(passwordEncoder.encode(userDto.getPassword()));
		Role role = new Role();
		role = roleJPADao.findByRoleName("ROLE_VIEWER");
		HashSet<Role> roles = new HashSet<Role>();

		roles.add(role);
		user.setRoles(roles);
		try {
			User savedUser = this.userJPADao.save(user); // ЗАПИСАЛИ ЮЗЕРА
			// TODO автовход. Регистрация нового фбукюзера сделана.
			UserDto registeredUser = UserMapper.mapUserDto(savedUser);

			try {
				emailSender.sendEmailToAdmin(registeredUser, 1);
				emailSender.sendEmailFromAdmin(savedUser, 1);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				log.error("FFFailed to sent email");
			}
			return registeredUser;

		} catch (Exception e) {
			log.error("failed to create user:" + userDto.getLogin() + "; e-mail: " + userDto.getEmail(),
					e.getMessage());
			return null;
		}
	}

	@Override
	public void updateUser(String login, UserDto userDto, MultipartFile file) {
		User user = this.userJPADao.findByLogin(login);
		if (user != null) {
			user.setLogin(userDto.getLogin());
			user.setEmail(userDto.getEmail());
			user.setEnabled(userDto.isEnabled());

			try {
				this.userJPADao.save(user);
			} catch (Exception e) {
				log.error("failed to update user:" + userDto.getLogin() + "; e-mail: " + userDto.getEmail(),
						e.getMessage());
			}
		}
		try {
			LogoFileUploader.updateOrCreateLogo(file, userDto);
		} catch (IOException e) {
			log.error("Failed to upload image. Error message: {}", e.getMessage());
		}

		try {
			emailSender.sendEmailFromAdmin(user, 1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("FFFailed to sent email from upd");
		}

	}

	@Override
	public void deleteUser(long id) {
		this.userJPADao.deleteById(id);
	}

	@Override
	public List<UserDto> getUsers() {
		return UserMapper.mapUserDtos(userJPADao.findAll());
	}
}
