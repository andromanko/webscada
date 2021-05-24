package webscada.services.services;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;
import webscada.api.consts.EVENT;
import webscada.api.dao.IRoleJPADao;
import webscada.api.dao.IUserJPADao;
import webscada.api.dto.UserDto;
import webscada.api.mappers.UserMapper;
import webscada.api.services.IEventService;
import webscada.api.services.IUserService;
import webscada.api.utils.IEmailSender;
import webscada.entity.Role;
import webscada.entity.User;
import webscada.services.utils.LogoFileUploader;

@Slf4j
@Service
public class UserService implements IUserService {

	@Autowired
	private IUserJPADao userDao;

	@Autowired
	private IRoleJPADao roleJPADao;

	@Autowired
	private IEmailSender emailSender;

	@Autowired
	private IEventService eventService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserDto findUser(long id) {
		User user = this.userDao.findById(id).orElse(null);
		return (user != null) ? UserMapper.mapUserDto(user) : null;
	}

	@Override
	public UserDto findUserByLogin(String login) {
		return UserMapper.mapUserDto(this.userDao.findByLogin(login));
	}

	@Override
	public UserDto createUser(UserDto userDto) {

		User user = new User();
		user.setLogin(userDto.getLogin());
		user.setEmail(userDto.getEmail());
		user.setInfo(userDto.getInfo());
		user.setEnabled(true);
		// TODO в сравнении юзеров и мыла делать toLowerCase
		User userByLogin = this.userDao.findByLogin(userDto.getLogin());
		User userByEmail = this.userDao.findByEmail(userDto.getEmail());
		if (((userByLogin) != null) || ((userByEmail) != null)) {
			log.error("Failed to registerUser. The Login or Email already exists");
			// TODO переделать
			return null;
		}
		user.setPassword(passwordEncoder.encode(userDto.getPassword()));
		HashSet<Role> roles = new HashSet<Role>();
		roles.add(roleJPADao.findByRoleName("ROLE_VIEWER"));
		user.setRoles(roles);
		try {
			User savedUser = this.userDao.save(user);
			UserDto registeredUser = UserMapper.mapUserDto(savedUser);
			emailSender.sendEmailToAdmin(registeredUser, 1);
			emailSender.sendEmailFromAdmin(savedUser, 1);
			eventService.createEvent(EVENT.USER_REGISTER, savedUser.getId());
			return registeredUser;

		} catch (Exception e) {
			log.error("failed to create user:" + userDto.getLogin() + "; e-mail: " + userDto.getEmail(),
					e.getMessage());
			return null;
		}
	}

	@Override
	public void updateUser(String login, UserDto userDto, MultipartFile file) {
		User user = this.userDao.findByLogin(login);
		if (user != null) {
			// TODO !!!маппер
			user.setLogin(userDto.getLogin());
			user.setEmail(userDto.getEmail());
			user.setEnabled(userDto.isEnabled());
			user.setInfo(userDto.getInfo());

			try {
				this.userDao.save(user);
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
			log.error("FFFailed to sent email from upd");
		}
	}

	@Override
	public void deleteUser(long id) {
		this.userDao.deleteById(id);
	}

	@Override
	public List<UserDto> getUsers() {
		return UserMapper.mapUserDtos(userDao.findAll());
	}
}
