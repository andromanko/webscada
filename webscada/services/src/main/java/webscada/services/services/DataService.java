package webscada.services.services;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import webscada.api.dao.IDevJPADao;
import webscada.api.dto.DataDto;
import webscada.api.dto.DevDto;
import webscada.api.dto.DevTypeDto;
import webscada.api.dto.DevTypeIdsDto;
import webscada.api.mappers.DevMapper;
import webscada.api.services.IDataService;
import webscada.entity.Dev;
import webscada.entity.DevType;
import webscada.utils.communication.DevComm;

@Slf4j
@Service
public class DataService implements IDataService {

	@Autowired
    private IDevJPADao devDao; //тут нужно сделать JPAdao - чтобы вытягивать инфо о данных которые нужно прочитать
    
	
	
	@Override
	 public DataDto readData(DevDto devDto) {
		 //вытащить всё что читаем из Dev
		//пробежаться for each
		//читать каждую переменную
		
		//а для начала - считаем одну переменную!
		 DataDto dataDto = new DataDto();
		 dataDto.setDataName("dataName");
		 dataDto.setDataUnit("мегапаскалейТЕСТ!");
		 dataDto.setId(1);
		 int data=DevComm.DataReceive(DevMapper.mapDev(devDto));
		 dataDto.setValue(data);
		 
		 return dataDto;
	 };
	    
	@Override
	public boolean writeData(DataDto dataDto, DevDto devDto) {
		// TODO Auto-generated method stub
		return false;
	}
	//прочитать данные со всех устройств
	@Override 
	
	public List<DataDto> readAllData(List<DevDto> devices) {
		//получаем список всех устройств
		//List<Dev> devices = devDao.findAll();
		List<DataDto> data = new LinkedList<DataDto>();
		//пробегаемся по списку и из каждого устройства читаем данные
		for (DevDto device : devices) {
			DataDto dataDto = readData(device);
			data.add(dataDto);
		}
		return data;
	}


	
	


}
