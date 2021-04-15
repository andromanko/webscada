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
    
//	@Autowired
//    private IDevTypeJPADao devTypeJPADao;
  
	@Override
	public DevDto createDev(DevDto devDto) {
		Dev entity = DevMapper.mapDev(devDto);
        return DevMapper.mapDevDto(this.devDao.save(entity));
//		Dev dev=new Dev();
//		dev.setDevName(devDto.getDevName());
//		dev.setIP(devDto.getIP());
//		dev.setPort(devDto.getPort());
//		DevType type=new DevType();
//		//type=typeJPADao.findByType("FX5");
//		//dev.setDevType(type);
//		Dev newDev=this.devJPADao.save(dev);
//		return DevMapper.mapDevDto(newDev);
	}

//	@Override
//	public DevDto findDevByKeyWords(String KeyWords) {
//		// TODO Auto-generated method stub
//		return null;
//	}
////	@Override
//	public Integer getData(DevDto dev) {
//		// TODO Auto-generated method stub
//		return null;
//	}
	
////	@Override
//	public void assignDevToType(DevTypeIdsDto ids) {
//        DevType type = this.typeJPADao.findById(ids.getTypeId()).orElse(null);
//        Dev dev = this.devJPADao.findById((long) ids.getDevId()).orElse(null);
//        dev.setType(type);
//        this.devJPADao.save(dev);
//        log.info("Dev assigned to type {}!");//, type.getId());
//    }

	@Override
	public void deleteDev(long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public DevDto findDev(long id) {
		//TODO косяк long/int
		Dev dev = this.devDao.findById( id).orElse(null);
        return (dev != null ) ? DevMapper.mapDevDto(dev) : null;
	}

	@Override
	public void updateDev(long id, DevDto devDto) {
		//TODO косяк с id int/long
		//TODO Уточнить как эта хрень работает
		Dev dev = this.devDao.findById( id).orElse(null);
        if(dev != null) {
            this.devDao.save(dev);
        }
	}

//	@Override
//	public DevDto findDevByType(DevTypeDto devType) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	@Override
	public List<DevDto> getDevs() {
		// TODO Auto-generated method stub
		return DevMapper.mapDevDtos(devDao.findAll());
	}

//	@Override
//	public DevDto findDevByType(DevTypeDto devType) {
//		// TODO Auto-generated method stub
//		return null;
//	}






}
