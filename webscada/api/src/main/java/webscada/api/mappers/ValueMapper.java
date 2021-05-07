package webscada.api.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import webscada.api.dto.ValueDto;
import webscada.entity.Value;

@Mapper
public interface ValueMapper {
	Value mapValue(ValueDto source);
	
	List<Value> mapValue(List<ValueDto> sources);
}
