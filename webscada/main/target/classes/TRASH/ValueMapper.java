package webscada.api.mappers;

import java.util.List;
import java.util.stream.Collectors;

import webscada.api.dto.ValueDto;
import webscada.entity.Value;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ValueMapper {

    public Value mapDev(ValueDto source) {
        return Value.builder()
                .id(source.getId())
                .name(source.getDataName())
                .units(source.getDataUnit())
//                .IP(source.getIP())
//                .addr(source.getAddr())
//                .port(source.getPort())
                .build();
    }

    public ValueDto mapDevDto(Value source) {
        return DevDto.builder()
                .id(source.getId())
                .devName(source.getDevName())
                .IP(source.getIP())
                .addr(source.getAddr())
                .port(source.getPort())
                .build();
    }

    public List<Dev> mapDevs(List<DevDto> source) {
        return source.stream().map(ValueMapper::mapDev).collect(Collectors.toList());
    }
    
    public List<DevDto> mapDevDtos(List<Dev> source) {
        return source.stream().map(ValueMapper::mapDevDto).collect(Collectors.toList());
    }
}
