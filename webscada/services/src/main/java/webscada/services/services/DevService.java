package webscada.services.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import webscada.api.dao.IDevJPADao;

import webscada.api.dto.DevDto;
import webscada.api.dto.DevTypeDto;
import webscada.api.dto.DevTypeIdsDto;
import webscada.api.mappers.DevMapper;
import webscada.api.services.IDevService;
import webscada.entity.Dev;
import webscada.entity.DevType;

@Slf4j
@Service
public class DevService implements IDevService {

	@Autowired
	private IDevJPADao devDao;


	@Override
	public DevDto createDev(DevDto devDto) {
		Dev entity = DevMapper.mapDev(devDto);
		return DevMapper.mapDevDto(this.devDao.save(entity));
	}

	@Override
	public void deleteDev(long id) {
		this.devDao.deleteById(id);
	}

	@Override
	public DevDto findDev(long id) {
		// TODO косяк long/int
		Dev dev = this.devDao.findById(id);
		return (dev != null) ? DevMapper.mapDevDto(dev) : null;
	}

	@Override
	public void updateDev(long id, DevDto devDto) {
		// TODO косяк с id int/long
		// TODO Уточнить как эта хрень работает
		Dev dev = this.devDao.findById(id);
		if (dev != null) {
			this.devDao.save(dev);
		}
	}

	@Override
	public List<DevDto> getDevs() {
		return DevMapper.mapDevDtos(devDao.findAll());
	}

	@Override
	public void assignDevToType(DevTypeIdsDto ids) {
		// TODO assignDevToType
	}
}
