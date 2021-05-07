package webscada.api.services;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import webscada.api.dto.ValueDto;
import webscada.api.dto.DevDto;
//import webscada.api.dto.UserPetIdsDto;

@Service
public interface IDataService {

	
	Map<Long,Number> readDevData(DevDto devDto);
    
    boolean writeData(ValueDto dataDto, DevDto devDto);
    
    List<ValueDto> readAllData(List<DevDto> devices);//прочитать данные со всех устройств
}
