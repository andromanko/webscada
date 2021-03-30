package webscada.api.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import webscada.api.dto.UserDto;
//import webscada.api.dto.UserPetIdsDto;

@Service
public interface IUserService {

    UserDto findUser(long id);
    
    UserDto findUserByLogin(String login);
    
//    UserDto findUserByEmail(String email);
    
    UserDto createUser(UserDto user);
    
    void updateUser(String login, UserDto userDto, MultipartFile file);
    
    void deleteUser(long id);
    
    List<UserDto> getUsers();

//    void assingPetToUser(UserPetIdsDto ids);

//    void getBookByIsbn(String isbn);
}
