package webscada.api.services;

import java.util.List;

import org.springframework.stereotype.Service;

import webscada.api.dto.DevDto;
import webscada.api.dto.DevTypeIdsDto;

@Service
public interface IDevService {

	DevDto findDev(long id);

	DevDto createDev(DevDto dev);

	void updateDev(long id, DevDto dev);// TODO MultipartFile file);

	void deleteDev(long id);

	List<DevDto> getDevs();

	void assignDevToType(DevTypeIdsDto ids);

}
