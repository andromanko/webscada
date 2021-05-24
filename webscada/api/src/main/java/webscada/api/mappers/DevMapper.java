package webscada.api.mappers;

import java.util.List;
import java.util.stream.Collectors;

import lombok.experimental.UtilityClass;
import webscada.api.dto.DevDto;
import webscada.api.dto.DevTypeDto;
import webscada.entity.Dev;
import webscada.entity.DevType;

@UtilityClass
public class DevMapper {

	public Dev mapDev(DevDto source) {
		return Dev.builder().id(source.getId()).devName(source.getDevName()).ip(source.getIp()).addr(source.getAddr())
				.port(source.getPort()).devType(DevTypeMapper.mapDevType(source.getDevType())).build();
	}

	public DevDto mapDevDto(Dev source) {

		return DevDto.builder().id(source.getId()).devName(source.getDevName()).ip(source.getIp())
				.addr(source.getAddr()).port(source.getPort()).devType(DevTypeMapper.mapDevTypeDto(source.getDevType())).build();
		
	}

	public List<Dev> mapDevs(List<DevDto> source) {
		return source.stream().map(DevMapper::mapDev).collect(Collectors.toList());
	}

	public List<DevDto> mapDevDtos(List<Dev> source) {
		return source.stream().map(DevMapper::mapDevDto).collect(Collectors.toList());
	}
}
