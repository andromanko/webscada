package webscada.api.services;

import java.util.Map;

import org.springframework.stereotype.Service;

import webscada.api.dto.DevDto;
import webscada.api.dto.ValueReal;

@Service
public interface IDataService {

	boolean writeData(ValueReal valueReal, DevDto devDto);

//	Map<Long, ValueReal> getAllData();

	// List<ValueReal>
	Map<Long, ValueReal> readAllData();

}
