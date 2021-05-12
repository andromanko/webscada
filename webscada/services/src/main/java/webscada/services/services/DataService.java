package webscada.services.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;
import webscada.api.dao.IDevJPADao;
import webscada.api.dao.IUserJPADao;
import webscada.api.dao.IValueJPADao;
import webscada.api.dto.DevDto;
import webscada.api.dto.ValueDto;
import webscada.api.dto.DevTypeDto;
import webscada.api.dto.DevTypeIdsDto;
import webscada.api.dto.UserDto;
import webscada.api.dto.ValDevIdsDto;
import webscada.api.mappers.DevMapper;
import webscada.api.mappers.ValueMapper;
import webscada.api.services.IValueService;
import webscada.api.services.IDataService;
import webscada.api.services.IDevService;
import webscada.entity.Value;
import webscada.entity.Dev;
import webscada.entity.DevType;

import webscada.utils.communication.modbusTCP;
import webscada.api.utils.IModbusTCP;

@Slf4j
@Service
public class DataService implements IDataService {

	private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

	private volatile boolean started = false;

	@Autowired
	private IValueJPADao valueJPADao;

	@Autowired
	private IDevService devService;

	@Autowired
	private IDevJPADao devDao;

	@Autowired
	private IModbusTCP modbusTCP;
	
	@Override
	public Map<Value, Number> readAllData() {

		List<DevDto> devices = devService.getDevs();
		Map<Value, Number> data = new HashMap<Value, Number>();
		for (DevDto device : devices) {
			data.putAll(readDevData(device));
		}
		return (data != null) ? data : null;
	}

	@Override
	public Map<Value, Number> readDevData(DevDto devDto) {
		
		// выбираем все data для данного dev
		List<Value> values = valueJPADao.findByDevId(DevMapper.mapDev(devDto));
		
		if (values==null) return null;
		
		
		
		DevTypeDto devType = devDto.getDevType();
		
		if (devType.getId() ==1) {
			 modbusTCP.start( DevMapper.mapDev(devDto), values);
			 Map<Value, Number> vals= new HashMap<Value, Number>();
			 vals.put(values.get(1), -1);
			 return vals;
		}
		if (devType.getId() ==2) {
			return readFX5Data( devDto, values);
		}
		if (devType.getId() ==3) {
			return readWebData( devDto, values);
		}

		return null;
	}

	@Override
	public boolean writeData(ValueDto dataDto, DevDto devDto) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public Map<Value, Number> readModbusTCPData(DevDto devDto, List<Value> values) {
		return null;
	}
	@Override
	public Map<Value, Number> readFX5Data(DevDto devDto, List<Value> values) {
		return null;
	}
	@Override
	public Map<Value, Number> readWebData(DevDto devDto, List<Value> values) {
		return null;
	}

}
