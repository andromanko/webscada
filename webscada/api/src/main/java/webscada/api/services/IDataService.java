package webscada.api.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import webscada.api.dto.DataDto;
import webscada.api.dto.DevDto;
//import webscada.api.dto.UserPetIdsDto;

@Service
public interface IDataService {

	
	
    DataDto readData(DevDto devDto);
    
    boolean writeData(DataDto dataDto, DevDto devDto);
    
    List<DataDto> readAllData(List<DevDto> devices);//прочитать данные со всех устройств
}
