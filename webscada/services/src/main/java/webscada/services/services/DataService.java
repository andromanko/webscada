package webscada.services.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import webscada.api.dao.IValueJPADao;
import webscada.api.dto.DevDto;
import webscada.api.dto.DevTypeDto;
import webscada.api.dto.ValueReal;
import webscada.api.mappers.DevMapper;
import webscada.api.services.IDataService;
import webscada.api.services.IDevService;
import webscada.api.services.IEventService;
import webscada.api.services.IUserService;
import webscada.api.utils.IModbusTCP;
import webscada.entity.Value;
import webscada.utils.communication.WebScraper;

@Slf4j
@Service
public class DataService implements IDataService {

	private static Map<Long, ValueReal> data = new HashMap<>();

	@Autowired
	private IValueJPADao valueJPADao;

	@Autowired
	private IDevService devService;

	@Autowired
	private IModbusTCP modbusTCP;

	@Autowired
	private IEventService eventService;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private WebScraper webScrapper;
	
	@Override
	public Map <Long, ValueReal> readAllData() {
		List<DevDto> devices = devService.getDevs();

		// TODO сделать через stream map collect
		// source.stream().map(DevMapper::mapDev).collect(Collectors.toList());
		for (DevDto device : devices) {
			List<Value> values = valueJPADao.findByDevId(DevMapper.mapDev(device));
			if (!values.isEmpty()) {
				data.putAll(readDevData(device, values));
			}
		}
		analizeData();
		return data;
	}

	private Map <Long, ValueReal> readDevData(DevDto devDto, List<Value> values) {

		DevTypeDto devType = devDto.getDevType();
		Map <Long, ValueReal> vals  = new HashMap<>();

		switch (devType.getId()) {
		case 1:
			vals.putAll(modbusTCP.start(DevMapper.mapDev(devDto), values));
			return vals;
		case 2:
			// TODO FX5U next version
			return null;
		case 3:
			// TODO next version readWebData( devDto, values);
			vals.putAll(webScrapper.start(DevMapper.mapDev(devDto), values));	
//			ValueReal value = ValueReal.builder().id(values.get(0).getId()).name("testDevTypeId=3").units("testUnits").number(13.3)
//					.build();
			//vals.putAll(WebScrapper.get(DevMapper.mapDev(devDto), values));
			return vals;
		default:
			log.info("dev type not defined" + devType);
			return vals;
		}
	}

	@Override
	public boolean writeData(ValueReal valueReal, DevDto devDtoo) {
		// TODO Auto-generated method stub
		return false;
	}

	private void analizeData() {
		//String user = SecurityContextHolder.getContext().getAuthentication().getName();
		List<Value> values = valueJPADao.findAll();
		for (Value value : values) {
			long id = value.getId();
			ValueReal valueReal = data.get(id);
			if (valueReal != null) {

				double max = (double) value.getMax();
				double min = (double) value.getMin();
				double number = (double) ((int) valueReal.getNumber());
				
				if (number > max && !valueReal.isHigh()) {
				
					eventService.createEvent(value.getMaxEventId().getId(), (long)13, value.getDevId().getId());
					valueReal.setHigh(true);
					// TODO emailSender...
				} else if (number < max) {
					valueReal.setHigh(false);
				}

				if (number < min & !valueReal.isLow()) {
					//User user = UserMapper.mapUser(userService.findUserByLogin("Roma"));
					eventService.createEvent(value.getMinEventId().getId(), (long)13,value.getDevId().getId());
					valueReal.setLow(true);
						// TODO emailSender...
				}else if (number > min) {
					valueReal.setLow(false);
				}
				data.put(valueReal.getId(),valueReal);
			}
		}
	}
}
