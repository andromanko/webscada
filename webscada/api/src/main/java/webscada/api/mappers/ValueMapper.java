package webscada.api.mappers;

import java.util.List;
import java.util.stream.Collectors;

import webscada.api.dto.ValueDto;
import webscada.entity.Value;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ValueMapper {

    public Value mapValue(ValueDto source) {
        return Value.builder()
                .id(source.getId())
                .name(source.getName())
                .units(source.getUnits())
                .devId(source.getDevId())
                .max(source.getMax())
                .maxMs(source.getMaxMs())
                .maxEventId(source.getMaxEventId())
                .min(source.getMin())
                .minMs(source.getMinMs())
                .minEventId(source.getMinEventId())
                .build();
    }

    public ValueDto mapValueDto(Value source) {
        return ValueDto.builder()
                .id(source.getId())
                .name(source.getName())
                .units(source.getUnits())
                .devId(source.getDevId())
                .max(source.getMax())
                .maxMs(source.getMaxMs())
                .maxEventId(source.getMaxEventId())
                .min(source.getMin())
                .minMs(source.getMinMs())
                .minEventId(source.getMinEventId())
                .build();
    }
    public List<Value> mapValues(List<ValueDto> source) {
        return source.stream().map(ValueMapper::mapValue).collect(Collectors.toList());
    }
    
    public List<ValueDto> mapValueDtos(List<Value> source) {
        return source.stream().map(ValueMapper::mapValueDto).collect(Collectors.toList());
    }
}
