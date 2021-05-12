package webscada.api.mappers;

//попытка сделать маппер через mapstruct. Eclipse не подтянулся. Совсем.
import java.util.List;

//import org.mapstruct.Mapper;

import webscada.api.dto.ValueDto;
import webscada.entity.Value;

//@Mapper
public interface IValueMapper {
	
	Value mapValue(ValueDto source);
	
	ValueDto mapValueDto(Value source);
	
	List<Value> mapValues(List<ValueDto> sources);
	
	List<ValueDto> mapValueDtos(List<Value> sources);
}
