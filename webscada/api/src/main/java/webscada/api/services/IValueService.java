package webscada.api.services;

import java.util.List;

import org.springframework.stereotype.Service;

import webscada.api.dto.DevDto;
import webscada.api.dto.ValDevIdsDto;
import webscada.api.dto.ValueDto;

@Service
public interface IValueService {

	ValueDto findValue(long id);

	ValueDto createValue(ValueDto value);

	void updateValue(long id, ValueDto valueDto);

	void deleteValue(long id);

	List<ValueDto> findValues();

	List<ValueDto> findValuesByDev(DevDto dev);

	void assignValueToDev(ValDevIdsDto ids);

}
