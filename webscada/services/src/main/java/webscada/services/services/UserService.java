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
        //проверка новоЮзера на ужеСуществующегоЮзера
    	//слито от Авдейчика из одного из его видео =)
    	//String newUser = userDto.getLogin();
    	//String email = userDto.getEmail();
    	//List<UserDto> existingUsers = getUsers();
    	//дальше - см где throw
    	
    	User user = new User(); //вот! создали ЮЗЕРА
        user.setLogin(userDto.getLogin());
        user.setEmail(userDto.getEmail());
        User userByLogin=this.userJPADao.findByLogin(userDto.getLogin());
        User userByEmail=this.userJPADao.findByEmail(userDto.getEmail());
        if (((userByLogin)!=null) || ((userByEmail)!=null) )
        {
        	log.error("Failed to registerUser. The Login or Email already exists");
        	//throw new UserAlreadyExistsException();
        	return null;//моё художество. надо красивее
        }
        
        
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
//        .add(new Role("VIEWER")); //если мы создаем роли - их у юзера быть не может. т.о. Set равен нулю! 
        Role role=new Role();
        role=roleJPADao.findByRoleName("ROLE_VIEWER");//находит!
        HashSet<Role> roles = new HashSet<Role>();//null;//user.getRoles();
        
        roles.add(role);//где-то ошибся в синтаксисе?!
        user.setRoles(roles);
        User savedUser = this.userJPADao.save(user); //ЗАПИСАЛИ ЮЗЕРА
        //TODO автовход. Регистрация нового фбукюзера сделана.
        
        UserDto registeredUser = UserMapper.mapUserDto(savedUser); 
        
        try {
			emailSender.sendEmailToAdmin(registeredUser,1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("FFFailed to sent email");
		}
        
        return registeredUser;
    }

	@Override
	public void updateUser(String login, UserDto userDto, MultipartFile file) {
//        User user = this.userJPADao.findById(id).orElse(null);
		User user = this.userJPADao.findByLogin(login);
		if (user != null) {
			user.setLogin(userDto.getLogin());
			user.setEmail(userDto.getEmail());
			this.userJPADao.save(user);
		}
		try {
			LogoFileUploader.updateOrCreateLogo(file, userDto);
		} catch (IOException e) {
			log.error("Failed to upload image. Error message: {}", e.getMessage());
		}
		
		try {
			emailSender.sendEmailFromAdmin(user,1);
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

//    @Override
//    public void assingPetToUser(UserPetIdsDto ids) {
//        User user = this.userJPADao.findById(ids.getUserId()).orElse(null);
//        Pet pet = this.petJPADao.findById(ids.getPetId()).orElse(null);
//        pet.setUser(user);
//        this.petJPADao.save(pet);
//        log.info("Pet assigned to user {}!", user.getUserName());
//    }

//    @Override
//    public void getBookByIsbn(String isbn) {
//        BookDetails details = this.webScraper.getBookDetailsFromWeb(isbn);
//        String stop = "stop";
//    }

//    @Autowired
//    private IUserDao userDao;
//
//    @Override
//    public UserDto findUser(int id) {
//        User user = this.userDao.get(id);
//        return UserMapper.mapUserDto(user);
//    }
//
//    @Override
//    @Transactional
//    public UserDto createUser(UserDto user) {
//        User entity = UserMapper.mapUser(user);
//        entity.setPets(new ArrayList<>());
//        User savedUser = this.userDao.create(entity);
//        return UserMapper.mapUserDto(savedUser);
//    }
//
//    @Override
//    @Transactional
//    public void updateUser(int id, UserDto user) {
//        User entity = this.userDao.get(id);
//        entity.setFirstName(Optional.ofNullable(user.getFirstName()).orElse("DefaultName"));
//        entity.setSalary(user.getSalary());
//        this.userDao.update(entity);
//    }
//
//    @Override
//    @Transactional
//    public void deleteUser(int id) {
//        User entity = this.userDao.get(id);
//        this.userDao.delete(entity);
//    }
//
//    @Override
//    public List<UserDto> getUsers() {
//        return UserMapper.mapUserDtos(this.userDao.getAll());
//    }
}
