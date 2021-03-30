package webscada.api.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import webscada.api.dto.DevDto;
//import webscada.api.dto.UserPetIdsDto;

@Service
public interface IDevService {

    DevDto findDev(int id);
    
    DevDto findDevByKeyWords(String KeyWords);
    
//    UserDto findUserByEmail(String email);
    
    DevDto createDev(DevDto dev);
    
    void updateDev(int id, DevDto dev);//, MultipartFile file);
    
    void deleteDev(int id);
    
//    List<DevDto> getUsers();

//    void assingPetToUser(UserPetIdsDto ids);

//    void getBookByIsbn(String isbn);
}
