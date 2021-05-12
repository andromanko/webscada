package webscada.api.services;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import webscada.api.dto.DevDto;
import webscada.api.dto.ValueDto;
import webscada.entity.Value;

@Service
public interface IDataService {

	Map<Value, Number> readDevData(DevDto devDto);

	boolean writeData(ValueDto dataDto, DevDto devDto);

	Map<Value, Number> readAllData();
	
	Map<Value, Number> readModbusTCPData(DevDto devDto, List<Value> values);
	Map<Value, Number> readFX5Data(DevDto devDto, List<Value> values);
	Map<Value, Number> readWebData(DevDto devDto, List<Value> values);
}
