package webscada.api.services;

import java.util.List;

import org.springframework.stereotype.Service;
import webscada.api.dto.ValueDto;
import webscada.api.dto.DevDto;
import webscada.api.dto.ValDevIdsDto;


@Service
public interface IValueService {

   ValueDto getValue(long id);
    
    //UserDto findUserByLogin(String login);
    
//    UserDto findUserByEmail(String email);
    
    ValueDto createValue(ValueDto value);
    
    void updateValue(long id, ValueDto valueDto);
    
    void deleteValue(long id);
    
    List<ValueDto> getValues();
    
    List<ValueDto> getValuesByDev(DevDto dev);

    void assingValueToDev(ValDevIdsDto ids);

}
