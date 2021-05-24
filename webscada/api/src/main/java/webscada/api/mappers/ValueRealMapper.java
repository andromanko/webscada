package webscada.api.mappers;

import java.util.List;
import java.util.stream.Collectors;

import lombok.experimental.UtilityClass;
import webscada.api.dto.ValueReal;
import webscada.entity.Value;

@UtilityClass
public class ValueRealMapper {

//    public Value mapValue(ValueDto source) {
//        return Value.builder()
//                .id(source.getId())
//                .name(source.getName())
//                .units(source.getUnits())
//                .devId(source.getDevId())
//                .max(source.getMax())
//                .maxMs(source.getMaxMs())
//                .maxEventId(source.getMaxEventId())
//                .min(source.getMin())
//                .minMs(source.getMinMs())
//                .minEventId(source.getMinEventId())
//                .build();
//    }

	public ValueReal mapValueReal(Value source) {
		return ValueReal.builder().id(source.getId()).name(source.getName()).units(source.getUnits()).build();
	}
//    public List<Value> mapValues(List<ValueDto> source) {
//        return source.stream().map(ValueRealMapper::mapValue).collect(Collectors.toList());
//    }

	public List<ValueReal> mapValuesReal(List<Value> source) {
		return source.stream().map(ValueRealMapper::mapValueReal).collect(Collectors.toList());
	}
}
