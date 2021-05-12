package webscada.api.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import webscada.api.dto.DevTypeDto;
import webscada.entity.DevType;

@Mapper
public interface DevTypeMapper {
	
	DevTypeMapper INSTANCE = Mappers.getMapper( DevTypeMapper.class );
	
	DevType mapDevType(DevTypeDto source);
	
	DevTypeDto mapDevTypeDto(DevType source);
	
}
