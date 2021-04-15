package webscada.api.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import webscada.api.dto.DevDto;
//import webscada.api.dto.UserPetIdsDto;
import webscada.api.dto.DevTypeDto;
import webscada.api.dto.DevTypeIdsDto;

@Service
public interface IDevService {

    DevDto findDev(long id);
    
//    DevDto findDevByKeyWords(String KeyWords);
    
//    DevDto findDevByType(DevTypeDto devType);
    
    DevDto createDev(DevDto dev);
    
    void updateDev(long id, DevDto dev);//, MultipartFile file);
    
    void deleteDev(long id);
    

    List<DevDto> getDevs();


   // void assignDevToType(DevTypeIdsDto ids);

//    void getBookByIsbn(String isbn);
}
