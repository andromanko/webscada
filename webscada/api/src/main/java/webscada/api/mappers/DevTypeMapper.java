package webscada.api.mappers;

import lombok.experimental.UtilityClass;
import webscada.api.dto.DevTypeDto;
import webscada.entity.DevType;

@UtilityClass
public class DevTypeMapper {

	public DevTypeDto mapDevTypeDto(DevType source) {
		return DevTypeDto.builder().id(source.getId()).type(source.getType())
				.description(source.getDescription()).url(source.getUrl()).build();
	}

	public DevType mapDevType(DevTypeDto source) {
		return DevType.builder().id(source.getId()).type(source.getType())
				.description(source.getDescription()).url(source.getUrl()).build();
	}
}
