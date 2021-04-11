package webscada.api.mappers;

import java.util.List;
import java.util.stream.Collectors;

import webscada.api.dto.DevDto;
import webscada.entity.Dev;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DevMapper {

    public Dev mapDev(DevDto source) {
        return Dev.builder()
                .id(source.getId())
                .devName(source.getDevName())
                .IP(source.getIP())
                .addr(source.getAddr())
                .port(source.getPort())
                .build();
    }

    public DevDto mapDevDto(Dev source) {
        return DevDto.builder()
                .id(source.getId())
                .devName(source.getDevName())
                .IP(source.getIP())
                .addr(source.getAddr())
                .port(source.getPort())
                .build();
    }

    public List<Dev> mapUsers(List<DevDto> source) {
        return source.stream().map(DevMapper::mapDev).collect(Collectors.toList());
    }
    
    public List<DevDto> mapUserDtos(List<Dev> source) {
        return source.stream().map(DevMapper::mapDevDto).collect(Collectors.toList());
    }
}
