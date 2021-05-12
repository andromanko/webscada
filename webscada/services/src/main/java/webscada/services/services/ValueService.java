package webscada.services.services;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import webscada.api.dao.IDevJPADao;
import webscada.api.dao.IValueJPADao;
import webscada.api.dto.DevDto;
import webscada.api.dto.ValDevIdsDto;
import webscada.api.dto.ValueDto;
import webscada.api.mappers.DevMapper;
import webscada.api.mappers.ValueMapper;
import webscada.api.services.IDevService;
import webscada.api.services.IValueService;
import webscada.entity.Dev;
import webscada.entity.Value;

@Service
public class ValueService implements IValueService {

	@Autowired
	private IValueJPADao valueJPADao;

	@Autowired
	private IDevService devService;


	public ValueDto findValue(long id) {
		Value value = this.valueJPADao.findById(id).orElse(null);
		return (value != null) ? ValueMapper.mapValueDto(value) : null;
	}

	public ValueDto createValue(ValueDto value) {
		Value entity = ValueMapper.mapValue(value);
		return ValueMapper.mapValueDto(this.valueJPADao.save(entity));
	}

	public void updateValue(long id, ValueDto valueDto) {

		Value value = this.valueJPADao.findById(id).orElse(null);
		if (value != null) {
			this.valueJPADao.save(value);
		}
	}

	public void deleteValue(long id) {
		this.valueJPADao.deleteById(id);
	}

	@Override
	public List<ValueDto> findValues() {
		List<Value> values = valueJPADao.findAll();
		return ValueMapper.mapValueDtos(values);
	}

	public List<ValueDto> findValuesByDev(DevDto dev) {
//TODO сделать поиск переменных по устройству
		List<Value> values = valueJPADao.findByDevId(DevMapper.mapDev(dev));
		return ValueMapper.mapValueDtos(values);
	}

	public void assignValueToDev(ValDevIdsDto ids) {

		// TODO boolean, implementing! assingValueToDev
	}

}
