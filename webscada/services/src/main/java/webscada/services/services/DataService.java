package webscada.services.services;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.digitalpetri.modbus.master.ModbusTcpMaster;
import com.digitalpetri.modbus.master.ModbusTcpMasterConfig;

import lombok.extern.slf4j.Slf4j;
import webscada.api.dao.IDevJPADao;
import webscada.api.dto.DataDto;
import webscada.api.dto.DevDto;
import webscada.api.dto.DevTypeDto;
import webscada.api.dto.DevTypeIdsDto;
import webscada.api.mappers.DevMapper;
import webscada.api.services.IDataService;
import webscada.api.services.IDevService;
import webscada.entity.Dev;
import webscada.entity.DevType;


@Slf4j
@Service
public class DataService implements IDataService {
	//показывает СТАРТОВАЛО ЛИ ВООБЩЕ ПРИЛАГА? /ТАК БЫЛО В ПРИМЕРЕ. если не стартовало - то и читать не будем
	private volatile boolean started = false;
	
	@Autowired
	private IDevService devService;
	
	@Autowired
    private IDevJPADao devDao; //тут нужно сделать JPAdao - чтобы вытягивать инфо о данных которые нужно прочитать
    
	//здесь будут данные о текущем состоянии устройств. Скооннекчен/не сконнекчен DevID и Bool
	private Map<Long,Boolean> devState= new HashMap<>();
	
	//здесь будут данные, считанные из устройств //ID данных, и само данное.ну, пока считаем данные целочисленными
	//TODO сделать данные общими. Может быть Number? 
	private Map<Long, Number> currentData= new HashMap<>();
	
	//коннекшн устройства открыть
	
	//коннекшн устройства закрыть
	
	//коннекшн всех устройств открыть
	//@Scheduled(fixedRate=30000)
	//
	private void DevicesConnect() {
		
		//List<DataDto> data = new LinkedList<DataDto>();
		
		//получаем список устройств
		//TODO сделать каждому устройству ENABLED
//		List<DevDto> devices = devService.getDevs();
//	//пробегаемся по кажодму устройству и устанавливаем коннект	 //если коннекта еще нет =)	
//		for (DevDto device : devices) {
//			//TODO проверка на то, установлен ли уже коннект? 
//			ModbusTcpMasterConfig config = new ModbusTcpMasterConfig.Builder(device.getIP())
//		            .setPort(device.getPort())
//		            .build();			
//			//задаем новый конфиг
//			ModbusTcpMaster master = new ModbusTcpMaster(config);
//			//коннектимся
//			master.connect();
//			devState.put(device.getId(),true);//типа сконнектились
//			//логгмируем. пока нужно понять как знать установлен ли коннект
//			log.debug(master.getLateResponseCounter().toString());
//		}
	}
	
	
	
	
	//TODO коннекшн всех устройтсв закрыть
	
	 public DataDto readData(DevDto devDto) {
		 //вытащить всё что читаем из Dev
		//пробежаться for each
		//читать каждую переменную
		
		//а для начала - считаем одну переменную!
		 DataDto dataDto = new DataDto();
		 dataDto.setDataName("dataName");
		 dataDto.setDataUnit("мегапаскалейТЕСТ!");
		 dataDto.setId(1);
		 int data=1;
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
		  
		if (!started) return null;
		//получаем список всех устройств
		//List<Dev> devices = devDao.findAll();
		List<DataDto> data = new LinkedList<DataDto>();
		//пробегаемся по списку и из каждого устройства читаем данные
		for (DevDto device : devices) {
			if (devState.get(device.getId())) {
				DataDto dataDto = readData(device);
				data.add(dataDto);
			}
			
			
		}
		return data;
	}


	
	


}
